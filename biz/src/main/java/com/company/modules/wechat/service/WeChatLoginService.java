package com.company.modules.wechat.service;

import java.util.Map;


public interface WeChatLoginService {

    Map<String,Object> getWeChatUserInfo(String code);
}
