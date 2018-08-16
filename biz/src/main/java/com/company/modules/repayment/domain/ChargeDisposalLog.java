package com.company.modules.repayment.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 抵押品处置登记
 * @author JDM
 * @date 2016年11月4日
 *
 */
public class ChargeDisposalLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5815931766879708109L;
	/**
     * 主键
     */
    private Long id;
    /**
     * 创建者
     */
    private Long creator;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    

	/**
     * 获取主键
     * 
     * @return 主键
     */
    public Long getId(){
        return id;
    }
    
    /**
     * 设置主键
     * 
     * @param id 要设置的主键
     */
    public void setId(Long id){
        this.id = id;
    }
    /**
     * 获取创建者
     * 
     * @return 创建者
     */
    public Long getCreator(){
        return creator;
    }
    
    /**
     * 设置创建者
     * 
     * @param creator 要设置的创建者
     */
    public void setCreator(Long creator){
        this.creator = creator;
    }
    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCreateTime(){
        return createTime;
    }
    
    /**
     * 设置创建时间
     * 
     * @param createTime 要设置的创建时间
     */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    /**
     * 获取备注
     * 
     * @return 备注
     */
    public String getRemark(){
        return remark;
    }
    
    /**
     * 设置备注
     * 
     * @param remark 要设置的备注
     */
    public void setRemark(String remark){
        this.remark = remark;
    }
    /**
     * 获取流程实例ID
     * 
     * @return 流程实例ID
     */
    public String getProcessInstanceId(){
        return processInstanceId;
    }
    
    /**
     * 设置流程实例ID
     * 
     * @param processInstanceId 要设置的流程实例ID
     */
    public void setProcessInstanceId(String processInstanceId){
        this.processInstanceId = processInstanceId;
    }
    
}


