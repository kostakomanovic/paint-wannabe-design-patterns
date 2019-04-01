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
		this.originalState = this.oldState.clone();
		if (this.fromLog) {
			for (Shape s : this.model.getShapes()) {
				if (s.equals(this.oldState)) {
					Circle circle = (Circle) s;
					this.oldState = circle;
					circle.moveTo(this.newState.getCenter().getX(), this.newState.getCenter().getY());
					circle.setRadius(this.newState.getRadius());
					circle.setColor(this.newState.getColor());
					circle.setFillColor(this.newState.getFillColor());
				}
			}
		} else {
			this.oldState.moveTo(this.newState.getCenter().getX(), this.newState.getCenter().getY());
			this.oldState.setRadius(this.newState.getRadius());
			this.oldState.setColor(this.newState.getColor());
			this.oldState.setFillColor(this.newState.getFillColor());
		}
	}

	@Override
	public void unexecute() {
		this.oldState.moveTo(this.originalState.getCenter().getX(), this.originalState.getCenter().getY());
		this.oldState.setRadius(this.originalState.getRadius());
		this.oldState.setColor(this.originalState.getColor());
		this.oldState.setFillColor(this.originalState.getFillColor());
	}
	
	@Override
	public String toString() {
		// edit,circle|oldCircle_newCircle
		return "edit,circle|" + this.originalState + "_" + this.newState;
	}


}
