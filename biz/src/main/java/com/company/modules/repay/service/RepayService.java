package com.company.modules.repay.service;

import com.company.modules.repay.domain.RpRepayDetail;
import com.company.modules.repay.domain.arithmetic.BaseBean;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface RepayService {
    PageInfo<Map<String, Object>> getPreSetRepayPlanList(Integer pageNum, Integer pageSize, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> createRepayPlan(String serviceParams);

    Map<String, Object> getRepayPlanByAnyKey(long projectId, BaseBean bb);

    /**
     * 对谋其进行还款（本质，更新改变还款状态）
     * @param rpRepayDetail
     * @return
     */
    Map<String,Object> refundToTheTerm(RpRepayDetail rpRepayDetail,int tpye);

    Map<String,Object> handCreateRepayPlan(String repaySetting, String repayPlan);
}
