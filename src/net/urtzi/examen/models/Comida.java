package net.urtzi.examen.models;

import javafx.scene.image.Image;

public class Comida {
	private String codigo;
	private String nombre;
	private double precio;
	private boolean disponible;
	private Image img;
	
	public String getCodigo() {
		return codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public double getPrecio() {
		return precio;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public Image getImg() {
		return img;
	}
	public Comida(String codigo, String nombre, double precio, boolean disponible) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
		this.disponible = disponible;
	}
	public Comida(String codigo, String nombre, double precio, boolean disponible, Image img) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
		this.disponible = disponible;
		this.img = img;
	}
}
