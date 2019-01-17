import com.aha.tech.TemplateApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import support.TimeVo;

import java.io.IOException;

/**
 * @Author: luweihong
 * @Date: 2018/12/14
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest(classes = TemplateApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JacksonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void serializationAndDeserialization() throws IOException {
        TimeVo timeVo = new TimeVo();
        String s = objectMapper.writeValueAsString(timeVo);
        System.out.println("序列化后的结果 : " + s);
        TimeVo newTimeVo = objectMapper.readValue(s,TimeVo.class);
        System.out.println("反序列化的结果 : " + newTimeVo);
    }

}
