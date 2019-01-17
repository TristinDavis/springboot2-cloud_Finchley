package com.aha.tech.controller;

import com.aha.tech.commons.response.RpcResponse;
import com.aha.tech.templateserver.facade.api.TemplateFacade;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: luweihong
 * @Date: 2019/1/14
 */
@RestController
public class TemplateContorller implements TemplateFacade {

    private static final Logger logger = LoggerFactory.getLogger(CommandController.class);

    @ApiOperation("打招呼,测试templateserver接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true)
    })
    @Override
    public RpcResponse<String> helloWorld(String name) {
        logger.info("Received hello world request,name is {}", name);
        return new RpcResponse<>(String.format("hello , ====> %s", name));
    }
}
