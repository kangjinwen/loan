package com.company.modules.advance.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.advance.domain.HousAdvanceApply;
import com.company.modules.advance.service.HousAdvanceApplyService;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.domain.SysUser;

/**
 * User:    gaoshan
 * DateTime:2016-09-14 11:54:13
 * details: 垫资申请,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/advance/HousAdvanceApplyAction")
@Controller
public class HousAdvanceApplyAction extends BaseAction {

    /**
     * 垫资申请的Service
     */
	@Autowired
	private HousAdvanceApplyService housAdvanceApplyService;

    /**
     * 垫资申请表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param housAdvanceApply	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdateHousAdvanceApply.htm")
    public void saveOrUpdateHousAdvanceApply(HttpServletRequest request, HttpServletResponse response, 
    	@RequestParam(value = "housAdvanceApply", required = false) String housAdvanceApply, @RequestParam(value = "status", required = false) String status) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		SysUser sysUser = this.getLoginUser(request);
		HousAdvanceApply housAdvanceApplyInfo = new HousAdvanceApply();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(housAdvanceApply)) {
			housAdvanceApplyInfo = JsonUtil.parse(housAdvanceApply, HousAdvanceApply.class);
		}
		if (status.equals("create")) {
			//如果表中有创建者   取当前登录人
			//housAdvanceApplyInfo.setCreator(sysUser.getId());
			housAdvanceApplyInfo.setCreateTime(new Date());
			//influence = housAdvanceApplyService.insert(housAdvanceApplyInfo);
		} else {
			//如果表中有修改者   取当前登录人
			//housAdvanceApplyInfo.setModifier(sysUser.getId());
			housAdvanceApplyInfo.setModifyTime(new Date());
			//influence = housAdvanceApplyService.update(housAdvanceApplyInfo);
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
    @RequestMapping("/getHousAdvanceApplyList.htm")
    public void getHousAdvanceApplyList(HttpServletResponse response, HttpServletRequest request,
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
		//List<HousAdvanceApply> housAdvanceApplys = housAdvanceApplyService.getPageListByMap(paramap);
		//PageInfo<HousAdvanceApply> page = new PageInfo<HousAdvanceApply>(housAdvanceApplys);
		//returnMap.put(Constant.RESPONSE_DATA, page.getList());
		//returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
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
		long influence = housAdvanceApplyService.deleteById(id);
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
    
    @RequestMapping("/getHousAdvanceApplyInfo.htm")
    public void getHousAdvanceApplyInfo(
    		HttpServletResponse response,
    		HttpServletRequest request,
    		@RequestParam(value = "processInstanceId") long processInstanceId,
    		@RequestParam(value = "projectId") long projectId) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	Map<String, Object> housAdvanceApplyInfo = new HashMap<String, Object>();
    	Map<String, Object> housAdvanceApply = new HashMap<String, Object>();
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	housAdvanceApply = housAdvanceApplyService.getHousAdvanceApply(processInstanceId);
    	
    	housAdvanceApplyInfo = housAdvanceApplyService.getHousAdvanceApplyInfo(projectId);    	
    	
    	if(housAdvanceApplyInfo != null){
    		housAdvanceApplyInfo.put("advanceApplyOperator", getLoginUser(request).getName());//垫资申请操作人为当前用户
    	}
    	result.put("housAdvanceApplyInfo", housAdvanceApplyInfo);
    	result.put("housAdvanceApply", housAdvanceApply);
    	
		if(housAdvanceApplyInfo == null  || housAdvanceApplyInfo == null){
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
     * @description 垫资申请提交时创建垫资申请任务
     * @param request
     * @param response
     * @param processInstanceId
     * @param branchingProcessType
     * @param serviceVariables
     * @author wtc
     * @return void
     * @throws Exception 
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/createAdvanceTasks.htm")
    public void createRebateTasks(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "processInstanceId", required = false) Long processInstanceId,
            @RequestParam(value = "consultId", required = false) Long consultId,
            @RequestParam(value = "advanceApplyAmount", required = false) BigDecimal advanceApplyAmount,
            @RequestParam(value = "advanceRateAmount", required = false) BigDecimal advanceRateAmount,
            @RequestParam(value = "advanceAmount", required = false) BigDecimal advanceAmount,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "remark", required = false) String remark,
            @RequestParam(value = "projectId", required = false) Long projectId)
            throws Exception {
        log.info("开始创建垫资任务---");
        SysUser sysUser = getLoginUser(request);
       // SysUser sysUser = new SysUser();
        //sysUser.setId((long)224);
       // sysUser.setUserName("cwzy");
       // sysUser.setOfficeId("11030502");
        // 调整 返回下一步的任务人
        Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);        
        try {
        	housAdvanceApplyService.createAdvanceTasks(processInstanceId, projectId, consultId,sysUser,advanceApplyAmount,advanceRateAmount,advanceAmount,type,remark);
        } catch (Exception e) {
            log.info("error", e);
            throw new ServiceException(e.getMessage());
        }

        ServletUtils.writeToResponse(response, res);
    }
    
    /**
     * 垫资放款操作
     * @description
     * @param request
     * @param response
     * @param data
     * @throws Exception
     * @author huy
     * @return void
     * @since  1.0.0
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/advanceLoan.htm")
    public void advanceLoan(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value = "data", required = true) String data)throws Exception{
    	HousAdvanceApply apply = new HousAdvanceApply();
		//对json对象进行转换
        if (!StringUtils.isEmpty(data)){
        	apply = JsonUtil.parse(data, HousAdvanceApply.class);
        }
        apply.setAdvanceStatus(HousAdvanceApply.ADVANCE_STATUS_LOAN);
        housAdvanceApplyService.update(apply);
    }
}
