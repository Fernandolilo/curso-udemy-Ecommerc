package com.systempro.domain.enums;

public enum TipoCliente {

	//estamos dado codigos ao tipo enumerado
	PESSOAFISICA(1, "Pessoa Fisíca"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");

	private int cod;
	private String descricao;

	// atribui o cod e descrição criados para efetuar o tipo de cliente 
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;

	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	public static TipoCliente toEnum(Integer cod) {
		//proteção caso o cod seja nulo retorna nulo também
		if(cod == null) {
			return null;
		}
		//esta percorrendo o tipo cliente , TipoCliente.values são todos os codigos possiveis
		for(TipoCliente x: TipoCliente.values()) {
			//estamos testando se o cod for igual a x.getCod, retorna um codigo x seja ele possoa
			// fisíca ou jurídica.
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
		
	}

}
