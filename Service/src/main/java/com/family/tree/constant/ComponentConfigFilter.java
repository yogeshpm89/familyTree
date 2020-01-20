package com.family.tree.constant;

public enum ComponentConfigFilter {

	FILTER("FILTER"),
	FILTER_COMPONENTS("FILTER_COMPONENTS"),
	TYPE("FILTER_TYPE"),
	KEY("FILTER_KEY"),
	VALUE("FILTER_VALUE"),
	DATE_RANGE("DATE_RANGE"),
	START_DATE("START_DATE"),
	END_DATE("END_DATE"),
	IS_EXACT("isExact"),
	IS_NOT("IS_NOT"),
	IS_NULL("IS_NULL"),
	LIST("LIST"),
	DOUBLE("DOUBLE"),
	NUMBER("NUMBER"),
	DATE("DATE"),
	CHARS("CHARS"),
	PAGE_NO("PAGE_NO"),
	PAGE_SIZE("PAGE_SIZE"),
	ORDER_BY("ORDER_BY"),
	ORDER_TYPE("ORDER_TYPE");
	
	String name;

	ComponentConfigFilter(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
