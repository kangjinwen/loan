package com.company.modules.anrong.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.anrong.domain.PubAnrongAttachment;

/**
* User:    mcwang
* DateTime:2016-09-13 03:01:50
* details: 安融附件,Service接口层
* source:  代码生成器
*/
public interface PubAnrongAttachmentService {

    /**
     * 安融附件表,插入数据
     * @param pubAnrongAttachment 安融附件类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PubAnrongAttachment pubAnrongAttachment) throws Exception;

    /**
     * 安融附件表,修改数据
     * @param pubAnrongAttachment 安融附件类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PubAnrongAttachment pubAnrongAttachment) throws Exception;

	/**
     * 安融附件表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PubAnrongAttachment> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 安融附件表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PubAnrongAttachment getItemInfoById(long id) throws Exception;
    
	/**
     * 安融附件表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PubAnrongAttachment getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 安融附件表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
