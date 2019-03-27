package main.model.command.add;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.shape.base.Shape;

public class AddShapeCmd implements Command {
	
	private Shape shape;
	private ShapesModel model;
	
	public AddShapeCmd(Shape shape, ShapesModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		this.model.getShapes().add(this.shape);
	}

	@Override
	public void unexecute() {
		this.model.getShapes().remove(this.shape);
	}

	@Override
	public String toString() {
		return "AddShapeCmd [shape=" + shape + "]";
	}
	
	

}
