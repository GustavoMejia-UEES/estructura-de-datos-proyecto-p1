package org.uees.view;

import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.collections.ObservableList;
import javafx.scene.layout.Priority;

public class ListsPanel extends VBox {

    public final ListView<String> listasView = new ListView<>();
    public final Button btnNuevaLista = new Button("Nuevo");
    public final Button btnEliminarLista = new Button("Eliminar");

    public ListsPanel() {
        super(20);
        setPrefWidth(320);
        getStyleClass().add("lists-panel");

        Label title = new Label("Mis Listas");
        title.getStyleClass().add("panel-title");

        listasView.getStyleClass().add("main-list");
        listasView.setPrefHeight(380);

        btnNuevaLista.getStyleClass().add("main-button-small");
        btnEliminarLista.getStyleClass().add("main-button-small");

        btnNuevaLista.setMinWidth(140);
        btnNuevaLista.setMaxWidth(Double.MAX_VALUE);
        btnEliminarLista.setMinWidth(140);
        btnEliminarLista.setMaxWidth(Double.MAX_VALUE);

        HBox btnBox = new HBox(24, btnNuevaLista, btnEliminarLista);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(8, 0, 12, 0));
        HBox.setHgrow(btnNuevaLista, Priority.ALWAYS);
        HBox.setHgrow(btnEliminarLista, Priority.ALWAYS);

        this.getChildren().addAll(title, btnBox, listasView);
    }

    public void setListas(ObservableList<String> listas) {
        listasView.setItems(listas);
    }
}
