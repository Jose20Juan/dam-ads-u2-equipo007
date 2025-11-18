package vista.views;

import javafx.util.StringConverter;
import modelo.*;
import servicio.ClubDeportivo;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;


public class ReservaFormView extends GridPane {
    public ReservaFormView(ClubDeportivo club) {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        TextField id = new TextField();
        ComboBox<Socio> idSocio = new ComboBox();
        ComboBox<Pista> idPista = new ComboBox();
        DatePicker fecha = new DatePicker(LocalDate.now());
        TextField hora = new TextField("10:00");
        Spinner<Integer> duracion = new Spinner<>(30, 300, 60, 30);
        Button crear = new Button("Reservar");

        try {
            idSocio.getItems().addAll(club.getSocios());
            idPista.getItems().addAll(club.getPistas());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Aqui le he tenido que meter convertidores para que el texto del ComboBox sea legible
        idSocio.converterProperty().set(createSocioConverter());
        idPista.converterProperty().set(createPistaConverter());

        addRow(0, new Label("idReserva*"), id);
        addRow(1, new Label("Socio*"), idSocio);
        addRow(2, new Label("Pista*"), idPista);
        addRow(3, new Label("Fecha*"), fecha);
        addRow(4, new Label("Hora inicio* (HH:mm)"), hora);
        addRow(5, new Label("Duración (min)"), duracion);
        add(crear, 1, 7);

        crear.setOnAction(e -> {
            try {
                LocalTime t = LocalTime.parse(hora.getText());

                  Reserva r = new Reserva(id.getText(), idSocio.getValue().getIdSocio(), idPista.getValue().getIdPista(),
                          fecha.getValue(), t, duracion.getValue(), 0.0);
                  boolean ok = club.crearReserva(r);

                  if (ok) showInfo("Reserva insertada correctamente");

            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText("Error");
        a.showAndWait();
    }
    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }

    //Estos son los convertidores para que el texto del ComboBox sea legible
    private StringConverter<Socio> createSocioConverter() {
        return new StringConverter<Socio>() {
            @Override
            public String toString(Socio socio) {
                return (socio == null) ? null : socio.getNombre() + " " + socio.getApellidos() + " (" + socio.getIdSocio() + ")";
            }
            @Override
            public Socio fromString(String string) { return null; }
        };
    }

    private StringConverter<Pista> createPistaConverter() {
        return new StringConverter<Pista>() {
            @Override
            public String toString(Pista pista) {
                return (pista == null) ? null : pista.getIdPista() + " (" + pista.getDeporte() + ")";
            }
            @Override
            public Pista fromString(String string) { return null; }
        };
    }

}
