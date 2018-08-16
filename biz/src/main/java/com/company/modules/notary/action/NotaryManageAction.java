package com.company.modules.notary.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.common.context.Constant;
import com.company.common.service.PlBorrowRequirementService;
import com.company.common.service.PlBorrowService;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.instance.domain.HousPersonType;
import com.company.modules.instance.service.HousBorrowingBasicsService;
import com.company.modules.instance.service.HousPersonTypeService;
import com.company.modules.notary.service.NotaryManageService;

/**
 * User:    mcwang
 * DateTime:2016-08-10 03:59:46
 * details: 公证登记,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/notary/NotaryManageAction")
@Controller
public class NotaryManageAction extends BaseAction {

    /**
     * 公证登记的Service
     */
	@Autowired
	private NotaryManageService notaryManageService;
	
	@Autowired
	private HousBorrowingBasicsService housBorrowingBasicsService;
	
	@Autowired
	private PlBorrowRequirementService plBorrowRequirementService;

	@Autowired
	private PlBorrowService plBorrowService;
	@Autowired
	private HousPersonTypeService housPersonTypeService;

    /**
     * 根据流程id查询公证登记信息
     * @param request      页面的request
     * @param response      页面的response
     * @param processInstanceId  查询条件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getNotaryRegistData.htm")
    public void getNotaryRegistData(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "processInstanceId") String processInstanceId
		) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();;
		
		//对json对象进行转换
		Map<String, Object> housNotarizationRegistration = notaryManageService.getNotaryRegistDataByInstanceId(processInstanceId);
		HousBorrowingBasics housBorrowingBasics = housBorrowingBasicsService.getItemInfoByProcessInstanceId(processInstanceId);
		Map<String, Object> plBorrowRequirement = plBorrowService.getborrowInfo(processInstanceId);
		List<HousPersonType> housPersonTypes = housPersonTypeService.getItemInfoByProcessInstanceId(processInstanceId);
		data.put("housNotarizationRegistration", housNotarizationRegistration);
		data.put("plBorrowRequirement", plBorrowRequirement);
		data.put("housBorrowingBasics", housBorrowingBasics);
		data.put("housPersonTypes", housPersonTypes);
		returnMap.put(Constant.RESPONSE_DATA, data);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * 根据流程id查询公证登记信息
     * @param request      页面的request
     * @param response      页面的response
     * @param processInstanceId  查询条件
     * @throws Exception
     */
    @RequestMapping("/getNotaryRegistDataOnly.htm")
    public void getNotaryRegistDataOnly(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "processInstanceId") String processInstanceId
		) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		//对json对象进行转换
		Map<String, Object> housNotarizationRegistration = notaryManageService.getNotaryRegistDataByInstanceId(processInstanceId);
		data.put("housNotarizationRegistration", housNotarizationRegistration);
		returnMap.put(Constant.RESPONSE_DATA, data);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
}
