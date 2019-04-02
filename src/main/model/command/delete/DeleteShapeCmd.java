package main.model.command.delete;

import java.util.List;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.shape.base.Shape;

public class DeleteShapeCmd implements Command {

	private List<Shape> deletedShapes;
	private ShapesModel model;
	private boolean fromLog;
	
	public DeleteShapeCmd(List<Shape> deletedShapes, ShapesModel model, boolean fromLog) {
		this.model = model;
		this.deletedShapes = deletedShapes;
	}

	@Override
	public void execute() {
		if(this.fromLog) {			
			for(Shape deleted : this.deletedShapes) {
				for(Shape shape : this.model.getShapes()) {
					if(deleted.equals(shape)) {
						deleted = shape;
					}
				}
			}
		}
		for (Shape shape : this.deletedShapes) {
			this.model.getShapes().remove(shape);
		}
	}

	@Override
	public void unexecute() {
		for (Shape shape : this.deletedShapes) {
			this.model.getShapes().add(shape);
		}
	}

	@Override
	public String toString() {
		// command,shape1_shape2...
		String shapes = "";
		for (int i = 0; i < this.deletedShapes.size(); i++) {
			Shape shape = this.deletedShapes.get(i);
			if(i == this.deletedShapes.size() - 1) {
				shapes += shape.toString();
			} else {
				shapes += shape.toString() + "_";
			}
		}
		return "delete|" + shapes;
	}

}
