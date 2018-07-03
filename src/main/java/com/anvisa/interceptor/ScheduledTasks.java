package com.anvisa.interceptor;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.anvisa.model.persistence.ScheduledEmail;
import com.anvisa.repository.generic.RepositoryScheduledEmail;

//@Component
public class ScheduledTasks {

	@Autowired
	static RepositoryScheduledEmail repositoryScheduledEmail;

	@Autowired
	private static JavaMailSender mailSender;

	@Autowired
	public void setService(RepositoryScheduledEmail repositoryScheduledEmail, JavaMailSender mailSender) {
		this.repositoryScheduledEmail = repositoryScheduledEmail;
		this.mailSender = mailSender;
	}

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	//@Scheduled(fixedRate = 5000000)
	public static void scheduledEmail() {

		log.info("scheduledEmail ", dateFormat.format(new Date()));

		Collection<ScheduledEmail> emails = repositoryScheduledEmail.getAll(false);

		for (ScheduledEmail scheduledEmail : emails) {
			sendEmail(scheduledEmail);
		}

	}

	public static void sendEmail(ScheduledEmail scheduledEmail) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.prot", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("mabc.development@gmail.com", "@idkfa0101ID");
			}
		});

		MimeMessage mail = new MimeMessage(session);
		try {

			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setTo(scheduledEmail.getEmail()); //
			helper.setReplyTo("someone@localhost");
			helper.setFrom("mabc.developement@gmail.com");
			helper.setSubject(scheduledEmail.getSubject());
			helper.setText(scheduledEmail.getBody());

			mailSender.send(mail);

			scheduledEmail.setSent(true);

			repositoryScheduledEmail.saveAndFlush(scheduledEmail);

		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
		}

		// return helper;
	}
}