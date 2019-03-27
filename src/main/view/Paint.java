package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.controller.PaintController;
import main.model.command.Command;

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
	 * Undo Redo Navigation
	 */
	private final JButton btnRedo = new JButton("Redo");
	private final JButton btnUndo = new JButton("Undo");
	private final JMenuBar mbMain = new JMenuBar();
	private final JMenu mnFile = new JMenu("File");
	private final JMenuItem mnSave = new JMenuItem("Save...");
	private final JMenuItem mnLoad = new JMenuItem("Load...");

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

		this.topCommandsPanel.setBackground(Color.black);
		this.bottomLogPanel.setBackground(Color.red);

		mainPanel.add(this.topCommandsPanel, BorderLayout.NORTH);

		/**
		 * undo button event handler
		 */
		btnUndo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.undo();
			}
		});

		btnRedo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.redo();
			}
		});

		topCommandsPanel.add(btnUndo);
		topCommandsPanel.add(btnRedo);

		this.setEnabledBtnUndo(false);
		this.setEnabledBtnRedo(false);

		JScrollPane spLog = new JScrollPane();
		spLog.setViewportView(this.commandList);
		this.bottomLogPanel.add(spLog);

		mainPanel.add(this.canvas, BorderLayout.CENTER);
		mainPanel.add(this.bottomLogPanel, BorderLayout.SOUTH);
		
		getContentPane().add(mbMain, BorderLayout.NORTH);
		
		mbMain.add(mnFile);
		mnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.handleSave();
			}
		});
		
		mnFile.add(mnSave);
		mnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.handleLoad();
			}
		});
		
		mnFile.add(mnLoad);

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
