package com.company.modules.loanchange.util.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.company.modules.common.exception.ServiceException;

/**
 * @Description 流程节点启动或结束监听
 * @author wtc,wtc@erongdu.com
 * @CreatTime 2015年6月19日 下午4:03:10
 * @since version 1.0.0
 */
public abstract class AbstractExecutionListener implements ExecutionListener {

    private static final long serialVersionUID = 1L;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String eventName = execution.getEventName();
        RDDelegateExecution exec = new RDDelegateExecution(execution);
        if (EVENTNAME_END.equals(eventName)) {
            notifyEndEvent(exec);
        }
        if (EVENTNAME_START.equals(eventName)) {
            notifyStartEvent(exec);
        }
    }
    public abstract void notifyEndEvent(RDDelegateExecution execution) throws ServiceException;

    public abstract void notifyStartEvent(RDDelegateExecution execution) throws ServiceException;

}
