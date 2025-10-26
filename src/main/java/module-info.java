module org.uees {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.uees.controller to javafx.fxml;
    opens org.uees.view to javafx.fxml;
    opens org.uees.model.TasksModels to javafx.fxml;
    exports org.uees.controller;
    exports org.uees.view;
    exports org.uees.model.TasksModels;
}
