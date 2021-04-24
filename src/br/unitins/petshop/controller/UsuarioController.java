package br.unitins.petshop.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Util;
import br.unitins.petshop.model.Perfil;
import br.unitins.petshop.model.Usuario;

@Named
@ViewScoped
public class UsuarioController implements Serializable{
	
	private static final long serialVersionUID = 1304667158255601678L;
	private Usuario usuario = null;
	private String confirmarSenha;
	private List<Usuario> listaUsuario = null;
	private int cont = 0;
	private UIComponent uicCpf;
	
	public UIComponent getUicCpf() {
		return uicCpf;
	}

	public void setUicCpf(UIComponent uicCpf) {
		this.uicCpf = uicCpf;
	}

	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}
	
	public void validarCpf() {
		if (getUsuario().getCpf().equals("111.111.111-11")) {
			Util.addErrorMessage("O cpf: 111.111.111-11 é inválido.", uicCpf.getClientId());
			;
		}
	}
	
	private boolean verificaSenha() {
		if (getUsuario().getSenha().equals(getConfirmarSenha())) {
			return true;
		}
		Util.addErrorMessage("As senhas estão diferentes.");
		return false;
	}

	public void incluir() {
		if (!verificaSenha()) {
			Util.addInfoMessage("As senhas estão diferentes.");
			return;
		}
		
		Connection conn = UsuarioController.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO usuario ");
		sql.append(" (cpf, nome, login, senha) ");
		sql.append("VALUES ");
		sql.append(" (?, ?, ?, ? ) ");
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, getUsuario().getCpf());
			stat.setString(2, getUsuario().getNome());
			stat.setString(3, getUsuario().getLogin());
			stat.setString(4, getUsuario().getSenha());
			
			stat.execute();
			Util.addInfoMessage("Inclusão realizada com sucesso.");
			limpar();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void alterar() {
		System.out.println("Alterar");
		if (verificaSenha()) {
			int index = listaUsuario.indexOf(getUsuario());
			listaUsuario.set(index, getUsuario());
			limpar();
			
			// envio de mensagem para a interface 
			Util.addInfoMessage("Alteração realizada com sucesso");
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
		Util.addInfoMessage("Exclusão realizada com sucesso");
	}
	
	public void excluir(Usuario usu) {
		System.out.println("Excluir");
		listaUsuario.remove(usu);
		System.out.println("Foi excluido o usuario do id: " + usu.getId());
	}
	
	public void limpar() {
		System.out.println("Limpar");
		setUsuario(null);
		setListaUsuario(null);
	}
	
	public void editar(Usuario usu) {
		System.out.println("Editar");
		setUsuario(usu.getClone());
	}
	
	private static Connection getConnection() {
		// Registro do driver do postgresql
	    try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager
					.getConnection("jdbc:postgresql://127.0.0.1:5432/petshopdb", "topicos1", "123456");
			return connection;
	    } catch (ClassNotFoundException e) {
			System.out.println("O Driver não foi encontrado.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Falha na conexao com o banco de dados.");
			e.printStackTrace();
		}
	    return null;
	}
	
	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
			Connection conn = UsuarioController.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  u.id, ");
			sql.append("  u.cpf, ");
			sql.append("  u.nome, ");
			sql.append("  u.login ");
			sql.append("FROM ");
			sql.append("  usuario u");
			
			PreparedStatement stat = null;
			try {
				stat = conn.prepareStatement(sql.toString());
				ResultSet rs = stat.executeQuery();
				
				while(rs.next()) {
					Usuario usuario = new Usuario();
					usuario.setId(rs.getInt("id"));
					usuario.setCpf(rs.getString("cpf"));
					usuario.setNome(rs.getString("nome"));
					usuario.setLogin(rs.getString("login"));
					listaUsuario.add(usuario);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					stat.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
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
