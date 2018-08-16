package com.company.modules.credit.dao;

import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.credit.domain.PubDundetail;

/**
 * 催收记录表DAO接口
 * @author wgc
 * @version 1.0
 * @since 2014-11-17
 */
@RDBatisDao
public interface PubDundetailDao extends BaseDao<PubDundetail, Long> {

    /**
     * 根据ID更新  通用更新
     * @param map
     * @return Boolean
     * @throws PersistentDataException
     */ 
    Boolean updateDundetailById(Map<String , Object> data);
    
    /**
     * 分页查询出的列表
     * @param mapdata
     * @return 分页列表
     * @throws PersistentDataException
     */
    List<? extends PubDundetail> getDundetailPageList(Map<String, Object> data);
        
    /**
     * 获取总记录数
     * @param param
     * @return 记录数
     * @throws PersistentDataException
     */
    int getDundetailCount(Map<String, Object> data);

    /**
     * 
     * @param data
     * @return
     */
    List<PubDundetail> getPageListByMap(Map<String, Object> data);
    
    /**
     * 
     * @param data
     * @return
     */
    List<Map<String, Object>> getDundetailAndRepaymentList(Map<String, Object> data);
    
}
