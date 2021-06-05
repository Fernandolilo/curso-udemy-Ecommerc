package com.systempro.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.systempro.domain.Cliente;
import com.systempro.domain.Pedido;

public interface EmailService {
	
	public void sendOrderConfirmationEmail(Pedido obj);
	
	public void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);

	void sendNewPasswordEmail(Cliente cliente, String newPass);

}
