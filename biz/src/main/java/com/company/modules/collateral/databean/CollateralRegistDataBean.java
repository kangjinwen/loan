package com.company.modules.collateral.databean;

import java.io.Serializable;

import com.company.modules.collateral.domain.HousMortgageRegistration;
import com.company.modules.common.utils.databean.BasicServiceDataBean;

public class CollateralRegistDataBean extends BasicServiceDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HousMortgageRegistration housMortgageRegistration;

	public HousMortgageRegistration getHousMortgageRegistration() {
		return housMortgageRegistration;
	}

	public void setHousMortgageRegistration(
			HousMortgageRegistration housMortgageRegistration) {
		this.housMortgageRegistration = housMortgageRegistration;
	}

}
