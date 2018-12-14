package cn.net.zhipeng.serurity.core.social.qq.connect;

import cn.net.zhipeng.serurity.core.social.qq.api.QQ;
import cn.net.zhipeng.serurity.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * 服务提供商的抽象类
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;
    // 获取授权码的请求地址
    public static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    // 根据授权码获取Access Token的请求地址
    public static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId, String appSecret) {
        // 父类的构造函数设置的OAuth2Operations，封装了OAuth2获取到accessToken的流程
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    /**
     * 有了令牌之后即可根据令牌获取用户信息
     * @param accessToken 获取用户信息的令牌
     * @return QQ用户信息
     */
    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
