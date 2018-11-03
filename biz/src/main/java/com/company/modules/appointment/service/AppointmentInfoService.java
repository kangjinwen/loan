package com.company.modules.appointment.service;

import com.company.modules.repay.domain.arithmetic.BaseBean;

import java.util.Map;

public interface AppointmentInfoService {
    Map<String,Object> addAppointmentInfo(String procDefId,
                                          String name,
                                          String phone,
                                          Integer type,
                                          String remark,
                                          Long appointTime);

    Map<String, Object> getAppointmentInfoByAnyKey(String procDefId,
                                                   String name,
                                                   String phone,
                                                   Integer type,
                                                   Long createTime, Long endTime, BaseBean bb);
}
