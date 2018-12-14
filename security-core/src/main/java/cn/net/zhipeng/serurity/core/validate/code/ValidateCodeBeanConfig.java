package cn.net.zhipeng.serurity.core.validate.code;

import cn.net.zhipeng.serurity.core.properties.SecurityProperties;
import cn.net.zhipeng.serurity.core.validate.code.image.ImageValidateCodeGenerator;
import cn.net.zhipeng.serurity.core.validate.code.sms.DefaultSmsValidateCodeSender;
import cn.net.zhipeng.serurity.core.validate.code.sms.SmsValidateCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

    private final SecurityProperties securityProperties;

    public ValidateCodeBeanConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean("imageValidateCodeGenerator")
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        return new ImageValidateCodeGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(SmsValidateCodeSender.class)
    public SmsValidateCodeSender smsValidateCodeSender() {
        return new DefaultSmsValidateCodeSender();
    }
}
