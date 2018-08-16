package com.company.modules.collateral.workflow.service;

import com.company.modules.collateral.databean.CollateralRegistDataBean;

public interface CollateralRegistService {
	
	void regist(CollateralRegistDataBean dataBean) throws Exception;
	
	//展期抵押登记
	void registExcessiveMortgage(CollateralRegistDataBean dataBean) throws Exception;

}
