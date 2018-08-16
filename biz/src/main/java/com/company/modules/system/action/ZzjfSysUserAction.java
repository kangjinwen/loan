package com.company.modules.system.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.common.context.Constant;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.ZzjfSysUserService;

/**
 *  赚赚金服用户定制需求action类
 *	@Description
 *  @author huy,yhu@erongdu.com
 *  @CreatTime 2016年10月27日 下午3:35:19
 *  @since version 1.0.0
 */
@SuppressWarnings({ "all" })
@RequestMapping("/modules/system/ZzjfSysUserAction")
@Controller
public class ZzjfSysUserAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(ZzjfSysUserAction.class);
	@Autowired
	private ZzjfSysUserService zzjfSysUserService;

	/**
	 * 是否接单设置
	 * @param request
	 * @param response
	 * @param isReceiveOrder
	 */
	@RequestMapping("/orderReceiveSet.htm")
	public void userEditPage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "isReceiveOrder", required = true) String isReceiveOrder) throws Exception{
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		SysUser user = getLoginUser(request);
		paramMap.put("id", user.getId());
		paramMap.put("isReceiveOrder", isReceiveOrder);
		int i = zzjfSysUserService.updateByMap(paramMap);
		if (i>0) {
			responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			responseMap.put(Constant.RESPONSE_CODE_MSG, "修改成功");
		} else {
			responseMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			responseMap.put(Constant.RESPONSE_CODE_MSG, "修改失败");
		}
		ServletUtils.writeToResponse(response, responseMap);
	}	
}
