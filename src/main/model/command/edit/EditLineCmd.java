package main.model.command.edit;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.shape.Line;
import main.model.shape.base.Shape;

public class EditLineCmd implements Command {

	private Line oldState;
	private Line newState;
	private Line originalState;
	private ShapesModel model;
	private boolean fromLog;

	public EditLineCmd(Line oldState, Line newState, ShapesModel model, boolean fromLog) {
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
					Line l = (Line) s;
					this.oldState = l;
					l.moveBothPoints(this.newState.getStartingPoint().getX(), this.newState.getStartingPoint().getY(),
							this.newState.getEndingPoint().getX(), this.newState.getEndingPoint().getY());
					l.setColor(this.newState.getColor());
				}
			}
		} else {
			this.oldState.moveBothPoints(this.newState.getStartingPoint().getX(), this.newState.getStartingPoint().getY(),
					this.newState.getEndingPoint().getX(), this.newState.getEndingPoint().getY());
			this.oldState.setColor(this.newState.getColor());
		}
	}

	@Override
	public void unexecute() {
		this.oldState.moveBothPoints(this.originalState.getStartingPoint().getX(), this.originalState.getStartingPoint().getY(),
				this.originalState.getEndingPoint().getX(), this.originalState.getEndingPoint().getY());
		this.oldState.setColor(this.originalState.getColor());
	}

	@Override
	public String toString() {
		// edit,line|oldLine_newLine
		return "edit,line|" + this.oldState + "_" + this.originalState;
	}

}
