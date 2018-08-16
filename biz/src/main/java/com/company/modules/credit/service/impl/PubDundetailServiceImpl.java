package com.company.modules.credit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.credit.dao.PubDundetailDao;
import com.company.modules.credit.domain.PubDundetail;
import com.company.modules.credit.service.PubDundetailService;

/**
 * 催收记录表Service
 * @author wgc
 * @version 1.0
 * @since 2014-11-17
 */
@Service(value = "dundetailService")
public class PubDundetailServiceImpl extends BaseServiceImpl implements PubDundetailService {
    private static final Logger logger = LoggerFactory.getLogger(PubDundetailServiceImpl.class);
    
    @Autowired
    private PubDundetailDao dundetailDao;
    
    @Transactional(rollbackFor=Exception.class)
    public Boolean addDundetail(PubDundetail dundetail)  throws ServiceException{
    	boolean isAdd = false;
    	try {
			isAdd = dundetailDao.insert(dundetail) > 0 ? true : false;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
       return isAdd;
    }
    
    public PubDundetail getDundetailById(Long id) throws ServiceException{
    	PubDundetail retClass = null;
    	try {
			retClass = dundetailDao.getByPrimary(id);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);	
		}
        return retClass;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public int dundetailUpdate(PubDundetail dundetail) throws ServiceException{
    	int updateCount = 0;
    	try {
    		updateCount = dundetailDao.update(dundetail);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
        return updateCount;

    }
    
    @Transactional(rollbackFor=Exception.class)
    public int deleteDundetail(Long id) throws ServiceException{
    	int deleteCount = 0;
    	try {
    		deleteCount = dundetailDao.deleteByPrimary(id);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
       return deleteCount;
    }
   
   @Transactional(rollbackFor=Exception.class)
   public Boolean updateDundetailById(Map<String , Object> map) throws ServiceException{
   		boolean isUpdate = false;
   		try{
   			isUpdate = dundetailDao.updateDundetailById(map);
   		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(400, e.getMessage());
		}
        return  isUpdate;
   }

	@Override
	public int getDundetailCount(Map<String, Object> data) throws ServiceException{
		int retNum = 0;
		try{
			retNum = dundetailDao.getDundetailCount(data);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(400, e.getMessage());
		}
		return retNum;
	}
    
	@Override
	public BaseDao getMapper() {
		return dundetailDao;
	}

	@Override
	public List<PubDundetail> getDundetailList(Map<String, Object> data)
			throws ServiceException {
		List<PubDundetail> list = null;
   		try {
			list = dundetailDao.getPageListByMap(data);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
        return list;
	}
			
	@Override
	public List<Map<String, Object>> getDundetailAndRepaymentList(
			Map<String, Object> data) throws Exception {
		List<Map<String,Object>> list = null;
		try {
			list = dundetailDao.getDundetailAndRepaymentList(data);
			for(Map<String,Object> detail : list){
		    	Map<String,Object> map = new HashMap<String,Object> ();
		    	map.put("repaymentId", detail.get("id"));
		    	//遍历list,获取还款计划表id从而得到上次催收时间
		    	List<PubDundetail> details = dundetailDao.getPageListByMap(map);
		    	if(null != details && 0 != details.size()) {
		    		detail.put("lastDuntime", details.get(0).getDunTime());
		    	}
		    }
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return list;
	}
	
}