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
import support.UserVO;

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
public class RedisSerializerObjTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(RedisSerializerObjTest.class);

    public static final Integer SIZE = 100000;

    public static final String PUBLISH_KEY = "v_publish";

    @Resource
    private RedisTemplate publicRedisTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    public void init() {
        LOGGER.debug("delete keys {}*", PUBLISH_KEY);
        String pattern = String.format("%s*", PUBLISH_KEY);
        publicRedisTemplate.keys(pattern).forEach(obj -> publicRedisTemplate.delete(obj));
    }

    @Test
    public void publish() {
        LOGGER.info("using {} serializer publish {} record data to redis server !", publicRedisTemplate.getValueSerializer().toString(), SIZE);
        init();
        Map<String, UserVO> data = Maps.newHashMapWithExpectedSize(SIZE);
        IntStream.range(0, SIZE).forEach(j -> data.put(String.valueOf(j), new UserVO()));
        IntStream.range(0, 5).forEach(i -> {
            Long begin = System.currentTimeMillis();
            publicRedisTemplate.opsForValue().set(String.format("%s_%s", PUBLISH_KEY, i), data);
            Long cost = System.currentTimeMillis() - begin;
            LOGGER.info("serializer {} record data and publish to redis server , cost is : {}", SIZE, cost);
        });
    }

    @Test
    public void load() {
        IntStream.range(0, 5).forEach(i -> {
            Long begin = System.currentTimeMillis();
            Object v = publicRedisTemplate.opsForValue().get(String.format("%s_%s", PUBLISH_KEY, i));
            Long cost = System.currentTimeMillis() - begin;
            LOGGER.info("using deserializer {}, cost is : {}", publicRedisTemplate.getValueSerializer().toString(), cost);
        });
    }

    /**
     * 多redis 数据源测试
     * 可以对同一个redis使用不同的database测试
     * redisTemplate使用database1
     * publicRedisTemplate使用database2
     */
    @Test
    public void redisDatasourceTest() {
        publicRedisTemplate.opsForValue().set("private_data_source", "public");
        redisTemplate.opsForValue().set("private_data_source", "private");
    }

}
