package br.unitins.petshop.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Session;
import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.VendaDAO;
import br.unitins.petshop.model.ItemVenda;
import br.unitins.petshop.model.Usuario;
import br.unitins.petshop.model.Venda;

@Named
@ViewScoped
public class CarrinhoController implements Serializable {

	private static final long serialVersionUID = -1295358829546823824L;
	private Venda venda;
	
	public void finalizar() {
		Usuario usuario = (Usuario) Session.getInstance().get("usuarioLogado");
		if (usuario == null) {
			Util.addErrorMessage("Impossível realizar a venda, faça o login no sistema.");
			return;
		}
		
		if (getVenda().getListaItemVenda() == null || 
			getVenda().getListaItemVenda().isEmpty()) {
			Util.addErrorMessage("Não existem produtos no carrinho.");
			return;
		}
		
		// adicionando o usuario logado na venda
		getVenda().setUsuario(usuario);
		
		// salvando no banco de dados
		VendaDAO dao = new VendaDAO();
		if (dao.inserir(getVenda())) {
			Util.addInfoMessage("Venda realizada com sucesso.");
			// limpando o carrinho
			Session.getInstance().set("carrinho", null);
			// limpar a venda
			setVenda(null);
		} else {
			Util.addErrorMessage("Problemas ao concluir a venda. Tente novamente mais tarde.");
		}
		
	}

	public Venda getVenda() {
		if (venda == null)
			venda = new Venda();
		
		@SuppressWarnings("unchecked")
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().get("carrinho");
		
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
