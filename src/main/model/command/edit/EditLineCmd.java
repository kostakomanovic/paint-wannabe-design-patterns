package main.model.command.edit;

import main.model.command.Command;
import main.model.shape.Line;
import main.model.shape.Point;

public class EditLineCmd implements Command{
	
	private Line originalLine;
	private Line newLine;
	private Line oldLine = new Line(new Point(), new Point());

	public EditLineCmd(Line original, Line newState) {
		this.originalLine = original;
		this.newLine = newState;
	}

	@Override
	public void execute() {
		oldLine.getStartingPoint().setX(originalLine.getStartingPoint().getX());
		oldLine.getStartingPoint().setY(originalLine.getStartingPoint().getY());
		oldLine.getEndingPoint().setX(originalLine.getEndingPoint().getX());
		oldLine.getEndingPoint().setY(originalLine.getEndingPoint().getY());
		oldLine.setColor(originalLine.getColor());

		originalLine.getStartingPoint().setX(newLine.getStartingPoint().getX());
		originalLine.getStartingPoint().setY(newLine.getStartingPoint().getY());
		originalLine.getEndingPoint().setX(newLine.getEndingPoint().getX());
		originalLine.getEndingPoint().setY(newLine.getEndingPoint().getY());
		originalLine.setColor(newLine.getColor());
	}

	@Override
	public void unexecute() {
		originalLine.getStartingPoint().setX(oldLine.getStartingPoint().getX());
		originalLine.getStartingPoint().setY(oldLine.getStartingPoint().getY());
		originalLine.getEndingPoint().setX(oldLine.getEndingPoint().getX());
		originalLine.getEndingPoint().setY(oldLine.getEndingPoint().getY());
		originalLine.setColor(oldLine.getColor());
	}
	
	@Override
	public String toString() {
		// edit,line|oldLine_newLine
		return "edit,line|" + this.oldLine + "_" + this.newLine;
	}

}
