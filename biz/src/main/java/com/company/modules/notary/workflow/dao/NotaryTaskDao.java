package com.company.modules.notary.workflow.dao;

import java.util.List;
import java.util.Map;

import com.company.common.utils.annotation.RDBatisDao;

@RDBatisDao
public interface NotaryTaskDao {
	
	 /**
     * 
    * @Description: 查询公证登记信息
    * @param @param map
    * @param @return    设定文件 
    * @author wangmc
    * @return List<Map<String,Object>>    返回类型 
    * @throws
     */
    public List<Map<String, Object>> getUnDoneRegistListByMap(Map<String, Object> map);
    
    public List<Map<String, Object>> getDoneRegistListByMap(Map<String, Object> map);

}
