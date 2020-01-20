package com.family.tree.constant;

public enum Application {

	NAME("RFID-Collect"),
	SYSTEM_USER("system");
	
	String value;
	private Application(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
