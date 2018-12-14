package cn.net.zhipeng.serurity.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QQProperties {

    /**
     * Application id.
     */
    private String appId;

    /**
     * Application secret.
     */
    private String appSecret;

    private String providerId = "qq";
}
