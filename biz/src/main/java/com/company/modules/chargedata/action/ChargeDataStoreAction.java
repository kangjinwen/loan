package com.company.modules.chargedata.action;

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

import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.chargedata.domain.ChargeDataStore;
import com.company.modules.chargedata.service.ChargeDataStoreService;
import com.company.modules.common.exception.ServiceException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * User: JDM DateTime:2016-12-02 11:45:24 details: 押品管理,Action请求层 source: 代码生成器
 */
@RequestMapping("/modules/chargedata/ChargeDataStoreAction")
@Controller
public class ChargeDataStoreAction extends BaseAction {

	/**
	 * 押品管理的Service
	 */
	@Autowired
	private ChargeDataStoreService chargeDataStoreService;

	/**
	 * 押品管理表,插入数据
	 * 
	 * @param request         页面的request
	 * @param response        页面的response
	 * @param chargeDataStore 页面参数
	 * @throws Exception
	 */
	@RequestMapping("/saveOrUpdateChargeDataStore.htm")
	public void saveOrUpdateChargeDataStore(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "chargeDataStore", required = false) String chargeDataStore,
			@RequestParam(value = "status", required = false) String status) throws Exception {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		Long influence = 0L;
		ChargeDataStore chargeDataStoreInfo = new ChargeDataStore();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(chargeDataStore)) {
			chargeDataStoreInfo = JsonUtil.parse(chargeDataStore, ChargeDataStore.class);
		}
		if (status.equals("create")) {
			// 如果表中有创建者 取当前登录人
			influence = chargeDataStoreService.insert(chargeDataStoreInfo);
		} else {
			// 如果表中有修改者 取当前登录人
			influence = chargeDataStoreService.update(chargeDataStoreInfo);
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
	 * 
	 * @param request      页面的request
	 * @param response     页面的response
	 * @param currentPage  当前页数
	 * @param pageSize     每页限制
	 * @param searchParams 查询条件
	 * @throws Exception
	 */
	@RequestMapping("/getChargeDataStoreList.htm")
	public void getChargeDataStoreList(HttpServletResponse response, HttpServletRequest request,
			ChargeDataStore chargeDataStore) throws Exception {
		// 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		PageHelper.startPage(chargeDataStore.getPageNum(), chargeDataStore.getPageSize());
		List<ChargeDataStore> chargeDataStores = chargeDataStoreService.getPageListByMap(chargeDataStore);
		PageInfo<ChargeDataStore> page = new PageInfo<ChargeDataStore>(chargeDataStores);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponseWithOnlyYMDDate(response, returnMap);
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param response 页面的response
	 * @param id       主键
	 * @throws ServiceException
	 */
	@RequestMapping("/deleteById.htm")
	public void deleteById(HttpServletResponse response, @RequestParam(value = "id") Long id) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Integer influence = chargeDataStoreService.deleteById(id);
		if (influence > 0) {
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
	}
}
