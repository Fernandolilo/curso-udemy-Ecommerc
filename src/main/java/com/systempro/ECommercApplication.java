package com.systempro;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.systempro.domain.Categoria;
import com.systempro.domain.Produto;
import com.systempro.repositories.CategoriaRepository;
import com.systempro.repositories.ProdutoRepository;

@SpringBootApplication
public class ECommercApplication implements CommandLineRunner{

	
	@Autowired
	private CategoriaRepository categoriarepository;
	
	@Autowired
	private ProdutoRepository produtoReposytory;
	
	public static void main(String[] args) {
		SpringApplication.run(ECommercApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");	
		
		Produto p1 = new Produto(null, "Computado", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriarepository.saveAll(Arrays.asList(cat1, cat2));
		produtoReposytory.saveAll(Arrays.asList(p1, p2, p3));
		
		
	}

}
