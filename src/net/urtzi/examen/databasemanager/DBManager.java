package net.urtzi.examen.databasemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
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
				Image imagen;
				if (imagenStream != null) {					
					imagen = new Image(imagenStream);
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
			if (comida.getImg() != null) {
				pstm.setBinaryStream(5, new FileInputStream(comida.getImg().getUrl()));
			}
			else
				pstm.setNull(5, Types.BLOB);
			pstm.executeUpdate();
			conexion.closeConexion();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	public Comida getProductoByID(String codigo) throws SQLException {
		conexion = new ConnectionDB();
		Comida equ = null;
		String sql = String.format("SELECT * FROM productos WHERE codigo = ?");
		PreparedStatement pstmt = conexion.getConexion().prepareStatement(sql);
		pstmt.setString(1, codigo);
		pstmt.executeQuery();
		ResultSet rs = pstmt.getResultSet();
		if (rs.next()) {
			if (rs.getBlob("imagen") != null) {				
				Image img = new Image(rs.getBlob("imagen").getBinaryStream());
				equ = new Comida(rs.getString("codigo"), rs.getString("nombre"), rs.getDouble("precio"), rs.getBoolean("disponible"),img);
			}
			else
				equ = new Comida(rs.getString("codigo"), rs.getString("nombre"), rs.getDouble("precio"), rs.getBoolean("disponible"));
		}
		conexion.closeConexion();
		return equ;
	}
	public boolean actualizarProducto(Comida comida) throws IOException {
		try {
			conexion = new ConnectionDB();
			String sqlAddEquipo;
			sqlAddEquipo = "REPLACE INTO productos VALUES(?,?,?,?,?)";
			PreparedStatement pstm = conexion.getConexion().prepareStatement(sqlAddEquipo);
			pstm.setString(1, comida.getCodigo());
			pstm.setString(2, comida.getNombre());
			pstm.setDouble(3, comida.getPrecio());
			int disponible=(comida.isDisponible())?1:0;
			pstm.setInt(4, disponible);
			if (comida.getImg() != null) {
				pstm.setBinaryStream(5, new FileInputStream(new File(comida.getImg().getUrl())));
			}
			else
				pstm.setNull(5, Types.BLOB);
			pstm.executeUpdate();
			conexion.closeConexion();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	public void borrarProducto(Comida comida) throws SQLException {
		conexion = new ConnectionDB();
		String sql = "DELETE FROM productos WHERE codigo=?";
		PreparedStatement stmt = conexion.getConexion().prepareStatement(sql);
		stmt.setString(1, comida.getCodigo());
		stmt.executeUpdate(sql);
		conexion.closeConexion();
	}
	

	
	


}