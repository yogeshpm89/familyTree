package com.family.tree.model;

public class ComponentParams {
	private long componentId;
	private long componentParamId;
	private String componentParamName;
	private String componentParamDBAlias;
	private long isActive;
	private String dbOperator;
	private int isStaticParam;
	private String childInMaster;
	
	
	public long getComponentId() {
		return componentId;
	}
	public void setComponentId(long componentId) {
		this.componentId = componentId;
	}
	public long getComponentParamId() {
		return componentParamId;
	}
	public void setComponentParamId(long componentParamId) {
		this.componentParamId = componentParamId;
	}
	public String getComponentParamName() {
		return componentParamName;
	}
	public void setComponentParamName(String componentParamName) {
		this.componentParamName = componentParamName;
	}
	public String getComponentParamDBAlias() {
		return componentParamDBAlias;
	}
	public void setComponentParamDBAlias(String componentParamDBAlias) {
		this.componentParamDBAlias = componentParamDBAlias;
	}
	public long getIsActive() {
		return isActive;
	}
	public void setIsActive(long isActive) {
		this.isActive = isActive;
	}
	public String getDbOperator() {
		return dbOperator;
	}
	public void setDbOperator(String dbOperator) {
		this.dbOperator = dbOperator;
	}
	public String getChildInMaster() {
		return childInMaster;
	}
	public void setChildInMaster(String childInMaster) {
		this.childInMaster = childInMaster;
	}
	public int getIsStaticParam() {
		return isStaticParam;
	}
	public void setIsStaticParam(int isStaticParam) {
		this.isStaticParam = isStaticParam;
	}
	@Override
	public String toString() {
		return "ComponentParams [componentId=" + componentId + ", componentParamId=" + componentParamId
				+ ", componentParamName=" + componentParamName + ", componentParamDBAlias=" + componentParamDBAlias
				+ "]";
	}
	
}
