package br.unitins.petshop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.petshop.model.Perfil;
import br.unitins.petshop.model.Usuario;

public class UsuarioDAO implements DAO<Usuario> {
	
	
	public Usuario validarLogin(Usuario usuario) {
		Connection conn = DAO.getConnection();
		
		Usuario usuarioLogado = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.cpf, ");
		sql.append("  u.nome, ");
		sql.append("  u.email, ");
		sql.append("  u.data_nascimento, ");
		sql.append("  u.login, ");
		sql.append("  u.senha, ");
		sql.append("  u.perfil ");
		sql.append("FROM ");
		sql.append("  usuario u ");
		sql.append("WHERE ");
		sql.append("  u.login = ? ");
		sql.append("  AND u.senha = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, usuario.getLogin());
			stat.setString(2, usuario.getSenha());
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				usuarioLogado = new Usuario();
				usuarioLogado.setId(rs.getInt("id"));
				usuarioLogado.setCpf(rs.getString("cpf"));
				usuarioLogado.setNome(rs.getString("nome"));
				usuarioLogado.setEmail(rs.getString("email"));
				Date data = rs.getDate("data_nascimento");
				usuarioLogado.setDataNascimento(data == null ? null : data.toLocalDate());
				usuarioLogado.setLogin(rs.getString("login"));
				usuarioLogado.setSenha(rs.getString("senha"));
				usuarioLogado.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			usuarioLogado = null;
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
		
		return usuarioLogado;		
	}

	@Override
	public boolean inserir(Usuario obj) {
		Connection conn = DAO.getConnection();
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO usuario ");
		sql.append(" (cpf, nome, email, data_nascimento, login, senha, perfil) ");
		sql.append("VALUES ");
		sql.append(" (?, ?, ?, ?, ?, ?, ? ) ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getCpf());
			stat.setString(2, obj.getNome());
			stat.setString(3, obj.getEmail());
			if (obj.getDataNascimento() == null)
				stat.setDate(4, null);
			else
				stat.setDate(4, Date.valueOf(obj.getDataNascimento())); 
			stat.setString(5, obj.getLogin());
			stat.setString(6, obj.getSenha());
			
			if (obj.getPerfil() != null)
				stat.setInt(7, obj.getPerfil().getValue());
			else 
				stat.setObject(7, null);
			
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
		sql.append(" cpf = ?, ");
		sql.append(" nome = ?, ");
		sql.append(" email = ?, ");
		sql.append(" data_nascimento = ?, ");
		sql.append(" login = ?, ");
		sql.append(" senha = ?, ");
		sql.append(" perfil = ? ");
		sql.append("WHERE ");
		sql.append(" id = ? ");
	
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getCpf());
			stat.setString(2, obj.getNome());
			stat.setString(3, obj.getEmail());
			if (obj.getDataNascimento() == null)
				stat.setDate(4, null);
			else
				stat.setDate(4, Date.valueOf(obj.getDataNascimento())); 
			stat.setString(5, obj.getLogin());
			stat.setString(6, obj.getSenha());
			
			if (obj.getPerfil() != null)
				stat.setInt(7, obj.getPerfil().getValue());
			else 
				stat.setObject(7, null);
			
			stat.setInt(8, obj.getId());
			
			stat.execute();
		} catch (Exception e) {
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
		Connection conn = DAO.getConnection();
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM usuario WHERE id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
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
	public List<Usuario> obterTodos() {
		Connection conn = DAO.getConnection();
		
		List<Usuario> listaUsuario = new ArrayList<Usuario>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.cpf, ");
		sql.append("  u.nome, ");
		sql.append("  u.email, ");
		sql.append("  u.data_nascimento, ");
		sql.append("  u.login, ");
		sql.append("  u.senha, ");
		sql.append("  u.perfil ");
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
				usuario.setEmail(rs.getString("email"));
				Date data = rs.getDate("data_nascimento");
				usuario.setDataNascimento(data == null ? null : data.toLocalDate());
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
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
		sql.append("  u.email, ");
		sql.append("  u.data_nascimento, ");
		sql.append("  u.login, ");
		sql.append("  u.senha, ");
		sql.append("  u.perfil ");
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
				usuario.setEmail(rs.getString("email"));
				Date data = rs.getDate("data_nascimento");
				usuario.setDataNascimento(data == null ? null : data.toLocalDate());
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
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
