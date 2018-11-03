package com.company.modules.appointment.action;

import com.company.modules.repay.domain.arithmetic.BaseBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 小贷预约列表相关接口，流程无关
 */
@RestController
public interface AppointmentInfoAction {
    /**
     * 添加预约记录
     * @param name              【string】【Y】        姓名
     * @param phone             【string】【Y】        电话
     * @param type              【int   】【Y】        预约类型：1.预约下户 2.预约公证 3.预约办押
     * @param remark            【string】【Y】        备注
     * @param appointTime       【long  】【Y】        时间，传毫秒时间戳
     * @param request
     * @param response
     */
    @RequestMapping("/modules/AppointmentInfoAction/addAppointmentInfo.htm")
    void addAppointmentInfo(@RequestParam(required = true)String processInstanceId,
                            @RequestParam(required = true) String name,
                            @RequestParam(required =true) String phone,
                            @RequestParam(required = true) Integer type,
                            @RequestParam(required =true) String remark,
                            @RequestParam(required = true) Long appointTime,
                            HttpServletRequest request,
                            HttpServletResponse response);

    /**
     * 获取列表
     * @param processInstanceId  [string][n]  流程实例id
     * @param name   [int][n]   预约的人
     * @param phone  [int][n]   手机号
     * @param type   [int][n]   预约类型：1.预约下户 2.预约公证 3.预约办押
     * @param beginTime      [long][n]  开始时间
     * @param endTime        [long][n]  结束时间
     * *@param pageNum       [int][n]   页码
     * *@param pageSize      [int][n]   每页条数
     * @param request
     * @param response
     */
    @RequestMapping("/modules/AppointmentInfoAction/getAppointmentInfoByAnyKey.htm")
    void getAppointmentInfoByAnyKey(@RequestParam(required = true)String processInstanceId,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required =false) String phone,
                                    @RequestParam(required = false) Integer type,
                                    @RequestParam(required = false) Long beginTime,
                                    @RequestParam(required = false) Long endTime,
                                    @RequestParam(required = false) BaseBean bb,
                                    HttpServletRequest request,
                                    HttpServletResponse response);
}
