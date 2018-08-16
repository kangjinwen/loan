package com.company.common.dao;

import java.util.List;
import java.util.Map;

import com.company.common.utils.annotation.RDBatisDao;
import com.company.common.domain.PubProjectProcess;

/**
* User:    wulb
* DateTime:2016-08-06 12:02:17
* details: 项目流程关联,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PubProjectProcessDao extends BaseDao<PubProjectProcess,Long> {

    /**
     * 项目流程关联表,分页查询数据
     * @param map
     * @return List<PubProjectProcess>
     */
    public List<PubProjectProcess> getPageListByMap(Map<String, Object> map);
    
    /**
     * 项目流程关联表,根据id查询数据
     * @param id
     * @return PubProjectProcess
     */
    public PubProjectProcess getItemInfoById(long id);
    
    /**
     * 项目流程关联表,根据流程id查询数据
     * @param processInstanceId
     * @return PubProjectProcess
     */
    public PubProjectProcess getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 项目流程关联表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public PubProjectProcess getItemByProcessInstanceId(Long processInstanceId);
	
	/**
	 * 
	* @Description: 根据项目id获取对象 
	* @param @param ProjectId
	* @param @return    设定文件 
	* @return PubProjectProcess    返回类型 
	* @throws
	 */
	public PubProjectProcess getProjectProcessByProjectId(Long ProjectId);
	
	public PubProjectProcess getProjectProcessByProjectIdAndType(Long ProjectId);
}
