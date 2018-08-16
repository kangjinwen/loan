package com.company.common.service;

import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.loanchange.util.listener.RDDelegateExecution;

public interface CompleteProcessService {
    void completeProcess(BasicServiceDataBean bean,RDDelegateExecution execution)throws ServiceException;
}
