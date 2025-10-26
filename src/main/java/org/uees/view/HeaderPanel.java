package org.uees.view;

import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class HeaderPanel extends HBox {

    public final ImageView logoImg = new ImageView();
    public final Label titleLabel = new Label("PENDO - Task Organizer");

    public HeaderPanel() {
        super(18);
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().add("header-panel");

        // Cargar logo (ajusta la ruta si es necesario)
        logoImg.setImage(new Image(getClass().getResource("/images/PendoAppLogo.png").toExternalForm()));
        logoImg.setFitWidth(56);
        logoImg.setFitHeight(56);
        logoImg.setPreserveRatio(true);

        titleLabel.getStyleClass().add("app-title");

        getChildren().addAll(logoImg, titleLabel);
    }
}
