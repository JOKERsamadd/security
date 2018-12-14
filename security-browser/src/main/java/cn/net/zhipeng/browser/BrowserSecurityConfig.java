package cn.net.zhipeng.browser;

import cn.net.zhipeng.browser.logout.MyLogOutSuccessHandler;
import cn.net.zhipeng.browser.session.MyExpiredSessionStrategy;
import cn.net.zhipeng.serurity.core.authentication.AbstractChannelSecurityConfig;
import cn.net.zhipeng.serurity.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import cn.net.zhipeng.serurity.core.authorize.AuthorizeConfigManager;
import cn.net.zhipeng.serurity.core.properties.SecurityConstants;
import cn.net.zhipeng.serurity.core.properties.SecurityProperties;
import cn.net.zhipeng.serurity.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService myUserDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer zhipengSocialConfigurer;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        applyPasswordAuthenticationConfig(http);

        http.apply(validateCodeSecurityConfig)
                    .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                .apply(zhipengSocialConfigurer)
                    .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    .userDetailsService(myUserDetailsService)
                    .and()
                .sessionManagement()
                    .invalidSessionUrl(securityProperties.getBrowser().getInvalidSessionUrl())
                    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                    .expiredSessionStrategy(sessionInformationExpiredStrategy)
                    .and()
                    .and()
                .logout()
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .deleteCookies("JSESSIONID")
                    .and()
                /*.authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        SecurityConstants.DEFAULT_SESSION_INVALID_URL,
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getBrowser().getSignUpUrl(),
                        "/user/regist"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()*/
                .csrf().disable();

        authorizeConfigManager.config(http.authorizeRequests());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        if (securityProperties.getBrowser().isCreateTableOnStartup()) {
            tokenRepository.setCreateTableOnStartup(true);
        }
        return tokenRepository;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new MyLogOutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
    }
}
