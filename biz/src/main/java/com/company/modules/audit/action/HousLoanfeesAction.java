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
import com.company.common.domain.PlBorrowRequirement;
import com.company.common.service.PlBorrowRequirementService;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.audit.domain.HousLoanfees;
import com.company.modules.audit.service.HousLoanfeesService;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.domain.SysUser;

/**
 * User:    fzc
 * DateTime:2016-08-17 03:54:15
 * details: 返费签单/代收服务费,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/audit/HousLoanfeesAction")
@Controller
public class HousLoanfeesAction extends BaseAction {

    /**
     * 返费签单/代收服务费的Service
     */
	@Autowired
	private HousLoanfeesService housLoanfeesService;
	
	@Autowired
	private PlBorrowRequirementService plBorrowRequirementServiceImpl;

    /**
     * 返费签单/代收服务费表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param housLoanfees	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdateHousLoanfees.htm")
    public void saveOrUpdateHousLoanfees(HttpServletRequest request, HttpServletResponse response, 
    	@RequestParam(value = "housLoanfees", required = false) String housLoanfees, @RequestParam(value = "status", required = false) String status) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		SysUser sysUser = this.getLoginUser(request);
		HousLoanfees housLoanfeesInfo = new HousLoanfees();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(housLoanfees)) {
			housLoanfeesInfo = JsonUtil.parse(housLoanfees, HousLoanfees.class);
		}
		if (status.equals("create")) {
			//如果表中有创建者   取当前登录人
			housLoanfeesInfo.setCreator(sysUser.getId());
			housLoanfeesInfo.setCreateTime(new Date());
			influence = housLoanfeesService.insert(housLoanfeesInfo);
		} else {
			//如果表中有修改者   取当前登录人
			housLoanfeesInfo.setModifier(sysUser.getId());
			housLoanfeesInfo.setModifyTime(new Date());
			influence = housLoanfeesService.update(housLoanfeesInfo);
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
    @RequestMapping("/getHousLoanfeesList.htm")
    public void getHousLoanfeesList(HttpServletResponse response, HttpServletRequest request,
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
		List<HousLoanfees> housLoanfeess = housLoanfeesService.getPageListByMap(paramap);
		PageInfo<HousLoanfees> page = new PageInfo<HousLoanfees>(housLoanfeess);
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
		long influence = housLoanfeesService.deleteById(id);
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
     * 查询返费签单和代收服务费
     * @param response
     * @param request
     * @param processInstanceId
     * @throws Exception
     */
    @RequestMapping("/getHousLoanfeesInfo.htm")
    public void getHousLoanfeesInfo(
    		HttpServletResponse response, 
    		HttpServletRequest request,
  		    @RequestParam(value = "processInstanceId") String processInstanceId) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	
    	HousLoanfees housLoanfees = housLoanfeesService.getItemInfoByProcessInstanceId(processInstanceId);
    	PlBorrowRequirement borrowRequirement =null;
    	if(housLoanfees== null){
    		borrowRequirement = plBorrowRequirementServiceImpl.getInfoByProcessInstanceId(processInstanceId);
    		if(borrowRequirement != null ){
    			returnMap.put(Constant.RESPONSE_DATA, borrowRequirement);
    		}
    	}else{
    		returnMap.put(Constant.RESPONSE_DATA, housLoanfees);
    	}
    	
    	if(housLoanfees == null && borrowRequirement == null){
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}else{
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		}
		//返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
}
