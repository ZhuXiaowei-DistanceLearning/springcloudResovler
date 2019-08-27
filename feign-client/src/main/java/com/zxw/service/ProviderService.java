package com.zxw.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author zxw
 * @date 2019/8/23 19:40
 */
@FeignClient(name = "sc-provider-service")
public interface ProviderService {
    @RequestMapping(value = "/getDashboard")
    public List<String> getProviderData();
}
