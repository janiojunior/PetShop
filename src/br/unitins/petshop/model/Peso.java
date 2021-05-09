package br.unitins.petshop.model;

public class Peso {

	private Integer id;
	private Double valor;
	private TipoPeso tipoPeso;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public TipoPeso getTipoPeso() {
		return tipoPeso;
	}

	public void setTipoPeso(TipoPeso tipoPeso) {
		this.tipoPeso = tipoPeso;
	}

}
