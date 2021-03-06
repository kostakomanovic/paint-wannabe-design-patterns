package main.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.io.LoadManager;
import main.io.SaveManager;
import main.io.in.LoadLog;
import main.io.in.LoadShapes;
import main.io.out.SaveLog;
import main.io.out.SaveShapes;
import main.model.ShapesModel;
import main.model.command.Command;
import main.model.command.add.AddShapeCmd;
import main.model.command.delete.DeleteShapeCmd;
import main.model.command.edit.EditCircleCmd;
import main.model.command.edit.EditHexagonAdapterCmd;
import main.model.command.edit.EditLineCmd;
import main.model.command.edit.EditPointCmd;
import main.model.command.edit.EditRectangleCmd;
import main.model.command.edit.EditSquareCmd;
import main.model.command.z.BringToBackCmd;
import main.model.command.z.BringToFrontCmd;
import main.model.command.z.ToBackCmd;
import main.model.command.z.ToFrontCmd;
import main.model.shape.Circle;
import main.model.shape.HexagonAdapter;
import main.model.shape.Line;
import main.model.shape.Point;
import main.model.shape.Rectangle;
import main.model.shape.Square;
import main.model.shape.base.Shape;
import main.util.LogMapper;
import main.util.ShapesModelHelper;
import main.util.constants.PaintMode;
import main.view.Paint;
import main.view.dialogs.edit.EditCircleDialog;
import main.view.dialogs.edit.EditHexagonDialog;
import main.view.dialogs.edit.EditLineDialog;
import main.view.dialogs.edit.EditPointDialog;
import main.view.dialogs.edit.EditRectangleDialog;
import main.view.dialogs.edit.EditSquareDialog;

public class PaintController extends Observable {

	private ShapesModel model;
	private Paint paint;

	private List<Command> commands = new ArrayList<>();
	private int currentCommandIndex = -1;

	private Point lineStartPoint;

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
			Point point = new Point(e.getX(), e.getY(), this.paint.getBtnColorBackground());
			AddShapeCmd addShape = new AddShapeCmd(point, this.model);
			helpCommandExecution(addShape);
			this.repaint();
		} else if (this.mode.equals(PaintMode.LINE)) {
			if (this.lineStartPoint == null) {
				this.lineStartPoint = new Point(e.getX(), e.getY(), this.paint.getBtnColorBackground());
			} else {
				Point lineEndPoint = new Point(e.getX(), e.getY(), this.paint.getBtnColorBackground());
				Line line = new Line(this.lineStartPoint.clone(), lineEndPoint, this.paint.getBtnColorBackground());
				this.lineStartPoint = null;
				AddShapeCmd addShape = new AddShapeCmd(line, this.model);
				this.helpCommandExecution(addShape);
			}
		} else if (this.mode.equals(PaintMode.SQUARE)) {
			String squareWidth = JOptionPane.showInputDialog(this.paint, "Enter square width:", "Square width",
					JOptionPane.QUESTION_MESSAGE);
			if (squareWidth != null) {
				int width = Integer.parseInt(squareWidth);
				Square square = new Square(new Point(e.getX(), e.getY()), width, this.paint.getBtnColorBackground(),
						this.paint.getBtnFillColorBackground());
				AddShapeCmd addShape = new AddShapeCmd(square, this.model);
				this.helpCommandExecution(addShape);
			}
		} else if (this.mode.equals(PaintMode.RECTANGLE)) {
			JTextField width = new JTextField();
			JTextField height = new JTextField();
			Object[] widthHeight = { "Width:", width, "Height:", height };
			int option = JOptionPane.showConfirmDialog(this.paint, widthHeight, "Choose rectangle size",
					JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				if (width.getText() != null && height.getText() != null) {
					Rectangle rectangle = new Rectangle(new Point(e.getX(), e.getY()),
							Integer.parseInt(width.getText()), Integer.parseInt(height.getText()),
							this.paint.getBtnColorBackground(), this.paint.getBtnFillColorBackground());
					AddShapeCmd addShape = new AddShapeCmd(rectangle, this.model);
					this.helpCommandExecution(addShape);
				}

			}
		} else if (this.mode.equals(PaintMode.CIRCLE)) {
			String circleRadius = JOptionPane.showInputDialog(this.paint, "Enter circle radius:", "Circle radius",
					JOptionPane.QUESTION_MESSAGE);
			if (circleRadius != null) {
				int radius = Integer.parseInt(circleRadius);
				Circle circle = new Circle(new Point(e.getX(), e.getY()), radius, this.paint.getBtnColorBackground(),
						this.paint.getBtnFillColorBackground());
				AddShapeCmd addShape = new AddShapeCmd(circle, this.model);
				this.helpCommandExecution(addShape);
			}
		} else if (this.mode.equals(PaintMode.HEXAGON)) {
			String hexagonRadius = JOptionPane.showInputDialog(this.paint, "Enter hexagon radius:", "Hexagon radius",
					JOptionPane.QUESTION_MESSAGE);
			if (hexagonRadius != null) {
				int radius = Integer.parseInt(hexagonRadius);
				HexagonAdapter hexagon = new HexagonAdapter(new Point(e.getX(), e.getY()), radius,
						this.paint.getBtnColorBackground(), this.paint.getBtnFillColorBackground());
				AddShapeCmd addShape = new AddShapeCmd(hexagon, this.model);
				this.helpCommandExecution(addShape);
			}
		} else if (this.mode.equals(PaintMode.SELECT)) {
			this.helpSelect(e.getX(), e.getY());
		}

		this.emitChangesToObservers();
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
			saveManager.save(new ArrayList<Object>(Collections.list(this.paint.getLogListModel().elements())),
					jFileChooser.getSelectedFile().getAbsolutePath());
		}
	}

	/**
	 * Handles mouse click on select button
	 */
	public void handleChangeMode(String mode) {
		this.mode = mode;
		this.setChanged();
		this.notifyObservers(mode);
	}

	/**
	 * Handles mouse click on load canvas button
	 */
	@SuppressWarnings("unchecked")
	public void handleLoadCanvas() {
		JFileChooser jFileChooser = new JFileChooser();
		if (jFileChooser.showOpenDialog(paint) == JFileChooser.APPROVE_OPTION) {
			LoadManager loadManager = new LoadManager(new LoadShapes());
			this.refreshCanvasAndLog();
			this.commands.clear();
			this.paint.getLogListModel().clear();
			this.setUndoRedoNavigation();
			this.repaint();
			for (Object obj : loadManager.load(jFileChooser.getSelectedFile().getAbsolutePath())) {
				Shape shape = (Shape) obj;
				this.model.getShapes().add(shape);
			}
			this.repaint();
		}
	}

	/**
	 * Handles to front command
	 */
	public void handleToFront() {
		Shape selectedShape = ShapesModelHelper.getSelectedShape(this.model.getShapes());
		if (this.model.getShapes().indexOf(selectedShape) == this.model.getShapes().size() - 1) {
			JOptionPane.showMessageDialog(this.paint,
					"Cannot move this shape to front since it is already the last shape!", "Error!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		ToFrontCmd command = new ToFrontCmd(selectedShape, this.model);
		this.helpCommandExecution(command);
	}

	/**
	 * Handles to back command
	 */
	public void handleToBack() {
		Shape selectedShape = ShapesModelHelper.getSelectedShape(this.model.getShapes());
		if (this.model.getShapes().indexOf(selectedShape) == 0) {
			JOptionPane.showMessageDialog(this.paint,
					"Cannot move this shape to back since it is already the first shape!", "Error!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		ToBackCmd command = new ToBackCmd(selectedShape, this.model);
		this.helpCommandExecution(command);
	}

	/**
	 * Handles bring to front command
	 */
	public void handleBringToFront() {
		Shape selectedShape = ShapesModelHelper.getSelectedShape(this.model.getShapes());
		BringToFrontCmd command = new BringToFrontCmd(this.model, this.model.getShapes().indexOf(selectedShape));
		if (this.model.getShapes().indexOf(selectedShape) == this.model.getShapes().size() - 1) {
			JOptionPane.showMessageDialog(this.paint,
					"Cannot bring this shape to front since it is already the last shape!", "Error!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		this.helpCommandExecution(command);
	}

	/**
	 * Handles bring to front command
	 */
	public void handleBringToBack() {
		Shape selectedShape = ShapesModelHelper.getSelectedShape(this.model.getShapes());
		BringToBackCmd command = new BringToBackCmd(this.model, this.model.getShapes().indexOf(selectedShape));
		if (this.model.getShapes().indexOf(selectedShape) == 0) {
			JOptionPane.showMessageDialog(this.paint,
					"Cannot bring this shape to back since it is already the first shape!", "Error!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		this.helpCommandExecution(command);
	}

	/**
	 * Handles mouse click on load log button
	 */
	public void handleLoadLog() {
		JFileChooser jFileChooser = new JFileChooser();
		if (jFileChooser.showOpenDialog(paint) == JFileChooser.APPROVE_OPTION) {
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
		String[] options = { "Cancel", "Yes" };
		int option = JOptionPane.showOptionDialog(this.paint, "Are you sure you want to delete selected shape/shapes?",
				"Delete shapes?", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		if (option == 1) {
			List<Shape> deletedShapes = new ArrayList<Shape>();
			for (Shape shape : this.model.getShapes()) {
				if (shape.isSelected())
					deletedShapes.add(shape);
			}
			DeleteShapeCmd deleteCmd = new DeleteShapeCmd(deletedShapes, this.model, false);
			this.helpCommandExecution(deleteCmd);
			this.emitChangesToObservers();
			this.deselectAll();
		}
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
			Point clone = point.clone();
			EditPointDialog dialog = new EditPointDialog(clone);
			dialog.setVisible(true);
			Command command = new EditPointCmd(point, clone, null, false);
			this.helpCommandExecution(command);
		} else if (selectedShape instanceof Line) {
			Line line = (Line) selectedShape;
			EditLineDialog dialog = new EditLineDialog(line);
			dialog.setVisible(true);
			if (dialog.getEditedShape() != null) {
				Command command = new EditLineCmd(line, (Line) dialog.getEditedShape(), null, false);
				this.helpCommandExecution(command);
			}
		} else if (selectedShape instanceof Rectangle) {
			Rectangle rectangle = (Rectangle) selectedShape;
			Rectangle clone = rectangle.clone();
			EditRectangleDialog dialog = new EditRectangleDialog(clone);
			dialog.setVisible(true);
			if (dialog.getEditedShape() != null) {
				Command command = new EditRectangleCmd(rectangle, clone, null, false);
				this.helpCommandExecution(command);
			}
		} else if (selectedShape instanceof Square) {
			Square square = (Square) selectedShape;
			Square clone = square.clone();
			EditSquareDialog dialog = new EditSquareDialog(clone);
			dialog.setVisible(true);
			if (dialog.getEditedShape() != null) {
				Command command = new EditSquareCmd(square, clone, null, false);
				this.helpCommandExecution(command);
			}
		} else if (selectedShape instanceof Circle) {
			Circle circle = (Circle) selectedShape;
			Circle clone = circle.clone();
			EditCircleDialog dialog = new EditCircleDialog(clone);
			dialog.setVisible(true);
			if (dialog.getEditedShape() != null) {
				Command command = new EditCircleCmd(circle, (Circle) dialog.getEditedShape(), null, false);
				this.helpCommandExecution(command);
			}
		} else if (selectedShape instanceof HexagonAdapter) {
			HexagonAdapter hexagon = (HexagonAdapter) selectedShape;
			EditHexagonDialog dialog = new EditHexagonDialog(hexagon);
			dialog.setVisible(true);
			if (dialog.getEditedShape() != null) {
				Command command = new EditHexagonAdapterCmd(hexagon, (HexagonAdapter) dialog.getEditedShape(), null,
						false);
				this.helpCommandExecution(command);
			}

		}

		this.emitChangesToObservers();
		this.repaint();
	}

	public void chooseFillColor(JButton btnColor) {
		Color color = JColorChooser.showDialog(this.paint, "Choose fill color", this.paint.getBtnFillColorBackground());

		if (color != null) {
			btnColor.setBackground(color);
			this.paint.setBtnFillColorBackground(color);
		}
	}

	public void chooseColor(JButton btnColor) {
		Color color = JColorChooser.showDialog(this.paint, "Choose color", this.paint.getBtnColorBackground());

		if (color != null) {
			btnColor.setBackground(color);
			this.paint.setBtnColorBackground(color);
		}
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
		if (this.currentCommandIndex == this.commands.size() - 1) {
			this.currentCommandIndex++;
		} else {
			this.currentCommandIndex = this.commands.size();
		}
		this.commands.add(command);
		this.paint.getLogListModel().addElement(String.valueOf(this.commands.get(this.currentCommandIndex)));
		this.setUndoRedoNavigation();
	}

	public void undo() {
		this.commands.get(this.currentCommandIndex).unexecute();
//		this.paint.getLogListModel().remove(this.paint.getLogListModel().size() - 1);
		this.paint.getLogListModel().addElement("UNDO:" + String.valueOf(this.commands.get(this.currentCommandIndex)));
		this.currentCommandIndex--;
		this.setUndoRedoNavigation();
		this.repaint();
	}

	public void redo() {
		this.currentCommandIndex++;
		this.commands.get(currentCommandIndex).execute();
		this.paint.getLogListModel().addElement("REDO: " + String.valueOf(this.commands.get(this.currentCommandIndex)));
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
