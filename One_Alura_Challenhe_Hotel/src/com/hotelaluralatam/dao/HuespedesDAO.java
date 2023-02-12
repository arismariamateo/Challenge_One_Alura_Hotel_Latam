package com.hotelaluralatam.dao;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
import java.util.List;

import com.hotelaluralatam.factory.ConnectionFactory;
import com.hotelaluralatam.modelo.Huesped;
import com.hotelaluralatam.modelo.Reserva;

import UsoFechas.UsoFecha;

public class HuespedesDAO {
    
    private Connection con;

    public HuespedesDAO(Connection con) {
        this.con = con;
    }

    public int guardar(Huesped huesped) {
        int huespedID = 0;
        final Connection con = new ConnectionFactory().getConnection();
        try(con){
            final PreparedStatement statement = con.prepareStatement(
                "INSERT INTO HUESPEDES (NOMBRE, APELLIDO, FECHA_NACIMIENTO, NACIONALIDAD, TELEFONO, ID_RESERVA) VALUES (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, huesped.getNombre());
            statement.setString(2, huesped.getApellido());
            statement.setDate(3, UsoFecha.tranformarADateSQL(huesped.getFechaNacimiento()));
            statement.setString(4, huesped.getNacionalidad());
            statement.setString(5, huesped.getTelefono());
            statement.setInt(6, huesped.getReserva());
            statement.execute();

            final ResultSet resultSet = statement.getGeneratedKeys();
            try(resultSet) {
                while(resultSet.next()) {
                    huespedID = resultSet.getInt(1);
                    huesped.setId(huespedID);
                    System.out.println("Huesped guardado con éxito" + huesped);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return huespedID;
    }

	public List<Huesped> obtenerHuespedes() {
		List<Huesped> huespedes = new ArrayList<>();
		final Connection con = new ConnectionFactory().getConnection();
        try(con){
            final PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM huespedes");
            try(statement){
                statement.execute();
                final ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()){
                	Huesped fila = new Huesped(
                			resultSet.getInt("id_huesped"),
                			resultSet.getString("nombre"),
                			resultSet.getString("apellido"),
                			resultSet.getDate("fecha_nacimiento").toLocalDate(),
                			resultSet.getString("nacionalidad"),
                			resultSet.getString("telefono"),
                			resultSet.getInt("id_reserva")
                	);
                    huespedes.add(fila);
                }
                return huespedes;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

    public int modificar(Huesped huesped) {
        final Connection con = new ConnectionFactory().getConnection();
        try(con){
            final PreparedStatement statement = con.prepareStatement(
                "UPDATE HUESPEDES SET NOMBRE = ?, APELLIDO = ?, FECHA_NACIMIENTO = ?, NACIONALIDAD = ?, TELEFONO = ?, ID_RESERVA = ? WHERE ID = ?");
            statement.setString(1, huesped.getNombre());
            statement.setString(2, huesped.getApellido());
            statement.setDate(3, UsoFecha.tranformarADateSQL(huesped.getFechaNacimiento()));
            statement.setString(4, huesped.getNacionalidad());
            statement.setString(5, huesped.getTelefono());
            statement.setInt(6, huesped.getReserva());
            statement.setInt(7, huesped.getId());
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
            // System.out.println("Error al eliminar la reserva" + e.getMessage());
            throw new RuntimeException();
        }
    }
}
