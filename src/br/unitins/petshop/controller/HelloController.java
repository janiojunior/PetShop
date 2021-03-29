package br.unitins.petshop.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class HelloController implements Serializable{
	
	private static final long serialVersionUID = 4604779248799935063L;
	private String testeAjax;
	private String testeAjax2;
	
	public void enviar() {
		System.out.println("Entrou no método enviar.");
		System.out.println(testeAjax);
		setTesteAjax2("blah");
	}

	public String getTesteAjax() {
		return testeAjax;
	}

	public void setTesteAjax(String testeAjax) {
		this.testeAjax = testeAjax;
	}

	public String getTesteAjax2() {
		return testeAjax2;
	}

	public void setTesteAjax2(String testeAjax2) {
		this.testeAjax2 = testeAjax2;
	}
	
	
	
}
