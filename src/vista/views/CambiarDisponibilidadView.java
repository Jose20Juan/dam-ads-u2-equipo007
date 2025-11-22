package vista.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import modelo.*;
import servicio.ClubDeportivo;

import java.sql.SQLException;

public class CambiarDisponibilidadView extends GridPane {
    public CambiarDisponibilidadView(ClubDeportivo club) throws SQLException {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        ComboBox<Pista> id = new ComboBox();
        id.getItems().addAll(club.getPistas());
        CheckBox disponible = new CheckBox("Disponible");
        Button cambiar = new Button("Aplicar");

        id.setOnAction(e -> {
            Pista pistaSeleccionada = id.getValue();
            if (pistaSeleccionada != null) {
                disponible.setSelected(pistaSeleccionada.isDisponible());
            }
        });

        addRow(0, new Label("idPista"), id);
        addRow(1, new Label("Estado"), disponible);
        add(cambiar, 1, 2);

        cambiar.setOnAction(e -> {
            try {

                Pista pistaSeleccionada = id.getValue();

                club.cambiarDisponibilidad(pistaSeleccionada.getIdPista(), disponible.isSelected());

                showInfo("Disponibilidad actualizada correctamente");

            } catch (Exception ex) {
                showError("No se pudo actualizar la disponibilidad");
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
