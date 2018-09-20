package com.asraf.services.email;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageBuilder {

	@Builder.Default
	private List<InternetAddress> emailTos = new ArrayList<>();

	private InternetAddress emailFrom;

	private InternetAddress emailReplyTo;

	private String emailBody;

	private String emailSubject;
	
	private boolean isHtml;
	
	public MessageBuilder addEmailTo(String emailAddress, String name) throws UnsupportedEncodingException {
		this.emailTos.add(new InternetAddress(emailAddress, name));
		return this;
	}

	public InternetAddress[] getEmailTos() {
		return emailTos.toArray(new InternetAddress[emailTos.size()]);
	}

}
