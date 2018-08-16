package com.company.common.dao;

import java.util.List;
import java.util.Map;

import com.company.common.domain.PubProject;
import com.company.common.utils.annotation.RDBatisDao;

/**
* User:    wulb
* DateTime:2016-08-06 11:39:36
* details: 项目,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PubProjectDao extends BaseDao<PubProject,Long> {

    /**
     * 项目表,分页查询数据
     * @param map
     * @return List<PubProject>
     */
    public List<PubProject> getPageListByMap(Map<String, Object> map);
    
    /**
     * 项目表,根据id查询数据
     * @param id
     * @return PubProject
     */
    public PubProject getItemInfoById(long id);
    
    /**
     * 项目表,根据流程id查询数据
     * @param processInstanceId
     * @return PubProject
     */
    public PubProject getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 项目表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public Map<String, Object> getMaxProjectCode(String projectCode);
}
