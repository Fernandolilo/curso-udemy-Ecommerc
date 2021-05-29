package com.systempro.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systempro.domain.Cliente;
import com.systempro.domain.dto.ClienteDTO;
import com.systempro.services.ClienteService;

@RestController
@RequestMapping(value ="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find (@PathVariable Integer id){
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	// pathvariable é um tratamento para informa que a busca esta sendo feira por
	// meio do ID.
	public ResponseEntity<List<ClienteDTO>> findAll() {
		// pega a lista de categorias
		List<Cliente> list = service.findAll();
		// converte a lista de categorias para um ClienteDTO
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		// retorna apenas dados nocessários.
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value= "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="direction", defaultValue="ASC")String direction,
			@RequestParam(value ="orderBy", defaultValue="nome")String orderBy) {
		// pega a lista de categorias
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction );
		// converte a lista de categorias para um ClienteDTO
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		// retorna apenas dados nocessários.
		return ResponseEntity.ok().body(listDto);
	}

}
