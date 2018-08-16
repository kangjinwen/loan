package com.company.modules.notary.databean;

import java.io.Serializable;

import com.company.common.domain.PlBorrowRequirement;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.notary.domain.HousNotarizationRegistration;

public class NotaryRegistDataBean extends BasicServiceDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HousNotarizationRegistration housNotarizationRegistration;
	
	private PlBorrowRequirement plBorrowRequirement;
	
	private HousBorrowingBasics housBorrowingBasics;
	
	private String productId;
	
	

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public HousNotarizationRegistration getHousNotarizationRegistration() {
		return housNotarizationRegistration;
	}

	public void setHousNotarizationRegistration(
			HousNotarizationRegistration housNotarizationRegistration) {
		this.housNotarizationRegistration = housNotarizationRegistration;
	}

	public PlBorrowRequirement getPlBorrowRequirement() {
		return plBorrowRequirement;
	}

	public void setPlBorrowRequirement(PlBorrowRequirement plBorrowRequirement) {
		this.plBorrowRequirement = plBorrowRequirement;
	}

	public HousBorrowingBasics getHousBorrowingBasics() {
		return housBorrowingBasics;
	}

	public void setHousBorrowingBasics(HousBorrowingBasics housBorrowingBasics) {
		this.housBorrowingBasics = housBorrowingBasics;
	}
	
}
