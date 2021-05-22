package br.unitins.petshop.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.DAO;
import br.unitins.petshop.dao.ProdutoDAO;
import br.unitins.petshop.model.Peso;
import br.unitins.petshop.model.Produto;
import br.unitins.petshop.model.TipoPeso;

@Named
@ViewScoped
public class ConsultaProdutoController implements Serializable {
	
	private static final long serialVersionUID = -7607053657604748068L;
	private List<Produto> listaProduto = null;
	private Integer tipoFiltro;
	private String filtro;
	
	public String novoProduto() {
		return "produto.xhtml?faces-redirect=true";
	}
	
	public String editar(Produto usu) {
		ProdutoDAO dao = new ProdutoDAO();
		Produto produto = dao.obterUm(usu.getId());
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("produtoFlash", produto);
		
		return "produto.xhtml?faces-redirect=true";
	}
	
	public List<Produto> getListaProduto() {
		if (listaProduto == null) {
			DAO<Produto> dao = new ProdutoDAO();
			listaProduto = dao.obterTodos();
			if (listaProduto == null)
				listaProduto = new ArrayList<Produto>();
		}
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public Integer getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(Integer tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	
}
