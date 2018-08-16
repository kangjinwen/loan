package com.company.common.dao;

import java.util.List;
import java.util.Map;

import com.company.common.utils.annotation.RDBatisDao;
import com.company.common.domain.PlBorrowRequirement;

/**
* User:    wulb
* DateTime:2016-08-08 02:01:19
* details: 借款需求信息,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PlBorrowRequirementDao extends BaseDao<PlBorrowRequirement,Long> {

    /**
     * 借款需求信息表,分页查询数据
     * @param map
     * @return List<PlBorrowRequirement>
     */
    public List<PlBorrowRequirement> getPageListByMap(Map<String, Object> map);

    /**
     * 借款需求信息表,根据id查询数据
     * @param id
     * @return PlBorrowRequirement
     */
    public PlBorrowRequirement getItemInfoById(long id);

    /**
     * 借款需求信息表,根据流程id查询数据
     * @param processInstanceId
     * @return Map<String, Object>
     */
    public Map<String, Object> getItemInfoByProcessInstanceId(String processInstanceId);

    /**
     * 借款需求信息表,根据流程id查询数据
     * @param processInstanceId
     * @return PlBorrowRequirement
     */
    public PlBorrowRequirement getInfoByProcessInstanceId(String processInstanceId);

    /**
    * 借款需求信息表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public PlBorrowRequirement getItemInfoByConsultId(Long consultId);

	public int updateByMap(Map<String, Object> map);
}
