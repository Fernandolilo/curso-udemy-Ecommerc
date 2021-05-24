package com.systempro.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systempro.domain.Categoria;
import com.systempro.repositories.CategoriaRepository;

@Service // class de serviço
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo; // importe do repositorio 
	
	// faz a busca por uma categoria atraves de um id
	public  Categoria find(Integer id) { 
		Optional<Categoria> obj = repo.findById(id);		
		return obj.orElse(null);
	}
}
