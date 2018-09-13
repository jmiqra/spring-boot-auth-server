package com.asraf.services.email.persistence;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.services.email.EmailSenderService;
import com.asraf.services.email.MessageBuilder;

@Service
@Transactional
public class EmailSenderServiceImpl implements EmailSenderService {

	
	private JavaMailSender sender;
	
	@Autowired
	public EmailSenderServiceImpl(JavaMailSender sender) {
		this.sender = sender;
	}

	public void sendText(MessageBuilder messageBuilder) throws MessagingException {
		
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		helper.setTo(messageBuilder.getEmailTo());
		helper.setText(messageBuilder.getEmailBody(), false);
		helper.setSubject(messageBuilder.getEmailSubject());

		sender.send(message);
	}

	public void sendHtml(MessageBuilder messageBuilder) throws MessagingException {
		
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		try {
			List<InternetAddress> tos = new ArrayList<InternetAddress>();
			InternetAddress to = new InternetAddress(messageBuilder.getEmailTo(), "Iqra");
			tos.add(to);
			helper.setTo(tos.toArray(new InternetAddress[tos.size()]));
			
			InternetAddress from = new InternetAddress("iqrah@dginnovationlab.com", "Jurdana");
			helper.setFrom(from);
			
			InternetAddress replyTo = new InternetAddress("ratul840@yahoo.com", "Asraf");
			helper.setReplyTo(replyTo);
			
			helper.setText(messageBuilder.getEmailBody(), true);
			helper.setSubject(messageBuilder.getEmailSubject());
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ClassPathResource file = new ClassPathResource("images/logo.png");
		helper.addInline("id101", file);

		sender.send(message);
	}

}
