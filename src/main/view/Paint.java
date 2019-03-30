package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import main.controller.PaintController;
import main.model.command.Command;
import main.model.shape.base.Shape;
import main.util.constants.PaintMode;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.event.MouseMotionAdapter;

public class Paint extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -926357764508616835L;

	private PaintController controller;

	/**
	 * Panels
	 */
	private Canvas canvas = new Canvas();
	private JPanel topCommandsPanel = new JPanel();
	private final JPanel bottomLogPanel = new JPanel();

	/**
	 * Lists
	 */
	private DefaultListModel<Command> logListModel = new DefaultListModel<>();
	private JList<Command> commandList = new JList<>(logListModel);

	/**
	 * Undo Redo Navigation, Select
	 */
	private final JButton btnRedo = new JButton("Redo");
	private final JButton btnUndo = new JButton("Undo");
	private final JButton btnSelect = new JButton("Select");

	private final JToggleButton tbtnPoint = new JToggleButton("Point");
	private final JToggleButton tbtnLine = new JToggleButton("Line");
	private final JToggleButton tbtnSquare = new JToggleButton("Square");
	private final JToggleButton tbtnRectangle = new JToggleButton("Rectangle");
	private final JToggleButton tbtnCircle = new JToggleButton("Circle");
	private final JToggleButton tbtnHexagon = new JToggleButton("Hexagon");

	private final JMenuBar mbMain = new JMenuBar();
	private final JMenu mnFile = new JMenu("File");
	private final JMenuItem mnSaveCanvas = new JMenuItem("Canvas");
	private final JMenuItem mnLoadCanvas = new JMenuItem("Canvas");
	private final JMenuItem mnSaveLog = new JMenuItem("Log");
	private final JMenu mnSave = new JMenu("Save");
	private final JMenu mnLoad = new JMenu("Load");
	private final JMenuItem mnLoadLog = new JMenuItem("Log");
	private final JPanel rightShapesPanel = new JPanel();
	private final JButton btnEdit = new JButton("Edit");
	private final JButton btnDelete = new JButton("Delete");
	private final JPanel leftBringToPanel = new JPanel();
	private final JButton btnToFront = new JButton("To Front");
	private final JButton btnToBack = new JButton("To Back");
	private final JButton btnBringToFront = new JButton("Bring to front");
	private final JButton btnBringToBack = new JButton("Bring to Back");
	private final JPanel logInfoPanel = new JPanel();
	private final JLabel lblMode = new JLabel(" ");
	private final JLabel lblXY = new JLabel(" ");

	public Paint(int width, int height) {
		this.setSize(698, 546);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));

		/**
		 * canvas mouse event handler
		 */
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				lblXY.setText("X: " + arg0.getX() + " Y: " + arg0.getY());
			}
		});
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.handleMouseClick(arg0);
			}
		});

		ButtonGroup bgShapes = new ButtonGroup();
		bgShapes.add(this.tbtnPoint);
		bgShapes.add(this.tbtnLine);
		bgShapes.add(this.tbtnSquare);
		bgShapes.add(this.tbtnRectangle);
		bgShapes.add(this.tbtnCircle);
		bgShapes.add(this.tbtnHexagon);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnDelete.isEnabled())
					controller.handleDelete();
			}
		});

		this.btnDelete.setEnabled(false);
		this.btnEdit.setEnabled(false);
		this.btnSelect.setEnabled(false);
		this.enableBringToButtons(false);

		this.topCommandsPanel.setBackground(Color.black);
		this.bottomLogPanel.setBackground(Color.red);
		this.rightShapesPanel.setBackground(Color.yellow);
		this.leftBringToPanel.setBackground(Color.GREEN);

		mainPanel.add(this.topCommandsPanel, BorderLayout.NORTH);

		/**
		 * undo button event handler
		 */
		btnUndo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnUndo.isEnabled())
					controller.undo();
			}
		});

		btnRedo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnRedo.isEnabled())
					controller.redo();
			}
		});

		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnEdit.isEnabled())
					controller.handleEdit();
			}
		});

		topCommandsPanel.add(btnUndo);
		topCommandsPanel.add(btnRedo);
		btnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnSelect.isEnabled())
					controller.handleChangeMode(PaintMode.SELECT);
			}
		});

		topCommandsPanel.add(btnSelect);

		topCommandsPanel.add(btnEdit);

		topCommandsPanel.add(btnDelete);

		this.setEnabledBtnUndo(false);
		this.setEnabledBtnRedo(false);
		bottomLogPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane spLog = new JScrollPane();
		spLog.setViewportView(this.commandList);
		this.bottomLogPanel.add(spLog, BorderLayout.CENTER);

		mainPanel.add(this.canvas, BorderLayout.CENTER);
		mainPanel.add(this.bottomLogPanel, BorderLayout.SOUTH);
		logInfoPanel.setBackground(SystemColor.info);
		
		bottomLogPanel.add(logInfoPanel, BorderLayout.NORTH);
		logInfoPanel.setLayout(new BorderLayout(0, 0));
		lblMode.setHorizontalAlignment(SwingConstants.LEFT);
		
		logInfoPanel.add(lblMode, BorderLayout.WEST);
		lblXY.setHorizontalAlignment(SwingConstants.RIGHT);
		
		logInfoPanel.add(lblXY, BorderLayout.EAST);

		mainPanel.add(rightShapesPanel, BorderLayout.EAST);

		getContentPane().add(mbMain, BorderLayout.NORTH);
		
		rightShapesPanel.setLayout(new BoxLayout(rightShapesPanel, BoxLayout.Y_AXIS));
		
		tbtnPoint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.handleChangeMode(PaintMode.POINT);
			}
		});
		rightShapesPanel.add(tbtnPoint);
		
		tbtnLine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.handleChangeMode(PaintMode.LINE);
			}
		});
		rightShapesPanel.add(tbtnLine);
		
		tbtnSquare.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.handleChangeMode(PaintMode.SQUARE);
			}
		});
		rightShapesPanel.add(tbtnSquare);
		
		tbtnRectangle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.handleChangeMode(PaintMode.RECTANGLE);
			}
		});
		rightShapesPanel.add(tbtnRectangle);
		
		tbtnCircle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.handleChangeMode(PaintMode.CIRCLE);
			}
		});
		rightShapesPanel.add(tbtnCircle);
		
		tbtnHexagon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.handleChangeMode(PaintMode.HEXAGON);
			}
		});
		rightShapesPanel.add(tbtnHexagon);
		
		mainPanel.add(leftBringToPanel, BorderLayout.WEST);
		leftBringToPanel.setLayout(new BoxLayout(leftBringToPanel, BoxLayout.Y_AXIS));
		btnToFront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnToFront.isEnabled()) controller.handleToFront();
			}
		});
		
		btnToBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnToBack.isEnabled()) controller.handleToBack();
			}
		});
		
		btnBringToFront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnBringToFront.isEnabled()) controller.handleBringToFront();
			}
		});
		
		btnBringToBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnBringToBack.isEnabled()) controller.handleBringToBack();
			}
		});
		
		leftBringToPanel.add(btnToFront);
		
		leftBringToPanel.add(btnToBack);
		
		leftBringToPanel.add(btnBringToFront);
		
		leftBringToPanel.add(btnBringToBack);
		
		tbtnLine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.handleChangeMode(PaintMode.LINE);
			}
		});

		mbMain.add(mnFile);

		mnFile.add(mnSave);
		mnSave.add(mnSaveLog);
		mnSave.add(mnSaveCanvas);

		mnFile.add(mnLoad);

		mnLoad.add(mnLoadLog);
		mnLoad.add(mnLoadCanvas);
		mnLoadCanvas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.handleLoadCanvas();
			}
		});
		mnLoadLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.handleLoadLog();
			}
		});

		mnSaveLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.handleSaveLog();
			}
		});

		mnSaveCanvas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.handleSaveCanvas();
			}
		});

	}

	public PaintController getController() {
		return controller;
	}

	public void setController(PaintController controller) {
		this.controller = controller;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public void setEnabledBtnUndo(boolean enabled) {
		this.btnUndo.setEnabled(enabled);
	}

	public void setEnabledBtnRedo(boolean enabled) {
		this.btnRedo.setEnabled(enabled);
	}

	public DefaultListModel<Command> getLogListModel() {
		return this.logListModel;
	}
	
	private void enableBringToButtons(boolean enable) {
		this.btnToBack.setEnabled(enable);
		this.btnToFront.setEnabled(enable);
		this.btnBringToBack.setEnabled(enable);
		this.btnBringToFront.setEnabled(enable);
	}

	/**
	 * Callback function called when observable emits changes
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable arg0, Object arg1) {
		
		if(arg1 instanceof String) {
			this.lblMode.setText(arg1.toString());
		} else {
			
		int counter = 0;
		List<Shape> shapes = (List<Shape>) arg1;
		for (Shape shape : shapes) {
			if (shape.isSelected())
				counter++;
		}

		this.btnDelete.setEnabled(counter > 0);
		this.btnEdit.setEnabled(counter == 1);
		this.btnSelect.setEnabled(shapes.size() > 0);
		this.enableBringToButtons(counter == 1);
		}
	}

}
