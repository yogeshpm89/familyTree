package com.family.tree.constant;

public enum EmailBody {

	USER_REGISTRATION_MAIL_SUBJECT
	(Application.NAME + " account registration"),
	USER_REGISTRATION_MAIL_BODY
	("Congratulations on your account registration with " + Application.NAME + ". <br>Please click on the following link to complete your registration <a href=\"###SERVER_URL###resetp/abc/\">Register</a>"),
	USER_FORGOT_PASSWORD_MAIL_SUBJECT
	(Application.NAME + " password reset"),
	USER_FORGOT_PASSWORD_MAIL_BODY
	("Please click on the following link to reset your password <a href=\"###SERVER_URL###/resetp/abc;source=email;username=###username###\">Reset Password</a>"),
	MAIL_SENT("Email has been sent, please check your inbox."),
	MAIL_FROM("do-not-reply"),
	MAIL_SENT_ERROR("Error!!! Email has not been sent.");

	String body;
	
	private EmailBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
