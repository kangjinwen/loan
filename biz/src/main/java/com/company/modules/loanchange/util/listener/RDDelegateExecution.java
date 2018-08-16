package com.company.modules.loanchange.util.listener;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.EngineServices;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.VariableInstance;

/**
 *	@Description 流程变量
 *  @author wtc,wtc@erongdu.com
 *  @CreatTime 2015年6月19日 下午4:03:38
 *  @since version 1.0.0
 */
public class RDDelegateExecution implements DelegateExecution{
    private DelegateExecution excution;
    public RDDelegateExecution(DelegateExecution excution){
        this.excution=excution;
    }
    @Override
    public Map<String, Object> getVariables() {
        return excution.getVariables();
    }
    @Override
    public Map<String, Object> getVariablesLocal() {
        return excution.getVariablesLocal();
    }
    @Override
    public Object getVariable(String variableName) {
        return excution.getVariable(variableName);
    }
    @Override
    public Object getVariableLocal(String variableName) {
        return excution.getVariableLocal(variableName);
    }
    @Override
    public <T> T getVariable(String variableName, Class<T> variableClass) {
        return excution.getVariable(variableName, variableClass);
    }
    @Override
    public <T> T getVariableLocal(String variableName, Class<T> variableClass) {
        return excution.getVariableLocal(variableName, variableClass);
    }
    @Override
    public Set<String> getVariableNames() {
        return excution.getVariableNames();
    }
    @Override
    public Set<String> getVariableNamesLocal() {
        return excution.getVariableNamesLocal();
    }
    @Override
    public void setVariable(String variableName, Object value) {
        excution.setVariable(variableName, value);
    }
    @Override
    public Object setVariableLocal(String variableName, Object value) {
        return excution.setVariableLocal(variableName, value);
    }
    @Override
    public void setVariables(Map<String, ? extends Object> variables) {
        excution.setVariables(variables);
    }
    @Override
    public void setVariablesLocal(Map<String, ? extends Object> variables) {
        excution.setVariablesLocal(variables);
    }
    @Override
    public boolean hasVariables() {
        return excution.hasVariables();
    }
    @Override
    public boolean hasVariablesLocal() {
        return excution.hasVariablesLocal();
    }
    @Override
    public boolean hasVariable(String variableName) {
        return excution.hasVariable(variableName);
    }
    @Override
    public boolean hasVariableLocal(String variableName) {
        return excution.hasVariableLocal(variableName);
    }
    @Override
    public void createVariableLocal(String variableName, Object value) {
        excution.createVariableLocal(variableName, value);
    }
    @Override
    public void removeVariable(String variableName) {
        excution.removeVariable(variableName);
    }
    @Override
    public void removeVariableLocal(String variableName) {
        excution.removeVariableLocal(variableName);
    }
    @Override
    public void removeVariables(Collection<String> variableNames) {
        excution.removeVariables(variableNames);        
    }
    @Override
    public void removeVariablesLocal(Collection<String> variableNames) {
        excution.removeVariablesLocal(variableNames);
    }
    @Override
    public void removeVariables() {
        excution.removeVariables();
        
    }
    @Override
    public void removeVariablesLocal() {
        excution.removeVariablesLocal();
    }
    @Override
    public String getId() {
        return excution.getId();
    }
    @Override
    public String getProcessInstanceId() {
        return excution.getProcessInstanceId();
    }
    @Override
    public String getEventName() {
        return excution.getEventName();
    }
    @Override
    public String getBusinessKey() {
        return excution.getBusinessKey();
    }
    @Override
    public String getProcessBusinessKey() {
        return excution.getProcessBusinessKey();
    }
    @Override
    public String getProcessDefinitionId() {
        return excution.getProcessDefinitionId();
    }
    @Override
    public String getParentId() {
        return excution.getParentId();
    }
    @Override
    public String getCurrentActivityId() {
        return excution.getCurrentActivityId();
    }
    @Override
    public String getCurrentActivityName() {
        return excution.getCurrentActivityName();
    }
    @Override
    public String getTenantId() {
        return excution.getTenantId();
    }
    @Override
    public EngineServices getEngineServices() {
        return excution.getEngineServices();
    }
    public DelegateExecution getDelegateExecution(){
        return excution;
    }
	@Override
	public Map<String, VariableInstance> getVariableInstances() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Object> getVariables(Collection<String> variableNames) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, VariableInstance> getVariableInstances(Collection<String> variableNames) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Object> getVariables(Collection<String> variableNames, boolean fetchAllVariables) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, VariableInstance> getVariableInstances(Collection<String> variableNames,
			boolean fetchAllVariables) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, VariableInstance> getVariableInstancesLocal() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Object> getVariablesLocal(Collection<String> variableNames) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, VariableInstance> getVariableInstancesLocal(Collection<String> variableNames) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Object> getVariablesLocal(Collection<String> variableNames, boolean fetchAllVariables) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, VariableInstance> getVariableInstancesLocal(Collection<String> variableNames,
			boolean fetchAllVariables) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public VariableInstance getVariableInstance(String variableName) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getVariable(String variableName, boolean fetchAllVariables) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public VariableInstance getVariableInstance(String variableName, boolean fetchAllVariables) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public VariableInstance getVariableInstanceLocal(String variableName) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getVariableLocal(String variableName, boolean fetchAllVariables) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public VariableInstance getVariableInstanceLocal(String variableName, boolean fetchAllVariables) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setVariable(String variableName, Object value, boolean fetchAllVariables) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object setVariableLocal(String variableName, Object value, boolean fetchAllVariables) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSuperExecutionId() {
		// TODO Auto-generated method stub
		return null;
	}
}
