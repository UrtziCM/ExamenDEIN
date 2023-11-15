package net.urtzi.examen.databasemanager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.urtzi.examen.models.*;

/**
 * Database manager class uses a ConnectionDB to access a database and retrieve
 * data.
 * 
 * @see net.urtzi.olimpiadas.controller.database.ConnectionDB
 */
public class DBManager {
	private ConnectionDB conexion;

	/**
	 * Retrieves the data from the Deporte table in the database and returns an
	 * ObservableList with it.
	 * 
	 * @return ObservableList with the Deporte objects from the database.
	 * @see net.urtzi.examen.models.Deporte
	 */
	public ObservableList<Comida> cargarComida() {
		ObservableList<Comida> deportes = FXCollections.observableArrayList();
		try {
			conexion = new ConnectionDB();
			String consulta = "SELECT * FROM Receta";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String codigo = rs.getString("id_deporte");
				String nombre = rs.getString("nombre");
				double precio= rs.getDouble("precio");
				boolean disponible =(rs.getInt("disponible")==1)?true:false;
				SerializableImage imagen = new SerializableImage(rs.getBinaryStream("imagen"));
				Comida d = new Comida(codigo,nombre,precio,disponible,imagen);
				deportes.add(d);
			}

			rs.close();
			conexion.closeConexion();

		} catch (SQLException e) {
			System.out.println("Error en la carga de deportes desde la base de datos");
		}
		return deportes;
	}


}