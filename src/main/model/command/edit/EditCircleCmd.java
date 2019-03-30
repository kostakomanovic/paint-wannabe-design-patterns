package main.model.command.edit;

import main.model.command.Command;
import main.model.shape.Circle;
import main.model.shape.Point;

public class EditCircleCmd implements Command {

	private Circle originalCircle;
	private Circle newCircle;
	private Circle oldCircle = new Circle(new Point(), 0);

	public EditCircleCmd(Circle original, Circle newState) {
		this.originalCircle = original;
		this.newCircle = newState;
	}

	@Override
	public void execute() {
		oldCircle.getCenter().setX(originalCircle.getCenter().getX());
		oldCircle.getCenter().setY(originalCircle.getCenter().getY());
		oldCircle.setRadius(originalCircle.getRadius());
		oldCircle.setColor(originalCircle.getColor());
		oldCircle.setFillColor(originalCircle.getFillColor());

		originalCircle.getCenter().setX(newCircle.getCenter().getX());
		originalCircle.getCenter().setY(newCircle.getCenter().getY());
		originalCircle.setRadius(newCircle.getRadius());
		originalCircle.setColor(newCircle.getColor());
		originalCircle.setFillColor(newCircle.getFillColor());
	}

	@Override
	public void unexecute() {
		originalCircle.getCenter().setX(oldCircle.getCenter().getX());
		originalCircle.getCenter().setY(oldCircle.getCenter().getY());
		originalCircle.setRadius(oldCircle.getRadius());
		originalCircle.setColor(oldCircle.getColor());
		originalCircle.setFillColor(oldCircle.getFillColor());
	}

}
