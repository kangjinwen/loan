package com.company.modules.system.model;

import java.util.List;

import com.company.modules.system.domain.SysMsgConfig;
import com.company.modules.system.domain.SysMsgModule;

public class MsgModuleModel extends SysMsgModule {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -616526029044963364L;
	
	private List<SysMsgConfig> msgConfigs;

	public List<SysMsgConfig> getMsgConfigs() {
		return msgConfigs;
	}

	public void setMsgConfigs(List<SysMsgConfig> msgConfigs) {
		this.msgConfigs = msgConfigs;
	}
}
