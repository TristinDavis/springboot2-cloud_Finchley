package com.aha.tech.templateserver.facade.api;

import com.aha.tech.commons.response.RpcResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: luweihong
 * @Date: 2018-11-21
 *  业务名 + facade结尾命名
 *
 */
public interface TemplateFacade {

    @RequestMapping(path = "/hello_world", method = RequestMethod.GET)
    RpcResponse<String> helloWorld(@RequestParam(value = "name") String name);

}
