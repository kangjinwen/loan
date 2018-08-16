package com.company.modules.collateral.databean;

import java.io.Serializable;

import com.company.modules.common.utils.databean.BasicServiceDataBean;

public class CollateralTaskAssignDataBean extends BasicServiceDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String collateralRegistAssignee;
	
	
	private String collateralRelieveAssignee;


	public String getCollateralRegistAssignee() {
		return collateralRegistAssignee;
	}


	public void setCollateralRegistAssignee(String collateralRegistAssignee) {
		this.collateralRegistAssignee = collateralRegistAssignee;
	}


	public String getCollateralRelieveAssignee() {
		return collateralRelieveAssignee;
	}


	public void setCollateralRelieveAssignee(String collateralRelieveAssignee) {
		this.collateralRelieveAssignee = collateralRelieveAssignee;
	}
	
	

}
