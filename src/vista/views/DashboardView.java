package vista.views;

import servicio.ClubDeportivo;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;
import java.util.function.Consumer;
import modelo.Socio;
import modelo.Reserva;
import modelo.Pista;

public class DashboardView extends BorderPane {

    public DashboardView(ClubDeportivo club) {
        setPadding(new Insets(10));
        Label title = new Label("Resumen");
        setTop(title);

        TableView<Socio> tablaSocios = new TableView<>();
        TableColumn<Socio, String> c1 = new TableColumn<>("ID");
        c1.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getIdSocio()));
        TableColumn<Socio, String> c2 = new TableColumn<>("Nombre");
        c2.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getNombre()));
        tablaSocios.getColumns().addAll(c1, c2);
        try {
            tablaSocios.getItems().addAll(club.getSocios());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        TableView<Pista> tablaPistas = new TableView<>();
        TableColumn<Pista, String> p1 = new TableColumn<>("ID");
        p1.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getIdPista()));
        TableColumn<Pista, String> p2 = new TableColumn<>("Deporte");
        p2.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getDeporte()));
        TableColumn<Pista, String> p3 = new TableColumn<>("Disponible");
        p3.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(String.valueOf(p.getValue().isDisponible())));
        tablaPistas.getColumns().addAll(p1, p2, p3);
        try {
            tablaPistas.getItems().addAll(club.getPistas());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        TableView<Reserva> tablaReservas = new TableView<>();
        TableColumn<Reserva, String> r1 = new TableColumn<>("ID");
        r1.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getIdReserva()));
        TableColumn<Reserva, String> r2 = new TableColumn<>("Socio");
        r2.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getIdSocio()));
        TableColumn<Reserva, String> r3 = new TableColumn<>("Pista");
        r3.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getIdPista()));
        TableColumn<Reserva, String> r4 = new TableColumn<>("Fecha");
        r4.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getFecha().toString()));
        TableColumn<Reserva, String> r5 = new TableColumn<>("Inicio");
        r5.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getHoraInicio().toString()));
        TableColumn<Reserva, String> r6 = new TableColumn<>("Min");
        r6.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(String.valueOf(p.getValue().getDuracionMin())));

        //Esto es para que en el dashboard de los datos se muestre el precio de la reserva
        TableColumn<Reserva, String> r7 = new TableColumn<>("Precio");
        r7.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(String.valueOf(p.getValue().getPrecio()) + " €"));
        tablaReservas.getColumns().addAll(r1, r2, r3, r4, r5, r6, r7);

        try {
            tablaReservas.getItems().addAll(club.getReservas());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        BorderPane center = new BorderPane();
        center.setTop(new Label("Socios"));
        center.setCenter(tablaSocios);
        BorderPane right = new BorderPane();
        right.setTop(new Label("Pistas"));
        right.setCenter(tablaPistas);
        setCenter(center);
        setRight(right);
        setBottom(tablaReservas);
    }
}
