package com.company.modules.contract.utils.databean;

import java.util.List;

import com.company.modules.common.utils.databean.BasicServiceDataBean;

public class ContractDataBean extends BasicServiceDataBean{
	private String remark;
	private List<Long> attachmentIds;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    @Override
    public String toString() {
        return "ContractDataBean{" +
                "remark='" + remark + '\'' +
                ", attachmentIds=" + attachmentIds +
                '}';
    }
}
