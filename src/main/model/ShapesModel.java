package main.model;

import java.util.ArrayList;
import java.util.List;

import main.model.shape.base.Shape;

public class ShapesModel {
	
	private List<Shape> shapes = new ArrayList<>();

	public List<Shape> getShapes() {
		return shapes;
	}

	public void setShapes(List<Shape> shapes) {
		this.shapes = shapes;
	}
}
