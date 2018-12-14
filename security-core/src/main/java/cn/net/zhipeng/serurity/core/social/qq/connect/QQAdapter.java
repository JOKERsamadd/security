package cn.net.zhipeng.serurity.core.social.qq.connect;

import cn.net.zhipeng.serurity.core.social.qq.api.QQ;
import cn.net.zhipeng.serurity.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 负责把服务提供商的用户信息转换成Connection标准用户信息
 */
public class QQAdapter implements ApiAdapter<QQ> {
    @Override
    public boolean test(QQ api) {
        return true;
    }

    /**
     * 适配逻辑
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());
    }

    /**
     * 用户主页，qq也没有
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    /**
     * 更新动态啥的，qq没有
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(QQ api, String message) {
        // do nothing
    }
}
