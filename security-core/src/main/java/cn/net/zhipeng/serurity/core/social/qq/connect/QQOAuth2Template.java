package cn.net.zhipeng.serurity.core.social.qq.connect;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Objects;

/**
 * 获取令牌
 */
public class QQOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        // 设置参数必须携带client_id，client_secret
        setUseParametersForClientAuthentication(true);
    }
    /**
     * qq返回的contentType是html/text，添加相应的HttpMessageConverter来处理。
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        // 发送获取令牌的请求
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        logger.info("获取AccessToken的响应：" + responseStr);
        // 父类打算响应回来的是map格式，然而不是，自己构建
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
        String accessToken = StringUtils.substringAfter(Objects.requireNonNull(items)[0], "=");
        Long expiresIn = Long.valueOf(StringUtils.substringAfter(items[1], "="));
        String refreshToken = StringUtils.substringAfter(items[2], "=");
        return new AccessGrant(accessToken,null, refreshToken, expiresIn);
    }
}
