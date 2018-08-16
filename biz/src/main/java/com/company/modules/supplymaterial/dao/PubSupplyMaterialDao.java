package com.company.modules.supplymaterial.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.supplymaterial.domain.PubSupplymaterial;

/**
* User:    JDM
* DateTime:2016-08-17 11:31:42
* details: 补充资料,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
@SuppressWarnings("all")
public interface PubSupplyMaterialDao extends BaseDao<PubSupplymaterial,Long> {

    /**
     * 补充资料表,分页查询数据
     * @param map
     * @return List<Map>
     */
    public List<Map> getUnCompletePageListByMap(Map<String, Object> map);
    
    public List<Map> getCompletedPageListByMap(Map<String, Object> data);
    
    /**
     * 补充资料表,根据id查询数据
     * @param id
     * @return PubAttachment
     */
    public PubSupplymaterial getItemInfoById(long id);
    
    /**
     * 补充资料表,根据流程id查询数据
     * @param processInstanceId
     * @return PubAttachment
     */
    public PubSupplymaterial getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 补充资料表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public PubSupplymaterial getItemByProjectId(long id);

	public List<PubSupplymaterial> getItemList(Map<String, Object> paramMap);

	
}
