package com.family.tree.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.family.tree.constant.EmailBody;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private AppService appService;
	
	@Override
	public boolean sendMail(String[] to, String subject, String body) throws Exception {
		EmailThread emailThread = new EmailThread(to, subject, body);
		Thread t = new Thread(emailThread);
		t.start();
		return true;
	}
	
	
	private class EmailThread implements Runnable {
		
		private final Logger logger = LogManager.getLogger("EmailThread");

		Map<String, String> mailSettings;
		String[] to;
		String subject;
		String body;
		
		public EmailThread() {
			
		}
		
		public EmailThread(String[] to, String subject, String body) throws Exception {
			this.mailSettings = appService.getMailSettings();
			this.to = to;
			this.subject = subject;
			this.body = body;
		}
		
		
		@Override
		public void run() {
			try {
				logger.info("run start");
				sendFromGMail(mailSettings, to, subject, body);
				
			} catch (Exception e) {
				logger.error("run, Exception = " + e.getLocalizedMessage());
				e.printStackTrace();
			}
			logger.info("run end");
		}
		
		
		private boolean sendFromGMail(Map<String, String> mailSettings, String[] to, String subject, String body) throws Exception {
			
			logger.info("sendFromGMail start, to = " + to + ", subejct = " + subject + ", body = " + body);
			Properties props = System.getProperties();
			
			String from = mailSettings.get("APP_MAIL_SMTP_USER");
			
			String host = "smtp.gmail.com";
			if (mailSettings.containsKey(("APP_MAIL_SMTP_HOST"))) {
				host = mailSettings.get("APP_MAIL_SMTP_HOST");
			}
			
			
			String pass = mailSettings.get("APP_MAIL_SMTP_PASSWORD");
			props.put("mail.smtp.starttls.enable", mailSettings.get("APP_MAIL_SMTP_STARTTLS_ENABLE"));
			props.put("mail.smtp.host", mailSettings.get("APP_MAIL_SMTP_HOST"));
			props.put("mail.smtp.auth", mailSettings.get("APP_MAIL_SMTP_AUTH"));
			
			boolean isAuth = Boolean.valueOf(mailSettings.get("APP_MAIL_SMTP_AUTH").toString());
			if (isAuth) {
				props.put("mail.smtp.user", from);
				props.put("mail.smtp.password", pass);
				props.put("mail.smtp.port", mailSettings.get("APP_MAIL_SMTP_PORT"));
			}
			
			Session session = Session.getDefaultInstance(props);
			MimeMessage message = new MimeMessage(session);

			try {
				if (from == null || "".equalsIgnoreCase(from)) {
					from = EmailBody.MAIL_FROM.getBody();
				}
				InternetAddress fromAddress = new InternetAddress(from);
				fromAddress.setPersonal(EmailBody.MAIL_FROM.getBody());
				message.setFrom(fromAddress);
				InternetAddress[] toAddress = new InternetAddress[to.length];

				// To get the array of addresses
				for (int i = 0; i < to.length; i++) {
					toAddress[i] = new InternetAddress(to[i]);
				}

				for (int i = 0; i < toAddress.length; i++) {
					message.addRecipient(Message.RecipientType.TO, toAddress[i]);
				}

				message.setSubject(subject);
				
				Map<String, String>  appURLMap = appService.getApplicationURL();
				body = body.replaceAll("###SERVER_URL###", appURLMap.get("APP_URL"));
				// body = body + MopGenericConstants.MOP_MAIL_SIGN;
				
				message.setContent(body, "text/html; charset=utf-8");
				Transport transport = session.getTransport("smtp");
				transport.connect(host, from, pass);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			} catch (AddressException ae) {
				logger.error("sendFromGMail, Exception = " + ae.getLocalizedMessage());
				ae.printStackTrace();
			} catch (MessagingException me) {
				logger.error("sendFromGMail, Exception = " + me.getLocalizedMessage());
				me.printStackTrace();
			}
			
			logger.info("sendFromGMail end");
			return true;
		}

	}
	


}
