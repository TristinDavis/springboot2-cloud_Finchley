import com.aha.tech.TemplateApplication;
import com.aha.tech.base.commons.utils.DateUtil;
import com.aha.tech.model.entity.ProductEntity;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import support.Product;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @Author: luweihong
 * @Date: 2018/8/23
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest(classes = TemplateApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ElasticsearchTemplateOpsTest {

    private Logger LOGGER = LoggerFactory.getLogger(ElasticsearchTemplateOpsTest.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    String DESC = "我是火车王,我喜欢玩炉石传说";


    @Test
    public void save() {
        int start = 0;
        int endPoint = DESC.length();

        List<String> nameList = Lists.newArrayList();
        nameList.add("花生");
        nameList.add("大米");
        nameList.add("油");
        nameList.add("辣条");
        nameList.add("方便面");
        nameList.add("黄酒");
        nameList.add("白酒");
        nameList.add("盐");
        nameList.add("酱油");

        IntStream.range(0, 20).forEach(v -> {
            ProductEntity product = new ProductEntity();
            product.setId(v * 1L);
            int rdr = RandomUtils.nextInt(start, endPoint);
            product.setDesc(DESC.substring(start, rdr));
            product.setAmount(RandomUtils.nextInt(1000, 2000) * 1L);
            int r = RandomUtils.nextInt(0, nameList.size());
            product.setName(nameList.get(r));
            Instant t = Instant.now().minus(Duration.ofDays(v));
            product.setCreated_at(Date.from(t));
            IndexQuery indexQuery = new IndexQueryBuilder().withObject(product).build();
            elasticsearchTemplate.index(indexQuery);
        });

    }

    /**
     *
     /**
     * PUT product/_mapping/eat/
     * {
     *   "properties": {
     *     "created_at": {
     *       "type":     "text",
     *       "fielddata": true
     *     }
     *   }
     * }
     *
     * 需要将排序的字段
     */
    @Test
    public void get() {
        // 分页
        int page = 0;
        int size = 5;

        // 排序 created_at.keyword
        FieldSortBuilder sortBuilder = new FieldSortBuilder("created_at").order(SortOrder.ASC);
        // 所有字段 全文检索
//        QueryBuilder queryBuilder = new QueryStringQueryBuilder("我");
        // 根据field分词查询
//        QueryBuilder queryBuilder = QueryBuilders.matchQuery("desc","我是火车王");

        QueryBuilder queryBuilder1 = QueryBuilders.termQuery("name", "黄酒");

        Instant lte = Instant.now().minus(Duration.ofDays(1));
        QueryBuilder queryBuilder2 = QueryBuilders.rangeQuery("created_at").lte(DateUtil.convertDate2Str(null, Date.from(lte)));

        // must = and, mustNot = Not  should = or
        QueryBuilder query = QueryBuilders.boolQuery().must(queryBuilder1).must(queryBuilder2);

        String[] fields = new String[]{"id", "name", "created_at"};
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withPageable(PageRequest.of(page, size))
                .withSort(sortBuilder)
                // 设置后除了指定字段,其他字段均为null
                .withFields(fields)
                .build();
        List<Product> productList = elasticsearchTemplate.queryForList(searchQuery, Product.class);
        LOGGER.info("list response : {}", productList);
    }

    @Test
    public void delete() {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("id", 0);
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setQuery(queryBuilder);
        deleteQuery.setIndex("product");
        deleteQuery.setType("eat");
        elasticsearchTemplate.delete(deleteQuery);
    }

    /**
     * MinAggregationBuilder --> select min(field) as alias from index  group by field
     * MaxAggregationBuilder --> select max(field) as alias from index  group by field
     * CountValueAggregationBuilder -> select count(field) as alias from index  group by field
     */
    @Test
    public void minAgg() {
        MinAggregationBuilder aggregationBuilder = AggregationBuilders.min("min_amount").field("amount");
        Instant gte = Instant.now().minus(Duration.ofDays(10));
        QueryBuilder queryBuilder = QueryBuilders.rangeQuery("created_at").gte(DateUtil.convertDate2Str(null, Date.from(gte)));
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("product")
                .withTypes("eat")
                .withQuery(queryBuilder)
                .addAggregation(aggregationBuilder)
                .build();

        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());

        Min v = aggregations.get("min_amount");
        LOGGER.info("min response : {}", v.getValue());

    }
}
