package com.family.tree.constant;

import java.util.HashMap;
import java.util.Map;

public class RFIDCollectMessages {

	public final static String MISSING_FIELDS = "Required information missing.";
	public final static String USER_EXISTS = "This email is already registered, try Reset Password instead.";
	public final static String USER_ROLE_MISSING_FIELDS = "Missing Fields to be added for user roles.";
	public final static String USER_NOT_EXISTS = "User not found.";

	public final static String MISSING_ANSWERS = "Required information missing";
	public final static String MISSING_ANSWERS_COUNT = "Please answer (###count###) mandatory questions.";


	public static final Map<String, String> ENGLISH = new HashMap<String, String>();

	static {
		// General errors
		ENGLISH.put("SERVER_ERROR", "Please refresh the browser window and then contact the administrator in case the problem persist.");
		ENGLISH.put("MISSING_FIELDS", "Required information missing.");
		
		// login and forgot password page
		ENGLISH.put("EMAIL", "Email");
		ENGLISH.put("PASSWORD", "Password");
		ENGLISH.put("CONFIRM_PASSWORD", "Confirm Password");
		//ENGLISH.put("FORGOT_PASSWORD", "Forgot password");
		ENGLISH.put("FORGOT_PASSWORD", "Reset password");
		ENGLISH.put("LOGIN", "Login");
		ENGLISH.put("RESET", "reset");
		ENGLISH.put("USERNAME", "Username");
		ENGLISH.put("SUBMIT", "Submit");
		ENGLISH.put("CANCEL", "Cancel");
		ENGLISH.put("LOGIN_ERROR", "Login Failed!!!");
		ENGLISH.put("RESET_PASSWORD_EMAIL", "An email has been sent with instructions to reset your password.");
		ENGLISH.put("INVALID_USERNAME", "Invalid username or password.");
		ENGLISH.put("INVALID_PASSWORD", "Invalid username or password.");
		ENGLISH.put("INVALID_EMAIL", "Invalid email address.");
		ENGLISH.put("INVALID_USERNAME_PASSWORD", "Invalid username or password.");
		ENGLISH.put("PASSWORD_CHANGE_SUCCESS", "Password changed.");
		ENGLISH.put("LOGOUT_ERROR", "Error in logout");
		ENGLISH.put("USER_EXISTS", "This email is already registered, try Reset Password instead.");
		ENGLISH.put("USER_NOT_EXISTS", "User does not exist.");
		
		ENGLISH.put("USER_FOUND", "User found");
		ENGLISH.put("USER_NOT_FOUND", "Error!!! User not found");
		
		ENGLISH.put("USER_ROLE_MISSING_FIELDS", "Missing Fields to be added for user roles.");
		
		ENGLISH.put("USER_REGISTRATION_MAIL_SUBJECT", "Proposal automation account registration");
		ENGLISH.put("USER_REGISTRATION_MAIL_BODY",
				"###USER_NAME###"
						+ "<br><br><br>Please <a href=\"###SERVER_URL###/resetp/abc;source=email;username=###email###\">click here</a> to confirm and complete your registration.");
		
		ENGLISH.put("USER_FORGOT_PASSWORD_MAIL_SUBJECT", "Proposal automation password reset");
		ENGLISH.put("USER_FORGOT_PASSWORD_MAIL_BODY", "Please click on the following link to reset your password <a href=\"###SERVER_URL###/resetp/abc;source=email;username=###username###\">reset password</a>");
		ENGLISH.put("MAIL_SENT", "Sent, please check your email.");
		ENGLISH.put("MAIL_SENT_ERROR", "Error!!! Email has not been sent.");
		ENGLISH.put("RESEND_REGISTRATION_MAIL", "Resend registration mail");
		
		// Proposal automation details
		ENGLISH.put("SOW_USER_ROLE", "Role");
		ENGLISH.put("USER_EXISTS", "Already added this user.");
		ENGLISH.put("MANDATORY_FIELDS_MISSING", "Mandatory fields missing, please fill in all Mandatory fields");
		ENGLISH.put("INVALID_FORM", "Mandatory fields missing or value is incorrect, please check form");
		ENGLISH.put("MISSING_ANSWERS", "Required information missing");
		ENGLISH.put("MISSING_ANSWERS_COUNT", "Please answer (###count###) mandatory questions.");
		
		// Proposal automation submit
		ENGLISH.put("CHECKER_EXISTS", "Already submitted to this user.");
		
		ENGLISH.put("SUBMIT_TO_USER", "Approver Email");
		ENGLISH.put("SUBMIT_COMMENTS", "Comments");
		ENGLISH.put("ADD", "Add");
		ENGLISH.put("APPROVE_COMMENTS", "Approval Comments");
		ENGLISH.put("REJECT_COMMENTS", "Rejection Comments");
		ENGLISH.put("COMMENTS", "Comments");
		
		ENGLISH.put("MAKER_USER", "Requested by");
		ENGLISH.put("MAKER_COMMENTS", "Comments");
		ENGLISH.put("CHECKER_USER", "Approver");
		ENGLISH.put("CHECKER_COMMENTS", "Approver Comments");
		ENGLISH.put("DUE_DATE", "Due Date");
		ENGLISH.put("STATUS", "Status");
		
		ENGLISH.put("STATUS_SAVED", "Saved");
		ENGLISH.put("STATUS_REJECTED", "Rejected");
		ENGLISH.put("RESUBMIT", "Resubmit");
		ENGLISH.put("WITHDRAW", "Withdraw");
		ENGLISH.put("WITHDRAWN", "Withdrawn");
		ENGLISH.put("STATUS_APPROVED", "Approved");
		ENGLISH.put("STATUS_SUBMITTED", "Submitted");
		ENGLISH.put("STATUS_RESUBMITTED", "Resubmitted");
		//ENGLISH.put("NO_USER_SELECTED", "No user selected for approval");
		ENGLISH.put("NO_USER_SELECTED", "No participants");

		// User screen
		ENGLISH.put("USER_SAVE_SUCCESS_MESSAGE", "User saved.");
		ENGLISH.put("USER_SAVE_ERROR_MESSAGE", "User save failed.");
		ENGLISH.put("USER_UPDATE_SUCCESS_MESSAGE", "User updated.");
		ENGLISH.put("USER_UPDATE_ERROR_MESSAGE", "User update failed.");
		ENGLISH.put("USER_DEACTIVATE_SUCCESS_MESSAGE", "User deactivated.");
		ENGLISH.put("USER_DEACTIVATE_ERROR_MESSAGE", "User deactivation failed.");
		ENGLISH.put("USER_ACTIVATE_SUCCESS_MESSAGE", "User activated.");
		ENGLISH.put("USER_ACTIVATE_ERROR_MESSAGE", "User activation failed.");
		
	}

	public static final String APP_LOCALE = "ENGLISH";
	public static final String LOCALE_ENGLISH = "ENGLISH";
	
	public static  Map<String, String> getMessagesForLocale(String locale) {
		
		if (locale.equals(LOCALE_ENGLISH)) {
			return ENGLISH;
		}
		
		return null;
	}
	
	public static String getMessages(String key) {
		
		if (APP_LOCALE.equals(LOCALE_ENGLISH)) {
			return ENGLISH.get(key);
		}
		
		return null;
	}
	

}
