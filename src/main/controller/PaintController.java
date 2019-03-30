package main.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import javax.swing.JFileChooser;

import main.io.LoadManager;
import main.io.SaveManager;
import main.io.in.LoadLog;
import main.io.in.LoadShapes;
import main.io.out.SaveLog;
import main.io.out.SaveShapes;
import main.model.ShapesModel;
import main.model.command.Command;
import main.model.command.add.AddShapeCmd;
import main.model.command.edit.EditLineCmd;
import main.model.command.edit.EditPointCmd;
import main.model.command.z.DeleteShapeCmd;
import main.model.shape.Line;
import main.model.shape.Point;
import main.model.shape.base.Shape;
import main.util.LogMapper;
import main.util.ShapesModelHelper;
import main.util.constants.PaintMode;
import main.view.Paint;
import main.view.dialogs.edit.EditLineDialog;
import main.view.dialogs.edit.EditPointDialog;

public class PaintController extends Observable {

	private ShapesModel model;
	private Paint paint;

	private List<Command> commands = new ArrayList<>();
	private int currentCommandIndex = -1;

	private Point lineStartPoint;

	// TODO add constants
	private String mode = PaintMode.NORMAL;

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

		if (this.mode.equals(PaintMode.POINT)) {
			Point point = new Point(e.getX(), e.getY(), Color.BLACK);
			AddShapeCmd addShape = new AddShapeCmd(point, this.model);
			helpCommandExecution(addShape);
			this.repaint();
		} else if (this.mode.equals(PaintMode.LINE)) {
			if(this.lineStartPoint == null) {
				this.lineStartPoint = new Point(e.getX(), e.getY(), Color.black);
			} else {
				Point lineEndPoint = new Point(e.getX(), e.getY(), Color.black);
				Line line = new Line(this.lineStartPoint.clone(), lineEndPoint, Color.black);
				this.lineStartPoint = null;
				AddShapeCmd addShape = new AddShapeCmd(line, this.model);
				this.helpCommandExecution(addShape);
			}
		} else if (this.mode.equals(PaintMode.SELECT)) {
			this.helpSelect(e.getX(), e.getY());
		}

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
			saveManager.save(new ArrayList<Object>(this.commands), jFileChooser.getSelectedFile().getAbsolutePath());
		}
	}

	/**
	 * Handles mouse click on select button
	 */
	public void handleChangeMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Handles mouse click on load canvas button
	 */
	@SuppressWarnings("unchecked")
	public void handleLoadCanvas() {
		JFileChooser jFileChooser = new JFileChooser();
		if (jFileChooser.showSaveDialog(paint) == JFileChooser.APPROVE_OPTION) {
			LoadManager loadManager = new LoadManager(new LoadShapes());
			this.model.setShapes(
					(List<Shape>) (Object) loadManager.load(jFileChooser.getSelectedFile().getAbsolutePath()));
			this.repaint();
		}
	}

	/**
	 * Handles mouse click on load log button
	 */
	public void handleLoadLog() {
		JFileChooser jFileChooser = new JFileChooser();
		if (jFileChooser.showSaveDialog(paint) == JFileChooser.APPROVE_OPTION) {
			LoadManager loadManager = new LoadManager(new LoadLog());
			this.refreshCanvasAndLog();
			this.commands = LogMapper
					.mapLogsToCommands(loadManager.load(jFileChooser.getSelectedFile().getAbsolutePath()), this.model);
			this.setUndoRedoNavigation();
		}
	}

	/**
	 * Handles mouse click on delete button
	 */
	public void handleDelete() {
		DeleteShapeCmd deleteCmd = new DeleteShapeCmd(this.model);
		this.helpCommandExecution(deleteCmd);
		this.emitChangesToObservers();
		this.deselectAll();
	}

	private void helpSelect(int x, int y) {
		Collections.reverse(this.model.getShapes());
		for (Shape shape : this.model.getShapes()) {
			if (shape.contains(x, y)) {
				shape.setSelected(!shape.isSelected());
				Collections.reverse(this.model.getShapes());
				this.emitChangesToObservers();
				this.repaint();
				return;
			}
		}

		Collections.reverse(this.model.getShapes());

		this.emitChangesToObservers();
		this.repaint();
	}

	/**
	 * Handles mouse click on edit button
	 */
	public void handleEdit() {
		Shape selectedShape = ShapesModelHelper.getSelectedShape(this.model.getShapes());
		if (selectedShape instanceof Point) {
			Point point = (Point) selectedShape;
			EditPointDialog dialog = new EditPointDialog(this.paint);
			dialog.setPoint(point.clone().clone());
			dialog.setVisible(true);
			if (dialog.getEditedShape() != null) {
				Command command = new EditPointCmd(point, (Point) dialog.getEditedShape());
				this.helpCommandExecution(command);
			}
		} else if (selectedShape instanceof Line) {
			Line line = (Line) selectedShape;
			EditLineDialog dialog = new EditLineDialog(this.paint);
			dialog.setLine(line.clone());
			dialog.setVisible(true);
			if(dialog.getEditedShape() != null) {
				Command command = new EditLineCmd(line, (Line) dialog.getEditedShape());
				this.helpCommandExecution(command);
			}
		}

		this.deselectAll();
		this.repaint();
	}

	/**
	 * Emits changes to all listeners (observers)
	 */
	private void emitChangesToObservers() {
		this.setChanged();
		this.notifyObservers(this.model.getShapes());
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

	private void refreshCanvas() {
		this.model.getShapes().clear();
		this.repaint();
	}

	private void refreshLog() {
		this.commands.clear();
		this.paint.getLogListModel().clear();
		this.currentCommandIndex = -1;
		this.repaint();
	}

	private void deselectAll() {
		ShapesModelHelper.deselectAll(this.model.getShapes());
		this.emitChangesToObservers();
		this.repaint();
	}

	private void refreshCanvasAndLog() {
		this.refreshCanvas();
		this.refreshLog();
	}

	private void repaint() {
		this.paint.repaint();
	}

}
