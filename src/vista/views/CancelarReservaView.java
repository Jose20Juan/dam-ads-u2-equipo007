package vista.views;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import modelo.*;
import servicio.ClubDeportivo;

import java.sql.SQLException;

public class CancelarReservaView extends GridPane {
    public CancelarReservaView(ClubDeportivo club) {
        setPadding(new Insets(12));
        setHgap(8);
        setVgap(8);

        ComboBox<Reserva> id = new ComboBox();
        try {
            id.getItems().addAll(club.getReservas());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Button cancelar = new Button("Cancelar reserva");

        addRow(0, new Label("Reserva"), id);
        add(cancelar, 1, 1);

        cancelar.setOnAction(e -> {
            try {
                club.cancelarReserva(id.getValue());

            } catch (Exception ex) {
                showError("No se pudo cancelar la reserva");
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
}
