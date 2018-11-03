package com.company.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.company.common.domain.PubCustomer;
import com.company.common.service.PubCustomerService;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.utils.ValidateUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.system.domain.SysUserRole;
import com.company.modules.system.service.SysUserRoleService;

/**
 * User:    huy
 * DateTime:2016-12-08 17:53:10
 * details: 客户管理,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/common/PubCustomerAction")
@Controller
public class PubCustomerAction extends BaseAction {

    /**
     * 客户管理的Service
     */
	@Autowired
	private PubCustomerService pubCustomerService;
	@Autowired
	private SysUserRoleService sysUserRoleService;


	private String checkCustomerInfoValid(PubCustomer pubCustomer) throws Exception {

		String errInfo = "";

		if (ValidateUtils.isEmpty(pubCustomer.getCertNumber())) {
			errInfo = "证件号码不允许为空";
			return errInfo;
		}
		if (!ValidateUtils.isInteger(pubCustomer.getEducation())) {
			errInfo = "教育程度不允许为空";
			return errInfo;
		}
		if (!ValidateUtils.isInteger(pubCustomer.getMarryStatus())) {
			errInfo = "婚姻状况不允许为空";
			return errInfo;
		}
		if (ValidateUtils.isEmpty(pubCustomer.getHouseholdAddress())) {
			errInfo = "户口地址不允许为空";
			return errInfo;
		}
		if (ValidateUtils.isEmpty(pubCustomer.getLiveAddress())) {
			errInfo = "现居住地不允许为空";
			return errInfo;
		}
		if (ValidateUtils.isEmpty(pubCustomer.getMobile())) {
			errInfo = "联系号码不允许为空";
			return errInfo;
		}
		if (ValidateUtils.isEmpty(pubCustomer.getCompany())) {
			errInfo = "公司名称不允许为空";
			return errInfo;
		}
		if (pubCustomer.getMarryStatus() == 1) {

			if (ValidateUtils.isEmpty(pubCustomer.getSpouseName())) {
				errInfo = "配偶名称不允许为空";
				return errInfo;
			}
			if (ValidateUtils.isEmpty(pubCustomer.getSpouseIdNumber())) {
				errInfo = "配偶证件号不允许为空";
				return errInfo;
			}
			if (ValidateUtils.isEmpty(pubCustomer.getSpouseMobile())) {
				errInfo = "配偶联系电话不允许为空";
				return errInfo;
			}
		}
		if (ValidateUtils.isEmpty(pubCustomer.getFamiliesName())) {
			errInfo = "家庭成员不允许为空";
			return errInfo;
		}
		if (ValidateUtils.isEmpty(pubCustomer.getFamiliesMobile())) {
			errInfo = "家庭成员联系电话不允许为空";
			return errInfo;
		}
		if (ValidateUtils.isEmpty(pubCustomer.getFamiliesRelationship())) {
			errInfo = "家庭成员与借款人关系不允许为空";
			return errInfo;
		}
		return errInfo;
	}

    /**
     * 客户管理表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param pubCustomer	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdatePubCustomer.htm")
    public void saveOrUpdatePubCustomer(HttpServletRequest request, HttpServletResponse response, 
    	@RequestParam(value = "pubCustomer", required = false) String pubCustomer) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		SysUser sysUser = this.getLoginUser(request);
		PubCustomer pubCustomerInfo = new PubCustomer();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(pubCustomer)) {
			pubCustomerInfo = JsonUtil.parse(pubCustomer, PubCustomer.class);
		}
		String errInfo = checkCustomerInfoValid(pubCustomerInfo);
		if (errInfo.length() == 0) {
			if (pubCustomerInfo.getId() == null) {
				//如果表中有创建者   取当前登录人
				pubCustomerInfo.setLoans(0);
				pubCustomerInfo.setRefusings(0);
				pubCustomerInfo.setCreateTime(new Date());
				pubCustomerInfo.setStatus((byte) 0);
				pubCustomerInfo.setCertType((byte) 1);
				pubCustomerInfo.setCreator(sysUser.getId().intValue());
				influence = pubCustomerService.insert(pubCustomerInfo);
			} else {
				//如果表中有修改者   取当前登录人
				influence = pubCustomerService.update(pubCustomerInfo);
			}
			if (influence > 0) {
				returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
			} else {
				returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
			}
		} else {
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, errInfo);
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
	@RequestMapping("/getPubCustomerList.htm")
    public void getPubCustomerList(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "currentPage") Integer currentPage,
		@RequestParam(value = "pageSize") Integer pageSize,
  		@RequestParam(value = "searchParams", required = false) String searchParams) throws Exception{
		// 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		Map<String, Object> usermap = new HashMap<>();
		SysUser loginUser = getLoginUser(request);
		//对json对象进行转换
		if (!StringUtils.isEmpty(searchParams)) {
			paramap = JsonUtil.parse(searchParams, Map.class);
		}
		usermap.put("userId", loginUser.getId());
		usermap.put("nid", "system");
		SysUserRole sr = sysUserRoleService.getSysUserRoleByNid(usermap);
		if (sr != null) {
			PageHelper.startPage(currentPage, pageSize);
			List<Map<String,Object>> pubCustomers = pubCustomerService.getCustomerListByMap(paramap);
			PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(pubCustomers);
			returnMap.put(Constant.RESPONSE_DATA, page.getList());
			returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {
			paramap.put("creator", loginUser.getId());
			PageHelper.startPage(currentPage, pageSize);
			List<Map<String,Object>> pubCustomers = pubCustomerService.getCustomerListByCreator(paramap);
			PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(pubCustomers);
			returnMap.put(Constant.RESPONSE_DATA, page.getList());
			returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		}
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
    * 根据id删除数据
    * @param response    页面的response
    * @param id          主键
    * @throws Exception
    */
    @RequestMapping("/deleteById.htm")
    public void deleteById(HttpServletResponse response, @RequestParam(value = "id") long id) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = pubCustomerService.deleteById(id);
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
    
    @SuppressWarnings("unchecked")
    @RequestMapping("/getPubCustomerByProcessInstanceId.htm")
    public void getPubCustomerByProcessInstanceId(
    		HttpServletResponse response, 
    		HttpServletRequest request,
    		@RequestParam(value = "processInstanceId") String processInstanceId,
    		@RequestParam(value = "customerId") String customerId) throws Exception{
            // 返回给页面的Map
    		Map<String, Object> returnMap = new HashMap<String, Object>();
    		Map<String, Object> paramap = new HashMap<>();
    		PubCustomer pubCustomer = null;
    		if(StringUtils.isEmpty(processInstanceId)){
    			pubCustomer = pubCustomerService.getItemInfoById(Long.parseLong(customerId));
    		}else{
    			pubCustomer = pubCustomerService.getCustomerByProcessInstanceId(processInstanceId);	
    		}
    		returnMap.put(Constant.RESPONSE_DATA, pubCustomer);
    		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
    		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
    		// 返回给页面
    		ServletUtils.writeToResponse(response, returnMap);
        }
    
}
