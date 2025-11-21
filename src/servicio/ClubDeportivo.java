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

    public boolean altaSocio(Socio socio) throws SQLException {

        String sql = "INSERT INTO socios\n" +
                "(id_socio, dni, nombre, apellidos, telefono, email)\n" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = conexion.prepareStatement(sql);

        pst.setString(1, socio.getIdSocio());
        pst.setString(2, socio.getDni());
        pst.setString(3, socio.getNombre());
        pst.setString(4, socio.getApellidos());
        pst.setString(5, socio.getTelefono());
        pst.setString(6, socio.getEmail());

        pst.executeUpdate();
        return true;
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

    public boolean altaPista(Pista pista) throws SQLException {

        String sql = "INSERT INTO pistas\n" +
                "(id_pista, deporte, descripcion, disponible)\n" +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement pst = conexion.prepareStatement(sql);

        pst.setString(1, pista.getIdPista());
        pst.setString(2, pista.getDeporte());
        pst.setString(3, pista.getDescripcion());
        pst.setBoolean(4, pista.isDisponible());

        pst.executeUpdate();
        return true;
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

    public boolean crearReserva(Reserva reserva) throws SQLException {

        String sql = "{CALL sp_crear_reserva(?, ?, ?, ?, ?, ?)}";

        CallableStatement cst = conexion.prepareCall(sql);

        cst.setString(1, reserva.getIdReserva());
        cst.setString(2, reserva.getIdSocio());
        cst.setString(3, reserva.getIdPista());
        cst.setDate(4, Date.valueOf(reserva.getFecha()));
        cst.setTime(5, Time.valueOf(reserva.getHoraInicio()));
        cst.setInt(6, reserva.getDuracionMin());

        cst.execute();
        return true;
    }

    public void cambiarDisponibilidad(String nombrePista, boolean disponible) throws SQLException {
        String sql = "UPDATE pistas SET disponible = ? WHERE id_pista = ?";

        PreparedStatement pst = conexion.prepareStatement(sql);

        pst.setBoolean(1, disponible);


        pst.setString(2, nombrePista);

        int filasAfectadas = pst.executeUpdate();


        pst.close();

        System.out.println("Se actualizaron " + filasAfectadas + " filas.");
    }

}
