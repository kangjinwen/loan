package com.company.common.dao;

import java.util.List;
import java.util.Map;

import com.company.common.domain.PubRepayloaninfo;
import com.company.common.utils.annotation.RDBatisDao;

/**
* User:    fzc
* DateTime:2016-08-28 11:07:59
* details: 放款,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PubRepayloaninfoDao extends BaseDao<PubRepayloaninfo,Long> {

    /**
     * 放款表,分页查询数据
     * @param map
     * @return List<PubRepayloaninfo>
     */
    public List<PubRepayloaninfo> getPageListByMap(Map<String, Object> map);
    
    /**
     * 放款表,根据id查询数据
     * @param id
     * @return PubRepayloaninfo
     */
    public PubRepayloaninfo getItemInfoById(long id);
    
    /**
     * 放款表,根据流程id查询数据
     * @param processInstanceId
     * @return PubRepayloaninfo
     */
    public PubRepayloaninfo getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 放款表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

    /**
     * 根据BorrowId查询数据
     * @param id
     * @return
     */
	public PubRepayloaninfo getItemInfoByBorrowId(long id);
	
}
