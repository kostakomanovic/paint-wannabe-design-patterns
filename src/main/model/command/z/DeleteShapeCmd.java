package main.model.command.z;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.shape.base.Shape;

public class DeleteShapeCmd implements Command {

	private List<Shape> deletedShapes = new ArrayList<>();
	private ShapesModel model;

	public DeleteShapeCmd(ShapesModel model) {
		this.model = model;
	}

	@Override
	public void execute() {
		Iterator<Shape> iterator = this.model.getShapes().iterator();

		while (iterator.hasNext()) {
			Shape shape = iterator.next();
			if (shape.isSelected()) {
				iterator.remove();
				this.deletedShapes.add(shape);
			}
		}
	}

	@Override
	public void unexecute() {
		this.deletedShapes.stream().forEach(shape -> {
			this.model.getShapes().add(shape);
		});
	}

	@Override
	public String toString() {
		// command,shape1_shape2...
		String shapes = "";
		for (Shape shape : this.deletedShapes) {
			shapes += shape.toString() + "_";
		}
		return "delete," + shapes;
	}

}
