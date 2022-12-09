package org.pigliu.rediscase.interceptor;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.dtflys.forest.config.ForestConfiguration;
import com.dtflys.forest.exceptions.ForestRuntimeException;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.Interceptor;
import com.dtflys.forest.reflection.ForestMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pigliu.rediscase.config.AIDrawConfig;
import org.pigliu.rediscase.dto.R;
import org.pigliu.rediscase.service.DrawService;
import org.pigliu.rediscase.service.client.AIDrawRequestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 画图请求拦截器
 * @author liufuqiang
 */
@Slf4j
@Component
public class DrawRequestInterceptor<T> implements Interceptor<T> {

    @Resource
    @Lazy
    private DrawService drawService;

    @Override
    public void onInvokeMethod(ForestRequest request, ForestMethod method, Object[] args) {
        if (!StrUtil.contains(request.getUrl(), "/oauth/token")) {
            request.addBody("access_token", drawService.getToken());
        }
    }

    @Override
    public boolean beforeExecute(ForestRequest request) {
        return true;
    }

    @Override
    public void afterExecute(ForestRequest request, ForestResponse response) {
        System.out.println("s");
    }

    @Override
    public void onSuccess(T data, ForestRequest req, ForestResponse res) {
        ForestConfiguration configuration = req.getConfiguration();
        if (!configuration.isLogEnabled()) {
            log.info("request success url:{}, response:{}", req.getUrl(), res.getContent());
        }
    }

    @Override
    public void onError(ForestRuntimeException ex, ForestRequest req, ForestResponse res) {
        ForestConfiguration configuration = req.getConfiguration();
        if (!configuration.isLogEnabled()) {
            log.info("request url:{}, content-type:{}, params:{}, body:{}", req.getUrl(), req.getDataType(),
                    req.getQueryString(), JSONUtil.toJsonStr(req.getBody()));
        }
        log.error("request error", ex);
    }


}
