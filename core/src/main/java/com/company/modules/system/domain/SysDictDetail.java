package com.company.modules.system.domain;

import java.io.Serializable;


/**
 * 客户字典详细项信息表实体
 * @author wgc
 * @version 1.0
 * @since 2014-11-03
 */
public class SysDictDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 
     */
    private Long id;
    /**
     * 
     */
    private String itemCode;
    /**
     * 
     */
    private String itemValue;
    /**
     * 
     */
    private Long parentId;
    
    /**
     * 获取
     * 
     * @return 
     */
    public Long getId(){
        return id;
    }
    
    /**
     * 设置
     * 
     * @param id 要设置的
     */
    public void setId(Long id){
        this.id = id;
    }
    /**
     * 获取
     * 
     * @return 
     */
    public String getItemCode(){
        return itemCode;
    }
    
    /**
     * 设置
     * 
     * @param itemCode 要设置的
     */
    public void setItemCode(String itemCode){
        this.itemCode = itemCode;
    }
    /**
     * 获取
     * 
     * @return 
     */
    public String getItemValue(){
        return itemValue;
    }
    
    /**
     * 设置
     * 
     * @param itemValue 要设置的
     */
    public void setItemValue(String itemValue){
        this.itemValue = itemValue;
    }
    /**
     * 获取
     * 
     * @return 
     */
    public Long getParentId(){
        return parentId;
    }
    
    /**
     * 设置
     * 
     * @param parentId 要设置的
     */
    public void setParentId(Long parentId){
        this.parentId = parentId;
    }
}


