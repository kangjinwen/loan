package com.company.modules.advance.util.databean;

import java.io.Serializable;

import com.company.modules.advance.domain.HousAdvanceNotarize;
import com.company.modules.common.utils.databean.BasicServiceDataBean;

public class AdvanceNotarizeTaskDataBean extends BasicServiceDataBean implements Serializable{

	/**
	 * serialVersionUID:TODO
	 *
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = 1L;
	
	private HousAdvanceNotarize housAdvanceNotarize;
	
	private String oldProcessInstanceId;

	public HousAdvanceNotarize getHousAdvanceNotarize() {
		return housAdvanceNotarize;
	}

	public void setHousAdvanceNotarize(HousAdvanceNotarize housAdvanceNotarize) {
		this.housAdvanceNotarize = housAdvanceNotarize;
	}

	public String getOldProcessInstanceId() {
		return oldProcessInstanceId;
	}

	public void setOldProcessInstanceId(String oldProcessInstanceId) {
		this.oldProcessInstanceId = oldProcessInstanceId;
	}
	
	

	
}
