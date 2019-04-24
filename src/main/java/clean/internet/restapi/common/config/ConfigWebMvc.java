package clean.internet.restapi.common.config;

import clean.internet.restapi.common.intercepter.InterceptorApiToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigWebMvc implements WebMvcConfigurer {

    @Bean
    public InterceptorApiToken interceptorApiToken() {
        return new InterceptorApiToken();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorApiToken())
                .addPathPatterns("/**");
    }
}