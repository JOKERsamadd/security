package cn.net.zhipeng.serurity.core.validate.code.sms;

import cn.net.zhipeng.serurity.core.validate.code.ValidateCode;
import cn.net.zhipeng.serurity.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private SmsValidateCodeSender smsValidateCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        smsValidateCodeSender.send(mobile, validateCode.getCode());
    }
}
