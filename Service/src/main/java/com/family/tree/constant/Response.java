package com.family.tree.constant;

public enum Response {

	STATUS("status"),
	REASON("reason");

	String name;

	private Response(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
