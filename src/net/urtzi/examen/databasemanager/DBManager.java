package net.urtzi.examen.databasemanager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

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
		ObservableList<Comida> comidas = FXCollections.observableArrayList();
		try {
			conexion = new ConnectionDB();
			String consulta = "SELECT * FROM productos";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String codigo = rs.getString("codigo");
				String nombre = rs.getString("nombre");
				double precio= rs.getDouble("precio");
				boolean disponible =(rs.getInt("disponible")==1)?true:false;
				InputStream imagenStream = rs.getBinaryStream("imagen");
				SerializableImage imagen;
				if (imagenStream != null) {					
					imagen = new SerializableImage(imagenStream);
				}else {					
					imagen = null;
				}
				Comida c = new Comida(codigo,nombre,precio,disponible,imagen);
				comidas.add(c);
			}

			rs.close();
			conexion.closeConexion();

		} catch (SQLException e) {
			System.out.println("Error en la carga de productos desde la base de datos");
			e.printStackTrace();
		}
		return comidas;
	}
	
	public boolean addProducto(Comida comida) throws IOException {
		try {
			conexion = new ConnectionDB();
			String sqlAddEquipo;
			sqlAddEquipo = "INSERT INTO productos VALUES(?,?,?,?,?)";
			PreparedStatement pstm = conexion.getConexion().prepareStatement(sqlAddEquipo);
			pstm.setString(1, comida.getCodigo());
			pstm.setString(2, comida.getNombre());
			pstm.setDouble(3, comida.getPrecio());
			int disponible=(comida.isDisponible())?1:0;
			pstm.setInt(4, disponible);
			if (comida.getImg() != null)
				pstm.setObject(5, comida.getImg());
			else
				pstm.setNull(5, Types.BLOB);
			pstm.executeUpdate();
			conexion.closeConexion();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	


}