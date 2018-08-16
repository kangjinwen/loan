package com.company.modules.audit.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.audit.domain.HousLoanfees;

/**
* User:    fzc
* DateTime:2016-08-17 03:54:15
* details: 返费签单/代收服务费,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousLoanfeesDao extends BaseDao<HousLoanfees,Long> {

    /**
     * 返费签单/代收服务费表,分页查询数据
     * @param map
     * @return List<HousLoanfees>
     */
    public List<HousLoanfees> getPageListByMap(Map<String, Object> map);
    
    /**
     * 返费签单/代收服务费表,根据id查询数据
     * @param id
     * @return HousLoanfees
     */
    public HousLoanfees getItemInfoById(long id);
    
    /**
     * 返费签单/代收服务费表,根据流程id查询数据
     * @param processInstanceId
     * @return HousLoanfees
     */
    public HousLoanfees getItemInfoByProcessInstanceId(String processInstanceId);
    
    /**
     * 返费签单/代收服务费表,根据项目id查询数据
     * @param processInstanceId
     * @return HousLoanfees
     */
    public HousLoanfees getItemInfoByProjectId(long projectId);

    /**
    * 返费签单/代收服务费表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

    /**
     * @description 获取放款表单信息
     * @param processInstanceId
     * @return
     * @author fzc
     * @return Map<String,Object>
     * @since  1.0.0
     */
	public Map<String, Object> getLoanFormInfo(String processInstanceId);
}
