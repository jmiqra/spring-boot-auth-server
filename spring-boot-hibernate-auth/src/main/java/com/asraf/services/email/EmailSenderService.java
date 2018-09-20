package com.asraf.services.email;

import java.io.File;

import javax.mail.MessagingException;

import org.springframework.core.io.ClassPathResource;

public interface EmailSenderService {

	EmailSenderService buildEmailSender(MessageBuilder messageBuilder) throws MessagingException;

	EmailSenderService addInline(String contentId, File file) throws MessagingException;

	EmailSenderService addInline(String contentId, ClassPathResource resource) throws MessagingException;
	
	EmailSenderService addAttachment(String attachmentFilename, File file) throws MessagingException;

	void send();

}
