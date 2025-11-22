package vista.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import modelo.*;
import servicio.ClubDeportivo;

import java.sql.SQLException;

public class BajaSocioView extends GridPane {
    public BajaSocioView(ClubDeportivo club) {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        ComboBox<Socio> id2 = new ComboBox<>();
        Button baja = new Button("Dar de baja");

        addRow(0, new Label("Socio"), id2);
        add(baja, 1, 1);
        try {
            id2.getItems().addAll(club.getSocios());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        baja.setOnAction(e -> {
            try {
                Socio socio = id2.getValue();
                boolean ok=true;
                ok= club.bajasocio(socio);
                if (ok) showInfo("Socio Borrado correctametne");
                else showError("Socio no Borrado correctamente");
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
}

