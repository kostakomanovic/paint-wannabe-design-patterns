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
		this.hexagon.setBorderColor(color);
		this.hexagon.setAreaColor(fillColor);
	}

	@Override
	public void fill(Graphics g) {
		this.hexagon.paint(g);
	}

	@Override
	public void draw(Graphics g) {
		this.hexagon.paint(g);
	}

	@Override
	public void select(Graphics g) {
		this.hexagon.paint(g);
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

}
