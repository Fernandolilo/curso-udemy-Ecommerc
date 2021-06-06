package com.systempro.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.systempro.domain.Cidade;
import com.systempro.domain.Cliente;
import com.systempro.domain.Endereco;
import com.systempro.domain.dto.ClienteDTO;
import com.systempro.domain.dto.ClienteNewDTO;
import com.systempro.domain.enums.Perfil;
import com.systempro.domain.enums.TipoCliente;
import com.systempro.repositories.ClienteRepository;
import com.systempro.repositories.EnderecoRepository;
import com.systempro.security.UserSS;
import com.systempro.services.exceptions.AuthorizationException;
import com.systempro.services.exceptions.DataIntegrityException;
import com.systempro.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private S3Service s3Service;
	
	

	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado!: Id: " + "Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert (Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(),null, null, null);
	}
	
	//metodo serve para fazer um insert.
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),TipoCliente.toEnum(objDto.getTipo()), bCryptPasswordEncoder.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() !=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() !=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	public URI uploadProfilePicture(MultipartFile multipartFile)  {
		UserSS user = UserService.authenticated();
		if(user ==null) {
			throw new AuthorizationException("Acesso negado");
		}		
		URI uri = s3Service.uploadFile(multipartFile);
		Optional<Cliente> cli = repo.findById(user.getId());
		cli.get().setImageUri(uri.toString());
		return uri;

	}
}
