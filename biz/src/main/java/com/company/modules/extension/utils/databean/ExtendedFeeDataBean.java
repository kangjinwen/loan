package com.company.modules.extension.utils.databean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.company.common.domain.PubProcessBranching;
import com.company.modules.common.utils.databean.BasicServiceDataBean;

public class ExtendedFeeDataBean extends BasicServiceDataBean implements Serializable {
	private static final long serialVersionUID = 1L;
    private String collectionForm;//收取形式
    private BigDecimal actualExtensionFee;//实收金额
    private String financeSpecialist;//收取形式
    private PubProcessBranching pubProcessBranching;
    

	public String getCollectionForm() {
		return collectionForm;
	}
	public void setCollectionForm(String collectionForm) {
		this.collectionForm = collectionForm;
	}
	public BigDecimal getActualExtensionFee() {
		return actualExtensionFee;
	}
	public void setActualExtensionFee(BigDecimal actualExtensionFee) {
		this.actualExtensionFee = actualExtensionFee;
	}
	public String getFinanceSpecialist() {
		return financeSpecialist;
	}
	public void setFinanceSpecialist(String financeSpecialist) {
		this.financeSpecialist = financeSpecialist;
	}
	public PubProcessBranching getPubProcessBranching() {
		return pubProcessBranching;
	}
	public void setPubProcessBranching(PubProcessBranching pubProcessBranching) {
		this.pubProcessBranching = pubProcessBranching;
	}
    
    
}
