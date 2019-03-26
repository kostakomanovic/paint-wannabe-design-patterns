package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.controller.PaintController;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Paint extends JFrame {

	private PaintController controller;

	private Canvas canvas = new Canvas();
	private JPanel topCommandsPanel = new JPanel();
	
	/**
	 * Undo Redo Navigation
	 */
	private final JButton btnRedo = new JButton("Redo");
	private final JButton btnUndo = new JButton("Undo");

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
		
		mainPanel.add(this.canvas, BorderLayout.CENTER);

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
	
	public void setEnabledBtnRedo(boolean enabled)  {
		this.btnRedo.setEnabled(enabled);
	}
	
}
