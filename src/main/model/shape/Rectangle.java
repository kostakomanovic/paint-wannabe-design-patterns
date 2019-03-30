package main.model.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import main.model.shape.base.Moveable;

public class Rectangle extends Square implements Moveable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8466687855428681270L;

	private int height;

	public Rectangle() {

	}

	public Rectangle(Point origin, int width, int height) {
		this.setOrigin(origin);
		this.setWidth(width);
		this.setHeight(height);
	}

	public Rectangle(Point origin, int width, int height, Color color, Color fillColor) {
		this.setOrigin(origin);
		this.setWidth(width);
		this.setHeight(height);
		this.setColor(color);
		this.setFillColor(fillColor);
	}

	@Override
	public Rectangle clone() {
		Rectangle rectangle = new Rectangle(this.getOrigin().clone(), this.getWidth(), this.getHeight(),
				this.getColor(), this.getFillColor());
		rectangle.setSelected(this.isSelected());
		return rectangle;
	}

	public Line diagonal() {
		Line diagonal = new Line();
		diagonal.setStartingPoint(this.getOrigin());
		diagonal.setEndingPoint(
				new Point(this.getOrigin().getX() + this.getWidth(), this.getOrigin().getY() + this.getHeight()));
		return diagonal;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(this.getFillColor());
		g.fillRect(this.getOrigin().getX() + 1, this.getOrigin().getY() + 1, this.getWidth() - 2, this.getHeight() - 2);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(this.getColor());
		g.drawRect(this.getOrigin().getX(), this.getOrigin().getY(), this.getWidth(), this.getHeight());
		this.fill(g);
		if (this.isSelected())
			this.select(g);
	}

	@Override
	public boolean contains(int x, int y) {
		return x > this.getOrigin().getX() && x < this.getOrigin().getX() + this.getWidth()
				&& y > this.getOrigin().getY() && y < this.getOrigin().getY() + this.getHeight();
	}

	@Override
	public void select(Graphics g) {
		new Line(this.getOrigin(), new Point(this.getOrigin().getX() + this.getWidth(), this.getOrigin().getY())).select(g);
		new Line(this.getOrigin(), new Point(this.getOrigin().getX(), this.getOrigin().getY() + this.getHeight())).select(g);
		new Line(new Point(this.getOrigin().getX() + this.getWidth(), this.getOrigin().getY()), diagonal().getEndingPoint()).select(g);
		new Line(new Point(this.getOrigin().getX(), this.getOrigin().getY() + this.getHeight()), diagonal().getEndingPoint()).select(g);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
