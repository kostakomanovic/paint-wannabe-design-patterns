package main.model.command.edit;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.shape.Circle;
import main.model.shape.base.Shape;

public class EditCircleCmd implements Command {

	private Circle oldState;
	private Circle newState;
	private Circle originalState;
	private ShapesModel model;
	private boolean fromLog;

	public EditCircleCmd(Circle oldState, Circle newState, ShapesModel model, boolean fromLog) {
		this.oldState = oldState;
		this.newState = newState;
		this.model = model;
		this.fromLog = fromLog;
	}

	@Override
	public void execute() {
		originalState = oldState.clone();
		if (this.fromLog) {
			for (Shape s : this.model.getShapes()) {
				if (s.equals(this.oldState)) {
					Circle circle = (Circle) s;
					this.oldState = circle;
					circle.moveTo(newState.getCenter().getX(), newState.getCenter().getY());
					circle.setRadius(newState.getRadius());
					circle.setColor(newState.getColor());
					circle.setFillColor(newState.getFillColor());
				}
			}
		} else {
			oldState.moveTo(newState.getCenter().getX(), newState.getCenter().getY());
			oldState.setRadius(newState.getRadius());
			oldState.setColor(newState.getColor());
			oldState.setFillColor(newState.getFillColor());
		}
	}

	@Override
	public void unexecute() {
		oldState.moveTo(originalState.getCenter().getX(), originalState.getCenter().getY());
		oldState.setRadius(originalState.getRadius());
		oldState.setColor(originalState.getColor());
		oldState.setFillColor(originalState.getFillColor());
	}
	
	@Override
	public String toString() {
		// edit,circle|oldCircle_newCircle
		return "edit,circle|" + this.originalState + "_" + this.newState;
	}


}
