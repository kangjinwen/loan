package com.company.modules.audit.util.databean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.company.modules.audit.domain.HousBills;
import com.company.modules.audit.domain.HousLoanfees;
import com.company.modules.common.utils.databean.BasicServiceDataBean;

/**
 *	@Description 放款单据
 *  @author fzc,fzc@erongdu.com
 *  @CreatTime 2016年8月18日 下午3:23:12
 *  @since version 1.0.0
 */
public class LoanInfoDataBean extends BasicServiceDataBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private HousBills lend;//放款单
	private List<HousBills> loans;//放款单
	private HousLoanfees housLoanfees;//返费签单/代收服务费
	private BigDecimal collectionServiceFee;   
	private BigDecimal collectionRate;  
    private BigDecimal returnServiceFee;    
    private String collectionServiceName;
    private String collectionServiceCard;
    private String collectionServiceBank;
	 
    

	public BigDecimal getCollectionRate() {
		return collectionRate;
	}
	public void setCollectionRate(BigDecimal collectionRate) {
		this.collectionRate = collectionRate;
	}
	public BigDecimal getCollectionServiceFee() {
		return collectionServiceFee;
	}
	public void setCollectionServiceFee(BigDecimal collectionServiceFee) {
		this.collectionServiceFee = collectionServiceFee;
	}
	public BigDecimal getReturnServiceFee() {
		return returnServiceFee;
	}
	public void setReturnServiceFee(BigDecimal returnServiceFee) {
		this.returnServiceFee = returnServiceFee;
	}
	public String getCollectionServiceName() {
		return collectionServiceName;
	}
	public void setCollectionServiceName(String collectionServiceName) {
		this.collectionServiceName = collectionServiceName;
	}
	public String getCollectionServiceCard() {
		return collectionServiceCard;
	}
	public void setCollectionServiceCard(String collectionServiceCard) {
		this.collectionServiceCard = collectionServiceCard;
	}
	public String getCollectionServiceBank() {
		return collectionServiceBank;
	}
	public void setCollectionServiceBank(String collectionServiceBank) {
		this.collectionServiceBank = collectionServiceBank;
	}
	public HousBills getLend() {
		return lend;
	}
	public void setLend(HousBills lend) {
		this.lend = lend;
	}
	public List<HousBills> getLoans() {
		return loans;
	}
	public void setLoans(List<HousBills> loans) {
		this.loans = loans;
	}
	public HousLoanfees getHousLoanfees() {
		return housLoanfees;
	}
	public void setHousLoanfees(HousLoanfees housLoanfees) {
		this.housLoanfees = housLoanfees;
	}
	
}
