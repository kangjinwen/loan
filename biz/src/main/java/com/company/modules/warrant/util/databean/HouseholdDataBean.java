package com.company.modules.warrant.util.databean;

import java.io.Serializable;

import com.company.modules.common.utils.databean.BasicServiceDataBean;

public class HouseholdDataBean extends BasicServiceDataBean implements Serializable{

	/**
	 * serialVersionUID:TODO
	 *
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String assignCheckBank;//手动任务分配人(核行)

	public String getAssignCheckBank() {
		return assignCheckBank;
	}

	public void setAssignCheckBank(String assignCheckBank) {
		this.assignCheckBank = assignCheckBank;
	}
	
	

}
