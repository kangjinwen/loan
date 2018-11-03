package com.company.modules.instance.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.instance.domain.HousPropertyInformation;

/**
* User:    wulb
* DateTime:2016-08-06 02:28:59
* details: 房产信息,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousPropertyInformationDao extends BaseDao<HousPropertyInformation,Long> {

    /**
     * 房产信息表,分页查询数据
     * @param map
     * @return List<HousPropertyInformation>
     */
    public List<HousPropertyInformation> getPageListByMap(Map<String, Object> map);
    
    /**
     * 房产信息表,根据id查询数据
     * @param id
     * @return HousPropertyInformation
     */
    public HousPropertyInformation getItemInfoById(long id);
    
    /**
     * 房产信息表,根据流程id查询数据
     * @param processInstanceId
     * @return HousPropertyInformation
     */
    public HousPropertyInformation getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 房产信息表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public HousPropertyInformation getItemInfoByConsultId(Long consultId);

	public String getHousAddress(Long propertyAddressId);

    Map<String,Object> getWhetherLoanByHomeNum(String homeNum);
}
