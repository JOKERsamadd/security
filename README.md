## 登录安全模块

### 功能介绍

这是一个帮你实现登录功能的安全模块。基础框架采用Spring Boot + Spring Security，将此模块集成在你的项目中，只需要简单的配置，就可实现表单登录、图片验证码登录、社交账户登录（QQ）以及手机验证码登录以及Session管理等功能。

### 使用方法

建议您的Spring Boot版本在2.x以下，可参考父项目的pom文件。

##### 创建一个Spring Boot工程，引入依赖。

```xml
	<dependency>
            <groupId>cn.net.zhipeng</groupId>
            <artifactId>security-browser</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```
请确保Spring可以扫描到cn.net.zhipeng下的所有文件。

##### 编写配置文件

- 浏览器环境配置项，前缀：zhipeng.security.browser， 参见BrowserProperties

```
signInPage = /demo-signIn.html
signInResponseType = REDIRECT
singInSuccessUrl = /manage.html
rememberMeSeconds = 294000
signOutUrl = /demo-logout.html
signUpUrl = /demo-signUp.html
```

- session管理相关配置，，前缀：zhipeng.security.browser.session，参见SessionProperties

```
maximumSessions = 1
maxSessionsPreventsLogin = false
sessionInvalidUrl = /demo-session-invalid.html
```

- 图片验证码配置项，前缀：zhipeng.security.code.image， 参见ImageCodeProperties

```
length = 4
width = 100
height = 30
expireIn = 30
url = /user/*
```

- 短信验证码配置项，前缀：zhipeng.security.code.sms，参见SmsCodeProperties

```
length = 6
expireIn = 60
url = /user/*
```

- 社交登录功能拦截的url，通过zhipeng.security.social.filterProcessesUrl配置， 参见SocilaProperties

```
zhipeng.security.social.filterProcessesUrl = /auth
```

- QQ登录配置，前缀：zhipeng.security.social.qq，参见QQProperties

```
app-id = 
app-secret = 
providerId = callback.do
```

##### 增加UserDetailsService接口实现

##### 如果需要记住我功能，需要创建数据库表(参见 db.sql)

##### 如果需要社交登录功能，需要以下额外的步骤

   1).配置appId和appSecret
   2).创建并配置用户注册页面，并实现注册服务(需要配置访问权限，如何配置请参考下文)，注意在服务中要调用ProviderSignInUtils的doPostSignUp方法。
   3).添加SocialUserDetailsService接口实现
   4).创建社交登录用的表 (参见 db.sql)

### 向spring容器注册以下接口的实现，可以替换默认的处理逻辑

- 配置访问权限，请实现**AuthorizeConfigProvider**接口，而不要继承WebSecurityConfigurerAdapter。这样才能双方配置生效。
- 密码加密解密策略
  org.springframework.security.crypto.password.PasswordEncoder

- 表单登录用户信息读取逻辑
  org.springframework.security.core.userdetails.UserDetailsService

- 社交登录用户信息读取逻辑
  org.springframework.social.security.SocialUserDetailsService

- 退出时的处理逻辑
  org.springframework.security.web.authentication.logout.LogoutSuccessHandler

- 短信发送的处理逻辑
  cn.net.zhipeng.security.core.validate.code.sms.SmsCodeSender

- 向spring容器注册名为imageValidateCodeGenerator的bean，可以替换默认的图片验证码生成逻辑,bean必须实现以下接口
  cn.net.zhipeng.security.core.validate.code.ValidateCodeGenerator

- 如果spring容器中有下面这个接口的实现，则在社交登录无法确认用户时，用此接口的实现自动注册用户，不会跳到注册页面
  org.springframework.social.connect.ConnectionSignUp

- Session失效时的处理策略
  org.springframework.security.web.session.InvalidSessionStrategy

- 并发登录导致前一个session失效时的处理策略配置
  org.springframework.security.web.session.SessionInformationExpiredStrategy
