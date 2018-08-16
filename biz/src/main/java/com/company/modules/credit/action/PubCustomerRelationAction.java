package com.company.modules.credit.action;

import java.util.ArrayList;
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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.credit.domain.PubCustomerRelation;
import com.company.modules.credit.service.PubCustomerRelationService;
import com.company.modules.system.domain.SysUser;

/**
 * 
 * DateTime:2016-12-12 10:29:58
 * details: 联系明细管理,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/credit/PubCustomerRelationAction")
@Controller
public class PubCustomerRelationAction extends BaseAction {
	
	private static final Logger log = LoggerFactory.getLogger(PubCustomerRelationAction.class);

    /**
     * 联系明细管理的Service
     */
	@Autowired
	private PubCustomerRelationService customerRelationService;

	/**
	 * 客户联系列表
	 * @param request
	 * @param response
	 * @param data 
	 * @version 1.0
	 * @created 2016年12月12日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getCustomerList.htm")
	public void getRepaymentList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "currentPage") Integer currentPage,
			@RequestParam(value = "pageSize") Integer pageSize,
	  		@RequestParam(value = "searchParams", required = false) String searchParams) throws Exception {
		// 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		//对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)){
         	paramap = JsonUtil.parse(searchParams, Map.class);
        }
        SysUser user = getSysUser();
        PageHelper.startPage(currentPage, pageSize);
		result = customerRelationService.getCustomerRelationList(paramap);
        PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(result);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
	}
	
	
	/**
	 * 增加客户联系记录
	 * @param request
	 * @param response
	 * @param data 
	 * @version 1.0
	 * @created 2016年12月12日
	 */
	@RequestMapping("/save.htm")
	public void saveCustomerRelation(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="customerRelation",required=false) String customerRelation){
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			SysUser sysUser = this.getLoginUser(request);
			PubCustomerRelation pubCustomerRelation = new PubCustomerRelation();
			// 对json对象进行转换
			if (!StringUtils.isEmpty(customerRelation)) {
				pubCustomerRelation = JsonUtil.parse(customerRelation, PubCustomerRelation.class);
			}
			pubCustomerRelation.setCreateTime(new Date());
			pubCustomerRelation.setCreator(sysUser.getId());
			pubCustomerRelation.setUserName(sysUser.getName());
			pubCustomerRelation.setIsDelete((byte)0);
			boolean isSuccess = customerRelationService.addCustomerRelation(pubCustomerRelation);
			if(isSuccess){
				res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				res.put(Constant.RESPONSE_CODE_MSG, "操作成功");
			}else{
				res.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				res.put(Constant.RESPONSE_CODE_MSG, "操作失败");
			}
			ServletUtils.writeToResponse(response, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据客户ID获得联系列表
	 * @param request
	 * @param response
	 * @param id 
	 * @version 1.0
	 * @created 2016年12月12日
	 */
	@RequestMapping("/list.htm")
	public void getCustomerRelationList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "currentPage") Integer currentPage,
			@RequestParam(value = "pageSize") Integer pageSize,
			@RequestParam(value="customerId",required=false) String customerId){
		// 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		try {
		    SysUser user = getSysUser();
	        PageHelper.startPage(currentPage, pageSize);
	        paramap.put("customerId", customerId);
	        result = customerRelationService.getCustomerRelationDetail(paramap);
	        PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(result);
			returnMap.put(Constant.RESPONSE_DATA, page.getList());
			returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
			// 返回给页面
			ServletUtils.writeToResponse(response, returnMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
