package org.pigliu.rediscase.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * post请求过滤器
 * @author liufuqiang
 */
public class RequestBodyCopyFilter implements Filter {
    @Override
    public void doFilter (ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(servletRequest instanceof HttpServletRequest) {
            if (ServletUtil.isPostMethod((HttpServletRequest) servletRequest)) {
                requestWrapper = new RequestBodyWrapper((HttpServletRequest) servletRequest);
            }
        }
        if(requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    public static class RequestBodyWrapper extends HttpServletRequestWrapper {

        private @Nullable byte[] body;

        public RequestBodyWrapper(HttpServletRequest request) throws IOException {
            super(request);
            if (ServletUtil.isMultipart(request)) {
                return;
            }
            if (!StrUtil.equals(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
                return;
            }
            if (ServletUtil.isPostMethod(request)) {
                StringBuilder sb = new StringBuilder();
                String line;
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String body = sb.toString();
                this.body = body.getBytes(StandardCharsets.UTF_8);
                System.out.println(1);
            }
        }

        public String getBody() {
            return new String(this.body , StandardCharsets.UTF_8) ;
        }

        @Override
        public ServletInputStream getInputStream() {
            final ByteArrayInputStream bais = new ByteArrayInputStream(body);
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }
                @Override
                public boolean isReady() {
                    return false;
                }
                @Override
                public void setReadListener(ReadListener readListener) {
                }
                @Override
                public int read(){
                    return bais.read();
                }
            };
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }
    }
}
