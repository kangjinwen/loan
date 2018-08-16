package com.company.common.service;

import java.util.Map;

import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;
import com.company.modules.system.domain.SysRole;

/**
* User:    wulb
* DateTime:2016-08-08 01:01:45
* details: 咨询信息,Service接口层
* source:  代码生成器
*/
public interface ZZJFPlConsultService {
	
	/**
     * 新增咨询
	 * @param sysRole 
     * @param preliminaryEvaluationDataBean
     * @throws Exception
     */
	Map<String, Object> addPlConsult(SysRole sysRole, PreliminaryEvaluationDataBean preliminaryEvaluationDataBean) throws Exception;
}
