package perpus9;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.io.*;

import javax.swing.*;

public class myExportPanel extends JFrame{
	
	Color col2 = new Color(0,70,100);
	Dimension dframe = new Dimension(200,75);
	Dimension dbutton = new Dimension(180,30);
	
	Connection con;
	Statement stmt;
	
	public File filedir;
	
	public File filecsv, filesql;
	public String csv, sql;
	public String rcsv, rsql;
	
	JLabel panel = new JLabel();
	JButton button1, button2;
	
	myExportPanel(){
		this.setResizable(false);
		this.setUndecorated(true);
		this.setLocation(MyFrame.tempmain.getLocationOnScreen().x+850,MyFrame.tempmain.getLocationOnScreen().y+160);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
			}
			@Override
			public void windowLostFocus(WindowEvent e) {
				Dispose();
			}
		});
		Panel();
		this.pack();
		this.setVisible(true);
	}
	void Panel() {
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(dframe);
		panel.setBackground(col2);
		this.add(panel);
		
		button1 = button(); button1.setText("Export as CSV");
		button1.addActionListener(e->{
			if(!filedir.exists()) {
				filedir.mkdirs();
			}
			if(filecsv.exists()) {
				filecsv.delete();
			}
			
			try {
				CreateCSVFile();
				this.Dispose();
				TimeUnit.MILLISECONDS.sleep(1000);
				Runtime.getRuntime().exec("explorer /select, "+filecsv);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		panel.add(button1);
		
		button2 = button(); button2.setText("Export as SQL");
		button2.addActionListener(e->{
			if(!filedir.exists()) {
				filedir.mkdirs();
			}
			if(filesql.exists()) {
				filesql.delete();
			}
			
			try {
				CreateSQLFile();
				this.Dispose();
				TimeUnit.MILLISECONDS.sleep(1000);
				Runtime.getRuntime().exec("explorer /select, "+filesql);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		panel.add(button2);
		
		ImageIcon unscaledImage = new ImageIcon("./col2.png");
		Image image = unscaledImage.getImage().getScaledInstance(panel.getPreferredSize().width, panel.getPreferredSize().height, Image.SCALE_SMOOTH);
		ImageIcon scaledImage = new ImageIcon(image);
		panel.setIcon(scaledImage);
	}
	
	void CreateCSVFile() {
		Koneksi();
		try {
			stmt.executeQuery(rcsv);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
	}
	
	void CreateSQLFile() {
		try {
			Runtime.getRuntime().exec(rsql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
	
	void Koneksi(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/multiperpus","root","");
			stmt = con.createStatement();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	void Dispose() {
		this.dispose();
	}
	JButton button() {
		JButton button = new JButton();
		button.setPreferredSize(dbutton);
		button.setFont(new Font(null, Font.PLAIN, 20));
		return button;
	}

}
