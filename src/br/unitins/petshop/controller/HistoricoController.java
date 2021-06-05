package br.unitins.petshop.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Session;
import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.VendaDAO;
import br.unitins.petshop.model.Usuario;
import br.unitins.petshop.model.Venda;

@Named
@ViewScoped
public class HistoricoController implements Serializable{

	private static final long serialVersionUID = -6223356893078168492L;
	
	private List<Venda> listaVenda;
	
	public void detalhes(Venda venda) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("detalhesFlash", venda);
		
		Util.redirect("detalhesvenda.xhtml");
	}

	public List<Venda> getListaVenda() {
		Usuario usuario = (Usuario) Session.getInstance().get("usuarioLogado");
		
		VendaDAO dao = new VendaDAO();
		listaVenda = dao.obterTodos(usuario);
		
		return listaVenda;
	}

	public void setListaVenda(List<Venda> listaVenda) {
		this.listaVenda = listaVenda;
	}
	
}
