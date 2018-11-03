package com.company.modules.wechat.service.impl;

import com.company.modules.wechat.config.WeChatConfig;
import com.company.modules.wechat.service.WeChatLoginService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeChatLoginServiceImpl implements WeChatLoginService {
    @Override
    public Map<String, Object> getWeChatUserInfo(String code) {
        //1.请求access_token和openid
        Map<String,Object> userInfoMap = null;
        Map<String,Object> tokenMap = getAccessTokenAndOpenId(code);
        if (tokenMap.toString().contains(WeChatConfig.ERRCODE)||tokenMap.toString().contains(WeChatConfig.ERRMSG)){
            return tokenMap;//如果请求失败，直接返回给前端错误信息
        }
        //2.通过第一步拿到的数据请求个人信息
        userInfoMap = getWeChatUserInfo(tokenMap);

        return userInfoMap;
    }

    //获取微信用户信息
    private Map<String,Object> getWeChatUserInfo(Map<String,Object> tokenMap) {
        Map<String,Object> map = new HashMap<>();
        HttpResponse<String> response = null;
        try {
            response = Unirest.get(WeChatConfig.GET_USER_INFO_URI+"?access_token="+tokenMap.get("accessToken")+"&openid="+tokenMap.get("openId")+"")
                    .header("Cache-Control", "no-cache")
                    .header("Postman-Token", "f74ab367-15f5-4699-8488-f471c7e6d33c")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if (null==response){
            throw new RuntimeException("请求微信个人信息接口失败，请检查网络");
        }
        String respBody = response.getBody();
        JSONObject jsonObject = JSONObject.fromObject(respBody);
        if (respBody.contains(WeChatConfig.ERRCODE)||respBody.contains(WeChatConfig.ERRMSG)){
            map.put(WeChatConfig.ERRCODE,jsonObject.get(WeChatConfig.ERRCODE));
            map.put(WeChatConfig.ERRMSG,jsonObject.get(WeChatConfig.ERRMSG));
            return map;
        }
        map.put("userInfo",respBody);
        return map;
    }



    //获取token和openid
    private Map<String,Object> getAccessTokenAndOpenId(String code) {
        Map<String,Object> map = new HashMap<>();
        HttpResponse<String> response = null;
        try {
            response = Unirest.get(WeChatConfig.GET_ACCESS_TOKEN_URI+"?appid="+WeChatConfig.AppID+"&secret="+WeChatConfig.AppSecret+"&code="+code+"&grant_type=authorization_code")
                    .header("Cache-Control", "no-cache")
                    .header("Postman-Token", "02aabeae-644e-43cb-967a-3819294ca10e")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if (null==response){
            throw new RuntimeException("请求微信个人信息接口失败，请检查网络");
        }
        String respBody = response.getBody();
        JSONObject jsonObject = JSONObject.fromObject(respBody);
        if (respBody.contains(WeChatConfig.ERRCODE)||respBody.contains(WeChatConfig.ERRMSG)){
            map.put(WeChatConfig.ERRCODE,jsonObject.get(WeChatConfig.ERRCODE));
            map.put(WeChatConfig.ERRMSG,jsonObject.get(WeChatConfig.ERRMSG));
            return map;
        }
        map.put("openId",jsonObject.get("openid"));
        map.put("accessToken",jsonObject.get("access_token"));
        return map;
    }

    public static void main(String[] args) {
        //1.请求access_token
        HttpResponse<String> response = null;
        try {
            response = Unirest.get(WeChatConfig.GET_ACCESS_TOKEN_URI+"?appid="+WeChatConfig.AppID+"&secret="+WeChatConfig.AppSecret+"&code=071DuOmO13OVc31oDPmO19WJmO1DuOmi&grant_type=authorization_code")
                    .header("Cache-Control", "no-cache")
                    .header("Postman-Token", "02aabeae-644e-43cb-967a-3819294ca10e")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        //请求失败
        //{"errcode":40163,"errmsg":"code been used, hints: [ req_id: uIe4KA0745hb26 ]"}
        //{"errcode":40001,"errmsg":"invalid credential, access_token is invalid or not latest, hints: [ req_id: lWUEAa08862291 ]"}
        //请求成功
        //{"access_token":"13_SZK7GwQDsf5utiTSZJim9uzpH4Hm3YVmwds_U610CyGg1BKx_ixrUb79YfsbdLjGX6S7_Xggw4y0Vkmh4P5m1w","expires_in":7200,"refresh_token":"13_i6E930STlZa2BLEJ7WU2OyN62SghkuiU11KaZUdbMuUVRqBeiq9B5xeKpxhDtmCWoDJPEmFbHF-uJOsCVI2eGQ","openid":"oJeOx0sMgJkhCrgGrMLOj7MHUGI8","scope":"snsapi_userinfo"}
        //{"openid":"oJeOx0sMgJkhCrgGrMLOj7MHUGI8","nickname":"知白","sex":2,"language":"zh_CN","city":"Baoshan","province":"Shanghai","country":"CN","headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/3KyGR4RxIebVk6zwQibAcTm8uiavxOdgul1QJZlHqkhAfpTBlQq7qorQp7ibndiaywpZUsiapFoic89mV1lx23Jlmxiag\/132","privilege":[]}

        String respBody = response.getBody();
        System.out.println(respBody);
        System.out.println(response.getStatus());

        Map<String,Object> map = new HashMap<>();
        map.put("msg",1);
        map.put("msg2","12");
        System.out.println(map.toString());
    }
}
