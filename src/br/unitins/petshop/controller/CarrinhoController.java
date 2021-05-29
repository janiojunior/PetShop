package br.unitins.petshop.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Session;
import br.unitins.petshop.model.ItemVenda;
import br.unitins.petshop.model.Venda;

@Named
@ViewScoped
public class CarrinhoController implements Serializable {

	private static final long serialVersionUID = -1295358829546823824L;
	private Venda venda;

	public Venda getVenda() {
		@SuppressWarnings("unchecked")
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().get("carrinho");
		
		venda = new Venda();
		if (carrinho == null) 
			venda.setListaItemVenda(new ArrayList<ItemVenda>());
		else 
			venda.setListaItemVenda(carrinho);
		
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}
