package main.model.command.edit;

import main.model.command.Command;
import main.model.shape.Point;

public class EditPointCmd implements Command {

	private Point oldPoint = new Point();
	private Point newPoint;
	private Point originalPoint;
	
	public EditPointCmd(Point originalPoint, Point newPoint) {
		this.originalPoint = originalPoint;
		this.newPoint = newPoint;
	}
	
	@Override
	public void execute() {
		
		this.oldPoint.setX(this.originalPoint.getX());
		this.oldPoint.setY(this.originalPoint.getY());
		this.oldPoint.setColor(this.originalPoint.getColor());
		
		this.originalPoint.setX(this.newPoint.getX());
		this.originalPoint.setY(this.newPoint.getY());
		this.originalPoint.setColor(this.newPoint.getColor());
	}

	@Override
	public void unexecute() {
		this.originalPoint.setX(this.oldPoint.getX());
		this.originalPoint.setY(this.oldPoint.getY());
		this.originalPoint.setColor(this.oldPoint.getColor());
	}
	
	@Override
	public String toString() {
		// edit,point|oldPoint_newPoint
		return "edit,point|" + this.oldPoint + "_" + this.newPoint;		
	}

}
