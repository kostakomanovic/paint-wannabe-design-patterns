package main.model.command.edit;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.shape.Point;
import main.model.shape.base.Shape;

public class EditPointCmd implements Command {

	private Point oldState;
	private Point newState;
	private Point originalState;
	private ShapesModel model;
	private boolean fromLog;
	
	public EditPointCmd(Point oldState, Point newState, ShapesModel model, boolean fromLog) {
		this.oldState = oldState;
		this.newState = newState;
		this.model = model;
		this.fromLog = fromLog;
	}
	
	@Override
	public void execute() {
		originalState = oldState.clone();
		if(this.fromLog) {
			for(Shape s : this.model.getShapes()) {
				if(s.equals(this.oldState)) {
					Point p = (Point) s;
					this.oldState = p;
					p.moveTo(newState.getX(), newState.getY());
					p.setColor(newState.getColor());	
				}
			}
		} else {
			oldState.moveTo(newState.getX(), newState.getY());
			oldState.setColor(newState.getColor());			
		}
	}

	@Override
	public void unexecute() {
		oldState.moveTo(originalState.getX(), originalState.getY());
		oldState.setColor(originalState.getColor());
	}
	
	@Override
	public String toString() {
		// edit,point|oldPoint_newPoint
		return "edit,point|" + this.originalState + "_" + this.newState;		
	}

}
