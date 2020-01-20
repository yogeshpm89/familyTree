package com.family.tree.constant;

public enum CrudConfigConstant {
	GENERATED_ID_JSON_MAPPING("GeneratedIDJsonMapping"), VERSION("version"), GENERATED_ID("generatedId"), STATUS(
			"status"),

	CREATED_BY("createdBy"), CREATED_DATE("createdDate"), MODIFIED_BY("modifiedBy"), MODIFIED_DATE(
			"modifiedDate"), IS_ACTIVE("isActive"), ACTIVITY_ID("activityId"),
	 ACTIVITY_NAME("activityName"),
	 HIST_ATTR_BEGIN_DATE("beginDate"),
		HIST_ATTR_END_DATE("endDate"),
		ENTITY_ATTR_TYPE_NUMBER("NUMBER"),
		ENTITY_ATTR_TYPE_STRING("STRING"), 
		ENTITY_ATTR_TYPE_DATE("DATE"),
		ENTITY_ATTR_TYPE_DOUBLE("DOUBLE");

	String name;

	CrudConfigConstant(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
