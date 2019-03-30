package main.model.command.z;

import java.util.Collections;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.shape.base.Shape;

public class ToFrontCmd implements Command {

	private Shape shape;
	private ShapesModel model;

	public ToFrontCmd(Shape shape, ShapesModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		int shapeIndex = this.model.getShapes().indexOf(this.shape);
		if (shapeIndex != -1 && shapeIndex < (this.model.getShapes().size() - 1)) {
			Collections.swap(this.model.getShapes(), shapeIndex, shapeIndex + 1);
		}
	}

	@Override
	public void unexecute() {
		int shapeIndex = this.model.getShapes().indexOf(this.shape);
		if (shapeIndex > 0) {
			Collections.swap(this.model.getShapes(), shapeIndex, shapeIndex - 1);
		}
	}

	@Override
	public String toString() {
		// command,shape
		return "toftont," + this.shape;
	}

}
