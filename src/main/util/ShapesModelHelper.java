package main.util;

import java.util.Iterator;
import java.util.List;

import main.model.shape.base.Shape;

public class ShapesModelHelper {

	public static Shape getSelectedShape(List<Shape> shapes) {
		Iterator<Shape> iterator = shapes.iterator();

		while (iterator.hasNext()) {
			Shape shape = iterator.next();
			if (shape.isSelected())
				return shape;
		}

		return null;
	}

	public static List<Shape> deselectAll(List<Shape> shapes) {
		Iterator<Shape> iterator = shapes.iterator();

		while (iterator.hasNext()) {
			Shape shape = iterator.next();
			shape.setSelected(false);
		}
		
		return shapes;
	}

}
