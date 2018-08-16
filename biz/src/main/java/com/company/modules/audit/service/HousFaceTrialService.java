package com.company.modules.audit.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.audit.domain.HousFaceTrial;

/**
* User:    fzc
* DateTime:2016-08-14 01:28:09
* details: 面审信息表,Service接口层
* source:  代码生成器
*/
public interface HousFaceTrialService {

    /**
     * 面审信息表表,插入数据
     * @param housFaceTrial 面审信息表类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousFaceTrial housFaceTrial) throws Exception;

    /**
     * 面审信息表表,修改数据
     * @param housFaceTrial 面审信息表类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousFaceTrial housFaceTrial) throws Exception;

	/**
     * 面审信息表表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousFaceTrial> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 面审信息表表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousFaceTrial getItemInfoById(long id) throws Exception;
    
	/**
     * 面审信息表表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public HousFaceTrial getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 面审信息表表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
