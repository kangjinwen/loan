package com.company.common.dao;

import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.common.domain.PlBorrow;

/**
* User:    wulb
* DateTime:2016-08-15 05:42:32
* details: 借款信息,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PlBorrowDao extends BaseDao<PlBorrow,Long> {

    /**
     * 借款信息表,分页查询数据
     * @param map
     * @return List<PlBorrow>
     */
    public List<PlBorrow> getPageListByMap(Map<String, Object> map);

    /**
     * 借款信息表,根据id查询数据
     * @param id
     * @return PlBorrow
     */
    public PlBorrow getItemInfoById(long id);

    /**
     * 借款信息表,根据流程id查询数据
     * @param processInstanceId
     * @return PlBorrow
     */
    public PlBorrow getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 借款信息表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

    /**
     * 借款信息表,根据条件查询数据
     * @param data
     * @return Map<String, Object>
     */
	public Map<String, Object> getItemInfoByMap(Map<String, Object> data);

	public long updatePlBorrowById(Map<String, Object> data);
}
