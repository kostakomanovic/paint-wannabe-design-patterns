package main.model.command.edit;

import java.awt.Color;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.shape.Circle;
import main.model.shape.HexagonAdapter;
import main.model.shape.Point;
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
		originalState = oldState.clone();
		if (this.fromLog) {
			for (Shape s : this.model.getShapes()) {
				if (s.equals(this.oldState)) {
					HexagonAdapter hexagon = (HexagonAdapter) s;
					this.oldState = hexagon;
					hexagon.moveTo(newState.getHexagon().getX(), newState.getHexagon().getY());
					hexagon.getHexagon().setX(newState.getHexagon().getX());
					hexagon.getHexagon().setY(newState.getHexagon().getY());
					hexagon.setColor(newState.getColor());
					hexagon.setFillColor(newState.getFillColor());
				}
			}
		} else {
			oldState.moveTo(newState.getHexagon().getX(), newState.getHexagon().getY());
			oldState.getHexagon().setX(newState.getHexagon().getX());
			oldState.getHexagon().setY(newState.getHexagon().getY());
			oldState.setColor(newState.getColor());
			oldState.setFillColor(newState.getFillColor());
		}
	}

	@Override
	public void unexecute() {
		oldState.moveTo(originalState.getHexagon().getX(), originalState.getHexagon().getY());
		oldState.getHexagon().setX(originalState.getHexagon().getX());
		oldState.getHexagon().setY(originalState.getHexagon().getY());
		oldState.setColor(originalState.getColor());
		oldState.setFillColor(originalState.getFillColor());
	}

	@Override
	public String toString() {
		// edit,hexagon|oldHexagon_newHexagon
		return "edit,hexagon|" + this.originalState + "_" + this.newState;
	}
	
}
