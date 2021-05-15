package br.unitins.petshop.model;

public enum TipoPeso {
	KG(1, "KG"), 
	GRAMA(2, "Grama"), 
	LIBRA(3, "Libra"), 
	MILIGRAMA(4, "Miligrama");
	
	private int value;
	private String label;
	
	TipoPeso (int value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static TipoPeso valueOf(Integer value) {
		if (value == null)
			return null;
		
		for (TipoPeso tipo : TipoPeso.values()) {
			if (tipo.getValue() == value) {
				return tipo;
			}
		}
		return null;
	}
}
