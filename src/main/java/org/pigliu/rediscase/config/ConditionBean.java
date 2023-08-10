package org.pigliu.rediscase.config;

import org.pigliu.rediscase.annotation.ConditionalOnPropertyNotEmpty;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/8/7
 */
//@ConditionalOnPropertyNotEmpty("${php.xjc.url}")
@FeignClient(name = "remotePhpTheaterService", url = "${php.xjc.url}")
public interface ConditionBean {




}
