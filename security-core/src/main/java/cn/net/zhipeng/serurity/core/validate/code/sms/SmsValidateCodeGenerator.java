package cn.net.zhipeng.serurity.core.validate.code.sms;

import cn.net.zhipeng.serurity.core.properties.SecurityProperties;
import cn.net.zhipeng.serurity.core.validate.code.ValidateCode;
import cn.net.zhipeng.serurity.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {
    private final SecurityProperties securityProperties;

    public SmsValidateCodeGenerator(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
    }
}
