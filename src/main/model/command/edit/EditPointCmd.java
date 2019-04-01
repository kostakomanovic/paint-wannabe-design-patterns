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
		this.originalState = this.oldState.clone();
		if(this.fromLog) {
			for(Shape s : this.model.getShapes()) {
				if(s.equals(this.oldState)) {
					Point p = (Point) s;
					this.oldState = p;
					p.moveTo(this.newState.getX(), this.newState.getY());
					p.setColor(this.newState.getColor());	
				}
			}
		} else {
			this.oldState.moveTo(this.newState.getX(), this.newState.getY());
			this.oldState.setColor(this.newState.getColor());			
		}
	}

	@Override
	public void unexecute() {
		this.oldState.moveTo(this.originalState.getX(), this.originalState.getY());
		this.oldState.setColor(this.originalState.getColor());
	}
	
	@Override
	public String toString() {
		// edit,point|oldPoint_newPoint
		return "edit,point|" + this.originalState + "_" + this.newState;		
	}

}
