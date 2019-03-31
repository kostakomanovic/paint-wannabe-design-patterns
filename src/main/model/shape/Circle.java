package main.model.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import main.model.shape.base.ArealShape;
import main.model.shape.base.Moveable;

public class Circle extends ArealShape implements Moveable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8902684168259121145L;
	
	private Point center;
	private int radius;

	public Circle() {

	}

	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}

	public Circle(Point center, int radius, Color color, Color fillColor) {
		this(center, radius);
		this.setColor(color);
		this.setFillColor(fillColor);
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(this.getFillColor());
		g.fillOval(this.getCenter().getX() - this.getRadius() + 1, this.getCenter().getY() - this.getRadius() + 1,
				this.getRadius() * 2 - 2, this.getRadius() * 2 - 2);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(this.getColor());
		g.drawOval(this.getCenter().getX() - this.getRadius(), this.getCenter().getY() - this.getRadius(),  2 * this.getRadius(), 2 * this.getRadius());
		this.fill(g);
		if(this.isSelected())this.select(g);
	}

	@Override
	public void select(Graphics g) {
		new Point(this.getCenter().getX(), this.getCenter().getY() - this.getRadius()).select(g);
		new Point(this.getCenter().getX(), this.getCenter().getY() + this.getRadius()).select(g);
		new Point(this.getCenter().getX(), this.getCenter().getY()).select(g);
		new Point(this.getCenter().getX() - this.getRadius(), this.getCenter().getY()).select(g);
		new Point(this.getCenter().getX() + this.getRadius(), this.getCenter().getY()).select(g);
		
	}

	@Override
	public boolean contains(int x, int y) {
		return this.getCenter().distance(new Point(x, y)) < this.getRadius();
	}

	@Override
	public Circle clone() {
		Circle circle = new Circle(this.getCenter().clone(), this.getRadius(), this.getColor(), this.getFillColor());
		circle.setSelected(this.isSelected());
		return circle;
	}
	
	public void moveTo(int x, int y) {
		this.getCenter().setX(x);
		this.getCenter().setY(y);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public void moveFor(int x, int y) {
		this.getCenter().setX(this.getCenter().getX() + x);
		this.getCenter().setY(this.getCenter().getY() + y);
	}
	
	@Override
	public String toString() {
		// circle:origin:radius:rgb:fillRgb
		return "circle:" + this.center + ":" + this.radius + ":" + this.getColor().getRed() + "," + this.getColor().getGreen() + "," + this.getColor().getBlue() + ":" + this.getFillColor().getRed() + "," + this.getFillColor().getGreen() + "," + this.getFillColor().getBlue();
	}

}
