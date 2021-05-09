package br.unitins.petshop.model;

public class Produto {

	private Integer id;
	private String nome;
	// definir 3 novos atributos

	private Peso peso;

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

	public Peso getPeso() {
		return peso;
	}

	public void setPeso(Peso peso) {
		this.peso = peso;
	}

}
