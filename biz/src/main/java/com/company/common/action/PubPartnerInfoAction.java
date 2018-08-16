package com.company.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.common.context.Constant;
import com.company.modules.system.service.ChannelPartnerService;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;

@RequestMapping("/modules/common/PubPartnerInfoAction")
@Controller
public class PubPartnerInfoAction extends BaseAction {

    @Autowired
    private ChannelPartnerService channelPartnerService;


    @RequestMapping("/getPubPartnerInfo.htm")
    public void getPubPartnerInfo(HttpServletResponse response, HttpServletRequest request,
        @RequestParam(value = "partnername") String partnername) throws Exception{

        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> PartnerInfo = channelPartnerService.getLogoInfoByName(partnername);
        if (!PartnerInfo.isEmpty()) {
            returnMap.put("logourl", PartnerInfo.get("logo"));
            returnMap.put("titlename", PartnerInfo.get("title"));
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        } else {
            returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
        }
        ServletUtils.writeToResponse(response, returnMap);
    }
}
