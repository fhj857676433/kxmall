package com.kxmall.market.app.api.api.user;

import com.kxmall.market.core.Const;
import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.annotation.param.TextFormat;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.UserDTO;

/**
 * Created by admin on 2019/6/30.
 */
@HttpOpenApi(group = "user", description = "用户服务")
public interface UserService {

    @HttpMethod(description = "发送验证码到用户手机")
    public String sendVerifyCode(
            @NotNull @TextFormat(length = 11) @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone) throws ServiceException;


    @HttpMethod(description = "手机号登录或者注册")
    public UserDTO phoneLoginOrRegister(
            @NotNull @TextFormat(length = 11) @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone,
            @NotNull @HttpParam(name = "verifyCode", type = HttpParamType.COMMON, description = "注册验证码") String verifyCode,
            @HttpParam(name = "ip", type = HttpParamType.IP, description = "用户Ip") String ip,
            @HttpParam(name = "openId", type = HttpParamType.COMMON, description = "第三方平台返回的数据openId") String openId,
            @HttpParam(name = "p", type = HttpParamType.COMMON, description = "邀请码") String p
    ) throws ServiceException;

    @HttpMethod(description = "用户注册")
    public String register(
            @NotNull @TextFormat(length = 11) @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone,
            @NotNull @TextFormat(lengthMin = 8, lengthMax = 18, notChinese = true) @HttpParam(name = "password", type = HttpParamType.COMMON, description = "用户密码") String password,
            @NotNull @HttpParam(name = "verifyCode", type = HttpParamType.COMMON, description = "注册验证码") String verifyCode,
            @HttpParam(name = "ip", type = HttpParamType.IP, description = "用户Ip") String ip) throws ServiceException;

    @HttpMethod(description = "用户绑定手机号")
    public String bindPhone(
            @NotNull @TextFormat(length = 11) @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone,
            @NotNull @TextFormat(lengthMin = 8, lengthMax = 18, notChinese = true) @HttpParam(name = "password", type = HttpParamType.COMMON, description = "设置用户密码") String password,
            @NotNull @HttpParam(name = "verifyCode", type = HttpParamType.COMMON, description = "注册验证码") String verifyCode,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "用户重置密码")
    public String resetPassword(
            @NotNull @TextFormat(length = 11) @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone,
            @NotNull @TextFormat(lengthMin = 8, lengthMax = 18, notChinese = true) @HttpParam(name = "password", type = HttpParamType.COMMON, description = "用户密码") String password,
            @NotNull @HttpParam(name = "verifyCode", type = HttpParamType.COMMON, description = "注册验证码") String verifyCode) throws ServiceException;

    @HttpMethod(description = "用户登录")
    public UserDTO login(
            @NotNull @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone,
            @NotNull @TextFormat(lengthMin = 8, lengthMax = 18, notChinese = true) @HttpParam(name = "password", type = HttpParamType.COMMON, description = "用户密码") String password,
            @HttpParam(name = "loginType", type = HttpParamType.COMMON, description = "登录方式") Integer loginType,
            @HttpParam(name = "raw", type = HttpParamType.COMMON, description = "第三方平台返回的数据") String raw,
            @NotNull @HttpParam(name = "ip", type = HttpParamType.IP, description = "登录IP") String ip) throws ServiceException;

    @HttpMethod(description = "微信授权手机号")
    public UserDTO authPhone(
            @NotNull @HttpParam(name = "encryptedData", type = HttpParamType.COMMON, description = "第三方平台返回的数据encryptedData") String encryptedData,
            @NotNull @HttpParam(name = "iv", type = HttpParamType.COMMON, description = "第三方平台返回的数据iv") String iv,
            @NotNull @HttpParam(name = "loginType", type = HttpParamType.COMMON, description = "第三方代号") Integer loginType,
            @NotNull @HttpParam(name = "session_key", type = HttpParamType.COMMON, description = "第三方平台返回的数据session_key") String session_key,
            @NotNull @HttpParam(name = "openId", type = HttpParamType.COMMON, description = "第三方平台返回的数据openId") String openId,
            @HttpParam(name = "p", type = HttpParamType.COMMON, description = "邀请码") String p,
            @NotNull @HttpParam(name = "ip", type = HttpParamType.IP, description = "登录IP") String ip) throws ServiceException;

    @HttpMethod(description = "用户注销")
    public String logout(
            @NotNull @HttpParam(name = Const.USER_ACCESS_TOKEN, type = HttpParamType.HEADER, description = "用户访问") String accessToken,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "第三方登录")
    public UserDTO thirdPartLogin(
            @NotNull @HttpParam(name = "loginType", type = HttpParamType.COMMON, description = "第三方代号") Integer loginType,
            @NotNull @HttpParam(name = "ip", type = HttpParamType.IP, description = "用户Ip") String ip,
            @NotNull @HttpParam(name = "raw", type = HttpParamType.COMMON, description = "第三方平台返回的数据") String raw) throws ServiceException;

    @HttpMethod(description = "同步用户信息")
    public String syncUserInfo(
            @HttpParam(name = "nickName", type = HttpParamType.COMMON, description = "用户昵称") String nickName,
            @HttpParam(name = "nickname", type = HttpParamType.COMMON, description = "用户昵称") String nickname,
            @HttpParam(name = "avatarUrl", type = HttpParamType.COMMON, description = "用户头像url") String avatarUrl,
            @HttpParam(name = "gender", type = HttpParamType.COMMON, description = "性别0未知1男2女") Integer gender,
            @HttpParam(name = "birthday", type = HttpParamType.COMMON, description = "用户生日") Long birthday,
            @HttpParam(name = Const.USER_ACCESS_TOKEN, type = HttpParamType.HEADER, description = "访问令牌") String accessToken,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId) throws ServiceException;

    @HttpMethod(description = "获取H5签名")
    public Object getH5Sign(
            @NotNull @HttpParam(name = "url", type = HttpParamType.COMMON, description = "url") String url) throws ServiceException;

}
