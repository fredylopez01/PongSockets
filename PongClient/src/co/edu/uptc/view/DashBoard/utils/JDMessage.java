package co.edu.uptc.view.DashBoard.utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JDMessage extends JDialog {
	private static final long serialVersionUID = 1L;
	private ImageIcon imgMessage;
	private JLabel lblMessage;
	private JButton ok;
    private JLabel lblColor;
    private JTextField txtColor;
	
	public JDMessage(ActionListener listener, JFrame father) {
		super(father, true);
		setTitle("Confirmation Message");
		setSize(400, 300);
		setLocation(500, 275);
		getContentPane().setBackground(new Color(0x55ddff));
		initComponents(listener);
	}

	private void initComponents(ActionListener listener) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		imgMessage = new ImageIcon(getClass().getResource("/co/edu/uptc/view/images/advertence.png"));
		
		lblMessage = new JLabel("¿Empezar el juego?");
		lblMessage.setIcon(imgMessage);
		lblMessage.setFont(new Font("Sans Serif", Font.PLAIN, 16));
		lblMessage.setForeground(new Color(0x230443));
		gbc.insets = new Insets(3, 0, 15, 0);
		add(lblMessage, gbc);

		lblColor = new JLabel("Color:");
		lblColor.setFont(new Font("Sans Serif", Font.PLAIN, 16));
		gbc.gridy = 2;
		add(lblColor, gbc);

		txtColor = new JTextField(10);
		txtColor.setFont(new Font("Sans Serif", Font.PLAIN, 16));
		gbc.gridy = 3;
		add(txtColor, gbc);
		
		ok = new JButton("Yes");
		ok.setIconTextGap(5);
		styleButton(ok, "play", listener, new Insets(2, 25, 2, 25));
		gbc.gridy = 4;
		add(ok, gbc);
	}
	
	public void styleButton(JButton btn, String comand, ActionListener listener, Insets insets) {
		btn.setFont(new Font("Arial", Font.PLAIN, 15));
		btn.setForeground(Color.WHITE);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setMargin(insets);
		btn.setFocusPainted(false);
		btn.setUI(new ShappedButton(new Color(0x21D11E)));
		btn.setActionCommand(comand);
		btn.addActionListener(listener);
	}

	public JLabel getLblMessage() {
		return lblMessage;
	}

	public void setLblMessage(JLabel lblMessage) {
		this.lblMessage = lblMessage;
	}

	public String getTxtColor() {
		return txtColor.getText();
	}

	public void setTxtColor(JTextField txtColor) {
		this.txtColor = txtColor;
	}

	
	
}
