package com.aha.tech.controller.resource.fallback;

import com.aha.tech.commons.constants.ResponseConstants;
import com.aha.tech.commons.response.RpcResponse;
import com.aha.tech.commons.response.RpcResponsePage;
import com.aha.tech.controller.resource.AccountResource;
import com.aha.tech.facade.model.dto.AccountLogsRequestDto;
import com.aha.tech.facade.model.dto.AccountQueryDto;
import com.aha.tech.facade.model.dto.ChangeRequestDto;
import com.aha.tech.facade.model.vo.AccountLogVo;
import com.aha.tech.facade.model.vo.BatchChangeResultVo;
import com.aha.tech.facade.model.vo.UserAccountVo;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: luweihong
 * @Date: 2019/1/9
 */
@Component
public class AccountFallbackFactory implements FallbackFactory<AccountResource> {

    private static Logger logger = LoggerFactory.getLogger(AccountFallbackFactory.class);

    /**
     * 根据Throwable 灵活返回
     * @param cause 具体的异常
     * @return
     */
    @Override
    public AccountResource create(Throwable cause) {

        return new AccountResource() {
            @Override
            public RpcResponse<UserAccountVo> wallet(AccountQueryDto accountQueryDto) {
                logger.error("查询账户信息失败,进入降级 : {}", cause);
                return RpcResponse.defaultHystrixFallbackResponse();
            }

            @Override
            public RpcResponse<List<UserAccountVo>> wallets(List<AccountQueryDto> accountQueryDtoList) {
                logger.error("批量查询账户信息失败,进入降级 : {}", cause);
                return RpcResponse.defaultHystrixFallbackResponse();
            }

            @Override
            public RpcResponse change(ChangeRequestDto changeRequestDto) {
                logger.error("账户变更失败,进入降级 : {}", cause);
                return RpcResponse.defaultHystrixFallbackResponse();
            }

            @Override
            public RpcResponse<BatchChangeResultVo> batchChange(List<ChangeRequestDto> changeRequestDTOList) {
                logger.error("批量更新操作失败,进入降级 : {}", cause);
                return RpcResponse.defaultHystrixFallbackResponse();
            }

            @Override
            public RpcResponse accounted(Integer eventType, String receiptNo) {
                logger.error("查询票据是否入账失败,进入降级 : {}", cause);
                return RpcResponse.defaultHystrixFallbackResponse();
            }

            @Override
            public RpcResponsePage<List<AccountLogVo>> logs(AccountLogsRequestDto accountLogsRequestDto) {
                logger.error("查询账户流水记录失败,进入降级 : {}", cause);
                RpcResponsePage rpcResponsePage = new RpcResponsePage();
                rpcResponsePage.setCode(ResponseConstants.FAILURE);
                rpcResponsePage.setMessage(ResponseConstants.FAILURE_MSG);
                return rpcResponsePage;
            }
        };
    }
}
