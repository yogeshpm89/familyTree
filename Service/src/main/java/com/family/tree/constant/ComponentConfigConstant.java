package com.family.tree.constant;

public enum ComponentConfigConstant {

	FINAL_SQL("finalSql"), 
	SORT_INFORMATION("sortInformation"), 
	PAGINATION_INFORMATION("paginationInformation"), 
	FILTER_BINDINGS("filterBindings");

	String name;

	ComponentConfigConstant(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

