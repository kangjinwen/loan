package com.company.modules.instance.utils.listener;

import org.activiti.engine.delegate.DelegateExecution;

import com.company.modules.common.exception.ServiceException;
import com.company.modules.loanchange.util.listener.AbstractExecutionListener;
import com.company.modules.loanchange.util.listener.RDDelegateExecution;


/** 
 *	@Description 合同、线下登记并行网关监听
 *  @author wtc,wtc@erongdu.com
 *  @CreatTime 2015年7月28日 下午4:26:09
 *  @since version 1.0.0
 */
public class ParallelOfContractAndGPSListener extends AbstractExecutionListener{
    private static final long serialVersionUID = 1L;
    
    static String GIVEUP_KEY="isCustomerGiveUp";
    
    static String REJECTPROCESS="rejectProcess";
    @Override
    public void notifyEndEvent(RDDelegateExecution execution) throws ServiceException {
        DelegateExecution de=execution.getDelegateExecution();
        Object giveUp=de.getVariable(GIVEUP_KEY);
        Object reject=de.getVariable(REJECTPROCESS);
        if(giveUp==null && null==reject){
            de.setVariable("comment", "pass");
        }else{
        	if(giveUp != null){
        		de.setVariable("comment", "customerGiveUp");
        	}else if(reject !=null){
        		de.setVariable("comment", "rejectProcess");
        	}else {
        		de.setVariable("comment", "customerGiveUp");
        	}   
        }
    }

    @Override
    public void notifyStartEvent(RDDelegateExecution execution) {
        DelegateExecution de=execution.getDelegateExecution();
        Object object=de.getVariable("comment");
        if(object.equals("customerGiveUp")){
            de.setVariable(GIVEUP_KEY, true);
        }else if(object.equals("rejectProcess")){
        	de.setVariable(REJECTPROCESS, true);
        }
    }
}
