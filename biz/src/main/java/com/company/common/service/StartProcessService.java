package com.company.common.service;

import com.company.modules.common.exception.ServiceException;
import com.company.modules.loanchange.util.listener.RDDelegateExecution;

public interface StartProcessService {
    void startProcessService(RDDelegateExecution execution) throws ServiceException;
}
