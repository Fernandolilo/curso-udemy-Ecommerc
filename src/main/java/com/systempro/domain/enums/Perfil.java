package com.systempro.domain.enums;

public enum Perfil {

	//estamos dado codigos ao tipo enumerado
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");

	private int cod;
	private String descricao;

	// atribui o cod e descrição criados para efetuar o tipo de cliente 
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;

	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	public static Perfil toEnum(Integer cod) {
		//proteção caso o cod seja nulo retorna nulo também
		if(cod == null) {
			return null;
		}
		//esta percorrendo o tipo cliente , TipoCliente.values são todos os codigos possiveis
		for(Perfil x: Perfil.values()) {
			//estamos testando se o cod for igual a x.getCod, retorna um codigo x seja ele possoa
			// fisíca ou jurídica.
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
		
	}

}
