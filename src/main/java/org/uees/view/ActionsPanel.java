package org.uees.view;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

public class ActionsPanel extends HBox {

    public final Button btnCrearTarea = new Button("Crear Tarea");
    public final Button btnVisualizarListas = new Button("Visualizar Listas");
    public final Button btnExportarHistorial = new Button("Exportar Historial");
    public final Button btnLimpiarCompletadas = new Button("Limpiar Completadas");

    public ActionsPanel() {
        super(32);
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().add("actions-panel");

        btnCrearTarea.getStyleClass().add("action-main");
        btnVisualizarListas.getStyleClass().add("action-secondary");
        btnExportarHistorial.getStyleClass().add("action-secondary");
        btnLimpiarCompletadas.getStyleClass().add("action-secondary");

        // Elimina setMinWidth/setMaxWidth para que el CSS y el layout decidan el ancho por el texto.
        getChildren().addAll(btnCrearTarea, btnVisualizarListas, btnExportarHistorial, btnLimpiarCompletadas);
    }
}
