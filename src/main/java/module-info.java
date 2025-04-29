/*
module com.example.cs213project4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;

    opens com.example.cs213project4 to javafx.fxml;
    opens com.example.cs213project4.controller to javafx.fxml;

    exports com.example.cs213project4.model;
}
*/

module com.example.cs213project4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    // Open the package containing your Application subclass for reflection by JavaFX:
    opens com.example.cs213project4.controller to javafx.graphics, javafx.fxml;
    opens com.example.cs213project4.model to javafx.fxml;

    // Export your packages as needed.
    exports com.example.cs213project4.controller;
    exports com.example.cs213project4.model;
}
