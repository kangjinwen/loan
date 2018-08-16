package com.company.modules.finance.service;

import java.util.List;
import java.util.Map;

import com.company.modules.common.domain.PubLoan;

/**
* User:    fzc
* DateTime:2016-08-26 05:51:23
* details: 放款,Service接口层
* source:  代码生成器
*/
public interface PubLoanService {

    /**
     * 放款表,插入数据
     * @param pubLoan 放款类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PubLoan pubLoan) throws Exception;

    /**
     * 放款表,修改数据
     * @param pubLoan 放款类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PubLoan pubLoan) throws Exception;

	/**
     * 放款表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PubLoan> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 放款表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PubLoan getItemInfoById(long id) throws Exception;
    
	/**
     * 放款表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PubLoan getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 放款表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
