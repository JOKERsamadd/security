package cn.net.zhipeng.serurity.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrowserProperties {

    private SessionProperties session = new SessionProperties();

    private String signUpUrl = SecurityConstants.DEFAULT_SIGN_UP_PAGE_URL;

    private String signOutUrl = SecurityConstants.DEFAULT_SIGN_OUT_PAGE_URL;

    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    private String invalidSessionUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;

    private LoginType loginType = LoginType.JSON ;

    private int rememberMeSeconds = 60*60*24*7*2;

    private boolean createTableOnStartup = false;

}
