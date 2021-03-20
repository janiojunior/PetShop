package br.unitins.petshop.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.model.Perfil;
import br.unitins.petshop.model.Usuario;

@Named
@ViewScoped
public class UsuarioController implements Serializable{
	
	private static final long serialVersionUID = 1304667158255601678L;
	private Usuario usuario = null;
	private String confirmarSenha;
	private List<Usuario> listaUsuario;
	private int cont = 0;
	
	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}
	
	private boolean verificaSenha() {
		if (getUsuario().getSenha().equals(getConfirmarSenha())) {
			return true;
		}
		FacesContext.getCurrentInstance().
		addMessage(null, 
			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"As senhas estão diferentes.", null));
		return false;
	}
	
	public void incluir() {
		System.out.println("Incluir");
		if (verificaSenha()) {
			getUsuario().setId(++cont);
			listaUsuario.add(getUsuario());
			limpar();
			
			// envio de mensagem para a interface 
			FacesContext.getCurrentInstance().
			addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"Inclusão realizada com sucesso", null));
		} 
	}
	
	public void alterar() {
		if (verificaSenha()) {
			int index = listaUsuario.indexOf(getUsuario());
			listaUsuario.set(index, getUsuario());
			limpar();
			
			// envio de mensagem para a interface 
			FacesContext.getCurrentInstance().
			addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"Alteração realizada com sucesso", null));
		}
		
//		for (int index = 0; index < listaUsuario.size(); index++) {
//			// comparando os ids
//			if (getUsuario().getId().equals(listaUsuario.get(index).getId())) {
//				listaUsuario.set(index, getUsuario());
//			}
//		}
		
	}
	
	public void excluir() {
//		int index = listaUsuario.indexOf(getUsuario());
//		listaUsuario.remove(index);
//		listaUsuario.remove(getUsuario());
		excluir(getUsuario());
		limpar();
		// envio de mensagem para a interface 
		FacesContext.getCurrentInstance().
		addMessage(null, 
			new FacesMessage(FacesMessage.SEVERITY_INFO, 
					"Exclusão realizada com sucesso", null));
	}
	
	public void excluir(Usuario usu) {
		listaUsuario.remove(usu);
		System.out.println("Foi excluido o usuario do id: " + usu.getId());
	}
	
	public void limpar() {
		System.out.println("Limpar");
		setUsuario(null);
	}
	
	public void editar(Usuario usu) {
		setUsuario(usu.getClone());
	}
	
	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null)
			listaUsuario = new ArrayList<Usuario>();
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	
}
