module pls_no_shinobu.videostore {
    requires javafx.controls;
    requires javafx.fxml;

    opens pls_no_shinobu.videostore to
            javafx.fxml;

    exports pls_no_shinobu.videostore;
}
