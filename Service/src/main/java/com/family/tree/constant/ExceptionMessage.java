package com.family.tree.constant;

public enum ExceptionMessage {
	
	ADD_USER("Add user exception"),
	UPDATE_USER("Update user exception"),
	FORGOT_PASSWORD("Forgot password exception");

	String name;

	private ExceptionMessage(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
