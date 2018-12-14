package cn.net.zhipeng.serurity.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageCodeProperties extends SmsCodeProperties {

    private int width = 100;
    private int height = 40;

    public ImageCodeProperties() {
        super.setLength(4);
    }
}
