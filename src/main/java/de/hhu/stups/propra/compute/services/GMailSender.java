package de.hhu.stups.propra.compute.services;

import de.hhu.stups.propra.compute.oldPJ.Credentials;
import de.hhu.stups.propra.compute.oldPJ.MailService;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class GMailSender implements MailService {

	private Properties props = new Properties();
	private Credentials credentials;

	public GMailSender(Credentials credentials) {
		this.credentials = credentials;
		configureMailService();
	}

	private void configureMailService() {
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.user", credentials.getMailUser());
		props.put("mail.password", credentials.getMailPassword());
	}

	@Override
	public void sendMail(String kunde) {
		Session session = createSession();
		try {
			Message message = createMessage(kunde, session);
			Transport.send(message);
			System.out.println("Mail sent succesfully!");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private Message createMessage(String kunde, Session session) throws MessagingException, AddressException {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(credentials.getMailUser()));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(kunde));
		message.setSubject("Endlich Eiszeit");
		message.setText("Hallo,\nkomm doch auf ein frisches Eis vorbei!");
		return message;
	}

	private Session createSession() {
		return Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				String username = props.getProperty("mail.user");
				String password = props.getProperty("mail.password");
				return new PasswordAuthentication(username, password);
			}
		});
	}

}
