import com.aha.tech.TemplateApplication;
import com.aha.tech.base.commons.utils.IpUtil;
import com.aha.tech.utils.KeyGenerateUtil;
import com.aha.tech.utils.SpringContextUtil;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: luweihong
 * @Date: 2018/7/30
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = SpringContextUtil.class)
@SpringBootTest(classes = TemplateApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class redisTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(redisTest.class);


    @Resource
    private RedisTemplate redisTemplate;

    @Before
    public void before() {
        int size = 10;
        redisTemplate.keys("test*").forEach((k) -> redisTemplate.delete(k));
        Map<String, Object> cacheData = Maps.newHashMapWithExpectedSize(size);
        String k = "test";

        for (int i = 0; i < size; i++) {
            cacheData.put("key" + i, "val" + i);
        }

        redisTemplate.opsForValue().set(k, cacheData);
    }

    @Test
    public void loadCache() {
        Long begin = System.currentTimeMillis();
        Object v = redisTemplate.opsForValue().get("test");
        Long cost = System.currentTimeMillis() - begin;
        LOGGER.info("v is : {} ", v);
        LOGGER.info("cost is : {} ms", cost);
    }

    @Test
    public void keySet() {
        String key = KeyGenerateUtil.Task.TEST;
        redisTemplate.opsForValue().set(key, "111");
        redisTemplate.expire(key, 10000L, TimeUnit.MILLISECONDS);
    }

    @Test
    public void keyDelete() {
        String key = KeyGenerateUtil.Task.TEST;
        redisTemplate.delete(key);
    }

    @Test
    public void hashSet() throws Exception {
        String key = KeyGenerateUtil.Task.TEST;
        redisTemplate.delete(key);
        String ip = IpUtil.getLocalHostAddress();
        Map<String, Object> registerIps = redisTemplate.opsForHash().entries(key);
        if (CollectionUtils.isEmpty(registerIps)) {
            Map<String, Object> v = Maps.newHashMap();
            v.put(ip, 123);
            redisTemplate.opsForHash().putAll(key, v);
        } else {
            redisTemplate.opsForHash().put(key, ip, 123);
        }
    }

    @Test
    public void listOps() {
        String key = "asd";
        ListOperations lops = redisTemplate.opsForList();
        for (int i = 0; i < 10; i++) {
            lops.rightPush(key, i);
        }
    }

    @Test
    public void fetchList() {
        String key = "asd";
        ListOperations lops = redisTemplate.opsForList();
        int index = 7;

        Long o = lops.remove(key, index, lops.index(key, index));
        Assert.assertTrue("删除失败", o > 0);
    }

}
