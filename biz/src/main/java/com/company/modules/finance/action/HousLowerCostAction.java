package com.company.modules.finance.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.modules.common.dao.PubLoanprocessDao;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.domain.SysUser;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.finance.domain.HousLowerCost;
import com.company.modules.finance.service.HousLowerCostService;

/**
 * User:    fzc
 * DateTime:2016-08-12 10:23:50
 * details: 下户费表,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/finance/HousLowerCostAction")
@Controller
public class HousLowerCostAction extends BaseAction {

    /**
     * 下户费表的Service
     */
	@Autowired
	private HousLowerCostService housLowerCostService;
	@Autowired
	private PubLoanprocessDao pubLoanprocessDao;

    /**
     * 下户费表表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param housLowerCost	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdateHousLowerCost.htm")
    public void saveOrUpdateHousLowerCost(HttpServletRequest request, HttpServletResponse response, 
    	@RequestParam(value = "housLowerCost", required = false) String housLowerCost, @RequestParam(value = "status", required = false) String status) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		SysUser sysUser = this.getLoginUser(request);
		HousLowerCost housLowerCostInfo = new HousLowerCost();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(housLowerCost)) {
			housLowerCostInfo = JsonUtil.parse(housLowerCost, HousLowerCost.class);
		}
		if (status.equals("create")) {
			//如果表中有创建者   取当前登录人
			housLowerCostInfo.setCreator(sysUser.getId());
			housLowerCostInfo.setCreateTime(new Date());
			influence = housLowerCostService.insert(housLowerCostInfo);
		} else {
			//如果表中有修改者   取当前登录人
			housLowerCostInfo.setModifier(sysUser.getId());
			housLowerCostInfo.setModifyTime(new Date());
			influence = housLowerCostService.update(housLowerCostInfo);
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
    @RequestMapping("/getHousLowerCostList.htm")
    public void getHousLowerCostList(HttpServletResponse response, HttpServletRequest request,
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
		List<HousLowerCost> housLowerCosts = housLowerCostService.getPageListByMap(paramap);
		PageInfo<HousLowerCost> page = new PageInfo<HousLowerCost>(housLowerCosts);
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
		long influence = housLowerCostService.deleteById(id);
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
     * @description  获取下户基本信息
     * @param response
     * @param request
     * @param projectId
     * @throws Exception
     * @author fzc
     * @return void
     * @since  1.0.0
     */
    @RequestMapping("/getHousLowerCostBasicInfo.htm")
    public void getHousLowerCostBasicInfo(
    		HttpServletResponse response,
    		HttpServletRequest request,
    		@RequestParam(value = "projectId") long projectId) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	Map<String, Object> data = new HashMap<String, Object>();
    	
    	data = housLowerCostService.getHousLowerCostBasicInfo(projectId);
    	
		if(!data.isEmpty()){
			returnMap.put(Constant.RESPONSE_DATA,data);
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		}else{
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		//返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    @RequestMapping("/getHousLowerCostInfo.htm")
    public void getHousLowerCostInfo(
    		HttpServletResponse response,
    		HttpServletRequest request,
    		@RequestParam(value = "processInstanceId") long processInstanceId,
    		@RequestParam(value = "projectId") long projectId) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	Map<String, Object> housLowerCostInfo = new HashMap<String, Object>();
    	Map<String, Object> housLowerCostBasicInfo = new HashMap<String, Object>();
    	Map<String, Object> pubLoanProcess = new HashMap<String, Object>();
    	Map<String, Object> pubLoanProcessReturnFee = new HashMap<String, Object>();
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	housLowerCostInfo = housLowerCostService.getHousLowerCostInfo(processInstanceId);
    	
    	housLowerCostBasicInfo = housLowerCostService.getHousLowerCostBasicInfo(projectId);
    	
    	//退费申请审批意见和备注
    	pubLoanProcess = pubLoanprocessDao.getByProjectId(projectId);
    	if(housLowerCostBasicInfo != null){
    		housLowerCostInfo.put("financeSpecialist", getLoginUser(request).getName());//财务专员为当前用户
    	}
    	result.put("housLowerCostInfo", housLowerCostInfo);
    	result.put("housLowerCostBasicInfo", housLowerCostBasicInfo);
    	result.put("pubLoanProcess", pubLoanProcess);
    	
		if(housLowerCostInfo == null  || housLowerCostBasicInfo == null){
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
     * @description 退费申请提交时创建退费申请任务
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
    @RequestMapping("/createReturnFeeTasks.htm")
    public void createRebateTasks(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "processInstanceId", required = false) Long processInstanceId,
            @RequestParam(value = "applyRefundAmount", required = false) BigDecimal applyRefundAmount,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "remark", required = false) String remark,
            @RequestParam(value = "projectId", required = false) Long projectId)
            throws Exception {
        log.info("开始执行退费任务---");
        SysUser sysUser = getLoginUser(request);
       // SysUser sysUser = new SysUser();
        //sysUser.setId((long)224);
        //sysUser.setUserName("cwzy");
        //sysUser.setOfficeId("11030502");
        // 调整 返回下一步的任务人
        Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);        
        try {
        	housLowerCostService.createReturnFeeTasks(processInstanceId, projectId, sysUser,applyRefundAmount,type,remark);
        } catch (Exception e) {
        	log.error("【退费申请失败】"+e.getMessage(),e);
			throw new ServiceException("分配任务失败，请检查人员配置",e);
        }

        ServletUtils.writeToResponse(response, res);
    }
    
}
