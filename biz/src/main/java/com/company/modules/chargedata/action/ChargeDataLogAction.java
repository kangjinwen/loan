package com.company.modules.chargedata.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.modules.common.exception.ServiceException;
import com.company.common.context.Constant;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.chargedata.domain.ChargeDataLog;
import com.company.modules.chargedata.service.ChargeDataLogService;

/**
 * User:    JDM
 * DateTime:2016-11-30 02:08:46
 * details: 押品管理,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/chargedata/ChargeDataLogAction")
@Controller
public class ChargeDataLogAction extends BaseAction {

    /**
     * 押品管理的Service
     */
	@Autowired
	private ChargeDataLogService chargeDataLogService;


    /**
     * 分页查询数据
     * @param request      页面的request
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParams   查询条件
     * @throws Exception
     */
    @RequestMapping("/getChargeDataLogList.htm")
    public void getChargeDataLogList(HttpServletResponse response, HttpServletRequest request,
  		ChargeDataLog chargeDataLog) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
        PageHelper.startPage(chargeDataLog.getPageNum(), chargeDataLog.getPageSize());
		List<ChargeDataLog> chargeDataLogs = chargeDataLogService.getPageListByMap(chargeDataLog);
		PageInfo<ChargeDataLog> page = new PageInfo<ChargeDataLog>(chargeDataLogs);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponseWithOnlyYMDDate(response, returnMap);
    }
    
    /**
     * 查询最后一次借出人
     */
    @RequestMapping("/getLendChargeDataLog.htm")
    public void getLendChargeDataLog(HttpServletResponse response, HttpServletRequest request,
  		ChargeDataLog chargeDataLog) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		chargeDataLog.setType(ChargeDataLog.TYPE_LEND_OUT);
		List<ChargeDataLog> chargeDataLogs = chargeDataLogService.getPageListByMap(chargeDataLog);
		if (CollectionUtils.isNotEmpty(chargeDataLogs)) {
			returnMap.put(Constant.RESPONSE_DATA, chargeDataLogs.get(0));
		}else{
			returnMap.put(Constant.RESPONSE_DATA,null);
		}
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
		Integer influence = chargeDataLogService.deleteById(id);
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
