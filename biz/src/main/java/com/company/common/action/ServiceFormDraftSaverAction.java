package com.company.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.common.context.Constant;
import com.company.common.utils.ServletUtils;
import com.company.common.utils.saver.ServiceDraftSaverFactory;
import com.company.common.web.action.BaseAction;
import com.company.modules.common.exception.ObjectNotFoundException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.parser.impl.DefaultClassTypeParser;
import com.company.modules.common.utils.saver.LoginContext;
import com.company.modules.common.utils.saver.ServiceFormDraftSaver;
import com.company.modules.instance.domain.HousAssessmentAgencies;
import com.company.modules.instance.service.HousAssessmentAgenciesService;
import com.company.modules.system.domain.SysRole;
import com.company.modules.system.domain.SysUser;

/**
 * 保存草稿的Action
 * 所有的保存草稿的请求都可以来这里
 * 
 * @author FHJ
 *
 */
@Controller
@RequestMapping("/modules/common/action/ServiceFormDraftSaverAction")
public class ServiceFormDraftSaverAction extends BaseAction{
	
	@Autowired
	private HousAssessmentAgenciesService housAssessmentAgenciesService;
	
	private DefaultClassTypeParser defaultClassTypeParser = new DefaultClassTypeParser();
	/**
	 * 保存表单草稿的方法
	 * @param formName
	 * @param serviceVariables
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("saveFormDraft")
	public void saveFormDraft(@RequestParam(value = "formName")String formName,
			@RequestParam(value = "serviceVariables")String serviceVariables,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServiceFormDraftSaver saver = getSaverByFormName(formName);
        SysUser loginUser = getLoginUser(request);
		SysRole role = getRoleForLoginUser(request);
		LoginContext loginInfo = new LoginContext(loginUser, role);

		saver.saveDraft(serviceVariables, loginInfo);
        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Object> serviceVarMap = defaultClassTypeParser.parse(
				serviceVariables, Map.class);
        //把草稿id返回去
        List<HousAssessmentAgencies> housAssessmentAgencies = housAssessmentAgenciesService.getItemInfoByProcessInstanceId((String)serviceVarMap.get("processInstanceId"));
        res.put("housAssessmentAgencies", housAssessmentAgencies);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);

        ServletUtils.writeToResponse(response, res);
	}

	private ServiceFormDraftSaver getSaverByFormName(String formName) throws Exception {
		ServiceDraftSaverFactory draftSaverFactory = ServiceDraftSaverFactory.getInstance();
		ServiceFormDraftSaver serviceFormDraftSaver = null;
		try {
			serviceFormDraftSaver = draftSaverFactory.getObject(formName);
		} catch (ObjectNotFoundException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return serviceFormDraftSaver;
	}
}
