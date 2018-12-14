package cn.net.zhipeng.browser.authentication;

import cn.net.zhipeng.serurity.core.support.SimpleResponse;
import cn.net.zhipeng.serurity.core.properties.LoginType;
import cn.net.zhipeng.serurity.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectMapper objectMapper;
    private final SecurityProperties securityProperties;

    public MyAuthenticationFailureHandler(ObjectMapper objectMapper, SecurityProperties securityProperties) {
        this.objectMapper = objectMapper;
        this.securityProperties = securityProperties;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        logger.info("登录失败");

        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
        } else {
            super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
        }

    }
}
