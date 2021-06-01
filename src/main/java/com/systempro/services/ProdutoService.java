package com.systempro.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.systempro.domain.Categoria;
import com.systempro.domain.Produto;
import com.systempro.repositories.CategoriaRepository;
import com.systempro.repositories.ProdutoRepository;
import com.systempro.services.exceptions.ObjectNotFoundException;

@Service // class de serviço
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo; // importe do repositorio
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	// faz a busca por uma categoria atraves de um id
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! ID: " + ", Tipo: " + Produto.class.getName()));
	}

	public Page <Produto> search (String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction ){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
