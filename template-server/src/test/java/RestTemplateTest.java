//import com.aha.tech.TemplateApplication;
//import com.aha.tech.templateserver.commons.response.RpcResponse;
//import com.aha.tech.templateserver.facade.model.dto.UserRequestDto;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.collect.Maps;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.client.RestTemplate;
//
//import javax.annotation.Resource;
//import java.beans.IntrospectionException;
//import java.lang.reflect.InvocationTargetException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Author: monkey
// * @Date: 2018/7/29
// */
//@RunWith(SpringRunner.class)
//@ActiveProfiles("local")
//@SpringBootTest(classes = TemplateApplication.class)
//public class RestTemplateTest {
//
//    private Logger logger = LoggerFactory.getLogger(RestTemplateTest.class);
//
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Before
//    public void before() {
//        logger.info("如果测试的uri是自己项目,需要先启动自己项目,否则链接拒绝!");
//    }
//
//    @Test
//    public void restGet() throws URISyntaxException {
//        logger.debug("get 请求使用 restTemplate.getForObject(URI,Response.class)");
//        Long userId = 123L;
//        logger.debug("rest的get请求,必须要用URI,如果使用String类型的url,其中的符号会导致请求不能解析");
//        String url = String.format("http://localhost:9892/paymentserver/rest/get/%s", userId);
//        RpcResponse resp = restTemplate.getForObject(new URI(url), RpcResponse.class);
//        logger.debug("resp : {}", resp);
//    }
//
//    @Test
//    public void get() {
//        logger.debug("非rest的get请求");
//        Map<String, Object> params = Maps.newHashMap();
//        params.put("user_id", 456L);
//        RpcResponse resp2 = restTemplate.getForObject("http://localhost:9892/paymentserver/get", RpcResponse.class, params);
//
//        logger.debug("resp2 : {}", resp2);
//    }
//
//    @Test
//    public void post() {
//        logger.debug("rest template post 请求");
//        UserRequestDto userRequestDTO = new UserRequestDto();
//
//    }
//
//    @Resource
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void test1() throws URISyntaxException, InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
////        Map<String, Object> params = Maps.newHashMap();
////        params.put("session_id", "asdasdasdasdasd");
////        params.put("user_id", 1000L);
////        params.put("version", "V2");
////        params.put("name", "luweihong");
////        params.put("client_type", 1);
////        params.put("mobile", "15618040084");
////        params.put("session_name", "aslkjdfhalsdghi");
////
////        AuthorizationRequestDTO authorizationRequestDTO = new AuthorizationRequestDTO();
////        authorizationRequestDTO.setClientType(1);
////        authorizationRequestDTO.setSessionId("1561804008asdasd4");
////        authorizationRequestDTO.setSessionName("asdjkhadsgfpgf");
////        authorizationRequestDTO.setVersion("V2");
////        logger.debug("rest的get请求,必须要用URI,如果使用String类型的url,其中的符号会导致请求不能解析");
////        String url = "http://ahadev:9893/passportserver/authorization/grant";
////        HttpEntity<AuthorizationRequestDTO> entity = new HttpEntity<>(authorizationRequestDTO);
////
////        RpcResponse resp = restTemplate.postForObject(new URI(url), entity, RpcResponse.class);
////        logger.debug("resp : {}", resp);
////        AccessTokenVO accessTokenVO = new AccessTokenVO();
////        BeanUtil.transMap2Bean((Map<String, Object>) resp.getData(), accessTokenVO, true);
////
////        logger.debug("accessTokenVO : {}", accessTokenVO);
////        ResponseEntity.status(1);
////        BeanUtil.convertMap(AccessTokenVO.class, (Map) resp.getData(),Boolean.TRUE);
////        AccessTokenVO accessTokenVO = resp.getData();
////        logger.debug("accessTokenVO : {}", accessTokenVO);
//
////        AccessTokenVO accessTokenVO = JSON.parseObject(resp,AccessTokenVO.class);
//
////        logger.debug("accessTokenVO : {}", accessTokenVO);
//    }
//
//    public static void main(String[] args) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
//        Map<String, Object> m = new HashMap<>();
//        m.put("access_token", "asdasd");
//        m.put("refresh_token", null);
//        m.put("expired_at", "2050-01-01");
//
////        AccessTokenVO accessTokenVO = new AccessTokenVO();
////        BeanUtil.transMap2Bean(m, accessTokenVO, true);
////
////        System.out.println("accessTokenVO : " + accessTokenVO);
//    }
//
//}
