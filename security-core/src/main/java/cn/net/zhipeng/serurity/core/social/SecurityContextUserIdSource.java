package cn.net.zhipeng.serurity.core.social;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.UserIdSource;
import org.springframework.util.Assert;

public class SecurityContextUserIdSource implements UserIdSource {
    @Override
    public String getUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Assert.state(authentication != null,
                "Unable to get a " + "ConnectionRepository: no user signed in");
        return authentication.getName();
    }
}
