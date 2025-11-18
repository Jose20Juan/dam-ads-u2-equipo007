package servicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.*;

public class ClubDeportivo {
   private Connection conexion;

    public ClubDeportivo() throws SQLException {
        conexion = DriverManager.getConnection("jdbc:mysql://mysql-j20j2.alwaysdata.net:3306/j20j2_club_dama?serverTimezone=UTC",
                "j20j2", "CholoDiversion");
    }

    public ArrayList<Socio> getSocios() {
        ArrayList<Socio> socios = new ArrayList<>();
        return socios;
    }

    public ArrayList<Pista> getPistas() {
        ArrayList<Pista> pistas = new ArrayList<>();
        return pistas;
    }

    public ArrayList<Reserva> getReservas() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        return reservas;
    }
}
