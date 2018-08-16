package com.company.modules.supplymaterial.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 补充资料实体
 * @author JDM
 * @version 1.0
 * @since 2016-08-18 03:51:48
 */
public class PubSupplymaterialLog implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
	public static final String OPERATE_TYPE_OTHER_MATERIAL = "他项材料";
	public static final String OPERATE_TYPE_OTHER_MATERIAL_NAME = "他项材料名称";
	public static final String OPERATE_TYPE_Notarization_MATERIAL = "公证材料";
	
	/**
    * id
    */
    private Long id;
    /**
     * 补充材料id
     */
     private Long supplymaterialId;
    /**
    * 操作者id
    */
    private Long operatorId;
    /**
     * 操作者名称
     */
    private String operatorName;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 补充类型
    */
    private String operateType;
    
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
     * 获取补充材料id
     *
     * @return 补充材料id
     */
     public Long getSupplymaterialId(){
         return supplymaterialId;
     }
     
     /**
      * 设置补充材料id
      * 
      * @param supplymaterialId 要设置的补充材料id
      */
     public void setSupplymaterialId(Long supplymaterialId){
         this.supplymaterialId = supplymaterialId;
     }
    /**
    * 获取操作者id
    *
    * @return 操作者id
    */
    public Long getOperatorId(){
        return operatorId;
    }
    
    /**
     * 设置操作者id
     * 
     * @param operatorId 要设置的操作者id
     */
    public void setOperatorId(Long operatorId){
        this.operatorId = operatorId;
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
    * 获取补充类型
    *
    * @return 补充类型
    */
    public String getOperateType(){
        return operateType;
    }
    
    /**
     * 设置补充类型
     * 
     * @param operateType 要设置的补充类型
     */
    public void setOperateType(String operateType){
        this.operateType = operateType;
    }

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
    
}