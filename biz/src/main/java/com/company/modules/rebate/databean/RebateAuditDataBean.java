package com.company.modules.rebate.databean;

import java.io.Serializable;

import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.rebate.domain.HousRebate;

public class RebateAuditDataBean extends BasicServiceDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private HousRebate housRebate;


	public HousRebate getHousRebate() {
		return housRebate;
	}


	public void setHousRebate(HousRebate housRebate) {
		this.housRebate = housRebate;
	}

}
