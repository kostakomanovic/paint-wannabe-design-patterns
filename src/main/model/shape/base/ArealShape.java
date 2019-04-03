package main.model.shape.base;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class ArealShape extends Shape implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6699961847455429560L;
	
	protected Color fillColor = Color.WHITE;
	
	public abstract void fill(Graphics g);

	public ArealShape() {
	}

	public ArealShape(Color fillColor) {
		this.fillColor = fillColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

}
