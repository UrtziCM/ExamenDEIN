package net.urtzi.examen.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import net.urtzi.examen.databasemanager.DBManager;
import net.urtzi.examen.models.Comida;

/**
 * Controlador principal de la aplicacion principal.
 */

public class ProductoController implements javafx.fxml.Initializable {

	@FXML
    private Button btnActualizar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnImage;

    @FXML
    private Button btnLimpiar;

    @FXML
    private CheckBox checkboxDispo;

    @FXML
    private TableView<Comida> tablaComida;

    @FXML
    private TextField textfCodigo;

    @FXML
    private TextField txtfNombre;

    @FXML
    private TextField txtfPrecio;
    private DBManager gestor;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gestor = new DBManager();
		
	}
	

    @FXML
    void actualizarComida(ActionEvent event) {

    }

    @FXML
    void crearComida(ActionEvent event) {

    }

    @FXML
    void limpiarTextfields(ActionEvent event) {

    }

    @FXML
    void onAboutClicked(ActionEvent event) {

    }

    @FXML
    void onTableClicked(MouseEvent event) {

    }

    @FXML
    void seleccionarImagen(ActionEvent event) {

    }


}
