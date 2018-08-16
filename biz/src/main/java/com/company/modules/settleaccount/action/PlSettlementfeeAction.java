package com.company.modules.settleaccount.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.NumberUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.RMB;
import com.company.modules.settleaccount.SettlementVO;
import com.company.modules.settleaccount.service.PlSettlementfeeService;
import com.company.modules.system.domain.SysUser;

/**
 * @Description 结算管理
 * @author wtc,wtc@erongdu.com
 * @CreatTime 2015年6月30日 下午5:45:55
 * @since version 1.0.0
 */
@Controller
@RequestMapping("/modules/settleaccount/PlSettlementfeeAction")
@SuppressWarnings("all")
public class PlSettlementfeeAction extends BaseAction {
    
    @Autowired
    private PlSettlementfeeService plSettlementfeeService;
    
    
    @RequestMapping("/getPlSettlementfeeList.htm")
    public void getPlSettlementfeeList(HttpServletResponse response, HttpServletRequest request,
		SettlementVO settlement) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("vo", settlement);
		
		SysUser loginUser = getLoginUser(request);
        List<String> coverdOffices = getCoverdOffices(loginUser);
        paramMap.put("coverdOffices", coverdOffices);
        paramMap.put("roleNid", getRoleForLoginUser(request).getNid());
        PageHelper.startPage(settlement.getPageNum(), settlement.getPageSize());
		List<Map> plSettlementfees = plSettlementfeeService.getPageListByMap(paramMap,settlement.getIsCompleted());
		if (CollectionUtils.isNotEmpty(plSettlementfees)) {
			for (Map map : plSettlementfees) {
				BigDecimal account=(BigDecimal)map.get("borrowAccount");
	            String big = RMB.toBigAmt(account.doubleValue());
	            map.put("borrowAccountChinese", big);
			}
		}
		
		PageInfo<Map> page = new PageInfo<Map>(plSettlementfees);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    
    

    /**
     * 结算处理
     * @param json	{isSettleaccounts,processInstanceId,remark}
     * @param request
     * @param response
     * @throws ServiceException
     */
    @RequestMapping("/settleAccounts")
    public void settleAccounts(@RequestParam(value = "json", required = true) String json,
            HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Map<String, Object> map = JsonUtil.parse(json, Map.class);
        Map<String, Object> res = new HashMap<String, Object>();
        map.put("handlerName", getLoginUserName(request));
        map.put("handlerTime", new Date());
        SysUser loginUser = getLoginUser(request);
        map.put("userid", loginUser.getId());
        plSettlementfeeService.saveOrUpdateSettleAccountsDetail(map);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 查看结算信息
     * @param processInstanceId
     * @param request
     * @param response
     * @throws ServiceException
     */
    @RequestMapping("/settleAccountsDetail")
    public void settleAccountsDetail(
            @RequestParam(value = "processInstanceId", required = true) String processInstanceId,
            HttpServletRequest request, HttpServletResponse response) throws ServiceException{
        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Object> map = plSettlementfeeService.settleAccountsDetail(processInstanceId);
        map.put("userName", getLoginUser(request).getName());
        NumberUtil.roundBigDecimal(map);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, map);
        ServletUtils.writeToResponse(response, res);
        
    }

}
