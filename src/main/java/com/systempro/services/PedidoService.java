package com.systempro.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systempro.domain.Pedido;
import com.systempro.repositories.PedidoRepository;
import com.systempro.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired PedidoRepository repo;
	
	public Pedido find (Integer id) {
		Optional<Pedido> obj =  repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! : Id: " + "Tipo: " + Pedido.class.getName()));
	}
	
}
