package org.pigliu.rediscase.config;

import org.pigliu.rediscase.filter.RequestBodyCopyFilter;
import org.pigliu.rediscase.interceptor.PhpSignatureInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liufuqiang
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		PhpSignatureInterceptor phpSignatureInterceptor = new PhpSignatureInterceptor();
		registry.addInterceptor(phpSignatureInterceptor).addPathPatterns("/test/**");
	}


	@Bean
	public RequestBodyCopyFilter getBodyWrapperFilter() {
		return new RequestBodyCopyFilter();
	}

	@Bean("bodyCopyFilter")
	public FilterRegistrationBean<RequestBodyCopyFilter> bodyCopyFilter(RequestBodyCopyFilter bodyWrapperFilter) {
		FilterRegistrationBean<RequestBodyCopyFilter> registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(bodyWrapperFilter);
		registrationBean.addUrlPatterns("/test/*");
		registrationBean.setOrder(1);
		registrationBean.setAsyncSupported(true);
		return registrationBean;
	}

}
