package net.urtzi.examen.models;

import java.io.InputStream;
import java.io.Serializable;

import javafx.scene.image.Image;

public class SerializableImage extends Image implements Serializable {
	public SerializableImage(InputStream arg0) {
		super(arg0);
	}
}
