package net.urtzi.examen.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import net.urtzi.examen.databasemanager.DBManager;
import net.urtzi.examen.models.Comida;
import net.urtzi.examen.models.SerializableImage;

/**
 * Controlador principal de la aplicacion principal.
 */

public class ProductoController implements javafx.fxml.Initializable {

	@FXML
    private ImageView imagenSeleccionada;
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
    private ObservableList<Comida> data;
    private static File imagenArchivo;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gestor = new DBManager();
		prepareTableForDBItems();
		data = gestor.cargarComida();
		tablaComida.setItems(data);
	}
	

    @FXML
    void actualizarComida(ActionEvent event) {

    }

    @FXML
    void crearComida(ActionEvent event) throws IOException {
    	System.out.println("Creando comida");
    	String codigo = textfCodigo.getText();
    	String nombre = txtfNombre.getText();
    	double precio = Double.parseDouble(txtfPrecio.getText());
    	boolean disponible = checkboxDispo.isSelected();
    	SerializableImage img;
    	if (imagenArchivo!=null)
    		img = new SerializableImage(new FileInputStream(imagenArchivo));
    	else 
    		img = null;
    	
    	gestor.addProducto(new Comida(codigo,nombre,precio,disponible,img));
    	tablaComida.setItems(data=gestor.cargarComida());
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
    	imagenArchivo = new FileChooser().showOpenDialog(null);
    	try (FileInputStream img =new FileInputStream(imagenArchivo)) {    		
    		imagenSeleccionada.setImage(new Image(img));
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    private void prepareTableForDBItems() {
		for (TableColumn<Comida, ?> tc : tablaComida.getColumns()) {
			if (tc.getText().toLowerCase().equals("codigo") || tc.getText().toLowerCase().equals("nombre") || tc.getText().toLowerCase().equals("precio"))
				tc.setCellValueFactory(new PropertyValueFactory<>(tc.getText().toLowerCase()));
			else
				tc.setCellValueFactory(new PropertyValueFactory<>("disponible"));
		}
	}


}
