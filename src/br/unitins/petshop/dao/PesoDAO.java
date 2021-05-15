package br.unitins.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.petshop.model.Peso;
import br.unitins.petshop.model.TipoPeso;

public class PesoDAO implements DAO<Peso> {

	@Override
	public boolean inserir(Peso obj) {
		Connection conn = DAO.getConnection();
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO peso ");
		sql.append(" (id, valor, tipopeso) ");
		sql.append("VALUES ");
		sql.append(" (?, ?, ?) ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());
			stat.setDouble(2, obj.getValor());
			stat.setInt(3, obj.getTipoPeso().getValue());
			
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
	public boolean alterar(Peso obj) {
		Connection conn = DAO.getConnection();
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE peso SET ");
		sql.append(" valor = ?, ");
		sql.append(" tipopeso = ? ");
		sql.append("WHERE ");
		sql.append(" id = ? ");
	
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setDouble(1, obj.getValor());
			stat.setInt(2, obj.getTipoPeso().getValue());
			
			stat.setInt(3, obj.getId());
			
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
		sql.append("DELETE FROM peso WHERE id = ? ");
		
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
	public List<Peso> obterTodos() {
		Connection conn = DAO.getConnection();
		
		List<Peso> listaPeso = new ArrayList<Peso>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.valor, ");
		sql.append("  p.tipopeso ");
		sql.append("FROM ");
		sql.append("  peso p ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Peso peso = new Peso();
				peso.setId(rs.getInt("id"));
				peso.setValor(rs.getDouble("valor"));
				peso.setTipoPeso(TipoPeso.valueOf(rs.getInt("tipopeso")));
				listaPeso.add(peso);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaPeso = null;
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
		
		if (listaPeso == null || listaPeso.isEmpty())
			return null;
		
		return listaPeso;
	}

	@Override
	public Peso obterUm(Integer id) {
		Connection conn = DAO.getConnection();
		
		Peso peso = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.valor, ");
		sql.append("  p.tipopeso ");
		sql.append("FROM ");
		sql.append("  peso p ");
		sql.append("WHERE ");
		sql.append("  p.id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				peso = new Peso();
				peso.setId(rs.getInt("id"));
				peso.setValor(rs.getDouble("valor"));
				peso.setTipoPeso(TipoPeso.valueOf(rs.getInt("tipopeso")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			peso = null;
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
		
		return peso;
	}

}
