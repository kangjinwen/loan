package com.company.modules.chargedata.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 押品管理实体
 * @author JDM
 * @version 1.0
 * @since 2016-12-05 10:00:28
 */
public class ChargeDataLog implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
	private Integer pageNum = 1;
	private Integer pageSize = 10;
	
	/**
    * id
    */
    private Long id;
    /**
    * 借出人/归还人
    */
    private String name;
    /**
    * 借出时间/归还时间
    */
    private Date createTime;
    /**
    * 备注
    */
    private String remark;
    /**
    * 押品资料id
    */
    private Long chargeDataId;
    /**
    * 流程id
    */
    private String processInstanceId;
    /**
    * 类型(lend.借出return.归还)
    */
    private String type;
    
    public static final String TYPE_LEND_OUT =  "借出";
    public static final String TYPE_RETURN =  "归还";
    
    /**
     * 登记时间
     */
     private Date checkInTime;
     /**
     * 登记人
     */
     private String creator;
    
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
    * 获取借出人/归还人
    *
    * @return 借出人/归还人
    */
    public String getName(){
        return name;
    }
    
    /**
     * 设置借出人/归还人
     * 
     * @param name 要设置的借出人/归还人
     */
    public void setName(String name){
        this.name = name;
    }

    /**
    * 获取借出时间/归还时间
    *
    * @return 借出时间/归还时间
    */
    public Date getCreateTime(){
        return createTime;
    }
    
    /**
     * 设置借出时间/归还时间
     * 
     * @param createTime 要设置的借出时间/归还时间
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
    * 获取押品资料id
    *
    * @return 押品资料id
    */
    public Long getChargeDataId(){
        return chargeDataId;
    }
    
    /**
     * 设置押品资料id
     * 
     * @param chargeDataId 要设置的押品资料id
     */
    public void setChargeDataId(Long chargeDataId){
        this.chargeDataId = chargeDataId;
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
    * 获取类型(1.借出2.归还)
    *
    * @return 类型(1.借出2.归还)
    */
    public String getType(){
        return type;
    }
    
    /**
     * 设置类型(1.借出2.归还)
     * 
     * @param type 要设置的类型(1.借出2.归还)
     */
    public void setType(String type){
        this.type = type;
    }
    
    /**
     * 获取登记时间
     *
     * @return 登记时间
     */
     public Date getCheckInTime(){
         return checkInTime;
     }
     
     /**
      * 设置登记时间
      * 
      * @param checkInTime 要设置的登记时间
      */
     public void setCheckInTime(Date checkInTime){
         this.checkInTime = checkInTime;
     }

     /**
     * 获取登记人
     *
     * @return 登记人
     */
     public String getCreator(){
         return creator;
     }
     
     /**
      * 设置登记人
      * 
      * @param creator 要设置的登记人
      */
     public void setCreator(String creator){
         this.creator = creator;
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
    
}