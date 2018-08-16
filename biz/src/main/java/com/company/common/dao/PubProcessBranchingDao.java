package com.company.common.dao;

import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.common.domain.PubProcessBranching;

/**
* User:    wulb
* DateTime:2016-08-10 05:46:59
* details: 贷后信息,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PubProcessBranchingDao extends BaseDao<PubProcessBranching,Long> {

    /**
     * 贷后信息表,分页查询数据
     * @param map
     * @return List<PubProcessBranching>
     */
    public List<PubProcessBranching> getPageListByMap(Map<String, Object> map);
    
    /**
     * 贷后信息表,根据id查询数据
     * @param id
     * @return PubProcessBranching
     */
    public PubProcessBranching getItemInfoById(long id);
    
    /**
     * 贷后信息表,根据流程id查询数据
     * @param processInstanceId
     * @return PubProcessBranching
     */
    public PubProcessBranching getItemInfoByProcessInstanceId(String processInstanceId);
    
    public PubProcessBranching getItemInfoByBranchingProcessInstanceId(String branchingProcessId);
    
    public PubProcessBranching getItemByMap(Map<String, Object> data);

    /**
    * 贷后信息表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public void updatePubProcessBranchingById(Map<String, Object> param);
}
