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
     * @param creditConsultFrom
	 * @throws Exception
     */
	Map<String, Object> addPlConsult(SysRole sysRole, PreliminaryEvaluationDataBean preliminaryEvaluationDataBean, String creditConsultFrom) throws Exception;

	/**
	 * 根据房产证编号查询 当前申请是否满足（每个房产证一个月仅仅可以申请一次）的要求
	 * @param homeNum
	 * @return
	 */
    Map<String, Object> getWhetherLoanByHomeNum(String homeNum);
}
