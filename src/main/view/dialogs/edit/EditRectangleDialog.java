package main.view.dialogs.edit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.model.shape.Point;
import main.model.shape.Rectangle;
import main.model.shape.base.Shape;
import main.view.dialogs.EditDialog;

public class EditRectangleDialog extends JDialog implements EditDialog {

	private JTextField txtX, txtY, txtLen, txtHei;
	private JButton btnColor, btnFillColor;
	private Rectangle rectangle;
	
	public Rectangle getRectangle() {
		return rectangle;
	}

	public EditRectangleDialog(Rectangle rectangle) {

		setTitle("Edit Rectangle");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 340);
		
		this.rectangle = rectangle.clone();

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblX = new JLabel("Enter x:");
		lblX.setBounds(10, 15, 170, 25);
		panel.add(lblX);

		JLabel lblY = new JLabel("Enter y:");
		lblY.setBounds(10, 55, 170, 25);
		panel.add(lblY);

		JLabel lblLen = new JLabel("Enter length:");
		lblLen.setBounds(10, 95, 170, 25);
		panel.add(lblLen);

		JLabel lblHei = new JLabel("Enter height:");
		lblHei.setBounds(10, 135, 170, 25);
		panel.add(lblHei);

		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(10, 175, 170, 25);
		panel.add(lblColor);

		JLabel lblFillColor = new JLabel("Fill Color:");
		lblFillColor.setBounds(10, 215, 170, 25);
		panel.add(lblFillColor);

		txtX = new JTextField();
		txtX.setBounds(200, 15, 224, 25);
		txtX.setText(Integer.toString(rectangle.getOrigin().getX()));
		panel.add(txtX);
		txtX.setColumns(10);

		txtY = new JTextField();
		txtY.setBounds(200, 55, 224, 25);
		txtY.setText(Integer.toString(rectangle.getOrigin().getY()));
		panel.add(txtY);
		txtY.setColumns(10);

		txtLen = new JTextField();
		txtLen.setBounds(200, 95, 224, 25);
		txtLen.setText(Integer.toString(rectangle.getWidth()));
		panel.add(txtLen);
		txtLen.setColumns(10);

		txtHei = new JTextField();
		txtHei.setBounds(200, 135, 224, 25);
		txtHei.setText(Integer.toString(rectangle.getHeight()));
		panel.add(txtHei);
		txtHei.setColumns(10);

		btnColor = new JButton();
		btnColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setColor(btnColor);
			}
		});
		btnColor.setBounds(200, 175, 224, 25);
		btnColor.setBackground(rectangle.getColor());
		panel.add(btnColor);

		btnFillColor = new JButton();
		btnFillColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setColor(btnFillColor);
			}
		});
		btnFillColor.setBounds(200, 215, 224, 25);
		btnFillColor.setBackground(rectangle.getFillColor());
		panel.add(btnFillColor);

		JButton btnOk = new JButton("Ok");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (txtX.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(getContentPane(), "Enter X!", "Error!", JOptionPane.WARNING_MESSAGE);
					return;
				} else if (txtY.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(getContentPane(), "Enter Y!", "Error!", JOptionPane.WARNING_MESSAGE);
					return;

				} else if (txtLen.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(getContentPane(), "Enter length!", "Error!",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else if (txtHei.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(getContentPane(), "Enter height!", "Error!",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					try {
						int x1 = Integer.parseInt(txtX.getText().trim());
						int y1 = Integer.parseInt(txtY.getText().trim());
						int len1 = Integer.parseInt(txtLen.getText().trim());
						int hei1 = Integer.parseInt(txtHei.getText().trim());

						if (x1 <= 0) {
							JOptionPane.showMessageDialog(getContentPane(), "X has to be larger than 0", "Error!",
									JOptionPane.WARNING_MESSAGE);
						} else if (y1 <= 0) {
							JOptionPane.showMessageDialog(getContentPane(), "Y  has to be larger than 0", "Error!",
									JOptionPane.WARNING_MESSAGE);
						} else if (len1 <= 0) {
							JOptionPane.showMessageDialog(getContentPane(), "Length has to be larger than 0", "Error!",
									JOptionPane.WARNING_MESSAGE);
						} else if (hei1 <= 0) {
							JOptionPane.showMessageDialog(getContentPane(), "Height has to be larger than 0", "Error!",
									JOptionPane.WARNING_MESSAGE);
						} else {
							rectangle.setOrigin(new Point(x1,y1));
							rectangle.setWidth(len1);
							rectangle.setHeight(hei1);
							rectangle.setColor(btnColor.getBackground());
							rectangle.setFillColor(btnFillColor.getBackground());
							
							dispose();
						}

					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(getContentPane(), "X, Y and length have to be integers!",
								"Error!", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnOk.setBounds(35, 270, 89, 25);
		panel.add(btnOk);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(248, 270, 89, 25);
		panel.add(btnCancel);
	}

	public void setColor(JButton btnColor) {
		JColorChooser jCCh = new JColorChooser();
		Color col = jCCh.showDialog(null, "Choose Color!", Color.BLACK);
		if (col != null) {
			btnColor.setBackground(col);
		}
	}

	@Override
	public Shape getEditedShape() {
		return this.rectangle;
	}

}
