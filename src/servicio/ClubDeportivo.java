package servicio;

import java.sql.*;
import java.util.ArrayList;

import modelo.*;

public class ClubDeportivo {
   private Connection conexion;

    public ClubDeportivo() throws SQLException {
        conexion = DriverManager.getConnection("jdbc:mysql://mysql-j20j2.alwaysdata.net:3306/j20j2_club_dama?serverTimezone=UTC",
                "j20j2", "CholoDiversion");
    }

    public ArrayList<Socio> getSocios() throws SQLException {

        ArrayList<Socio> listaSocios = new ArrayList<>();

        String sql = "SELECT id_socio, dni, nombre, apellidos, telefono, email\n" +
                "FROM socios;\n";
        PreparedStatement pst = conexion.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Socio socio = new Socio(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6));
            listaSocios.add(socio);
        }

        rs.close();

        return listaSocios;
    }

    public ArrayList<Pista> getPistas() throws SQLException {

        ArrayList<Pista> listaPistas = new ArrayList<>();

        String sql = "SELECT id_pista, deporte, descripcion, disponible\n" +
                "FROM pistas;\n";
        PreparedStatement pst = conexion.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Pista pista = new Pista(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getBoolean(4));
            listaPistas.add(pista);
        }

        rs.close();

        return listaPistas;
    }

    public ArrayList<Reserva> getReservas() throws SQLException {

        ArrayList<Reserva> listaReservas = new ArrayList<>();

        String sql = "SELECT id_reserva, id_socio, id_pista, fecha, hora_inicio, duracion_min, precio\n" +
                "FROM reservas;\n";
        PreparedStatement pst = conexion.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Reserva reserva = new Reserva(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getDate(4).toLocalDate(), rs.getTime(5).toLocalTime(),
                    rs.getInt(6), rs.getDouble(7));
            listaReservas.add(reserva);
        }

        rs.close();

        return listaReservas;
    }
}
