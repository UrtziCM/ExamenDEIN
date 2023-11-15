module ExamenDein {
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.controls;
	requires java.sql;
	
	opens net.urtzi.examen.application to javafx.controls, javafx.base, javafx.fxml, javafx.graphics;
	opens net.urtzi.examen.controller to javafx.controls, javafx.base, javafx.fxml, javafx.graphics;
	opens net.urtzi.examen.models to javafx.base;
}