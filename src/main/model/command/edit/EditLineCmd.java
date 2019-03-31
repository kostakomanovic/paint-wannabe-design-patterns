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
		originalState = oldState.clone();
		if (this.fromLog) {
			for (Shape s : this.model.getShapes()) {
				if (s.equals(this.oldState)) {
					Line l = (Line) s;
					this.oldState = l;
					l.moveBothPoints(newState.getStartingPoint().getX(), newState.getStartingPoint().getY(),
							newState.getEndingPoint().getX(), newState.getEndingPoint().getY());
					l.setColor(newState.getColor());
				}
			}
		} else {
			oldState.moveBothPoints(newState.getStartingPoint().getX(), newState.getStartingPoint().getY(),
					newState.getEndingPoint().getX(), newState.getEndingPoint().getY());
			oldState.setColor(newState.getColor());
		}
	}

	@Override
	public void unexecute() {
		oldState.moveBothPoints(originalState.getStartingPoint().getX(), originalState.getStartingPoint().getY(),
				originalState.getEndingPoint().getX(), originalState.getEndingPoint().getY());
		oldState.setColor(originalState.getColor());
	}

	@Override
	public String toString() {
		// edit,line|oldLine_newLine
		return "edit,line|" + this.oldState + "_" + this.originalState;
	}

}
