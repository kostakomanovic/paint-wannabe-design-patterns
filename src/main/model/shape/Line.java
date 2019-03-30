package main.model.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import main.model.shape.base.Moveable;
import main.model.shape.base.Shape;

public class Line extends Shape implements Moveable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2044721333072167557L;
	
	private Point startingPoint;
	private Point endingPoint;

	public Line() {

	}

	public Line(Point startingPoint, Point endingPoint) {
		this.startingPoint = startingPoint;
		this.endingPoint = endingPoint;
	}

	public Line(Point startingPoint, Point endingPoint, Color color) {
		this(startingPoint, endingPoint);
		super.setColor(color);
	}

	public double length() {
		return this.getStartingPoint().distance(this.getEndingPoint());
	}

	public Point getMiddlePoint() {
		Point point = new Point();
		point.setX((this.getStartingPoint().getX() + this.getEndingPoint().getX()) / 2);
		point.setY((this.getStartingPoint().getY() + this.getEndingPoint().getY()) / 2);
		return point;
	}

	public Point getStartingPoint() {
		return startingPoint;
	}

	public void setStartingPoint(Point startingPoint) {
		this.startingPoint = startingPoint;
	}

	public Point getEndingPoint() {
		return endingPoint;
	}

	public void setEndingPoint(Point endingPoint) {
		this.endingPoint = endingPoint;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(this.getColor());
		g.drawLine(this.getStartingPoint().getX(), this.getStartingPoint().getY(), this.getEndingPoint().getX(),
				this.getEndingPoint().getY());
		if(this.isSelected()) this.select(g);
	}

	@Override
	public void select(Graphics g) {
		g.setColor(Color.BLUE);
		this.getStartingPoint().select(g);
		this.getEndingPoint().select(g);
		this.getMiddlePoint().select(g);
	}

	@Override
	public boolean contains(int x, int y) {
		Point clickPoint = new Point(x, y);
		double length = this.getStartingPoint().distance(clickPoint) + this.getEndingPoint().distance(clickPoint);
		return length - this.length() < 1;
	}
	
	@Override
	public Line clone() {
		Point startingPoint = this.getStartingPoint().clone();
		Point endingPoint = this.getEndingPoint().clone();
		Line line = new Line(startingPoint, endingPoint, this.getColor());
		line.setSelected(this.isSelected());
		return line;
	}

	@Override
	public void moveTo(int x, int y) {
		this.getStartingPoint().moveTo(x, y);
		this.getEndingPoint().moveTo(x - this.getStartingPoint().getX(),  y - this.getStartingPoint().getY());
	}
	
	public void moveBothPoints(int x1, int y1, int x2, int y2) {
		this.getStartingPoint().moveTo(x1, y1);
		this.getEndingPoint().moveTo(x2, y2);
	}

	@Override
	public void moveFor(int x, int y) {
		this.getStartingPoint().moveFor(x,  y);
		this.getEndingPoint().moveFor(x, y);
	}
	
	@Override
	public String toString() {
		// line,point1-point2
		return "line," + this.startingPoint + "-" + this.endingPoint;
	}

}
