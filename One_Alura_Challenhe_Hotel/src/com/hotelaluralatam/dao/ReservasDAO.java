package com.hotelaluralatam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hotelaluralatam.factory.ConnectionFactory;
import com.hotelaluralatam.modelo.Huesped;
import com.hotelaluralatam.modelo.Reserva;

import UsoFechas.UsoFecha;

public class ReservasDAO {
    private Connection con;

    public ReservasDAO(Connection con) {
        this.con = con;
    }

    public int guardar(Reserva reserva) {
        int reservaID = 0;
        final Connection con = new ConnectionFactory().getConnection();
        try(con){
            final PreparedStatement statement = con.prepareStatement(
                "INSERT INTO reservas (fecha_entrada, fecha_salida, valor, tipo_pago) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, UsoFecha.tranformarADateSQL(reserva.getFechaEntrada()));
            statement.setDate(2, UsoFecha.tranformarADateSQL(reserva.getFechaSalida()));
            statement.setString(3, reserva.getValor());
            statement.setString(4, reserva.getFormaPago());
            statement.execute();

            final ResultSet resultSet = statement.getGeneratedKeys();
            try(resultSet) {
                while(resultSet.next()) {
                    reservaID = resultSet.getInt(1);
                    reserva.setId(reservaID);
                    System.out.println("Reserva guardada con éxito" + reserva);
                }
                return reservaID;
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Reserva> obtener() {
        List<Reserva> reservas = new ArrayList<>();
        final Connection con = new ConnectionFactory().getConnection();
        try(con){
            final PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM reservas");
            try(statement){
                statement.execute();
                final ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()){
                    Reserva fila = new Reserva(
                        resultSet.getInt("id"),
                        resultSet.getDate("fecha_entrada").toLocalDate(),
                        resultSet.getDate("fecha_salida").toLocalDate(),
                        resultSet.getString("valor"),
                        resultSet.getString("tipo_pago")
                    );
                    reservas.add(fila);
                }
                return reservas;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int modificar(Reserva reserva) {
        final Connection con = new ConnectionFactory().getConnection();
        try(con){
            final PreparedStatement statement =  con.prepareStatement(
                "UPDATE reservas SET fecha_entrada = ?, fecha_salida = ?, valor = ?, tipo_pago = ? WHERE id = ?");
            try(statement){
                statement.setDate(1, UsoFecha.tranformarADateSQL(reserva.getFechaEntrada()));
                statement.setDate(2, UsoFecha.tranformarADateSQL(reserva.getFechaSalida()));
                statement.setString(3, reserva.getValor());
                statement.setString(4, reserva.getFormaPago());
                statement.setInt(5, reserva.getId());
                statement.execute();
                return statement.getUpdateCount();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int eliminar(Integer id) {
        final Connection con = new ConnectionFactory().getConnection();
        try(con) {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE id_reserva = ?");
            try(statement){
                statement.setInt(1, id);
                statement.execute();
                final PreparedStatement statement2 = con.prepareStatement("DELETE FROM reservas WHERE id = ?");
                try(statement2){
                    statement2.setInt(1, id);
                    statement2.execute();
                    return statement.getUpdateCount() + statement2.getUpdateCount();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar la reserva" + e.getMessage());
            throw new RuntimeException();
        }
    }
    
    
    public List<Reserva> buscar(String busqueda, String campo) {
		List<Reserva> resultado = new ArrayList<Reserva>();
		try {

			
			final PreparedStatement statement;
			if(campo == "NOMBRE") {
				statement = con.prepareStatement("SELECT R.id, R.fecha_entrada, R.fecha_salida, R.valor,"
						+ " R.tipo_pago, H.id_huesped, H.nmbre, H.apellido, H.fecha_nacimientoto, H.nacionalidad,  H.telefono, H.id_reserva FROM reservas R INNER JOIN"
						+ " huespedes H ON H.id_reserva = R.id WHERE H.nombre LIKE '%' ? '%'");
				statement.setString(1, busqueda);
			}else if (campo == "APELLIDO") {
				statement = con.prepareStatement("SELECT R.id, R.fecha_entrada, R.fecha_salidad, R.valor,"
						+ " R.tipo_pago, H.id_huesped, H.nombre, H.apellido, H.fecha_nacimineto, H.nacionalidad,  H.telefono, H.id_reserva FROM reservas R INNER JOIN"
						+ " huespedes H ON H.id_reserva = R.ID WHERE H.apellido LIKE '%' ? '%'");
				statement.setString(1, busqueda);
			}else {
				statement = con.prepareStatement("SELECT R.id, R.fecha_entrada, R.decha_salida, R.valor,"
						+ " R.tipo_pago, H.id_huesped, H.nombre, H.apellido, H.fecha_nacimiento, H.nacionalidad,  H.telefono, H.id_reserva FROM reservas R INNER JOIN"
						+ " huespedes H ON H.id_reserva = R.id WHERE H.id_reserva = ?");
				statement.setInt(1, Integer.parseInt(busqueda));
			}
			
			
			try (statement) {
				statement.execute();

				ResultSet resultSet = statement.getResultSet();

				while (resultSet.next()) {
		
					
					Reserva fila = new Reserva(resultSet.getInt("R.id"), resultSet.getDate("R.fecha_entrada").toLocalDate(),
							resultSet.getDate("R.fecha_salida").toLocalDate(), resultSet.getString("R.tipo_pago"), 
							new Huesped(resultSet.getInt("H.id_huesped"), resultSet.getString("H.nombre"),
									resultSet.getString("H.apellido"), resultSet.getDate("H.fecha_nacimiento").toLocalDate(),
									resultSet.getString("H.nacimiento"),resultSet.getString("H.telefono"),resultSet.getInt("H.id_reserva")),
							Double.parseDouble(resultSet.getString("valor").toString()));
					resultado.add(fila);
				}

				return resultado;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
    

