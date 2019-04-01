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
		this.originalState = this.oldState.clone();
		if (this.fromLog) {
			for (Shape s : this.model.getShapes()) {
				if (s.equals(this.oldState)) {
					Rectangle r = (Rectangle) s;
					this.oldState = r;
					r.moveTo(this.newState.getOrigin().getX(), this.newState.getOrigin().getY());
					r.setWidth(this.newState.getWidth());
					r.setHeight(this.newState.getHeight());
					r.setColor(this.newState.getColor());
					r.setFillColor(this.newState.getFillColor());
				}
			}
		} else {
			this.oldState.moveTo(this.newState.getOrigin().getX(), this.newState.getOrigin().getY());
			this.oldState.setWidth(this.newState.getWidth());
			this.oldState.setHeight(this.newState.getHeight());
			this.oldState.setColor(this.newState.getColor());
			this.oldState.setFillColor(this.newState.getFillColor());
		}
	}

	@Override
	public void unexecute() {
		this.oldState.moveTo(this.originalState.getOrigin().getX(), this.originalState.getOrigin().getY());
		this.oldState.setWidth(this.originalState.getWidth());
		this.oldState.setHeight(this.originalState.getHeight());
		this.oldState.setColor(this.originalState.getColor());
		this.oldState.setFillColor(this.originalState.getFillColor());
	}
	
	@Override
	public String toString() {
		// edit,rectangle|oldRectangle_newRectangle
		return "edit,rectangle|" + this.originalState + "_" + this.newState;
	}

	
}
