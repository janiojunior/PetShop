package br.unitins.petshop.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TesteBanco {
	
	public static void main(String[] args) {
		// Registro do driver do postgresql
	    try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("O Driver não foi encontrado.");
			e.printStackTrace();
		}
	    
	    Connection connection = null;
		try {
			connection = DriverManager
				.getConnection("jdbc:postgresql://127.0.0.1:5432/petshopdb", "topicos1", "123456");
		} catch (SQLException e) {
			System.out.println("Falha na conexao com o banco de dados.");
			e.printStackTrace();
		}
	    
	    ResultSet rs = null;
		try {
			rs = connection.createStatement()
					.executeQuery("SELECT * FROM usuario");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	
	    try {
			while (rs.next()) {
			  System.out.println(rs.getInt("id"));
			  System.out.println(rs.getString("cpf"));
			  System.out.println(rs.getString("nome"));
			  System.out.println(rs.getInt("perfil"));
			  System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
