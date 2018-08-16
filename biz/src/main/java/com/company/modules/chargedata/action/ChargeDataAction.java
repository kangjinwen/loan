package com.company.modules.chargedata.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.chargedata.domain.ChargeData;
import com.company.modules.chargedata.domain.ChargeDataLog;
import com.company.modules.chargedata.domain.ChargeDataStore;
import com.company.modules.chargedata.service.ChargeDataService;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.domain.SysUser;

/**
 * User:    JDM
 * DateTime:2016-11-30 02:08:46
 * details: 押品管理,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/chargedata/ChargeDataAction")
@Controller
public class ChargeDataAction extends BaseAction {
	
    /**
     * 押品管理的Service
     */
	@Autowired
	private ChargeDataService chargeDataService;

    /**
     * 入库,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param chargeData	页面参数
     * @throws Exception
     */
    @RequestMapping("/inStoreChargeData.htm")
    public void saveChargeData(HttpServletRequest request, HttpServletResponse response, 
    	String params) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ChargeData chargeData = JsonUtil.parseWithOnlyYMDDate(params, ChargeData.class);
		Long influence = chargeDataService.inStore(chargeData.getChargeDatas());
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
     * 出库,更新数据
     * @param request	页面的request
     * @param response	页面的response
     * @param chargeData	页面参数
     * @throws Exception
     */
    @RequestMapping("/outStoreChargeData.htm")
    public void outStoreChargeData(HttpServletRequest request, HttpServletResponse response, 
    	String params) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ChargeDataStore chargeDataStore = JsonUtil.parse(params, ChargeDataStore.class);
		Long influence = chargeDataService.outStore(chargeDataStore);
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
     * 借出
     * @param request
     * @param response
     * @param chargeDatas
     * @throws Exception
     */
    @RequestMapping("/lendChargeData.htm")
    public void lendChargeData(HttpServletRequest request, HttpServletResponse response, 
    		ChargeDataLog chargeDataLog) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		SysUser sysUser = getLoginUser(request);
		Long influence = chargeDataService.lendOut(chargeDataLog,sysUser);
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
     * 归还
     * @param request
     * @param response
     * @param chargeDatas
     * @throws Exception
     */
    @RequestMapping("/returnChargeData.htm")
    public void returnChargeData(HttpServletRequest request, HttpServletResponse response, 
    		ChargeDataLog chargeDataLog) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		SysUser sysUser = getLoginUser(request);
		Long influence = chargeDataService.giveBack(chargeDataLog,sysUser);
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
    @RequestMapping("/getChargeDataList.htm")
    public void getChargeDataList(HttpServletResponse response, HttpServletRequest request,
  		ChargeData chargeData) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
        PageHelper.startPage(chargeData.getPageNum(), chargeData.getPageSize());
		List<ChargeData> chargeDatas = chargeDataService.getPageListByMap(chargeData);
		PageInfo<ChargeData> page = new PageInfo<ChargeData>(chargeDatas);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponseWithOnlyYMDDate(response, returnMap);
    }
    
    /**
    * 根据id删除数据
    * @param response    页面的response
    * @param id          主键
    * @throws ServiceException
    */
    @RequestMapping("/deleteById.htm")
    public void deleteById(HttpServletResponse response, @RequestParam(value = "id") Long id) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
		Integer influence = chargeDataService.deleteById(id);
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
