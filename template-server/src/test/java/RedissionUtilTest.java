import com.aha.tech.TemplateApplication;
import com.aha.tech.utils.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: luweihong
 * @Date: 2018/12/28
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
//@ContextConfiguration(classes = SpringContextUtil.class)
@SpringBootTest(classes = TemplateApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedissionUtilTest {

    @Test
    public void testLock() throws InterruptedException {
        String key = "test";
        int coreSize = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(coreSize);
        for (int i = 0; i < coreSize; i++) {
            CompletableFuture.runAsync(() -> {
                System.out.println("asdasd");
                String threadName = Thread.currentThread().getName();
                RedisLock redisLock = new RedisLock(key);
                try {
                    if (redisLock.lock()) {
                        System.out.println(threadName + "do something");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(threadName + " 释放锁");
                    redisLock.unlock();
                }
            }, executorService);
        }

        Thread.sleep(10000L);
    }
}
