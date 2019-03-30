package main.model.command.edit;

import java.awt.Color;

import main.model.command.Command;
import main.model.shape.HexagonAdapter;
import main.model.shape.Point;

public class EditHexagonAdapterCmd implements Command {
	
	private HexagonAdapter originalHexagon;
	private HexagonAdapter newHexagon;
	private HexagonAdapter oldHexagon = new HexagonAdapter(new Point(0, 0), 0, Color.BLACK, Color.BLACK);

	public EditHexagonAdapterCmd(HexagonAdapter original, HexagonAdapter newState) {
		this.originalHexagon = original;
		this.newHexagon = newState;
	}

	@Override
	public void execute() {
		oldHexagon.getHexagon().setX(originalHexagon.getHexagon().getX());
		oldHexagon.getHexagon().setY(originalHexagon.getHexagon().getY());
		oldHexagon.getHexagon().setR(originalHexagon.getHexagon().getR());
		oldHexagon.setColor(originalHexagon.getColor());
		oldHexagon.setFillColor(originalHexagon.getFillColor());

		originalHexagon.getHexagon().setX(newHexagon.getHexagon().getX());
		originalHexagon.getHexagon().setY(newHexagon.getHexagon().getY());
		originalHexagon.getHexagon().setR(newHexagon.getHexagon().getR());
		originalHexagon.setColor(newHexagon.getColor());
		originalHexagon.setFillColor(newHexagon.getFillColor());
	}

	@Override
	public void unexecute() {
		originalHexagon.getHexagon().setX(oldHexagon.getHexagon().getX());
		originalHexagon.getHexagon().setY(oldHexagon.getHexagon().getY());
		originalHexagon.getHexagon().setR(oldHexagon.getHexagon().getR());
		originalHexagon.setColor(oldHexagon.getColor());
		originalHexagon.setFillColor(oldHexagon.getFillColor());
	}

}
