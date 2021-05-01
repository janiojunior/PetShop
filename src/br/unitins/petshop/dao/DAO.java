package br.unitins.petshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import br.unitins.petshop.model.Usuario;

public interface DAO {
	
	public boolean inserir(Usuario obj);
	public boolean alterar(Usuario obj);
	public boolean excluir(Integer id);
	public List<Usuario> obterTodos();
	public Usuario obterUm(Integer id);

	public static Connection getConnection() {
		// Registro do driver do postgresql
	    try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager
					.getConnection("jdbc:postgresql://127.0.0.1:5432/petshopdb", "topicos1", "123456");
			return connection;
	    } catch (ClassNotFoundException e) {
			System.out.println("O Driver n�o foi encontrado.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Falha na conexao com o banco de dados.");
			e.printStackTrace();
		}
	    return null;
	}
}
