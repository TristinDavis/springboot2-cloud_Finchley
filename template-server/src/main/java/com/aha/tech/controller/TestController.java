//package com.aha.tech.controller;
//
//import com.aha.tech.base.commons.response.RpcResponse;
//import com.aha.tech.templateserver.commons.response.RpcResponse;
//import com.aha.tech.model.entity.UserEntity;
//import com.aha.tech.repository.dao.readwrite.UserMapper;
//import com.aha.tech.templateserver.facade.model.dto.UserRequestDto;
//import com.google.common.collect.Maps;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.lang3.RandomUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.websocket.server.PathParam;
//import java.sql.Timestamp;
//import java.time.Instant;
//import java.util.Date;
//import java.util.Map;
//import java.util.stream.IntStream;
//
////import com.aha.tech.message.producer.KafkaProducer;
//
///**
// * @Author: luweihong
// * @Date: 2018/7/25
// *
// * 用作单元测试,可以删除
// */
//@RestController
//public class TestController {
//
//    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);
//
//    /**
//     * 测试swagger get api 是否正确生成
//     */
//    @ApiOperation("测试swagger和fastjson config")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "event_type", value = "1 充值 2 退款 3 购买 4 分销系统 5 其他系统", required = true, paramType = "path", dataType = "Integer"),
//            @ApiImplicitParam(name = "receipt_no", value = "凭证号", required = true, paramType = "path", dataType = "String")
//    })
//    @GetMapping(value = "/test/swagger/get")
//    public RpcResponse<Object> get(@RequestParam(value = "event_type") Integer eventType, @PathVariable(value = "receipt_no") String receiptNo){
//        LOG.info("这里是日志输出!");
//        Map<String,Object> resp = Maps.newHashMap();
//        resp.put("date",new Date());
//        resp.put("timestamp",new Timestamp(System.currentTimeMillis()));
//        resp.put("instant", Instant.now());
//        return new RpcResponse(resp);
//    }
//
//    /**
//     * 测试 rest的get请求是否能正确接收参数
//     * 对应 restTemplateTest.java 的 get test
//     * @param userId
//     * @return
//     */
//    @GetMapping(value = "/rest/get/{user_id}")
//    public RpcResponse<Long> restGet(@PathVariable(value = "user_id") Long userId) throws InterruptedException {
//        return new RpcResponse<>(userId);
//    }
//
//    /**
//     * 非rest get 请求 使用问号
//     * @param userId
//     * @return
//     */
//    @GetMapping(value = "/get")
//    public RpcResponse<Long> get(@PathParam(value = "user_id") Long userId) {
//        return new RpcResponse<>(userId);
//    }
//
//    /**
//     * rest post 请求
//     * @param userRequestDTO
//     * @return
//     */
//    @PostMapping(value = "rest/post")
//    public RpcResponse<UserRequestDto> restPost(@RequestBody UserRequestDto userRequestDTO) {
//        return new RpcResponse<>(userRequestDTO);
//    }
//
//    @Resource
//    private UserMapper userMapper;
//
//    @GetMapping(value = "sql_session")
//    @Transactional(value = "readwriteTransactionManager")
//    public RpcResponse testSqlSession() {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUserId(RandomUtils.nextInt() + 9999L);
//        userEntity.setName(String.valueOf(RandomUtils.nextLong()));
//        IntStream.range(0, 10).forEach(o -> userMapper.insertSelective(userEntity));
//        return new RpcResponse(userMapper.selectByPrimaryKey(1L));
//    }
//
//
//}
