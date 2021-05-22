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
public class ProdutoController implements Serializable {
	
	private static final long serialVersionUID = 4153068272054439450L;
	private Produto produto = null;
	private List<Produto> listaProduto = null;
	
	public ProdutoController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("produtoFlash");
		setProduto((Produto)flash.get("produtoFlash"));
	}
	
	public TipoPeso[] getListaTipoPeso() {
		return TipoPeso.values();
	}
	
	public void incluir() {
		DAO<Produto> dao = new ProdutoDAO();
		if (dao.inserir(getProduto())) {
			Util.addInfoMessage("Inclusão realizada com sucesso.");
			limpar();
		} else
			Util.addErrorMessage("Problemas ao inserir no banco de dados.");
	}
	
	public void alterar() {
		DAO<Produto> dao = new ProdutoDAO();
		if (dao.alterar(getProduto())) {
			Util.addInfoMessage("Alteração realizada com sucesso.");
			limpar();
		} else
			Util.addErrorMessage("Problemas ao alterar no banco de dados.");
	}
	
	public void excluir() {
		excluir(getProduto());
	}
	
	public void excluir(Produto usu) {
		DAO<Produto> dao = new ProdutoDAO();
		if (dao.excluir(usu.getId())) {
			Util.addInfoMessage("Exclusão realizada com sucesso.");
			limpar();
		} else
			Util.addErrorMessage("Problemas ao excluir no banco de dados.");
	}
	
	public void limpar() {
		System.out.println("Limpar");
		setProduto(null);
		setListaProduto(null);
	}
	
	public void editar(Produto usu) {
		DAO<Produto> dao = new ProdutoDAO();
		setProduto(dao.obterUm(usu.getId()));
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

	public Produto getProduto() {
		if (produto == null) {
			produto = new Produto();
			produto.setPeso(new Peso());
		}
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
