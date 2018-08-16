package com.company.common.utils.factory;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.InterestCalculator;
import com.company.modules.common.utils.factory.ObjectFactory;

public class InterestCaculatorFactory implements ObjectFactory<InterestCalculator>{
	private Logger logger = LoggerFactory.getLogger(InterestCaculatorFactory.class);
	private static InterestCaculatorFactory instance = new InterestCaculatorFactory();
	
	private InterestCaculatorFactory() {
	}
	
	public static InterestCaculatorFactory getInstance() {
		return instance;
	}
	
	@Override
	public InterestCalculator getObject(Object qualifier) {
		Byte repaymentType = (Byte)qualifier;
		InterestCalculator interestCalculator;

		/*// TODO 信贷暂时都先用RoinChina的规则
		if(repaymentType == SystemConstant.REPAYMENT_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST) {
			// 等额本息
			interestCalculator = new RoinChinaInstallmentRepaymentCalculator();
    	} else if(repaymentType == SystemConstant.REPAYMENT_TYPE_AVERAGE_CAPITAL) {
    		// 等额本金
    		interestCalculator = new RoinChinaAverageCapitalInterestCalculator();
    	} else if(repaymentType == SystemConstant.REPAYMENT_TYPE_ONE_TIME) {
    		// 一次性还本付息
    		interestCalculator = new RoinChinaOnetimeRepaymentCalculator();
    	} else if(repaymentType == SystemConstant.REPAYMENT_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST_CAR_SIX || repaymentType == SystemConstant.REPAYMENT_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST_CAR_NINE){
    		// 等额本息
    		interestCalculator = new CarRepaymentCalculator();
    	}else {
    		throw new ObjectNotFoundException("用户选择的是" + repaymentType + ",该系统暂时不支持这个计算方式。");
    	}*/
    	
		return null;
	}
//	public InterestCalculator getObject(String fullClassName,Object... args){
//	   
//	}
	@SuppressWarnings("unchecked")
    public InterestCalculator getObject(String fullClassName,ClassLoader cl,Object... args){
	    Class<InterestCalculator> clazz;
	    InterestCalculator ic;
        try {
            clazz = (Class<InterestCalculator>) Class.forName(fullClassName,true,cl);
            ic=ConstructorUtils.invokeConstructor(clazz, args);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new RDRuntimeException(e.getMessage(),e);
        }
        return ic;
	}
	public static String getClassName(Byte repaymentType){
		//根据还款方式区分
        if (repaymentType == 1) {
        	//先息后本
            return "com.company.modules.common.utils.interest.henhaodai.HenhaodaiAInterestCalculator";
        } else if(repaymentType==2){
        	//等额本息
            return "com.company.modules.common.utils.interest.henhaodai.HenhaodaiBInterestCalculator";
        }else if(repaymentType == 3){
            return "com.company.modules.common.utils.interest.henhaodai.HenhaodaiCInterestCalculator";
        }else if(repaymentType == 4){
            return "com.company.modules.common.utils.interest.henhaodai.HenhaodaiDInterestCalculator";
        }
        //TODO 暂时都用A产品
        return null;
    }
}
