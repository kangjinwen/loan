package com.company.modules.instance.action;

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
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.domain.SysUser;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.instance.service.HousBorrowingBasicsService;

/**
 * User:    wulb
 * DateTime:2016-08-06 02:36:08
 * details: 借款基本信息,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/instance/HousBorrowingBasicsAction")
@Controller
public class HousBorrowingBasicsAction extends BaseAction {

    /**
     * 借款基本信息的Service
     */
	@Autowired
	private HousBorrowingBasicsService housBorrowingBasicsService;

    /**
     * 借款基本信息表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param housBorrowingBasics	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdateHousBorrowingBasics.htm")
    public void saveOrUpdateHousBorrowingBasics(HttpServletRequest request, HttpServletResponse response, 
    	@RequestParam(value = "housBorrowingBasics", required = false) String housBorrowingBasics, @RequestParam(value = "status", required = false) String status) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		SysUser sysUser = this.getLoginUser(request);
		HousBorrowingBasics housBorrowingBasicsInfo = new HousBorrowingBasics();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(housBorrowingBasics)) {
			housBorrowingBasicsInfo = JsonUtil.parse(housBorrowingBasics, HousBorrowingBasics.class);
		}
		if (status.equals("create")) {
			//如果表中有创建者   取当前登录人
			housBorrowingBasicsInfo.setCreator(sysUser.getId());
			housBorrowingBasicsInfo.setCreateTime(new Date());
			influence = housBorrowingBasicsService.insert(housBorrowingBasicsInfo);
		} else {
			//如果表中有修改者   取当前登录人
			housBorrowingBasicsInfo.setModifier(sysUser.getId());
			housBorrowingBasicsInfo.setModifyTime(new Date());
			influence = housBorrowingBasicsService.update(housBorrowingBasicsInfo);
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
    @RequestMapping("/getHousBorrowingBasicsList.htm")
    public void getHousBorrowingBasicsList(HttpServletResponse response, HttpServletRequest request,
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
		List<HousBorrowingBasics> housBorrowingBasicss = housBorrowingBasicsService.getPageListByMap(paramap);
		PageInfo<HousBorrowingBasics> page = new PageInfo<HousBorrowingBasics>(housBorrowingBasicss);
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
    @RequestMapping("/getHousBorrowingBasicsByProcessInstanceId.htm")
    public void getHousBorrowingBasicsByProcessInstanceId(HttpServletResponse response, HttpServletRequest request,
    		@RequestParam(value = "processInstanceId", required = true) String processInstanceId) throws Exception{
    	// 返回给页面的Map
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	HousBorrowingBasics housBorrowingBasicss = housBorrowingBasicsService.getItemInfoByProcessInstanceId(processInstanceId);
    	returnMap.put(Constant.RESPONSE_DATA, housBorrowingBasicss);
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
		long influence = housBorrowingBasicsService.deleteById(id);
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
