package main.view.dialogs.edit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.model.shape.Point;
import main.model.shape.base.Shape;
import main.view.dialogs.EditDialog;

public class EditPointDialog extends JDialog implements EditDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5567244405970261868L;

	public JTextField tfX;
	public JTextField tfY;
	public JButton btnColor;

	Point point;

	public EditPointDialog(JFrame parent) {
		super(parent, "Edit point", true);

		JPanel jpMain = new JPanel();
		jpMain.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(jpMain, BorderLayout.NORTH);
		GridBagLayout gbl_jpMain = new GridBagLayout();
		jpMain.setLayout(gbl_jpMain);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel lblX = new JLabel("X:");
		jpMain.add(lblX, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		tfX = new JTextField(10);
		tfX.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (Character.isDigit(ke.getKeyChar())) {
					if (tfX.getText().length() > 4) {
						EditPointDialog.this.tooLargeNumberEntered(tfX);
					}
				} else {
					if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) {
						return;
					}
					EditPointDialog.this.notNumberInserted(tfX);
				}
			}
		});
		jpMain.add(tfX, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		JLabel lblY = new JLabel("Y:");
		jpMain.add(lblY, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		tfY = new JTextField(10);
		tfY.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (Character.isDigit(ke.getKeyChar())) {
					if (tfY.getText().length() > 4) {
						EditPointDialog.this.tooLargeNumberEntered(tfY);
					}
				} else {
					if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) { // pritisnut
																												// backspace
						return;
					}
					EditPointDialog.this.notNumberInserted(tfY);
				}
			}
		});
		jpMain.add(tfY, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		JLabel lblColor = new JLabel("Color:");
		jpMain.add(lblColor, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		btnColor = new JButton(" ");
		jpMain.add(btnColor, gbc);
		btnColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				EditPointDialog.this.color(btnColor);
			}
		});

		JButton btnOk = new JButton("Save");
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 3;
		jpMain.add(btnOk, gbc);

		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				point.setColor(btnColor.getBackground());
				point.moveTo(Integer.parseInt(tfX.getText()), Integer.parseInt(tfY.getText()));
				dispose();
			}
		});

		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(parent);
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
		this.tfX.setText(String.valueOf(point.getX()));
		this.tfY.setText(String.valueOf(point.getY()));
		this.btnColor.setBackground(point.getColor());
	}

	private void color(JButton btnColor) {
		JColorChooser jccColor = new JColorChooser();
		Color color = jccColor.showDialog(null, "Choose color", btnColor.getBackground());

		if (color != null)
			this.btnColor.setBackground(color);

	}

	@Override
	public Shape getEditedShape() {
		return this.point;
	}

	private void notNumberInserted(JTextField tf) {
		JOptionPane.showMessageDialog(this, "Only number allowed!", "Error", JOptionPane.ERROR_MESSAGE);
		tf.setText(tf.getText().substring(0, tf.getText().length() - 1));
	}

	private void tooLargeNumberEntered(JTextField tf) {
		JOptionPane.showMessageDialog(this, "Number is too big!", "Error", JOptionPane.ERROR_MESSAGE);
		tf.setText(tf.getText().substring(0, tf.getText().length() - 1));
	}

}
