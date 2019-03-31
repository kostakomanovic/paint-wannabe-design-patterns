package main.model.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import hexagon.Hexagon;
import main.model.shape.base.ArealShape;
import main.model.shape.base.Moveable;

public class HexagonAdapter extends ArealShape implements Moveable, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5270168233786654833L;
	
	private Hexagon hexagon;

	public HexagonAdapter(Point center, int side, Color color, Color fillColor) {
		this.hexagon = new Hexagon(center.getX(), center.getY(), side);
		this.setColor(color);
		this.setFillColor(fillColor);
	}

	@Override
	public void fill(Graphics g) {
	}

	@Override
	public void draw(Graphics g) {
		this.hexagon.paint(g);
	}

	@Override
	public void select(Graphics g) {
	}

	@Override
	public boolean contains(int x, int y) {
		return this.hexagon.doesContain(x, y);
	}

	@Override
	public HexagonAdapter clone() {
		HexagonAdapter hAdapter = new HexagonAdapter(new Point(this.getHexagon().getX(), this.getHexagon().getY()),
				this.getHexagon().getR(), this.getHexagon().getAreaColor(), this.getHexagon().getBorderColor());
		hAdapter.setSelected(this.isSelected());
		return hAdapter;
	}

	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	public void setColor(Color color) {
		this.hexagon.setBorderColor(color);
		super.setColor(color);
	}
	
	public Color getColor() {
		return super.getColor();
	}
	
	public void setFillColor(Color color) {
		this.hexagon.setAreaColor(color);
		super.setFillColor(color);;
	}
	
	public Color getFillColor(Color color) {
		return super.getFillColor();
	}

	public void setSelected(boolean selected) {
		this.hexagon.setSelected(selected);
		this.selected = selected;
	}

	
	@Override
	public void moveTo(int x, int y) {
		this.hexagon.setX(x);
		this.hexagon.setY(y);
	}

	@Override
	public void moveFor(int x, int y) {
		this.hexagon.setX(this.hexagon.getX() + x);
		this.hexagon.setY(this.hexagon.getY() + y);
	}

	@Override
	public String toString() {
		// hexagon:center:radius:r,g,b:fillrgb
		return "hexagon:" + new Point(this.getHexagon().getX(), this.getHexagon().getY()) + ":" + this.getHexagon().getR() + ":" + this.getColor().getRed() + "," + this.getColor().getGreen() + "," + this.getColor().getBlue() + ":" + this.getFillColor().getRed() + "," + this.getFillColor().getGreen() + "," + this.getFillColor().getBlue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			Hexagon hexaFromObj = ((HexagonAdapter) obj).getHexagon();
			return hexagon.getX() == hexaFromObj.getX() && hexagon.getY() == hexaFromObj.getY()
					&& hexagon.getR() == hexaFromObj.getR();
		}
		return false;
	}
}
