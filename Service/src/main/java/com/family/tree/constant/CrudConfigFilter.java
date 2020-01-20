package com.family.tree.constant;

public enum CrudConfigFilter {

	FILTER("filter"),
	FILTER_COMPONENTS("filterComponents"),
	TYPE("filterType"),
	KEY("filterKey"),
	VALUE("filterValue"),
	DATE_RANGE("filterTypeDateRange"),
	START_DATE("filterStartDate"),
	END_DATE("filterEndDate"),
	IS_EXACT("isExact"),
	IS_NOT("isNot"),
	IS_NULL("isNull"),
	LIST("list"),
	DOUBLE("double"),
	NUMBER("number"),
	DATE("date"),
	CHARS("chars"),
	PAGE_NO("pageNo"),
	PAGE_SIZE("pageSize"),
	ORDER_BY("orderBy"),
	ORDER_TYPE("orderType");
	
	String name;

	CrudConfigFilter(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
