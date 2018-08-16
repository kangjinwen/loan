package com.company.common.service;

import java.util.Map;
import java.util.List;
import  com.company.common.domain.PubProcessBranching;

/**
* User:    wulb
* DateTime:2016-08-10 05:46:59
* details: 贷后信息,Service接口层
* source:  代码生成器
*/
public interface PubProcessBranchingService {

    /**
     * 贷后信息表,插入数据
     * @param pubProcessBranching 贷后信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PubProcessBranching pubProcessBranching) throws Exception;

    /**
     * 贷后信息表,修改数据
     * @param pubProcessBranching 贷后信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PubProcessBranching pubProcessBranching) throws Exception;

	/**
     * 贷后信息表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PubProcessBranching> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 贷后信息表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PubProcessBranching getItemInfoById(long id) throws Exception;
    
	/**
     * 贷后信息表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PubProcessBranching getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 贷后信息表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
