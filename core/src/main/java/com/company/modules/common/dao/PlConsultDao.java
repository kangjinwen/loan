package com.company.modules.common.dao;

import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.domain.PlConsultFee;

/**
* User:    wulb
* DateTime:2016-08-08 01:01:45
* details: 咨询信息,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PlConsultDao extends BaseDao<PlConsult,Long> {

    /**
     * 咨询信息表,分页查询数据
     * @param map
     * @return List<PlConsult>
     */
    public List<PlConsult> getPageListByMap(Map<String, Object> map);
    
    /**
     * 咨询信息表,分页查询数据(下户费收取成功)
     * @param data
     * @return
     * @throws Exception
     */
    public List<PlConsultFee> getPlConsultFeeByMap(Map<String , Object> data) throws Exception;
    
    /**
     * 咨询信息表,分页查询数据(垫资申请记录)
     * @param data
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> getDoPlConsultByadvanceApplyList(Map<String , Object> data) throws Exception;
    
    
    /**
     * 咨询信息表,分页查询数据(姿势申请列表)
     * @param data
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> getPlConsultByadvanceApplyList(Map<String , Object> data) throws Exception;
    
    
    /**
     * 咨询信息表,根据id查询数据
     * @param id
     * @return PlConsult
     */
    public PlConsult getItemInfoById(long id);
    
    /**
     * 咨询信息表,根据流程id查询数据
     * @param processInstanceId
     * @return PlConsult
     */
    public PlConsult getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 咨询信息表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

    /**
     * 咨询信息表,根据projectId查询数据
     * @param projectId
     * @return PlConsult
     */
	public PlConsult getPlConsultByProjectId(long projectId);
	
	public List<Map<String, Object>> getPlConsultList(Map<String, Object> data);
}
