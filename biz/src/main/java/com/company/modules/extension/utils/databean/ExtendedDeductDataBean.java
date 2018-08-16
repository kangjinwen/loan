package com.company.modules.extension.utils.databean;

import java.io.Serializable;

import com.company.common.domain.PubProcessBranching;
import com.company.modules.common.utils.databean.BasicServiceDataBean;

public class ExtendedDeductDataBean extends BasicServiceDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	 private PubProcessBranching pubProcessBranching;
	 
	 
	public PubProcessBranching getPubProcessBranching() {
		return pubProcessBranching;
	}
	public void setPubProcessBranching(PubProcessBranching pubProcessBranching) {
		this.pubProcessBranching = pubProcessBranching;
	}
	    
}
