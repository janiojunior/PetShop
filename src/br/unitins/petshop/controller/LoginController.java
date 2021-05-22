package br.unitins.petshop.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.unitins.petshop.application.Session;
import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.UsuarioDAO;
import br.unitins.petshop.model.Usuario;

@Named
@RequestScoped
public class LoginController {

	private Usuario usuario;
	
	public String entrar() {
		UsuarioDAO dao = new UsuarioDAO();
		String hash = Util.hash(getUsuario().getSenha() + getUsuario().getLogin());
		getUsuario().setSenha(hash);
		Usuario usuarioLogado = dao.validarLogin(getUsuario());
		if (usuarioLogado != null) {
			//Adicionar na sessao o objeto usuarioLogado
//			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
//			context.getSessionMap().put("usuarioLogado", usuarioLogado);
			Session.getInstance().set("usuarioLogado", usuarioLogado);
			
			// redirecionando para o template
			return "template.xhtml";
			
		}
		Util.addErrorMessage("Login ou senha inválido.");
		return null;
		
	}
	
	public void limpar() {
		usuario = null;
	}

	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
