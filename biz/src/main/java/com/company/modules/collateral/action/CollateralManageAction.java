package com.company.modules.collateral.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.domain.SysUser;
import com.company.modules.collateral.domain.HousMortgageRegistration;
import com.company.modules.collateral.service.CollateralManageService;

/**
 * User:    mcwang
 * DateTime:2016-08-08 04:18:34
 * details: 押品登记表,Action请求层
 */
@Controller
@RequestMapping("/modules/collateral/CollateralManageAction")
public class CollateralManageAction extends BaseAction {

    /**
     * 押品登记表的Service
     */
	@Autowired
	private CollateralManageService collateralManageService;

    /**
     * 押品登记表表,插入数据
     * @param response      页面的response
     * @param json          页面参数
     * @throws ServiceException
     */
    @RequestMapping("/saveOrUpdateCollateralRegist.htm")
    public void saveOrUpdateCollateralRegist(HttpServletRequest request,HttpServletResponse response,
    	@RequestParam(value = "json" ,required = false)String json,
    	@RequestParam(value = "processInstanceId" ,required = false)String processInstanceId //执行的动作
    	)throws Exception {
	     Map<String,Object> returnMap=new HashMap<String,Object>();
	     long flag=0;
     
        HousMortgageRegistration collateralRegist = new HousMortgageRegistration();
        //对json对象进行转换
        if (!StringUtils.isEmpty(json))
            collateralRegist = JsonUtil.parse(json, HousMortgageRegistration.class);
        
        HousMortgageRegistration hmr=collateralManageService.getHousMortgageRegistrationByInstanceId(processInstanceId);
        if(hmr!=null){
        	//修改数据
			flag=collateralManageService.update(collateralRegist);
        }else{
        	//动态插入数据
        	flag=collateralManageService.insert(collateralRegist);
        }
		if(flag>0){//判断操作是否成功
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
     * 押品登记表表,删除数据(逻辑)
     * @param response      页面的response
     * @param json          页面参数
     * @throws ServiceException
     */
    @RequestMapping("/deleteCollateralRegist.htm")
    public void deleteCollateralRegist(HttpServletRequest request,HttpServletResponse response,
    	@RequestParam(value = "id" ,required = false)String id
    	)throws Exception {
	     Map<String,Object> returnMap=new HashMap<String,Object>();
	     long flag=0;
     
        HousMortgageRegistration collateralRegist = new HousMortgageRegistration();
        collateralRegist.setId(Long.valueOf(id));
   //     collateralRegist.setIsDelete(1);  //删除状态
       
		 //修改数据
		flag=collateralManageService.update(collateralRegist);
		
		if(flag>0){//判断操作是否成功
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
     * 抵押-根据流程id查询抵押物信息
     * @param request      页面的request
     * @param response      页面的response
     * @param processInstanceId  查询条件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getCollateralRegistDataByInstanceId.htm")
    public void getCollateralRegistDataByInstanceId(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "processInstanceId") String processInstanceId
		) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> data=null;
		SysUser loginUser = getLoginUser(request);
		
		//对json对象进行转换
		data = collateralManageService.getCollateralRegistData(processInstanceId);
		returnMap.put(Constant.RESPONSE_DATA, data);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * 解押-根据流程id查询抵押物信息
     * @param request      页面的request
     * @param response      页面的response
     * @param processInstanceId  查询条件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getCollateralRelieveDataByInstanceId.htm")
    public void getCollateralRelieveDataByInstanceId(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "processInstanceId") String processInstanceId
		) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> data=null;
		SysUser loginUser = getLoginUser(request);
		
		//对json对象进行转换
		data = collateralManageService.getCollateralRegistData(processInstanceId);
		if(!data.containsKey("outboundPerson"))
		data.put("outboundPerson",loginUser.getName());
		
		returnMap.put(Constant.RESPONSE_DATA, data);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
}
