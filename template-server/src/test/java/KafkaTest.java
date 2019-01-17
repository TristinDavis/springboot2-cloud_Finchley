import com.aha.tech.TemplateApplication;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import support.UserVO;

import java.util.concurrent.ExecutionException;

/**
 * @Author: luweihong
 * @Date: 2018/9/4
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = TemplateApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KafkaTest {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private Logger LOGGER = LoggerFactory.getLogger(KafkaTest.class);


    public static final String TOPIC = "foo3";

    /**
     * 发送一个kafka对象
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void sendSimpleMessage() throws ExecutionException, InterruptedException {
        UserVO userVO = new UserVO();
        ListenableFuture<SendResult> sendFuture = kafkaTemplate.send(TOPIC, JSON.toJSONString(userVO));
        SendResult s = sendFuture.get();
        LOGGER.debug("producer record info : {}", s.getProducerRecord());
        LOGGER.debug("producer record metadata : {}", s.getRecordMetadata());
    }

    @Test
    public void consumer(){
//        kafkaTemplate
    }

}
