package main.model.command.edit;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.shape.Square;
import main.model.shape.base.Shape;

public class EditSquareCmd implements Command {

	private Square oldState;
	private Square newState;
	private Square originalState;
	private ShapesModel model;
	private boolean fromLog;

	public EditSquareCmd(Square oldState, Square newState, ShapesModel model, boolean fromLog) {
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
					Square square = (Square) s;
					this.oldState = square;
					square.moveTo(newState.getOrigin().getX(), newState.getOrigin().getY());
					square.setWidth(newState.getWidth());
					square.setColor(newState.getColor());
					square.setFillColor(newState.getFillColor());
				}
			}
		} else {
			oldState.moveTo(newState.getOrigin().getX(), newState.getOrigin().getY());
			oldState.setWidth(newState.getWidth());
			oldState.setColor(newState.getColor());
			oldState.setFillColor(newState.getFillColor());
		}
	}

	@Override
	public void unexecute() {
		oldState.moveTo(originalState.getOrigin().getX(), originalState.getOrigin().getY());
		oldState.setWidth(originalState.getWidth());
		oldState.setColor(originalState.getColor());
		oldState.setFillColor(originalState.getFillColor());
	}

	@Override
	public String toString() {
		// edit,square|oldSquare_newSquare
		return "edit,square|" + this.originalState + "_" + this.newState;
	}

}
