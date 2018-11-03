package com.company.modules.wechat.action;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信登录相关接口
 */
@RestController
public interface WeChatLoginAction {
    @RequestMapping(value = "modules/repay/WeChatLoginAction/getWeChatUserInfo.htm",method = RequestMethod.GET)
    void getWeChatUserInfo(@RequestParam(required = false) String code,
                           HttpServletRequest request,
                           HttpServletResponse response);
}
