package com.hotelaluralatam.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.hotelaluralatam.dao.HuespedesDAO;
import com.hotelaluralatam.factory.ConnectionFactory;
import com.hotelaluralatam.modelo.Huesped;

public class HuespedesController {
    
    private HuespedesDAO huespedesDAO;

    public HuespedesController() {
    	var connection = new ConnectionFactory();
        this.huespedesDAO = new HuespedesDAO(connection.getConnection());
    }

    public int guardarHuesped(String nombre, String apellido, Date  fecha_nacimiento, String nacionalidad, String telefono, String nReserva ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaNacimiento = dateFormat.format(fecha_nacimiento);
                
        LocalDate fechaN;
	
			fechaN = LocalDate.parse(fechaNacimiento);
			
        Huesped huesped = new Huesped(nombre, apellido, fechaN, nacionalidad, telefono, nReserva);
        return huespedesDAO.guardar(huesped);
    }

	public List<Huesped> listar() {
		return huespedesDAO.obtenerHuespedes();
	}

    public int modificarHuesped(Huesped huesped) {
        return huespedesDAO.modificar(huesped);
    }

    public int eliminarHuesped(Integer id) {
        return huespedesDAO.eliminar(id);
    }
}
