package com.company.modules.appointment.action.impl;

import com.company.common.context.Constant;
import com.company.common.utils.ServletUtils;
import com.company.modules.appointment.action.AppointmentInfoAction;
import com.company.modules.appointment.service.AppointmentInfoService;
import com.company.modules.repay.domain.arithmetic.BaseBean;
import com.company.modules.repay.utils.JudgeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
@Component
public class AppointmentInfoActionImpl implements AppointmentInfoAction {

    @Autowired
    AppointmentInfoService appointmentInfoService;
    @Override
    public void addAppointmentInfo(String processInstanceId,
                                   String name,
                                   String phone,
                                   Integer type,
                                   String remark,
                                   Long appointTime,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data=null;
        if (JudgeValue.isNullOfString(processInstanceId)){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "processInstanceId不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if (JudgeValue.isNullOfString(name)){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "name不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if (JudgeValue.isNullOfString(phone)){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "phone不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if(JudgeValue.isNullOfString(remark)){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "remark不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if (JudgeValue.isNullOrL0OfInteger(type)){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "预约类型type不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if (JudgeValue.isNullOr0OfLong(appointTime)){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "预约时间不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        Map<String,Object> map = appointmentInfoService.addAppointmentInfo(processInstanceId,name,phone,type,remark,appointTime);
        if (map.get("msg").equals("ok")){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "插入成功");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }

    }

    @Override
    public void getAppointmentInfoByAnyKey(String processInstanceId,String name, String phone, Integer type, Long beginTime, Long endTime, BaseBean bb, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data=null;
        /*if (JudgeValue.isNullOfString(name)){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "name不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if (JudgeValue.isNullOfString(phone)){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "phone不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if (JudgeValue.isNullOrL0OfInteger(type)){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "预约类型type不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if (JudgeValue.isNullOr0OfLong(createTime)){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "预约时间不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }*/
        Map<String,Object> list = appointmentInfoService.getAppointmentInfoByAnyKey(processInstanceId,name,phone,type,beginTime,endTime,bb);
        if (!list.toString().contains("list")){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "列表为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }else {
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "返回列表成功");
            returnMap.put("tototalCount",list.get("count"));
            returnMap.put(Constant.RESPONSE_DATA,list.get("list"));
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
    }
}
