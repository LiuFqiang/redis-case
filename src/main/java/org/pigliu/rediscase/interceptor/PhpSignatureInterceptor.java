package org.pigliu.rediscase.interceptor;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.internal.http2.ErrorCode;
import org.apache.tomcat.util.http.RequestUtil;
import org.pigliu.rediscase.annotation.Signature;
import org.pigliu.rediscase.filter.RequestBodyCopyFilter;
import org.pigliu.rediscase.utils.SignUtils;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class PhpSignatureInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Signature signatureAnnotation = handlerMethod.getMethodAnnotation(Signature.class);
        if (null == signatureAnnotation) {
            return true;
        }

        String sign = request.getHeader("sign");
        if (StrUtil.isBlank(sign)) {
            log.error("sign is empty, check sign failed");
            writeErrorResponseCode(response);
            return false;
        }
        if (ServletUtil.isGetMethod(request)) {
            String queryString = request.getQueryString();
            Map<String, String> paramMap = HttpUtil.decodeParamMap(queryString, StandardCharsets.UTF_8);
            return checkSignature(sign, paramMap, response);
        }
        if (ServletUtil.isPostMethod(request)) {
            String contentType = request.getContentType();
            Map<String, String> parameterMap;
            if (StrUtil.equals(contentType, MediaType.APPLICATION_JSON_VALUE)) {
                RequestBodyCopyFilter.RequestBodyWrapper wrapper = (RequestBodyCopyFilter.RequestBodyWrapper) request;
                String body = wrapper.getBody();
                if (log.isDebugEnabled()) {
                    log.info("post request body: {}", body);
                }
                parameterMap = SignUtils.signParseMap(body);
            } else {
                parameterMap = ServletUtil.getParamMap(request);
            }
            return checkSignature(sign, parameterMap, response);

        }
        return true;
    }

    private static final String MD5_SALT = "gLvzYnRhwCFKG_WP";

    private static final MD5 getMd5() {
        return new MD5(MD5_SALT.getBytes(StandardCharsets.UTF_8));
    }

    private boolean checkSignature(String signStr, Map<String, String> paramMap, HttpServletResponse response) {
        return checkSignature(signStr, SignUtils.mapConvertString(paramMap), response);
    }

    private boolean checkSignature(String signStr, String params, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.info("sign is {}, params is {}", signStr, params);
        }
        String str = getMd5().digestHex(params, StandardCharsets.UTF_8);
        if (Objects.equals(signStr, str)) {
            return true;
        }
        writeErrorResponseCode(response);
        return false;
    }

    private void writeErrorResponseCode(HttpServletResponse response) {
        response.setStatus(403);
    }
}
