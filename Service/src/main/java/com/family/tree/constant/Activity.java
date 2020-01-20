package com.family.tree.constant;

public enum Activity {

	ADD_USER("Add user"),
	UPDATE_USER("Update user"),
	UPDATE_USER_PASSWORD("Update user password"),

	ADD_IMAGE_IMAGE_DETAILS("Add image details");
	
	String name;

	private Activity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
