package cn.net.zhipeng.serurity.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialProperties {

    private String filterProcessesUrl = "/auth";

    private QQProperties qq = new QQProperties();

    private String tablePrefix;

    private WeixinProperties weixin = new WeixinProperties();
}
