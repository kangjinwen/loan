package com.company.modules.advance.util.databean;

import java.io.Serializable;

import com.company.modules.advance.domain.HousAdvanceApply;
import com.company.modules.common.utils.databean.BasicServiceDataBean;

public class AdvanceConfirmTaskDataBean extends BasicServiceDataBean implements Serializable{

	/**
	 * serialVersionUID:TODO
	 *
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = 1L;
	
	private HousAdvanceApply housAdvanceApply;
	
	
	//private 

	public HousAdvanceApply getHousAdvanceApply() {
		return housAdvanceApply;
	}

	public void setHousAdvanceApply(HousAdvanceApply housAdvanceApply) {
		this.housAdvanceApply = housAdvanceApply;
	}	
	
}
