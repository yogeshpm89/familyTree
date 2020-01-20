package com.family.tree.service;

public interface EmailService {
	public boolean sendMail(String[] to, String subject, String body) throws Exception;
}
