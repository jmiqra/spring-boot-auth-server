package com.asraf.services.email;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageBuilder {

	private String emailTo;
	
	private String emailBody;
	
	private String emailSubject;

}
