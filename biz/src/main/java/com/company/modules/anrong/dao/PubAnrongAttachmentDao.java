package com.company.modules.anrong.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.anrong.domain.PubAnrongAttachment;

/**
* User:    mcwang
* DateTime:2016-09-13 03:01:50
* details: 安融附件,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PubAnrongAttachmentDao extends BaseDao<PubAnrongAttachment,Long> {

    /**
     * 安融附件表,分页查询数据
     * @param map
     * @return List<PubAnrongAttachment>
     */
    public List<PubAnrongAttachment> queryAttachmentList(Map<String, Object> map);
    
    /**
     * 安融附件表,根据id查询数据
     * @param id
     * @return PubAnrongAttachment
     */
    public PubAnrongAttachment getItemInfoById(long id);
    

    /**
    * 安融附件表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
