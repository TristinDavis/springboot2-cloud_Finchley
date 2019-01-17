package com.aha.tech.config;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.network.NetworkModule;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.util.CollectionUtils;
import org.elasticsearch.transport.Netty3Plugin;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: luweihong
 * @Date: 2018/8/16
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@ConditionalOnProperty(name = "es.enable", havingValue = "on")
public class ElasticsearchConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchConfiguration.class);

    private String clusterNodes;

    private String clusterName;

    private String readTimeout;

    private Map<String, String> properties = new HashMap<>();

    private Settings settings() {
        Map<String, Object> settings = Maps.newHashMap();
        settings.putAll(this.properties);
        settings.put(NetworkModule.HTTP_TYPE_KEY, Netty3Plugin.NETTY_HTTP_TRANSPORT_NAME);
        settings.put(NetworkModule.TRANSPORT_TYPE_KEY, Netty3Plugin.NETTY_TRANSPORT_NAME);
        settings.put("cluster.name", clusterName);

        return Settings.builder().put(settings).build();
    }


    @Bean(name = "transportClient")
    protected TransportClient buildClient() {
        TransportClient preBuiltTransportClient = new PreBuiltTransportClient(settings());

        String[] hosts = StringUtils.isNotBlank(clusterNodes) ? clusterName.split(",") : null;

        if (!CollectionUtils.isEmpty(hosts)) {

            Arrays.stream(hosts).forEach(url -> {
                try {
                    String uri[] = clusterNodes.split(":");
                    String address = uri[0];
                    Integer port = Integer.valueOf(uri[1]);
                    preBuiltTransportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(address), port));
                } catch (UnknownHostException e) {
                    LOGGER.error("Error addTransportAddress,with url:{}.", url);
                }
            });
        }
        return preBuiltTransportClient;
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() {
        ElasticsearchTemplate template = new ElasticsearchTemplate(buildClient(), new ElasticsearchJacksonMapper());
        template.setSearchTimeout(readTimeout);
        return template;
    }

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(String readTimeout) {
        this.readTimeout = readTimeout;
    }

}
