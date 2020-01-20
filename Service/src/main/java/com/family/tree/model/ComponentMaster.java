package com.family.tree.model;

public class ComponentMaster {
	
	private long componentId;
	private long componentMasterId;
	private long isActive;
	private String childInMaster;
	private String masterSql;
	public long getComponentId() {
		return componentId;
	}
	public void setComponentId(long componentId) {
		this.componentId = componentId;
	}
	public long getComponentMasterId() {
		return componentMasterId;
	}
	public void setComponentMasterId(long componentMasterId) {
		this.componentMasterId = componentMasterId;
	}
	public long getIsActive() {
		return isActive;
	}
	public void setIsActive(long isActive) {
		this.isActive = isActive;
	}
	public String getChildInMaster() {
		return childInMaster;
	}
	public void setChildInMaster(String childInMaster) {
		this.childInMaster = childInMaster;
	}
	public String getMasterSql() {
		return masterSql;
	}
	public void setMasterSql(String masterSql) {
		this.masterSql = masterSql;
	}

}
