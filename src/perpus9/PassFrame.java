package perpus9;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class PassFrame extends JFrame{
	
	JLabel gradcol;
	String password;
	JTextField tfield;

	PassFrame(){
		this.setLayout(null);
		this.setSize(230,110);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		
		gradcol = new JLabel();
		gradcol.setSize(this.getSize());
		gradcol.setLayout(new FlowLayout());
		ImageIcon unscaledImage = new ImageIcon("./col2.png");
		Image image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		this.add(gradcol);
		
		Password();
		this.setVisible(true);
	}

	void Password() {
		JLabel lbl = new JLabel("Masukkan Password");
		lbl.setFont(new Font(null, Font.PLAIN, 20));
		gradcol.add(lbl);
		
		tfield = new JTextField();
		tfield.setPreferredSize(new Dimension(180,30));
		tfield.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					 Gate();
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		});
		gradcol.add(tfield);
		
		JLabel help = new JLabel("?");
		help.setBounds(150,0,30,30);
		help.setHorizontalAlignment(JLabel.CENTER);
		help.setForeground(new Color(200,200,200));
		help.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Password adalah '"+password+"'");
			}

		});
		tfield.add(help);
		
		JButton msk = new JButton("Masuk");
		msk.setPreferredSize(new Dimension(90,30));
		msk.addActionListener(e->{
			Gate();
			
		});
		
				  
		gradcol.add(msk);
		
		JButton btl = new JButton("Batal");
		btl.setPreferredSize(msk.getPreferredSize());
		btl.addActionListener(e->{
			System.exit(0);
		});
		gradcol.add(btl);
		
		String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 3) { // panjang string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        password = saltStr;
	}
	
	void Gate() {
//		password = ;
	    
		if(tfield.getText().equals(password)) {
			Main.myframe.setVisible(true);
			this.dispose();
		}else {
			JOptionPane.showMessageDialog(null, "Password Salah!");
			tfield.setText(null);
		}
	}
}
