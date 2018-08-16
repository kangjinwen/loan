package com.company.modules.instance.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.instance.domain.HousBorrowingBasics;

/**
* User:    wulb
* DateTime:2016-08-06 02:36:08
* details: 借款基本信息,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousBorrowingBasicsDao extends BaseDao<HousBorrowingBasics,Long> {

    /**
     * 借款基本信息表,分页查询数据
     * @param map
     * @return List<HousBorrowingBasics>
     */
    public List<HousBorrowingBasics> getPageListByMap(Map<String, Object> map);
    
    /**
     * 借款基本信息表,根据id查询数据
     * @param id
     * @return HousBorrowingBasics
     */
    public HousBorrowingBasics getItemInfoById(long id);
    
    /**
     * 借款基本信息表,根据流程id查询数据
     * @param processInstanceId
     * @return HousBorrowingBasics
     */
    public HousBorrowingBasics getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 借款基本信息表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public HousBorrowingBasics getItemInfoByConsultId(Long consultId);
	
	public int updateByMap(Map<String, Object> map);
}
