package com.company.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.modules.system.domain.SysUser;
import com.company.common.context.Constant;
import com.company.common.domain.PlApprovalResults;
import com.company.common.service.PlApprovalResultsService;
import com.company.common.domain.PlBorrowRequirement;
import com.company.common.service.PlBorrowRequirementService;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;

@RequestMapping("/modules/common/PubApprovalResultsAction")
@Controller
public class PubApprovalResultsAction extends BaseAction {

    @Autowired
    private PlApprovalResultsService plApprovalResultsService;

    @Autowired
    private PlBorrowRequirementService plBorrowRequirementService;

    @RequestMapping("/getPubApprovalResults.htm")
    public void getPubApprovalResults(HttpServletResponse response, HttpServletRequest request,
        @RequestParam(value = "processInstanceId", required = true) String processInstanceId,
        @RequestParam(value = "approvalaccount") BigDecimal approvalaccount,
        @RequestParam(value = "approvaltimelimit") Integer approvaltimelimit,
        @RequestParam(value = "mortgageprice") BigDecimal mortgageprice,
        @RequestParam(value = "remark") String remark) throws Exception{

        long influence = 0;
        Map<String, Object> returnMap = new HashMap<String, Object>();
        SysUser sysUser = this.getLoginUser(request);

        PlApprovalResults plApprovalResults = plApprovalResultsService.getInfoByProcessInstanceId(processInstanceId);
        if (plApprovalResults != null) {
            if (plApprovalResults.getApprovalAccount() != approvalaccount) {
                plApprovalResults.setApprovalAccount(approvalaccount);
            }
            if (plApprovalResults.getApprovalTimeLimit() != approvaltimelimit) {
                plApprovalResults.setApprovalTimeLimit(approvaltimelimit);
            }
            if (plApprovalResults.getMortgagePrice() != mortgageprice) {
                plApprovalResults.setMortgagePrice(mortgageprice);
            }
            influence =plApprovalResultsService.update(plApprovalResults);
            if (influence > 0) {
                returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
            } else {
                returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
            }
        }  else {
            PlBorrowRequirement plBorrowRequirement = plBorrowRequirementService.getInfoByProcessInstanceId(processInstanceId);
            if (plBorrowRequirement != null) {
                PlApprovalResults newplApprovalResults = new PlApprovalResults();
                newplApprovalResults.setCreator(plBorrowRequirement.getCreator().intValue());
                newplApprovalResults.setCreateTime(new Date());
                newplApprovalResults.setProcessInstanceId(processInstanceId);
                newplApprovalResults.setProjectId(plBorrowRequirement.getProjectId().intValue());
                newplApprovalResults.setConsultId(plBorrowRequirement.getConsultId().intValue());
                if (plBorrowRequirement.getCustomerId() != null) {
                    newplApprovalResults.setCustomerId(plBorrowRequirement.getCustomerId().intValue());
                }
                newplApprovalResults.setProductId(plBorrowRequirement.getProductId().intValue());
                newplApprovalResults.setApprovalAccount(approvalaccount);
                newplApprovalResults.setApprovalTimeLimit(approvaltimelimit);
                newplApprovalResults.setMortgagePrice(mortgageprice);
                influence = plApprovalResultsService.insert(newplApprovalResults);
                if (influence > 0) {
                    returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                    returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
                } else {
                    returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                    returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
                }
            } else {
                returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
            }
        }
        ServletUtils.writeToResponse(response, returnMap);
    }

    @RequestMapping("/queryPubApprovalResults.htm")
    public void QueryPubApprovalResults(HttpServletResponse response, HttpServletRequest request,
                                      @RequestParam(value = "processInstanceId", required = true) String processInstanceId) throws Exception{

        long influence = 0;
        Map<String, Object> returnMap = new HashMap<String, Object>();
        SysUser sysUser = this.getLoginUser(request);

        PlApprovalResults plApprovalResults = plApprovalResultsService.getInfoByProcessInstanceId(processInstanceId);
        if (plApprovalResults != null) {
            returnMap.put("approvalaccount", plApprovalResults.getApprovalAccount().toString());
            returnMap.put("approvaltimelimit", plApprovalResults.getApprovalTimeLimit().toString());
            returnMap.put("mortgageprice", plApprovalResults.getMortgagePrice().toString());
            returnMap.put("remark", plApprovalResults.getRemark());
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        } else {
            returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
        }
        ServletUtils.writeToResponse(response, returnMap);
    }
}
