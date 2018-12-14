package cn.net.zhipeng.serurity.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();
}
