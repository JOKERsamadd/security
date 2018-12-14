package cn.net.zhipeng.serurity.core.validate.code.sms;

public interface SmsValidateCodeSender {

    void send(String mobile, String code);
}
