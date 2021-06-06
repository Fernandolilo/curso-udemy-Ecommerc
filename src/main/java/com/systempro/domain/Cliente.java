package com.systempro.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.systempro.domain.enums.Perfil;
import com.systempro.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;

	@Column(unique = true)
	private String email;
	private String cpfOuCnpj;
	private Integer tipo;

	@JsonIgnore
	private String senha;

	// relacionamento muitos para um, por tanto deste lado estará o @OneToMany
	// sendo um unico cliente para muitos muitos endereços para.
	// o nome do campo que correponde a endereço @OneToMany(mappedBy = "cliente")
	// sendo assim a coluna cliente_id estará mostrando qual o cliente é em
	// endereço.
	// relacioando assim o cliente ao endereço.
	// @JsonManagedReference proteção para serialização ciclica, estou dizendo que
	// o cliente pode buscar os esndereços
	// cascade = CascadeType.ALL esta anotação, serve pra refletir em cascata tudo o
	// que ocorrer no cliente

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();

	// Set é um conjunto e não aceita repetição, etão para este conjunto de Strings
	// desta manteira garante que não haverá repetição.
	// no caso estamos criando uma entidade fraca, não estará acrecida de ID, pois
	// não haverá necessidade.
	// @ElementCollection cria a tabela
	// @CollectionTable(name =" TELEFONE") //nomeaia a tabela
	// NESTE CASO IRÁ CRIAR O CLIENTE_ID EM TELEFONE

	@ElementCollection
	@CollectionTable(name = " TELEFONE")
	private Set<String> telefones = new HashSet<>();

	// estamos fazendo a proteção de serialização ciclica, @JsonBackReference
	// o clinte não irar serializar pedido, apenas os pedidos buscará seus cliente.

	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos = new ArrayList<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	


	public Cliente() {
		addPerfil(Perfil.CLIENTE);
	}

	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = (tipo == null) ? null : tipo.getCod();
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Perfil> getPerfis(){
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}

}
