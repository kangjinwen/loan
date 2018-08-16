package com.company.modules.finance.util.databean;

import java.io.Serializable;

import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.finance.domain.HousLowerCost;

public class ReturnFeeTaskDataBean extends BasicServiceDataBean implements Serializable{

	/**
	 * serialVersionUID:TODO
	 *
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = 1L;

	private HousLowerCost housLowerCost;

	public HousLowerCost getHousLowerCost() {
		return housLowerCost;
	}

	public void setHousLowerCost(HousLowerCost housLowerCost) {
		this.housLowerCost = housLowerCost;
	}
	
	
}
