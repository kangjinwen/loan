package com.company.modules.anrong.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
//import com.company.contract.util.ContractUtil;
import com.company.common.context.Constant;
import com.company.common.utils.code.MD5;
import com.company.modules.anrong.api.MyApi;
import com.company.modules.anrong.constant.AnRongConstant;
import com.company.modules.anrong.dao.PubAnrongAttachmentDao;
import com.company.modules.anrong.dao.PubArRiskResultDao;
import com.company.modules.anrong.domain.PubAnrongAttachment;
import com.company.modules.anrong.domain.PubArRiskResult;
import com.company.modules.anrong.entity.enterprise.CompanyAnliInfoList;
import com.company.modules.anrong.entity.enterprise.CompanyShixinInfoList;
import com.company.modules.anrong.entity.enterprise.CompanyZhixinInfoList;
import com.company.modules.anrong.entity.enterprise.EnterAndJudiReport;
import com.company.modules.anrong.entity.enterprise.ResultOverview;
import com.company.modules.anrong.entity.msp.Error;
import com.company.modules.anrong.entity.msp.MSP;
import com.company.modules.anrong.entity.person.EnterOfficeInfor;
import com.company.modules.anrong.entity.person.EnterShareholderInfor;
import com.company.modules.anrong.entity.person.EnterpriseLegalPersonInfor;
import com.company.modules.anrong.entity.person.GeRenGongshang;
import com.company.modules.anrong.entity.person.PersonAndEnterReport;
import com.company.modules.anrong.entity.person.PersonInfo;
import com.company.modules.anrong.service.AnRongService;
import com.company.modules.anrong.utils.ApiAccessUtil;
import com.company.modules.anrong.utils.ObjectMapperUtil;
import com.company.modules.common.exception.ServiceException;

@Service(value = "anRongServiceImpl")
public class AnRongServiceImpl implements AnRongService{
	/**
	 * 日志操作
	 */
    private static final Logger logger = LoggerFactory.getLogger(AnRongServiceImpl.class);
	@Autowired 
	private PubArRiskResultDao creditHistoryDao;
	@Autowired
	private PubAnrongAttachmentDao  pubAnrongAttachmentDao;
	
	
//	private  static final String member="1360";
//	private  static final String sign="w(iw^I8u)qu)h";
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	/**
	 * 生成征信报告
	 */
	public String createCreditReport(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		int type = (Integer) map.get("riskType");// 请求类型
		long consultId = (Integer) map.get("consultId");
		long projectId = (Integer) map.get("projectId");

		long userid = (Long) map.get("userid");
		String rootDir = (String) map.get("rootDir"); // 文件根目录
		String dirName = new SimpleDateFormat("yyyy-MM").format(new Date());
		String baseDestFile = "/anrong/" + dirName + "/";

		String riskParam = "";
		String errors = "";// 定义接口返回的错误
		Long fileId = null;
		Map<String, Object> result = null;
		try {
			if (type == 1) {

				map.put("applyDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				map.put("applyId", getGeneratorId()); // 申请编号

				riskParam = mspParam(map);
				// 插入请求消息
				PubArRiskResult prr = new PubArRiskResult();
				prr.setConsultId(consultId);
				prr.setProjectId(projectId);
				prr.setRiskParam(riskParam);
				prr.setRiskType(1L);
				prr.setCreateTime(new Date());
				prr.setCreator(userid);
				saveCreditHistory(prr);

				String json = Msp(map);// 调用接口

				JSONObject mspjson = JSONObject.parseObject(json);

				MSP cr = ObjectMapperUtil.getObjectSafe(mspjson.getString("msp").toString(), MSP.class);
				if (cr.getErrors() == null) {
					String excelPath = cr.getExcelPath(); // 获取到excel路径

					File fileDir = new File(rootDir + baseDestFile);
					if (!fileDir.exists()) {
						fileDir.mkdirs();
					}
					String savePath = baseDestFile + System.currentTimeMillis() + ".pdf";

					creatMspPdf(excelPath, rootDir + savePath); // 生成pdf

					File file = new File(rootDir + savePath);
					fileId = saveFileInfo(file, savePath, "1", userid); // 保存文件信息

				} else {
					List<Error> errList = cr.getErrors();
					for (Error err : errList) {
						errors = errors + "-" + err.getMsg();
					}
				}

				// 插入响应消息
				if (fileId != null)
					prr.setFileId(fileId);
				prr.setRiskResult(json);
				updateCreditHistory(prr);

			} else if (type == 2) { // 个人关联企业

				String templateFile = rootDir + "/anrong/template/person_enterprise_template.docx";
				String cid = (String) map.get("cid");

				riskParam = cid.trim();

				// 插入请求消息
				PubArRiskResult prr = new PubArRiskResult();
				prr.setConsultId(consultId);
				prr.setProjectId(projectId);
				prr.setRiskParam(riskParam);
				prr.setRiskType(2L);
				prr.setCreateTime(new Date());
				prr.setCreator(userid);
				saveCreditHistory(prr);

				logger.info("开始调用接口,接口类型：个人企业关联查询.请求参数：" + riskParam);
				result = perAndEnter(cid);

				errors = (String) result.get("errors");
				if (errors.equals("")) {
					File fileDir = new File(rootDir + baseDestFile);
					if (!fileDir.exists()) {
						fileDir.mkdirs();
					}
					String savePath = baseDestFile + cid + ".pdf";

					String destFile = rootDir + savePath;
					//ContractUtil.generatePdf(templateFile, destFile, result);// 生成pdf

					File file = new File(new String(destFile.getBytes(), "utf-8"));
					fileId = saveFileInfo(file, savePath, "2", userid); // 保存文件信息
				}

				// 插入响应消息
				String returnJson = (String) result.get("returnJson"); // 接口返回的json
				if (fileId != null)
					prr.setFileId(fileId);
				prr.setRiskResult(returnJson);
				updateCreditHistory(prr);

			} else if (type == 3) { // 司法企业

				String templateFile = rootDir + "/anrong/template/enterprise_justice_template.docx";
				List<String> enterpriseNames = (List<String>) map.get("enterpriseNames");

				HashSet<String> hs = new HashSet<String>(enterpriseNames); // 去除重复
				for (int i = 0; i < hs.size(); i++) {

					String enterprise = enterpriseNames.get(i);

					riskParam = enterprise;
					// 插入请求消息
					PubArRiskResult prr = new PubArRiskResult();
					prr.setConsultId(consultId);
					prr.setProjectId(projectId);
					prr.setRiskParam(riskParam);
					prr.setRiskType(3L);
					prr.setCreateTime(new Date());
					prr.setCreator(userid);
					saveCreditHistory(prr); // 保存请求消息

					logger.info("开始调用接口,接口类型：司法企业关联查询.请求参数:" + riskParam);
					if (isValidCompany(enterprise)) { // 含有中文则是企业名称
						result = enterJudi(enterprise, null);

					} else {
						result = enterJudi(null, enterprise);

					}
					String error = (String) result.get("errors");

					if (error == null) {
						File fileDir = new File(rootDir + baseDestFile);
						if (!fileDir.exists()) {
							fileDir.mkdirs();
						}
						String savePath = baseDestFile + new String(enterprise.getBytes(), "UTF-8") + ".pdf";

						String destFile = rootDir + savePath;
						//ContractUtil.generatePdf(templateFile, destFile, result);// 生成pdf

						File file = new File(new String(destFile.getBytes(), "utf-8"));
						fileId = saveFileInfo(file, savePath, "3", userid); // 保存文件信息
					} else {
						errors = errors + "-" + "第" + (i + 1) + "项" + error;
					}

					// 插入响应消息
					String returnJson = (String) result.get("returnJson"); // 接口返回的json
					prr.setRiskResult(returnJson);
					if (fileId != null)
						prr.setFileId(fileId);
					updateCreditHistory(prr);

					fileId = null;// 初始化
				}
			}

		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return errors;

	}
	
	/**
	 * 查询msp会员信息
	 * @return
	 * @throws Exception
	 */
	public String Msp(Map<String,Object> map) throws Exception{
		
		//测试换成member\sign  正式的用AnRongConstant.
		MyApi mApi=new MyApi(AnRongConstant.MEMBER,AnRongConstant.SIGN,AnRongConstant.MSP_URL);
		return mApi.createApply(map);
	}
	
	/**
	 * msp会员参数
	 * @return
	 * @throws Exception
	 */
	public String mspParam(Map<String,Object> map) throws Exception{
		
		//测试换成member\sign 正式的用AnRongConstant.
		MyApi mApi=new MyApi(AnRongConstant.MEMBER,AnRongConstant.SIGN,AnRongConstant.MSP_URL);
		LinkedHashMap<String, String> params=mApi.mspParam(map);
		params.remove("member");
		params.remove("sign");
		
		return JSONObject.toJSONString(params);
	}
	
	
	/**
	 * 查询个人关联企业信息
	 * @param cida
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> perAndEnter(String cid) throws Exception{
		
		String passWord=AnRongConstant.SIGN;
		String realSign=MD5.encode(cid+AnRongConstant.MEMBER+passWord);
		
		MyApi mApi=new MyApi(AnRongConstant.MEMBER,realSign,AnRongConstant.PERSON_URL);
		String returnJson=mApi.queryPerAndEn(cid);
		
		Map<String,Object> map = new HashMap<>();
		map.put("reportDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("reportNo",getGeneratorId());
		map.put("organ", "赚赚金融");
		map.put("executor", "信息筛查专员");
		
		PersonAndEnterReport par=ObjectMapperUtil.getObjectSafe(returnJson,PersonAndEnterReport.class);
		String message=par.getMessage();
		String errors="";
		
	if(message.equals("0000")){
		
		GeRenGongshang grg=par.getGeRenGongshang();
		
		PersonInfo personInfo=grg.getPersonInfo(); //身份信息
		map.put("personInfo", personInfo);
		
		//企业法人
		List<EnterpriseLegalPersonInfor> enterPersonList=new ArrayList<EnterpriseLegalPersonInfor>();
		if(grg.getEnterpriseLegalPersonInfor()!=null&&grg.getEnterpriseLegalPersonInfor().size()>0){
			enterPersonList=grg.getEnterpriseLegalPersonInfor();  
		}else{
			enterPersonList.add(new EnterpriseLegalPersonInfor());
		}
		map.put("enterpriseLegalPersonInforList", enterPersonList);
		
		//股东信息
		List<EnterShareholderInfor> enterShareholderInforList=new ArrayList<EnterShareholderInfor>();
		if(grg.getEnterShareholderInfor()!=null&&grg.getEnterShareholderInfor().size()>0){
			enterShareholderInforList=grg.getEnterShareholderInfor();  
		}else{
			enterShareholderInforList.add(new EnterShareholderInfor());
		}
		map.put("enterShareholderInforList", enterShareholderInforList);
		
		//任职信息
		List<EnterOfficeInfor> enterOfficeInforList=new ArrayList<EnterOfficeInfor>();
		if(grg.getEnterOfficeInfor()!=null&&grg.getEnterOfficeInfor().size()>0){
			enterOfficeInforList=grg.getEnterOfficeInfor();
		}else{
			enterOfficeInforList.add(new EnterOfficeInfor());
		}
		map.put("enterOfficeInforList", enterOfficeInforList);
		}
		else {
			//错误信息
			errors=ValidPerAndEnt(message);
		}
		map.put("errors", errors);
		map.put("returnJson",returnJson);
		return map;
	}
	
	/**
	 * 查询企业司法信息
	 * @param enterpriseNamea
	 * @param enterpriseNoa
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> enterJudi(String enterpriseName,String enterpriseNo) throws Exception{
		
		MyApi mApi=new MyApi(AnRongConstant.MEMBER,AnRongConstant.SIGN,AnRongConstant.ENTERPRISE_URL);
		
		String json=mApi.queryEnterprise(enterpriseName,enterpriseNo);//查询
		
		EnterAndJudiReport ear=ObjectMapperUtil.getObjectSafe(json,EnterAndJudiReport.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("enterpriseName",enterpriseName==null?"":enterpriseName);
		map.put("enterpriseNo",enterpriseNo==null?"":enterpriseNo);
		map.put("reportNo",getGeneratorId());
		map.put("organ","赚赚金融");
		map.put("executor", "信息筛查专员");
		map.put("reportDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		String errors=ear.getError();
		if(errors==null){
			ResultOverview rOverview=new ResultOverview();
			
			List<CompanyZhixinInfoList> companyZhixinInfoList=new ArrayList<CompanyZhixinInfoList>();
			if(ear.getCompanyZhixinInfoList()!=null&&ear.getCompanyZhixinInfoList().size()>0){
				companyZhixinInfoList=ear.getCompanyZhixinInfoList();
				rOverview.setZhiXinCount(String.valueOf(companyZhixinInfoList.size()));
			}else{
				companyZhixinInfoList.add(new CompanyZhixinInfoList());
				rOverview.setZhiXinCount("0");
			}
		map.put("companyZhixinInfoList", companyZhixinInfoList);
		
		List<CompanyShixinInfoList> companyShixinInfoList=new ArrayList<CompanyShixinInfoList>();
				if(ear.getCompanyShixinInfoList()!=null&&ear.getCompanyShixinInfoList().size()>0)
				{
					companyShixinInfoList=ear.getCompanyShixinInfoList();
					rOverview.setShiXinCount(String.valueOf(companyShixinInfoList.size()));
				}
				else{
					companyShixinInfoList.add(new CompanyShixinInfoList());
					rOverview.setShiXinCount("0");
				}
		map.put("companyShixinInfoList",companyShixinInfoList);
		
		List<CompanyAnliInfoList>  companyAnliInfoList=new ArrayList<CompanyAnliInfoList>();
		if(ear.getCompanyAnliInfoList()!=null&&ear.getCompanyAnliInfoList().size()>0){
			companyAnliInfoList=ear.getCompanyAnliInfoList();
			rOverview.setAnLiCount(String.valueOf(companyAnliInfoList.size()));
			
		}else{
			companyAnliInfoList.add(new CompanyAnliInfoList());
			rOverview.setAnLiCount("0");
		}
		map.put("companyAnliInfoList", companyAnliInfoList);
		
		map.put("resultOverView", rOverview);
		}else{
			errors=ValidEntJud(errors);
		}
		map.put("errors", errors);
		map.put("returnJson", json);
		return map;
		
	}
	
	/**
	 * 创建pdf
	 * @param result
	 */
	public void createPDF(Map<String,Object> result){
		
		try {
//			String destFile = baseDestFile + System.currentTimeMillis()+".pdf";
//			ContractUtil.generatePdf(templateFile, destFile, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 个人关联企业错误码
	 * @param message
	 * @return
	 */
	 private String ValidPerAndEnt(String errors){
		 switch (errors) {
		 case "0000":  //查询成功
				errors="提交成功";
				break;
			case "0001":
				errors="必填参数不能为空";
				break;
			case "0001_wrong":
				errors="必填参数格式错误";
				break;
			case "0002":
				errors="授权失败";
				break;
			case "0003_grgs":
				errors="个人工商查询接口欠费";
				break;
			case "0004":
				errors="系统错误";
				break;
			case "0006":
				errors="未查到相关数据，此笔查询不扣费";
				break;
			case "0008":
				errors="身份证号格式错误";
				break;
			default:
				break;
			}
		 return errors;
		 
	 }
	 
	 /**
	  * 司法企业错误码
	  * @param errors
	  * @return
	  */
	 private String ValidEntJud(String errors){
		 switch (errors) {
			case "1":
				errors="企业名称和组织机构代码不能同时为空";
				break;
			case "4":
				errors="机构号member不能为空";
				break;
			case "5":
				errors="机构验证码sign不能为空";
				break;
			case "6":
				errors="机构号member错误";
				break;
			case "7":
				errors="机构验证码sign错误";
				break;
			case "8":
				errors="该机构还未绑定IP，暂无法访问";
				break;
			case "9":
				errors="该IP没有访问权限";
				break;
			case "10":
				errors="该会员合同中没有司法信息P2P查询权限";
				break;
			case "11":
				errors="费用不足，请充费";
				break;
			case "21":
				errors="组织机构代码错误";
				break;
			default:
				break;
			}
		 return errors;
		 
	 }

	/**
	 * 保存征信历史
	 */
	public void saveCreditHistory(PubArRiskResult parr) throws Exception {
		// TODO Auto-generated method stub
		try {
			creditHistoryDao.insert(parr);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	
	/**
	 *更新征信历史
	 */
	public void updateCreditHistory(PubArRiskResult parr) throws Exception {
		// TODO Auto-generated method stub
		try {
			creditHistoryDao.update(parr);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	
	/**
	 * 保存附件信息
	 * @param map
	 * @throws Exception 
	 */
	public long saveFileInfo(File file,String savePath,String fileType,long userid) throws Exception{
		long pramerkey;
		PubAnrongAttachment paa=new PubAnrongAttachment();
		
		paa.setFilePath(new String(savePath.getBytes(),"utf-8"));
		paa.setFileName(new String(file.getName().getBytes(),"UTF-8"));
		paa.setFileSize(file.length());
		paa.setFileType(fileType);
		paa.setCreator(userid);
		paa.setCreateTime(new Date());
		try {
			 pramerkey=pubAnrongAttachmentDao.insert(paa);
			 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		
		return paa.getId();
	}
	
	
	
	/**
	 * 安融附件表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<PubAnrongAttachment> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return pubAnrongAttachmentDao.queryAttachmentList(data);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 根据id获取附件
	 */
	@Override
	public PubAnrongAttachment getFileById(long id) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			return pubAnrongAttachmentDao.getItemInfoById(id);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	
 public void creatMspPdf(String httpsUrl,String filePath) throws Exception{
	        try {  
	        	URL reqURL = new URL(httpsUrl); //创建URL对象
				HttpsURLConnection httpsConn = (HttpsURLConnection)reqURL.openConnection();
				
				if (httpsUrl.toLowerCase().startsWith("https")) {
					// 兼容https
					ApiAccessUtil.resolveSslConn(httpsConn);
				}
	        	 InputStream is = httpsConn.getInputStream();
	             
	             File file=new File(filePath);  
	             if(!file.exists())  
	                 file.createNewFile();  
	             
	             FileOutputStream out=new FileOutputStream(file);  
	             int c;  
	             byte buffer[]=new byte[is.available()];  
	             while((c=is.read(buffer))!=-1){  
	                 for(int i=0;i<c;i++)  
	                     out.write(buffer[i]);          
	             }  
	             is.close();  
	             out.close();  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	            throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
	        }    
		 
		 
	 }

	@Override
	public List<PubArRiskResult> getQueryCountItems(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		try {
			return creditHistoryDao.getItemCountsByMap(map);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	
	//生成申请编号
	public static String getGeneratorId(){
				Timestamp ts = new Timestamp(System.currentTimeMillis());
				DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				return sdf.format(ts);
			}
	
	
	//判断是否是数字
	public static boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("[0-9]*");  
	    return pattern.matcher(str).matches();     
	}
	

	@Override
	public List<PubArRiskResult> getCheckStatus(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		try {
			
			return creditHistoryDao.checkStatusByMap(map);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}  
	
	 /** 
     * 验证企业代码是否正确 
     *  
     * @param code 企业组织机构代码 
     * @return 
     */  
    private static final boolean isValidEntpCode(String code) {  
        int[] ws = { 3, 7, 9, 10, 5, 8, 4, 2 };  
        String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";  
        String regex = "^([0-9A-Z]){8}-[0-9|X]$";  
  
        if (!code.matches(regex)) {  
            return false;  
        }  
        int sum = 0;  
        for (int i = 0; i < 8; i++) {  
            sum += str.indexOf(String.valueOf(code.charAt(i))) * ws[i];  
        }  
        logger.info("sum is {}", sum);  
        logger.info("sum % 11 is {}", sum % 11);  
  
        int c9 = 11 - (sum % 11);  
  
        String sc9 = String.valueOf(c9);  
        if (11 == c9) {  
            sc9 = "0";  
        } else if (10 == c9) {  
            sc9 = "X";  
        }  
        logger.info("sc9 is {}", sc9);  
        return sc9.equals(String.valueOf(code.charAt(9)));  
    }  
	
    /**
     * 验证是否还有中文
     * @param str
     * @return
     * @throws Exception 
     */
    private static final boolean isValidCompany(String str) throws Exception{
		 
		 String regEx = "[\u4e00-\u9fa5]";
		 Pattern pat = Pattern.compile(regEx);
		 try {
			 Matcher matcher = pat.matcher(str);
			 boolean flg = false;
			 if (matcher.find())    {
			 flg = true;
			 }
			 return flg;
		} catch (Exception e) {
			// TODO: handle exception
			throw new ServiceException("企业或组织机构代码不为空",e);
		}
	}
		
		
}
