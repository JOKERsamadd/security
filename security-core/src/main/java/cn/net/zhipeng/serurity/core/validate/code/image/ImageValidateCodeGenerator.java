package cn.net.zhipeng.serurity.core.validate.code.image;

import cn.net.zhipeng.serurity.core.properties.ImageCodeProperties;
import cn.net.zhipeng.serurity.core.properties.SecurityProperties;
import cn.net.zhipeng.serurity.core.validate.code.ValidateCode;
import cn.net.zhipeng.serurity.core.validate.code.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageValidateCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request){
        ImageCodeProperties imageCodeProperties = securityProperties.getCode().getImage();
        // 验证码的宽高可以在请求时配置，默认为应用级配置
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", imageCodeProperties.getHeight());
        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", imageCodeProperties.getWidth());

        String code = VerifyCodeUtils.generateVerifyCode(imageCodeProperties.getLength());
        BufferedImage image = null;
        try {
            image = VerifyCodeUtils.getImage(width, height, code);
        } catch (IOException e) {
            throw new RuntimeException("获取图片失败", e);
        }
        return new ImageCode(image, code, imageCodeProperties.getExpireIn());
    }
}
