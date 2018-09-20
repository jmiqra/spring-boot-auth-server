package com.asraf.services.email.persistence;

import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
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
	private MimeMessage mimeMessage;
	private MimeMessageHelper mimeMessageHelper;

	@Autowired
	public EmailSenderServiceImpl(JavaMailSender sender) throws MessagingException {
		this.sender = sender;

		mimeMessage = sender.createMimeMessage();
		mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

	}

	public EmailSenderService buildEmailSender(MessageBuilder messageBuilder) throws MessagingException {
		mimeMessageHelper.setTo(messageBuilder.getEmailTos());
		mimeMessageHelper.setReplyTo(messageBuilder.getEmailReplyTo());
		mimeMessageHelper.setText(messageBuilder.getEmailBody(), messageBuilder.isHtml());
		mimeMessageHelper.setSubject(messageBuilder.getEmailSubject());
		if (messageBuilder.getEmailFrom() != null) {
			mimeMessageHelper.setFrom(messageBuilder.getEmailFrom());
		}
		return this;
	}

	public EmailSenderService addInline(String contentId, File file) throws MessagingException {
		mimeMessageHelper.addInline(contentId, file);
		return this;
	}

	public EmailSenderService addInline(String contentId, ClassPathResource resource) throws MessagingException {
		mimeMessageHelper.addInline(contentId, resource);
		return this;
	}

	public EmailSenderService addAttachment(String attachmentFilename, File file) throws MessagingException {
		mimeMessageHelper.addAttachment(attachmentFilename, file);
		return this;
	}

	public void send() {
		sender.send(mimeMessage);
	}

}
