package com.company.modules.rebate.workflow.service;

import com.company.modules.rebate.databean.RebateAuditDataBean;

public interface RebateHandleService {
	
	void handle(RebateAuditDataBean dataBean) throws Exception;

}
