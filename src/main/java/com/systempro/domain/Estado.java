package com.systempro.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;

	// mapeamento muitos para um no caso estamos mapeando estados e cidades
	// como um estado tem muitas cidades, dentro da class Estado chamamos uma lista
	// de cidades
	// criando assim muitas cidades para um estado =>private List<Cidade> cidades =
	// new ArrayList<>();
	// @OneToMany(mappedBy = "estado") como na class cidade foi mapeado muitos para
	// um, aqui esta sendo feito
	// um mapeamento reverso, en cidade mapeamos no atributo etado =>private Estado
	// estado; por isto (mappedBy = "estado")
	// @JsonManagedReference -> proteção contra serialização ciclica,
	// estou dizendo para o estado que não pode serializar a cidade.
	// neste caso estamos mudando a proteçao de serialização ciclica para o
	// JsonIgnore, ele fará com que o atributo private List<Cidade> cidades = new
	// ArrayList<>(); seja ignorado na hora de buscar estados. efetuando assim a
	// busca de estados e suas cidades e não cidades e seus estados

	@JsonIgnore
	@OneToMany(mappedBy = "estado")
	private List<Cidade> cidades = new ArrayList<>();

	public Estado() {
	}

	// no contrutor com parametro não pode ser posto listas por este motivo
	// private List<Cidade> cidades = new ArrayList<>(); não entra no construtor.

	public Estado(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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
		Estado other = (Estado) obj;
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

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

}
