package main.model.shape.base;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape implements Cloneable {

	private Color color;
	private boolean selected;

	public Shape() {
	}

	public Shape(Color color, boolean selected) {
		this.color = color;
		this.selected = selected;
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
	
	

}
