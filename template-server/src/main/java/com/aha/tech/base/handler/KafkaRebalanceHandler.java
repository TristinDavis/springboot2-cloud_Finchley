package com.aha.tech.base.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author: luweihong
 * @Date: 2018/9/3
 * kafka rebalnce时候的处理
 * 这是个demo
 */
public class KafkaRebalanceHandler implements ConsumerAwareRebalanceListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaRebalanceHandler.class);

    private static final String COMMIT_META = "commit";

    /**
     * 在rebalance发生前
     *
     * 获取消费组已经成功处理的offset
     * 提交到对应的partition
     * 这个监听在触发rebalance前,消费组未提交offset时
     * @See The consumer method position and commited 2 look up offset in partition
     * @param consumer
     * @param partitions
     */
    public void onPartitionsRevokedBeforeCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        LOGGER.error("即将出发kafka rebalance动作,时间 : {}", Instant.now());

        ConcurrentMap<TopicPartition, OffsetAndMetadata> shouldCommitOffsets = Maps.newConcurrentMap();
        partitions.stream().forEach(topicPartition -> {
            Long unCommitOffset = consumer.position(topicPartition);
            OffsetAndMetadata offsetAndMetadata = consumer.committed(topicPartition);
            Long lastCommittedOffset = offsetAndMetadata.offset();
            LOGGER.error("partition : {} last committed offset : {}, unCommit offset is : {}", topicPartition, lastCommittedOffset, unCommitOffset);
            if (unCommitOffset > lastCommittedOffset) {
                LOGGER.info("存在已经拉取但是没有commit的数据");
                // 计算出一批数据哪些业务是自己做完的,可是没有数据啊..
                List<Long> commitOffsetList = Lists.newArrayList();
                commitOffsetList.stream().forEach(offset -> shouldCommitOffsets.put(topicPartition, new OffsetAndMetadata(offset, COMMIT_META)));
            }
        });

        if (!CollectionUtils.isEmpty(shouldCommitOffsets)) {
            shouldCommitOffsets.forEach((k, v) -> {
                LOGGER.error("即将要提交挂起的事务信息");
                LOGGER.error("topic : {} , offset : {}", k.topic(), v.offset());
            });

            consumer.commitSync(shouldCommitOffsets);
        }
    }

    /**
     * 发生在commit后,rebalance前
     * @param consumer
     * @param partitions
     */
    public void onPartitionsRevokedAfterCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        LOGGER.error("即将撤销分区,时间 : {}", Instant.now());

    }

    /**
     * 发生在rebalance后
     * 在rebalance成功后,consumer消费前
     * 重新定位消费组线程所处哪个分区
     * @param consumer
     * @param partitions
     */
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        LOGGER.error("kafka rebalance 结束,重新分配消费组新的分区,时间 : {}", Instant.now());

        partitions.stream().forEach(topicPartition -> {
            // consumer.committed 定位到last committed
            OffsetAndMetadata offsetAndMetadata = consumer.committed(topicPartition);
            Long offset = offsetAndMetadata.offset();
            LOGGER.error("新的分区定位 : topic : {} , offset : {}", topicPartition.topic(), offset + 1);
            consumer.seek(topicPartition, offset + 1);
        });

    }
}
