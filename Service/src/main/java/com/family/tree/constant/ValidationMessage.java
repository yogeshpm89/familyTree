package com.family.tree.constant;

public enum ValidationMessage {

	USER_ALREADY_PRESENT("User already present");

	String name;

	private ValidationMessage(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
