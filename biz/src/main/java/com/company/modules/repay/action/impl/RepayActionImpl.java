package com.company.modules.repay.action.impl;

import com.company.common.context.Constant;
import com.company.common.utils.ServletUtils;
import com.company.modules.repay.action.RepayAction;
import com.company.modules.repay.domain.RpRepayDetail;
import com.company.modules.repay.domain.arithmetic.BaseBean;
import com.company.modules.repay.service.RepayService;
import com.company.modules.repay.utils.JudgeValue;
import com.company.modules.repay.utils.ReturnData;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class RepayActionImpl implements RepayAction {

    @Autowired
    RepayService repayService;

    @Override
    public void getPreSetRepayPlanList(Integer pageNum,
                                                 Integer pageSize,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {

        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data=null;



        if (null==pageNum||pageNum<=0){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "pageNum不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if (null==pageSize||pageSize<=0){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "pageSize不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        PageInfo<Map<String, Object>> page = repayService.getPreSetRepayPlanList(pageNum,pageSize,request,response);
        returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        returnMap.put(Constant.RESPONSE_CODE_MSG, "获取待设置还款计划的列表成功");
        returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT,page.getTotal());
        returnMap.put(Constant.RESPONSE_DATA, page.getList());
        // 返回给页面
        ServletUtils.writeToResponse(response, returnMap);
    }

    @Override
    public void createRepayPlan(String serviceParams,HttpServletRequest request,
                                HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data=null;
        JSONObject jsonObject = JSONObject.fromObject(serviceParams);

        if (jsonObject.get("projectId").equals("")||jsonObject.get("account").equals("")||
                jsonObject.get("repayType").equals("")|| jsonObject.get("term").equals("")||
                jsonObject.get("repayRate").equals("")|| jsonObject.get("repayDay").equals("")||
                jsonObject.getInt("repayDay")<1||jsonObject.getInt("repayDay")>30||
                jsonObject.get("isSkipFirstMonth").equals("")||jsonObject.get("isCalcServiceFee").equals("")||
                jsonObject.get("overduePenaltyRate").equals("")|| jsonObject.get("serviceFee").equals("") ||
                jsonObject.get("loanDate").equals("")){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "还款明细参数不能存在空值/或者不合法");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        Map<String,Object> res = repayService.createRepayPlan(serviceParams);
        if (res.get("msg").equals("ok")){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "创建还款计划列表成功");
        }
        ServletUtils.writeToResponse(response, returnMap);
    }

    /**
     * 手动生成还款计划
     * @param repaySetting
     * @param repayPlan
     * @param request
     * @param response
     */
    public void handCreateRepayPlan(String repaySetting,
                                    String repayPlan,
                                    HttpServletRequest request,
                                    HttpServletResponse response){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data=null;


        if (null==repaySetting||repaySetting.equals("")||null==repayPlan||repayPlan.equals("")){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "还款明细参数不能存在空值/或者不合法");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        Map<String,Object> map = repayService.handCreateRepayPlan(repaySetting,repayPlan);
        if (map.get("msg").equals("ok")){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "生成还款计划成功");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }else {
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "生成还款计划失败");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }

    }
    @Override
    public void getRepayPlanByAnyKey(long projectId, BaseBean bb, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data=null;
        if (projectId<=0){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "projectId不合法");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if (JudgeValue.isNullOr0OfInteger(bb.getPageNum())){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "pageNum不合法");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if (JudgeValue.isNullOr0OfInteger(bb.getPageSize())){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "pageSize不合法");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        //执行查询
        Map<String, Object> page = repayService.getRepayPlanByAnyKey(projectId,bb);
        if (page.get("msg").equals("no")){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "列表为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        returnMap.put(Constant.RESPONSE_CODE_MSG, "成功返回还款计划列表");
        returnMap.put(Constant.RESPONSE_DATA,page.get("list"));
        returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT,page.get("count"));
        // 返回给页面
        ServletUtils.writeToResponse(response, returnMap);
    }

    @Override
    public ReturnData refundPunishedToTheDate() {
        return null;
    }

    @Override
    public void refundToTheTerm(RpRepayDetail rpRepayDetail,int type,HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data=null;
        if (JudgeValue.isNullOrL0OfInteger(rpRepayDetail.getProjectId())){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "projectId不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if (JudgeValue.isNullOrL0OfInteger(rpRepayDetail.getTerm())){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "期次term不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if(JudgeValue.isNullOfString(rpRepayDetail.getRepayStatusType())){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "还款状态repayStatusType不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        if (JudgeValue.isNullOrL0OfInteger(type)){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "还款类型type不能为空");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
        Map<String,Object> res = repayService.refundToTheTerm(rpRepayDetail,type);
        if (res.get("msg").equals("ok")){
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, "操作成功");
            // 返回给页面
            ServletUtils.writeToResponse(response, returnMap);
        }
    }

    @Override
    public ReturnData preRefund() {
        return null;
    }
}
