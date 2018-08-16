package com.company.modules.extension.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.dao.PlBorrowDao;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.dao.PubProjectProcessDao;
import com.company.common.dao.PubRepayloaninfoDao;
import com.company.common.dao.PubRepaymentdetailDao;
import com.company.common.domain.CommonEachPlan;
import com.company.common.domain.EachPlanFactory;
import com.company.common.domain.PlBorrow;
import com.company.common.domain.PubProcessBranching;
import com.company.common.domain.PubProjectProcess;
import com.company.common.domain.PubRepayloaninfo;
import com.company.common.domain.PubRepaymentdetail;
import com.company.common.domain.EachPlanFactory.PLANTYPE;
import com.company.common.utils.DateUtil;
import com.company.modules.common.dao.PlConsultDao;
import com.company.modules.common.dao.PlFeeinfoDao;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.domain.PlFeeinfo;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.EachPlan;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.contract.dao.PlContractDao;
import com.company.modules.contract.domain.PlBankcard;
import com.company.modules.contract.domain.PlContract;
import com.company.modules.extension.service.ExtendedDeductService;
import com.company.modules.extension.utils.databean.ExtendedDeductDataBean;
import com.company.modules.fel.context.FelConstant;
import com.company.modules.fel.context.FelTypeEnum;
import com.company.modules.fel.service.FelService;
import com.company.modules.instance.dao.HousBorrowingBasicsDao;
import com.company.modules.instance.domain.HousBorrowingBasics;
/**
 * @author Administrator
 *
 */
@Transactional
@Service
public class ExtendedDuductServiceImpl extends HistoryRecorderService implements ExtendedDeductService{
	private static final Logger logger = LoggerFactory.getLogger(ExtendedDuductServiceImpl.class);
    protected static final String LOANED = "loaned";

    protected static final Byte NOT_PAYOFF = 0;

    protected static final Byte NOT_YET_EXECUTED = 0;

    protected static final Byte REPAYING = 0;

    protected static final Integer ROLLBACK_DAYS = 5;

    protected static final Byte NORMAL = 0;

    protected static final String LOAN_MONEY_FAILED = "放款失败";

    protected static final Byte VISIT_TYPE_VISIT = new Byte("1");

    protected static final Byte VISIT_TYPE_INSPECTION = new Byte("2");
	
	@Autowired
	private PubProcessBranchingDao pubProcessBranchingDao;
	@Autowired
	private PubRepaymentdetailDao pubRepaymentdetailDao;
	@Autowired
    private PlContractDao plContractDao;
	@Autowired
	private PlConsultDao plConsultDao;
	@Autowired
	private FelService felService;
	@Autowired
    private PubProjectProcessDao pubProjectProcessDao;
	@Autowired
    private PubRepayloaninfoDao pubRepayloaninfoDao;   
    @Autowired
    private PlBorrowDao plBorrowDao; 
    @Autowired
    private FelService felServiceImpl;
    @Autowired
    private PlFeeinfoDao plFeeinfoDao ;  
    @Autowired
	private HousBorrowingBasicsDao borrowingBasicsDao;

    
	

	@Override
	protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
	


	@Override
	public void deduct(ExtendedDeductDataBean dataBean) throws Exception {
		PubProcessBranching pubProcessBranchingParam = dataBean.getPubProcessBranching();
			//更新划扣时间
			PubProcessBranching pubProcessBranching  = pubProcessBranchingDao.getItemInfoById(pubProcessBranchingParam.getId());
			if (pubProcessBranching!=null) {
				pubProcessBranching.setDeductTime(pubProcessBranchingParam.getDeductTime());
				pubProcessBranching.setModifyTime(new Date());
				pubProcessBranchingDao.update(pubProcessBranching);
			}	
			//插入plBorrow一条数据用于生成展期还款计划
			PlBorrow plBorrow = plBorrowDao.getItemInfoByProcessInstanceId(pubProcessBranching.getProcessInstanceId());
			PlFeeinfo plFeeinfo = plFeeinfoDao.getItemInfoByProcessInstanceId(pubProcessBranching.getProcessInstanceId());
			PlContract plContract = plContractDao.getItemInfoByProcessInstanceId(pubProcessBranching.getProcessInstanceId());
			HousBorrowingBasics borrowBasic = borrowingBasicsDao
					.getItemInfoByProcessInstanceId(pubProcessBranching.getProcessInstanceId());
			plBorrow.setAccount(pubProcessBranching.getExtensionAmount());
			plBorrow.setProcessInstanceId(pubProcessBranching.getBranchingProcessId());
			plBorrow.setTimeLimit(pubProcessBranching.getExtensionCount());
			plBorrow.setModifyTime(DateUtil.now());
			plBorrow.setModifier(dataBean.getLoginUserId());
			plBorrow.setExpectTime(pubProcessBranching.getDeductTime());
			plBorrow.setLoanTime(pubProcessBranching.getDeductTime());
			plBorrowDao.insert(plBorrow);
			plFeeinfo.setProcessInstanceId(pubProcessBranching.getBranchingProcessId());
			plFeeinfo.setCreator(dataBean.getLoginUserId());
			plFeeinfo.setCreateTime(new Date());
			plFeeinfo.setTimeLimit(pubProcessBranching.getExtensionCount());
			plFeeinfoDao.insert(plFeeinfo);
			plContract.setCreator(String.valueOf(dataBean.getUserName()));
			plContract.setProcessInstanceId(pubProcessBranching.getBranchingProcessId());
			plContract.setCreateTime(new Date());
			plContractDao.insert(plContract);
			PlBorrow borrowInfo = plBorrowDao.getItemInfoByProcessInstanceId(pubProcessBranching.getBranchingProcessId());
			borrowBasic.setProcessInstanceId(pubProcessBranching.getBranchingProcessId());
			borrowBasic.setCreateTime(new Date());
			borrowBasic.setCreator(dataBean.getLoginUserId());
			borrowBasic.setModifier(dataBean.getLoginUserId());
			borrowingBasicsDao.insert(borrowBasic);
			 // 根据借款信息，制定回访计划,巡检计划和还款计划
	    	makeVisitPlanAndInspectionPlanAndRepaymentDetailPlanByBorrowInfo(dataBean, borrowInfo,pubProcessBranching);
				
	        //记录审批日志
	        recordLoanProcessHistory(dataBean);		
	}
	 @Transactional
	    protected void makeVisitPlanAndInspectionPlanAndRepaymentDetailPlanByBorrowInfo(ExtendedDeductDataBean loanDataBean, PlBorrow borrowInfo,PubProcessBranching pubProcessBranching) throws ServiceException {
	        borrowInfo.setLoanTime(borrowInfo.getLoanTime());//放款日为当前操作时间
	        List<EachPlan> allPlans = createEachPlans(borrowInfo,pubProcessBranching);

	        for (byte period = 0; period < allPlans.size(); period++) {
	            // 制定每期还款计划
	            makeEachRepaymentPlan(allPlans.get(period), loanDataBean, borrowInfo,pubProcessBranching);
	        }
	        
	        //生成还款信息
	        createRepayLoanInfo(borrowInfo, allPlans,pubProcessBranching);
	        
	    }
	 
	 /**
	     * 制定每期还款计划
	     *
	     * @param period
	     * @param eachPlanData
	     * @param borrowInfo
	     * @param borrowInfo
	     * @param period
	     * @param loanDataBean
	     * @throws ServiceException
	     */
	    protected void makeEachRepaymentPlan(EachPlan eachPlanData, ExtendedDeductDataBean loanDataBean, PlBorrow borrowInfo,PubProcessBranching pubProcessBranching) throws ServiceException {
	    	PubRepaymentdetail item = populateRepaymentDetail(eachPlanData, loanDataBean, borrowInfo,pubProcessBranching);
	        Long inserted = null;
	        try {
	        	if (item.getRepaymentTime()!=null) {	
	        		item.setCustomerId(0L);
	        		inserted = pubRepaymentdetailDao.insert(item);
				}
	        } catch (Exception e) {
	            throw new ServiceException(e.getMessage());
	        }
	        //checkIfInsertSuccess(inserted, "Repaymentdetail");
	    }
		
		public List<EachPlan> createEachPlans(PlBorrow borrowInfo,PubProcessBranching pubProcessBranching) throws ServiceException {
	    	List<EachPlan> plans=new ArrayList<EachPlan>();
	    	try {
	    		//PlProduct plProduct = plProductDao.getItemInfoById(borrowInfo.getProductId());
	        	//Byte repaymentType = borrowInfo.getRepaymentType();
	        	int timeLimit = (int)borrowInfo.getTimeLimit()+1;
//	        	byte period;
	        	// 还款开始时间
	            Date reoayDate = borrowInfo.getLoanTime();
	            BigDecimal total = borrowInfo.getAccount();//.multiply(new BigDecimal(10000));//单位为万
//	            if (timeLimit == 1) {
//	            	period = 2;
//	            	//只有一期只还本金
//					CommonEachPlan ep=(CommonEachPlan) EachPlanFactory.createEachPlan(PLANTYPE.common);
//		            ep.setTotal(total.doubleValue());
//		            ep.setRepayTime(DateUtil.rollMonAndOneDay(reoayDate, 2));
//		            ep.setInterest(0);
//		            ep.setPeriod(period);
//		            ep.setCapital(total.doubleValue());
//		            ep.setNeedRepay(total.doubleValue());
//		            plans.add(ep);
//		            return plans;
//				}else {
//					timeLimit += 1;
//				}
	        	/*if (repaymentType == 1) {*/
	            	//先息后本
	            Calendar calendar = Calendar.getInstance();
	            if (reoayDate!=null) {				
	            	calendar.setTime(reoayDate);
				}
	            
	            int day = calendar.get(Calendar.DAY_OF_MONTH);
	    		for(int i = 1;i <= timeLimit;i++){
	    			if(timeLimit == i){
	    				//最后一期还本金
	    				CommonEachPlan ep=(CommonEachPlan) EachPlanFactory.createEachPlan(PLANTYPE.common);
	    	            ep.setTotal(total.doubleValue());
	    	            if (day == 1) {
	    	            	if (reoayDate!=null) {							
	    	            		ep.setRepayTime(DateUtil.rollMon(reoayDate, i-1));
							}
						}else {
							if (reoayDate!=null) {							
								ep.setRepayTime(DateUtil.rollMonAndOneDay(reoayDate, i-1));
							}		
						}
	    	            
	    	            ep.setInterest(0);
	    	            ep.setPeriod(i);
	    	            ep.setCapital(total.doubleValue());
	    	            ep.setNeedRepay(total.doubleValue());
	    	            plans.add(ep);
	    			}else{
	    				CommonEachPlan ep=(CommonEachPlan) EachPlanFactory.createEachPlan(PLANTYPE.common);
		                ep.setTotal(felServiceImpl.calculateUseFeeInfo(pubProcessBranching.getBranchingProcessId(), total, FelConstant.MONTH_REPAY).doubleValue());
		                if(i==1){
		                	ep.setRepayTime(reoayDate);
		                }else{
		                	if (day == 1) {
		                		ep.setRepayTime(DateUtil.rollMon(reoayDate, i-1));
							}else {
								ep.setRepayTime(DateUtil.rollMonAndOneDay(reoayDate, i-1));
							}
		                }
		                ep.setPeriod(i);
		                ep.setInterest(felServiceImpl.calculateUseFeeInfo(pubProcessBranching.getBranchingProcessId(), total, FelConstant.MONTH_REPAY).doubleValue());
		                ep.setCapital(0);
		                if(i != 0){//先息后本去掉第一期还款利息的计划
		                	plans.add(ep);
		                }
	    			}
	            }
	            /*} else {
	            	//等额本息
	            	for (int i = 0; i < timeLimit; i++) {
	            		CommonEachPlan ep = (CommonEachPlan) EachPlanFactory.createEachPlan(PLANTYPE.common);
	                    ep.setTotal(felServiceImpl.calculateUseFeeInfo(borrowInfo.getProcessInstanceId(), borrowInfo.getAccount(), FelConstant.MONTH_REPAY).doubleValue());
		                ep.setRepayTime(DateUtil.rollMon(DateUtil.rollDay(reoayDate, -1), i+1));
		                ep.setInterest(felServiceImpl.calculateUseFeeInfo(borrowInfo.getProcessInstanceId(), borrowInfo.getAccount(), FelConstant.MONTHLY_INTEREST).doubleValue());
		                ep.setCapital(borrowInfo.getAccount().divide(new BigDecimal(timeLimit), mc).setScale(2, RoundingMode.CEILING).doubleValue());
		                ep.setParkingFee(plProduct.getParkingFee());
		                ep.setOtherFee(new BigDecimal("0"));
	    	            ep.setServiceFee(felServiceImpl.calculateUseFeeInfo(borrowInfo.getProcessInstanceId(), borrowInfo.getAccount(), FelConstant.MIDTERM_FEE).subtract(plProduct.getParkingFee()));
		                plans.add(ep);
	                }
	            }*/
			} catch (Exception e) {
	              throw  new ServiceException(e.getMessage(),e);
			}
	         return plans;
	    }
		
	    /**
	     * @description 生成还款信息记录
	     * @throws ServiceException
	     * @author wtc
	     * @return void
	     * @since  1.0.0
	    */
	    public void createRepayLoanInfo(PlBorrow borrow,List<EachPlan> plans,PubProcessBranching pubProcessBranching)throws ServiceException{
	        String processInstanceId=pubProcessBranching.getBranchingProcessId();
	        Long projectId = borrow.getProjectId();
	        //合同
	        PlContract contract=plContractDao.getItemInfoByProcessInstanceId(processInstanceId);
	        //客户银行账号
	        Map<String,Object> paramMap=new HashMap<String,Object>();
	        paramMap.put("processInstanceId", processInstanceId);
	        PlBankcard card;
	        PlConsult cc;
	        try {
	            cc = plConsultDao.getPlConsultByProjectId(borrow.getProjectId());
//	            card = plBankcardDao.getItemByMap(paramMap);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new ServiceException(e);
	        }
	        //金额信息
	        MathContext mc=new MathContext(20, RoundingMode.CEILING);
	        BigDecimal capitalAmount=new BigDecimal(0);
	        BigDecimal interestAmount=new BigDecimal(0);
	        BigDecimal repaymentInterestAmount=new BigDecimal(0);
	        BigDecimal repaymentCapitalAmount=new BigDecimal(0);
	        //计算应还本金和利息总额
	        for(EachPlan ep:plans){
	            capitalAmount=capitalAmount.add(new BigDecimal(ep.getCapital()),mc);
	            interestAmount=interestAmount.add(new BigDecimal(ep.getInterest()),mc);
	        }
	        
	        Date startRepayTime = plans.get(0).getRepayTime();//开始还款时间
	        Date endRepayTime = plans.get(plans.size()-1).getRepayTime();//结束还款时间
	        
	        //还款信息
	        PubRepayloaninfo item=new PubRepayloaninfo();
	        
	        if (borrow.getRepaymentType() == 1) {
//	        	repaymentInterestAmount = felService.calculateUseFeeInfo(borrow.getProcessInstanceId(), borrow.getAccount(), FelTypeEnum.MONTH_REPAY.value());
	        	interestAmount=interestAmount.subtract(felService.calculateUseFeeInfo(pubProcessBranching.getBranchingProcessId(), borrow.getAccount(), FelTypeEnum.MONTH_REPAY.value()));
			}
	        
	        item.setRepaymenttime(startRepayTime);
	        item.setEndRepayTime(endRepayTime);
	        item.setOfficeid(borrow.getLoanOffice());
	        item.setBorrowid(borrow.getId());
	        item.setCapitalAmount(capitalAmount);
	        item.setInterestAmount(interestAmount);
	        item.setRepaymentCapitalAmount(repaymentCapitalAmount);
	        item.setRepaymentInterestAmount(repaymentInterestAmount);
	        item.setContractid(contract.getId());
	        try {
	        	PubProjectProcess pubProjectProcess = pubProjectProcessDao.getItemByProcessInstanceId(Long.valueOf(processInstanceId));
	        	int extensionNumber = 0;
	        	if(pubProjectProcess != null){
	        		extensionNumber = Integer.parseInt(String.valueOf(pubProjectProcess.getExtensionNumber()));
	        	}
	        	if(extensionNumber > 0){
	        		//item.setContractNo(contract.getExtensionNo());
				}else{
					item.setContractNo(contract.getContractNo());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	        //item.setCustomerAccount(card.getBankCardId());
	        item.setCustomerName(cc.getName());
	        item.setIslocked((byte)0);
	        item.setProductid(borrow.getProductId());
	        item.setProjectid(projectId);
	        //正常还款
	        item.setRepaymentProcess((byte)0);
	        //还款中状态
	        item.setRepaymentStatus((byte)0);
//	        item.setRepaymentTime(borrow.);
	        item.setTimeLimit(borrow.getTimeLimit());
	        item.setDisposalAmount(new BigDecimal(0));;
	        item.setDisposalBalance(new BigDecimal(0));
	        try {
	            pubRepayloaninfoDao.insert(item);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new ServiceException(e);
	        }
	      
	    }
	    
	    /**
	     * 组装Repaymentdetail
	     *
	     * @param period
	     * @param eachPlanData
	     * @param loanDataBean
	     * @param borrowInfo
	     * @return
	     */
	    protected PubRepaymentdetail populateRepaymentDetail(EachPlan eachPlanData, ExtendedDeductDataBean loanDataBean, PlBorrow borrowInfo,PubProcessBranching pubProcessBranching)  throws ServiceException {
	    	PubRepaymentdetail repaymentdetail = new PubRepaymentdetail();

	    	repaymentdetail.setBorrowId(borrowInfo.getId());
	    	
	        repaymentdetail.setAccount(new BigDecimal(eachPlanData.getTotal()));
	        repaymentdetail.setCreateTime(DateUtil.now());
	        repaymentdetail.setCreator(loanDataBean.getLoginUserId());

	        repaymentdetail.setProcessinstanceid(borrowInfo.getProcessInstanceId());
	        repaymentdetail.setCapital(new BigDecimal(eachPlanData.getCapital()));
	        repaymentdetail.setInterest(new BigDecimal(eachPlanData.getInterest()));
	        repaymentdetail.setIsDelete(NORMAL);
	        repaymentdetail.setIsPayoff(NOT_PAYOFF);
	        repaymentdetail.setModifier(loanDataBean.getLoginUserId());
	        repaymentdetail.setModifyTime(DateUtil.now());
	        // 方便贷后计算
	        repaymentdetail.setPenalty(new BigDecimal(0));
	        repaymentdetail.setDefaultInterest(new BigDecimal(0));
	        repaymentdetail.setPeriod(eachPlanData.getPeriod());
	        repaymentdetail.setRepaymentStaus(REPAYING);
	        repaymentdetail.setRepaymentTime(eachPlanData.getRepayTime());
	        repaymentdetail.setRepaymentType(borrowInfo.getRepaymentType());
	        repaymentdetail.setNeedrepayCapital(new BigDecimal(eachPlanData.getNeedRepay()));

	        return repaymentdetail;
	    }
	
}
