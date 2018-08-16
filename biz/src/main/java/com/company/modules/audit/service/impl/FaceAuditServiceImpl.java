package com.company.modules.audit.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.common.utils.DateUtil;
import com.company.modules.audit.service.AbstractAuditService;
import com.company.modules.audit.util.databean.FaceAuditDataBean;
import com.company.modules.common.dao.PlProductDao;
import com.company.modules.common.domain.PlProduct;
import com.company.modules.common.domain.PubAttachment;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.PubAttachmentService;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.instance.dao.HousBorrowingBasicsDao;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.instance.domain.HousEnquiryInstitution;
import com.company.modules.instance.service.HousEnquiryInstitutionService;

/**
 * 流程-面审
 * @author JDM
 * @date 2016年9月30日
 *
 */
@Service(value="faceAuditServiceImpl")
public class FaceAuditServiceImpl extends AbstractAuditService{

	private static final Logger logger = LoggerFactory.getLogger(FaceAuditServiceImpl.class);
	
	
	/** 户口本 */
	private static final String HOUSEHOLD = "HOUSEHOLD";
	/** 征信材料 */
	private static final String CREDIT = "CREDIT";
	/** 风控单 */
	private static final String RISK = "RISK";
	
	@Autowired
	private PlProductDao  productDao;
	@Autowired
	private PubAttachmentService attachmentService;
	@Autowired
   	private HousBorrowingBasicsDao housBorrowingBasicsDao;
	@Autowired
	private HousEnquiryInstitutionService housEnquiryInstitutionService;
	
	
	@Override
	public void audit(BasicServiceDataBean serviceDataBean, DelegateTask delegateTask) throws Exception {
		FaceAuditDataBean faceAuditDataBean =(FaceAuditDataBean) serviceDataBean;
		
		if (faceAuditDataBean.getPlBorrowRequirement()!=null) {			
			PlProduct product = productDao.getItemInfoById(faceAuditDataBean.getPlBorrowRequirement().getProductId());
			faceAuditDataBean.setProduct(product);
		}
//		if (!faceAuditDataBean.getNextStep().equals("endReturnFee")) {			
////			checkIfAttachmentUpLoaded(faceAuditDataBean,HOUSEHOLD,"户口本");
//			checkIfAttachmentUpLoaded(faceAuditDataBean,CREDIT,"征信资料");
//			checkIfAttachmentUpLoaded(faceAuditDataBean,RISK,"风控单");
//		}
		
		insertOrUpdateAuditInfo(faceAuditDataBean);
		//更新基本信息和信息筛查信息
		updateHousBorrowingBasics(faceAuditDataBean);
		updateHousEnquiryInstitution(faceAuditDataBean);
		//if(WorkFlowConstant.NEXT_STEP_PASS.equals(faceAuditDataBean.getNextStep())){
		//	createOrUpdateBorrowInfo(faceAuditDataBean);//插入或更新borrow表
//			createOrUpdatePlBorrowRequirement(faceAuditDataBean);//插入或更新借款需求表
		//}
		 //记录审批日志
        recordLoanProcessHistory(faceAuditDataBean);
	}
	
	public void updateHousBorrowingBasics(FaceAuditDataBean dataBean) throws ServiceException {
		HousBorrowingBasics housBorrowingBasics = dataBean.getHousBorrowingBasics();
		if(housBorrowingBasics != null){
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", housBorrowingBasics.getId());
			paramMap.put("modifier", dataBean.getLoginUserId());
			paramMap.put("modifyTime", DateUtil.now());
			paramMap.put("name", housBorrowingBasics.getName());
			paramMap.put("certNumber", housBorrowingBasics.getCertNumber());
			paramMap.put("phone", housBorrowingBasics.getPhone());
			paramMap.put("marryStatus", housBorrowingBasics.getMarryStatus());
			paramMap.put("spouseName", housBorrowingBasics.getSpouseName());
			paramMap.put("spouseCertNumber", housBorrowingBasics.getSpouseCertNumber());
			paramMap.put("spousePhone", housBorrowingBasics.getSpousePhone());
			paramMap.put("fatherName", housBorrowingBasics.getFatherName());
			paramMap.put("motherName", housBorrowingBasics.getMotherName());
			paramMap.put("fatherNumber", housBorrowingBasics.getFatherNumber());
			paramMap.put("motherNumber", housBorrowingBasics.getMotherNumber());
			paramMap.put("totalBorrowed", housBorrowingBasics.getTotalBorrowed());
			paramMap.put("totalBorrowedCertNumber", housBorrowingBasics.getTotalBorrowedCertNumber());
			paramMap.put("residentialAddress", housBorrowingBasics.getResidentialAddress());
			paramMap.put("residentialAddressId", housBorrowingBasics.getResidentialAddressId());
			paramMap.put("remark", housBorrowingBasics.getRemark());
			housBorrowingBasicsDao.updateByMap(paramMap);
		}
	}
	
	public void updateHousEnquiryInstitution(FaceAuditDataBean dataBean) throws ServiceException {
		if(dataBean.getHousEnquiryInstitution() != null){
			for (HousEnquiryInstitution housEnquiryInstitution : dataBean.getHousEnquiryInstitution()) {
				housEnquiryInstitution.setProcessInstanceId(dataBean.getProcessInstanceId());
				housEnquiryInstitution.setProjectId(dataBean.getProjectId());
				housEnquiryInstitution.setConsultId(dataBean.getConsultId());
				try {
					createOrUpdateHousEnquiryInstitution(dataBean, housEnquiryInstitution);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
/*		if (dataBean.getHousEnquiryInstitution()!=null) {
			for (HousEnquiryInstitution housEnquiryInstitution : dataBean.getHousEnquiryInstitution()) {
				if(housEnquiryInstitution.getId() > 0){
					HousEnquiryInstitution housEnquiryInstitutionInfo;
					try {
						housEnquiryInstitutionInfo = housEnquiryInstitutionService.getItemInfoById(housEnquiryInstitution.getId());
						housEnquiryInstitution.setId(housEnquiryInstitutionInfo.getId());
						housEnquiryInstitution.setCreator(housEnquiryInstitutionInfo.getCreator());
						housEnquiryInstitution.setConsultId(housEnquiryInstitutionInfo.getConsultId());
						housEnquiryInstitution.setModifier(dataBean.getLoginUserId());
						housEnquiryInstitution.setProjectId(housEnquiryInstitutionInfo.getProjectId());
						housEnquiryInstitution.setProcessInstanceId(housEnquiryInstitutionInfo.getProcessInstanceId());
						housEnquiryInstitution.setModifyTime(new Date());
						housEnquiryInstitutionService.update(housEnquiryInstitution);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}		
			}
		}*/
		
	}
	
	/**
	 * 保存或修改查询机构信息
	 * @param preliminaryEvaluationDataBean
	 * @throws Exception
	 */
	public void createOrUpdateHousEnquiryInstitution(FaceAuditDataBean preliminaryEvaluationDataBean, HousEnquiryInstitution housEnquiryInstitutionInfo) throws Exception {
		if(housEnquiryInstitutionInfo.getId() !=null){
			HousEnquiryInstitution housEnquiryInstitution = housEnquiryInstitutionService.getItemInfoById(housEnquiryInstitutionInfo.getId());
			housEnquiryInstitutionInfo.setId(housEnquiryInstitution.getId());
			housEnquiryInstitutionInfo.setModifier(preliminaryEvaluationDataBean.getLoginUserId());
			housEnquiryInstitutionInfo.setModifyTime(new Date());
			housEnquiryInstitutionService.update(housEnquiryInstitutionInfo);
		}else{
			housEnquiryInstitutionInfo.setCreator(preliminaryEvaluationDataBean.getLoginUserId());
			housEnquiryInstitutionInfo.setCreateTime(new Date());
			housEnquiryInstitutionService.insert(housEnquiryInstitutionInfo);
		}
	}
	
	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
	
	private void checkIfAttachmentUpLoaded(FaceAuditDataBean faceAuditDataBean,String attachmentType,String attachmentName) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("btype", attachmentType);
        paramMap.put("processInstanceId", faceAuditDataBean.getProcessInstanceId());
        long count = 0;
    	List<PubAttachment> plAttachment = attachmentService.select(paramMap);
        count = plAttachment.size();
        if (count <= 0) {
            throwServiceExceptionAndLog(logger,  attachmentName + "附件没有上传，请上传后再提交。", Constant.FAIL_CODE_VALUE);
        }
    }

}
