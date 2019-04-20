package clean.internet.restapi.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class ConfigHttpHeaders {

    @Bean
    HttpHeaders httpHeaders() {
        return new HttpHeaders();
    }
}