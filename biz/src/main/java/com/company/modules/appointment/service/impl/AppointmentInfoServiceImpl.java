package com.company.modules.appointment.service.impl;

import com.company.modules.appointment.service.AppointmentInfoService;
import com.company.modules.audit.dao.HousBillsDao;
import com.company.modules.repay.domain.arithmetic.BaseBean;
import com.company.modules.repay.utils.JudgeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AppointmentInfoServiceImpl implements AppointmentInfoService {
    @Autowired
    HousBillsDao housBillsDao;
    @Override
    public Map<String, Object> addAppointmentInfo(String procDefId,
                                                  String name,
                                                  String phone,
                                                  Integer type,
                                                  String remark,
                                                  Long appointTime){
        Long time = System.currentTimeMillis();
        int res = housBillsDao.addAppointmentInfo(procDefId,name,phone,type,remark,appointTime,time,time);
        if (res>0){
            Map<String,Object> map = new HashMap<>();
            map.put("msg","ok");
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> getAppointmentInfoByAnyKey(String procDefId,String name, String phone, Integer type, Long beginTime, Long endTime, BaseBean bb) {
        Integer count = housBillsDao.getAppointmentInfoCountByAnyKey(procDefId,name,phone,type,beginTime,endTime,bb.getBeginNum(),bb.getCheckNum());
        if (JudgeValue.isNullOrL0OfInteger(count)){
            Map<String, Object> map = new HashMap<>();
            map.put("count",0);
            return map;
        }
        List<Map<String, Object>> list = housBillsDao.getAppointmentInfoByAnyKey(procDefId,name,phone,type,beginTime,endTime,bb.getBeginNum(),bb.getCheckNum());
        Map<String, Object> map = new HashMap<>();
        map.put("count",count);
        map.put("list",list);
        return map;
    }
}
