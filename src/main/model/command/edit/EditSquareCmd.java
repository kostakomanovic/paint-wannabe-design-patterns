package main.model.command.edit;

import main.model.command.Command;
import main.model.shape.Point;
import main.model.shape.Square;

public class EditSquareCmd implements Command {
	

	private Square originalSquare;
	private Square newSquare;
	private Square oldSquare = new Square(new Point(), 0);

	public EditSquareCmd(Square original, Square newState) {
		this.originalSquare = original;
		this.newSquare = newState;
	}

	@Override
	public void execute() {
		oldSquare.getOrigin().setX(originalSquare.getOrigin().getX());
		oldSquare.getOrigin().setY(originalSquare.getOrigin().getY());
		oldSquare.setWidth(originalSquare.getWidth());
		oldSquare.setColor(originalSquare.getColor());
		oldSquare.setFillColor(originalSquare.getFillColor());

		originalSquare.getOrigin().setX(newSquare.getOrigin().getX());
		originalSquare.getOrigin().setY(newSquare.getOrigin().getY());
		originalSquare.setWidth(newSquare.getWidth());
		originalSquare.setColor(newSquare.getColor());
		originalSquare.setFillColor(newSquare.getFillColor());
	}

	@Override
	public void unexecute() {
		originalSquare.getOrigin().setX(oldSquare.getOrigin().getX());
		originalSquare.getOrigin().setY(oldSquare.getOrigin().getY());
		originalSquare.setWidth(oldSquare.getWidth());
		originalSquare.setColor(oldSquare.getColor());
		originalSquare.setFillColor(oldSquare.getFillColor());
	}
	
	@Override
	public String toString() {
		// edit,square|oldSquare_newSquare
		return "edit,square|" + this.oldSquare + "_" + this.newSquare;
	}

}
