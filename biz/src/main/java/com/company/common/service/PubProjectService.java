package com.company.common.service;

import java.util.List;
import java.util.Map;

import com.company.common.domain.PubProject;

/**
* User:    wulb
* DateTime:2016-08-06 11:39:36
* details: 项目,Service接口层
* source:  代码生成器
*/
public interface PubProjectService {

    /**
     * 项目表,插入数据
     * @param pubProject 项目类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PubProject pubProject) throws Exception;

    /**
     * 项目表,修改数据
     * @param pubProject 项目类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PubProject pubProject) throws Exception;

	/**
     * 项目表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PubProject> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 项目表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PubProject getItemInfoById(long id) throws Exception;
    
	/**
     * 项目表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PubProject getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;
    
    /**
     * 获取项目代码最大值
     * @param projectCode
     * @return
     * @throws Exception
     */
    public Map<String, Object> getMaxProjectCode(String projectCode)  throws Exception;

    /**
    * 项目表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
