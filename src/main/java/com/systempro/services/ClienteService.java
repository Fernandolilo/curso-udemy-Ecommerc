package com.systempro.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.systempro.domain.Cliente;
import com.systempro.domain.dto.ClienteDTO;
import com.systempro.repositories.ClienteRepository;
import com.systempro.services.exceptions.DataIntegrityException;
import com.systempro.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado!: Id: " + "Tipo: " + Cliente.class.getName()));
	}

	public Cliente update(Cliente obj) {
		//estamos buscando todos os dados do cliente,
		Cliente newObj =find(obj.getId());
		//feito um tratamento de dados a baixo em updateDatam setando novo nome e novo email.
		updateData(newObj, obj);
		//salva novos campos.
		return repo.save(newObj);
	}
	//metodo criado para atualizar campos nome e email do cliente, que é a unica coisa que permitimos atualizar.
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos.");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	// para fazer um POST em ClienteResource
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(),null, null);
	}

}
