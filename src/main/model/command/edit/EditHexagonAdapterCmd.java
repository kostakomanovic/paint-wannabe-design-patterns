package main.model.command.edit;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.shape.HexagonAdapter;
import main.model.shape.base.Shape;

public class EditHexagonAdapterCmd implements Command {
	
	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter originalState;
	private ShapesModel model;
	private boolean fromLog;

	public EditHexagonAdapterCmd(HexagonAdapter oldState, HexagonAdapter newState, ShapesModel model, boolean fromLog) {
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
					HexagonAdapter hexagon = (HexagonAdapter) s;
					this.oldState = hexagon;
					hexagon.moveTo(this.newState.getHexagon().getX(), this.newState.getHexagon().getY());
					hexagon.getHexagon().setX(this.newState.getHexagon().getX());
					hexagon.getHexagon().setY(this.newState.getHexagon().getY());
					hexagon.setColor(this.newState.getColor());
					hexagon.setFillColor(this.newState.getFillColor());
				}
			}
		} else {
			this.oldState.moveTo(this.newState.getHexagon().getX(), this.newState.getHexagon().getY());
			this.oldState.getHexagon().setX(this.newState.getHexagon().getX());
			this.oldState.getHexagon().setY(this.newState.getHexagon().getY());
			this.oldState.setColor(this.newState.getColor());
			this.oldState.setFillColor(this.newState.getFillColor());
		}
	}

	@Override
	public void unexecute() {
		this.oldState.moveTo(this.originalState.getHexagon().getX(), this.originalState.getHexagon().getY());
		this.oldState.getHexagon().setX(this.originalState.getHexagon().getX());
		this.oldState.getHexagon().setY(this.originalState.getHexagon().getY());
		this.oldState.setColor(this.originalState.getColor());
		this.oldState.setFillColor(this.originalState.getFillColor());
	}

	@Override
	public String toString() {
		// edit,hexagon|oldHexagon_newHexagon
		return "edit,hexagon|" + this.originalState + "_" + this.newState;
	}
	
}
