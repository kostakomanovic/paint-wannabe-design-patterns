package main.model.shape.base;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6941301878779242233L;
	
	protected Color color;
	protected boolean selected = false;

	public Shape() {
	}

	public Shape(Color color) {
		this.color = color;
	}

	public abstract void draw(Graphics g);

	public abstract void select(Graphics g);

	public abstract boolean contains(int x, int y);

	public abstract Shape clone();

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return "Shape [color=" + color + ", selected=" + selected + "]";
	}

}
