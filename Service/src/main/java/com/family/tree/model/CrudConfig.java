package com.family.tree.model;

import java.util.Map;

public class CrudConfig {
	private Long crudConfigId;
	private String entityName;
	private String primaryKeyColumn;
	private String sequenceName;
	/*
	 * Entity Attribute Name Entity Attribute Mapping Is Active Is Mandatory
	 * Entity Attribute type
	 */
	private Map<String, CrudConfigParams> entityMappingDetails;
	private int entityHardDeleteAllowed;
	private String entityHistoryName;
	private String entityJsonMappingForPrimaryKey;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Map<String, CrudConfigParams> getEntityMappingDetails() {
		return entityMappingDetails;
	}

	public void setEntityMappingDetails(
			Map<String, CrudConfigParams> entityMappingDetails) {
		this.entityMappingDetails = entityMappingDetails;
	}

	public int getEntityHardDeleteAllowed() {
		return entityHardDeleteAllowed;
	}

	public void setEntityHardDeleteAllowed(int entityHardDeleteAllowed) {
		this.entityHardDeleteAllowed = entityHardDeleteAllowed;
	}

	public Long getCrudConfigId() {
		return crudConfigId;
	}

	public void setCrudConfigId(Long crudConfigId) {
		this.crudConfigId = crudConfigId;
	}

	public String getPrimaryKeyColumn() {
		return primaryKeyColumn;
	}

	public void setPrimaryKeyColumn(String primaryKeyColumn) {
		this.primaryKeyColumn = primaryKeyColumn;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public String getEntityHistoryName() {
		return entityHistoryName;
	}

	public void setEntityHistoryName(String entityHistoryName) {
		this.entityHistoryName = entityHistoryName;
	}

	@Override
	public String toString() {
		return "CrudConfig [crudConfigId=" + crudConfigId + ", entityName="
				+ entityName + ", primaryKeyColumn=" + primaryKeyColumn
				+ ", sequenceName=" + sequenceName + ", entityMappingDetails="
				+ entityMappingDetails + ", entityHardDeleteAllowed="
				+ entityHardDeleteAllowed + ", entityHistoryName="
				+ entityHistoryName + "]";
	}

	public String getEntityJsonMappingForPrimaryKey() {
		return entityJsonMappingForPrimaryKey;
	}

	public void setEntityJsonMappingForPrimaryKey(
			String entityJsonMappingForPrimaryKey) {
		this.entityJsonMappingForPrimaryKey = entityJsonMappingForPrimaryKey;
	}

}
