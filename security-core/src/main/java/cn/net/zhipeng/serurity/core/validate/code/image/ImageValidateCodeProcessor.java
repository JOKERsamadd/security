package cn.net.zhipeng.serurity.core.validate.code.image;

import cn.net.zhipeng.serurity.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

@Component("imageValidateCodeProcessor")
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws IOException {
        ImageIO.write(validateCode.getBufferedImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
