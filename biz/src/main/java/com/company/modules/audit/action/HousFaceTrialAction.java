package com.company.modules.audit.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.common.service.PlBorrowService;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.audit.domain.HousControlInformation;
import com.company.modules.audit.domain.HousCreditInformation;
import com.company.modules.audit.domain.HousFaceTrial;
import com.company.modules.audit.domain.HousMarriageInformation;
import com.company.modules.audit.service.AuditService;
import com.company.modules.audit.service.HousControlInformationService;
import com.company.modules.audit.service.HousCreditInformationService;
import com.company.modules.audit.service.HousFaceTrialService;
import com.company.modules.audit.service.HousMarriageInformationService;
import com.company.modules.audit.util.databean.FaceAuditDataBean;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.instance.domain.HousPropertyInformation;
import com.company.modules.instance.service.HousPropertyInformationService;
import com.company.modules.system.domain.SysUser;
import com.company.modules.warrant.domain.HousOritoInformation;
import com.company.modules.warrant.service.HousOritoInformationService;

/**
 * User:    fzc
 * DateTime:2016-08-14 01:28:09
 * details: 面审信息表,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/audit/HousFaceTrialAction")
@Controller
public class HousFaceTrialAction extends BaseAction {

    /**
     * 面审信息表的Service
     */
	@Autowired
	private HousFaceTrialService housFaceTrialService;

    /**
     * 下户信息表(权证下户)的Service
     */
	@Autowired
	private HousOritoInformationService housOritoInformationService;
	/**
     * 风控信息表(面审)的Service
     */
	@Autowired
	private HousControlInformationService housControlInformationService;
	 /**
     * 征信信息表(面审)的Service
     */
	@Autowired
	private HousCreditInformationService housCreditInformationService;
	/**
     * 婚姻信息表(面审)的Service
     */
	@Autowired
	private HousMarriageInformationService housMarriageInformationService;

	@Autowired
	//@Resource(name="faceAuditServiceImpl")
	private AuditService faceAuditServiceImpl;

	@Autowired
	private PlBorrowService plBorrowServiceImpl;
	@Autowired
	private HousPropertyInformationService housPropertyInformationService;

    /**
     * 面审信息表表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param housFaceTrial	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdateHousFaceTrial.htm")
    public void saveOrUpdateHousFaceTrial(HttpServletRequest request, HttpServletResponse response,
    	@RequestParam(value = "housFaceTrial", required = false) String housFaceTrial, @RequestParam(value = "status", required = false) String status) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		SysUser sysUser = this.getLoginUser(request);
		HousFaceTrial housFaceTrialInfo = new HousFaceTrial();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(housFaceTrial)) {
			housFaceTrialInfo = JsonUtil.parse(housFaceTrial, HousFaceTrial.class);
		}
		if (status.equals("create")) {
			//如果表中有创建者   取当前登录人
			housFaceTrialInfo.setCreator(sysUser.getId());
			housFaceTrialInfo.setCreateTime(new Date());
			influence = housFaceTrialService.insert(housFaceTrialInfo);
		} else {
			//如果表中有修改者   取当前登录人
			housFaceTrialInfo.setModifier(sysUser.getId());
			housFaceTrialInfo.setModifyTime(new Date());
			influence = housFaceTrialService.update(housFaceTrialInfo);
		}
		if (influence > 0) {
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, returnMap);
    }

    /**
     * 分页查询数据
     * @param request      页面的request
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParams   查询条件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getHousFaceTrialList.htm")
    public void getHousFaceTrialList(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "currentPage") Integer currentPage,
		@RequestParam(value = "pageSize") Integer pageSize,
  		@RequestParam(value = "searchParams", required = false) String searchParams) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		//对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)){
         	paramap = JsonUtil.parse(searchParams, Map.class);
        }
        PageHelper.startPage(currentPage, pageSize);
		List<HousFaceTrial> housFaceTrials = housFaceTrialService.getPageListByMap(paramap);
		PageInfo<HousFaceTrial> page = new PageInfo<HousFaceTrial>(housFaceTrials);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }

    /**
    * 根据id删除数据
    * @param response    页面的response
    * @param id          主键
    * @throws ServiceException
    */
    @RequestMapping("/deleteById.htm")
    public void deleteById(HttpServletResponse response, @RequestParam(value = "id") long id) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = housFaceTrialService.deleteById(id);
		if(influence > 0){
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		}else{
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		//返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }

   /**
    * @description  查询面审信息
    * @param response
    * @param request
    * @param processInstanceId
    * @throws Exception
    * @author fzc
    * @return void
    * @since  1.0.0
    */
    @RequestMapping("/getFaceAuditInfo.htm")
    public void getFaceAuditInfo(
    		HttpServletResponse response,
    		HttpServletRequest request,
  		@RequestParam(value = "processInstanceId") String processInstanceId) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
   	 	Map<String, Object> result = new HashMap<String, Object>();

   	 	HousFaceTrial housFaceTrial = housFaceTrialService.getItemInfoByProcessInstanceId(processInstanceId);
   	 	HousControlInformation housControlInformation = housControlInformationService.getItemInfoByProcessInstanceId(processInstanceId);
   	 	HousCreditInformation housCreditInformation = housCreditInformationService.getItemInfoByProcessInstanceId(processInstanceId);
   	 	HousMarriageInformation housMarriageInformation = housMarriageInformationService.getItemInfoByProcessInstanceId(processInstanceId);
   	 	Map<String,Object> borrowInfo = plBorrowServiceImpl.getborrowInfo(processInstanceId);//负数bug所在
   	    HousOritoInformation housOritoInformation = housOritoInformationService.getItemInfoByProcessInstanceId(processInstanceId);
   	 	HousPropertyInformation housPropertyInformation = housPropertyInformationService.getItemInfoByProcessInstanceId(processInstanceId);
   	 	if (housFaceTrial==null) {
   	 		Map<String, Object> returnMap1 = new HashMap<String, Object>();
//   	 		returnMap1.put("propertyAddressId", housOritoInformation.getPropertyAddressId());
//   	 		returnMap1.put("propertyAddress", housOritoInformation.getPropertyAddress());
//   	 		returnMap1.put("propertyProperties",housPropertyInformation.getPropertyProperties());
//   	 		returnMap1.put("planningPurposes", housPropertyInformation.getPlanningPurposes());
   	 	    result.put("housFaceTrial", returnMap1);
		}else{
			result.put("housFaceTrial", housFaceTrial);
		}
    	 result.put("housOritoInformation", housOritoInformation);
    	 result.put("housPropertyInformation", housPropertyInformation);
    	 result.put("housControlInformation", housControlInformation);
    	 result.put("housCreditInformation", housCreditInformation);
    	 result.put("housMarriageInformation", housMarriageInformation);
    	 result.put("borrow", borrowInfo);

    	//返回给页面
    	 returnMap.put(Constant.RESPONSE_DATA, result);
    	 returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		 returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
 		 ServletUtils.writeToResponse(response, returnMap);
    }

    /**
     * @description 保存面审信息草稿
     * @param response
     * @param request
     * @param params
     * @throws Exception
     * @author fzc
     * @return void
     * @since  1.0.0
     */
    @RequestMapping("/saveFaceAuditDraft.htm")
    public void saveFaceAuditDraft(
    		HttpServletResponse response,
    		HttpServletRequest request,
  		    @RequestParam(value = "params") String params) throws Exception{
    	FaceAuditDataBean faceAuditDataBean = new FaceAuditDataBean();
  	  //对json对象进行转换
        if (!StringUtils.isEmpty(params)){
        	faceAuditDataBean = JsonUtil.parseWithOnlyYMDDate(params, FaceAuditDataBean.class);
        }

  	 boolean flag = faceAuditServiceImpl.insertOrUpdateAuditInfo(faceAuditDataBean);
  	 Map<String, Object> returnMap = new HashMap<String, Object>();
  	 if(flag){
  		 returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
   		 returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
  	 }else{
  		 returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
   		 returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
  	 }
		 ServletUtils.writeToResponse(response, returnMap);
    }
}
