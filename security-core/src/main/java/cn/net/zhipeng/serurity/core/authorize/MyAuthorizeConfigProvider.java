package cn.net.zhipeng.serurity.core.authorize;

import cn.net.zhipeng.serurity.core.properties.SecurityConstants;
import cn.net.zhipeng.serurity.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
@Order(Integer.MIN_VALUE)
public class MyAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    SecurityProperties securityProperties;

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                SecurityConstants.DEFAULT_SESSION_INVALID_URL,
                securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                securityProperties.getBrowser().getLoginPage(),
                securityProperties.getBrowser().getSignUpUrl(),
                securityProperties.getBrowser().getSignOutUrl()).permitAll();
        return false;
    }
}
