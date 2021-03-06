package main.model.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import main.model.shape.base.Moveable;
import main.model.shape.base.Shape;


/**
 * @author sebamed
 *
 */
public class Point extends Shape implements Moveable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5770642039001983179L;
	
	private int x;
	private int y;

	public Point() {
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(int x, int y, Color color) {
		this(x, y);
		super.setColor(color);
	}
	
	public void moveTo(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	public double distance(Point p) {
		int dX = this.x - p.x;
		int dY = this.y - p.y;
		double d = Math.sqrt(dX*dX + dY*dY);
		return d;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(super.getColor());
		g.drawLine(this.getX() - 2, this.getY(), this.getX() + 2, this.getY());
		g.drawLine(this.getX(), this.getY() - 2, this.getX(), this.getY() + 2);
		if(this.isSelected()) this.select(g);
	}
	
	@Override
	public boolean contains(int x, int y) {
		return this.distance(new Point(x, y)) <  5;
	}
	
	@Override
	public void select(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(this.getX() - 3, this.getY() - 3, 6, 6);
	}
	
	@Override
	public Point clone() {
		return new Point(this.getX(), this.getY(), this.getColor());
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		// point, x, y, r, g, b
		int r = this.getColor() != null ? this.getColor().getRed() : 0;
		int g = this.getColor() != null ? this.getColor().getGreen() : 0;
		int b = this.getColor() != null ? this.getColor().getBlue() : 0;
		return "point," + this.x  + "," + this.y + "," + r + "," + g + "," + b;
	}

	@Override
	public void moveFor(int x, int y) {
		this.setX(this.getX() + x);
		this.setY(this.getY() + y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point castedObj = (Point) obj;
			return x == castedObj.getX() && y == castedObj.getY();
		}
		return false;
	}

}
