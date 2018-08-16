package com.company.modules.advance.action;

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
import com.company.modules.advance.domain.HousAdvanceNotarize;
import com.company.modules.advance.service.HousAdvanceNotarizeService;
import com.company.modules.system.domain.SysUser;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;

/**
 * User:    wulb
 * DateTime:2016-09-21 09:24:32
 * details:垫资公证信息,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/advance/HousAdvanceNotarizeAction")
@Controller
public class HousAdvanceNotarizeAction extends BaseAction {

    /**
     * 垫资公证信息的Service
     */
	@Autowired
	private HousAdvanceNotarizeService housAdvanceNotarizeService;

    /**
     * 垫资公证信息表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param housAdvanceNotarize	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdateHousAdvanceNotarize.htm")
    public void saveOrUpdateHousAdvanceNotarize(HttpServletRequest request, HttpServletResponse response, 
    	@RequestParam(value = "housAdvanceNotarize", required = false) String housAdvanceNotarize, @RequestParam(value = "status", required = false) String status) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		SysUser sysUser = this.getLoginUser(request);
		HousAdvanceNotarize housAdvanceNotarizeInfo = new HousAdvanceNotarize();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(housAdvanceNotarize)) {
			housAdvanceNotarizeInfo = JsonUtil.parse(housAdvanceNotarize, HousAdvanceNotarize.class);
		}
		if (status.equals("create")) {
			//如果表中有创建者   取当前登录人
			//housAdvanceNotarizeInfo.setCreator(sysUser.getId());
			housAdvanceNotarizeInfo.setCreateTime(new Date());
			influence = housAdvanceNotarizeService.insert(housAdvanceNotarizeInfo);
		} else {
			//如果表中有修改者   取当前登录人
			//housAdvanceNotarizeInfo.setModifier(sysUser.getId());
			housAdvanceNotarizeInfo.setModifyTime(new Date());
			influence = housAdvanceNotarizeService.update(housAdvanceNotarizeInfo);
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
    @RequestMapping("/getHousAdvanceNotarizeList.htm")
    public void getHousAdvanceNotarizeList(HttpServletResponse response, HttpServletRequest request,
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
		List<HousAdvanceNotarize> housAdvanceNotarizes = housAdvanceNotarizeService.getPageListByMap(paramap);
		PageInfo<HousAdvanceNotarize> page = new PageInfo<HousAdvanceNotarize>(housAdvanceNotarizes);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    @RequestMapping("/getHousAdvanceNotarizeInfo.htm")
    public void getHousAdvanceApplyInfo(
    		HttpServletResponse response,
    		HttpServletRequest request,
    		@RequestParam(value = "processInstanceId") long processInstanceId,
    		@RequestParam(value = "projectId") long projectId) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	Map<String, Object> housAdvanceNotarizeInfo = new HashMap<String, Object>();
    	Map<String, Object> housAdvanceNotarize = new HashMap<String, Object>();
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	housAdvanceNotarize = housAdvanceNotarizeService.getHousAdvanceNotarize(processInstanceId);
    	
    	housAdvanceNotarizeInfo = housAdvanceNotarizeService.getHousAdvanceNotarizeInfo(projectId);    	
    	
    	if(housAdvanceNotarizeInfo != null){
    		housAdvanceNotarizeInfo.put("advanceApplyOperator", getLoginUser(request).getName());//垫资申请操作人为当前用户
    	}
    	result.put("housAdvanceApplyInfo", housAdvanceNotarizeInfo);
    	result.put("housAdvanceApply", housAdvanceNotarize);
    	
		if(housAdvanceNotarizeInfo == null  || housAdvanceNotarizeInfo == null){
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}else{
			returnMap.put(Constant.RESPONSE_DATA,result);
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		}
		//返回给页面
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
		long influence = housAdvanceNotarizeService.deleteById(id);
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
