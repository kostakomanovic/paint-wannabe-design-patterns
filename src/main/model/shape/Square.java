package main.model.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import main.model.shape.base.ArealShape;
import main.model.shape.base.Moveable;

public class Square extends ArealShape implements Moveable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4013006916658557219L;
	
	protected Point origin;
	private int width;

	public Square() {
	}

	public Square(Point origin, int width) {
		this.origin = origin;
		this.width = width;
	}

	public Square(Point origin, int width, Color color, Color fillColor) {
		this.origin = origin;
		this.width = width;
		super.setColor(color);
		super.setFillColor(fillColor);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(this.getColor());
		g.drawRect(this.getOrigin().getX(), this.getOrigin().getY(), this.getWidth(), this.getWidth());
		this.fill(g);
		if(this.isSelected()) this.select(g);
	}

	@Override
	public void select(Graphics g) {
		new Line(this.getOrigin(), new Point(this.getOrigin().getX() + this.getWidth(), this.getOrigin().getY()))
				.select(g);
		new Line(this.getOrigin(), new Point(this.getOrigin().getX(), this.getOrigin().getY() + this.getWidth()))
				.select(g);
		;
		new Line(new Point(this.getOrigin().getX(), this.getOrigin().getY() + this.getWidth()),
				this.getDiagonal().getEndingPoint()).select(g);
		;
		new Line(new Point(this.getOrigin().getX() + this.getWidth(), this.getOrigin().getY()),
				this.getDiagonal().getEndingPoint()).select(g);
		;

	}

	@Override
	public boolean contains(int x, int y) {
		return x > this.getOrigin().getX() && x < this.getOrigin().getX() + this.getWidth()
				&& y > this.getOrigin().getY() && y < this.getOrigin().getY() + this.getWidth();
	}

	@Override
	public Square clone() {
		Square square = new Square(this.getOrigin().clone(), this.getWidth(), this.getColor(), this.getFillColor());
		square.setSelected(this.isSelected());
		return square;
	}

	@Override
	public void moveTo(int x, int y) {
		this.getOrigin().moveTo(x, y);
	}

	public Line getDiagonal() {
		Line diagonal = new Line();
		diagonal.setStartingPoint(this.getOrigin());
		diagonal.setEndingPoint(
				new Point(this.getOrigin().getX() + this.getWidth(), this.getOrigin().getY() + this.getWidth()));
		return diagonal;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(this.getFillColor());
		g.fillRect(this.getOrigin().getX() + 1, this.getOrigin().getY() + 1, this.getWidth() - 2, this.getWidth() - 2);
	}

	@Override
	public void moveFor(int x, int y) {
		this.getOrigin().moveFor(x, y);
	}
	
	@Override
	public String toString() {
		// square: origin : width : r,g,b : r,g,b
		return "square:" + this.origin + ":" + this.width + ":" + this.getColor().getRed() + "," + this.getColor().getGreen() + "," + this.getColor().getBlue() + ":" + this.getFillColor().getRed() + "," + this.getFillColor().getGreen() + "," + this.getFillColor().getBlue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Square) {
			Square castedObj = (Square) obj;
			return origin.equals(castedObj.getOrigin()) && width == castedObj.getWidth();
		}
		return false;
	}

}
