package main.model.shape.base;

import java.awt.Color;
import java.awt.Graphics;

public abstract class ArealShape extends Shape {

	private Color fillColor = Color.WHITE;
	
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
