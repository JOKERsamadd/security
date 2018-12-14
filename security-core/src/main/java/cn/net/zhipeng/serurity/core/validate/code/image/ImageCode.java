package cn.net.zhipeng.serurity.core.validate.code.image;

import cn.net.zhipeng.serurity.core.validate.code.ValidateCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageCode extends ValidateCode {

    private static final long serialVersionUID = -4358752597296354336L;
    private BufferedImage bufferedImage;

    public ImageCode(BufferedImage bufferedImage, String code, int expireIn) {
        super(code, expireIn);
        this.bufferedImage = bufferedImage;
    }
}
