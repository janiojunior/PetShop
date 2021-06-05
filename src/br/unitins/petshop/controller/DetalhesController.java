package br.unitins.petshop.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.model.Venda;

@Named(value = "detalheVendaController")
@ViewScoped
public class DetalhesController implements Serializable{

	private static final long serialVersionUID = -477353260413013490L;
	
	private Venda venda;
	
	public DetalhesController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("detalhesFlash");
		venda  = (Venda) flash.get("detalhesFlash");
	}

	public Venda getVenda() {
		if (venda == null)
			venda = new Venda();
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
}
