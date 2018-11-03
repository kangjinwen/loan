package com.company.common.dao;

import com.company.common.utils.annotation.RDBatisDao;
import com.company.common.domain.PlApprovalResults;
import java.util.List;
import java.util.Map;

@RDBatisDao
public interface PlApprovalResultsDao extends BaseDao<PlApprovalResults,Long> {
    /**
     * 借款需求信息表,分页查询数据
     * @param map
     * @return List<PlBorrowRequirement>
     */
    public List<PlApprovalResults> getPageListByMap(Map<String, Object> map);

    /**
     * 借款需求信息表,根据id查询数据
     * @param id
     * @return PlBorrowRequirement
     */
    public PlApprovalResults getItemInfoById(long id);

    /**
     * 借款需求信息表,根据流程id查询数据
     * @param processInstanceId
     * @return Map<String, Object>
     */
    public Map<String, Object> getItemInfoByProcessInstanceId(String processInstanceId);

    public List<Map<String, Object>> getItemInfosByProcessInstanceId(String processInstanceId);
    /**
     * 借款需求信息表,根据流程id查询数据
     * @param processInstanceId
     * @return PlBorrowRequirement
     */
    public PlApprovalResults getInfoByProcessInstanceId(String processInstanceId);

    /**
     * 借款需求信息表,删除数据
     * @param id 主键
     * @return
     */
    public int deleteById(long id);

    public PlApprovalResults getItemInfoByConsultId(Long consultId);

    public int updateByMap(Map<String, Object> map);
}