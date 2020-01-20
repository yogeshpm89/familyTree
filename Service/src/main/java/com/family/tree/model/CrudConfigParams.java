package com.family.tree.model;

public class CrudConfigParams {
	/*
	 * Entity Attribute Name Entity Attribute Mapping Is Active Is Mandatory
	 * Entity Attribute type
	 */
	private String entityAttributeName;
	private String entityAttributeMapping;
	private int isActive;
	private int isMandatory;
	private String entityAttributType;

	public String getEntityAttributeName() {
		return entityAttributeName;
	}

	public void setEntityAttributeName(String entityAttributeName) {
		this.entityAttributeName = entityAttributeName;
	}

	public String getEntityAttributeMapping() {
		return entityAttributeMapping;
	}

	public void setEntityAttributeMapping(String entityAttributeMapping) {
		this.entityAttributeMapping = entityAttributeMapping;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(int isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getEntityAttributType() {
		return entityAttributType;
	}

	public void setEntityAttributType(String entityAttributType) {
		this.entityAttributType = entityAttributType;
	}

	@Override
	public String toString() {
		return "CrudConfigParams [entityAttributeName=" + entityAttributeName
				+ ", entityAttributeMapping=" + entityAttributeMapping
				+ ", isActive=" + isActive + ", isMandatory=" + isMandatory
				+ ", entityAttributType=" + entityAttributType + "]";
	}

}
