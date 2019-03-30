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

import main.model.shape.Square;
import main.model.shape.base.Shape;
import main.view.dialogs.EditDialog;

public class EditSquareDialog extends JDialog implements EditDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4142289500776050313L;
	public JTextField tfX, tfY, tfWidth;
	public JButton btnFillColor, btnColor;
	private Square square;
	private boolean sacuvano = false;

	public EditSquareDialog(JFrame parent) {
		super(parent, "Edit square", true);

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
					if(tfX.getText().length() > 4) {
						tooLargeNumberEntered(tfX);
					}
				} else { // nije unet broj
					if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) { // pritisnut backspace
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
					if(tfY.getText().length() > 4) {
						tooLargeNumberEntered(tfY);
					}
				} else { // nije unet broj
					if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) { // pritisnut backspace
						return;
					}
					notNumberInserted(tfY);
				}
			}
		});
		jpMain.add(tfY, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		JLabel lblXDruga = new JLabel("Stranica:");
		jpMain.add(lblXDruga, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		tfWidth = new JTextField(10);
		tfWidth.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (Character.isDigit(ke.getKeyChar())) { // unet je broj
					if(tfWidth.getText().length() > 4) {
						tooLargeNumberEntered(tfWidth);
					}
				} else { // nije unet broj
					if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) { // pritisnut backspace
						return;
					}
					notNumberInserted(tfWidth);
				}
			}
		});
		jpMain.add(tfWidth, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		JLabel lblBojaOkvira = new JLabel("Boja okvira:");
		jpMain.add(lblBojaOkvira, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		btnColor = new JButton(" ");
		jpMain.add(btnColor, gbc);
		btnColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				EditSquareDialog.this.boja(btnColor);
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 4;
		JLabel lblBoja = new JLabel("Boja unutrasnjosti:");
		jpMain.add(lblBoja, gbc);
	
		gbc.gridx = 1;
		gbc.gridy = 4;
		btnFillColor = new JButton(" ");
		jpMain.add(btnFillColor, gbc);
		btnFillColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				EditSquareDialog.this.boja(btnFillColor);
			}
		});

		JButton btnPotvrdi = new JButton("Sacuvaj");
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 5;
		jpMain.add(btnPotvrdi, gbc);

		btnPotvrdi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				square.moveTo(Integer.parseInt(tfX.getText()), Integer.parseInt(tfY.getText()));
				square.setWidth(Integer.parseInt(tfWidth.getText()));
				square.setColor(btnColor.getBackground());
				square.setFillColor(btnFillColor.getBackground());
				dispose();
			}
		});


		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(parent);
	}

	private void boja(JButton btnBoja) {
		JColorChooser jccUnutrasnjost = new JColorChooser();
		Color colorUnutrasnjost = jccUnutrasnjost.showDialog(null, "Izaberite boju", btnBoja.getBackground());

		if (colorUnutrasnjost != null)
			btnBoja.setBackground(colorUnutrasnjost);

	}

	public boolean getSacuvano() {
		return this.sacuvano;
	}
	
	private void notNumberInserted(JTextField tf) {
		JOptionPane.showMessageDialog(this, "Mozete uneti samo brojeve!", "Greska",
				JOptionPane.ERROR_MESSAGE);
		tf.setText(tf.getText().substring(0, tf.getText().length() - 1));
	}

	private void tooLargeNumberEntered(JTextField tf) {
		JOptionPane.showMessageDialog(this, "To je prevelik broj!", "Greska", JOptionPane.ERROR_MESSAGE);
		tf.setText(tf.getText().substring(0, tf.getText().length() - 1));
	}

	public void setSquare(Square square) {
		this.square = square;
		this.tfX.setText(String.valueOf(square.getOrigin().getX()));
		this.tfY.setText(String.valueOf(square.getOrigin().getY()));
		this.tfWidth.setText(String.valueOf(square.getWidth()));
		this.btnColor.setBackground(square.getColor());
		this.btnFillColor.setBackground(square.getFillColor());
	}
	
	@Override
	public Shape getEditedShape() {
		return this.square;
	}

}
