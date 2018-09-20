package com.asraf.templates;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.asraf.entities.User;

@Component
public class ChangePasswordTemplate {

	private SpringTemplateEngine templateEngine;

	@Autowired
	public ChangePasswordTemplate(SpringTemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public String createTemplate(User user, String link) throws MessagingException {

		String name = user.getUsername();
		Context context = new Context();
		context.setVariable("message", "Enjoy Thymeleaf");
		context.setVariable("name", name);
		context.setVariable("link", link);
		String text = templateEngine.process("ChangePassword.html", context);

		return text;
	}

}
