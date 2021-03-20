package br.unitins.petshop.model;

public enum Perfil {
	ADMINISTRADOR (1, "Adminstrador"), 
	CLIENTE (2, "Cliente"), 
	FUNCIONARIO (3, "Funcionario");
	
	private int value;
	private String label;
	
	Perfil (int value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getLabel() {
		return label;
	}

}
