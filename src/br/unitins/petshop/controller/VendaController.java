package br.unitins.petshop.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Session;
import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.ProdutoDAO;
import br.unitins.petshop.model.ItemVenda;
import br.unitins.petshop.model.Produto;

@Named
@ViewScoped
public class VendaController implements Serializable {

	private static final long serialVersionUID = 4860609187938109341L;
	private Integer tipoFiltro;
	private String filtro;
	private List<Produto> listaProduto;
	
	public void pesquisar() {
		ProdutoDAO dao = new ProdutoDAO();
		if (getTipoFiltro() == 1)
			setListaProduto(dao.obterPeloNome(filtro));
		else
			setListaProduto(dao.obterPelaDescricao(filtro));
	}
	
	public void addCarrinho(Produto produto) {
		// obtendo o carrinho da sessao
		@SuppressWarnings("unchecked")
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().get("carrinho");
		// caso nao exista o carrinho na sessao ... criar uma nova instancia local
		if (carrinho == null) 
			carrinho = new ArrayList<ItemVenda>();
		
		
		ItemVenda iv = new ItemVenda();
		iv.setProduto(produto);
		iv.setQuantidade(1);
		iv.setValorUnitario(produto.getPreco());
	
		if (carrinho.contains(iv)) {
			int index = carrinho.indexOf(iv);
			int quantidade = carrinho.get(index).getQuantidade();
			carrinho.get(index).setQuantidade(++ quantidade );
			
		} else {
			carrinho.add(iv);
		}
		
		Session.getInstance().set("carrinho", carrinho);
		
		Util.addInfoMessage("Item adicionado no carrinho.");
		
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Produto> getListaProduto() {
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
	
	
}
