package com.company.modules.repay.action;

import com.company.modules.repay.domain.RpRepayDetail;
import com.company.modules.repay.domain.arithmetic.BaseBean;
import com.company.modules.repay.utils.ReturnData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 还款计划业务接口
 *
 *【展示】获取待设置还款计划的列表
 *
 *【操作】设置还款计划，并生成还款计划表
 *
 *【展示】获取某一个单子的还款计划表
 *
 *【操作】对某一期进行还款
 *
 *【操作】对某一期进行罚息还款
 *
 *【操作】执行提前还款
 * 提示：接口名定义，“还款计划”用repayPlan   “罚息”用 punish    ”还款“用refund
 * @author wdx
 */
@RestController
public interface RepayAction {
    /**
     * 获取待设置还款计划的列表
     *
     *
     * @param pageNum            分页参数，起始页            【int】    必传
     * @param pageSize           分页参数，每页显示条数       【int】    必传
     * @param request
     * @param response
     * @return {"projectCode": "1BJ00002","customerName": "张景荣","timeLimit": 20,"borrowAccount": 1800000.00,"repaymentStartTime": "2018-07-21",
                "repaymentTypeText": "等额本息","projectId": 1167}
            【参数说明】项目编号       客户姓名        借款期数      贷款金额          放款日期               还款方式
     *               projectCode   customerName   timeLimit   borrowAccount    repaymentStartTime    repaymentTypeText
     *               工程编号
     *               projectId
     */
    @RequestMapping(value = "modules/repay/RepayAction/getPreSetRepayPlanList.htm",method = RequestMethod.GET)
    void getPreSetRepayPlanList(
                                      Integer pageNum,
                                      Integer pageSize,
                                      HttpServletRequest request, HttpServletResponse response);

    /**
     * 设置还款计划，并生成还款计划表
     * @param serviceParams  业务参数        【string】【y】 要求json格式，包含下面的几个参数
     * *@param projectId   项目编号id        【long】【Y】
     * *@param account   借款金额           【double】【Y】
     * *@param repayType   还款方式        【string】【Y】 传字符串
                                             *        firstInterest,              //1.先息后本
                                             *        averageCapitalPlusInterest, //2.等额本息
                                             *        averageCapital,             //3.等额本金
                                             *        combine,                    //4.休假
                                             *        averageCapitalAverageInterest //5.等本等息
                                             *        unknown,//6.unknow
     * *@param term     借款期限           【int】【Y】
     * *@param repayRate    利率          【double】【Y】
     * *@param repayDay     还款日         【int】【Y】传 1-31整形数字
     * *@param isSkipFirstMonth 是否跳过首月 【boolean】【Y】
     * *@param isCalcServiceFee 服务费收取方式(True:服务费均摊到首12期)  【boolean】【Y】
     * *@param overduePenaltyRate  逾期罚息率  【double】【Y】
     * *@param serviceFee    服务费    【double】【Y】 传比例，如0.007
     * *@param loanDate      放款日期   【long】【Y】  传毫秒时间戳
     * *@param
     * @return
     */
    @RequestMapping(value = "modules/repay/RepayAction/createRepayPlan.htm",method = RequestMethod.GET)
    void createRepayPlan(String serviceParams,
                         HttpServletRequest request,
                         HttpServletResponse response);

    /**
     * 手动生成还款计划
     * 以下是repaySetting节点
     * *@param projectId         [int][Y]
     * *@param projectCode       [string][Y]        借款项目编号，例：1BJ00002(没有的话可以不传)
     * *@param term              【int】【Y】         借款期限
     * *@param account           【double】【Y】      借款金额
     * *@param repay_day         【int   】【Y】      还款日 1-30
     * *@param repay_rate        【double】【Y】      成本利率
     * *@param is_skip_first_month 【int】【Y】       是否跳过首月 0.否  1.是
     * *@param repay_type          【string】【Y】    还款方式:firstInterest:先息后本;
     *                                              averageCapitalPlusInterest:等额本息;
     *                                              averageCapital:等额本金;combine:休假;
     *                                              averageCapitalAverageInterest:等本等息
     * *@param is_calc_service_fee 【boolean】【Y】       服务费是否均摊到前十二期。是传true
     * *@param status              【int】【暂时不传】       '所处状态;0代表待还款，1代表还款中，2代表已还完'
     * *@param ahead_repay_setting 【int】【暂时不传】       是否提前还款，0代表否，1代表是
     * *@param over_due_penalty_rate【double】【Y】   逾期罚息率
     *
     * 以下是repayPlan节点
     * *@param term               【int   】【Y】      期次
     * *@param capital            【double】【Y】     本期本金
     * *@param interest           【double】【Y】     本期利息
     * *@param service_fee        【double】【Y】     本期服务费
     * *@param date               【long  】【Y】     本期还款日期  传毫秒时间戳
     * *@param actual_date        【double】【N】     本期实际还款日期  默认空
     * *@param repay_status_type  【string】【Y】     本期还款状态  传 prepare ，代表待还款
     * *@param is_payoff          【int   】【Y】     本期是否还清  0.未还清  1.还清  ，此处传0
     * *@param serviceParams
     * @param request
     * @param response
     */
    @RequestMapping(value = "modules/repay/RepayAction/handCreateRepayPlan.htm",method = RequestMethod.GET)
    void handCreateRepayPlan(@RequestParam(required = true) String repaySetting,
                             @RequestParam(required = true) String repayPlan,
                             HttpServletRequest request,
                             HttpServletResponse response);
    /**
     * 根据项目编号id（projectId）获取某一个该的还款计划表
     * *@param projectId     【long】【Y】 项目编号（不是项目编号的id）
     * *@param pageNum   【int】【Y】    页码
     * *@param pageSize  【int】【Y】    每页条数
     * *@return
     */
    @RequestMapping(value = "modules/repay/RepayAction/getRepayPlanByAnyKey.htm",method = RequestMethod.GET)
    void getRepayPlanByAnyKey(long projectId, BaseBean bb, HttpServletRequest request, HttpServletResponse response);

    /**
     * 对某一期进行罚息还款
     * *@param  projectId        【long】【Y】              项目编号主键
     * @param
     * @param
     * @param
     *
     * @return
     */
    @RequestMapping(value = "modules/repay/RepayAction/refundPunishedToTheDate.htm",method = RequestMethod.GET)
    ReturnData refundPunishedToTheDate();

    /**
     * 对某一期进行还款(即更改还款状态)
     * 业务说明：1.若没有逾期，本期应还就是 （本息和，罚息为0）,期限内还款，则状态设置为已还完
     *         2.若逾期，本期应还就是（本息和，罚息），超出期限还清所有，则状态标识为已还完，
     *           还款状态为【逾期已还本息已还罚息】；逾期后只还本息，状态标识为未还清，还款状
     *           态为【逾期已还本息未还罚息】
     *
     * *@param projectId             项目编号主键              【int】【Y】
     * *@param term                  期次                    【int】【Y】
     * *@param type                  还款类型                  【int】【Y】  1.还本息 2.还本息加罚息 3.还罚息  4.
     * *@param capital               本期本金                 【double】【N】
     * *@param interest              本期利息                 【double】【N】
     * *@param totalAmount           本期应还                 【double】【N】
     * *@param repayInterm           本期已还                 【double】【N】
     * *@param penaltyAmount         罚息金额                 【double】【N】
     * *@param repayStatusType       还款状态                 【string】【Y】  传
     *
     * @return
     */
    @RequestMapping(value = "modules/repay/RepayAction/refundToTheDate.htm",method = RequestMethod.GET)
    void refundToTheTerm(RpRepayDetail rpRepayDetail,int type,HttpServletRequest request,HttpServletResponse response);

    /**
     * 提前还款
     * @return
     */
    @RequestMapping(value = "modules/repay/RepayAction/preRefund.htm",method = RequestMethod.GET)
    ReturnData preRefund();
}
