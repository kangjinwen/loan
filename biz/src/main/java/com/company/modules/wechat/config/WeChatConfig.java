package com.company.modules.wechat.config;

public class WeChatConfig {

    //广尊微信开发相关参数
    public static final String AppID = "wxc35d7083dafe8d8c";
    public static final String AppSecret = "fb2f5cdb0831a53318a517eb5ab60306";
    public static final String redirect_uri = "http://day.ngrok.goodzoomtech.com";


    //个人微信开发
    public static final String myAppID = "wx25aa0980041e974b";
    public static final String myAppSecret = "448659edd5662c80c639d6e2e0ac748a";
    public static final String myredirect_uri = "";


    //微信接口地址
    public static final String GET_ACCESS_TOKEN_URI = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String GET_USER_INFO_URI = "https://api.weixin.qq.com/sns/userinfo";

    //微信常用错误码
    public static final String ERRCODE = "errcode";
    public static final String ERRMSG = "errmsg";
}
