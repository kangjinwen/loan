package com.company.common.action;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.common.utils.upload.CustomFileUpload;
import com.company.modules.system.domain.SysUploadInfo;
import com.company.modules.system.service.ChannelPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.common.context.Constant;
import com.company.common.service.PlProductService;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.common.domain.PlProduct;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.domain.SysUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * User: wulb DateTime:2016-08-15 05:50:16 details: 产品信息,Action请求层 source: 代码生成器
 */
@RequestMapping("/modules/common/PlProductAction")
@Controller
public class PlProductAction extends BaseAction {

	/**
	 * 产品信息的Service
	 */
	@Autowired
	private PlProductService plProductService;
	@Autowired
	private CustomFileUpload customFileUpload;
	@Autowired
	private ChannelPartnerService channelPartnerService;


	@SuppressWarnings("unchecked")
	@RequestMapping("/uploadProductDoc.htm")
	public void uploadProductDoc(final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {

		Map<String, Object> res = new HashMap<String, Object>();
		List<SysUploadInfo> sysUploadInfos = customFileUpload.doFileUpload(request, channelPartnerService.getDocPath(),
				channelPartnerService.getDocFileURL(), false);
		List<String> urls = new ArrayList<String>();
		if (sysUploadInfos!=null && sysUploadInfos.size()==1) {
			String url = sysUploadInfos.get(0).getUrl();
			res.put("url", url);
		} else {
			throw new RuntimeException("没有文件可上传");
		}
		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put("success", "true");
		ServletUtils.writeToResponse(response, res);
	}


	/**
	 * 产品信息表,插入数据
	 * 
	 * @param request   页面的request
	 * @param response  页面的response
	 * @param plProduct 页面参数
	 * @throws Exception
	 */
	@RequestMapping("/saveOrUpdatePlProduct.htm")
	public void saveOrUpdatePlProduct(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "json", required = false) String plProduct,
			@RequestParam(value = "status", required = false) String status) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		PlProduct plProductInfo = new PlProduct();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(plProduct)) {
			plProductInfo = JsonUtil.parse(plProduct, PlProduct.class);
		}
		if (status.equals("create")) {
			plProductInfo.setPtype((byte) 2);
			plProductInfo.setIsOpen((byte) 1);
			plProductInfo.setCreatetime(new Date());
			plProductInfo.setIsDelete((byte) 0);
			SysUser loginUser = getLoginUser(request);
			if (loginUser.getChannelPartnerId() != null && loginUser.getChannelPartnerId().length() != 0) {
				plProductInfo.setChannelPartnerId(Integer.parseInt(loginUser.getChannelPartnerId()));
			}
			// 如果表中有创建者 取当前登录人
			influence = plProductService.insert(plProductInfo);
		} else {
			// 如果表中有修改者 取当前登录人
			influence = plProductService.update(plProductInfo);
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/getPlProductList.htm")
	public void getPlProductList(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "currentPage") Integer currentPage,
			@RequestParam(value = "pageSize") Integer pageSize,
			@RequestParam(value = "searchParams", required = false) String searchParams) throws Exception {
		// 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(searchParams)) {
			paramap = JsonUtil.parse(searchParams, Map.class);
		}
		SysUser loginUser = getLoginUser(request);
		paramap.put("channelPartnerId", loginUser.getChannelPartnerId());
		PageHelper.startPage(currentPage, pageSize);
		List<Map<String, Object>> plProducts = plProductService.getPageListByMap(paramap);

		PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(plProducts);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param response 页面的response
	 * @param id       主键
	 * @throws ServiceException
	 */
	@RequestMapping("/deleteById.htm")
	public void deleteById(HttpServletResponse response, @RequestParam(value = "id") long id) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = plProductService.deleteById(id);
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

