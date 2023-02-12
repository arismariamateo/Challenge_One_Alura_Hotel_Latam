package com.hotelaluralatam.controller;

import java.util.List;

import com.hotelaluralatam.dao.UsuariosDAO;
import com.hotelaluralatam.factory.ConnectionFactory;
import com.hotelaluralatam.modelo.Huesped;
import com.hotelaluralatam.modelo.Usuario;

public class UsuariosController {


	private UsuariosDAO usuarioDAO;

	public UsuariosController() {
		this.usuarioDAO = new UsuariosDAO(new ConnectionFactory().getConnection());
	}

	public List<Usuario> buscarUsuario(Usuario usuario) {
		return usuarioDAO.buscarUsuario(usuario);
		
	}


	public List<Usuario> obtenerUsuarios() {
		return usuarioDAO.obtenerUsuarios();
	}

	
	   public int eliminarUsuario(Integer id) {
	        return usuarioDAO.eliminar(id);
	    }

		public int modificarUsuario(Usuario user) {
			 return usuarioDAO.modificar(user);
		}
}
