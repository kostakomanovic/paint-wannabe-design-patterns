package main.model.command.z;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.shape.base.Shape;

public class BringToFrontCmd implements Command {
	
	private Shape shape;
	private ShapesModel model;
	private int currentIndex = -1;
	
	

	public BringToFrontCmd(ShapesModel model, int currentIndex) {
		this.model = model;
		this.currentIndex = currentIndex;
		this.shape = this.model.getShapes().get(this.currentIndex);
	}

	@Override
	public void execute() {
		this.model.getShapes().remove(this.shape);
		this.model.getShapes().add(this.shape);
	}

	@Override
	public void unexecute() {
		this.model.getShapes().remove(this.shape);
		this.model.getShapes().add(this.currentIndex, this.shape);
	}

	@Override
	public String toString() {
		// command,shape
		return "bringtofront|" + this.shape;
	}
}
