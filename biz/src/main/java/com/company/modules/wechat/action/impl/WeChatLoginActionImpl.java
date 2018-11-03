package com.company.modules.wechat.action.impl;

import com.company.common.context.Constant;
import com.company.common.utils.ServletUtils;
import com.company.modules.wechat.action.WeChatLoginAction;
import com.company.modules.wechat.config.WeChatConfig;
import com.company.modules.wechat.service.WeChatLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
@Component
public class WeChatLoginActionImpl implements WeChatLoginAction {
    @Autowired
    WeChatLoginService weChatLoginService;
    @Override
    public void getWeChatUserInfo(String code, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //用户禁止授权，此时前端传来的code是空的
        if (code==null||code.equals("")){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "用户禁止授权");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        Map<String,Object> map = weChatLoginService.getWeChatUserInfo(code);
        if (map.toString().contains(WeChatConfig.ERRMSG)||map.toString().contains(WeChatConfig.ERRCODE)){
            returnMap.put(Constant.RESPONSE_CODE, map.get(WeChatConfig.ERRCODE));
            returnMap.put(Constant.RESPONSE_CODE_MSG, map.get(WeChatConfig.ERRMSG));
            ServletUtils.writeToResponse(response, returnMap);
        }else {
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, map.get("userInfo"));
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }

    }
}
