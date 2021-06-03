package com.systempro.services;

import org.springframework.mail.SimpleMailMessage;

import com.systempro.domain.Pedido;

public interface EmailService {
	
	public void sendOrderConfirmationEmail(Pedido obj);
	
	public void sendEmail(SimpleMailMessage msg);

}
