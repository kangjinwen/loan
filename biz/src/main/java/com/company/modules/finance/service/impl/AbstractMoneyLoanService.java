package com.company.modules.finance.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.company.common.dao.PubProjectProcessDao;
import com.company.common.dao.PubRepayloaninfoDao;
import com.company.common.domain.PlBorrow;
import com.company.common.domain.PubProjectProcess;
import com.company.common.domain.PubRepayloaninfo;
import com.company.common.domain.PubRepaymentdetail;
import com.company.common.service.PlBorrowService;
import com.company.common.utils.DateUtil;
import com.company.common.utils.ParamChecker;
import com.company.common.utils.factory.InterestCaculatorFactory;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.dao.PlConsultDao;
import com.company.modules.common.dao.PlProductDao;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.domain.PlProduct;
import com.company.modules.common.domain.PubLoan;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.EachPlan;
import com.company.modules.common.utils.InterestCalculator;
import com.company.modules.common.utils.converter.impl.LoanModelConverter;
import com.company.modules.common.utils.databean.LoanDataBean;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.contract.dao.PlContractDao;
import com.company.modules.contract.domain.PlContract;
import com.company.modules.fel.service.FelService;
import com.company.modules.finance.dao.PubLoanDao;
import com.company.modules.finance.service.MoneyLoanService;

/**
 * Created by FHJ on 2015/4/9.
 */
public abstract class AbstractMoneyLoanService extends HistoryRecorderService implements MoneyLoanService{

    protected static final Logger logger = LoggerFactory.getLogger(AbstractMoneyLoanService.class);

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
    private PlBorrowService plBorrowService;

    @Autowired
    private PubLoanDao pubLoanDao;
    @Autowired
    private PlContractDao contractDao;
    @Autowired
    private PlProductDao plProductDao;
    @Autowired
    private PubRepayloaninfoDao pubRepayloaninfoDao;
    @Autowired
    private PlContractDao plContractDao;
    @Autowired
    private PubProjectProcessDao pubProjectProcessDao;
    @Autowired
    private PlConsultDao plConsultDao;
    @Autowired
    private FelService felService;

    public static final byte ZERO = 0;
    public static final BigDecimal BIGDECIMAL_ZERO = new BigDecimal(0);
    
    
    @Override
    protected void preCheckCurrentWorkflowState(
            ProjectWorkflowDataBean projectWorkflowDataBean) throws ServiceException {
        logger.info("检查当前的工作流的状态。");
    }

    protected void recordLoanInfo(LoanDataBean loanDataBean) throws ServiceException {
        LoanModelConverter<PubLoan> loanModelConverter = new LoanModelConverter<PubLoan>();
        try {
        	PubLoan loan = loanModelConverter.convert(loanDataBean);
        	if (loan!=null) {				
        		loan.setAccount(loan.getAccount()==null?new BigDecimal(0):loan.getAccount());
        		pubLoanDao.insert(loan);
			}
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 根据项目编号来获取借款信息
     *
     * @param loanDataBean
     * @return
     * @throws ServiceException
     */
    protected PlBorrow updateBorrowInfoAndRetrieveIt(LoanDataBean loanDataBean) throws ServiceException {
        PlBorrow borrow;
		try {
			borrow = plBorrowService.getItemInfoByProcessInstanceId(loanDataBean.getProcessInstanceId());
			if (borrow!=null) {				
				borrow.setLoanTime(loanDataBean.getLoanTime());
				borrow.setLenders(loanDataBean.getLoginUserId());
				borrow.setLendersOffice(loanDataBean.getOfficeId());
				borrow.setRepaymentStatus(SystemConstant.REPAYMENT_STATUS_REPAYING);
				plBorrowService.update(borrow);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
        return borrow;
    }


    /**
     * 根据“还款类型”生成对应的计算器
     *
     * @param repaymentType
     * @param borrowInfo
     * @return
     * @throws ServiceException
     */
    protected InterestCalculator getInterestCalculatorByRepaymentTypeAndBorrowInfo(
            Byte repaymentType, PlBorrow borrowInfo) throws ServiceException {
        ParamChecker.checkEmpty(repaymentType, "repaymentType");

        // 借款期限 or 期数
        Integer timeLimit = borrowInfo.getTimeLimit();

        double account = borrowInfo.getAccount().doubleValue();
        double yearApr = borrowInfo.getRepaymentRate().doubleValue();

        // 开始计息日,用合同中所填写的日期
        Date interestTime = getContractBorrowStartTime(borrowInfo.getProcessInstanceId());

        int periods = timeLimit.intValue();
        //服务费率从product表中获取
        BigDecimal serviceFeeRate = BIGDECIMAL_ZERO;
        try {
            PlProduct product=plProductDao.getItemInfoById(borrowInfo.getProductId());
            //serviceFeeRate = product.getBorrowRate().multiply(product.getServiceFee());
        } catch (Exception e) {
            e.printStackTrace();
        }

        InterestCaculatorFactory caculatorFactory = InterestCaculatorFactory.getInstance();
        InterestCalculator interestCalculator = caculatorFactory.getObject(repaymentType);

        //interestCalculator.init(account, yearApr, interestTime, periods, null);

        return interestCalculator;
    }
    
    protected Date getContractBorrowStartTime(String processInstanceId) throws ServiceException {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("processInstanceId", processInstanceId);
        PlContract contract = null;
        try {
            contract = contractDao.getItemByMap(param);
        } catch (Exception e) {
            throwServiceExceptionAndLog(logger, "查询到合同信息出错。");
        }

        if (contract == null) {
            throwServiceExceptionAndLog(logger, "无法查询到合同信息。");
        }

        return contract.getBorrowStart();

    }
    
    protected Date getLoanTime(String processInstanceId) throws ServiceException {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("processInstanceId", processInstanceId);
        PubLoan loan = null;
        try {
        	loan = pubLoanDao.getOne(param);
        } catch (Exception e) {
            throwServiceExceptionAndLog(logger, "查询到放款信息出错。");
        }

        if (loan == null) {
            throwServiceExceptionAndLog(logger, "无法查询到放款信息。");
        }

        return loan.getLoanTime();

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
    protected PubRepaymentdetail populateRepaymentDetail(EachPlan eachPlanData, LoanDataBean loanDataBean, PlBorrow borrowInfo)  throws ServiceException {
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
        repaymentdetail.setPenalty(BIGDECIMAL_ZERO);
        repaymentdetail.setDefaultInterest(BIGDECIMAL_ZERO);
        repaymentdetail.setPeriod(eachPlanData.getPeriod());
        repaymentdetail.setRepaymentStaus(REPAYING);
        repaymentdetail.setRepaymentTime(eachPlanData.getRepayTime());
        if(borrowInfo.getRepaymentType() != null){
        	repaymentdetail.setRepaymentType(borrowInfo.getRepaymentType());
        }else{
        	repaymentdetail.setRepaymentType(Byte.valueOf("1"));
        }
        repaymentdetail.setNeedrepayCapital(new BigDecimal(eachPlanData.getNeedRepay()));

        return repaymentdetail;
    }


    @Override
    public PubLoan getLoanInformation(String processInstanceId) throws ServiceException {
    	PubLoan loan = null;
        try {
        	loan = pubLoanDao.getItemInfoByProcessInstanceId(processInstanceId);
        } catch (Exception e) {
            throw new  ServiceException(e.getMessage());
        }
        return loan;
    }
    /**
     * 生成还款详情信息记录
     * @param borrow
     * @param plans
     * @throws ServiceException
     */
    public void createRepayLoanInfo(PlBorrow borrow,List<EachPlan> plans)throws ServiceException{
        String processInstanceId=borrow.getProcessInstanceId();
        Long projectId = borrow.getProjectId();
        //合同
        PlContract contract = plContractDao.getItemInfoByProcessInstanceId(processInstanceId);
        //客户银行账号
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("processInstanceId", processInstanceId);
        PlConsult consult;
        try {
            consult = plConsultDao.getPlConsultByProjectId(borrow.getProjectId());
            
            //金额信息
            MathContext mathContext = new MathContext(20, RoundingMode.CEILING);
    		BigDecimal capitalAmount = new BigDecimal(0);
    		BigDecimal interestAmount = new BigDecimal(0);
    		BigDecimal repaymentInterestAmount = new BigDecimal(0);
    		BigDecimal repaymentCapitalAmount = new BigDecimal(0);
            //计算应还本金和利息总额
            for(EachPlan ep : plans){
                capitalAmount=capitalAmount.add(new BigDecimal(ep.getCapital()),mathContext);
                interestAmount=interestAmount.add(new BigDecimal(ep.getInterest()),mathContext);
            }
            
            Date startRepayTime = plans.get(0).getRepayTime();//开始还款时间
            Date endRepayTime = plans.get(plans.size()-1).getRepayTime();//结束还款时间
            
            PlBorrow plBorrow = plBorrowService.getItemInfoByProcessInstanceId(processInstanceId);
            //还款信息
            PubRepayloaninfo repayloaninfo = pubRepayloaninfoDao.getItemInfoByBorrowId(plBorrow.getId());
            if (repayloaninfo == null) {
            	repayloaninfo = new PubRepayloaninfo();
			} else {
				throw new ServiceException("还款计划已存在");
			}
            
//            if (borrow.getRepaymentType() == CommonConstant.REPAYMENT_TYPE_NORMAL) {
//            	interestAmount=interestAmount.subtract(felService.calculateUseFeeInfo(borrow.getProcessInstanceId(), borrow.getAccount(), FelTypeEnum.MONTH_REPAY.value()));
//    		}
            
            repayloaninfo.setRepaymenttime(startRepayTime);
            repayloaninfo.setEndRepayTime(endRepayTime);
            repayloaninfo.setOfficeid(borrow.getLoanOffice());
            repayloaninfo.setBorrowid(borrow.getId());
            repayloaninfo.setCapitalAmount(capitalAmount);
            repayloaninfo.setInterestAmount(interestAmount);
            repayloaninfo.setRepaymentCapitalAmount(repaymentCapitalAmount);
            repayloaninfo.setRepaymentInterestAmount(repaymentInterestAmount);
//            repayloaninfo.setContractid(contract.getId());
            // 判断是否有展期
            PubProjectProcess pubProjectProcess = pubProjectProcessDao.getItemByProcessInstanceId(Long.valueOf(processInstanceId));
        	int extensionNumber = ZERO;
        	if(pubProjectProcess != null){
        		extensionNumber = Integer.parseInt(String.valueOf(pubProjectProcess.getExtensionNumber()));
        	}
//        	if(extensionNumber > ZERO){
//        		//item.setContractNo(contract.getExtensionNo());
//			}else{
//				repayloaninfo.setContractNo(contract.getContractNo());
//			}
            repayloaninfo.setCustomerName(consult.getName());
            repayloaninfo.setIslocked(ZERO);
            repayloaninfo.setProductid(borrow.getProductId());
            repayloaninfo.setProjectid(projectId);
            //正常还款
            repayloaninfo.setRepaymentProcess(ZERO);
            //还款中状态
            repayloaninfo.setRepaymentStatus(ZERO);
            repayloaninfo.setTimeLimit(borrow.getTimeLimit());
            repayloaninfo.setDisposalAmount(BIGDECIMAL_ZERO);
            repayloaninfo.setDisposalBalance(BIGDECIMAL_ZERO);
            pubRepayloaninfoDao.insert(repayloaninfo);
            
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e.getMessage(),e);
        }
    }
}
	