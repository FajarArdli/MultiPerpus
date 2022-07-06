package perpus9;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class myToolPicker extends JFrame{
	
	Color col2 = new Color(50,120,140);
	
	Font mvBoli = new Font(null, Font.PLAIN, 25);
	
	String kd_buku, j_buku;
	
	JLabel panel;
	JLabel carilbl, imglabel;
	JTextField tfield;
	JButton button;
	
	DefaultTableModel model;
	JTable table;
	TableModel modeli;
	JScrollPane sp;
	
	Connection con;
	String sql;
	Statement stmt;
	ResultSet rs;
	
	ImageIcon unscaledImage, scaledImage;
	Image image;
	
	myToolPicker(){
		this.setLayout(null);
		this.setSize(700,260);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
			}
			@Override
			public void windowLostFocus(WindowEvent e) {
				Dispose();
			}
		});
		
		myPanel();
		
		this.setVisible(true);
		
	}
	
	void myPanel() {
		panel = new JLabel();
		panel.setOpaque(true);
		panel.setLayout(null);
		panel.setSize(700,260);
		this.add(panel);
		
		imglabel = new JLabel("Gambar");
		imglabel.setBounds(540,10,150,200);
		imglabel.setOpaque(true);
		imglabel.setVisible(true);
		imglabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(imglabel);
		
		TampilBuku();
		table = new JTable();
		table.setModel(model);
		table.setFocusable(false);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//stobulis.setVisible(false);
				int i = table.getSelectedRow();
				modeli = table.getModel();
		        kd_buku = modeli.getValueAt(i, 0).toString();
		        j_buku = modeli.getValueAt(i, 1).toString();
		        try {
		        	Koneksi();
		        	sql = "select gambar from stok_barang where kode_barang = '"+modeli.getValueAt(i, 0).toString()+"'";
		        	rs = stmt.executeQuery(sql);
		        	
		        	File imgfile = new File("./bargimg.bin");
		        	FileOutputStream fos = new FileOutputStream(imgfile);
		        	
		        	if(rs.next()) {
		        		InputStream is = rs.getBinaryStream("gambar");
		        		byte[] buffer = new byte[1024];
		        		while(is.read(buffer)>0) {
		        			fos.write(buffer);
		        		}
		        	}
		        	con.close();
		        }catch(Exception e1) {
		        	System.out.println(e1);
		        }
		        unscaledImage = new ImageIcon("./bargimg.bin");
				image = unscaledImage.getImage().getScaledInstance(imglabel.getWidth(), imglabel.getHeight(), Image.SCALE_SMOOTH);
				scaledImage = new ImageIcon(image);
				imglabel.setIcon(scaledImage);
			}
		});
		sp = new JScrollPane();
		sp.setBounds(10,50,520,200);
		sp.setViewportView(table);
		panel.add(sp);
		
		carilbl = new JLabel("Cari");
		carilbl.setFont(mvBoli);
		carilbl.setForeground(Color.white);
		carilbl.setBounds(10,10,100,30);
		panel.add(carilbl);
		
		tfield = new JTextField();
		tfield.setBounds(65,10,465,30);
		tfield.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				searchPinjaman();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				searchPinjaman();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				searchPinjaman();
			}
			
			public void searchPinjaman() {
				model.setRowCount(0);
				try {
					Koneksi();
					rs = stmt.executeQuery("SELECT `kode_barang`, `nama_barang`, `merek`, `kadaluarsa`, `kategori`, `jumlah` FROM stok_barang WHERE kode_barang like '%"+tfield.getText()+"%' or nama_barang like '%"+tfield.getText()+"%' or merek like '%"+tfield.getText()+"%' or kadaluarsa like '%"+tfield.getText()+"%'");
					while (rs.next()) {
						model.addRow(new Object[] {
								rs.getString("kode_barang"),
								rs.getString("nama_barang"),
								rs.getString("merek"),
								rs.getString("kadaluarsa"),
								rs.getString("jumlah")
						});
					}
					con.close();
				}
				catch(Exception e1) {
					System.out.println(e1);
				}
			}
			
		});
		panel.add(tfield);
		
		button = new JButton("Ambil");
		button.setBounds(540,210,150,40);
		button.setBackground(col2);
		button.setForeground(Color.white);
		button.setFont(mvBoli);
		button.addActionListener(e->{
			MyFrame.kd_barang.setText(kd_buku);
			MyFrame.j_barang.setText(j_buku);
			this.dispose();
		});
		panel.add(button);
		
		ImageIcon unscaledImage = new ImageIcon("./col2.png");
		Image image = unscaledImage.getImage().getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledImage = new ImageIcon(image);
		panel.setIcon(scaledImage);
	}
	
	void Koneksi() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/multiperpus","root","");
			stmt = con.createStatement();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	void TampilBuku() {
		model = new DefaultTableModel();
		model.setRowCount(0);
		model.setColumnCount(0);
		
		model.addColumn("Kode");
		model.addColumn("Nama Barang");
		model.addColumn("Merek");
		model.addColumn("Kadaluarsa");
		model.addColumn("Jumlah");
		
		try {
			Koneksi();
			rs = stmt.executeQuery("SELECT * FROM stok_barang");
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("kode_barang"),
						rs.getString("nama_barang"),
						rs.getString("merek"),
						rs.getString("kadaluarsa"),
						rs.getString("jumlah")
				});
				
			}
			con.close();
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}

	void Dispose() {
		this.dispose();
		
	}
}
