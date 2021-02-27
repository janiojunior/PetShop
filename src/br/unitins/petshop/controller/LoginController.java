package br.unitins.petshop.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginController {

	private String usuario = "Teste";
	private String senha;
	
	public void entrar() {
		System.out.println("Entrou no metodo entrar");
		System.out.println(usuario);
		System.out.println(senha);
	}
	
	public void limpar() {
		System.out.println("Entrou no metodo limpar");
		usuario = "";
		senha = "";
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
