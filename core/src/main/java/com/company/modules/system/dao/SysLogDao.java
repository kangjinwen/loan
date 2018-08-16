package com.company.modules.system.dao;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.system.domain.SysLog;

/**
 * 
 * 系统操作日志DAO接口
 * @version 1.0
 * @author 吴国成
 * @created 2014年9月23日 上午9:44:19
 */
@RDBatisDao
public interface SysLogDao extends BaseDao<SysLog,Long> {
	
}
