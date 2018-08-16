package com.company.modules.loanchange.util.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.common.service.CompleteProcessService;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.common.utils.parser.impl.DefaultClassTypeParser;

/**
 *	@Description 违约金罚息减免流程结束监听
 *  @author wtc,wtc@erongdu.com
 *  @CreatTime 2015年6月24日 上午10:01:09
 *  @since version 1.0.0
 */
public class CompleteBreachofContractReliefListener extends BranchProcessExcutionListener{
    private static final long serialVersionUID = 1L;
    private static Logger log=LoggerFactory.getLogger(CompleteBreachofContractReliefListener.class);
    protected ClassTypeParser defaultClassTypeParser = new DefaultClassTypeParser();
    @Override
    public void notifyEndEvent(RDDelegateExecution execution) throws ServiceException {
        String serviceVariables=execution.getVariable(SystemConstant.SERVICE_VARIABLES).toString();  
        log.debug("carBreachofContractReliefService:"+serviceVariables);
        CompleteProcessService cps=ApplicationContextHelperBean.getBean("breachofContractReliefService",CompleteProcessService.class);
        cps.completeProcess(defaultClassTypeParser.parse(serviceVariables, BasicServiceDataBean.class),execution);
    }

    @Override
    public void notifyStartEvent(RDDelegateExecution execution) {
       
    }

    @Override
    protected boolean needUpdateProcessBranching() {
        return false;
    }

}
