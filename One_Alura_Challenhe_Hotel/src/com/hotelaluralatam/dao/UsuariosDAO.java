package com.hotelaluralatam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hotelaluralatam.factory.ConnectionFactory;
import com.hotelaluralatam.modelo.Huesped;
import com.hotelaluralatam.modelo.Usuario;

public class UsuariosDAO {


	final private Connection con;

	public UsuariosDAO(Connection con) {
		this.con = con;
	}
	
/*public List<Usuario> buscarUsuario(Usuario usuario) {
		
		
		try {
			
			List<Usuario> resultado = new ArrayList<>();
			
			final PreparedStatement statement = con.prepareStatement("SELECT USUARIO, PASSWORD, NIVEL_ACCESO "
					+ "FROM USUARIOS WHERE USUARIO = ?");
			
			statement.setString(1, usuario.getUsuario());
			
			try(statement){
				statement.execute();
				
				ResultSet resultSet = statement.getResultSet(); 
				
				while (resultSet.next()) {
					Usuario fila = new Usuario(resultSet.getString("USUARIO"), resultSet.getString("PASSWORD"),
						resultSet.getString("NIVEL_ACCESO"));

					resultado.add(fila);
				}
				
				return resultado;
			}
			

		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
		
	
	}*/

public List<Usuario> obtenerUsuarios() {
	List<Usuario> usuarios = new ArrayList<>();
	final Connection con = new ConnectionFactory().getConnection();
    try(con){
        final PreparedStatement statement = con.prepareStatement(
            "SELECT * FROM usuarios");
        try(statement){
            statement.execute();
            final ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
            	Usuario fila = new Usuario(
            		//	resultSet.getInt("id"),
            			resultSet.getString("usuario"),
            			resultSet.getString("password"),
            			resultSet.getInt("nivelAcceso")
            			
            	);
                usuarios.add(fila);
            }
            return usuarios;
        }
    }
    catch (SQLException e) {
        throw new RuntimeException(e);
    }
}


public int modificar(Usuario usuario) {
    final Connection con = new ConnectionFactory().getConnection();
    try(con){
        final PreparedStatement statement = con.prepareStatement(
            "UPDATE usuarios SET usuario = ?, password = ?, nivelAcceso = ? WHERE usuario = ?");
        statement.setString(1, usuario.getUsuario());
        statement.setString(2, usuario.getPassword());
        statement.setInt(3, usuario.getNivelAcceso());
        statement.execute();
        return statement.getUpdateCount();
    }
    catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

public int eliminar(Integer id) {
    final Connection con = new ConnectionFactory().getConnection();
    try(con) {
        final PreparedStatement statement = con.prepareStatement("DELETE FROM usuarios WHERE id_usuario = ?");
        try(statement){
            statement.setInt(1, id);
            statement.execute();
         
                return statement.getUpdateCount();
            }
        
    } catch (Exception e) {
        // System.out.println("Error al eliminar la reserva" + e.getMessage());
        throw new RuntimeException();
    }
}
	
public List<Usuario> buscarUsuario(Usuario usuario) {
	
	
	try {
		
		List<Usuario> resultado = new ArrayList<>();
		
		final PreparedStatement statement = con.prepareStatement("SELECT usuario, password, nivelAcceso "
				+ "FROM usuarios WHERE usuario = ?");
		
		statement.setString(1, usuario.getUsuario());
		
		try(statement){
			statement.execute();
			
			ResultSet resultSet = statement.getResultSet(); 
			
			while (resultSet.next()) {
				Usuario fila = new Usuario(resultSet.getString("usuario"), resultSet.getString("password"),
						Integer.parseInt(resultSet.getString("nivelAcceso")));

				resultado.add(fila);
			}
			
			return resultado;
		}
		

	}catch (SQLException e) {
		throw new RuntimeException(e);
	}

	
}

}
