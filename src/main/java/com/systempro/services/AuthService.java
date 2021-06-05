package com.systempro.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.systempro.domain.Cliente;
import com.systempro.repositories.ClienteRepository;
import com.systempro.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand= new Random();
	
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		String newPass = newPassword();
		cliente.setSenha(bCryptPasswordEncoder.encode(newPass));
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char [] vet = new char[10];
		for(int i =0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String (vet);
	}

	//metodo randomiza uma nova senha 
	//https://unicode-table.com/pt/
	//os digitos na tabela inicia com algarismo que esta setado  +48, +65 +97
	private char randomChar() {
		int opt = rand.nextInt(3);
		if(opt == 0){//gera digito
			return (char) (rand.nextInt(10) +48);
		}
		else if(opt ==1){//gera letra maiuscula inicia no cod 65
			return (char) (rand.nextInt(26) +65);
		}
		else {//gera letra menuscula inicia no cod 97
			return (char) (rand.nextInt(26) +97);
		}
		
	}

}
