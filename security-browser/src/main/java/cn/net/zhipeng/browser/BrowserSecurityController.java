package cn.net.zhipeng.browser;

import cn.net.zhipeng.serurity.core.support.SimpleResponse;
import cn.net.zhipeng.browser.support.SocialUserInfo;
import cn.net.zhipeng.serurity.core.properties.SecurityConstants;
import cn.net.zhipeng.serurity.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {

    // 跳转请求被保存在这里
    private RequestCache requestCache = new HttpSessionRequestCache();

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    /**
     * 当需要身份认证时，跳转到这里
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 拿到引发跳转的请求
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是：" + redirectUrl);
            if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html") ||
                    StringUtils.endsWithIgnoreCase(redirectUrl, ".jsp")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问的服务需要身份认证，请引导用户登录");
    }

    @GetMapping("/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo socialUserInfo = new SocialUserInfo();
        Connection<?> connectionFromSession = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        socialUserInfo.setProviderId(connectionFromSession.getKey().getProviderId());
        socialUserInfo.setProviderUserId(connectionFromSession.getKey().getProviderUserId());
        socialUserInfo.setNickName(connectionFromSession.getDisplayName());
        socialUserInfo.setHeadImg(connectionFromSession.getImageUrl());
        return socialUserInfo;
    }

    @GetMapping(SecurityConstants.DEFAULT_SESSION_INVALID_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse sessionInvalid() {
        return new SimpleResponse("session失效");
    }
}
