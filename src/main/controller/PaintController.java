package main.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import main.io.LoadManager;
import main.io.SaveManager;
import main.io.in.LoadShapes;
import main.io.out.SaveLog;
import main.io.out.SaveShapes;
import main.model.ShapesModel;
import main.model.command.Command;
import main.model.command.add.AddShapeCmd;
import main.model.shape.Point;
import main.model.shape.base.Shape;
import main.view.Paint;

public class PaintController {

	private ShapesModel model;
	private Paint paint;

	private List<Command> commands = new ArrayList<>();
	private int currentCommandIndex = -1;

	public PaintController(ShapesModel model, Paint paint) {
		this.model = model;
		this.paint = paint;
	}

	/**
	 * Handles mouse click on canvas
	 * 
	 * @param e <MouseEvent>
	 */
	public void handleMouseClick(MouseEvent e) {
		Point point = new Point(e.getX(), e.getY(), Color.BLACK);
		AddShapeCmd addShape = new AddShapeCmd(point, this.model);
		helpCommandExecution(addShape);
		this.paint.getCanvas().repaint();
	}

	/**
	 * Handles mouse click on save canvas button
	 */
	public void handleSaveCanvas() {
		JFileChooser jFileChooser = new JFileChooser();
		if (jFileChooser.showSaveDialog(paint) == JFileChooser.APPROVE_OPTION) {
			SaveManager saveManager = new SaveManager(new SaveShapes());
			saveManager.save(new ArrayList<Object>(this.model.getShapes()),
					jFileChooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	/**
	 * Handles mouse click on save log button
	 */
	public void handleSaveLog() {
		JFileChooser jFileChooser = new JFileChooser();
		if (jFileChooser.showSaveDialog(paint) == JFileChooser.APPROVE_OPTION) {
			SaveManager saveManager = new SaveManager(new SaveLog());
			saveManager.save(new ArrayList<Object>(this.commands),
					jFileChooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	/**
	 * Handles mouse click on load canvas button
	 */
	@SuppressWarnings("unchecked")
	public void handleLoadCanvas() {
		JFileChooser jFileChooser = new JFileChooser();
		if(jFileChooser.showSaveDialog(paint) == JFileChooser.APPROVE_OPTION) {
			LoadManager loadManager = new LoadManager(new LoadShapes());
			this.model.setShapes((List<Shape>)(Object)loadManager.load(jFileChooser.getSelectedFile().getAbsolutePath()));
			this.repaint();
		}
	}
	
	/**
	 * Handles mouse click on load log button
	 */
	public void handleLoadLog() {
		
	}

	private void helpCommandExecution(Command command) {
		command.execute();
		this.commands.add(command);
		this.currentCommandIndex++;
		this.paint.getLogListModel().addElement(this.commands.get(this.currentCommandIndex));
		this.setUndoRedoNavigation();
	}

	public void undo() {
		this.commands.get(this.currentCommandIndex).unexecute();
		this.paint.getLogListModel().removeElement(this.commands.get(this.currentCommandIndex));
		this.currentCommandIndex--;
		this.setUndoRedoNavigation();
		this.repaint();
	}

	public void redo() {
		this.currentCommandIndex++;
		this.commands.get(currentCommandIndex).execute();
		this.paint.getLogListModel().addElement(this.commands.get(this.currentCommandIndex));
		this.setUndoRedoNavigation();
		this.repaint();
	}

	private void setUndoRedoNavigation() {
		this.paint.setEnabledBtnUndo(this.currentCommandIndex > -1);
		this.paint.setEnabledBtnRedo(this.currentCommandIndex < this.commands.size() - 1);
		this.repaint();
	}

	private void repaint() {
		this.paint.repaint();
	}

}
