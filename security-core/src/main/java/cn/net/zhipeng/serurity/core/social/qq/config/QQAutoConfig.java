package cn.net.zhipeng.serurity.core.social.qq.config;

import cn.net.zhipeng.serurity.core.properties.QQProperties;
import cn.net.zhipeng.serurity.core.properties.SecurityProperties;
import cn.net.zhipeng.serurity.core.social.MyConnectView;
import cn.net.zhipeng.serurity.core.social.qq.connect.QQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.web.servlet.View;

@Configuration
@ConditionalOnProperty(prefix = "zhipeng.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {

    private final SecurityProperties securityProperties;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public QQAutoConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        logger.info("第三方认证自动配置开启：创建连接工厂ing...");
        QQProperties qq = securityProperties.getSocial().getQq();
        connectionFactoryConfigurer.addConnectionFactory(new QQConnectionFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret()));
    }

    @Bean({"connect/qqConnect", "connect/qqConnected"})
    @ConditionalOnMissingBean(name = "qqConnectedView")
    public View qqConnectedView() {
        return new MyConnectView();
    }
}
