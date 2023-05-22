module pls_no_shinobu.videostore {
    requires javafx.controls;
    requires javafx.fxml;

    opens pls_no_shinobu.videostore to
            javafx.fxml;
    opens pls_no_shinobu.videostore.model to
            javafx.base;

    exports pls_no_shinobu.videostore;
    exports pls_no_shinobu.videostore.controller;

    opens pls_no_shinobu.videostore.controller to
            javafx.fxml;
}
