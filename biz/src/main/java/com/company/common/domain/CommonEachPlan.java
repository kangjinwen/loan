package com.company.common.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.company.modules.common.utils.EachPlan;

/**
 * 每期还款计划
 * 
 * @author：xx
 * @version 1.0
 * @since 2014年7月15日
 */
public class CommonEachPlan implements EachPlan{

    /** 本金 */
    private double capital;
    /** 利息 */
    private double interest;
    /** 净收利息（扣除管理费后） */
    private double netInterest;
    /** 本息 */
    private double total;
    /** 净收本息（扣除管理费后） */
    private double netTotal;
    /** 未还本息 */
    private double needRepay;
    /** 开始计息日 */
    private Date interestTime;
    /** 还款日 */
    private Date repayTime;
    /** 服务费  */
    private BigDecimal serviceFee;
    /** 停车费  */
    private BigDecimal parkingFee;
    /** 其他费用 */
    private BigDecimal otherFee;
    /** 违约金 */
    private BigDecimal liquidatedDamages;
    /** 期数 */
    private Integer period;
    

    /**
     * 获取本金
     * 
     * @return 本金
     */
    public double getCapital() {
        return capital;
    }

    /**
     * 设置本金
     * 
     * @param capital 要设置的本金
     */
    public void setCapital(double capital) {
        this.capital = capital;
    }

    /**
     * 获取利息
     * 
     * @return 利息
     */
    public double getInterest() {
        return interest;
    }

    /**
     * 设置利息
     * 
     * @param interest 要设置的利息
     */
    public void setInterest(double interest) {
        this.interest = interest;
    }

    /**
     * 获取净收利息（扣除管理费后）
     * 
     * @return 净收利息（扣除管理费后）
     */
    public double getNetInterest() {
        return netInterest;
    }

    /**
     * 设置净收利息（扣除管理费后）
     * 
     * @param netInterest 要设置的净收利息（扣除管理费后）
     */
    public void setNetInterest(double netInterest) {
        this.netInterest = netInterest;
    }

    /**
     * 获取本息
     * 
     * @return 本息
     */
    public double getTotal() {
        return total;
    }

    /**
     * 设置本息
     * 
     * @param total 要设置的本息
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * 获取净收本息（扣除管理费后）
     * 
     * @return 净收本息（扣除管理费后）
     */
    public double getNetTotal() {
        return netTotal;
    }

    /**
     * 设置净收本息（扣除管理费后）
     * 
     * @param netTotal 要设置的净收本息（扣除管理费后）
     */
    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }

    /**
     * 获取未还本息
     * 
     * @return 未还本息
     */
    public double getNeedRepay() {
        return needRepay;
    }

    /**
     * 设置未还本息
     * 
     * @param needRepay 要设置的未还本息
     */
    public void setNeedRepay(double needRepay) {
        this.needRepay = needRepay;
    }

    /**
     * 获取开始计息日
     * 
     * @return 开始计息日
     */
    public Date getInterestTime() {
        return interestTime;
    }

    /**
     * 设置开始计息日
     * 
     * @param interestTime 要设置的开始计息日
     */
    public void setInterestTime(Date interestTime) {
        this.interestTime = interestTime;
    }

    /**
     * 获取还款日
     * 
     * @return 还款日
     */
    public Date getRepayTime() {
        return repayTime;
    }

    /**
     * 设置还款日
     * 
     * @param repayTime 要设置的还款日
     */
    public void setRepayTime(Date repayTime) {
        this.repayTime = repayTime;
    }
    /**
     * 获取服务费
     * 
     * @return 服务费
     */
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	/**
     * 设置服务费
     * 
     * @param serviceFee 要设置的服务费
     */
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	 /**
     * 获取停车费
     * 
     * @return 停车费
     */
	public BigDecimal getParkingFee() {
        return parkingFee;
    }
	/**
     * 设置停车费
     * 
     * @param parkingFee 要设置的停车费
     */
    public void setParkingFee(BigDecimal parkingFee) {
        this.parkingFee = parkingFee;
    }
    /**
     * 获取其他费用
     * 
     * @return 其他费用
     */
    public BigDecimal getOtherFee() {
        return otherFee;
    }
    /**
     * 设置其他费用
     * 
     * @param otherFee 要设置的其他费用
     */
    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }
    /**
     * 获取违约金
     * 
     * @return 违约金
     */
	public BigDecimal getLiquidatedDamages() {
		return liquidatedDamages;
	}
	/**
     * 设置违约金
     * 
     * @param liquidatedDamages 要设置的违约金
     */
	public void setLiquidatedDamages(BigDecimal liquidatedDamages) {
		this.liquidatedDamages = liquidatedDamages;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	@Override
	public String toString() {
		return "CommonEachPlan [capital=" + capital + ", interest=" + interest
				+ ", netInterest=" + netInterest + ", total=" + total
				+ ", netTotal=" + netTotal + ", needRepay=" + needRepay
				+ ", interestTime=" + interestTime + ", repayTime=" + repayTime
				+ ", serviceFee=" + serviceFee + ", parkingFee=" + parkingFee
				+ ", otherFee=" + otherFee + ", liquidatedDamages="
				+ liquidatedDamages + "]";
	}
}
