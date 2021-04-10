package br.unitins.petshop.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Util;

@Named
@RequestScoped
public class LoginController {

	private String usuario = "Teste";
	private String senha;
	
	public String entrar() {
		System.out.println("Entrou no metodo entrar");
		System.out.println(usuario);
		System.out.println(senha);
		if (usuario.equals("janio") && senha.equals("123"))
			return "usuario.xhtml?faces-redirect=true";
		
		Util.addErrorMessage("Usuário ou Senha inválido.");
		return null;
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
