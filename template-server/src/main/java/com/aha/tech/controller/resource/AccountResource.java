package com.aha.tech.controller.resource;

import com.aha.tech.controller.resource.fallback.AccountFallbackFactory;
import com.aha.tech.facade.api.AccountFacade;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: luweihong
 * @Date: 2019/1/2
 */
@FeignClient(name = "accountserver", path = "aha-account", fallbackFactory = AccountFallbackFactory.class)
public interface AccountResource extends AccountFacade {
}