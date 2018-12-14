package cn.net.zhipeng.serurity.core.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("connect/status")
public class MyConnectionStatusView extends AbstractView{

    private final ObjectMapper objectMapper;

    public MyConnectionStatusView(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) map.get("connectionMap");
        Map<String, Boolean> result = new HashMap<>();
        connections.forEach((s, connections1) -> result.put(s, CollectionUtils.isNotEmpty(connections.get(s))));
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
