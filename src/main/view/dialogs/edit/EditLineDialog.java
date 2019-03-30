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

import main.model.shape.Line;
import main.model.shape.base.Shape;
import main.view.dialogs.EditDialog;

public class EditLineDialog extends JDialog implements EditDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7751780953179156198L;
	public JTextField tfXFirst, tfYFirst, tfXSecond, tfYSecond;
	public JButton btnColor;
	private boolean sacuvano = false;
	
	private Line line;

	public EditLineDialog(JFrame parent) {
		super(parent, "Edit line", true);

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
		JLabel lblXPrva = new JLabel("First point  X:");
		jpMain.add(lblXPrva, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		tfXFirst = new JTextField(10);
		tfXFirst.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (Character.isDigit(ke.getKeyChar())) { // unet je broj
					if (tfXFirst.getText().length() > 4) {
						tooLargeNumberEntered(tfXFirst);
					}
				} else { // nije unet broj
					if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) { // pritisnut
																												// backspace
						return;
					}
					notNumberInserted(tfXFirst);
				}
			}
		});
		jpMain.add(tfXFirst, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		JLabel lblYPrva = new JLabel("First point Y:");
		jpMain.add(lblYPrva, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		tfYFirst = new JTextField(10);
		tfYFirst.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (Character.isDigit(ke.getKeyChar())) {
					if (tfYFirst.getText().length() > 4) {
						tooLargeNumberEntered(tfYFirst);
					}
				} else {
					if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) {
						return;
					}
					notNumberInserted(tfYFirst);
				}
			}
		});
		jpMain.add(tfYFirst, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		JLabel lblXDruga = new JLabel("Second point X:");
		jpMain.add(lblXDruga, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		tfXSecond = new JTextField(10);
		tfXSecond.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (Character.isDigit(ke.getKeyChar())) {
					if (tfXSecond.getText().length() > 4) {
						tooLargeNumberEntered(tfXSecond);
					}
				} else {
					if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) {
						return;
					}
					notNumberInserted(tfXSecond);
				}
			}
		});
		jpMain.add(tfXSecond, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		JLabel lblYDruga = new JLabel("Second point Y:");
		jpMain.add(lblYDruga, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		tfYSecond = new JTextField(10);
		tfYSecond.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (Character.isDigit(ke.getKeyChar())) {
					if (tfYSecond.getText().length() > 4) {
						tooLargeNumberEntered(tfYSecond);
					}
				} else {
					if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) {
						return;
					}
					notNumberInserted(tfYSecond);
				}
			}
		});
		jpMain.add(tfYSecond, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		JLabel lblColor = new JLabel("Color:");
		jpMain.add(lblColor, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		btnColor = new JButton(" ");
		jpMain.add(btnColor, gbc);
		btnColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				EditLineDialog.this.boja(btnColor);
			}
		});

		JButton btnOk = new JButton("Save");
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 5;
		jpMain.add(btnOk, gbc);

		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				line.setColor(btnColor.getBackground());
				line.moveBothPoints(Integer.parseInt(tfXFirst.getText()), Integer.parseInt(tfYFirst.getText()), Integer.parseInt(tfXSecond.getText()), Integer.parseInt(tfYSecond.getText()));
				dispose();
			}
		});


		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(parent);
	}

	private void insertInfo(Line temp) {
		this.tfXFirst.setText(String.valueOf(temp.getStartingPoint().getX()));
		this.tfYFirst.setText(String.valueOf(temp.getStartingPoint().getY()));
		this.tfXSecond.setText(String.valueOf(temp.getEndingPoint().getX()));
		this.tfYSecond.setText(String.valueOf(temp.getEndingPoint().getY()));
		this.btnColor.setBackground(temp.getColor());
	}

	private void boja(JButton btnColor) {
		JColorChooser jccColor = new JColorChooser();
		Color color = jccColor.showDialog(null, "Choose color", btnColor.getBackground());

		if (color != null)
			btnColor.setBackground(color);

	}

	public boolean getSacuvano() {
		return this.sacuvano;
	}

	private void notNumberInserted(JTextField tf) {
		JOptionPane.showMessageDialog(this, "Only numbers allowed!!", "Error", JOptionPane.ERROR_MESSAGE);
		tf.setText(tf.getText().substring(0, tf.getText().length() - 1));
	}

	private void tooLargeNumberEntered(JTextField tf) {
		JOptionPane.showMessageDialog(this, "Number is too large!", "Error", JOptionPane.ERROR_MESSAGE);
		tf.setText(tf.getText().substring(0, tf.getText().length() - 1));
	}

	@Override
	public Shape getEditedShape() {
		return this.line;
	}
	
	public void setLine(Line line) {
		this.line = line;
		this.tfXFirst.setText(String.valueOf(line.getStartingPoint().getX()));
		this.tfYFirst.setText(String.valueOf(line.getStartingPoint().getY()));
		this.tfXSecond.setText(String.valueOf(line.getEndingPoint().getX()));
		this.tfYSecond.setText(String.valueOf(line.getEndingPoint().getY()));
		this.btnColor.setBackground(line.getColor());
		
	}

}
