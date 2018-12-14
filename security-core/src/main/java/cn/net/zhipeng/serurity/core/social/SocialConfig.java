package cn.net.zhipeng.serurity.core.social;

import cn.net.zhipeng.serurity.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    private final DataSource dataSource;

    private final SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    public SocialConfig(DataSource dataSource, SecurityProperties securityProperties) {
        this.dataSource = dataSource;
        this.securityProperties = securityProperties;
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new SecurityContextUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        MyJdbcUsersConnectionRepository repository = new MyJdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        if (!StringUtils.isEmpty(securityProperties.getSocial().getTablePrefix())) {
            repository.setTablePrefix(securityProperties.getSocial().getTablePrefix());
        }
        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    @Bean
    public UsersConnectionRepository usersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return getUsersConnectionRepository(connectionFactoryLocator);
    }

    @Bean
    public SpringSocialConfigurer zhipengSocialConfigurer() {
        ZhiPengSpringSocialConfig socialConfig = new ZhiPengSpringSocialConfig(securityProperties.getSocial().getFilterProcessesUrl());
        socialConfig.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        return socialConfig;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }
}
