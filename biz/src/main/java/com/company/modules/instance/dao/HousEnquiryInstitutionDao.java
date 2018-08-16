package com.company.modules.instance.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.instance.domain.HousEnquiryInstitution;

/**
* User:    wulb
* DateTime:2016-08-12 01:50:43
* details: 查询机构信息,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousEnquiryInstitutionDao extends BaseDao<HousEnquiryInstitution,Long> {

    /**
     * 查询机构信息表,分页查询数据
     * @param map
     * @return List<HousEnquiryInstitution>
     */
    public List<HousEnquiryInstitution> getPageListByMap(Map<String, Object> map);
    
    /**
     * 查询机构信息表,根据id查询数据
     * @param id
     * @return HousEnquiryInstitution
     */
    public HousEnquiryInstitution getItemInfoById(long id);
    
    /**
     * 查询机构信息表,根据流程id查询数据
     * @param processInstanceId
     * @return List<HousEnquiryInstitution>
     */
    public List<HousEnquiryInstitution> getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 查询机构信息表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public List<HousEnquiryInstitution> getItemInfoByConsultId(Long consultId);
}
