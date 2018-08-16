package com.company.common.service;

import java.util.Map;
import java.util.List;
import  com.company.common.domain.PubProjectProcess;

/**
* User:    wulb
* DateTime:2016-08-06 12:02:17
* details: 项目流程关联,Service接口层
* source:  代码生成器
*/
public interface PubProjectProcessService {

    /**
     * 项目流程关联表,插入数据
     * @param pubProjectProcess 项目流程关联类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PubProjectProcess pubProjectProcess) throws Exception;

    /**
     * 项目流程关联表,修改数据
     * @param pubProjectProcess 项目流程关联类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PubProjectProcess pubProjectProcess) throws Exception;

	/**
     * 项目流程关联表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PubProjectProcess> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 项目流程关联表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PubProjectProcess getItemInfoById(long id) throws Exception;
    
	/**
     * 项目流程关联表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PubProjectProcess getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 项目流程关联表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
