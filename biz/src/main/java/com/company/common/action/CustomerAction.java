/**
 * @title CustomerAction.java
 * @package com.company.modules.common.action
 * @author 吴国成
 * @version V1.0
 * created 2014年12月1日
 */
package com.company.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.company.common.context.Constant;
import com.company.common.domain.Customer;
import com.company.common.utils.DateUtil;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.system.domain.SysRole;
import com.company.modules.system.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 客户管理模块
 * @version 1.0
 * @author 吴国成
 * @created 2014年12月1日 下午3:38:24
 */
@RequestMapping("/modules/common/action/CustomerAction")
@Controller
public class CustomerAction extends BaseAction {
	private Logger logger = LoggerFactory.getLogger(CustomerAction.class);
	@Autowired
	private SysRoleService sysRoleService;
	@RequestMapping("/saveOrUpdate.htm")
	public void saveOrUpdate(HttpServletResponse response, final HttpServletRequest request, final String json) {
	}
	/**
	 * 客户列表
	 * @param request
	 * @param response
	 * @param data
	 * @param start
	 * @param limit 
	 * @version 1.0
	 * @author 吴国成
	 * @created 2014年12月1日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list.htm")
	public void getCustomerList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "currentPage") Integer currentPage,
			@RequestParam(value = "pageSize") Integer pageSize,
	  		@RequestParam(value = "searchParams", required = false) String searchParams){
		logger.info("开始执行     CustomerAction-----------getCustomerList()");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		//对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)){
         	paramap = JsonUtil.parse(searchParams, Map.class);
        }
        PageHelper.startPage(currentPage, pageSize);
        List<Map<String,Object>> list = null;
		PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(list);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
		
	}


	
	/**
	 * 参数映射 没有使用 暂时保留。
	 * @param m
	 * @return 
	 * @version 1.0
	 * @author 吴国成
	 * @created 2014年12月3日
	 */
	private Customer getCustomerInfo(Map<String, Object> m) {
		Customer customer = new Customer();
		customer.setName(String.valueOf(m.get("name")));
		if(!StringUtils.isEmpty(m.get("arealevel2"))){
			customer.setAreaId(Long.valueOf(String.valueOf(m.get("arealevel2"))));
		}
		customer.setBirthday(DateUtil.getDate(String.valueOf(m.get("birthday")),DateUtil.DATE_PATTERN));
		customer.setCertNumber(String.valueOf(m.get("certNumber")));
		customer.setCertType(Byte.valueOf(String.valueOf(m.get("certType"))));
		customer.setCompany(String.valueOf(m.get("company")));
		customer.setCompanyAddr(String.valueOf(m.get("companyAddr")));
		customer.setCompanyPhone(String.valueOf(m.get("companyPhone")));
		if(!StringUtils.isEmpty(m.get("companyPro"))){
			customer.setCompanyPro(Byte.valueOf(String.valueOf(m.get("companyPro"))));
		}
		if(!StringUtils.isEmpty(m.get("creditScore"))){
			customer.setCreditScore(Long.valueOf(String.valueOf(m.get("creditScore"))));
		}
		if(!StringUtils.isEmpty(m.get("education"))){
			customer.setEducation(Byte.valueOf(String.valueOf(m.get("education"))));
		}
		customer.setEmail(String.valueOf(m.get("email")));
		customer.setFixedPhone(String.valueOf(m.get("fixedPhone")));
		customer.setHouseholdAddress(String.valueOf(m.get("householdAddress")));
		customer.setLiveAddress(String.valueOf(m.get("liveAddress")));
		if(!StringUtils.isEmpty(m.get("marryStatus"))){
			customer.setMarryStatus(Byte.valueOf(String.valueOf(m.get("marryStatus"))));
		}
		customer.setMobile(String.valueOf(m.get("mobile")));
		customer.setNationality(String.valueOf(m.get("nationality")));
		customer.setNativePlace(String.valueOf(m.get("nativePlace")));
		customer.setPosition(String.valueOf(m.get("position")));
		customer.setRemark(String.valueOf(m.get("remark")));
		customer.setSex(Byte.valueOf(String.valueOf(m.get("sex"))));
		customer.setStatus(Byte.valueOf(String.valueOf(m.get("status"))));
		if(!StringUtils.isEmpty(m.get("type"))){
			customer.setType(Byte.valueOf(String.valueOf(m.get("type"))));
		}
		customer.setVocation(String.valueOf(m.get("vocation")));
		if(!StringUtils.isEmpty(m.get("workLife"))){
			customer.setWorkLife(Byte.valueOf(String.valueOf(m.get("workLife"))));
		}
		if(!StringUtils.isEmpty(m.get("workStatus"))){
			customer.setWorkStatus(Byte.valueOf(String.valueOf(m.get("workStatus"))));
		}
		if(!StringUtils.isEmpty(m.get("yearsIncome"))){
			customer.setYearsIncome(Byte.valueOf(String.valueOf(m.get("yearsIncome"))));
		}
		return customer;
	}
	
	/**
	 * 将json转成map
	 * @param jsonStr
	 * @return 
	 * @version 1.0
	 * @author 吴国成
	 * @created 2014年12月4日
	 */
    private static Map<String, Object> parseJSON2Map(String jsonStr){
        Map<String, Object> map = new HashMap<String, Object>();
        //最外层解析
        JSONObject json = JSONObject.parseObject(jsonStr);
        for(Object k : json.keySet()){
            Object v = json.get(k); 
            //如果内层还是数组的话，继续解析
            if(v instanceof JSONArray){
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
                Iterator<Object> it = ((JSONArray)v).iterator();
                while(it.hasNext()){
                    Object json2 = it.next();
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), StringUtils.isEmpty(v)?null:v);
            }
        }
        return map;
    }
    /**
     * 是否是團隊經理
     * @description
     * @param request
     * @param response
     * @author wgc
     * @return void
     * @since  1.0.0
     */
    @RequestMapping("/isTeamManager.htm")
    public void getTeamManagerPermissions(HttpServletRequest request ,HttpServletResponse response){
    	Map<String, Object> responseMap = new HashMap<String, Object>();
    	try {
			Long roleId = (Long) request.getSession().getAttribute(Constant.ROLEID);
			SysRole sysRole = sysRoleService.getRoleById(roleId);
			
			if("teamManager".equals(sysRole.getNid())){//如果是團隊經理
				responseMap.put("isTeamManager", true);
			}else{
				responseMap.put("isTeamManager", false);
			}
			ServletUtils.writeToResponse(response, responseMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @RequestMapping("/getCurrentRole.htm")
    public void getCurrentRole(HttpServletRequest request ,HttpServletResponse response){
    	Map<String, Object> responseMap = new HashMap<String, Object>();
    	try {
			Long roleId = (Long) request.getSession().getAttribute(Constant.ROLEID);
			SysRole sysRole = sysRoleService.getRoleById(roleId);
			responseMap.put("user",sysRole );
			ServletUtils.writeToResponse(response, responseMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
