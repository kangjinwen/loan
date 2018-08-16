package com.company.modules.common.service;

import com.company.modules.common.utils.databean.BasicServiceDataBean;

public interface HistoryRecordableService {
	void recordLoanProcessHistory(BasicServiceDataBean basicServiceDataBean) throws Exception;
}
