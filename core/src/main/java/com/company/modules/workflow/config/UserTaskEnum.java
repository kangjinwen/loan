package com.company.modules.workflow.config;

/**
 * 工作流状态枚举
 * 注意：流程图中状态值都是中划线，枚举定义不支持 中划线。所以在使用的时候需要把下划线替换成 中划线
 * @author wdx
 */
public enum UserTaskEnum {

    usertask_appointment_guaranty("预约办押"),
    usertask_appointment_notarization("预约公证"),
    usertask_appointment_xiahu("预约下户"),
    usertask_xiahu("下户"),
    usertask_uploading("资料上传"),
    usertask_collateralAssess("风控初审"),
    usertask_control_review("风控复审"),
    usertask_organization_initialAudit("机构初审"),
    usertask_organization_reviewAudit("放款终审"),
    usertask_offline_tasks("签约办押"),
    usertask_face_audit("放款初审"),
    usertask_face_review("放款复审"),
    usertask_loan("放款管理");

    private String taskValue;
    private UserTaskEnum(String taskValue){
        this.taskValue = taskValue;
    }

    public String getTaskValue(){
        return taskValue;
    }

    public static void main(String[] args) {

        System.out.println(UserTaskEnum.usertask_collateralAssess.toString().replaceAll("_","-"));
        System.out.println(UserTaskEnum.usertask_collateralAssess.getTaskValue().equals("风控初审"));

    }
}
