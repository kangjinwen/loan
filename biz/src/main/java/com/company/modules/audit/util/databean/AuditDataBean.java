package com.company.modules.audit.util.databean;

import java.io.Serializable;

import com.company.common.domain.PlBorrow;
import com.company.common.domain.PlBorrowRequirement;
import com.company.modules.common.domain.PlProduct;
import com.company.modules.common.utils.databean.BasicServiceDataBean;

public class AuditDataBean extends BasicServiceDataBean implements Serializable{

	/**
	 * serialVersionUID:TODO
	 */
	private static final long serialVersionUID = 1L;
	private PlBorrowRequirement plBorrowRequirement;//借款需求表
	private PlProduct product;//产品表
	private PlBorrow borrow;//产品表
	
	public PlBorrowRequirement getPlBorrowRequirement() {
		return plBorrowRequirement;
	}
	public void setPlBorrowRequirement(PlBorrowRequirement plBorrowRequirement) {
		this.plBorrowRequirement = plBorrowRequirement;
	}
	public PlProduct getProduct() {
		return product;
	}
	public void setProduct(PlProduct product) {
		this.product = product;
	}
	public PlBorrow getBorrow() {
		return borrow;
	}
	public void setBorrow(PlBorrow borrow) {
		this.borrow = borrow;
	}
}
