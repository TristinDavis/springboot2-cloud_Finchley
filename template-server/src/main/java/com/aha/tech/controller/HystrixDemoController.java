package com.aha.tech.controller;

import com.aha.tech.commons.response.RpcResponse;
import com.aha.tech.controller.resource.AccountResource;
import com.aha.tech.facade.constants.AccountServerConstants;
import com.aha.tech.facade.model.dto.AccountQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: luweihong
 * @Date: 2019/1/2
 */
@RestController
public class HystrixDemoController {

    @Autowired(required = false)
    private AccountResource accountResource;

    @RequestMapping(value = "/is_accounted", method = RequestMethod.GET)
    public RpcResponse isAccounted() {
        Integer eventType = 1;
        String receiptNo = "asdasdasd";

        RpcResponse rpcResponse = accountResource.accounted(eventType, receiptNo);
        return rpcResponse;
    }

    @RequestMapping(value = "/wallet_info", method = RequestMethod.GET)
    public RpcResponse walletInfo() {
        AccountQueryDto accountQueryDto = new AccountQueryDto();
        accountQueryDto.setUserId(82L);
        accountQueryDto.setWalletType(AccountServerConstants.IOS_WALLET);
        RpcResponse rpcResponse = accountResource.wallet(accountQueryDto);
        return rpcResponse;
    }
}
