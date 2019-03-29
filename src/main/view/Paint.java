package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.channels.SelectableChannel;

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
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;

public class Paint extends JFrame {

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
	
	private final JMenuBar mbMain = new JMenuBar();
	private final JMenu mnFile = new JMenu("File");
	private final JMenuItem mnSaveCanvas = new JMenuItem("Canvas");
	private final JMenuItem mnLoadCanvas = new JMenuItem("Canvas");
	private final JMenuItem mnSaveLog = new JMenuItem("Log");
	private final JMenu mnSave = new JMenu("Save");
	private final JMenu mnLoad = new JMenu("Load");
	private final JMenuItem mnLoadLog = new JMenuItem("Log");
	private final JPanel rightShapesPanel = new JPanel();

	public Paint(int width, int height) {
		this.setSize(520, 409);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));

		/**
		 * canvas mouse event handler
		 */
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.handleMouseClick(arg0);
			}
		});
		
		ButtonGroup bgShapes = new ButtonGroup();
		bgShapes.add(this.tbtnPoint);

		this.topCommandsPanel.setBackground(Color.black);
		this.bottomLogPanel.setBackground(Color.red);
		this.rightShapesPanel.setBackground(Color.yellow);

		
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

		topCommandsPanel.add(btnUndo);
		topCommandsPanel.add(btnRedo);
		btnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 	{
				if(btnSelect.isEnabled()) controller.handleStartSelect();
			}
		});
		
		topCommandsPanel.add(btnSelect);

		this.setEnabledBtnUndo(false);
		this.setEnabledBtnRedo(false);

		JScrollPane spLog = new JScrollPane();
		spLog.setViewportView(this.commandList);
		this.bottomLogPanel.add(spLog);

		mainPanel.add(this.canvas, BorderLayout.CENTER);
		mainPanel.add(this.bottomLogPanel, BorderLayout.SOUTH);
		
		mainPanel.add(rightShapesPanel, BorderLayout.EAST);

		getContentPane().add(mbMain, BorderLayout.NORTH);
		rightShapesPanel.setLayout(new BoxLayout(rightShapesPanel, BoxLayout.Y_AXIS));
		rightShapesPanel.add(tbtnPoint);
		rightShapesPanel.add(tbtnLine);
		
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

}
