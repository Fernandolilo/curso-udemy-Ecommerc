package com.systempro.domain.enums;

public enum EstadoPagamento {

	//estamos dado codigos ao tipo enumerado
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");

	private int cod;
	private String descricao;

	// atribui o cod e descrição criados para efetuar o tipo de cliente 
	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;

	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	public static EstadoPagamento toEnum(Integer cod) {
		//proteção caso o cod seja nulo retorna nulo também
		if(cod == null) {
			return null;
		}
		//esta percorrendo o tipo cliente , TipoCliente.values são todos os codigos possiveis
		for(EstadoPagamento x: EstadoPagamento.values()) {
			//estamos testando se o cod for igual a x.getCod, retorna um codigo x seja ele possoa
			// fisíca ou jurídica.
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
		
	}

}
