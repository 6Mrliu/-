package com.sangeng.enums;

import lombok.Data;


public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200, "操作成功"),
    // 登录
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    SYSTEM_ERROR(500, "出现错误"),
    USERNAME_EXIST(501, "用户名已存在"),
    PHONENUMBER_EXIST(502, "手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505, "用户名或密码错误"),
    CONTENT_NOT_NULL(500,"评论内容不能为空"),
    REQUIRE_PASSWORD(506, "必需填写密码"),
    REQUIRE_EMAIL(507, "必需填写邮箱"),
    REQUIRE_NICKNAME(508, "必需填写昵称"),
    FILE_TYPE_ERROR(509, "文件类型错误，请上传jpg文件"),
    FILE_NOT_FOUND(510, "文件不存在"),
    FILE_WRITE_ERROR(511, "文件写入错误")

    ;
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}