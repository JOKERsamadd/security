package cn.net.zhipeng.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 处理并发登录失败请求
 */
public class MyExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public MyExpiredSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent eventØ) throws IOException, ServletException {
        eventØ.getResponse().setContentType("application/json;charset=UTF-8");
        eventØ.getResponse().getWriter().write("并发登录");
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
