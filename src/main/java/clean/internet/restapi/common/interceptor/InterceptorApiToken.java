package clean.internet.restapi.common.interceptor;

import ch.qos.logback.classic.Logger;
import clean.internet.restapi.common.exception.UnAuthorizedException;
import clean.internet.restapi.repository.ApiTokenRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class InterceptorApiToken extends HandlerInterceptorAdapter {

    @Autowired
    ApiTokenRepository apiTokenRepository;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(InterceptorApiToken.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestApiToken = request.getHeader("api-token");
        logger.info("request api-token", requestApiToken);

        if (Optional.ofNullable(apiTokenRepository.getApiTokenDataByApiToken(requestApiToken)).isPresent()) {
            return true;
        } else {
            throw new UnAuthorizedException();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}