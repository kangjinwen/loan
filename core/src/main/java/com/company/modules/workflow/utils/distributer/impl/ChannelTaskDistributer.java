package com.company.modules.workflow.utils.distributer.impl;

import com.alibaba.fastjson.JSONObject;
import com.company.common.utils.JudgeValue;
import com.company.common.utils.ParamChecker;
import com.company.modules.common.dao.PlProductDao;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.system.dao.SysUserDao;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;

import java.util.Map;

/**
 * 渠道分配器
 */
public class ChannelTaskDistributer implements TaskDistributer {
    @Override
    public void assignTask(TaskWrapper taskWrapper) throws ServiceException {
        //参数判空
        ParamChecker.checkEmpty(taskWrapper,"taskWrapper");

        DelegateTask delegateTask = taskWrapper.getDelegateTask();
        //当前流程实例id
        String processInstanceId = delegateTask.getProcessInstanceId();

        //通过工作流流程变量，拿到产品id，进而拿到对应的渠道id
        Integer channelId = getChannelByWorkFlow(delegateTask);
        //去该渠道分配任务人
        SysUserDao sysUserDao = ApplicationContextHelperBean.getBean(SysUserDao.class);
        Map<String,Object> user = sysUserDao.getUserByChannelId(channelId);
        if (user==null){
            //此处没查到，可能该渠道没有人，或者该渠道不存在
            throw new RuntimeException("根据渠道id随机分配任务人失败，请检查是否存在该渠道/该渠道下是否有成员");
        }

        //设置任务人
        delegateTask.setAssignee(user.get("user_name").toString());
    }

    //通过工作流流程变量，拿到产品id，进而拿到对应的渠道id
    private Integer getChannelByWorkFlow(DelegateTask delegateTask) {
        String productId = "";

        RuntimeService runtimeService = ApplicationContextHelperBean.getBean(RuntimeService.class);
        String creditConsultFrom = (String) runtimeService.getVariable(delegateTask.getExecutionId(),"creditConsultFrom");
        //解析json，拿到productId
        JSONObject jsonObject = JSONObject.parseObject(creditConsultFrom);
        if (jsonObject!=null){
            String plBorrowRequirement =  jsonObject.getString("plBorrowRequirement");
            JSONObject json = JSONObject.parseObject(plBorrowRequirement);
            if (json!=null){
                productId =  json.getString("productId");
            }
        }
        //查询，通过产品id查询到该产品的渠道id
        PlProductDao plProductDao = ApplicationContextHelperBean.getBean(PlProductDao.class);
        Integer channelId = plProductDao.getChannelIdByProductId(Integer.valueOf(productId));
        if (JudgeValue.isNullOr0OfInteger(channelId)){
            throw new RuntimeException("该产品对应渠道id为空，可能产品不存在，或者渠道不存在");
        }
        return channelId;
    }
}
