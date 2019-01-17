import com.aha.tech.TemplateApplication;
import com.aha.tech.utils.SpringContextUtil;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @Author: luweihong
 * @Date: 2018/8/7
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = SpringContextUtil.class)
@SpringBootTest(classes = TemplateApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisSerializerMapTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(RedisSerializerMapTest.class);

    public static final Integer SIZE = 100000;

    public static final String PUBLISH_KEY = "m_publish";

    @Resource
    private RedisTemplate redisTemplate;

    public void init() {
        LOGGER.debug("delete keys {}*", PUBLISH_KEY);
        String pattern = String.format("%s*", PUBLISH_KEY);
        redisTemplate.keys(pattern).forEach(obj -> redisTemplate.delete(obj));
    }

    @Test
    public void publish() {
        LOGGER.info("using {} serializer publish {} record data to redis server !", redisTemplate.getValueSerializer().toString(), SIZE);
        init();
        Map<String, Object> data = Maps.newHashMapWithExpectedSize(SIZE);
        IntStream.range(0, SIZE).forEach(i ->
                data.put(String.format("key_%s", i), String.format("val_%s", i))
        );
        // 1个map10万数据
        IntStream.range(0, 5).forEach(i -> {
            Long begin = System.currentTimeMillis();
            redisTemplate.opsForValue().set(PUBLISH_KEY, data);
            Long cost = System.currentTimeMillis() - begin;
            LOGGER.info("serializer {} record data and publish to redis server , cost is : {}", SIZE, cost);
        });
    }

    @Test
    public void load() {
        IntStream.range(0, 5).forEach(i -> {
            Long begin = System.currentTimeMillis();
            Object v = redisTemplate.opsForValue().get(PUBLISH_KEY);
            Long cost = System.currentTimeMillis() - begin;
            LOGGER.info("using deserializer {}, cost is : {}", redisTemplate.getValueSerializer().toString(), cost);
        });
    }

}
