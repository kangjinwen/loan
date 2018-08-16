package com.company.common.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.dao.BaseDao;
import com.company.common.dao.CustomerDao;
import com.company.common.domain.Customer;
import com.company.common.service.CustomerService;
import com.company.modules.common.exception.ServiceException;

/**
 * 客户基本信息Service
 * @author wgc
 * @version 1.0
 * @since 2014-12-01
 */
@Service(value = "customerService")
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    
    @Autowired
    private CustomerDao customerDao;
    
    @Transactional(rollbackFor=Exception.class)
    public Boolean addCustomer(Customer customer)  throws ServiceException{
    	boolean isAdd = false;
    	isAdd = customerDao.insert(customer) > 0 ? true : false;
       return isAdd;
    }
    
    public Customer getCustomerById(Long id) throws ServiceException{
    	Customer retClass = customerDao.getByPrimary(id);
    	return retClass;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public int customerUpdate(Customer customer) throws ServiceException{
    	return customerDao.update(customer);

    }
    
   public List<Customer> getCustomerList(Map<String , Object> map) throws ServiceException{
	   return customerDao.getPageListByMap(map);
   }
   
   @Transactional(rollbackFor=Exception.class)
   public Boolean updateCustomerById(Map<String , Object> map) throws ServiceException{
   		return customerDao.updateCustomerById(map);
   }
   
   	@Override
	public List<? extends Customer> getCustomerPageList(Map<String, Object> mapdata) throws ServiceException {
   		return customerDao.getPageListByMap(mapdata);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getCustomerMapPageList(
			Map<String, Object> data) throws ServiceException {
			List<Map<String, Object>> list;
		    list = customerDao.getCustomerMapPageList(data);
		    //遍历客户列表
		    for(Map map:list){
		    	//获取到每条数据的证件号码
//		    	String certNumber = (String)map.get("certNumber");
//		    	//通过证件号码找到咨询表最新记录的咨询状态
//		    	List<PlCreditconsult> list2 = plCreditconsultDao.getConsultInfoByCertNumber(certNumber);
//		    	if(0 != list2.size()){
//		    		String status = list2.get(0).getStatus();
//			    	//根据status判断当前是否申请贷款
//			    	if("customerNotQulified".equals(status) || "continue".equals(status) || "gave-up".equals(status) || "rejected".equals(status)
//			    			|| "pay-off".equals(status)
//			    			|| "usertask-audit-front".equals(status)
//			    			|| "usertask-assignTask-front".equals(status)) {//如果满足其中任一状态则当前没有申请贷款
//			    		map.put("applying", 0);//当前没有申请贷款
//			    	} else {
//			    		map.put("applying", 1);//当前正在申请贷款
//			    	}
//		    	} else {//咨询表里面没有记录
//		    		map.put("applying", 0);//当前没有申请贷款
//		    	}
		    	
		    }
		return list; 
	}

//	@Override
//	public int getCustomerCount(Map<String, Object> data) throws ServiceException{
//		int retNum = 0;
//		try{
//			retNum = customerDao.getCustomerCount(data);
//		}catch (PersistentDataException e) {
//			e.printStackTrace();
//			throw new ServiceException(400, e.getMessage());
//		}
//		return retNum;
//	}

//    @Override
//    public Map<String, Object> getCustomer(Map<String, Object> params) throws ServiceException {
//        try {
//            return customerDao.simpleQueryRec("pub_customer",params);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ServiceException(400, e.getMessage());
//        }
//    }
//
//	@Override
//	public Map<String, Object> getCustomerByCertTypeAndCertNumber(Map<String, Object> data) throws ServiceException {
//		Map<String, Object> ret = new HashMap<String, Object>();
//		try {
//			ret = customerDao.getCustomerByCertTypeAndCertNumber(data);
//		} catch (Exception e) {
//			throw new ServiceException(400, e.getMessage());
//		}
//		return ret;
//	}
//
//	@Transactional(rollbackFor=Exception.class)
//	@Override
//	public Boolean addCustomerToBlack(Map<String, Object> param) throws ServiceException {
//		logger.info("执行---------------------CustomerServiceImpl-addCustomerToBlack()---------------------------------------");
//		boolean isAdd = true;
//		try {
//			//获取当前登录用户信息
//			UserDetails userinfo = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			String loginUserName = userinfo.getUsername();
//			Date curDate = new Date();
//			
//			isAdd = customerDao.updateCustomerById(param);//将客户更新成黑名单
//			
//			Long id = Long.parseLong(String.valueOf(param.get("id")));
//			Customer customer = this.getCustomerById(id);
//			
//			//黑名单业务逻辑处理如下：
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("certNumber", customer.getCertNumber());
//			paramMap.put("certType", customer.getCertType());
//			paramMap.put("limit", 1);
//			List<BlackCustomer> list = pubBlackCustomerDao.getItemListByMap(paramMap, "BlackCustomer");
//			
//			if(list.size()>0){//不变更
//				isAdd = true;
//			}else{//新增
//				BlackCustomer blackCustomer = new BlackCustomer();
//				blackCustomer.setCertNumber(customer.getCertNumber());
//				blackCustomer.setCertType(customer.getCertType());
//				blackCustomer.setCreateTime(curDate);
//				blackCustomer.setCreator(loginUserName);
//				blackCustomer.setCustomerName(customer.getName());
//				blackCustomer.setIsDelete((byte)0);
//				blackCustomer.setIsSystemEnter((byte)1);
//				blackCustomer.setRemark("来源：[个人客户]-加入黑名单功能");
//				isAdd = pubBlackCustomerDao.insert(blackCustomer)>0?true:false;
//			}		
//		} catch (PersistentDataException e) {
//			throw new ServiceException(400, e.getMessage());
//		}
//		return isAdd;
//	}
//	
//	/**
//	 * 查询今日新增客户数
//	 */
//	@Override
//	public long getCustomerNumber() throws ServiceException{
//		long count = 0;
//		try {
//			  Calendar cal = Calendar.getInstance();
//			  cal.set(Calendar.HOUR_OF_DAY, 0);
//			  cal.set(Calendar.SECOND, 0);
//			  cal.set(Calendar.MINUTE, 0);
//			  cal.set(Calendar.MILLISECOND, 001);
//			  Timestamp zeroTime =  new Timestamp(cal.getTimeInMillis());
//			  
//			  count = customerDao.getCustomerNumber(zeroTime);
//			  
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return count;
//		
//	}
//	
//	/**
//	 * 查询不同机构今日新增客户数
//	 * @return
//	 * @throws ServiceException
//	 */
//	public long getCustomerNumberByOfficeId(String officeId) throws ServiceException{
//		long count = 0;
//		try {
//			  Calendar cal = Calendar.getInstance();
//			  cal.set(Calendar.HOUR_OF_DAY, 0);
//			  cal.set(Calendar.SECOND, 0);
//			  cal.set(Calendar.MINUTE, 0);
//			  cal.set(Calendar.MILLISECOND, 001);
//			  Timestamp zeroTime =  new Timestamp(cal.getTimeInMillis());
//			  Map<String,Object> params = new HashMap<String,Object>();
//			  params.put("zeroTime", zeroTime);
//			  params.put("officeId", officeId);
//			  count = customerDao.getCustomerNumberByOfficeId(params);
//			  
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return count;
//		
//	}
	
	@Override
	public BaseDao getMapper() {
		// TODO Auto-generated method stub
		return null;
	}
	

}