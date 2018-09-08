package com.vankata.cardealer.config;

import com.vankata.cardealer.interceptor.AddCustomerInterceptor;
import com.vankata.cardealer.interceptor.EditCustomerInterceptor;
import com.vankata.cardealer.interceptor.LoggingInterceptor;
import com.vankata.cardealer.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AddCustomerInterceptor addCustomerInterceptor;
    private final EditCustomerInterceptor editCustomerInterceptor;
    private final SessionInterceptor sessionInterceptor;
    private final LoggingInterceptor loggingInterceptor;

    @Autowired
    public WebMvcConfig(AddCustomerInterceptor addCustomerInterceptor,
                        EditCustomerInterceptor editCustomerInterceptor,
                        SessionInterceptor sessionInterceptor,
                        LoggingInterceptor loggingInterceptor) {
        this.addCustomerInterceptor = addCustomerInterceptor;
        this.editCustomerInterceptor = editCustomerInterceptor;
        this.sessionInterceptor = sessionInterceptor;
        this.loggingInterceptor = loggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.addCustomerInterceptor).addPathPatterns("/customers/add");
        registry.addInterceptor(this.editCustomerInterceptor).addPathPatterns("/customers/*/edit");
        registry.addInterceptor(this.sessionInterceptor);
        registry.addInterceptor(this.loggingInterceptor);
    }
}
