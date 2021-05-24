package com.systempro;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.systempro.domain.Categoria;
import com.systempro.repositories.CategoriaRepository;

@SpringBootApplication
public class ECommercApplication implements CommandLineRunner{

	
	@Autowired
	CategoriaRepository categoriarepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ECommercApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");		
		categoriarepository.saveAll(Arrays.asList(cat1, cat2));
		
	}

}
