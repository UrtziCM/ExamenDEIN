package net.urtzi.examen.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.PopupWindow.AnchorLocation;
import javafx.stage.Stage;
import net.urtzi.examen.databasemanager.DBManager;
import net.urtzi.examen.models.*;

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
    void actualizarComida(ActionEvent event) throws IOException {
    	String codigo = textfCodigo.getText();
    	String nombre = txtfNombre.getText();
    	double precio = Double.parseDouble(txtfPrecio.getText());
    	boolean disponible = checkboxDispo.isSelected();
    	LocatedImage img;
    	if (imagenArchivo!=null)
    		img = new LocatedImage(imagenArchivo.getAbsolutePath());
    	else 
    		img = null;
    	gestor.actualizarProducto(new Comida(codigo,nombre,precio,disponible,img));
    	tablaComida.setItems(data=gestor.cargarComida());
    }

    @FXML
    void crearComida(ActionEvent event) throws IOException {
    	String codigo = textfCodigo.getText();
    	String nombre = txtfNombre.getText();
    	double precio = Double.parseDouble(txtfPrecio.getText());
    	boolean disponible = checkboxDispo.isSelected();
    	LocatedImage img;
    	if (imagenArchivo!=null)
    		img = new LocatedImage(imagenArchivo.getPath().toString());
    	else 
    		img = null;
    	gestor.addProducto(new Comida(codigo,nombre,precio,disponible,img));
    	tablaComida.setItems(data=gestor.cargarComida());
    }

    @FXML
    void limpiarTextfields(ActionEvent event) {
    	textfCodigo.setDisable(false);
    	textfCodigo.setText("");
    	txtfNombre.setText("");
    	txtfPrecio.setText("");
    	checkboxDispo.setSelected(false);
    	btnActualizar.setDisable(true);
    	btnCrear.setDisable(false);
    	imagenSeleccionada.setImage(null);
    }

    @FXML
    void onAboutClicked(ActionEvent event) {
    	mostrarVentanaEmergente("INFO", "GEstion de productos V0.1\n Autor: Urtzi", AlertType.INFORMATION);
    }

    @FXML
    void onTableClicked(MouseEvent event) throws SQLException {
    	if (event.getButton() == MouseButton.PRIMARY && tablaComida.getSelectionModel().getSelectedItem() != null) {
    		Comida c = tablaComida.getSelectionModel().getSelectedItem();
    		c = gestor.getProductoByID(c.getCodigo());
    		textfCodigo.setText(c.getCodigo());
    		textfCodigo.setDisable(true);
    		btnCrear.setDisable(true);
    		btnActualizar.setDisable(false);
    		txtfNombre.setText(c.getNombre());
    		txtfPrecio.setText(c.getPrecio()+"");
    		checkboxDispo.setSelected(c.isDisponible());
    		imagenSeleccionada.setImage(c.getImg());
    	} else if (event.getButton() == MouseButton.SECONDARY && tablaComida.getSelectionModel().getSelectedItem() != null) {
    		ContextMenu menu = new ContextMenu();
    		MenuItem borrar = new MenuItem("Borrar");
    		MenuItem imagen = new MenuItem("Imagen");
    		imagen.setOnAction(e -> {
				try {
					verImagen();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			});
    		borrar.setOnAction(e -> {
				try {
					gestor.borrarProducto(tablaComida.getSelectionModel().getSelectedItem());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
    		menu.getItems().add(borrar);
    		menu.getItems().add(imagen);
    		menu.setX(event.getScreenX());
    		menu.setY(event.getScreenY());
    		menu.show(tablaComida.getScene().getWindow());
    		
    	}
    	
    }
    
    private void verImagen() throws SQLException {
    	Comida c = tablaComida.getSelectionModel().getSelectedItem();
		c = gestor.getProductoByID(c.getCodigo());
		mostrarVentanaImagen(c.getImg());
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
	

	private static void mostrarVentanaEmergente(String titulo, String content, AlertType tipo) {
		Alert anadidoAnimal = new Alert(tipo);
		anadidoAnimal.setTitle(titulo);
		anadidoAnimal.setHeaderText(null);
		anadidoAnimal.setContentText(content);
		anadidoAnimal.showAndWait();
	}
	private static void mostrarVentanaImagen(Image img) {
		if (img == null) return;
		BorderPane bpImagen = new BorderPane();
		bpImagen.getChildren().add(new ImageView(img));
		Scene scn = new Scene(bpImagen);
		Stage stg = new Stage();
		stg.setScene(scn);
		stg.showAndWait();
	}


}
