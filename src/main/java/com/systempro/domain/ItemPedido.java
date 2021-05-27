package com.systempro.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	// quando é feito um atributo de uma outra class em JPA é preciso ir na clas e
	// colocar a anotação
	// @Embedable para dizer que esta class é um subtipo.
	// @EmbeddedId é uma chave composta por isto se faz uso desta anotação por ser
	// um identificador embutido
	// @JsonIgnore tratamento de serialização ciclica, neste caso estamos utilizado
	// o JsonIgnore pois seu papel e fazer ser ignorado na hora da busca por pedido
	// ou produto

	@JsonIgnore
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();

	private Double preco;
	private Integer quantidade;
	private Double desconto;

	public ItemPedido() {
	}

	public ItemPedido(Pedido pedido, Produto produto, Integer quantidade, Double preco, Double desconto) {
		super();
		id.setPedido(pedido);
		id.setProduto(produto);
		this.quantidade = quantidade;
		this.preco = preco;
		this.desconto = desconto;
	}

	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}


	public Produto getProduto() {
		return id.getProduto();
	}

	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
