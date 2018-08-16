package com.company.modules.finance.dao;

import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.common.domain.PubLoan;

/**
* User:    fzc
* DateTime:2016-08-26 05:51:23
* details: 放款,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PubLoanDao extends BaseDao<PubLoan,Long> {

    /**
     * 放款表,分页查询数据
     * @param map
     * @return List<PubLoan>
     */
    public List<PubLoan> getPageListByMap(Map<String, Object> map);
    
    /**
     * 放款表,根据id查询数据
     * @param id
     * @return PubLoan
     */
    public PubLoan getItemInfoById(long id);
    
    /**
     * 放款表,根据流程id查询数据
     * @param processInstanceId
     * @return PubLoan
     */
    public PubLoan getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 放款表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
