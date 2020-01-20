package com.family.tree.model;

import java.util.Map;
import java.util.Set;

public class ComponentConfig {

	private long componentId;
	private String compnentURL;
	private long isActive;
	private String defaultSort;
	private String sql;
	private Set<String> staticFilter;
	private Map<String, ComponentParams> componentParams;
	Map<String, ComponentMaster> componentMaster;

	public long getComponentId() {
		return componentId;
	}

	public void setComponentId(long componentId) {
		this.componentId = componentId;
	}

	public String getCompnentURL() {
		return compnentURL;
	}

	public void setCompnentURL(String compnentURL) {
		this.compnentURL = compnentURL;
	}

	public long getIsActive() {
		return isActive;
	}

	public void setIsActive(long isActive) {
		this.isActive = isActive;
	}

	public String getDefaultSort() {
		return defaultSort;
	}

	public void setDefaultSort(String defaultSort) {
		this.defaultSort = defaultSort;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Map<String, ComponentMaster> getComponentMaster() {
		return componentMaster;
	}

	public void setComponentMaster(Map<String, ComponentMaster> componentMaster) {
		this.componentMaster = componentMaster;
	}

	public Map<String, ComponentParams> getComponentParams() {
		return componentParams;
	}

	public void setComponentParams(Map<String, ComponentParams> componentParams) {
		this.componentParams = componentParams;
	}

	public Set<String> getStaticFilter() {
		return staticFilter;
	}

	public void setStaticFilter(Set<String> staticFilter) {
		this.staticFilter = staticFilter;
	}

	@Override
	public String toString() {
		return "ComponentConfig [componentId=" + componentId + ", compnentURL=" + compnentURL + ", defaultSort="
				+ defaultSort + "]";
	}

}
