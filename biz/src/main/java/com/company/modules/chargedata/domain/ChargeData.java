package com.company.modules.chargedata.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.company.common.utils.DateUtil;

/**
 * 押品管理实体
 * @author JDM
 * @version 1.0
 * @since 2016-11-30 02:08:46
 */
public class ChargeData implements Serializable {
	
	

	private static final Long serialVersionUID = 1L;
	
	private Integer pageNum = 1;
	private Integer pageSize = 10;
	
	/**
    * id
    */
    private Long id;
    /**
    * 押品名称
    */
    private String name;
    /**
    * 押品状态（1.在库2.借出3.出库）
    */
    private Long status;
    
    public static final long STATUS_IN_STORE = 1;
    public static final long STATUS_LEND_OUT = 2;
    public static final long STATUS_OUT_STORE = 3;
    
    /**
    * 备注
    */
    private String remark;
    /**
    * 创建时间
    */
    @DateTimeFormat(pattern=DateUtil.DATE_PATTERN)
    private Date createTime;
    /**
    * 流程id
    */
    private String processInstanceId;
    
    /**
     * 档案编号
     */
     private String fileNumber;
     
     private List<ChargeData> chargeDatas;
    
    /**
    * 获取id
    *
    * @return id
    */
    public Long getId(){
        return id;
    }
    
    /**
     * 设置id
     * 
     * @param Long 要设置的id
     */
    public void setId(Long id){
        this.id = id;
    }
    
    /**
    * 获取押品名称
    *
    * @return 押品名称
    */
    public String getName(){
        return name;
    }
    
    /**
     * 设置押品名称
     * 
     * @param name 要设置的押品名称
     */
    public void setName(String name){
        this.name = name;
    }

    /**
    * 获取押品状态（1.在库2.借出3.出库）
    *
    * @return 押品状态（1.在库2.借出3.出库）
    */
    public Long getStatus(){
        return status;
    }
    
    /**
     * 设置押品状态（1.在库2.借出3.出库）
     * 
     * @param status 要设置的押品状态（1.在库2.借出3.出库）
     */
    public void setStatus(Long status){
        this.status = status;
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
    * 获取流程id
    *
    * @return 流程id
    */
    public String getProcessInstanceId(){
        return processInstanceId;
    }
    
    /**
     * 设置流程id
     * 
     * @param processInstanceId 要设置的流程id
     */
    public void setProcessInstanceId(String processInstanceId){
        this.processInstanceId = processInstanceId;
    }
    
    /**
     * 获取档案编号
     *
     * @return 档案编号
     */
     public String getFileNumber(){
         return fileNumber;
     }
     
     /**
      * 设置档案编号
      * 
      * @param fileNumber 要设置的档案编号
      */
     public void setFileNumber(String fileNumber){
         this.fileNumber = fileNumber;
     }

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<ChargeData> getChargeDatas() {
		return chargeDatas;
	}

	public void setChargeDatas(List<ChargeData> chargeDatas) {
		this.chargeDatas = chargeDatas;
	}
    
    

}