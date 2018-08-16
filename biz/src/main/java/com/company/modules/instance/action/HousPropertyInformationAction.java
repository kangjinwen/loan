package com.company.modules.instance.action;

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
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.instance.domain.HousPropertyInformation;
import com.company.modules.instance.service.HousPropertyInformationService;
import com.company.modules.system.domain.SysUser;

/**
 * User:    wulb
 * DateTime:2016-08-06 02:28:59
 * details: 房产信息,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/instance/HousPropertyInformationAction")
@Controller
public class HousPropertyInformationAction extends BaseAction {

    /**
     * 房产信息的Service
     */
	@Autowired
	private HousPropertyInformationService housPropertyInformationService;

    /**
     * 房产信息表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param housPropertyInformation	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdateHousPropertyInformation.htm")
    public void saveOrUpdateHousPropertyInformation(HttpServletRequest request, HttpServletResponse response, 
    	@RequestParam(value = "housPropertyInformation", required = false) String housPropertyInformation, @RequestParam(value = "status", required = false) String status) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		SysUser sysUser = this.getLoginUser(request);
		HousPropertyInformation housPropertyInformationInfo = new HousPropertyInformation();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(housPropertyInformation)) {
			housPropertyInformationInfo = JsonUtil.parse(housPropertyInformation, HousPropertyInformation.class);
		}
		if (status.equals("create")) {
			//如果表中有创建者   取当前登录人
			housPropertyInformationInfo.setCreator(sysUser.getId());
			housPropertyInformationInfo.setCreateTime(new Date());
			influence = housPropertyInformationService.insert(housPropertyInformationInfo);
		} else {
			//如果表中有修改者   取当前登录人
			housPropertyInformationInfo.setModifier(sysUser.getId());
			housPropertyInformationInfo.setModifyTime(new Date());
			influence = housPropertyInformationService.update(housPropertyInformationInfo);
		}
		returnMap.put("id", housPropertyInformationInfo.getId());
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
    @RequestMapping("/getHousPropertyInformationList.htm")
    public void getHousPropertyInformationList(HttpServletResponse response, HttpServletRequest request,
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
		List<HousPropertyInformation> housPropertyInformations = housPropertyInformationService.getPageListByMap(paramap);
		PageInfo<HousPropertyInformation> page = new PageInfo<HousPropertyInformation>(housPropertyInformations);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * 分页查询数据
     * @param request      页面的request
     * @param response      页面的response
     * @param processInstanceId   流程id
     * @throws Exception
     */
    @RequestMapping("/getHousPropertyInformationByProcessInstanceId.htm")
    public void getHousPropertyInformationByProcessInstanceId(HttpServletResponse response, HttpServletRequest request,
    		@RequestParam(value = "processInstanceId", required = true) String processInstanceId) throws Exception{
    	// 返回给页面的Map
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	HousPropertyInformation housPropertyInformations = housPropertyInformationService.getItemInfoByProcessInstanceId(processInstanceId);
    	returnMap.put(Constant.RESPONSE_DATA, housPropertyInformations);
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
		long influence = housPropertyInformationService.deleteById(id);
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
}
