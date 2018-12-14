package cn.net.zhipeng.serurity.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeixinProperties  {

    /**
     * Application id.
     */
    private String appId;

    /**
     * Application secret.
     */
    private String appSecret;

    private String providerId = "weixin";

}
