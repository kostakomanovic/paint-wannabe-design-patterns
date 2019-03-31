package main.model.command.edit;

import main.model.command.Command;
import main.model.shape.Point;
import main.model.shape.Rectangle;

public class EditRectangleCmd implements Command{

	private Rectangle originalRectangle;
	private Rectangle newRectangle;
	private Rectangle oldRectangle = new Rectangle(new Point(), 0, 0);

	public EditRectangleCmd(Rectangle original, Rectangle newState) {
		this.originalRectangle = original;
		this.newRectangle = newState;
	}

	@Override
	public void execute() {
		oldRectangle.getOrigin().setX(originalRectangle.getOrigin().getX());
		oldRectangle.getOrigin().setY(originalRectangle.getOrigin().getY());
		oldRectangle.setHeight(originalRectangle.getHeight());
		oldRectangle.setWidth(originalRectangle.getWidth());
		oldRectangle.setColor(originalRectangle.getColor());
		oldRectangle.setFillColor(originalRectangle.getFillColor());

		originalRectangle.getOrigin().setX(newRectangle.getOrigin().getX());
		originalRectangle.getOrigin().setY(newRectangle.getOrigin().getY());
		originalRectangle.setHeight(newRectangle.getHeight());
		originalRectangle.setWidth(newRectangle.getWidth());
		originalRectangle.setColor(newRectangle.getColor());
		originalRectangle.setFillColor(newRectangle.getFillColor());
	}

	@Override
	public void unexecute() {
		originalRectangle.getOrigin().setX(oldRectangle.getOrigin().getX());
		originalRectangle.getOrigin().setY(oldRectangle.getOrigin().getY());
		originalRectangle.setHeight(oldRectangle.getHeight());
		originalRectangle.setWidth(oldRectangle.getWidth());
		originalRectangle.setColor(oldRectangle.getColor());
		originalRectangle.setFillColor(oldRectangle.getFillColor());
	}
	
	@Override
	public String toString() {
		// edit,rectangle|oldRectangle_newRectangle
		return "edit,rectangle|" + this.oldRectangle + "_" + this.newRectangle;
	}

	
}
