package com.company.common.dao;

import java.util.List;
import java.util.Map;

import com.company.common.domain.PubRepaymentdetail;
import com.company.common.utils.annotation.RDBatisDao;

/**
* User:    fzc
* DateTime:2016-08-28 10:30:16
* details: 放款,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PubRepaymentdetailDao extends BaseDao<PubRepaymentdetail,Long> {

    /**
     * 放款表,分页查询数据
     * @param map
     * @return List<PubRepaymentdetail>
     */
    public List<PubRepaymentdetail> getPageListByMap(Map<String, Object> map);
    
    /**
     * 放款表,根据id查询数据
     * @param id
     * @return PubRepaymentdetail
     */
    public PubRepaymentdetail getItemInfoById(long id);
    
    /**
     * 放款表,根据流程id查询数据
     * @param processInstanceId
     * @return PubRepaymentdetail
     */
    public PubRepaymentdetail getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 放款表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
    
    /**
     * @description 获取最新一期未还款记录
     * @return
     * @author fzc
     * @return PubRepaymentdetail
     * @since  1.0.0
     */
    public PubRepaymentdetail getMinUnPayedRepaymentdetail(Map<String, Object> map);

	public PubRepaymentdetail getItemInfoByParams(Map<String, Object> params);
}
