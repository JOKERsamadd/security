package cn.net.zhipeng.serurity.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码校验异常
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -5796330850689882403L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
