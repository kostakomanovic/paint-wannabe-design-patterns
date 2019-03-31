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
import main.model.shape.base.Shape;
import main.view.dialogs.EditDialog;

public class EditPointDialog extends JDialog implements EditDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5567244405970261868L;

	private JTextField txtX, txtY;
	private JButton btnColor;
	private Point point;
	
	public EditPointDialog(Point point) {

		setTitle("Edit point");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 250);
		
		this.point = point;

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblEnterX = new JLabel("Enter X coordinate:");
		lblEnterX.setBounds(10, 15, 170, 25);
		panel.add(lblEnterX);

		JLabel lblEnterY = new JLabel("Enter Y coordinate:");
		lblEnterY.setBounds(10, 55, 170, 25);
		panel.add(lblEnterY);

		JLabel lblChooseColor = new JLabel("Choose color:");
		lblChooseColor.setBounds(10, 95, 170, 25);
		panel.add(lblChooseColor);

		txtX = new JTextField();
		txtX.setBounds(200, 15, 224, 25);
		txtX.setText(Integer.toString(point.getX()));
		panel.add(txtX);
		txtX.setColumns(10);

		txtY = new JTextField();
		txtY.setBounds(200, 55, 224, 25);
		txtY.setText(Integer.toString(point.getY()));
		panel.add(txtY);
		txtY.setColumns(10);

		btnColor = new JButton();
		btnColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setColor(btnColor);
			}
		});
		btnColor.setBounds(200, 95, 224, 25);
		btnColor.setBackground(point.getColor());

		panel.add(btnColor);
		JButton btnOk = new JButton("Ok");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (txtX.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(getContentPane(), "Enter x!", "Error!", JOptionPane.WARNING_MESSAGE);
					return;
				} else if (txtY.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(getContentPane(), "Enter y!", "Error!", JOptionPane.WARNING_MESSAGE);
					return;

				} else {
					try {
						int x1 = Integer.parseInt(txtX.getText().trim());
						int y1 = Integer.parseInt(txtY.getText().trim());
						if (x1 <= 0) {
							JOptionPane.showMessageDialog(getContentPane(), "X has to be larger than 0", "Error!",
									JOptionPane.WARNING_MESSAGE);
						} else if (y1 <= 0) {
							JOptionPane.showMessageDialog(getContentPane(), "Y has to be larger than 0", "Error!",
									JOptionPane.WARNING_MESSAGE);
						} else {
							point.setX(x1);
							point.setY(y1);
							point.setColor(btnColor.getBackground());
							dispose();
						}

					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(panel, "X and Y have to be integers!", "Error!",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnOk.setBounds(35, 180, 89, 25);
		panel.add(btnOk);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		btnCancel.setBounds(247, 180, 89, 25);
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
		return this.point;
	}

}
