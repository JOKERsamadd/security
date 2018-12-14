package cn.net.zhipeng.serurity.core.social.qq.connect;

import cn.net.zhipeng.serurity.core.social.qq.api.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 该类负责构建含有用户信息的Connection实例
 * QQServiceProvider负责获取用户信息
 * QQAdapter负责把服务提供商的用户信息转换成Connection标准用户信息
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }

}
