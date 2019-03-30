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

import main.model.shape.Circle;
import main.model.shape.base.Shape;
import main.view.dialogs.EditDialog;

public class EditCircleDialog extends JDialog implements EditDialog {

	public JTextField tfX, tfY, tfRadius;
	public JButton btnFillColor, btnColor;
	
	private Circle circle;

	public EditCircleDialog(JFrame parent) {
		super(parent, "Edit circle", true);

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
		JLabel lblXPrva = new JLabel("X:");
		jpMain.add(lblXPrva, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		tfX = new JTextField(10);
		tfX.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (Character.isDigit(ke.getKeyChar())) { // unet je broj
					if (tfX.getText().length() > 4) {
						tooLargeNumberEntered(tfX);
					}
				} else { // nije unet broj
					if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) { // pritisnut
																												// backspace
						return;
					}
					notNumberInserted(tfX);
				}
			}
		});
		jpMain.add(tfX, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		JLabel lblYPrva = new JLabel("Y:");
		jpMain.add(lblYPrva, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		tfY = new JTextField(10);
		tfY.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (Character.isDigit(ke.getKeyChar())) { // unet je broj
					if (tfY.getText().length() > 4) {
						tooLargeNumberEntered(tfY);
					}
				} else { // nije unet broj
					if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) { // pritisnut
																												// backspace
						return;
					}
					notNumberInserted(tfY);
				}
			}
		});
		jpMain.add(tfY, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		JLabel lblXDruga = new JLabel("Poluprecnik:");
		jpMain.add(lblXDruga, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		tfRadius = new JTextField(10);
		tfRadius.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (Character.isDigit(ke.getKeyChar())) { // unet je broj
					if (tfRadius.getText().length() > 4) {
						tooLargeNumberEntered(tfRadius);
					}
				} else { // nije unet broj
					if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) { // pritisnut
																												// backspace
						return;
					}
					notNumberInserted(tfRadius);
				}
			}
		});
		jpMain.add(tfRadius, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		JLabel lblBojaOkvira = new JLabel("Color:");
		jpMain.add(lblBojaOkvira, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		btnColor = new JButton(" ");
		jpMain.add(btnColor, gbc);
		btnColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				EditCircleDialog.this.color(btnColor);
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 4;
		JLabel lblBoja = new JLabel("Fill Color:");
		jpMain.add(lblBoja, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		btnFillColor = new JButton(" ");
		jpMain.add(btnFillColor, gbc);
		btnFillColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				EditCircleDialog.this.color(btnFillColor);
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
				circle.moveTo(Integer.parseInt(tfX.getText()), Integer.parseInt(tfY.getText()));
				circle.setRadius(Integer.parseInt(tfRadius.getText()));
				circle.setColor(btnColor.getBackground());
				circle.setFillColor(btnFillColor.getBackground());
				dispose();
			}
		});

		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(parent);
	}



	private void color(JButton btnColor) {
		JColorChooser jccUnutrasnjost = new JColorChooser();
		Color colorUnutrasnjost = jccUnutrasnjost.showDialog(null, "Izaberite boju", btnColor.getBackground());

		if (colorUnutrasnjost != null)
			btnColor.setBackground(colorUnutrasnjost);

	}

	private void notNumberInserted(JTextField tf) {
		JOptionPane.showMessageDialog(this, "Only numbers allowed!", "Error", JOptionPane.ERROR_MESSAGE);
		tf.setText(tf.getText().substring(0, tf.getText().length() - 1));
	}

	private void tooLargeNumberEntered(JTextField tf) {
		JOptionPane.showMessageDialog(this, "Number is too big!", "Error", JOptionPane.ERROR_MESSAGE);
		tf.setText(tf.getText().substring(0, tf.getText().length() - 1));
	}
	
	public void setCircle(Circle circle) {
		this.circle = circle;
		this.tfX.setText(String.valueOf(circle.getCenter().getX()));
		this.tfY.setText(String.valueOf(circle.getCenter().getY()));
		this.tfRadius.setText(String.valueOf(circle.getRadius()));
		this.btnColor.setBackground(circle.getColor());
		this.btnFillColor.setBackground(circle.getFillColor());
	}

	@Override
	public Shape getEditedShape() {
		return this.circle;
	}

}