package com.systempro;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.systempro.domain.Categoria;
import com.systempro.domain.Cidade;
import com.systempro.domain.Cliente;
import com.systempro.domain.Endereco;
import com.systempro.domain.Estado;
import com.systempro.domain.ItemPedido;
import com.systempro.domain.Pagamento;
import com.systempro.domain.PagamentoComBoleto;
import com.systempro.domain.PagamentoComCartao;
import com.systempro.domain.Pedido;
import com.systempro.domain.Produto;
import com.systempro.domain.enums.EstadoPagamento;
import com.systempro.domain.enums.TipoCliente;
import com.systempro.repositories.CategoriaRepository;
import com.systempro.repositories.CidadeRepository;
import com.systempro.repositories.ClienteRepository;
import com.systempro.repositories.EnderecoRepository;
import com.systempro.repositories.EstadoRepository;
import com.systempro.repositories.ItemPedidoRepository;
import com.systempro.repositories.PagamentoRepository;
import com.systempro.repositories.PedidoRepository;
import com.systempro.repositories.ProdutoRepository;

@SpringBootApplication
public class ECommercApplication implements CommandLineRunner{

	
	@Autowired
	private CategoriaRepository categoriarepository;
	
	@Autowired
	private ProdutoRepository produtoReposytory;
	
	@Autowired
	private EstadoRepository estadoReposytory;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	
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
		
		Estado est1 = new Estado(null, "São Paulo");
		Estado est2 = new Estado(null, "Minas Gerais");
		
		Cidade c1 = new Cidade(null, "São Paulo", est1);
		Cidade c2 = new Cidade(null, "Campinas", est1);
		Cidade c3 = new Cidade(null, "Uberandia", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1, c2));
		est2.getCidades().addAll(Arrays.asList(c3));
		
		estadoReposytory.saveAll(Arrays.asList(est1 , est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Fernando da Silva", "fernando.nandotania@hotmail.com", "36906855832", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("91234-1234", "4545-4000"));
		
		Endereco e1 = new Endereco(null, "Rua Silva Tele", "13", "Apto 15 bloco 3","Vila formosa", "01234-5", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Ruth Cardoso", "251", "","Vila formosa", "01234-5", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("25/05/2021 18:47"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("26/05/2021 20:47"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("30/05/2021 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));		
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 1, 2000.00, 0.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 2, 80.00, 0.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 1, 800.00, 100.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}

}
