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
		this.originalState = this.oldState.clone();
		if (this.fromLog) {
			for (Shape s : this.model.getShapes()) {
				if (s.equals(this.oldState)) {
					Square square = (Square) s;
					this.oldState = square;
					square.moveTo(this.newState.getOrigin().getX(), this.newState.getOrigin().getY());
					square.setWidth(this.newState.getWidth());
					square.setColor(this.newState.getColor());
					square.setFillColor(this.newState.getFillColor());
				}
			}
		} else {
			this.oldState.moveTo(this.newState.getOrigin().getX(), this.newState.getOrigin().getY());
			this.oldState.setWidth(this.newState.getWidth());
			this.oldState.setColor(this.newState.getColor());
			this.oldState.setFillColor(this.newState.getFillColor());
		}
	}

	@Override
	public void unexecute() {
		this.oldState.moveTo(this.originalState.getOrigin().getX(), this.originalState.getOrigin().getY());
		this.oldState.setWidth(this.originalState.getWidth());
		this.oldState.setColor(this.originalState.getColor());
		this.oldState.setFillColor(this.originalState.getFillColor());
	}

	@Override
	public String toString() {
		// edit,square|oldSquare_newSquare
		return "edit,square|" + this.originalState + "_" + this.newState;
	}

}
