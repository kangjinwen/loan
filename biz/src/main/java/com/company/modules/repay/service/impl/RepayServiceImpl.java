package com.company.modules.repay.service.impl;

import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.repay.dao.RpRepayDetailDao;
import com.company.modules.repay.dao.RpRepaySettingDao;
import com.company.modules.repay.domain.RpRepayDetail;
import com.company.modules.repay.domain.RpRepaySetting;
import com.company.modules.repay.domain.arithmetic.*;
import com.company.modules.repay.service.RepayService;
import com.company.modules.repay.utils.JudgeValue;
import com.company.modules.workflow.service.RDZZTaskService;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 还款计划接口业务实现类
 */
@Service
@Transactional
public class RepayServiceImpl implements RepayService {

    /**
     * 获取待设置还款计划的列表
     * @param pageNum
     * @param pageSize
     * @param request
     * @param response
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> getPreSetRepayPlanList(Integer pageNum,
                                                 Integer pageSize,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
        RDZZTaskService rdzzTaskService = ApplicationContextHelperBean.getBean(RDZZTaskService.class);

        try {
            Map<String,Object> params = new HashMap<>();
            params.put("taskDefinition", "usertask-loan");
            PageInfo<Map<String, Object>> page = rdzzTaskService.queryPreSetRepayPlanTasks(null,params,false,pageNum,pageSize,"");
            return page;
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成还款计划表
     * @return
     */
    @Override
    public Map<String,Object> createRepayPlan(String serviceParams) {
        //1.解析前端参数
        RepaySettings repaySettings = parseServiceParams(serviceParams);

        //2.初始化repay对象
        Repay repay = RepayFactory.getInstance().CreateRepay();
        repay.setRepaySettings(repaySettings);
        repay.setLoanDate(new Date(repaySettings.getLoanDate()));
        repay.setServiceFee(repaySettings.getServiceFee()*repaySettings.getAccount());
        //执行还款算法
        RepayFactory.getInstance().CreateRepayDetailCalculator(repaySettings.getRepayType()).Calc(repay);
        //拿到还款计划表的集合
        List<RepayDetail> repayDetails = repay.getRepayDetails();
        //3.该项目的还款计划表入库（当有还款操作时，此表需要更新操作）
        RpRepayDetailDao rpRepayDetailDao = ApplicationContextHelperBean.getBean(RpRepayDetailDao.class);
        //根据projectId拿到外键的主键
        RpRepaySettingDao rpRepaySettingDao = ApplicationContextHelperBean.getBean(RpRepaySettingDao.class);
        //第一步:根据projectId拿到 pub_customer  的主键
        Map<String,Object> map  =rpRepaySettingDao.getForeignKeyByProjectId((int)repaySettings.getProjectId());
        //第二步:
        RpRepaySetting rrs = getRPRepaySettingParams(map,repaySettings);
        //插入并且返回主键
        int keyId = rpRepaySettingDao.insertSelective(rrs);


        int res = rpRepayDetailDao.insertRepayDetails(repayDetails,(int)repaySettings.getProjectId(),keyId);
        //int res =1;
        if (res>0){
            Map<String,Object> map1 = new HashMap<>();
            map1.put("msg","ok");
            return map;
        }
        return null;
    }


    /**
     * 手动生成还款计划
     * @param repaySetting
     * @param repayPlan
     * @return
     */
    @Override
    public Map<String, Object> handCreateRepayPlan(String repaySetting, String repayPlan) {
        //1.先插入repaysetting,返回主键
        RpRepaySettingDao rpRepaySettingDao = ApplicationContextHelperBean.getBean(RpRepaySettingDao.class);
        JSONObject json1 = JSONObject.fromObject(repaySetting);
            //参数拼接
        RepaySettings repaySettings = handRepaySettingsParams(repaySetting);
        int result1 = rpRepaySettingDao.insertSelectiveByProjectId(json1.getInt("projectId"),repaySettings);
        //2.在插入repayPlan(即RpRepayDetail对象)
            //参数拼接
        List<RepayDetail> repayDetails = handRepayPlanParams(repayPlan);

        RpRepayDetailDao rpRepayDetailDao = ApplicationContextHelperBean.getBean(RpRepayDetailDao.class);
        int result2 = rpRepayDetailDao.insertRepayDetailsBylistAndProjectId(repayDetails,json1.getInt("projectId"));

        Map<String,Object> resultMap = new HashMap<>();
        if (result1>0&&result2>0){
            resultMap.put("msg","ok");
        }
        resultMap.put("msg","no");
        return resultMap;
    }

    private List<RepayDetail> handRepayPlanParams(String repayPlan) {
        JSONObject json1 = JSONObject.fromObject(repayPlan);
        List<RepayDetail> repayDetails = new ArrayList<>();
        for (int i=1;i<=json1.size();i++){
            JSONObject json = JSONObject.fromObject(json1.get(i+""));
            RepayDetail repayDetail = RepayFactory.getInstance().CreateRepayDetail();
            repayDetail.setActualDate(null);//实际还款日期(记录刚生成的时候为空)
            repayDetail.setTerm(json.getInt("term"));//期次
            repayDetail.setCapital(json.getDouble("capital"));//本期本金
            repayDetail.setInterest(json.getDouble("interest"));//本期利息
            repayDetail.setServiceFee(json.getDouble("service_fee"));//本期服务费
            repayDetail.setPenaltyAmount(0);//罚息金额

            repayDetail.setTotalAmount(json.getDouble("capital")+
                    json.getDouble("interest")+json.getDouble("service_fee"));//本期应还
            repayDetail.setRepayInTerm(0);//本期已还
            repayDetail.setDate(new Date(json.getLong("date")));//还款日期
            repayDetail.setRepayStatusType(RepayStatusType.valueOf(json.getString("repay_status_type")));//还款状态
            repayDetail.setIsPayOff(false);//是否还清

            repayDetails.add(repayDetail);
        }


        return repayDetails;
    }

    private RepaySettings handRepaySettingsParams(String repaySetting) {

        RepaySettings repaySettings = RepayFactory.getInstance().CreateRepaySettings();
        JSONObject json1 = JSONObject.fromObject(repaySetting);
        repaySettings.setAccount(json1.getDouble("account"));//借款金额
        repaySettings.setTerm(json1.getInt("term"));//借款期限
        repaySettings.setRepayDay(json1.getInt("repay_day"));//还款日
        repaySettings.setRepayRate(json1.getDouble("repay_rate"));//成本利率
        repaySettings.setIsSkipFirstMonth(json1.getBoolean("is_skip_first_month"));//是否跳过首月
        repaySettings.setRepayType(RepayType.valueOf(json1.getString("repay_type")));//还款方式
        repaySettings.setIsCalcServiceFee(json1.getBoolean("is_calc_service_fee"));//服务费收取方式:True:服务费均摊到首12期
        //repaySettings.setAheadRepaySettings(null);
        repaySettings.setOverduePenaltyRate(json1.getDouble("over_due_penalty_rate"));//设置逾期罚息率

        return repaySettings;
    }

    public static void main(String[] args) {
           String param = "{\n" +
                   "\t\"1\": {\n" +
                   "\t\t\"capital\": \"1\",\n" +
                   "\t\t\"interest\": \"\"\n" +
                   "\t},\n" +
                   "\t\"2\": {\n" +
                   "\t\t\"capital\": \"2\",\n" +
                   "\t\t\"interest\": \"\"\n" +
                   "\t}\n" +
                   "}";
           JSONObject jsonObject = JSONObject.fromObject(param);
           for (int i=1;i<=jsonObject.size();i++){
               System.out.println(jsonObject.get(i+""));
           }
    }
    private RpRepaySetting getRPRepaySettingParams(Map<String,Object> map, RepaySettings repaySettings) {
        RpRepaySetting rrs = new RpRepaySetting();
        rrs.setCustomerId((int)map.get("customerId"));
        rrs.setHousPropertyInformationId((int)map.get("housPropertyInformationId"));
        rrs.setBorrowRequirementId((int)map.get("borrowRequirementId"));
        rrs.setAccount(repaySettings.getAccount());
       // rrs.setAheadRepaySetting(repaySettings.getAheadRepaySettings());
        rrs.setTerm(repaySettings.getTerm());
        //rrs.setRepayDay(repaySettings.getRepayDay());
        rrs.setRepayRate(repaySettings.getRepayRate());
        rrs.setIsSkipFirstMonth(repaySettings.getIsSkipFirstMonth());
        rrs.setIsCalcServiceFee(repaySettings.getIsCalcServiceFee());
        rrs.setOverDuePenaltyRate(repaySettings.getOverduePenaltyRate());
        //rrs.setRepayType();
        return rrs;
    }

    private RepaySettings parseServiceParams(String serviceParams) {
        //1.解析前端参数
        JSONObject jsonObject = JSONObject.fromObject(serviceParams);
        long projectId = jsonObject.getLong("projectId");//借款项目主键
        double account =  jsonObject.getDouble("account");//借款金额
        double repayRate = jsonObject.getDouble("repayRate");//利率
        int term = jsonObject.getInt("term");//借款期限
        String type = jsonObject.getString("repayType");//还款方式
        RepayType repayType = RepayType.valueOf(type);
        int repayDay = jsonObject.getInt("repayDay");//还款日
        boolean isSkipFirstMonth = jsonObject.getBoolean("isSkipFirstMonth");//是否跳过首月
        boolean isCalcServiceFee = jsonObject.getBoolean("isCalcServiceFee"); //服务费收取方式(True:服务费均摊到首12期)
        double overduePenaltyRate = jsonObject.getDouble("overduePenaltyRate");//逾期罚息率
        double serviceFee = jsonObject.getDouble("serviceFee");//服务费比例
        long loanDate = jsonObject.getLong("loanDate");
        //2.设置参数，执行还款计划算法
        //设置借款明细
        RepaySettings repaySettings = RepayFactory.getInstance().CreateRepaySettings();
        repaySettings.setTerm(term);
        repaySettings.setAccount(account);
        repaySettings.setRepayRate(repayRate);
        repaySettings.setRepayType(repayType);
        repaySettings.setRepayDay(repayDay);
        repaySettings.setIsCalcServiceFee(isCalcServiceFee);
        repaySettings.setIsSkipFirstMonth(isSkipFirstMonth);
        repaySettings.setOverduePenaltyRate(overduePenaltyRate);
        repaySettings.setServiceFee(serviceFee);
        repaySettings.setProjectId(projectId);
        repaySettings.setLoanDate(loanDate);
        return repaySettings;
    }


    /**
     * 根据项目编号projectId获取某一个该的还款计划表
     * *@param projectId     【long】【Y】 项目编号（不是项目编号的id）
     * *@param pageNum   【int】【Y】    页码
     * *@param pageSize  【int】【Y】    每页条数
     * @return
     */
    @Override
    public Map<String, Object> getRepayPlanByAnyKey(long projectId, BaseBean bb) {
        RpRepayDetailDao rpRepayDetailDao = ApplicationContextHelperBean.getBean(RpRepayDetailDao.class);
        Integer count = rpRepayDetailDao.getCountOfRepayDetailByProjectId(projectId);
        if (JudgeValue.isNullOrL0OfInteger(count)){
            Map<String,Object> map = new HashMap<>();
            map.put("msg","no");
            return map;
        }
        List<Map<String,Object>> list =  rpRepayDetailDao.getListOfRepayDetailByProjectId(projectId,bb);
        Map<String,Object> map = new HashMap<>();
        map.put("msg","ok");
        map.put("list",list);
        map.put("count",count);
        //3.组装报文，返回给前端
        return map;
    }

    @Override
    public Map<String,Object> refundToTheTerm(RpRepayDetail rpRepayDetail,int type) {
        RpRepayDetailDao rpRepayDetailDao = ApplicationContextHelperBean.getBean(RpRepayDetailDao.class);
        RpRepayDetail rrd = rpRepayDetailDao.selectByProjectIdAndTerm(rpRepayDetail);
        if (null==rrd){
            Map<String,Object> map = new HashMap<>();
            map.put("msg","当前项目不存在");
            return map;
        }
        if (rpRepayDetail.getRepayStatusType().equals(rrd.getRepayStatusType())){
            Map<String,Object> map = new HashMap<>();
            map.put("msg","目标还款状态和当前还款状态一直，无需更新");
            return map;
        }
        //如果当前还款状态：逾期未还本息未还罚息
        if (rrd.getRepayStatusType().equals(RepayStatusType.overdue.toString())){
            //此时还款有种两可能：1.还本息 2.还本息加罚息
            if (type==1){//还本息，此时本期应还（本金，利息，服务费，罚息），本期已还就应该设置
                // TODO: 2018/9/6  需求会变，暂时不写
            }
        }
        int res = rpRepayDetailDao.updateByPrimaryKeySelective(rpRepayDetail);
        if (res>0){
            Map<String,Object> map = new HashMap<>();
            map.put("msg","ok");
            return map;
        }
        return null;

    }
}
