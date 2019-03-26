package main.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;

import main.model.ShapesModel;
import main.model.shape.Point;
import main.view.Paint;

public class PaintController {

	private ShapesModel model;
	private Paint paint;

	public PaintController(ShapesModel model, Paint paint) {
		this.model = model;
		this.paint = paint;
	}
	
	public void handleMouseClick(MouseEvent e) {
		Point point = new Point(e.getX(), e.getY(), Color.BLACK);
		this.model.getShapes().add(point);
		this.paint.getCanvas().repaint();
	}

}
