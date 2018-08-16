package com.company.modules.contract.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.common.service.PlBorrowService;
import com.company.common.utils.DateUtil;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.RulesNumberUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.contract.domain.PlContract;
import com.company.modules.contract.service.PlContractService;

/**
 * 合同管理
 * 
 * @version 1.0
 * @author wulb
 * @created 2016年08月15日
 */
@RequestMapping("/modules/contract/action/PlContractAction")
@Controller
public class PlContractAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(PlContractAction.class);
	@Autowired
	private PlBorrowService plBorrowService;
	@Autowired
	private PlContractService plContractService;

	/**
	 * 1、保存功能 2、生成协议
	 * 
	 * @param request
	 * @param response
	 * @param params
	 *            合同基本信息
	 * @param status
	 *            更新或插入
	 * @version 1.0
	 * @author wulb
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveContract.htm")
	public void save(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "contract", required = false) String contract,
			@RequestParam(value = "status", required = false) String status) throws Exception {
		logger.info("PlContractAction-save 合同管理-生成合同...");
		boolean isFlag = false;
		Map<String, Object> res = new HashMap<String, Object>();
		JSONObject tempJson = new JSONObject();
		tempJson.putAll(JsonUtil.parse(contract, Map.class));
		Map<String, Object> map = JsonUtil.parse(tempJson.toJSONString(), Map.class);
		if ("update".equals(status)) {// 更新
			Map<String, Object> m = new HashMap<String, Object>();
			map.put("modifier", this.getLoginUser(request).getUserName());
			m.put("qdhtdata", tempJson.toJSONString());
			m.put("paramap", map);
			isFlag = plContractService.updatePlContractById(m);
		} else {// 保存合同内容
			PlContract plContract = new PlContract();
			String processInstanceId = String.valueOf(map.get("processInstanceId"));
			Map<String, Object> param = new HashMap<String, Object>();
        	param.put("createTime", DateUtil.dateStr(new Date(), DateUtil.MONTH_PATTERN));
        	List<PlContract> contractList = plContractService.getPageListByMap(param);	 //目前是获取当月的信息，如果有编号是递增。如果有没下面编号生成则从01开始。
        	Map<String,Object> pubMap = new HashMap<String, Object>();
        	pubMap.put("contractList", contractList);
        	String contractNo = RulesNumberUtil.createContractNo(pubMap);//合同编号
        	plContract.setContractNo(contractNo);
        	plContract.setProcessInstanceId(processInstanceId);
        	plContract.setCreateTime(new Date());
        	plContract.setQdhtdata(tempJson.toJSONString());
        	plContract.setBorrowEnd(DateUtil.getDate(String.valueOf(map.get("borrowEnd")), DateUtil.DATE_PATTERN));
        	plContract.setBorrowStart(DateUtil.getDate(String.valueOf(map.get("borrowStart")), DateUtil.DATE_PATTERN));
        	plContract.setSignatureTime(DateUtil.getDate(String.valueOf(map.get("signatureTime")), DateUtil.DATE_PATTERN));
        	plContract.setCreator(this.getLoginUser(request).getUserName());
        	plContract.setConsultId(Long.valueOf(String.valueOf(map.get("consultId"))));
        	plContract.setContractAccount(new BigDecimal(String.valueOf(map.get("account"))));
			isFlag = plContractService.addPlContract(plContract, map);
		}
		if (isFlag) {
			res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			res.put(Constant.RESPONSE_CODE_MSG, "操作成功");
		} else {
			res.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			res.put(Constant.RESPONSE_CODE_MSG, "数据保存失败，请稍后重试");
		}
		ServletUtils.writeToResponse(response, res);
	}
	
	@RequestMapping("/createContractNo.htm")
	public void createContractNo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "processInstanceId", required = true) String processInstanceId,
			@RequestParam(value = "consultId", required = true) Long consultId, 
			@RequestParam(value = "projectId", required = true) Long projectId) throws Exception {
		logger.info("PlContractAction-save 合同管理-生成合同编号...");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		PlContract plContract = plContractService.getItemInfoByProcessInstanceId(processInstanceId);
		if(plContract == null){
			plContract = new PlContract();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("createTime", DateUtil.dateStr(new Date(), DateUtil.MONTH_PATTERN));
			List<PlContract> contractList = plContractService.getPageListByMap(param);	 //目前是获取当月的信息，如果有编号是递增。如果有没下面编号生成则从01开始。
			Map<String,Object> pubMap = new HashMap<String, Object>();
			pubMap.put("contractList", contractList);
			String contractNo = RulesNumberUtil.createContractNo(pubMap);//合同编号
			plContract.setContractNo(contractNo);
			plContract.setProcessInstanceId(processInstanceId);
			plContract.setProjectId(projectId);
			plContract.setCreateTime(new Date());
			plContract.setCreator(this.getLoginUser(request).getUserName());
			plContract.setConsultId(consultId);
			plContract.setStatus((byte)0);
			plContractService.insert(plContract);
		}
		returnMap.put(Constant.RESPONSE_DATA, plContract);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, returnMap);
	}

	/**
	 * 借款协议页面初始化
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getBorrowInfo.htm")
	public void getBorrowInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("#####################PlContractAction-getBorrowInfo 合同管理-合同生成页面初始化");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String processInstanceId = this.paramString(request, "processInstanceId");
		PlContract plContract = plContractService.getItemInfoByProcessInstanceId(processInstanceId);
		returnMap.put(Constant.RESPONSE_DATA, plContract);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, returnMap);
	}

	/**
	 * 合同列表
	 * @param request
	 * @param response
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getContractList.htm")
	public void getContractList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "currentPage") Integer currentPage,
			@RequestParam(value = "pageSize") Integer pageSize) throws Exception{
		logger.info("#####################PlContractAction-getContractList 合同管理-合同列表");
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		//对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)){
        	data = JsonUtil.parse(searchParams, Map.class);
        }
		data.put("userId", this.getLoginUser(request).getUserName());
		data.put("status", "1");
		PageHelper.startPage(currentPage, pageSize);
		List<PlContract> contractList = plContractService.getPageListByMap(data);
		PageInfo<PlContract> page = new PageInfo<PlContract>(contractList);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, returnMap);
	}

	/**
	 * 根据签署日期计算还款起始日和借款结束日和还款日
	 * 
	 * @description
	 * @param request
	 * @param response
	 * @author wulb
	 * @return void
	 * @since 1.0.0
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/getRepaymentDay.htm")
	public void getRepaymentDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String signaturedateStr = this.paramString(request, "signatureTime");
		String timeLimit = this.paramString(request, "timeLimit");
		Date signaturedateDate = DateUtil.getDate(signaturedateStr, DateUtil.DATE_PATTERN);
		Date afterMonth = DateUtil.rollMon(signaturedateDate, Integer.parseInt(timeLimit));
		String yesterDay = DateUtil.yesteday(afterMonth, 1);
		Date yesterDate = DateUtil.getDate(yesterDay, DateUtil.DATE_PATTERN);
		responseMap.put("repaymentDay", yesterDate.getDate());// 还款日
		responseMap.put("borrowEnd", DateUtil.dateStr(yesterDate, DateUtil.DATE_PATTERN));// 借款结整日
		ServletUtils.writeToResponse(response, responseMap);
	}
}
