package com.company.modules.common.dao;

import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.common.domain.PlProduct;
import org.apache.ibatis.annotations.Param;

/**
* User:    wulb
* DateTime:2016-08-15 05:50:16
* details: 产品信息,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PlProductDao extends BaseDao<PlProduct,Long> {

    /**
     * 产品信息表,分页查询数据
     * @param map
     * @return List<PlProduct>
     */
	public List<Map<String, Object>> getPageListByMap(Map<String, Object> map);
    
    /**
     * 产品信息表,根据id查询数据
     * @param id
     * @return PlProduct
     */
    public PlProduct getItemInfoById(long id);
    
    /**
     * 产品信息表,根据流程id查询数据
     * @param processInstanceId
     * @return PlProduct
     */
    public PlProduct getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 产品信息表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public Map<String, Object> getItemMapById(long productId);

	Integer getChannelIdByProductId(@Param("productId") Integer productId);

}
