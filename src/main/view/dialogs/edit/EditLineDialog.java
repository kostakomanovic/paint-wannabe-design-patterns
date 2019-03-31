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

import main.model.shape.Line;
import main.model.shape.base.Shape;
import main.view.dialogs.EditDialog;

public class EditLineDialog extends JDialog implements EditDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7751780953179156198L;
	private JTextField txtXStart;
	private JTextField txtYStart;
	private JTextField txtXEnd;
	private JTextField txtYEnd;
	private JButton btnColor;
	private Line line;
	

	public EditLineDialog(Line line) {

		setTitle("Edit Line");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 300);
		
		this.line = line.clone();

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblXStart = new JLabel("Enter Start X:");
		lblXStart.setBounds(10, 15, 170, 25);
		panel.add(lblXStart);

		JLabel lblYStart = new JLabel("Enter Start Y:");
		lblYStart.setBounds(10, 55, 170, 25);
		panel.add(lblYStart);

		JLabel lblXEnd = new JLabel("Enter End X:");
		lblXEnd.setBounds(10, 95, 170, 25);
		panel.add(lblXEnd);

		JLabel lblYEnd = new JLabel("Enter End Y:");
		lblYEnd.setBounds(10, 135, 170, 25);
		panel.add(lblYEnd);

		JLabel lblColor = new JLabel("Choose color");
		lblColor.setBounds(10, 175, 170, 25);
		panel.add(lblColor);

		txtXStart = new JTextField();
		txtXStart.setBounds(200, 15, 224, 25);
		txtXStart.setText(Integer.toString(line.getStartingPoint().getX()));
		panel.add(txtXStart);
		txtXStart.setColumns(10);

		txtYStart = new JTextField();
		txtYStart.setBounds(200, 55, 224, 25);
		txtYStart.setText(Integer.toString(line.getStartingPoint().getY()));
		panel.add(txtYStart);
		txtYStart.setColumns(10);

		txtXEnd = new JTextField();
		txtXEnd.setBounds(200, 95, 224, 25);
		txtXEnd.setText(Integer.toString(line.getEndingPoint().getX()));
		panel.add(txtXEnd);
		txtXEnd.setColumns(10);

		txtYEnd = new JTextField();
		txtYEnd.setBounds(200, 135, 224, 25);
		txtYEnd.setText(Integer.toString(line.getEndingPoint().getY()));
		panel.add(txtYEnd);
		txtYEnd.setColumns(10);

		btnColor = new JButton();
		btnColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setColor(btnColor);
			}
		});
		btnColor.setBounds(200, 175, 224, 25);
		btnColor.setBackground(line.getColor());
		panel.add(btnColor);

		JButton btnOk = new JButton("Ok");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (txtXStart.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(getContentPane(), "Enter X Start", "Error!",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else if (txtYStart.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(getContentPane(), "Enter Y Start!", "Error!",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else if (txtXEnd.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(getContentPane(), "Enter X End!", "Error!",
							JOptionPane.WARNING_MESSAGE);
				} else if (txtYEnd.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(getContentPane(), "Enter Y End!", "Error!",
							JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						int xS = Integer.parseInt(txtXStart.getText().trim());
						int yS = Integer.parseInt(txtYStart.getText().trim());
						int xE = Integer.parseInt(txtXEnd.getText().trim());
						int yE = Integer.parseInt(txtYEnd.getText().trim());
						if (xS <= 0) {
							JOptionPane.showMessageDialog(getContentPane(), "X Start has to be larger than 0",
									"Error!", JOptionPane.WARNING_MESSAGE);
						} else if (yS <= 0) {
							JOptionPane.showMessageDialog(getContentPane(), "Y Start has to be larger than 0",
									"Error!", JOptionPane.WARNING_MESSAGE);
						} else if (xE <= 0) {
							JOptionPane.showMessageDialog(getContentPane(), "X End has to be larger than 0",
									"Error!", JOptionPane.WARNING_MESSAGE);
						} else if (yE <= 0) {
							JOptionPane.showMessageDialog(getContentPane(), "Y End has to be larger than 0",
									"Error!", JOptionPane.WARNING_MESSAGE);
						} else {
							line.getStartingPoint().setX(xS);
							line.getStartingPoint().setY(yS);
							line.getEndingPoint().setX(xE);
							line.getEndingPoint().setY(yE);
							line.setColor(btnColor.getBackground());
							
							dispose();
						}

					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(panel, "X and Y coordinates have to be integers", "Error!",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnOk.setBounds(35, 220, 89, 25);
		panel.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		btnCancel.setBounds(248, 220, 89, 25);
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
		return this.line;
	}
}
