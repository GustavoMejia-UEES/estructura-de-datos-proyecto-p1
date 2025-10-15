module org.uees {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.uees to javafx.fxml;
    exports org.uees;
}
