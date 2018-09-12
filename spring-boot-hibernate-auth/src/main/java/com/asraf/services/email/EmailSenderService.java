package com.asraf.services.email;

import javax.mail.MessagingException;

public interface EmailSenderService {

	void sendText(MessageBuilder messageBuilder) throws MessagingException;

	void sendHtml(MessageBuilder messageBuilder) throws MessagingException;

//	void sendTextSystem(MessageBuilder messageBuilder);
//
//	void sendHtmlSystem(MessageBuilder messageBuilder);

}
