package com.company.modules.system.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.utils.StringUtil;
import com.company.common.web.action.BaseAction;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.CheckBoxTree;
import com.company.modules.system.domain.SysOffice;
import com.company.modules.system.domain.SysRole;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysDictService;
import com.company.modules.system.service.SysOfficeService;
import com.company.modules.system.service.SysUserService;

/**
 * 
 * 组织机构action
 * @version 1.0
 * @author 吴国成
 * @created 2014年9月23日 下午2:06:32
 */
@Controller
public class SysOfficeAction extends BaseAction {
	private static final int DEP_2 = 2; //营业部

	private static final Logger logger = LoggerFactory.getLogger(SysOfficeAction.class);

	/** 参数封装map */
	private Map<String, Object> map = new HashMap<String, Object>();
	/** 组织结构service */
	@Autowired
	private SysOfficeService sysOfficeService;
	/** 数据字典service */
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private SysUserService sysUserService;

	
	/**
	 * 查询所有机构                
	 * @description
	 * @param request
	 * @param response
	 * @author fzc
	 * @return void
	 * @throws ServiceException 
	 * @since  1.0.0
	 */
	@RequestMapping("/modules/system/fetchAllOffice.htm")
	public void fetchAllOffice(final HttpServletRequest request,HttpServletResponse response) throws ServiceException{
		Map<String,Object> responseMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = sysOfficeService.getOfficeTreeList();
		if(list.size()>0){
			responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			responseMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
			responseMap.put(Constant.RESPONSE_DATA, list);
		}else{
			responseMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			responseMap.put(Constant.RESPONSE_CODE_MSG,Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, responseMap);
	}
	
	/**
	 * 增加机构、修改机构        
	 * @param request
	 * @param response
	 * @param office
	 * @param status 
	 * @version 1.0
	 * @author 吴国成
	 * @throws ServiceException 
	 * @created 2014年10月23日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/modules/system/addoffice.htm")
	public void addOffice(HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam(value="office",required=false) String office,
			@RequestParam(value="status",required=false) String status) throws ServiceException{
		Map<String,Object> responseMap = new HashMap<String, Object>();
		Map<String,Object> paramap = JsonUtil.parse(office, Map.class);
		
		SysOffice sysOffice = JsonUtil.parse(office, SysOffice.class);
		//获取当前登录用户信息 
		String  userName = this.getLoginUser(request).getUserName();
		String id = "";
		String parentId = "0";
		if(null == sysOffice.getParentId()){
			id = "11";
		}else{
			parentId = sysOffice.getParentId();
		}
					
		if("create".equals(status)){//增加机构
			//下一级ID处理
			StringBuilder sb = new StringBuilder();
			/*Map<String, Object> m = new HashMap<String, Object>();
			m.put("parentId", parentId);*/
			String officeId = sysOfficeService.getIdByParentId(parentId);
			if(StringUtils.isEmpty(officeId)){
				id=String.valueOf(sb.append(parentId).append("01"));
			}else{
				id = String.valueOf(sb.append(officeId));
			}					
			
			sysOffice.setAddTime(new Date());
			sysOffice.setAddUser(userName);
			sysOffice.setParentId(parentId);//上级
			sysOffice.setId(id);//主键，这个自定义			
			
			sysOfficeService.officeAdd(sysOffice);
			responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			responseMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		}else if("update".equals(status)){//修改机构
			paramap.remove("parentId");
			paramap.put("updateTime", new Date());
			paramap.put("updateUser", userName);
			Boolean isTrue = sysOfficeService.updateSysOfficeById(paramap);
			if(isTrue){
				responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				responseMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
			}			
		}else{//返回信息
			responseMap.put(Constant.RESPONSE_CODE,Constant.FAIL_CODE_VALUE);
			responseMap.put(Constant.RESPONSE_CODE_MSG,Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, responseMap);
	}
	
	/**
	 * 编辑机构时加载出原有机构数据          
	 * @param request
	 * @param response
	 * @author ccf
	 * @throws Exception 
	 * @created 2015年12月30日
	 */
	@RequestMapping(value="/modules/system/getOfficeInfoById.htm")
	public void getOfficeInfoById(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="id",required=true) Long id) throws Exception{	
		Map<String,Object> responseMap = new HashMap<String, Object>();
		if (id == 11) {// 根节点
			responseMap.put("data", sysOfficeService.getOfficeById(id));
		} else {
			responseMap.put("data", sysOfficeService.getOfficeInfoById(id));
		}
		responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		responseMap.put(Constant.RESPONSE_CODE_MSG,Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, responseMap);
	}
	
	/**
	 * 删除组织机构
	 * 
	 * @throws Exception 异常
	 */
	@RequestMapping(value = "/modules/system/general/officeDelete.htm")
	public void officeDelete(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		Map<String,Object> responseMap = new HashMap<String, Object>();
		try {
			sysOfficeService.deleteOfficeAll(id);
			responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			responseMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} catch (ServiceException e) {
			e.printStackTrace();
			responseMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			responseMap.put(Constant.RESPONSE_CODE_MSG, "请稍后在试");
		}
		ServletUtils.writeToResponse(response, responseMap);
	}
	
	/**
	 * 获取带checkbox组织树
	 * @param request
	 * @param response 
	 * @version 1.0
	 * @author 吴国成
	 * @throws ServiceException 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @created 2014年12月31日
	 */
	@RequestMapping(value="/modules/system/checkboxoffices.htm")
	public void getCheckBoxOffices(HttpServletRequest request, HttpServletResponse response) throws Exception{	
		Map<String,Object> responseMap = new HashMap<String, Object>();
		String id = request.getParameter("id");
		//String id = null;
		String[] officeOverIds = null;
	    if(StringUtil.isNoneBlank(id)){//如果ID不为空，查询出该用户的管辖区域。
			SysUser sysUser = sysUserService.getUserById(Long.parseLong(id));
			String officeOver = sysUser.getOfficeOver();
			if(null != officeOver){
				officeOverIds = officeOver.split(";");
		    }		
	    }
	    
		String officeId = this.getLoginUser(request).getOfficeId();
		//查询所有的组织
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("isDelete", 0);
		//param.put("id", officeId);
		List<SysOffice> sysOfficesList = sysOfficeService.getListByOfficeId(param);
		
		responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		responseMap.put(Constant.RESPONSE_DATA, CheckBoxTree.TreeList(sysOfficesList, "id", "name", "parentId",null == officeOverIds?"":officeOverIds[0]));
		//将树返回到页面
		ServletUtils.writeToResponse(response,responseMap);
	}
	
    @RequestMapping("/modules/system/getProjectBelongInfo.htm")
    public void getProjectBelongInfo(
    		HttpServletResponse response, 
    		HttpServletRequest request) throws Exception{
    	SysRole role = getRoleForLoginUser(request);
    	SysUser user = getSysUser();
    	Map<String,Object> projectBelongInfo = sysOfficeService.getProjectBelongInfo(role.getNid(),user.getId());
    	
    	Map<String,Object> responseMap = new HashMap<String, Object>();
    	responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
    	responseMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		responseMap.put(Constant.RESPONSE_DATA, projectBelongInfo);
		//返回到页面
		ServletUtils.writeToResponse(response,responseMap);
    }
    
    /**
     * @description 根据type 查询组织机构
     * @param response
     * @param request
     * @param type
     * @throws Exception
     * @author fzc
     * @return void
     * @since  1.0.0
     */
    @RequestMapping("/modules/system/getBusinessDepartment.htm")
    public void getBusinessDepartment(
    		HttpServletResponse response, 
    		HttpServletRequest request,
    		@RequestParam("type") String type) throws Exception{
    	Map<String,Object> param = new HashMap<String,Object>();
    	param.put("type", type);
    	List<Map<String, Object>>  businessDepartments = sysOfficeService.getMapListByMap(param);
    	
    	Map<String,Object> responseMap = new HashMap<String, Object>();
    	responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
    	responseMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		responseMap.put(Constant.RESPONSE_DATA, businessDepartments);
		//返回到页面
		ServletUtils.writeToResponse(response,responseMap);
    }
    
    /**
     * 在本单位或者上级营业厅范围下查找指定类型的机构
     * @description
     * @param response
     * @param request
     * @param type
     * @throws Exception
     * @author huy
     * @return void
     * @since  1.0.0
     */
    @RequestMapping("/modules/system/getOfficeListByTypeAndParentId.htm")
    public void getOfficeListByTypeAndParentId(
    		HttpServletResponse response, 
    		HttpServletRequest request,
    		@RequestParam(value="type",required=true) String type) throws Exception{
    	SysUser sysUser = getLoginUser(request);
    	String officeId = sysUser.getOfficeId();
    	Map<String,Object> param = new HashMap<String,Object>();
    	param.put("type", type);
    	param.put("officeId", officeId);
    	//查找所归属的营业厅
    	SysOffice sysOffice = sysOfficeService.getBusinessHallByOrganizationId(officeId); 
    	if(sysOffice != null){
    		param.put("parentOfficeId", sysOffice.getId());
    	}
    	List<Map<String, Object>>  businessDepartments = sysOfficeService.getOfficeListByTypeAndParentId(param);
    	Map<String,Object> responseMap = new HashMap<String, Object>();
    	responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
    	responseMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		responseMap.put(Constant.RESPONSE_DATA, businessDepartments);
		//返回到页面
		ServletUtils.writeToResponse(response,responseMap);
    }
    
    @RequestMapping("/modules/system/getOrgListByParentId.htm")
    public void getOrgListByTypeAndParentId(
    		HttpServletResponse response, 
    		HttpServletRequest request) throws Exception{
    	SysUser sysUser = getLoginUser(request);
    	String officeId = sysUser.getOfficeId();
    	Map<String,Object> param = new HashMap<String,Object>();
    	//暂时写死,后续根据规则获取
    	param.put("id", "1105");
//    	List<Map<String, Object>>  businessDepartments = sysOfficeService.getOfficeListByTypeAndParentId(param);
    	
    	List<SysOffice> sysOfficesList = sysOfficeService.getListByOfficeId(param);
    	
    	Map<String,Object> responseMap = new HashMap<String, Object>();
    	responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
    	responseMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		responseMap.put(Constant.RESPONSE_DATA, sysOfficesList);
//		responseMap.put(Constant.RESPONSE_DATA, CheckBoxTree.TreeList(sysOfficesList, "id", "name", "parentId",""));
		
		//返回到页面
		ServletUtils.writeToResponse(response,responseMap);
    }
    
}
