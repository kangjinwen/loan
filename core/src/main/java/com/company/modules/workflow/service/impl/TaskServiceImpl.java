package com.company.modules.workflow.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.company.common.utils.ParamChecker;
import com.company.modules.common.dao.PubLoanprocessDao;
import com.company.modules.common.domain.PubLoanprocess;
import com.company.modules.common.exception.NoExecutionPrivilegeException;
import com.company.modules.common.exception.ObjectNotFoundException;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.exception.TaskExecutionException;
import com.company.modules.common.exception.TaskNotFoundException;
import com.company.modules.system.dao.SysOfficeDao;
import com.company.modules.system.dao.SysRoleDao;
import com.company.modules.system.dao.SysUserDao;
import com.company.modules.system.domain.SysRole;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysUserService;
import com.company.modules.workflow.dao.TaskDao;
import com.company.modules.workflow.service.RDTaskService;
import com.company.modules.workflow.utils.factory.TaskExecutionStrategyFactory;
import com.company.modules.workflow.utils.strategy.TaskExecutionStrategy;
import com.company.modules.workflow.utils.strategy.TaskServiceWrapper;

/**
 * 流程任务Service
 * @author FHJ
 *
 */
@Service
public class TaskServiceImpl implements RDTaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
	HistoryService historyService;
    @Autowired
	private TaskService taskService;

	@Autowired
	private SysUserService userService;

	@Autowired
	private SysRoleDao sysRoleDao;

	@Autowired
	private SysOfficeDao sysOfficeDao;

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private PubLoanprocessDao pubLoanprocessDao;

	@Autowired
	private TaskDao taskDao;

	//经办人，此处为下户，用于机构初审之后的押品管理定向指向为发起者
	private static final String ASSIGNEE = "usertask-xiahu";

	private static final String TASKUSER_SECONDARY_FINAL_AUDIT = "taskuser-secondary-final-audit";

	/**
	 * 直接调用Activiti的API来完成任务
	 * API内部用观察者模式（listener）维护了task的生命周期，设置在task的各个监听器会被调用，
	 * 所以需要在这里将Transactional加上以实现监听器与流程API的一起事务回滚
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void completeTask(String taskId, String executor, Map<String, Object> bpmValues) throws ServiceException {
		preCheckParams(taskId, executor);

		// TODO FHJ这个从系统配置中读取出来
		Object qualifier = "com.company.modules.workflow.utils.strategy.impl.StrictOneUserExecutionStrategy";
		TaskExecutionStrategy taskExecutionStrategy;

		try {
			taskExecutionStrategy = TaskExecutionStrategyFactory.getInstance().getObject(qualifier);
			taskExecutionStrategy.executeTask(new TaskServiceWrapper(taskService), taskId, executor, bpmValues);
		} catch (ObjectNotFoundException e) {
			throw new ServiceException(e.getMessage(), e);
		} catch (NoExecutionPrivilegeException e) {
			throw new ServiceException(e.getMessage(), e);
		} catch (TaskNotFoundException e) {
			throw new ServiceException(e.getMessage(), e);
		} catch (TaskExecutionException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}



	@Override
	@Transactional(rollbackFor = Exception.class)
	public void completeTask(String taskId, String executor, Map<String, Object> serviceVarMap, Map<String, Object> bpmValues) throws ServiceException {
		preCheckParams(taskId, executor);

		// TODO FHJ这个从系统配置中读取出来
		Object qualifier = "com.company.modules.workflow.utils.strategy.impl.StrictOneUserExecutionStrategy";
		TaskExecutionStrategy taskExecutionStrategy;

		try {
			taskExecutionStrategy = TaskExecutionStrategyFactory.getInstance().getObject(qualifier);
			taskExecutionStrategy.executeTask(new TaskServiceWrapper(taskService), taskId, executor, bpmValues);

			List<Task> taskList = taskService.createTaskQuery().processInstanceId((String)serviceVarMap.get("processInstanceId")).list();
			String nextAssignee = "";
			String nextAssigneeName = "";
			if(CollectionUtils.isNotEmpty(taskList)){
				for(int i=0;i<taskList.size();i++){
					Task task = taskList.get(i);
					if (task!=null) {
						try {
							SysUser user = sysUserDao.getUserByUserName(task.getAssignee());
							if(user != null){
								nextAssignee += task.getAssignee();
								nextAssigneeName += user.getName();
							}
						} catch (PersistentDataException e) {
							throw new ServiceException(e);
						}
						if(i < taskList.size()-1){
							nextAssignee += ",";
							nextAssigneeName += ",";
						}
					}
				}
				if(!StringUtils.isEmpty(nextAssignee)){
				PubLoanprocess pubLoanprocess = pubLoanprocessDao.getByTaskId(taskId);
				if (pubLoanprocess!=null) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("id", pubLoanprocess.getId());
					param.put("nextAssignee", nextAssignee);
					param.put("nextAssigneeName", nextAssigneeName);
					pubLoanprocessDao.updateById(param);
				}
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map.put("processInstanceId",(String)serviceVarMap.get("processInstanceId"));
				List<PubLoanprocess> list = pubLoanprocessDao.getPageListByMap(map);
				if (list!=null && list.size()>0) {
					for (int i = 0; i < list.size(); i++) {
						map1.put("id", list.get(i).getId());
						map1.put("workflowNextAssigneeName", nextAssigneeName);
						pubLoanprocessDao.updateWorkflowNextAssigneeNameById(map1);
					}
				}
				}
			}
		} catch (ObjectNotFoundException e) {
			throw new ServiceException(e.getMessage(), e);
		} catch (NoExecutionPrivilegeException e) {
			throw new ServiceException(e.getMessage(), e);
		} catch (TaskNotFoundException e) {
			throw new ServiceException(e.getMessage(), e);
		} catch (TaskExecutionException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void completeOrgaFirstTask(String taskId, String executor, Map<String, Object> serviceVarMap, Map<String, Object> bpmValues) throws ServiceException {
		preCheckParams(taskId, executor);

		// TODO FHJ这个从系统配置中读取出来
		Object qualifier = "com.company.modules.workflow.utils.strategy.impl.StrictOneUserExecutionStrategy";
		TaskExecutionStrategy taskExecutionStrategy;

		try {
			taskExecutionStrategy = TaskExecutionStrategyFactory.getInstance().getObject(qualifier);
			taskExecutionStrategy.executeTask(new TaskServiceWrapper(taskService), taskId, executor, bpmValues);
			List<HistoricTaskInstance> hisTaskList = historyService.createHistoricTaskInstanceQuery().processInstanceId((String) serviceVarMap.get("processInstanceId")).list();
			List<Task> taskList = taskService.createTaskQuery().processInstanceId((String)serviceVarMap.get("processInstanceId")).list();
			String nextAssignee = "";
			String nextAssigneeName = "";
			if(CollectionUtils.isNotEmpty(hisTaskList)){
				for(int i=0;i<hisTaskList.size();i++){
					HistoricTaskInstance task = hisTaskList.get(i);
					if (task!=null) {
						try {
									//得到下一个流程的任务处理人（应该从此处修改，去查询该流程实例发起者的 assignee 和name）

									if (null!=task.getTaskDefinitionKey()&&ASSIGNEE.equalsIgnoreCase(task.getTaskDefinitionKey())) {
										//拿到经办人信息（name和assignee）
										SysUser user = sysUserDao.getUserByUserName(task.getAssignee());
										nextAssignee += task.getAssignee();
										nextAssigneeName += user.getName();
									}


						} catch (PersistentDataException e) {
							throw new ServiceException(e);
						}

					}
				}
				if(!StringUtils.isEmpty(nextAssignee)){
					//根据任务id拿到审批历史
					PubLoanprocess pubLoanprocess = pubLoanprocessDao.getByTaskId(taskId);
					if (pubLoanprocess!=null) {
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("id", pubLoanprocess.getId());
						param.put("nextAssignee", nextAssignee);
						param.put("nextAssigneeName", nextAssigneeName);
						//更新审批历史表的nextAssignee
						pubLoanprocessDao.updateById(param);
					}
					Map<String, Object> map = new HashMap<String, Object>();
					Map<String, Object> map1 = new HashMap<String, Object>();

					map.put("processInstanceId",(String)serviceVarMap.get("processInstanceId"));
					//根据流程实例id查询 审批历史 的列表
					List<PubLoanprocess> list = pubLoanprocessDao.getPageListByMap(map);
					if (list!=null && list.size()>0) {
						for (int i = 0; i < list.size(); i++) {
							map1.put("id", list.get(i).getId());
							map1.put("workflowNextAssigneeName", nextAssigneeName);
							//更新审批历史表的nextAssignee
							pubLoanprocessDao.updateWorkflowNextAssigneeNameById(map1);
						}
					}
				}
			}
		} catch (ObjectNotFoundException e) {
			throw new ServiceException(e.getMessage(), e);
		} catch (NoExecutionPrivilegeException e) {
			throw new ServiceException(e.getMessage(), e);
		} catch (TaskNotFoundException e) {
			throw new ServiceException(e.getMessage(), e);
		} catch (TaskExecutionException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	private void preCheckParams(String taskId, String executor) throws ServiceException {
		if(StringUtils.isEmpty(taskId)){
			throw new ServiceException("任务执行失败：任务ID不能为空");
		}
		if(StringUtils.isEmpty(executor)){
			throw new ServiceException("任务执行失败：执行者名称不能为空");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void assignTask(String taskId, String newTaskAssignee, String assigner) throws ServiceException {
		ParamChecker.checkEmpty(taskId, "taskId");
		ParamChecker.checkEmpty(newTaskAssignee, "newTaskAssignee");
		ParamChecker.checkEmpty(assigner, "assigner");

		//检查流程状态,二终审以外的节点都不能进行重新分件
//		checkProcessState(taskId);

		// 找出原来的任务执行者
		String originalAssignee = getOriginalAssignee(taskId);

        SysUser newTaskAssigneeUser = getUserByUsername(newTaskAssignee);
        SysUser originalAssigneeUser = getUserByUsername(originalAssignee);
//        SysUser assignerUser = getUserByUsername(assigner);

        // 检查新执行者与原执行者是不是同一个人
//        checkIfTheyAreTheSamePerson(newTaskAssigneeUser, originalAssigneeUser);

        // 检查当前分配者是否是原执行者的上级
//        checkLeadershipForAssignerAndOrgAssignee(assignerUser, originalAssigneeUser);

		// 检查当前分配者是否是新执行者的上级
//        checkLeadershipForAssignerAndNewAssignee(assignerUser, newTaskAssigneeUser);

        // 检查新的执行者与原来的执行者是不是同一角色（岗位）
        checkRoleForNewAssigneeAndOrgAssignee(newTaskAssigneeUser, originalAssigneeUser);

		assignTaskToNewUser(taskId, newTaskAssignee);
	}

	private void assignTaskToNewUser(String taskId, String newTaskAssignee) {
		taskService.setAssignee(taskId, newTaskAssignee);
	}

	private void checkIfTheyAreTheSamePerson(SysUser userOne, SysUser userTwo) throws ServiceException {
        if (userOne.getId().equals(userTwo.getId())) {
            throw new ServiceException("您不需要将任务分配给他（她）自己！");
        }
    }

	private SysUser getUserByUsername(String username) throws ServiceException {
        ParamChecker.checkEmpty(username, "username");
        SysUser user = userService.getUserByUserName(username);
        if (user == null) {
            throw new ServiceException("没有查询到用户");
        }

        return user;
    }

    private void checkRoleForNewAssigneeAndOrgAssignee(SysUser newTaskAssigneeUser, SysUser originalAssigneeUser) throws ServiceException {
        List<SysRole> roleOfNewAssignee = null;
        List<SysRole> roleOforiginalAssignee = null;
        boolean haveSameRole = false;
        try {
            roleOfNewAssignee = sysRoleDao.getRoleListByUsername(newTaskAssigneeUser.getUserName());
            roleOforiginalAssignee = sysRoleDao.getRoleListByUsername(originalAssigneeUser.getUserName());

            if (CollectionUtils.isNotEmpty(roleOfNewAssignee)&& CollectionUtils.isNotEmpty(roleOforiginalAssignee)) {
				for (SysRole originalRole : roleOforiginalAssignee) {
					for (SysRole newRole : roleOfNewAssignee) {
						if (originalRole.getNid().equals(newRole.getNid())) {
							haveSameRole = true;
							break;
						}
					}
				}
			}
        } catch (PersistentDataException e) {
            throw new ServiceException("查询角色失败。", e);
        }

        if (!haveSameRole) {
            throw new ServiceException("您所分配的新的执行者["+ newTaskAssigneeUser.getName() +"]与原来的执行者["+ originalAssigneeUser.getName() +"]不是属于同一个角色，请重新分配。");
        }
    }

    private void checkLeadershipForAssignerAndOrgAssignee(SysUser assignerUser, SysUser originalAssigneeUser) throws ServiceException {
        if (!hasLeadershipRelation(assignerUser, originalAssigneeUser)) {
            throw new ServiceException("您不能分配您所指定的那个人[" + originalAssigneeUser.getName() + "]的任务，您不是他（她）的上级。");
        }
    }

    private void checkLeadershipForAssignerAndNewAssignee(SysUser assignerUser, SysUser newTaskAssigneeUser) throws ServiceException {
        if (!hasLeadershipRelation(assignerUser, newTaskAssigneeUser)) {
            throw new ServiceException("您不能把任务分配给您所指定的人[" + newTaskAssigneeUser.getName() + "]，您不是他（她）的上级。");
        }
    }

    private void checkProcessState(String taskId) throws ServiceException{
    	//获取流程当前状态
    	Map<String,Object> stateMap;
    	try {
    		stateMap = taskDao.getProcessState(taskId);
        } catch (PersistentDataException e) {
        	throw new ServiceException(e.getMessage(), e, e.getCode());
        }

    	//如果是初审状态不能进行重新分件
    	if(stateMap!=null && !TASKUSER_SECONDARY_FINAL_AUDIT.equals(stateMap.get("processState"))){
    		throw new ServiceException("该任务不是二终审环节,不能进行重新分件!");
    	}
    }

    private String getOriginalAssignee(String taskId) throws ServiceException {
		Task singleResult = taskService.createTaskQuery().taskId(taskId).singleResult();
		String originalAssignee = singleResult.getAssignee();
		if(StringUtils.isEmpty(originalAssignee)) {
            logger.error("该任务[taskId:{}]配置有误，没有找到执行者，请联系管理员!", taskId);
            throw new ServiceException("该任务配置有误，没有找到执行者，请联系管理员!");
        }
        return originalAssignee;
	}

    /**
     * 检查上下级关系。
     * 如果leader的offices中包含staff的office，那他们就属于上下级关系
     * @param leaderUser 上级
     * @param staffUser 下级
     * @return 是上下级关系，返回true
     * @throws ServiceException
     */
	private boolean hasLeadershipRelation(SysUser leaderUser, SysUser staffUser) throws ServiceException {
		ParamChecker.checkEmpty(leaderUser, "leaderUser");
		ParamChecker.checkEmpty(staffUser, "staffUser");

		String officesStr = leaderUser.getOfficeOver();
		String[] leaderOfficeArray = officesStr.split(",");

        String officeId = staffUser.getOfficeId();
        ParamChecker.checkEmpty(officeId, "officeId");

        boolean hasLeadership = false;
        for (int i = 0; i < leaderOfficeArray.length; i++) {
            String s = leaderOfficeArray[i];
            if (officeId.equals(s)) {
                hasLeadership = true;
            }
        }

        return hasLeadership;
	}

	@Override
	public List<Map> queryLoanChangeApprovalTasks(Long roleId, boolean isCompleted, Map<String, Object> paramMap) {
		if (isCompleted) {
			return taskDao.queryAlreadyDoneAfterLoanApprovalTasks(paramMap);
		}else {
			return taskDao.queryUndoneAfterLoanApprovalTasks(paramMap);
		}

	}

	@Override
	public List<Map<String, Object>> queryUserList(String taskId)
			throws ServiceException {
		List<Map<String,Object>> resultList = null;
		try {
			resultList = taskDao.queryUserList(taskId);
		} catch (PersistentDataException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage(), e);
		}
		return resultList;
	}

}
