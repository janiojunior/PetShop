package br.unitins.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.unitins.petshop.model.Peso;
import br.unitins.petshop.model.Produto;
import br.unitins.petshop.model.TipoPeso;

public class ProdutoDAO implements DAO<Produto> {

	@Override
	public boolean inserir(Produto obj) {
		Connection conn = DAO.getConnection();
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO produto ");
		sql.append(" (nome, descricao, estoque, preco) ");
		sql.append("VALUES ");
		sql.append(" (?, ?, ?, ?) ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setDouble(3, obj.getEstoque());
			stat.setDouble(4, obj.getPreco());
			
			stat.execute();
				
			ResultSet rs = stat.getGeneratedKeys();
			if (rs.next()) {
				obj.getPeso().setId(rs.getInt("id"));
				PesoDAO dao = new PesoDAO();
				dao.inserir(obj.getPeso());
			}
			
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
	public boolean alterar(Produto obj) {
		Connection conn = DAO.getConnection();
		boolean deuErro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE produto SET ");
		sql.append(" nome = ?, ");
		sql.append(" descricao = ?, ");
		sql.append(" estoque = ?, ");
		sql.append(" preco = ? ");
		sql.append("WHERE ");
		sql.append(" id = ? ");
	
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setDouble(3, obj.getEstoque());
			stat.setDouble(4, obj.getPreco());
			
			stat.setInt(5, obj.getId());
			
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
		sql.append("DELETE FROM produto WHERE id = ? ");
		
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
	public List<Produto> obterTodos() {
		Connection conn = DAO.getConnection();
		
		List<Produto> listaProduto = new ArrayList<Produto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.nome, ");
		sql.append("  p.descricao, ");
		sql.append("  p.estoque, ");
		sql.append("  p.preco, ");
		sql.append("  pe.id AS id_peso, ");
		sql.append("  pe.valor, ");
		sql.append("  pe.tipopeso ");
		sql.append("FROM ");
		sql.append("  produto p, ");
		sql.append("  peso pe ");
		sql.append("WHERE ");
		sql.append("  p.id = pe.id ");
		sql.append("ORDER BY p.nome ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setEstoque(rs.getDouble("estoque"));
				produto.setPreco(rs.getDouble("preco"));
				
				produto.setPeso(new Peso());
				produto.getPeso().setId(rs.getInt("id_peso"));
				produto.getPeso().setValor(rs.getDouble("valor"));
				produto.getPeso().setTipoPeso(TipoPeso.valueOf(rs.getInt("tipopeso")));
				
				listaProduto.add(produto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaProduto = null;
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
		
		if (listaProduto == null || listaProduto.isEmpty())
			return null;
		
		return listaProduto;
	}
	

	public List<Produto> obterPeloNome(String nome) {
		Connection conn = DAO.getConnection();
		
		List<Produto> listaProduto = new ArrayList<Produto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.nome, ");
		sql.append("  p.descricao, ");
		sql.append("  p.estoque, ");
		sql.append("  p.preco, ");
		sql.append("  pe.id AS id_peso, ");
		sql.append("  pe.valor, ");
		sql.append("  pe.tipopeso ");
		sql.append("FROM ");
		sql.append("  produto p, ");
		sql.append("  peso pe ");
		sql.append("WHERE ");
		sql.append("  p.id = pe.id ");
		sql.append("  AND p.nome ILIKE ? ");
		sql.append("ORDER BY p.nome ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%"+nome+"%");
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setEstoque(rs.getDouble("estoque"));
				produto.setPreco(rs.getDouble("preco"));
				
				produto.setPeso(new Peso());
				produto.getPeso().setId(rs.getInt("id_peso"));
				produto.getPeso().setValor(rs.getDouble("valor"));
				produto.getPeso().setTipoPeso(TipoPeso.valueOf(rs.getInt("tipopeso")));
				
				listaProduto.add(produto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaProduto = null;
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
		
		if (listaProduto == null || listaProduto.isEmpty())
			return null;
		
		return listaProduto;
	}
	
	public List<Produto> obterPelaDescricao(String descricao) {
		Connection conn = DAO.getConnection();
		
		List<Produto> listaProduto = new ArrayList<Produto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.nome, ");
		sql.append("  p.descricao, ");
		sql.append("  p.estoque, ");
		sql.append("  p.preco, ");
		sql.append("  pe.id AS id_peso, ");
		sql.append("  pe.valor, ");
		sql.append("  pe.tipopeso ");
		sql.append("FROM ");
		sql.append("  produto p, ");
		sql.append("  peso pe ");
		sql.append("WHERE ");
		sql.append("  p.id = pe.id ");
		sql.append("  AND p.descricao ILIKE ? ");
		sql.append("ORDER BY p.nome ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%"+descricao+"%");
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setEstoque(rs.getDouble("estoque"));
				produto.setPreco(rs.getDouble("preco"));
				
				produto.setPeso(new Peso());
				produto.getPeso().setId(rs.getInt("id_peso"));
				produto.getPeso().setValor(rs.getDouble("valor"));
				produto.getPeso().setTipoPeso(TipoPeso.valueOf(rs.getInt("tipopeso")));
				
				listaProduto.add(produto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaProduto = null;
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
		
		if (listaProduto == null || listaProduto.isEmpty())
			return null;
		
		return listaProduto;
	}


	@Override
	public Produto obterUm(Integer id) {
		Connection conn = DAO.getConnection();
		
		Produto produto = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.nome, ");
		sql.append("  u.descricao, ");
		sql.append("  u.estoque, ");
		sql.append("  u.preco ");
		sql.append("FROM ");
		sql.append("  produto u ");
		sql.append("WHERE ");
		sql.append("  u.id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setEstoque(rs.getDouble("estoque"));
				produto.setPreco(rs.getDouble("preco"));
				
				PesoDAO dao = new PesoDAO();
				produto.setPeso(dao.obterUm(produto.getId()));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			produto = null;
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
		
		return produto;
	}

}
