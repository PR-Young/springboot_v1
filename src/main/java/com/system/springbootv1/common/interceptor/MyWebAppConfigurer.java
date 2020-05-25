package com.system.springbootv1.common.interceptor;

import com.system.springbootv1.common.config.BaseConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;

/**
 * 拦截器
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurationSupport {

    @Resource
    BaseConfig config;

    /**
     * 解决跨域问题
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
		/*
			registry.addMapping("/**")
			// 设置允许跨域请求的域名
			.allowedOrigins("*")
			// 是否允许证书
			.allowCredentials(true)
			// 设置允许的方法
			.allowedMethods("GET", "POST", "DELETE", "PUT")
			// 设置允许的header属性
			.allowedHeaders("*")
			// 跨域允许时间
			.maxAge(3600);
			super.addCorsMappings(registry);
		*/
    }

    /**
     * 添加拦截器
     **/
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        if (!config.isSeparate()) {
            registry.addInterceptor(new MyInterceptor());
        }
        super.addInterceptors(registry);
    }

    /**
     * 这里配置视图解析器
     **/
    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
    }

    /**
     * 配置内容裁决的一些选项
     **/
    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        super.configureContentNegotiation(configurer);
    }

    /**
     * 视图跳转控制器
     **/
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {

        super.addViewControllers(registry);
    }


    /**
     * 静态资源处理
     **/
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

    }

    /**
     * 默认静态资源处理器
     **/

    protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


}
