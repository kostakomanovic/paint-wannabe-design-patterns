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

import main.model.shape.Rectangle;
import main.model.shape.base.Shape;
import main.view.dialogs.EditDialog;

public class EditRectangleDialog extends JDialog implements EditDialog {

	private static final long serialVersionUID = 5786643812552701632L;
	public JTextField tfX, tfY, tfHeight, tfWidth;
	public JButton btnFillColor, btnColor;
	private boolean sacuvano = false;
	
	private Rectangle rectangle;

	public EditRectangleDialog(JFrame parent) {
		super(parent, "Edit Rectangle", true);

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
		JLabel lblXVisina = new JLabel("Height:");
		jpMain.add(lblXVisina, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		tfHeight = new JTextField(10);
		tfHeight.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (Character.isDigit(ke.getKeyChar())) { // unet je broj
					if(tfHeight.getText().length() > 4) {
						tooLargeNumberEntered(tfHeight);
					}
				} else { // nije unet broj
					if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) { // pritisnut backspace
						return;
					}
					notNumberInserted(tfHeight);
				}
			}
		});
		jpMain.add(tfHeight, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		JLabel lblSirina = new JLabel("Width:");
		jpMain.add(lblSirina, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
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
		gbc.gridy = 4;
		JLabel lblBojaOkvira = new JLabel("Color:");
		jpMain.add(lblBojaOkvira, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		btnColor = new JButton(" ");
		jpMain.add(btnColor, gbc);
		btnColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				EditRectangleDialog.this.boja(btnColor);
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 5;
		JLabel lblBoja = new JLabel("Fill color:");
		jpMain.add(lblBoja, gbc);
	
		gbc.gridx = 1;
		gbc.gridy = 5;
		btnFillColor = new JButton(" ");
		jpMain.add(btnFillColor, gbc);
		btnFillColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				EditRectangleDialog.this.boja(btnFillColor);
			}
		});

		JButton btnOk = new JButton("Save");
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 6;
		jpMain.add(btnOk, gbc);

		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				rectangle.moveTo(Integer.parseInt(tfX.getText()), Integer.parseInt(tfY.getText()));
				rectangle.setColor(btnColor.getBackground());
				rectangle.setFillColor(btnFillColor.getBackground());
				rectangle.setWidth(Integer.parseInt(tfWidth.getText()));
				rectangle.setHeight(Integer.parseInt(tfHeight.getText()));
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
		JOptionPane.showMessageDialog(this, "Only number allowed!", "Error",
				JOptionPane.ERROR_MESSAGE);
		tf.setText(tf.getText().substring(0, tf.getText().length() - 1));
	}

	private void tooLargeNumberEntered(JTextField tf) {
		JOptionPane.showMessageDialog(this, "Numer is too big!", "Error", JOptionPane.ERROR_MESSAGE);
		tf.setText(tf.getText().substring(0, tf.getText().length() - 1));
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
		this.tfX.setText(String.valueOf(rectangle.getOrigin().getX()));
		this.tfY.setText(String.valueOf(rectangle.getOrigin().getY()));
		this.tfHeight.setText(String.valueOf(rectangle.getHeight()));
		this.tfWidth.setText(String.valueOf(rectangle.getWidth()));
		this.btnColor.setBackground(rectangle.getColor());
		this.btnFillColor.setBackground(rectangle.getFillColor());
	}


	@Override
	public Shape getEditedShape() {
		return this.rectangle;
	}

}
