package main.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import main.model.ShapesModel;

public class Canvas extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6342879691720539190L;
	
	private ShapesModel model;
	
	public Canvas() {
		this.setBackground(Color.white);
	}

	public ShapesModel getModel() {
		return model;
	}

	public void setModel(ShapesModel model) {
		this.model = model;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if(this.model != null) {
			this.model.getShapes().stream().forEach(shape -> shape.draw(g));
		}
	}
}
