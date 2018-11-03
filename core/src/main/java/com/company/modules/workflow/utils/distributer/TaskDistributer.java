package com.company.modules.workflow.utils.distributer;


import com.company.modules.common.exception.ServiceException;

/**
 * 任务分配器接口
 *
 */
public interface TaskDistributer {
	/**
	 * 分配任务
	 * @param task 那个需要被分配执行人员的任务
	 */
	void assignTask(TaskWrapper task) throws ServiceException;

}
