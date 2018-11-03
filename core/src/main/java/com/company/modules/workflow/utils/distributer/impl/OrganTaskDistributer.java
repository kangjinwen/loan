package com.company.modules.workflow.utils.distributer.impl;

import com.alibaba.fastjson.JSONObject;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.system.dao.SysOfficeDao;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysUserService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 机构分配器
 * 机构审核的执行人需要从 放款机构 里面的成员去拿
 * @author wdx
 */
public class OrganTaskDistributer implements TaskDistributer {

    @Override
    public void assignTask(TaskWrapper taskWrapper) throws ServiceException {
        DelegateTask delegateTask = taskWrapper.getDelegateTask();

        //拿到officeId
       String officeIdList = getOrganIdFromFolw(delegateTask);
        //根据officeId去该放款机构挑选随机的执行人
        SysUserService sysUserService = ApplicationContextHelperBean.getBean(SysUserService.class);
        SysUser sysUser = sysUserService.getNextAssigneeByOfficeId(null,officeIdList,delegateTask.getProcessInstanceId(),null);
        if (null==sysUser){
            throw new ServiceException("根据放款机构分配执行人失败，检查是否产品数据异常");
        }
        delegateTask.setAssignee(sysUser.getUserName());
    }
    //通过工作流，拿到产品id，进而查询到该产品对应的部门id
    private String getOrganIdFromFolw(DelegateTask delegateTask) {
        String productId = "";
        String organId = "";
        RuntimeService runtimeService = ApplicationContextHelperBean.getBean(RuntimeService.class);
        String service_variables = (String) runtimeService.getVariable(delegateTask.getExecutionId(),"service_variables");
        //解析json，拿到productId
        JSONObject jsonObject = JSONObject.parseObject(service_variables);
        if (jsonObject!=null){
            String plBorrowRequirement =  jsonObject.getString("plBorrowRequirement");
            JSONObject json = JSONObject.parseObject(plBorrowRequirement);
            if (json!=null){
                productId =  json.getString("productId");
            }

        }
        //根据productId查询该产品所在机构

        SysOfficeDao sysOfficeDao = ApplicationContextHelperBean.getBean(SysOfficeDao.class);
        //目前产品和放款机构是一对一的关系，后期可能拓展，查询命名暂时设为多个的形式。
        //目前产品表机构是存储的office_ids是数组形式，所以查到的数据是 单个
        Map<String,Object> organProList =  sysOfficeDao.listOrganByProductId(productId);
        /*List<String> organIds = new ArrayList<>();
        if (null!=organProList){
            organId = (String) organProList.get("office_ids");
            String[] arr = organId.split(",");
            for (int i=0;i<arr.length;i++){
                organIds.add(arr[i]);
            }
        }*/
        return (String) organProList.get("office_ids");
    }
    public static void main(String[] args) {
        List<String>  list = new ArrayList<>();
        String str = "a,b,c";
        String[] arrs = str.split(",");
        for (int i=0;i<arrs.length;i++){
            System.out.println(arrs[i]);
        }
        System.out.println(arrs.toString());
    }
}
