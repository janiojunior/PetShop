package br.unitins.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.petshop.model.Usuario;

public class UsuarioDAO implements DAO {

	@Override
	public boolean inserir(Usuario obj) {
		Connection conn = DAO.getConnection();
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO usuario ");
		sql.append(" (cpf, nome, login, senha) ");
		sql.append("VALUES ");
		sql.append(" (?, ?, ?, ? ) ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getCpf());
			stat.setString(2, obj.getNome());
			stat.setString(3, obj.getLogin());
			stat.setString(4, obj.getSenha());
			
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			deuErro = true;
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
		
		if (deuErro)
			return false;
		return true;
	}

	@Override
	public boolean alterar(Usuario obj) {
		Connection conn = DAO.getConnection();
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE usuario SET ");
		sql.append(" cpf = ?, nome = ?, login = ?, senha = ? ");
		sql.append("WHERE ");
		sql.append(" id = ? ");
	
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getCpf());
			stat.setString(2, obj.getNome());
			stat.setString(3, obj.getLogin());
			stat.setString(4, obj.getSenha());
			
			stat.setInt(5, obj.getId());
			
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			deuErro = true;
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
		
		if (deuErro)
			return false;
		return true;
	}

	@Override
	public boolean excluir(Integer id) {
		return false;
	}

	@Override
	public List<Usuario> obterTodos() {
		Connection conn = DAO.getConnection();
		
		List<Usuario> listaUsuario = new ArrayList<Usuario>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.cpf, ");
		sql.append("  u.nome, ");
		sql.append("  u.login, ");
		sql.append("  u.senha ");
		sql.append("FROM ");
		sql.append("  usuario u ");
		sql.append("ORDER BY u.nome ");
		
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
				usuario.setSenha(rs.getString("senha"));
				listaUsuario.add(usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaUsuario = null;
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
		
		if (listaUsuario == null || listaUsuario.isEmpty())
			return null;
		
		return listaUsuario;
	}

	@Override
	public Usuario obterUm(Integer id) {
		Connection conn = DAO.getConnection();
		
		Usuario usuario = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.cpf, ");
		sql.append("  u.nome, ");
		sql.append("  u.login, ");
		sql.append("  u.senha ");
		sql.append("FROM ");
		sql.append("  usuario u ");
		sql.append("WHERE ");
		sql.append("  u.id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			usuario = null;
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
		
		return usuario;
	}

}
