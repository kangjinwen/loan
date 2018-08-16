package com.company.modules.collateral.workflow.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
//import com.company.contract.util.ContractUtil;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.modules.common.domain.ExportHousMortgageRegistration;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ExportExcel;
import com.company.modules.instance.service.HousPropertyInformationService;
import com.company.modules.collateral.workflow.service.CollateralTaskService;
import com.company.modules.system.domain.SysUser;
import com.company.modules.workflow.controller.WorkflowBaseController;

/**
 * User:    mcwang
 * DateTime:2016-08-08 04:18:34
 * details: 押品管理cotraller
 */
@Controller
@RequestMapping("/modules/collateral/CollateralProcessTaskContraller")
public class CollateralProcessTaskController extends WorkflowBaseController{

    /**
     * 押品管理的Service
     */
	@Autowired
	private CollateralTaskService collateralTaskService;
	@Autowired
	private HousPropertyInformationService housPropertyInformationService;
	
	
	/**
     * 抵押登记查询数据
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParam   查询条件
     * @param whereSql      直接的sql
     * @throws ServiceException
     */
    @RequestMapping("/getCollateralRegistTasks.htm")
    public void getCollateralRegistTasks ( HttpServletRequest request,HttpServletResponse response,
    					@RequestParam(required = true) boolean isCompleted,
                        @RequestParam(value = "currentPage") Integer currentPage,
                        @RequestParam(value = "pageSize") Integer pageSize,
                        @RequestParam(value = "searchParam",required = false)String searchParam)
    throws Exception{
    Map<String, Object> paramap=new HashMap<String, Object>();
        //对json对象进行转换
        if (!StringUtils.isEmpty(searchParam))
         paramap = JsonUtil.parse(searchParam, Map.class);
        
    	SysUser loginUser = getLoginUser(request);
		String userName = loginUser.getUserName();
		List<String> coverdOffices = getCoverdOffices(loginUser);

		Long roleId = getRoleId(request);
		paramap.put("coverdOffices", coverdOffices);
		paramap.put("userName", userName);
		paramap.put("roleId", roleId);
		paramap.put("roleNid", getRoleForLoginUser(request).getNid());
		paramap.put("isCompleted", isCompleted);
        
		paramap.put("currentPage",currentPage);
		paramap.put("pageSize", pageSize);
        //返回页面的json参数
        PageInfo<Map<String,Object>> page=collateralTaskService.getCollateralRegistList(paramap);
        Map<String,Object> returnMap=new HashMap<String,Object>();
      
        //返回给页面
        returnMap.put(Constant.RESPONSE_DATA,page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		
        ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * 抵押登记导出数据
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParam   查询条件
     * @param whereSql      直接的sql
     * @throws ServiceException
     */
    @RequestMapping("/exportCollateralRegistTasks.htm")
    public void exportCollateralRegistTasks ( HttpServletRequest request,HttpServletResponse response,
                        @RequestParam(value = "searchParam",required = false)String searchParam)throws Exception{
    	Map<String, Object> paramap=new HashMap<String, Object>();
        //对json对象进行转换
        if (!StringUtils.isEmpty(searchParam))
         paramap = JsonUtil.parse(searchParam, Map.class);
        
    	SysUser loginUser = getLoginUser(request);
		String userName = loginUser.getUserName();
		List<String> coverdOffices = getCoverdOffices(loginUser);

		Long roleId = getRoleId(request);
		paramap.put("coverdOffices", coverdOffices);
		paramap.put("userName", userName);
		paramap.put("roleId", roleId);
		paramap.put("roleNid", getRoleForLoginUser(request).getNid());
        //返回页面的json参数
        List<ExportHousMortgageRegistration> collateralList = collateralTaskService.queryCollateralRegistList(paramap);
        if (collateralList!=null && collateralList.size()>0) {
			for (ExportHousMortgageRegistration exportHousMortgageRegistration : collateralList) {
				String houseAddress = housPropertyInformationService.getHousAddress(exportHousMortgageRegistration.getResidentialAddressId()); 
				if (exportHousMortgageRegistration.getResidentialAddress()!=null) {					
					String address = houseAddress+=exportHousMortgageRegistration.getResidentialAddress();
					exportHousMortgageRegistration.setAddress(address);
				}else{
					exportHousMortgageRegistration.setAddress(houseAddress);
				}
			}
		}
        
        
        response.setContentType("octets/stream");
    	Map<String, Object> result = new HashMap<>();
    	String sep = File.separator;
    	String rootDir = request.getRealPath("/");// 文件根目录
    	String dirName = new SimpleDateFormat("yyyy-MM").format(new Date());
    	String baseDestDir = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append(dirName).toString();
    	File baseDestDirFile =  new File(baseDestDir);
		if (!baseDestDirFile.exists()) {
			baseDestDirFile.mkdirs();
		}
		String templeteFile = null;
		String destFile = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			String filename = "抵押登记.xls";
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + new String(filename.replace(" ", "").getBytes("UTF-8"), "iso8859-1"));
			result.put(Constant.RESPONSE_DATA, collateralList);
			String[] headers = {"项目编号","客户名称","房产面积（平方）","贷款金额(元)","贷款期限(月)","来源","机构名称","房产地址","出借人","出借人的受托人","他项权利证可领取时间","报单人","报单时间","流程状态","任务处理人","任务开始时间","任务结束时间"};
			ExportExcel<ExportHousMortgageRegistration> ex = new ExportExcel<ExportHousMortgageRegistration>();
			ex.exportExcel("抵押登记",headers,collateralList,response.getOutputStream(),"yyy-MM-dd HH:mm:ss");
			/*result.put(Constant.RESPONSE_DATA, collateralList);
			templeteFile = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append("collateral.docx").toString();
			destFile = new StringBuilder(baseDestDir).append(sep).append("collateral").append(System.currentTimeMillis()).append(".docx").toString();
			ContractUtil.generateWord(templeteFile, destFile, result);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("抵押登记.docx".getBytes("UTF-8"),"ISO-8859-1"));
			FileCopyUtils.copy(new BufferedInputStream(new FileInputStream(destFile)),response.getOutputStream());*/
			response.getOutputStream().close();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			Map<String, Object> res = new HashMap<String, Object>();
			res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
			res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
			ServletUtils.writeToResponse(response, res);
		}
    }
    
    
    /**
     * 押品解压列表
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParam   查询条件
     * @param whereSql      直接的sql
     * @throws ServiceException
     */
    @RequestMapping("/getCollateralRelieveTasks.htm")
    public void getCollateralRelieveTasks ( HttpServletRequest request,HttpServletResponse response,
    					@RequestParam(required = true) boolean isCompleted,
                        @RequestParam(value = "currentPage") Integer currentPage,
                        @RequestParam(value = "pageSize") Integer pageSize,
                        @RequestParam(value = "searchParams",required = false)String searchParams)
    throws Exception{
    Map<String, Object> paramap=new HashMap<String, Object>();
        //对json对象进行转换
        if (!StringUtils.isEmpty(searchParams))
         paramap = JsonUtil.parse(searchParams, Map.class);
        
    	SysUser loginUser = getLoginUser(request);
		String userName = loginUser.getUserName();
		
		
		List<String> coverdOffices = getCoverdOffices(loginUser);
		
		Long roleId = getRoleId(request);
		paramap.put("coverdOffices", coverdOffices);
		paramap.put("userName", userName);
		paramap.put("roleId", roleId);
		paramap.put("isCompleted", isCompleted);
        
		paramap.put("currentPage",currentPage);
		paramap.put("pageSize", pageSize);
        //返回页面的json参数
        PageInfo<Map<String,Object>> page=collateralTaskService.getCollateralRelieveList(paramap);
      Map<String,Object> returnMap=new HashMap<String,Object>();
      
        //返回给页面
        returnMap.put(Constant.RESPONSE_DATA,page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		
        ServletUtils.writeToResponse(response, returnMap);
    }
    
    
    /**
     * 抵押任务分配
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParam   查询条件
     * @param whereSql      直接的sql
     * @throws ServiceException
     */
    @RequestMapping("/getCollateralRegistAssignTasks.htm")
    public void getCollateralRegistAssignTasks ( HttpServletRequest request,HttpServletResponse response,
    					@RequestParam(required = true) boolean isCompleted,
                        @RequestParam(value = "currentPage") Integer currentPage,
                        @RequestParam(value = "pageSize") Integer pageSize,
                        @RequestParam(value = "searchParams",required = false)String searchParams)
    throws Exception{
    	Map<String, Object> paramap = new HashMap<>();
        //对json对象进行转换
        if (!StringUtils.isEmpty(searchParams))
         paramap = JsonUtil.parse(searchParams, Map.class);
        
    	SysUser loginUser = getLoginUser(request);
		String userName = loginUser.getUserName();
		List<String> coverdOffices = getCoverdOffices(loginUser);

		Long roleId = getRoleId(request);
		paramap.put("coverdOffices", coverdOffices);
		paramap.put("userName", userName);
		paramap.put("roleId", roleId);
		paramap.put("isCompleted", isCompleted);
        
		paramap.put("currentPage",currentPage);
		paramap.put("pageSize", pageSize);
        //返回页面的json参数
        PageInfo<Map<String,Object>> page=collateralTaskService.getCollateralRegistAssignTaskList(paramap);
      Map<String,Object> returnMap=new HashMap<String,Object>();
      
        //返回给页面
        returnMap.put(Constant.RESPONSE_DATA,page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		
        ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * 解押任务分配
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParam   查询条件
     * @param whereSql      直接的sql
     * @throws ServiceException
     */
    @RequestMapping("/getCollateralRelieveAssignTasks.htm")
    public void getCollateralRelieveAssignTasks ( HttpServletRequest request,HttpServletResponse response,
    					@RequestParam(required = true) boolean isCompleted,
                        @RequestParam(value = "currentPage") Integer currentPage,
                        @RequestParam(value = "pageSize") Integer pageSize,
                        @RequestParam(value = "searchParams",required = false)String searchParams)
    throws Exception{
    Map<String, Object> paramap=new HashMap<String, Object>();
        //对json对象进行转换
        if (!StringUtils.isEmpty(searchParams))
         paramap = JsonUtil.parse(searchParams, Map.class);
        
    	SysUser loginUser = getLoginUser(request);
		String userName = loginUser.getUserName();
		List<String> coverdOffices = getCoverdOffices(loginUser);

		Long roleId = getRoleId(request);
		paramap.put("coverdOffices", coverdOffices);
		paramap.put("userName", userName);
		paramap.put("roleId", roleId);
		paramap.put("isCompleted", isCompleted);
        
		paramap.put("currentPage",currentPage);
		paramap.put("pageSize", pageSize);
        //返回页面的json参数
        PageInfo<Map<String,Object>> page=collateralTaskService.getCollateralRelieveAssignTaskList(paramap);
      Map<String,Object> returnMap=new HashMap<String,Object>();
      
        //返回给页面
        returnMap.put(Constant.RESPONSE_DATA,page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		
        ServletUtils.writeToResponse(response, returnMap);
    }
    


}
