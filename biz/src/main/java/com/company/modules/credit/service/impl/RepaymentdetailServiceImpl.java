package com.company.modules.credit.service.impl;

import java.util.Date;
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
import com.company.common.dao.PlBorrowDao;
import com.company.common.dao.PubRepayloaninfoDao;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.credit.dao.CreditRepaymentdetailDao;
import com.company.modules.credit.domain.PubRepaymentdetail;
import com.company.modules.credit.service.PubDundetailService;
import com.company.modules.credit.service.RepaymentdetailService;

/**
 * 还款明细表Service
 * @author wgc
 * @version 1.0
 * @since 2014-11-17
 */
@Service(value = "repaymentdetailService")
public class RepaymentdetailServiceImpl extends BaseServiceImpl implements RepaymentdetailService {
    private static final Logger logger = LoggerFactory.getLogger(RepaymentdetailServiceImpl.class);
    
    @Autowired
    private CreditRepaymentdetailDao repaymentdetailDao;
    @Autowired
    private PlBorrowDao plBorrowDao;
    @Autowired
    private PubRepayloaninfoDao pubRepayloaninfoDao;
    @Autowired
    private PubDundetailService dundetailservice;
    
    @Transactional(rollbackFor=Exception.class)
    public Boolean addRepaymentdetail(PubRepaymentdetail repaymentdetail)  throws ServiceException{
    	boolean isAdd = false;
    	try {
			isAdd = repaymentdetailDao.insert(repaymentdetail) > 0 ? true : false;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
       return isAdd;
    }
    
    public PubRepaymentdetail getRepaymentdetailById(Long id) throws ServiceException{
    	PubRepaymentdetail retClass = null;
    	try {
			retClass = repaymentdetailDao.getByPrimary(id);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
        return retClass;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public int repaymentdetailUpdate(PubRepaymentdetail repaymentdetail) throws ServiceException{
    	int updateCount = 0;
    	try {
    		updateCount = repaymentdetailDao.update(repaymentdetail);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
        return updateCount;

    }
    
    @Transactional(rollbackFor=Exception.class)
    public int deleteRepaymentdetail(Long id) throws ServiceException{
    	int deleteCount = 0;
    	try {
    		deleteCount = repaymentdetailDao.deleteByPrimary(id);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
       return deleteCount;
    }
   
   @Transactional(rollbackFor=Exception.class)
   public Boolean updateRepaymentdetailById(Map<String , Object> map) throws ServiceException{
   		boolean isUpdate = false;
   		try{
   			isUpdate = repaymentdetailDao.updateRepaymentdetailById(map);
   		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
        return  isUpdate;
   }

	@Override
	public int getRepaymentdetailCount(Map<String, Object> data) throws ServiceException{
		int retNum = 0;
		try{
			retNum = repaymentdetailDao.getRepaymentdetailCount(data);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return retNum;
	}

	
	
	@Override
	public List<? extends PubRepaymentdetail> getCollectionList(
			Map<String, Object> data) throws ServiceException {
		List<? extends PubRepaymentdetail> list=null;
		try{
		    list = repaymentdetailDao.getCollectionList(data);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return list; 
	}

	@Override
	public int getRepaymentdetailCountForDun(Map<String, Object> data)
			throws ServiceException {
		int retNum = 0;
		try{
			retNum = repaymentdetailDao.getRepaymentdetailCountForDun(data);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return retNum;
	}
	
	@Override
	public int getCollectionCountList(Map<String, Object> data)
			throws ServiceException {
		int retNum = 0;
		try{
			retNum = repaymentdetailDao.getCollectionCountList(data);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return retNum;
	}
    

	@Override
	public int updateRepaymentdetail(PubRepaymentdetail pd) throws ServiceException {
		int updateCount = 0;
		try {
			updateCount = repaymentdetailDao.update(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updateCount;
	}

    @Override
    public List<Map<String, Object>> queryRepaymentdetailByRepaymentDay(Date day) throws ServiceException {
        Map<String,Object> searchParam=new HashMap<String,Object>();
        //未结清
        searchParam.put("isPayoff", 0);
        List<Map<String, Object>> list=null;
        try {
            list=repaymentdetailDao.queryRepaymentdetailByRepaymentDay(day, searchParam);
            searchParam.put("cancelOverdue", 1);
            List<Map<String,Object>> cancelOverdueList=repaymentdetailDao.queryList(searchParam);
            list.addAll(cancelOverdueList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
        }
        return list;
    }

	@Override
	public BaseDao getMapper() {
		return repaymentdetailDao;
	}

}