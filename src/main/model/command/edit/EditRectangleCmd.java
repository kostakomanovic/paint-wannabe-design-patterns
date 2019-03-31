package main.model.command.edit;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.shape.Rectangle;
import main.model.shape.base.Shape;

public class EditRectangleCmd implements Command{

	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle originalState;
	private ShapesModel model;
	private boolean fromLog;

	public EditRectangleCmd(Rectangle oldState, Rectangle newState, ShapesModel model, boolean fromLog) {
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
					Rectangle r = (Rectangle) s;
					this.oldState = r;
					r.moveTo(newState.getOrigin().getX(), newState.getOrigin().getY());
					r.setWidth(newState.getWidth());
					r.setHeight(newState.getHeight());
					r.setColor(newState.getColor());
					r.setFillColor(newState.getFillColor());
				}
			}
		} else {
			oldState.moveTo(newState.getOrigin().getX(), newState.getOrigin().getY());
			oldState.setWidth(newState.getWidth());
			oldState.setHeight(newState.getHeight());
			oldState.setColor(newState.getColor());
			oldState.setFillColor(newState.getFillColor());
		}
	}

	@Override
	public void unexecute() {
		oldState.moveTo(originalState.getOrigin().getX(), originalState.getOrigin().getY());
		oldState.setWidth(originalState.getWidth());
		oldState.setHeight(originalState.getHeight());
		oldState.setColor(originalState.getColor());
		oldState.setFillColor(originalState.getFillColor());
	}
	
	@Override
	public String toString() {
		// edit,rectangle|oldRectangle_newRectangle
		return "edit,rectangle|" + this.originalState + "_" + this.newState;
	}

	
}
