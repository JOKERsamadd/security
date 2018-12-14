package cn.net.zhipeng.serurity.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class ZhiPengSpringSocialConfig extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public ZhiPengSpringSocialConfig(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter socialAuthenticationFilter = (SocialAuthenticationFilter) super.postProcess(object);
        socialAuthenticationFilter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) socialAuthenticationFilter;
    }
}
