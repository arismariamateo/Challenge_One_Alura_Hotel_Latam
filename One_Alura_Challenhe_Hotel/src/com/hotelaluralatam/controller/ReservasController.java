package com.hotelaluralatam.controller;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.hotelaluralatam.dao.ReservasDAO;
import com.hotelaluralatam.factory.ConnectionFactory;
import com.hotelaluralatam.modelo.Reserva;

public class ReservasController {

    private ReservasDAO reservasDAO;
	

    public ReservasController() {
        var connection = new ConnectionFactory();
        this.reservasDAO = new ReservasDAO(connection.getConnection());
    }

    public double valorReserva(Date fechaEntrada, Date fechaSalida, double valorDiario) {
        long dias = (fechaSalida.getTime() - fechaEntrada.getTime()) / (1000 * 60 * 60 * 24);
        return dias * valorDiario;
    }
    
    
    public List<Reserva> listar() {
        return reservasDAO.obtener();
    }

    public int modificarReserva(Reserva reserva) {
		 return this.reservasDAO.modificar(reserva);
		
	}

    public int eliminarReserva(Integer id) {
        return reservasDAO.eliminar(id);
    }
    
    public List<Reserva> buscar(String busqueda, String campo) {
		return  reservasDAO.buscar(busqueda, campo);
		
	}

	public int guardarReserva(Date dateE, Date dateS, String valor, String formapago) {
		   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        String fechaEntrada = dateFormat.format(dateE);
	        String fechaSalida = dateFormat.format(dateS);
	        
	        LocalDate fechaE;
			LocalDate fechaS;
		
				fechaE = LocalDate.parse(fechaEntrada);
				fechaS = LocalDate.parse(fechaSalida);
		
	        var reserva = new Reserva(fechaE, fechaS, valor, formapago);
	        return reservasDAO.guardar(reserva);
	}



	
}

