package com.company.modules.repayment.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.common.utils.DateUtil;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ParamChecker;
import com.company.common.utils.ServletUtils;
import com.company.common.utils.StringUtil;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.PoiUtil;
import com.company.modules.common.utils.RMB;
import com.company.modules.repayment.RepaymentVO;
import com.company.modules.repayment.service.PubRepaymentService;
import com.company.modules.system.domain.SysUser;
import com.company.modules.workflow.controller.WorkflowBaseController;

/**
 * 还款管理Action
 * @author JDM
 * @date 2016年8月25日
 *
 */
@SuppressWarnings({ "all" })
@RequestMapping("/modules/repayment/RepaymentAction")
@Controller
public class PubRepaymentAction extends WorkflowBaseController {
	
	static  Map<String,String> productTypeMapper=new HashMap<String,String>();
    static{
        productTypeMapper.put("0", "A");
        productTypeMapper.put("1", "B");
        productTypeMapper.put("2", "C");
        productTypeMapper.put("3", "D");
    }
	
	@Autowired
	private PubRepaymentService repaymentService;

	private void roundBigDecimal(Map<String, Object> map) {
        for (Entry<String, Object> entry : map.entrySet()) {
            Object v = entry.getValue();
            if (v instanceof BigDecimal) {
                BigDecimal value = (BigDecimal) v;
                entry.setValue(value.setScale(2, BigDecimal.ROUND_CEILING));
            }
        }
    }
	
    /**
     * 分页查询还款数据
     * @param response
     * @param request
     * @param repayment 还款请求参数
     * @throws Exception
     */
    @RequestMapping("/getRepaymentPage.htm")
    public void getRepaymentPage(HttpServletResponse response, HttpServletRequest request,
		RepaymentVO repayment) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<>();
		
        SysUser loginUser = getLoginUser(request);
        List<String> coverdOffices = getCoverdOffices(loginUser);
        if (CollectionUtils.isNotEmpty(coverdOffices)) {
        	paramMap.put("coverdOffices", coverdOffices);
		}
        paramMap.put("userName", getLoginUserName(request));
        paramMap.put("roleId", getRoleId(request));
        paramMap.put("repayment", repayment);
        if (repayment.getExecutorId()!=null) {
			SysUser executor = sysUserService.getUserById(repayment.getExecutorId());
			List<String> executorCoverdOffices = getCoverdOffices(executor);
			paramMap.put("executorCoverdOffices", executorCoverdOffices);
		}
        
        PageHelper.startPage(repayment.getPageNum(), repayment.getPageSize());
        List<Map> repaymentMap = repaymentService.getPageListByMap(paramMap);
        for(Map<String,Object> map: repaymentMap){
            roundBigDecimal(map);
            
            BigDecimal account=(BigDecimal)map.get("borrowAccount");
            String big = RMB.toBigAmt(account.doubleValue());
            map.put("borrowAccountChinese", big);
        }
		PageInfo<Map> page = new PageInfo<Map>(repaymentMap);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * 分页查询还款详情数据
     * @param response
     * @param request
     * @param repayment //processInstanceId
     * @throws Exception
     */
    @RequestMapping("/getRepaymentDetailPage.htm")
    public void getRepaymentDetailPage(HttpServletResponse response, HttpServletRequest request,
		RepaymentVO repayment) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("repayment", repayment);
        PageHelper.startPage(repayment.getPageNum(), repayment.getPageSize());
        List<Map> repaymentDetailMap = repaymentService.getRepaymentDetailPageByMap(paramMap);
        if (CollectionUtils.isNotEmpty(repaymentDetailMap)) {
        	for(Map<String,Object> map: repaymentDetailMap){
                roundBigDecimal(map);
            }
		}
        
		PageInfo<Map> page = new PageInfo<Map>(repaymentDetailMap);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    
    /**
     * 正常还款、提前还款等界面信息获取
     * @param request
     * @param response
     * @param repayment
     * @throws ServiceException
     * @throws ParseException
     */
    @RequestMapping(value = { "/queryNormalRepayment", "/queryForcePayoffRepayment" })
    public void queryNormalRepayment(HttpServletRequest request,HttpServletResponse response,
    		RepaymentVO repayment) throws ServiceException, ParseException{
        ParamChecker.checkEmpty(repayment.getType(), "还款操作类型");
        // 返回给页面的Map
  		Map<String, Object> returnMap = new HashMap<String, Object>();
  		Map<String, Object> paramMap = new HashMap<>();
        	
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date realPaymentDate;
        
        if (StringUtil.isEmpty(repayment.getRealRepaymentDate())) {
            realPaymentDate = new Date();
        } else {
            realPaymentDate = sdf.parse(repayment.getRealRepaymentDate());
        }
        Map<String, Object> result = repaymentService.queryRepaymentDetail(repayment.getType(), repayment.getProcessInstanceId(), realPaymentDate);
		// 对BigDecimal做四舍五入
        roundBigDecimal(result);
        if (result.get("realPaymentDate") != null) {
        	result.put("realPaymentDate", DateUtil.getFormatDate((Date)result.get("realPaymentDate")) );
		}
        if (result.get("repaymentTime") != null) {
        	result.put("repaymentTime", DateUtil.getFormatDate((Date)result.get("repaymentTime")) );
		}
        
        BigDecimal account=(BigDecimal)result.get("account");
        String big = RMB.toBigAmt(account.doubleValue());
        returnMap.put("amountBigFormat", big);
        returnMap.put(Constant.RESPONSE_DATA, result);
 		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
 		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
 		// 返回给页面
 		ServletUtils.writeToResponse(response, returnMap);
    }
    
    
    
    /**
     * 还款管理--提交
     * @param json
     * @param response
     * @param request
     * @throws ServiceException
     */
    @RequestMapping("/pay")
    public void pay1(String json, HttpServletResponse response, HttpServletRequest request) throws ServiceException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        Long userId = loginUser.getId();
        Map<String,Object> repayment = JsonUtil.parse(json,HashMap.class);
        ParamChecker.checkEmpty("type", "还款操作类型");
        // 类型 1、正常 2、提前还款 3、违规还款 4、强制结清 5、房屋处置
        Integer type = Integer.parseInt(repayment.get("type").toString());
        String processInstanceId = repayment.get("processInstanceId").toString();
        if (type != 5) {
        	 // 还款明细
	        Integer period = Integer.parseInt(repayment.get("period").toString());
	        // 检查是否可以还款
	        validatePay(processInstanceId, period);
		}
        repaymentService.pay(type, repayment, userId);
        returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        ServletUtils.writeToResponse(response, returnMap);

    }
    
    /**
     * 提交补充信息筛查申请
     * @description
     * @param json
     * @param response
     * @param request
     * @throws ServiceException
     * @author huy
     * @return void
     * @since  1.0.0
     */
    @RequestMapping("/supplyInvestigat.htm")
    public void supplyInvestigat(String processInstanceId, HttpServletResponse response, HttpServletRequest request) throws ServiceException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        Long userId = loginUser.getId();
//        Map<String,Object> paramMap = JsonUtil.parse(json,HashMap.class);
//        String processInstanceId = paramMap.get("processInstanceId").toString();
        repaymentService.startSupplyInvestigateProcess(processInstanceId, userId);
        returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        ServletUtils.writeToResponse(response, returnMap);

    }
    
    private void validatePay(String processInstanceId, Integer period) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("processInstanceId", processInstanceId);
        params.put("period", period);
        params.put("isPayoff", 0);
        Boolean canPay = false;
        canPay = repaymentService.validateWhetherCanPay(params);
        if (canPay == false) {
            String msg = "项目编号:" + processInstanceId + " 第" + period + "期欠费已还款不能再次还款";
            throw new ServiceException(Constant.FAIL_CODE_VALUE, msg);
        }
    }
    

    
    
    
    /**
     * 导出表格
     * @param processInstanceId
     * @param request
     * @param response
     * @throws ServiceException
     */
    @RequestMapping("/exportXls")
    public void exportXls(final HttpServletRequest request,final HttpServletResponse response,
    		final RepaymentVO repayment) throws ServiceException {
        SysUser loginUser = getLoginUser(request);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final List<String> coverdOffices = this.getCoverdOffices(loginUser);
        try {
            List<Map<String, Object>> list;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("coverdOffices", coverdOffices);
            params.put("repayment",repayment);
            
            PageHelper.startPage(1,10);
            list = repaymentService.queryFactBorrowList(params);
            for(Map<String,Object> detail:list){
                roundBigDecimal(detail);
            }
//            String customerName = (String) list.get(0).get("customerName");
            response.setContentType("application/download");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + new String(("customerName" + "-还款信息.xls").getBytes("utf8"), "iso8859-1"));

            PoiUtil poi = new PoiUtil();
            PoiUtil.Render dateRander = new PoiUtil.Render() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");

                @Override
                public Object render(Object value) {
                    if (value == null)
                        return value;
                    return sdf.format(value);
                }
            };
            for(Map<String, Object> factBorrow:list){
                Object value=factBorrow.get("productType");
                if(value!=null&&StringUtils.hasLength(value.toString())){
                    factBorrow.put("productTypeText", productTypeMapper.get(value));
                }
            }
            poi.registerTitlaMap(new Object[][] { new Object[] { "项目编号", "projectCode" },
                    new Object[] { "合同编号", "htbh" }, new Object[] { "产品类型", "productTypeText"}, 
                    new Object[] { "放款日期", "loanTime"},
                    new Object[] { "客户名称", "customerName" },
                    new Object[] { "分公司", "officeName" },
                    new Object[] { "借款期数", "timeLimit" },
                    new Object[] { "贷款金额", "account" },
                    
                    new Object[] { "已还本金", "repayCapital" },
                    new Object[] { "已还利息", "repayInterest" }, 
                    new Object[] { "剩余本金", "needpayCapital" }, 
                    
                    new Object[] { "服务费", "fwf" },
                    new Object[] { "账户余额", "balance" },
                    new Object[] { "还款状态", "repaymentStatusText" },
                    new Object[] { "还款处理", "repaymentProcessText" }
            
            });
            poi.writeXls("客户信息", list);
            params = new HashMap<String, Object>();
            params.put("realHkDate", new Date());
            
            PageHelper.startPage(1,10000);
            list = repaymentService.queryRepaymentDetailList(params);
            for(Map<String,Object> detail:list){
                roundBigDecimal(detail);
                detail.put("repaymentTime", sdf.format(sdf.parse(detail.get("repaymentTime").toString())));
                String realpaymentTimeStr;
                try {
                    Date realpaymentTime=sdf1.parse(detail.get("realpaymentTime").toString());
                    realpaymentTimeStr=sdf1.format(realpaymentTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                    realpaymentTimeStr=detail.get("realpaymentTime").toString();
                }
                detail.put("realpaymentTime",realpaymentTimeStr );
            }
            poi.registerTitlaMap(
                    new Object[][] { 
                            new Object[] { "期次", "period" },
                            new Object[] { "还款日期", "repaymentTime"},
                            new Object[] { "本期应还金额", "totalAccount" },
                            new Object[] { "已还平台服务费", "repaymentPlatformAmount" },
                            
                            new Object[] { "已还本期利息", "repaymentInterestAmount" },
                            new Object[] { "已还本期本金", "repaymentCapitalAmount" },
                            new Object[] { "本期实还金额", "realAccount" },
                            
                            new Object[] { "剩余待还本金", "needrepayCapital" },
                            new Object[] { "本期账户余额", "customerBalance" },
                            
                            new Object[] { "罚息", "defaultInterest" }, 
                            new Object[] { "违约金", "penalty" },
                            new Object[] { "减免总额", "allBreaks" },
                            new Object[] { "实际还款日期", "realpaymentTime"},
                            
                            new Object[] { "还款状态", "repaymentStausText"},
                            new Object[] { "是否还清", "isPayoffText"} }).writeXls("明细", list);
            
            poi.write(response.getOutputStream());
        } catch (Exception e) {
            log.error(e.getMessage(),e );
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
}
