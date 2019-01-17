import com.aha.tech.TemplateApplication;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStatsAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * @Author: luweihong
 * @Date: 2018/8/17
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest(classes = TemplateApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ElasticsearchOpsTest {

    private Logger LOGGER = LoggerFactory.getLogger(ElasticsearchOpsTest.class);

    @Autowired
    private TransportClient transportClient;

    /**
     * 创建索引
     */
    @Test
    public void createIndex() {
        CreateIndexResponse indexResponse = transportClient
                .admin()
                .indices()
                .prepareCreate("index_name")
                .get();

        Assert.assertTrue("创建失败", indexResponse.isAcknowledged());
        LOGGER.info("创建成功");
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndex() {
        DeleteIndexResponse deleteIndexResponse =
                transportClient
                        .admin()
                        .indices()
                        .prepareDelete("index_name")
                        .get();
        Assert.assertTrue("删除失败", deleteIndexResponse.isAcknowledged());
        LOGGER.info("删除成功");
    }

    /**
     * 插入数据
     * @throws IOException
     */
    @Test
    public void save() throws IOException {
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("user_id", 123456L)
                .field("wallet", 20000)
                .field("name", "黄客气是2B")
                .field("created_at", new Date())
                .endObject();

        String index = "user";
        String type = "account";
        String id = "3";
        IndexResponse response = transportClient.prepareIndex(index, type, id).setSource(builder).get();
        RestStatus status = response.status();
        LOGGER.info("rest status is {} ", status);
        Assert.assertTrue("插入数据失败", status == RestStatus.CREATED);
        LOGGER.debug("插入数据成功 ===> {}", response);
    }

    /**
     * 根据索引,类型,id查询
     */
    @Test
    public void sampleGet() {
        String index = "user";
        String type = "account";
        String id = "1";
        GetResponse response = transportClient.prepareGet(index, type, id).get();
        Map<String, Object> result = response.getSource();
        LOGGER.debug("查询结果 {}", result);
    }

    /**
     * 删除数据
     */
    @Test
    public void delete() {
        String index = "user";
        String type = "account";
        String id = "1";
        DeleteResponse response = transportClient.prepareDelete(index, type, id).get();
        RestStatus status = response.status();
        LOGGER.info("response rest status : {}", status);
        Assert.assertTrue("删除数据失败", status == RestStatus.OK);
    }

    /**
     * 根据条件删除
     * 可以删除多个索引
     * matchQuery(field,value)
     * .source(索引,索引,缩影)
     *
     * 删除索引 user 下 user_id是123456L的数据
     */
    @Test
    public void deleteByQuery() {
        String field = "user_id";
        Long value = 123456L;
        String[] indices = new String[]{"user"};
        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(transportClient)
                .filter(matchQuery(field, value))
                .source(indices)
                .get();
        long deleted = response.getDeleted();

        LOGGER.info("deleted count : {} ", deleted);
    }

    /**
     * 异步删除
     */
    @Test
    public void asyncDeleteByQuery() {
        String field = "user_id";
        Long value = 123456L;
        String[] indices = new String[]{"user"};

        DeleteByQueryAction.INSTANCE.newRequestBuilder(transportClient)
                .filter(matchQuery(field, value))
                .source(indices)
                .execute(new ActionListener<BulkByScrollResponse>() {
                    @Override
                    public void onResponse(BulkByScrollResponse response) {
                        long deleted = response.getDeleted();
                        LOGGER.info("deleted count : {} ", deleted);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        LOGGER.error("Handle the exception !");
                    }
                });
    }

    /**
     * 更新一个文档
     * 有匹配field 则修改对应的value
     * 否则增加filed : value
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void update() throws IOException, ExecutionException, InterruptedException {
        String index = "user";
        String type = "account";
        String id = "1";


        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index);
        updateRequest.type(type);
        updateRequest.id(id);
        updateRequest.doc(jsonBuilder()
                .startObject()
                .field("gender", "123")
                .endObject());
        UpdateResponse response = transportClient.update(updateRequest).get();
        RestStatus status = response.status();
        LOGGER.info("update rest status : {}", status);

        Assert.assertTrue("更新失败", status == RestStatus.OK);
        LOGGER.info("更新成功");
    }

    /**
     * 如果文档不存在则插入
     * 存在则更新
     */
    @Test
    public void upsert() throws IOException, ExecutionException, InterruptedException {
        String index = "user";
        String type = "account";
        // 新增一个id
        String id = "2";
        String oldValue = "黄客气是傻逼";
        String newValue = "黄客气是傻逼!!";

        IndexRequest indexRequest = new IndexRequest(index, type, id)
                .source(jsonBuilder()
                        .startObject()
                        .field("user_id", 66L)
                        .field("wallet", 500000L)
                        .field("name", oldValue)
                        .field("gender", "male")
                        .endObject());

        UpdateRequest updateRequest = new UpdateRequest(index, type, id)
                .doc(jsonBuilder()
                        .startObject()
                        .field("name", newValue)
                        .endObject())
                .upsert(indexRequest);

        UpdateResponse response = transportClient.update(updateRequest).get();
        RestStatus status = response.status();
        LOGGER.info("update rest status : {}", status);
        if (RestStatus.CREATED == status) {
            LOGGER.info("文档不存在,新建成功");
        }

        if (RestStatus.OK == status) {
            LOGGER.info("文档已存在,更新成功");
        }
    }

    /**
     * 指定列段返回
     */
    @Test
    public void specialFiled() {
        String index = "user";
        String type = "account";
        // 构建一个查询request
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(index).setTypes(type);
        MatchQueryBuilder queryBuilders = matchQuery("user_id", 123456);
        // 查询条件
        searchRequestBuilder.setQuery(queryBuilders);
        // 排序
        searchRequestBuilder.addSort("user_id", SortOrder.ASC);

        // 指定字段返回
        String[] includes = new String[]{"user_id"};
        // 排除哪些字段
        String[] excludes = new String[]{};
        searchRequestBuilder.setFetchSource(includes, excludes);
        // limit 数量
        searchRequestBuilder.setSize(100);
        // future
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> result = hit.getSource();
            LOGGER.info("hit is : {}", result);
        }

        LOGGER.debug("search response is : {} ", searchResponse);
    }

    /**
     * scroll 滚动分页 非实时性
     * 用于保存x时间上下文,防止在使用阶段数据被更新
     * 性能比深度分页好
     */
    @Test
    public void pageSearch() {
        SearchResponse scrollResp = transportClient.prepareSearch("user").setTypes("account")
                .addSort("wallet", SortOrder.ASC)
                // 上下文保存60000毫秒
                .setScroll(new TimeValue(60000))
                .setQuery(QueryBuilders.matchAllQuery())
                // 最多返回分片数量 * size
                .setSize(100).get();
        LOGGER.debug("第一次返回的 scroll信息 : {} ", scrollResp);
        do {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                Map<String, Object> result = hit.getSource();
                LOGGER.info("数据信息 : {}", result);
            }

            // 第二步 根据scroll的id去各个分区查询
            scrollResp = transportClient.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
        } while (scrollResp.getHits().getHits().length != 0);
    }

    /**
     * 多个查询
     *
     * queryStringQuery 分词查询
     *
     * 第一个查询 返回 id =2 和 id = 3
     *
     * 第二个查询是 id = 1 和id = 3
     *
     * 合并后共计4条row
     */
    @Test
    public void multiSearch() {
        // queryString 分词查询
        String index = "user";
        String type = "account";
        SearchRequestBuilder srb1 = transportClient
                .prepareSearch(index).setTypes(type).setQuery(QueryBuilders.queryStringQuery("黄客气")).setSize(10);

        // 匹配查询
        SearchRequestBuilder srb2 = transportClient
                .prepareSearch(index).setTypes(type).setQuery(matchQuery("user_id", 123456)).setSize(10);

        MultiSearchResponse sr = transportClient.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .get();

        // You will get all individual responses from MultiSearchResponse#getResponses()
        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            LOGGER.debug("this item value is : {}", item);
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits();
            SearchHit[] searchHits = response.getHits().getHits();
            for (SearchHit s : searchHits) {
                Map<String, Object> result = s.getSource();
                LOGGER.info("result : {}", result);
            }
        }

        LOGGER.info("response nbHits : {}", nbHits);
    }

    /**
     * 聚合
     * metric aggregations
     * min,max,avg 等聚合方法
     * min : MinAggregationBuilder
     * max : MaxAggregationBuilder
     * avg : AvgAggregationBuilder
     * count : ValueCountAggregationBuilder
     * query 时间范围内
     */
    @Test
    public void aggregations() {
        String alias = "min_wallet";
        String field = "wallet";

        MinAggregationBuilder aggregation =
                AggregationBuilders
                        .min(alias)
                        .field(field);
        Instant gte = Instant.now().minus(Duration.ofDays(400));
        Instant lte = Instant.now();
        SearchResponse response = transportClient.prepareSearch("user").setTypes("account")
                .setQuery(QueryBuilders.rangeQuery("created_at").gte(gte.toString()).lte(lte.toString()))
                .addAggregation(aggregation).execute().actionGet();
        Min minWallet = response.getAggregations().get(alias);
        LOGGER.info("min wallet : {}", minWallet.getValue());
    }

    /**
     * 多种指标聚合 全家桶
     */
    @Test
    public void extendedAggregation() {
        ExtendedStatsAggregationBuilder aggregation =
                AggregationBuilders
                        .extendedStats("agg")
                        .field("wallet");
        Instant gte = Instant.now().minus(Duration.ofDays(400));
        Instant lte = Instant.now();
        SearchResponse sr = transportClient.prepareSearch("user").setTypes("account")
                .setQuery(QueryBuilders.rangeQuery("created_at").gte(gte.toString()).lte(lte.toString()))
                .addAggregation(aggregation).execute().actionGet();
        ExtendedStats agg = sr.getAggregations().get("agg");
        double min = agg.getMin();
        double max = agg.getMax();
        double avg = agg.getAvg();
        double sum = agg.getSum();
        long count = agg.getCount();

        LOGGER.info("min wallet : {}", min);
        LOGGER.info("max wallet : {}", max);
        LOGGER.info("avg wallet : {}", avg);
        LOGGER.info("sum wallet : {}", sum);
        LOGGER.info("count wallet : {}", count);
    }


}
