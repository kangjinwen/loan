package com.company.modules.workflow.utils.distributer.impl;

import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;

/**
 * 空任务分配器
 * @author FHJ
 *
 */
public class EmptyTaskDistributer implements TaskDistributer{
	@Override
	public void assignTask(TaskWrapper taskWrapper) {
        // 空实现
	}

}
