package org.uees.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class TaskOrganizer extends BorderPane {

    public final HeaderPanel headerPanel = new HeaderPanel();
    public final ListsPanel listsPanel = new ListsPanel();
    public final TasksPanel tasksPanel = new TasksPanel();
    public final CompletedPanel completedPanel = new CompletedPanel();

    public TaskOrganizer() {
        super();
        this.getStyleClass().add("organizer-root");

        VBox centerVBox = new VBox(tasksPanel, completedPanel);
        centerVBox.setSpacing(0);

        setTop(headerPanel);
        setLeft(listsPanel);
        setCenter(centerVBox);

        setPrefSize(1280, 832);
    }
}
