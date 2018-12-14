package cn.net.zhipeng.browser.logout;

import cn.net.zhipeng.serurity.core.support.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class MyLogOutSuccessHandler implements LogoutSuccessHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final String signOutUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public MyLogOutSuccessHandler(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Enumeration<String> attributeNames = request.getSession().getAttributeNames();
        if (attributeNames.hasMoreElements()) {
            String s = attributeNames.nextElement();
            System.out.println(s + ": " + request.getSession().getAttribute(s));
        }
        logger.info("退出成功");
        if (StringUtils.isNotBlank(signOutUrl)) {
            response.sendRedirect(signOutUrl);
        } else {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        }
    }
}
