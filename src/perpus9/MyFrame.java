package perpus9;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.filechooser.*;
import javax.swing.text.*;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.*;

//import static javax.swing.JOptionPane.*;

public class MyFrame extends JFrame{
	//Color
	Color col1 = new Color(20,120,120);
	Color col4 = new Color(50,120,140);
	Color col2 = col4;
	
	//template title
	JLabel ttitle;
	
	//stok template
	JPanel stoktemplate;
	JPanel stokBukuTemplate, stobulis, stobuin;
	JPanel stokBarangTemplate, stobalis, stobain;
	
	//kunjungan template
	JPanel kunjungantemplate, kunjulis, kunjuin;
	
	//pinjaman template
	JPanel pinjamantemplate;
	JPanel pinjamBukuTemplate, pinbulis, pinbuin;
	JPanel pinjamBarangTemplate, pinbalis, pinbain;
	
	//dashboard template
	JPanel dashboardtemplate;
	JPanel dashall;
	JLabel all1,all2,all3,all4;
	JLabel one1,one2,one3,one4;
	JComboBox dashcbyear;
	
	//File
	File imgfile;
	//Input stream
	FileOutputStream fos;
	InputStream is;
	//file
	
	//jframe
	JPanel sidebar;
	public static JLabel tempmain;
	JLabel title;

	//filechooser
	JFileChooser fchooser;
	//file;
	
	//tanggal
	public static JTextField tglfield, tglfield1, tglfield2, tglfield3, tglfield4, tglfield5, jamfield;

	//export
	myExportPanel expanel;
	public static JButton export, export1, export2, export3, export4, export5;
	
	//tabel
	//stok
	DefaultTableModel bukumodel = new DefaultTableModel();
	DefaultTableModel barangmodel = new DefaultTableModel();
	//kunjungan
	DefaultTableModel kunjungmodel  = new DefaultTableModel();
	
	//pinjaman
	DefaultTableModel pinjammodel = new DefaultTableModel();
	DefaultTableModel pinjammodel1 = new DefaultTableModel();
	
//	JTable table;
//	JScrollPane sp;
//	TableModel modeli;
	
	public static JTextField kd_buku, j_buku;
	public static JTextField kd_barang, j_barang;
	
	
	//koneksi
	Connection con;
	String sql;
	Statement stmt;
	ResultSet rs, rs1;
	
	//image label
	//JLabel imglabel;
	ImageIcon unscaledImage, scaledImage;
	Image image;
	//file	
	
	MyFrame(){
		
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(null);
		this.setSize(1280,720);
		this.setResizable(false);
		this.setTitle("MultiPerpus - Perpustakaan Multifungsi");
		this.setLocationRelativeTo(null);
		
		ImageIcon unscaled = new ImageIcon("./title_logo.png");
		Image img = unscaled.getImage().getScaledInstance(1000, 1000, Image.SCALE_SMOOTH);
		ImageIcon scaled = new ImageIcon(img);
		this.setIconImage(scaled.getImage());
		
		Koneksi();
		KillProcess();
		Sidebar();
		TemplateMain();
		
		this.setVisible(true);
	}
	
	void KillProcess(){
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				try {
					Runtime.getRuntime().exec("./xampp/apache_stop.bat");
					Runtime.getRuntime().exec("./xampp/mysql_stop.bat");
//					Runtime.getRuntime().exec("taskkill /IM \"mysqld.exe\" /F");
					System.exit(0);
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
			}
		});
		
	}
	
	void Koneksi(){
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/multiperpus","root","");
			stmt = con.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	void TampilBuku() {
		
		bukumodel.setColumnCount(0);
		bukumodel.setRowCount(0);
		
		bukumodel.addColumn("Kode");
		bukumodel.addColumn("Penyusun");
		bukumodel.addColumn("Tahun");
		bukumodel.addColumn("Judul");
		bukumodel.addColumn("Kota");
		bukumodel.addColumn("Penerbit");
		bukumodel.addColumn("Kategori");
		bukumodel.addColumn("Jml");
		
		try {
			Koneksi();
			rs = stmt.executeQuery("SELECT * FROM stok_buku ORDER BY 1 DESC");
			while (rs.next()) {
				bukumodel.addRow(new Object[] {
						rs.getString("kode_buku"),
						rs.getString("penulis"),
						rs.getString("tahun"),
						rs.getString("judul"),
						rs.getString("kota"),
						rs.getString("penerbit"),
						rs.getString("kategori"),
						rs.getString("jumlah")
				});
				
			}
			con.close();
		}
		
		catch(Exception e) {
//			e.printStackTrace();
		}
	}
	
	void TampilBarang() {
		
		barangmodel.setColumnCount(0);
		barangmodel.setRowCount(0);
		
		barangmodel.addColumn("Kode");
		barangmodel.addColumn("Nama Barang");
		barangmodel.addColumn("Merek");
		barangmodel.addColumn("Kadaluarsa");
		barangmodel.addColumn("Kategori");
		barangmodel.addColumn("Jml");
		
		try {
			Koneksi();
			rs = stmt.executeQuery("SELECT * FROM stok_barang ORDER BY 1 DESC");
			while (rs.next()) {
				barangmodel.addRow(new Object[] {
						rs.getString("kode_barang"),
						rs.getString("nama_barang"),
						rs.getString("merek"),
						rs.getString("kadaluarsa"),
						rs.getString("kategori"),
						rs.getString("jumlah")
				});
				
			}
			con.close();
		}
		
		catch(Exception e) {
			
		}
	}
	
	void TampilKunjungan() {
		
		kunjungmodel.setColumnCount(0);
		kunjungmodel.setRowCount(0);
		
		kunjungmodel.addColumn("Tanggal Input");
		kunjungmodel.addColumn("Tahun Ajaran");
		kunjungmodel.addColumn("Tgl Masuk");
		kunjungmodel.addColumn("Jam Masuk");
		kunjungmodel.addColumn("Nama");
		kunjungmodel.addColumn("Kelas");
		kunjungmodel.addColumn("Jenis Kunjungan");
		kunjungmodel.addColumn("Keterangan");
		
		try {
			Koneksi();
			rs = stmt.executeQuery("SELECT * FROM daftar_kunjungan ORDER BY 1 DESC");
			while (rs.next()) {
				kunjungmodel.addRow(new Object[] {
						rs.getString("tgl_input"),
						rs.getString("thn_ajaran"),
						rs.getString("tgl_msk"),
						rs.getString("jam_msk"),
						rs.getString("nama"),
						rs.getString("kelas"),
						rs.getString("jenis_kunjungan"),
						rs.getString("keterangan")
						
				});
				
			}
			con.close();
		}
		
		catch(Exception e) {
			
		}
	}
	
	void TampilPinjamanBuku() {
		
		pinjammodel.setColumnCount(0);
		pinjammodel.setRowCount(0);
		
		pinjammodel.addColumn("Tanggal Input");
		pinjammodel.addColumn("Tahun Ajaran");
		pinjammodel.addColumn("Nama");
		pinjammodel.addColumn("Kelas");
		pinjammodel.addColumn("Kode Buku");
		pinjammodel.addColumn("Judul");
		pinjammodel.addColumn("Jumlah");
		pinjammodel.addColumn("Tanggal Pinjam");
		pinjammodel.addColumn("Tanggal Kembali");
		
		try {
			Koneksi();
			rs = stmt.executeQuery("SELECT * FROM pinjaman_buku ORDER BY 1 DESC");
			while (rs.next()) {
				pinjammodel.addRow(new Object[] {
						rs.getString("tgl_input"),
						rs.getString("thn_ajaran"),
						rs.getString("nama"),
						rs.getString("kelas"),
						rs.getString("kode_buku"),
						rs.getString("judul"),
						rs.getString("jumlah"),
						rs.getString("tgl_pinjam"),
						rs.getString("tgl_kembali")
				});
				
			}
			con.close();
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	void TampilPinjamanBarang() {
		
		pinjammodel1.setColumnCount(0);
		pinjammodel1.setRowCount(0);
		
		pinjammodel1.addColumn("Tanggal Input");
		pinjammodel1.addColumn("Tahun Ajaran");
		pinjammodel1.addColumn("Nama");
		pinjammodel1.addColumn("Kelas");
		pinjammodel1.addColumn("Kode Barang");
		pinjammodel1.addColumn("Nama Barang");
		pinjammodel1.addColumn("Jumlah");
		pinjammodel1.addColumn("Tanggal Pinjam");
		pinjammodel1.addColumn("Tanggal Kembali");
		
		try {
			Koneksi();
			rs = stmt.executeQuery("SELECT * FROM pinjaman_barang ORDER BY 1 DESC");
			while (rs.next()) {
				pinjammodel1.addRow(new Object[] {
						rs.getString("tgl_input"),
						rs.getString("thn_ajaran"),
						rs.getString("nama"),
						rs.getString("kelas"),
						rs.getString("kode_barang"),
						rs.getString("nama_barang"),
						rs.getString("jumlah"),
						rs.getString("tgl_pinjam"),
						rs.getString("tgl_kembali")
				});
				
			}
			con.close();
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
	void Sidebar() {
		sidebar = new JPanel();
		
		sidebar.setLayout(null);
		sidebar.setSize(200,681);
//		sidebar.setBackground(col0);
		
		Title();
		DashboardButton();
		DaftarPinjamButton();
		DaftarKunjunganButton();
		DaftarStokButton();
//		ImportButton();
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(sidebar.getSize());
		unscaledImage = new ImageIcon("./col2-1.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		gradcol.setBorder(compound);
		sidebar.add(gradcol);
		
		this.add(sidebar);
	}
	
	void Title() {
		title = new JLabel("SDN CC 09 PG");
		title.setFont(new Font(null, Font.BOLD, 20));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setHorizontalTextPosition(JLabel.CENTER);
		title.setVerticalTextPosition(JLabel.BOTTOM);
		title.setForeground(Color.white);
		title.setBounds(0,0,200,200);
		
		unscaledImage = new ImageIcon("./title_logo.png");
		image = unscaledImage.getImage().getScaledInstance(title.getWidth()/2, title.getHeight()/2, Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		title.setIcon(scaledImage);
		sidebar.add(title);
		
	}
	
	JButton SidebarButton() {
		JButton button = new JButton();
		button.setSize(190, 95);
		button.setBackground(col4);
		button.setForeground(Color.white);
		button.setFont(new Font("MV Boli", Font.PLAIN, 30));
		
		return button;
	}
	
	void DashboardButton() {
		var dashbutton = SidebarButton();
		
		dashbutton.setText("Dashboard");
		dashbutton.setLocation(5,200);
		dashbutton.addActionListener(e -> {
			dashboardtemplate.setVisible(true);
			stoktemplate.setVisible(false);
			pinjamantemplate.setVisible(false);
			kunjungantemplate.setVisible(false);
			
			ttitle.setText("Dashboard");
			
		});
		sidebar.add(dashbutton);
	}
	
	void DaftarPinjamButton() {
		var dafpinbutton = SidebarButton();
		
		dafpinbutton.setText("Pinjaman");
		dafpinbutton.setLocation(5,400);
		dafpinbutton.addActionListener(e -> {
			pinjamantemplate.setVisible(true);
			dashboardtemplate.setVisible(false);
			stoktemplate.setVisible(false);
			kunjungantemplate.setVisible(false);
			
			ttitle.setText("Pinjaman Buku & Barang Perpustakaan");
		});
		sidebar.add(dafpinbutton);
	}
	
	void DaftarKunjunganButton() {
		var dafkunbutton = SidebarButton();
		
		dafkunbutton.setText("Kunjungan");
		dafkunbutton.setLocation(5, 300);
		dafkunbutton.addActionListener(e -> {
			kunjungantemplate.setVisible(true);
			dashboardtemplate.setVisible(false);
			stoktemplate.setVisible(false);
			pinjamantemplate.setVisible(false);
			
			ttitle.setText("Daftar Kunjungan Siswa");
		});
		sidebar.add(dafkunbutton);
	}
	void DaftarStokButton() {
		var dafstokbutton = SidebarButton();
		
		dafstokbutton.setText("Stok");
		dafstokbutton.setLocation(5, 500);
		dafstokbutton.addActionListener(e -> {
			stoktemplate.setVisible(true);
			dashboardtemplate.setVisible(false);
			kunjungantemplate.setVisible(false);
			pinjamantemplate.setVisible(false);
			
			ttitle.setText("Stok Buku & Barang Perpustakaan");
		});
		sidebar.add(dafstokbutton);
	}
	
	
	void  TemplateMain() {
		tempmain = new JLabel();
		
		tempmain.setLayout(null);
		tempmain.setBounds(200,0,1064,681);
		
		ttitle = new JLabel("Dashboard");
		ttitle.setBounds(10,10,1040,60);
		ttitle.setForeground(Color.white);
		ttitle.setFont(new Font("MV Boli", Font.BOLD, 50));
		tempmain.add(ttitle);
		
		DashboardTemplate();
		PinjamanTemplate();
		KunjunganTemplate();
		StokTemplate();
		
		
		dashboardtemplate.setVisible(true);
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(tempmain.getSize());
		unscaledImage = new ImageIcon("./col2.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		gradcol.setBorder(compound);
		tempmain.add(gradcol);
		
		this.add(tempmain);
	}
	
	JButton TemplateButton() {
		var button = new JButton();
		button.setBackground(col4);
		button.setForeground(Color.white);
		button.setFont(new Font(null, Font.PLAIN, 20));
		button.setSize(150,50);
		
		return button;
	}
	
	JTextField InputTextField() {
		JTextField tfield = new JTextField();
		tfield.setSize(490, 30);
		tfield.setFont(new Font(null, Font.PLAIN, 12));
		
		return tfield;
	}
	
	JLabel LabelField() {
		var lfield = new JLabel();
		lfield.setSize(300,30);
		lfield.setFont(new Font(null, Font.PLAIN, 20));
		lfield.setVerticalAlignment(JLabel.TOP);
		lfield.setForeground(Color.white);
		
		return lfield;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//															Template
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	void PinjamanPieChart() {
		try {
			Koneksi();
			rs = stmt.executeQuery("select kelas, sum(jumlah) from pinjaman_buku where thn_ajaran='"+dashcbyear.getSelectedItem()+"' group by kelas");
			DefaultPieDataset pieset = new DefaultPieDataset();
			
			while (rs.next()) {
				pieset.setValue(rs.getString("kelas"), Double.parseDouble(rs.getString("sum(jumlah)")));
			}
			JFreeChart chart = ChartFactory.createPieChart(
					"Pinjaman Buku SDN CC 09 PG Tahun Ajaran '"+dashcbyear.getSelectedItem()+"'",   // chart title           
			         pieset,          // data           
			         true,             // include legend          
			         true,           
			         false);
			
			File pieChart = new File( "./multiperpus/charts/pie_pinjaman_buku.jpeg" );
			ChartUtilities.saveChartAsJPEG( pieChart , chart , 680 , 400 );
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void PinjamanBarChart() {
		try {
			Koneksi();
			rs = stmt.executeQuery("select kelas, sum(jumlah) from pinjaman_buku where thn_ajaran='"+dashcbyear.getSelectedItem()+"' group by kelas");
			DefaultCategoryDataset barset = new DefaultCategoryDataset();
			while (rs.next()) {
				barset.setValue(Double.parseDouble(rs.getString("sum(jumlah)")), rs.getString("kelas"), rs.getString("kelas"));
			}
			
			String barTitle = null;
			rs1 = stmt.executeQuery("select sum(jumlah) from pinjaman_buku where thn_ajaran='"+dashcbyear.getSelectedItem()+"'");
			while (rs1.next()) {
				barTitle = "Jumlah Peminjam Buku '"+dashcbyear.getSelectedItem()+"' = "+rs1.getString("sum(jumlah)");
			}
			JFreeChart barChart = ChartFactory.createBarChart(
			         barTitle, 
			         "Kelas", "Jumlah", 
			         barset,PlotOrientation.VERTICAL, 
			         true, true, false);
			         
			File BarChart = new File( "./multiperpus/charts/bar_pinjaman_buku.jpeg" ); 
			ChartUtilities.saveChartAsJPEG( BarChart , barChart , 680 , 400 );
			con.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	void KunjunganPieChart() {
		try {
			Koneksi();
			rs = stmt.executeQuery("select jenis_kunjungan, count(*) from daftar_kunjungan where thn_ajaran='"+dashcbyear.getSelectedItem()+"' group by jenis_kunjungan");
			DefaultPieDataset pieset1 = new DefaultPieDataset();
			
			while (rs.next()) {
				pieset1.setValue(rs.getString( "jenis_kunjungan" ), Double.parseDouble(rs.getString("count(*)")));
			}
			JFreeChart chart1 = ChartFactory.createPieChart(
					"Kunjungan Perpus SDN CC 09 PG Tahun Ajaran '"+dashcbyear.getSelectedItem()+"'",   // chart title           
			         pieset1,          // data           
			         true,             // include legend          
			         true,           
			         false);
			File pieChart1 = new File( "./multiperpus/charts/pie_daftar_kunjungan.jpeg" );
			ChartUtilities.saveChartAsJPEG( pieChart1 , chart1 , 680 , 400 );
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void KunjunganBarChart() {
		try {
			Koneksi();
			rs = stmt.executeQuery("select jenis_kunjungan, count(*) from daftar_kunjungan where thn_ajaran='"+dashcbyear.getSelectedItem()+"' group by jenis_kunjungan");
			DefaultCategoryDataset barset1 = new DefaultCategoryDataset();
			
			while (rs.next()) {
				barset1.setValue(Double.parseDouble(rs.getString("count(*)")), rs.getString("jenis_kunjungan"), rs.getString("jenis_kunjungan"));
			}
			
			String barTitle = null;
			rs1 = stmt.executeQuery("select count(*) from daftar_kunjungan where thn_ajaran='"+dashcbyear.getSelectedItem()+"'");
			while (rs1.next()) {
				barTitle = "Jumlah Pengunjung Perpus '"+dashcbyear.getSelectedItem()+"' = "+rs1.getString("count(*)");
			}
			JFreeChart barChart1 = ChartFactory.createBarChart(
			         barTitle, 
			         "Jenis Kunjungan", "Jumlah", 
			         barset1,PlotOrientation.VERTICAL, 
			         true, true, false);
			         
			File BarChart1 = new File( "./multiperpus/charts/bar_daftar_kunjungan.jpeg" ); 
			ChartUtilities.saveChartAsJPEG( BarChart1 , barChart1 , 680 , 400 );
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void DashboardTemplate() {
		dashboardtemplate = new JPanel();
		dashboardtemplate.setLayout(null);
		dashboardtemplate.setBounds(10,70,1040,600);
		dashboardtemplate.setVisible(false);
		tempmain.add(dashboardtemplate);
		
		JLabel ajaran = new JLabel("Tahun Ajaran");
		ajaran.setBounds(10,10,200,22);
		ajaran.setForeground(Color.white);
		ajaran.setFont(new Font(null, Font.PLAIN, 20));
		dashboardtemplate.add(ajaran);
		
		ArrayList<String> year_tmp = new ArrayList<String>();
		for(int d=Calendar.getInstance().get(Calendar.YEAR); d >= Calendar.getInstance().get(Calendar.YEAR) - 10; d--) {
		    year_tmp.add(d+"-"+(d+1));
		}
		
		dashcbyear = new JComboBox(year_tmp.toArray());
		dashcbyear.setBounds(130,10,100,30);
		dashcbyear.setSelectedIndex(1);
		dashcbyear.addActionListener(e->{
			
			try {
				PinjamanPieChart();
				PinjamanBarChart();
				KunjunganPieChart();
				KunjunganBarChart();
				
				TimeUnit.MILLISECONDS.sleep(100);
				
				DashboardAllImage();
				DashboardOneImage();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		dashboardtemplate.add(dashcbyear);
		
		var openImage = TemplateButton();
		openImage.setText("Buka Gambar");
		openImage.setBounds(230,10,150,30);
		openImage.addActionListener(e->{
			try {
				Runtime.getRuntime().exec("explorer /open, " + new File("./multiperpus/charts/"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		dashboardtemplate.add(openImage);
		
		one1 = new JLabel("lbl");
		one1.setBounds(90,50,860,510);
		one1.setOpaque(true);
		one1.setVisible(false);
		dashboardtemplate.add(one1);
		
		one2 = new JLabel("lbl1");
		one2.setBounds(90,50,860,510);
		one2.setOpaque(true);
		one2.setVisible(false);
		dashboardtemplate.add(one2);
		
		one3 = new JLabel("lbl2");
		one3.setBounds(90,50,860,510);
		one3.setOpaque(true);
		one3.setVisible(false);
		dashboardtemplate.add(one3);
		
		one4 = new JLabel("lbl3");
		one4.setBounds(90,50,860,510);
		one4.setOpaque(true);
		one4.setVisible(false);
		dashboardtemplate.add(one4);
		
		JButton next = TemplateButton();
		next.setBounds(950,50,80,510);
		next.setFont(new Font(null, Font.BOLD, 50));
		unscaledImage = new ImageIcon("./arrow_r.png");
		image = unscaledImage.getImage().getScaledInstance(next.getWidth(), next.getHeight()/3, Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		next.setIcon(scaledImage);
		next.addActionListener(e->{
			if(dashall.isVisible()==true) {
				dashall.setVisible(false);
				one1.setVisible(true);
				
			}else if(one1.isVisible()==true) {
				one1.setVisible(false);
				one2.setVisible(true);
				
			}else if(one2.isVisible()==true) {
				one2.setVisible(false);
				one3.setVisible(true);
				
			}else if(one3.isVisible()==true) {
				one3.setVisible(false);
				one4.setVisible(true);
				
			}else if(one4.isVisible()==true) {
				one4.setVisible(false);
				dashall.setVisible(true);
				
			}
		});
		dashboardtemplate.add(next);
		
		JButton prev = TemplateButton();
		prev.setBounds(10,50,80,510);
		unscaledImage = new ImageIcon("./arrow_l.png");
		image = unscaledImage.getImage().getScaledInstance(prev.getWidth(), prev.getHeight()/3, Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		prev.setIcon(scaledImage);
		prev.addActionListener(e->{
			if(dashall.isVisible()==true) {
				dashall.setVisible(false);
				one4.setVisible(true);
				
			}else if(one4.isVisible()==true) {
				one4.setVisible(false);
				one3.setVisible(true);
				
			}else if(one3.isVisible()==true) {
				one3.setVisible(false);
				one2.setVisible(true);
				
			}else if(one2.isVisible()==true) {
				one2.setVisible(false);
				one1.setVisible(true);
				
			}else if(one1.isVisible()==true) {
				one1.setVisible(false);
				dashall.setVisible(true);
				
			}
		});
		dashboardtemplate.add(prev);
		
		export = new JButton("Export DB");
		export.setBounds(930,10,100,30);
		export.addActionListener(e->{
			expanel = new myExportPanel();
			expanel.setLocation(tempmain.getLocationOnScreen().x+850,tempmain.getLocationOnScreen().y+110);
			expanel.button1.setVisible(false);
			//expanel.button2.setVisible(false);
			expanel.filedir = new File("./multiperpus/");
			
			expanel.filesql = new File("./multiperpus/multiperpus.sql");
			expanel.sql = expanel.filesql.getAbsolutePath().replace("\\", "/");
			expanel.rsql = "./xampp/mysql/bin/mysqldump -u root multiperpus stok_buku stok_barang pinjaman_buku pinjaman_barang daftar_kunjungan -r "+expanel.sql;
			
		});
		dashboardtemplate.add(export);
		
		DashboardAll();
		DashboardOneImage();
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(dashboardtemplate.getSize());
		unscaledImage = new ImageIcon("./col2-2.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		dashboardtemplate.add(gradcol);
	}
	
	private void DashboardOneImage() {
		
		unscaledImage = new ImageIcon("./multiperpus/charts/pie_pinjaman_buku.jpeg");
		image = unscaledImage.getImage().getScaledInstance(one1.getWidth(), one1.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		one1.setIcon(scaledImage);
		
		unscaledImage = new ImageIcon("./multiperpus/charts/bar_pinjaman_buku.jpeg");
		image = unscaledImage.getImage().getScaledInstance(one2.getWidth(), one2.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		one2.setIcon(scaledImage);
		
		unscaledImage = new ImageIcon("./multiperpus/charts/pie_daftar_kunjungan.jpeg");
		image = unscaledImage.getImage().getScaledInstance(one3.getWidth(), one3.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		one3.setIcon(scaledImage);
		
		unscaledImage = new ImageIcon("./multiperpus/charts/bar_daftar_kunjungan.jpeg");
		image = unscaledImage.getImage().getScaledInstance(one4.getWidth(), one4.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		one4.setIcon(scaledImage);
	}

	void DashboardAll(){
		dashall = new JPanel();
		dashall.setLayout(null);
		dashall.setBounds(90,50,860,510);
		dashall.setBackground(new Color(0,0,0,10));
		dashboardtemplate.add(dashall);
		
		// pinjaman
		all1 = new JLabel();
		all1.setBounds(0,0,425,250);
		all1.setOpaque(true);
		dashall.add(all1);
		
		all2 = new JLabel();
		all2.setBounds(0,260,425,250);
		all2.setOpaque(true);
		dashall.add(all2);
		
		// kunjungan
		all3 = new JLabel();
		all3.setBounds(435,0,425,250);
		all3.setOpaque(true);
		dashall.add(all3);
		
		all4 = new JLabel();
		all4.setBounds(435,260,425,250);
		all4.setOpaque(true);
		dashall.add(all4);
		
		DashboardAllImage();
	}
	
	void DashboardAllImage() {
		unscaledImage = new ImageIcon("./multiperpus/charts/pie_pinjaman_buku.jpeg");
		image = unscaledImage.getImage().getScaledInstance(all1.getWidth(), all1.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		all1.setIcon(scaledImage);
		
		unscaledImage = new ImageIcon("./multiperpus/charts/bar_pinjaman_buku.jpeg");
		image = unscaledImage.getImage().getScaledInstance(all2.getWidth(), all2.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		all2.setIcon(scaledImage);
		
		
		// kunjungan
		unscaledImage = new ImageIcon("./multiperpus/charts/pie_daftar_kunjungan.jpeg");
		image = unscaledImage.getImage().getScaledInstance(all3.getWidth(), all3.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		all3.setIcon(scaledImage);
		
		unscaledImage = new ImageIcon("./multiperpus/charts/bar_daftar_kunjungan.jpeg");
		image = unscaledImage.getImage().getScaledInstance(all4.getWidth(), all4.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		all4.setIcon(scaledImage);	
	}

	void PinjamanTemplate() {
		
		pinjamantemplate = new JPanel();
		pinjamantemplate.setLayout(null);
		pinjamantemplate.setBounds(10,70,1040,600);
		pinjamantemplate.setVisible(false);
		tempmain.add(pinjamantemplate);
		
		pinjamBukuTemplate = new JPanel();
		pinjamBukuTemplate.setLayout(null);
		pinjamBukuTemplate.setBounds(0,50,1040,550);
		pinjamBukuTemplate.setBackground(col1);
		//pinjamBukuTemplate.setVisible(false);
		pinjamantemplate.add(pinjamBukuTemplate);
		
		pinjamBarangTemplate = new JPanel();
		pinjamBarangTemplate.setLayout(null);
		pinjamBarangTemplate.setBounds(0,50,1040,550);
		//pinjamBarangTemplate.setVisible(false);
		pinjamantemplate.add(pinjamBarangTemplate);
		
		var pinjamBukuTab = TemplateButton();
		pinjamBukuTab.setLocation(70,0);
		pinjamBukuTab.setText("Buku");
		pinjamBukuTab.addActionListener(e->{
			pinjamBukuTemplate.setVisible(true);
			pinjamBarangTemplate.setVisible(false);
		});
		pinjamantemplate.add(pinjamBukuTab);
	
		var pinjamBarangTab = TemplateButton();
		pinjamBarangTab.setLocation(220,0);
		pinjamBarangTab.setText("Barang");
		pinjamBarangTab.addActionListener(e->{
			pinjamBukuTemplate.setVisible(false);
			pinjamBarangTemplate.setVisible(true);
		});
		pinjamantemplate.add(pinjamBarangTab);
		
		PinjamanBukuList();
		PinjamanBarangList();
		pinjamBarangTemplate.setVisible(false);
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(pinjamantemplate.getSize());
		unscaledImage = new ImageIcon("./col2-2.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		pinjamantemplate.add(gradcol);
	}
	
	void PinjamanBukuList() {
		pinbulis = new JPanel();
		pinbulis.setLayout(null);
		pinbulis.setSize(1040,600);
		pinbulis.setBackground(col1);
		pinjamBukuTemplate.add(pinbulis);
		
		var inputPanelButton = TemplateButton();
		inputPanelButton.setBounds(10,10,150,30);
		inputPanelButton.setText("Input / Edit");
		inputPanelButton.addActionListener(e->{
			pinbuin.setVisible(true);
			pinbulis.setVisible(false);
			
		});
		pinbulis.add(inputPanelButton);
		
		JLabel imglabel = new JLabel();
		imglabel.setBounds(980,50,50,65);
		imglabel.setOpaque(true);
		pinbulis.add(imglabel);
		
		JTable table = new JTable();
		table.setModel(pinjammodel);
		table.setFocusable(false);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//stobulis.setVisible(false);
				int i = table.getSelectedRow();
				TableModel modeli = table.getModel();
		        
		        try {
		        	Koneksi();
		        	sql = "select gambar from stok_buku where kode_buku = '"+modeli.getValueAt(i, 4).toString()+"'";
		        	rs = stmt.executeQuery(sql);
		        	
		        	imgfile = new File("./bukuimg.bin");
		        	fos = new FileOutputStream(imgfile);
		        	
		        	if(rs.next()) {
		        		is = rs.getBinaryStream("gambar");
		        		byte[] buffer = new byte[1024];
		        		while(is.read(buffer)>0) {
		        			fos.write(buffer);
		        		}
		        	}
		        	con.close();
		        }catch(Exception e1) {
		        	System.out.println(e1);
		        }
		        unscaledImage = new ImageIcon("./bukuimg.bin");
				image = unscaledImage.getImage().getScaledInstance(imglabel.getWidth(), imglabel.getHeight(), Image.SCALE_SMOOTH);
				scaledImage = new ImageIcon(image);
				imglabel.setIcon(scaledImage);
			}
		});
		
		JScrollPane sp = new JScrollPane();
		sp.setBounds(10,50,970,490);
		sp.setViewportView(table);
		pinbulis.add(sp);
		
		var cariLabel = LabelField();
		cariLabel.setLocation(175,15);
		cariLabel.setText("Cari");
		cariLabel.setFont(new Font(null, Font.BOLD, 15));
		pinbulis.add(cariLabel);
		
		var cariTfield = InputTextField();
		cariTfield.setLocation(210,12);
		cariTfield.getDocument().addDocumentListener(new DocumentListener() {
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
				pinjammodel.setRowCount(0);
				try {
					Koneksi();
					rs = stmt.executeQuery("SELECT * FROM pinjaman_buku WHERE tgl_input like '%"+cariTfield.getText()+"%' or thn_ajaran like '%"+cariTfield.getText()+"%' or nama like '%"+cariTfield.getText()+"%' or kelas like '%"+cariTfield.getText()+"%' or kode_buku like '%"+cariTfield.getText()+"%' or judul like '%"+cariTfield.getText()+"%' or tgl_pinjam like '%"+cariTfield.getText()+"%' or tgl_kembali like '%"+cariTfield.getText()+"%'");
					while (rs.next()) {
						pinjammodel.addRow(new Object[] {
								rs.getString("tgl_input"),
								rs.getString("thn_ajaran"),
								rs.getString("nama"),
								rs.getString("kelas"),
								rs.getString("kode_buku"),
								rs.getString("judul"),
								rs.getString("jumlah"),
								rs.getString("tgl_pinjam"),
								rs.getString("tgl_kembali"),
								
						});
					}
					con.close();
				}
				catch(Exception e1) {
					return;
				}
			}
			
		});
		pinbulis.add(cariTfield);
		
		export2 = new JButton("Export");
		export2.setBounds(930,10,100,30);
		export2.addActionListener(e->{
			expanel = new myExportPanel();
			expanel.filedir = new File("./multiperpus/pinjaman_buku/");

			expanel.filecsv = new File("./multiperpus/pinjaman_buku/pinjaman_buku.CSV");
			expanel.csv = expanel.filecsv.getAbsolutePath().replace("\\", "/");
			expanel.rcsv = "SELECT 'tgl_input','thn_ajaran','nama','kelas','kode_buku','judul','jumlah','tgl_pinjam','tgl_kembali' "
					+ "UNION ALL SELECT tgl_input,thn_ajaran,nama,kelas,kode_buku,judul,jumlah,tgl_pinjam,tgl_kembali "
					+ "FROM pinjaman_buku INTO OUTFILE '"+expanel.csv
					+ "' fields terminated by ';' lines terminated by '\n'".replace("\\", "/");
			
			expanel.filesql = new File("./multiperpus/pinjaman_buku/pinjaman_buku.sql");
			expanel.sql = expanel.filesql.getAbsolutePath().replace("\\", "/");
			expanel.rsql = "./xampp/mysql/bin/mysqldump -u root multiperpus pinjaman_buku -r "+expanel.sql;
			
		});
		pinbulis.add(export2);
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(pinbulis.getSize());
		unscaledImage = new ImageIcon("./col2-3.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		pinbulis.add(gradcol);
		
		PinjamanBukuInput();
		pinbuin.setVisible(false);
		
		TampilPinjamanBuku();
	}

	void PinjamanBukuInput() {
		pinbuin = new JPanel();
		pinbuin.setLayout(null);
		pinbuin.setSize(1040,600);
		pinbuin.setBackground(col1);
		pinbuin.setVisible(false);
		pinjamBukuTemplate.add(pinbuin);
		
		var listPanelButon = TemplateButton();
		listPanelButon.setBounds(10,10,150,30);
		listPanelButon.setText("List Buku");
		listPanelButon.addActionListener(e->{
			pinbulis.setVisible(true);
			pinbuin.setVisible(false);
			
		});
		pinbuin.add(listPanelButon);
		
		var label = LabelField();
		label.setLocation(50,50);
		label.setText("Tahun Ajaran");
		pinbuin.add(label);
		
		var label1 = LabelField();
		label1.setLocation(50,80);
		label1.setText("Nama Peminjam");
		pinbuin.add(label1);
		
		var label7 = LabelField();
		label7.setLocation(50,110);
		label7.setText("Kelas");
		pinbuin.add(label7);
		
		var label2 = LabelField();
		label2.setLocation(50,140);
		label2.setText("Kode Buku");
		pinbuin.add(label2);
		
		var label3 = LabelField();
		label3.setLocation(50,170);
		label3.setText("Judul");
		pinbuin.add(label3);
		
		var label4 = LabelField();
		label4.setLocation(50,230);
		label4.setText("Jumlah");
		pinbuin.add(label4);
		
		var label5 = LabelField();
		label5.setLocation(50,260);
		label5.setText("Tanggal Pinjam");
		pinbuin.add(label5);
		
		var label6 = LabelField();
		label6.setLocation(50,290);
		label6.setText("Tanggal Kembali");
		pinbuin.add(label6);
		
		//...........................................TEXTFIELDS..............................................//
		
		JLabel imglabel = new JLabel();
		imglabel.setBounds(690,50,300,400);
		imglabel.setOpaque(true);
		imglabel.setText("Gambar");
		imglabel.setHorizontalAlignment(JLabel.CENTER);
		pinbuin.add(imglabel);
		
		ArrayList<String> year_tmp = new ArrayList<String>();
		for(int d=Calendar.getInstance().get(Calendar.YEAR); d >= Calendar.getInstance().get(Calendar.YEAR) - 10; d--) {
		    year_tmp.add(d+"-"+(d+1));
		}
		JComboBox cbyear = new JComboBox(year_tmp.toArray());
		cbyear.setBounds(210,50,100,30);
		pinbuin.add(cbyear);
		
		var tfield1 = InputTextField();
		tfield1.setBounds(210,80,465,30);
		pinbuin.add(tfield1);
		
		String[] kls = {"Staff","1","1A","1b","2","2A","2B","3","3A","3B","4","4A","4B","5","5A","5B","6","6A","6B"};
		JComboBox tfield2 = new JComboBox(kls);
		tfield2.setBounds(210,110,100,30);
		pinbuin.add(tfield2);
		
		kd_buku = new JTextField();
		var tfield3 = kd_buku;
		tfield3.setBounds(210,140,465,30);
		tfield3.setEnabled(false);
		tfield3.getDocument().addDocumentListener(new DocumentListener() {
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
				try {
		        	Koneksi();
		        	sql = "select gambar from stok_buku where kode_buku = '"+tfield3.getText()+"'";
		        	rs = stmt.executeQuery(sql);
		        	
		        	imgfile = new File("./bukuimg.bin");
		        	fos = new FileOutputStream(imgfile);
		        	
		        	if(rs.next()) {
		        		is = rs.getBinaryStream("gambar");
		        		byte[] buffer = new byte[1024];
		        		while(is.read(buffer)>0) {
		        			fos.write(buffer);
		        		}
		        	}
		        	con.close();
		        }catch(Exception e1) {
		        	System.out.println(e1);
		        }
				unscaledImage = new ImageIcon("./bukuimg.bin");
				image = unscaledImage.getImage().getScaledInstance(imglabel.getWidth(), imglabel.getHeight(), Image.SCALE_SMOOTH);
				scaledImage = new ImageIcon(image);
				imglabel.setIcon(scaledImage);
			}
			
		});
		pinbuin.add(tfield3);
		
		j_buku = new JTextField();
		var tfield4 = j_buku;
		tfield4.setBounds(210,170,465,30);
		tfield4.setEnabled(false);
		pinbuin.add(tfield4);
		
		var buku = TemplateButton();
		buku.setBounds(210,200,150,30);
		buku.setText("Pilih Buku");
		buku.addActionListener(e->{
			new myBookPicker();
		});
		pinbuin.add(buku);
		
		var tfield5 = InputTextField();
		tfield5.setBounds(210,230,465,30);
		pinbuin.add(tfield5);
		
		//ttgl pinjam
		tglfield = new JTextField();
		var tfield6 = tglfield;
		tfield6.setBounds(210,260,100,30);
		tfield6.setEnabled(false);
		pinbuin.add(tfield6);
		
		JLabel xlbl = new JLabel("  X  ");
		xlbl.setBounds(75,2,22,20);
		xlbl.setHorizontalAlignment(JLabel.RIGHT);
		xlbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tfield6.setText(null);
			}

		});
		tfield6.add(xlbl);
		
		JButton dateButton = new JButton("...");
		dateButton.setBackground(col2);
		dateButton.setForeground(Color.white);
		dateButton.setBounds(310,260,25,30);
		dateButton.addActionListener(e->{
			new myDatePicker();
		});
		pinbuin.add(dateButton);
		
		//tgl kembali
		tglfield1 = new JTextField();
		var tfield7 = tglfield1;
		tfield7.setBounds(210,290,100,30);
		tfield7.setEnabled(false);
		pinbuin.add(tfield7);
		
		JLabel xlbl1 = new JLabel("  X  ");
		xlbl1.setBounds(75,2,22,20);
		xlbl1.setHorizontalAlignment(JLabel.RIGHT);
		xlbl1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tfield7.setText(null);
			}

		});
		tfield7.add(xlbl1);
		
		JButton dateButton1 = new JButton("...");
		dateButton1.setBackground(col2);
		dateButton1.setForeground(Color.white);
		dateButton1.setBounds(310,290,25,30);
		dateButton1.addActionListener(e->{
			new myDatePicker1();
		});
		pinbuin.add(dateButton1);
		
		JTable table = new JTable();
		table.setModel(pinjammodel);
		table.setFocusable(false);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//stobulis.setVisible(false);
				int i = table.getSelectedRow();
				TableModel modeli = table.getModel();
				cbyear.setSelectedItem(modeli.getValueAt(i, 1).toString());
		        tfield1.setText(modeli.getValueAt(i, 2).toString());
		        tfield2.setSelectedItem(modeli.getValueAt(i, 3).toString());;
		        tfield3.setText(modeli.getValueAt(i, 4).toString());
		        tfield4.setText(modeli.getValueAt(i, 5).toString());
		        tfield5.setText(modeli.getValueAt(i, 6).toString());
		        tfield6.setText(modeli.getValueAt(i, 7).toString());
		        tfield7.setText(modeli.getValueAt(i, 8).toString());
		        
		        try {
		        	Koneksi();
		        	sql = "select gambar from stok_buku where kode_buku = '"+tfield3.getText()+"'";
		        	rs = stmt.executeQuery(sql);
		        	
		        	imgfile = new File("./bukuimg.bin");
		        	fos = new FileOutputStream(imgfile);
		        	
		        	if(rs.next()) {
		        		is = rs.getBinaryStream("gambar");
		        		byte[] buffer = new byte[1024];
		        		while(is.read(buffer)>0) {
		        			fos.write(buffer);
		        		}
		        	}
		        	con.close();
		        }catch(Exception e1) {
		        	System.out.println(e1);
		        }
		        unscaledImage = new ImageIcon("./bukuimg.bin");
				image = unscaledImage.getImage().getScaledInstance(imglabel.getWidth(), imglabel.getHeight(), Image.SCALE_SMOOTH);
				scaledImage = new ImageIcon(image);
				imglabel.setIcon(scaledImage);
			}
		});
		
		JScrollPane sp = new JScrollPane();
		sp.setBounds(50,320,625,130);
		sp.setViewportView(table);
		pinbuin.add(sp);
		
		var cariLabel = LabelField();
		cariLabel.setLocation(175,15);
		cariLabel.setText("Cari");
		cariLabel.setFont(new Font(null, Font.BOLD, 15));
		pinbuin.add(cariLabel);
		
		var cariTfield = InputTextField();
		cariTfield.setLocation(210,12);
		cariTfield.getDocument().addDocumentListener(new DocumentListener() {
			
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
				pinjammodel.setRowCount(0);
				try {
					Koneksi();
					rs = stmt.executeQuery("SELECT * FROM pinjaman_buku WHERE tgl_input like '%"+cariTfield.getText()+"%' or thn_ajaran like '%"+cariTfield.getText()+"%' or nama like '%"+cariTfield.getText()+"%' or kelas like '%"+cariTfield.getText()+"%' or kode_buku like '%"+cariTfield.getText()+"%' or judul like '%"+cariTfield.getText()+"%' or tgl_pinjam like '%"+cariTfield.getText()+"%' or tgl_kembali like '%"+cariTfield.getText()+"%'");
					while (rs.next()) {
						pinjammodel.addRow(new Object[] {
								rs.getString("tgl_input"),
								rs.getString("thn_ajaran"),
								rs.getString("nama"),
								rs.getString("kelas"),
								rs.getString("kode_buku"),
								rs.getString("judul"),
								rs.getString("jumlah"),
								rs.getString("tgl_pinjam"),
								rs.getString("tgl_kembali"),
								
						});
					}
					con.close();
				}
				catch(Exception e1) {
					return;
				}
			}
			
		});
		pinbuin.add(cariTfield);
		
		var clearButton = TemplateButton();
		clearButton.setBounds(350,290,150,30);
		clearButton.setText("Clear");
		clearButton.addActionListener(e->{
			tfield1.setText(null);
			tfield3.setText(null);
			tfield4.setText(null);
			tfield5.setText(null);
			tfield6.setText(null);
			tfield7.setText(null);
			imglabel.setIcon(null);
		});
		pinbuin.add(clearButton);
		
		var insButton = TemplateButton();
		insButton.setLocation(50,450);
		insButton.setText("Insert");
		insButton.addActionListener(e->{
			if(tfield1.getText().equals("") || tfield3.getText().equals("") || tfield5.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Lengkapi formulir minimal nama, buku dan jumlah yang dipinjam.");
				
			}else {
				
				try {
					Koneksi();
					sql = "INSERT INTO pinjaman_buku VALUES (NOW(),'"+cbyear.getSelectedItem()+"','"+tfield1.getText()+"','"+tfield2.getSelectedItem()+"','"+tfield3.getText()+"','"+tfield4.getText()+"','"+tfield5.getText()+"','"+tfield6.getText()+"','"+tfield7.getText()+"')";
					
					stmt.executeUpdate(sql);
					con.close();
				}catch(Exception e1) {
					System.out.println(e1);
				}
//				JOptionPane.showMessageDialog(null, "pinjaman "+tfield1.getText()+" telah disimpan");
			}
			TampilPinjamanBuku();
		});
		pinbuin.add(insButton);
		
		var upButton = TemplateButton();
		upButton.setLocation(200,450);
		upButton.setText("Update");
		upButton.addActionListener(e->{
			int i = table.getSelectedRow();
			TableModel modeli = table.getModel();
			try {
				Koneksi();
				sql = "UPDATE pinjaman_buku SET thn_ajaran='"+cbyear.getSelectedItem()+"',nama='"+tfield1.getText()+"',kelas='"+tfield2.getSelectedItem()+"',kode_buku='"+tfield3.getText()+"',judul='"+tfield4.getText()+"',jumlah='"+tfield5.getText()+"',tgl_pinjam='"+tfield6.getText()+"',tgl_kembali='"+tfield7.getText()+"' WHERE tgl_input='"+modeli.getValueAt(i, 0)+"'";
				stmt.executeUpdate(sql);
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "pinjaman "+tfield1.getText()+" telah diupdate");
			TampilPinjamanBuku();
		});
		pinbuin.add(upButton);
		
		var delButton = TemplateButton();
		delButton.setLocation(350,450);
		delButton.setText("Delete");
		delButton.addActionListener(e->{
			int i = table.getSelectedRow();
			TableModel modeli = table.getModel();
			try {
				Koneksi();
				sql = "DELETE FROM pinjaman_buku WHERE tgl_input='"+modeli.getValueAt(i, 0)+"'";
				stmt.executeUpdate(sql);
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "pinjaman "+tfield1.getText()+" telah dihapus");
			TampilPinjamanBuku();
			
			tfield1.setText(null);
			tfield3.setText(null);
			tfield4.setText(null);
			tfield5.setText(null);
			tfield6.setText(null);
			tfield7.setText(null);
			imglabel.setIcon(null);
		});
		pinbuin.add(delButton);
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(pinbuin.getSize());
		unscaledImage = new ImageIcon("./col2-3.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		pinbuin.add(gradcol);
		
		TampilPinjamanBuku();
	}

	void PinjamanBarangList() {
		pinbalis = new JPanel();
		pinbalis.setLayout(null);
		pinbalis.setSize(1040,600);
		pinbalis.setBackground(col1);
		pinjamBarangTemplate.add(pinbalis);
		
		var inputPanelButton = TemplateButton();
		inputPanelButton.setBounds(10,10,150,30);
		inputPanelButton.setText("Input / Edit");
		inputPanelButton.addActionListener(e->{
			pinbain.setVisible(true);
			pinbalis.setVisible(false);
		});
		pinbalis.add(inputPanelButton);
		
		JLabel imglabel = new JLabel();
		imglabel.setBounds(980,50,50,65);
		imglabel.setOpaque(true);
		pinbalis.add(imglabel);
		
		JTable table = new JTable();
		table.setModel(pinjammodel1);
		table.setFocusable(false);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//stobulis.setVisible(false);
				int i = table.getSelectedRow();
				TableModel modeli = table.getModel();
		        
		        try {
		        	Koneksi();
		        	sql = "select gambar from stok_barang where kode_barang = '"+modeli.getValueAt(i, 4).toString()+"'";
		        	rs = stmt.executeQuery(sql);
		        	
		        	imgfile = new File("./bargimg.bin");
		        	fos = new FileOutputStream(imgfile);
		        	
		        	if(rs.next()) {
		        		is = rs.getBinaryStream("gambar");
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
		
		JScrollPane sp = new JScrollPane();
		sp.setBounds(10,50,970,490);
		sp.setViewportView(table);
		pinbalis.add(sp);
		
		var cariLabel = LabelField();
		cariLabel.setLocation(175,15);
		cariLabel.setText("Cari");
		cariLabel.setFont(new Font(null, Font.BOLD, 15));
		pinbalis.add(cariLabel);
		
		var cariTfield = InputTextField();
		cariTfield.setLocation(210,12);
		cariTfield.getDocument().addDocumentListener(new DocumentListener() {
			
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
				pinjammodel1.setRowCount(0);
				try {
					Koneksi();
					rs = stmt.executeQuery("SELECT * FROM pinjaman_barang WHERE tgl_input like '%"+cariTfield.getText()+"%' or thn_ajaran like '%"+cariTfield.getText()+"%' or nama like '%"+cariTfield.getText()+"%' or kelas like '%"+cariTfield.getText()+"%' or kode_barang like '%"+cariTfield.getText()+"%' or nama_barang like '%"+cariTfield.getText()+"%' or tgl_pinjam like '%"+cariTfield.getText()+"%' or tgl_kembali like '%"+cariTfield.getText()+"%'");
					while (rs.next()) {
						pinjammodel1.addRow(new Object[] {
								rs.getString("tgl_input"),
								rs.getString("thn_ajaran"),
								rs.getString("nama"),
								rs.getString("kelas"),
								rs.getString("kode_barang"),
								rs.getString("nama_barang"),
								rs.getString("jumlah"),
								rs.getString("tgl_pinjam"),
								rs.getString("tgl_kembali"),
								
						});
					}
					con.close();
				}
				catch(Exception e1) {
					System.out.println(e1);
				}
			}
			
		});
		pinbalis.add(cariTfield);
		
		export1 = new JButton("Export");
		export1.setBounds(930,10,100,30);
		export1.addActionListener(e->{
			expanel = new myExportPanel();
			expanel.filedir = new File("./multiperpus/pinjaman_barang/");

			expanel.filecsv = new File("./multiperpus/pinjaman_barang/pinjaman_barang.CSV");
			expanel.csv = expanel.filecsv.getAbsolutePath().replace("\\", "/");
			expanel.rcsv = "SELECT 'tgl_input','thn_ajaran','nama','kelas','kode_barang','nama_barang','jumlah','tgl_pinjam','tgl_kembali' "
					+ "UNION ALL SELECT tgl_input,thn_ajaran,nama,kelas,kode_barang,nama_barang,jumlah,tgl_pinjam,tgl_kembali "
					+ "FROM pinjaman_barang INTO OUTFILE '"+expanel.csv
					+ "' fields terminated by ';' lines terminated by '\n'".replace("\\", "/");
			
			expanel.filesql = new File("./multiperpus/pinjaman_barang/pinjaman_barang.sql");
			expanel.sql = expanel.filesql.getAbsolutePath().replace("\\", "/");
			expanel.rsql = "./xampp/mysql/bin/mysqldump -u root multiperpus pinjaman_barang -r "+expanel.sql;
			
		});
		pinbalis.add(export1);
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(pinbalis.getSize());
		unscaledImage = new ImageIcon("./col2-3.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		pinbalis.add(gradcol);
		
		PinjamanBarangInput();
		pinbain.setVisible(false);
		
		TampilPinjamanBarang();
	}

	void PinjamanBarangInput() {
		pinbain = new JPanel();
		pinbain.setLayout(null);
		pinbain.setSize(1040,600);
		pinbain.setBackground(col1);
		pinbain.setVisible(false);
		pinjamBarangTemplate.add(pinbain);
		
		var listPanelButon = TemplateButton();
		listPanelButon.setBounds(10,10,150,30);
		listPanelButon.setText("List Barang");
		listPanelButon.addActionListener(e->{
			pinbalis.setVisible(true);
			pinbain.setVisible(false);
		});
		pinbain.add(listPanelButon);
		
		var label = LabelField();
		label.setLocation(50,50);
		label.setText("Tahun Ajaran");
		pinbain.add(label);
		
		var label1 = LabelField();
		label1.setLocation(50,80);
		label1.setText("Nama Peminjam");
		pinbain.add(label1);
		
		var label7 = LabelField();
		label7.setLocation(50,110);
		label7.setText("Kelas");
		pinbain.add(label7);
		
		var label2 = LabelField();
		label2.setLocation(50,140);
		label2.setText("Kode Barang");
		pinbain.add(label2);
		
		var label3 = LabelField();
		label3.setLocation(50,170);
		label3.setText("Nama Barang");
		pinbain.add(label3);
		
		var label4 = LabelField();
		label4.setLocation(50,230);
		label4.setText("Jumlah");
		pinbain.add(label4);
		
		var label5 = LabelField();
		label5.setLocation(50,260);
		label5.setText("Tanggal Pinjam");
		pinbain.add(label5);
		
		var label6 = LabelField();
		label6.setLocation(50,290);
		label6.setText("Tanggal Kembali");
		pinbain.add(label6);
		
		//...........................................TEXTFIELDS..............................................//
		
		JLabel imglabel = new JLabel();
		imglabel.setBounds(690,50,300,400);
		imglabel.setOpaque(true);
		imglabel.setText("Gambar");
		imglabel.setHorizontalAlignment(JLabel.CENTER);
		pinbain.add(imglabel);
		
		ArrayList<String> year_tmp = new ArrayList<String>();
		for(int d=Calendar.getInstance().get(Calendar.YEAR); d >= Calendar.getInstance().get(Calendar.YEAR) - 10; d--) {
		    year_tmp.add(d+"-"+(d+1));
		}
		JComboBox cbyear = new JComboBox(year_tmp.toArray());
		cbyear.setBounds(210,50,100,30);
		pinbain.add(cbyear);
		
		var tfield1 = InputTextField();
		tfield1.setBounds(210,80,465,30);
		pinbain.add(tfield1);
		
		String[] kls = {"Staff","1","1A","1b","2","2A","2B","3","3A","3B","4","4A","4B","5","5A","5B","6","6A","6B"};
		JComboBox tfield2 = new JComboBox(kls);
		tfield2.setBounds(210,110,100,30);
		pinbain.add(tfield2);
		
		kd_barang = new JTextField();
		var tfield3 = kd_barang;
		tfield3.setBounds(210,140,465,30);
		tfield3.setEnabled(false);
		tfield3.getDocument().addDocumentListener(new DocumentListener() {
			
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
				try {
		        	Koneksi();
		        	sql = "select gambar from stok_barang where kode_barang = '"+tfield3.getText()+"'";
		        	rs = stmt.executeQuery(sql);
		        	
		        	imgfile = new File("./bargimg.bin");
		        	fos = new FileOutputStream(imgfile);
		        	
		        	if(rs.next()) {
		        		is = rs.getBinaryStream("gambar");
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
		pinbain.add(tfield3);
		
		j_barang = new JTextField();
		var tfield4 = j_barang;
		tfield4.setBounds(210,170,465,30);
		tfield4.setEnabled(false);
		pinbain.add(tfield4);
		
		var buku = TemplateButton();
		buku.setBounds(210,200,150,30);
		buku.setText("Pilih Barang");
		buku.addActionListener(e->{
			new myToolPicker();
		});
		pinbain.add(buku);
		
		var tfield5 = InputTextField();
		tfield5.setBounds(210,230,465,30);
		pinbain.add(tfield5);
		
		//ttgl pinjam
		tglfield2 = new JTextField();
		var tfield6 = tglfield2;
		tfield6.setBounds(210,260,100,30);
		tfield6.setEnabled(false);
		pinbain.add(tfield6);
		
		JLabel xlbl = new JLabel("  X  ");
		xlbl.setBounds(75,2,22,20);
		xlbl.setHorizontalAlignment(JLabel.RIGHT);
		xlbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tfield6.setText(null);
			}

		});
		tfield6.add(xlbl);
		
		JButton dateButton = new JButton("...");
		dateButton.setBackground(col2);
		dateButton.setForeground(Color.white);
		dateButton.setBounds(310,260,25,30);
		dateButton.addActionListener(e->{
			new myDatePicker2();
		});
		pinbain.add(dateButton);
		
		//tgl kembali
		tglfield3 = new JTextField();
		var tfield7 = tglfield3;
		tfield7.setBounds(210,290,100,30);
		tfield7.setEnabled(false);
		pinbain.add(tfield7);
		
		JLabel xlbl1 = new JLabel("  X  ");
		xlbl1.setBounds(75,2,22,20);
		xlbl1.setHorizontalAlignment(JLabel.RIGHT);
		xlbl1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tfield7.setText(null);
			}

		});
		tfield7.add(xlbl1);
		
		JButton dateButton1 = new JButton("...");
		dateButton1.setBackground(col2);
		dateButton1.setForeground(Color.white);
		dateButton1.setBounds(310,290,25,30);
		dateButton1.addActionListener(e->{
			new myDatePicker3();
		});
		pinbain.add(dateButton1);
		
		JTable table = new JTable();
		table.setModel(pinjammodel1);
		table.setFocusable(false);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//stobulis.setVisible(false);
				int i = table.getSelectedRow();
				TableModel modeli = table.getModel();
		        tfield1.setText(modeli.getValueAt(i, 2).toString());
		        tfield2.setSelectedItem(modeli.getValueAt(i, 3).toString());
		        tfield3.setText(modeli.getValueAt(i, 4).toString());
		        tfield4.setText(modeli.getValueAt(i, 5).toString());
		        tfield5.setText(modeli.getValueAt(i, 6).toString());
		        tfield6.setText(modeli.getValueAt(i, 7).toString());
		        tfield7.setText(modeli.getValueAt(i, 8).toString());
		        
		        try {
		        	Koneksi();
		        	sql = "select gambar from stok_barang where kode_barang = '"+tfield3.getText()+"'";
		        	rs = stmt.executeQuery(sql);
		        	
		        	imgfile = new File("./bargimg.bin");
		        	fos = new FileOutputStream(imgfile);
		        	
		        	if(rs.next()) {
		        		is = rs.getBinaryStream("gambar");
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
		
		JScrollPane sp = new JScrollPane();
		sp.setBounds(50,320,625,130);
		sp.setViewportView(table);
		pinbain.add(sp);
		
		var cariLabel = LabelField();
		cariLabel.setLocation(175,15);
		cariLabel.setText("Cari");
		cariLabel.setFont(new Font(null, Font.BOLD, 15));
		pinbain.add(cariLabel);
		
		var cariTfield = InputTextField();
		cariTfield.setLocation(210,12);
		cariTfield.getDocument().addDocumentListener(new DocumentListener() {
			
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
				pinjammodel1.setRowCount(0);
				try {
					Koneksi();
					rs = stmt.executeQuery("SELECT * FROM pinjaman_barang WHERE tgl_input like '%"+cariTfield.getText()+"%' or thn_ajaran like '%"+cariTfield.getText()+"%' or nama like '%"+cariTfield.getText()+"%' or kelas like '%"+cariTfield.getText()+"%' or kode_barang like '%"+cariTfield.getText()+"%' or nama_barang like '%"+cariTfield.getText()+"%' or tgl_pinjam like '%"+cariTfield.getText()+"%' or tgl_kembali like '%"+cariTfield.getText()+"%'");
					while (rs.next()) {
						pinjammodel1.addRow(new Object[] {
								rs.getString("tgl_input"),
								rs.getString("thn_ajaran"),
								rs.getString("nama"),
								rs.getString("kelas"),
								rs.getString("kode_barang"),
								rs.getString("nama_barang"),
								rs.getString("jumlah"),
								rs.getString("tgl_pinjam"),
								rs.getString("tgl_kembali"),
								
						});
					}
					con.close();
				}
				catch(Exception e1) {
					return;
				}
			}
			
		});
		pinbain.add(cariTfield);
		
		var clearButton = TemplateButton();
		clearButton.setBounds(350,290,150,30);
		clearButton.setText("Clear");
		clearButton.addActionListener(e->{
			tfield1.setText(null);
			tfield3.setText(null);
			tfield2.setSelectedItem("");
			tfield4.setText(null);
			tfield5.setText(null);
			tfield6.setText(null);
			tfield7.setText(null);
			imglabel.setIcon(null);
		});
		pinbain.add(clearButton);
		
		var insButton = TemplateButton();
		insButton.setLocation(50,450);
		insButton.setText("Insert");
		insButton.addActionListener(e->{
			if(tfield1.getText().equals("") || tfield3.getText().equals("") || tfield5.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Lengkapi formulir minimal nama, nama barang dan jumlah yang dipinjam.");
				
			}else {
				
				try {
					Koneksi();
					sql = "INSERT INTO pinjaman_barang VALUES (NOW(),'"+cbyear.getSelectedItem()+"','"+tfield1.getText()+"','"+tfield2.getSelectedItem()+"','"+tfield3.getText()+"','"+tfield4.getText()+"','"+tfield5.getText()+"','"+tfield6.getText()+"','"+tfield7.getText()+"')";
					
					stmt.executeUpdate(sql);
					con.close();
				}catch(Exception e1) {
					System.out.println(e1);
				}
//				JOptionPane.showMessageDialog(null, "pinjaman "+tfield1.getText()+" telah disimpan");
			}
			TampilPinjamanBarang();
		});
		pinbain.add(insButton);
		
		var upButton = TemplateButton();
		upButton.setLocation(200,450);
		upButton.setText("Update");
		upButton.addActionListener(e->{
			int i = table.getSelectedRow();
			TableModel modeli = table.getModel();
			try {
				Koneksi();
				sql = "UPDATE pinjaman_barang SET thn_ajaran='"+cbyear.getSelectedItem()+"',nama='"+tfield1.getText()+"',kelas='"+tfield2.getSelectedItem()+"',kode_barang='"+tfield3.getText()+"',nama_barang='"+tfield4.getText()+"',jumlah='"+tfield5.getText()+"',tgl_pinjam='"+tfield6.getText()+"',tgl_kembali='"+tfield7.getText()+"' WHERE tgl_input='"+modeli.getValueAt(i, 0)+"'";
				stmt.executeUpdate(sql);
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "pinjaman "+tfield1.getText()+" telah diupdate");
			TampilPinjamanBarang();
		});
		pinbain.add(upButton);
		
		var delButton = TemplateButton();
		delButton.setLocation(350,450);
		delButton.setText("Delete");
		delButton.addActionListener(e->{
			int i = table.getSelectedRow();
			TableModel modeli = table.getModel();
			try {
				Koneksi();
				sql = "DELETE FROM pinjaman_barang WHERE tgl_input='"+modeli.getValueAt(i, 0)+"'";
				stmt.executeUpdate(sql);
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "pinjaman "+tfield1.getText()+" telah dihapus");
			TampilPinjamanBarang();
			
			tfield1.setText(null);
			tfield3.setText(null);
			tfield2.setSelectedItem("");
			tfield4.setText(null);
			tfield5.setText(null);
			tfield6.setText(null);
			tfield7.setText(null);
			imglabel.setIcon(null);
		});
		pinbain.add(delButton);
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(pinbain.getSize());
		unscaledImage = new ImageIcon("./col2-3.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		pinbain.add(gradcol);
		
		TampilPinjamanBarang();
	}

	
	void KunjunganTemplate() {
		kunjungantemplate = new JPanel();
		kunjungantemplate.setLayout(null);
		kunjungantemplate.setBounds(10,70,1040,600);
		kunjungantemplate.setVisible(false);
		tempmain.add(kunjungantemplate);
		KunjunganList();
	}
	
	void KunjunganList() {
		kunjulis = new JPanel();
		kunjulis.setLayout(null);
		kunjulis.setSize(1040,600);
		kunjulis.setBackground(col1);
		kunjungantemplate.add(kunjulis);
		
		var inputPanelButton = TemplateButton();
		inputPanelButton.setBounds(10,10,160,30);
		inputPanelButton.setText("Input / Edit");
		inputPanelButton.addActionListener(e->{
			
			kunjuin.setVisible(true);
			kunjulis.setVisible(false);
			
		});
		kunjulis.add(inputPanelButton);
		
		JTable table = new JTable();
		table.setModel(kunjungmodel);
		table.setFocusable(false);
		
		JScrollPane sp = new JScrollPane();
		sp.setBounds(10,50,1020,540);
		sp.setViewportView(table);
		kunjulis.add(sp);
		
		var cariLabel = LabelField();
		cariLabel.setLocation(210,15);
		cariLabel.setText("Cari");
		cariLabel.setFont(new Font(null, Font.BOLD, 15));
		kunjulis.add(cariLabel);
		
		var cariTfield = InputTextField();
		cariTfield.setLocation(245,12);
		cariTfield.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				searchKunjungan();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				searchKunjungan();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				searchKunjungan();
			}
			
			public void searchKunjungan() {
				
				if(cariTfield.getText().equals("")) {
					TampilKunjungan();
				}else {
					kunjungmodel.setRowCount(0);
					try {
						Koneksi();
						rs = stmt.executeQuery("SELECT * FROM daftar_kunjungan WHERE tgl_input like '%"+cariTfield.getText()+"%' or thn_ajaran like '%"+cariTfield.getText()+"%' or tgl_msk like '%"+cariTfield.getText()+"%' or jam_msk like '%"+cariTfield.getText()+"%' or nama like '%"+cariTfield.getText()+"%' or kelas like '%"+cariTfield.getText()+"%' or jenis_kunjungan like '%"+cariTfield.getText()+"%' or keterangan like '%"+cariTfield.getText()+"%'");
						while (rs.next()) {
							kunjungmodel.addRow(new Object[] {
									rs.getString("tgl_input"),
									rs.getString("thn_ajaran"),
									rs.getString("tgl_msk"),
									rs.getString("jam_msk"),
									rs.getString("nama"),
									rs.getString("kelas"),
									rs.getString("jenis_kunjungan"),
									rs.getString("keterangan")
									
							});
						}
						con.close();
					}
					catch(Exception e1) {
						return;
					}
				}
			}
			
		});
		kunjulis.add(cariTfield);
		
		export3 = new JButton("Export");
		export3.setBounds(930,10,100,30);
		export3.addActionListener(e->{
			expanel = new myExportPanel();
			expanel.setLocation(tempmain.getLocationOnScreen().x+850,tempmain.getLocationOnScreen().y+110);
			expanel.filedir = new File("./multiperpus/daftar_kunjungan/");

			expanel.filecsv = new File("./multiperpus/daftar_kunjungan/daftar_kunjungan.CSV");
			expanel.csv = expanel.filecsv.getAbsolutePath().replace("\\", "/");
			expanel.rcsv = "SELECT 'tgl_input','thn_ajaran','tgl_msk','nama','kelas','jenis_kunjungan','keterangan' "
					+ "UNION ALL SELECT tgl_input,thn_ajaran,tgl_msk,nama,kelas,jenis_kunjungan,keterangan "
					+ "FROM daftar_kunjungan INTO OUTFILE '"+expanel.csv
					+ "' fields terminated by ';' lines terminated by '\n'".replace("\\", "/");
			
			expanel.filesql = new File("./multiperpus/daftar_kunjungan/daftar_kunjungan.sql");
			expanel.sql = expanel.filesql.getAbsolutePath().replace("\\", "/");
			expanel.rsql = "./xampp/mysql/bin/mysqldump -u root multiperpus daftar_kunjungan -r "+expanel.sql;
			
		});
		kunjulis.add(export3);
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(kunjulis.getSize());
		unscaledImage = new ImageIcon("./col2-2.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		kunjulis.add(gradcol);
		
		KunjunganInput();
		kunjuin.setVisible(false);
		
		TampilKunjungan();
		
	}

	void KunjunganInput() {
		kunjuin = new JPanel();
		kunjuin.setLayout(null);
		kunjuin.setSize(1040,600);
		kunjuin.setBackground(col1);
		kunjungantemplate.add(kunjuin);
		
		var listPanelButton = TemplateButton();
		listPanelButton.setBounds(10,10,160,30);
		listPanelButton.setText("List Kunjungan");
		listPanelButton.addActionListener(e->{
			
			kunjulis.setVisible(true);
			kunjuin.setVisible(false);
			
		});
		kunjuin.add(listPanelButton);
		
		var label = LabelField();
		label.setLocation(50,50);
		label.setText("Tahun Ajaran");
		kunjuin.add(label);
		
		var label1 = LabelField();
		label1.setLocation(50,80);
		label1.setText("Tanggal Masuk");
		kunjuin.add(label1);
		
		var label2 = LabelField();
		label2.setLocation(50,110);
		label2.setText("Jam Masuk");
		kunjuin.add(label2);
		
		var label3 = LabelField();
		label3.setLocation(50,140);
		label3.setText("Nama");
		kunjuin.add(label3);
		
		var label4 = LabelField();
		label4.setLocation(50,170);
		label4.setText("Kelas");
		kunjuin.add(label4);
		
		var label5 = LabelField();
		label5.setLocation(50,200);
		label5.setText("Jenis Kunjungan");
		kunjuin.add(label5);

		var label6 = LabelField();
		label6.setLocation(50,230);
		label6.setText("Keterangan");
		kunjuin.add(label6);
		
		//...........................................TEXTFIELDS..............................................//
		
//		var tfield = InputTextField();
//		tfield.setBounds(210,50,780,25);
//		kunjuin.add(tfield);
		
		ArrayList<String> year_tmp = new ArrayList<String>();
		for(int d=Calendar.getInstance().get(Calendar.YEAR); d >= Calendar.getInstance().get(Calendar.YEAR) - 10; d--) {
		    year_tmp.add(d+"-"+(d+1));
		}
		JComboBox cbyear = new JComboBox(year_tmp.toArray());
		cbyear.setBounds(210,50,100,30);
		kunjuin.add(cbyear);
		
		tglfield4 = new JTextField();
		var tfield1 = tglfield4;
		tfield1.setBounds(210,80,100,30);
		tfield1.setEnabled(false);
		kunjuin.add(tfield1);
		
		JLabel xlbl = new JLabel("  X  ");
		xlbl.setBounds(75,2,22,20);
		xlbl.setHorizontalAlignment(JLabel.RIGHT);
		xlbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tfield1.setText(null);
			}

		});
		tfield1.add(xlbl);
		
		JButton dateButton = new JButton("...");
		dateButton.setBackground(col2);
		dateButton.setForeground(Color.white);
		dateButton.setBounds(310,80,25,30);
		dateButton.addActionListener(e->{
			new myDatePicker4();
		});
		kunjuin.add(dateButton);
		
		jamfield = new JTextField();
		var tfield2 = jamfield;
		tfield2.setBounds(210,110,100,30);
		tfield2.setEnabled(false);
		kunjuin.add(tfield2);
		
		JLabel xlbl1 = new JLabel("  X  ");
		xlbl1.setBounds(75,2,22,20);
		xlbl1.setHorizontalAlignment(JLabel.RIGHT);
		xlbl1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tfield2.setText(null);
			}

		});
		tfield2.add(xlbl1);
		
		JButton timeButton = new JButton("...");
		timeButton.setBackground(col2);
		timeButton.setForeground(Color.white);
		timeButton.setBounds(310,110,25,30);
		timeButton.addActionListener(e->{
			new myTimePicker();
		});
		kunjuin.add(timeButton);
		
		var tfield3 = InputTextField();
		tfield3.setBounds(210,140,780,30);
		kunjuin.add(tfield3);
		
		String[] kls = {"Tamu","1","1A","1b","2","2A","2B","3","3A","3B","4","4A","4B","5","5A","5B","6","6A","6B"};
		JComboBox tfield4 = new JComboBox(kls);
		tfield4.setBounds(210,170,100,30);
		kunjuin.add(tfield4);
		
		
		String[] kategori = {"Perpustakaan", "Kelas", "UKS", "Musholla"};
		JComboBox cbbox = new JComboBox(kategori);
		cbbox.setBounds(210,200,125,30);
		kunjuin.add(cbbox);
		
		var tfield6 = InputTextField();
		tfield6.setBounds(210,230,780,30);
		kunjuin.add(tfield6);
		
		JTable table = new JTable();
		table.setModel(kunjungmodel);
		table.setFocusable(false);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//stobulis.setVisible(false);
				int i = table.getSelectedRow();
		        TableModel modeli = table.getModel();
		        
		        cbyear.setSelectedItem(modeli.getValueAt(i, 1).toString());
		        tfield1.setText(modeli.getValueAt(i, 2).toString());
		        tfield2.setText(modeli.getValueAt(i, 3).toString());
		        tfield3.setText(modeli.getValueAt(i, 4).toString());
		        tfield4.setSelectedItem(modeli.getValueAt(i, 5).toString());;
		        cbbox.setSelectedItem(modeli.getValueAt(i, 6).toString());
		        tfield6.setText(modeli.getValueAt(i, 7).toString());
		        
			}
		});
		
		JScrollPane sp = new JScrollPane();
		sp.setBounds(50,290,940,210);
		sp.setViewportView(table);
		kunjuin.add(sp);
		
		var insButton = TemplateButton();
		insButton.setLocation(50,500);
		insButton.setText("Insert");
		insButton.addActionListener(e->{
			if(tfield1.getText().equals("") || tfield3.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Lengkapi formulir minimal tanggal masuk dan nama.");
				
			}else {
				
				try {
					Koneksi();
					sql = "INSERT INTO daftar_kunjungan VALUES (NOW(),'"+cbyear.getSelectedItem()+"','"+tfield1.getText()+"','"+tfield2.getText()+"','"+tfield3.getText()+"','"+tfield4.getSelectedItem()+"','"+cbbox.getSelectedItem()+"','"+tfield6.getText()+"')";
					
					stmt.executeUpdate(sql);
					con.close();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
//				JOptionPane.showMessageDialog(null, "Kunjungan "+tfield3.getText()+" telah disimpan");
			}
			TampilKunjungan();
		});
		kunjuin.add(insButton);
		
		var upButton = TemplateButton();
		upButton.setLocation(200,500);
		upButton.setText("Update");
		upButton.addActionListener(e->{
			int i = table.getSelectedRow();
			TableModel modeli = table.getModel();
	        
			try {
				Koneksi();
				sql = "UPDATE daftar_kunjungan SET thn_ajaran='"+cbyear.getSelectedItem()+"',tgl_msk='"+tfield1.getText()+"',jam_msk='"+tfield2.getText()+"',nama='"+tfield3.getText()+"',kelas='"+tfield4.getSelectedItem()+"',jenis_kunjungan='"+cbbox.getSelectedItem()+"',keterangan='"+tfield6.getText()+"' WHERE tgl_input='"+modeli.getValueAt(i, 0).toString()+"'";
				
				stmt.executeUpdate(sql);
				con.close();
			}catch(Exception e1){
				//JOptionPane.showMessageDialog(null, e1);
				//return;
			}
			JOptionPane.showMessageDialog(null, "Kunjungan "+tfield3.getText()+" telah diupdate.");
			TampilKunjungan();
		});
		kunjuin.add(upButton);
		
		var delButton = TemplateButton();
		delButton.setLocation(350,500);
		delButton.setText("Delete");
		delButton.addActionListener(e->{
			int confirm = JOptionPane.showConfirmDialog(null, "<html>Data yang dihapus tidak akan bisa dikembalikan.<br>Hapus data "+tfield3.getText()+"?", "Penghapusan Data Kunjungan",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(confirm==0) {
				int i = table.getSelectedRow();
				TableModel modeli = table.getModel();
				
				try {
					Koneksi();
					sql = "DELETE FROM daftar_kunjungan WHERE tgl_input = '"+modeli.getValueAt(i, 0).toString()+"'";
					
					stmt.executeUpdate(sql);
					con.close();
					
				}catch(Exception e1) {
					//JOptionPane.showMessageDialog(null, e1);
					//return;
				}
				JOptionPane.showMessageDialog(null, "Buku "+tfield3.getText()+" telah dihapus");
			}
			TampilKunjungan();
			
			tfield2.setText(null);
			tfield2.setText(null);
			tfield3.setText(null);
			tfield4.setSelectedItem("");
			cbbox.setSelectedItem("");
			tfield6.setText(null);
		});
		kunjuin.add(delButton);
		
		var cariLabel = LabelField();
		cariLabel.setLocation(210,15);
		cariLabel.setText("Cari");
		cariLabel.setFont(new Font(null, Font.BOLD, 15));
		kunjuin.add(cariLabel);
		
		var cariTfield = InputTextField();
		cariTfield.setLocation(245,12);
		cariTfield.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				searchKunjungan();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				searchKunjungan();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				searchKunjungan();
			}
			
			public void searchKunjungan() {
				if(cariTfield.getText().equals("")) {
					TampilKunjungan();
				}else {
					kunjungmodel.setRowCount(0);
					try {
						Koneksi();
						rs = stmt.executeQuery("SELECT * FROM daftar_kunjungan WHERE tgl_input like '%"+cariTfield.getText()+"%' or thn_ajaran like '%"+cariTfield.getText()+"%' or tgl_msk like '%"+cariTfield.getText()+"%' or jam_msk like '%"+cariTfield.getText()+"%' or nama like '%"+cariTfield.getText()+"%' or kelas like '%"+cariTfield.getText()+"%' or jenis_kunjungan like '%"+cariTfield.getText()+"%' or keterangan like '%"+cariTfield.getText()+"%'");
						while (rs.next()) {
							kunjungmodel.addRow(new Object[] {
									rs.getString("tgl_input"),
									rs.getString("thn_ajaran"),
									rs.getString("tgl_msk"),
									rs.getString("jam_msk"),
									rs.getString("nama"),
									rs.getString("kelas"),
									rs.getString("jenis_kunjungan"),
									rs.getString("keterangan")
									
							});
						}
						con.close();
					}
					catch(Exception e1) {
						return;
					}
				}
			}
			
		});
		kunjuin.add(cariTfield);
		
		var clearButton = TemplateButton();
		clearButton.setLocation(50,260);
		clearButton.setSize(150,30);
		clearButton.setText("Clear");
		clearButton.addActionListener(e->{
			tfield2.setText(null);
			tfield2.setText(null);
			tfield3.setText(null);
			tfield4.setSelectedItem("");
			cbbox.setSelectedItem("");
			tfield6.setText(null);
			cariTfield.setText(null);
		});
		kunjuin.add(clearButton);
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(kunjuin.getSize());
		unscaledImage = new ImageIcon("./col2-2.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		kunjuin.add(gradcol);
		
		TampilKunjungan();
		
	}

	void StokTemplate() {
		stoktemplate = new JPanel();
		stoktemplate.setLayout(null);
		stoktemplate.setBounds(10,70,1040,600);
		stoktemplate.setVisible(false);
		tempmain.add(stoktemplate);
		
		stokBukuTemplate = new JPanel();
		stokBukuTemplate.setLayout(null);
		stokBukuTemplate.setBounds(0,50,1040,550);
		//stokBukuTemplate.setVisible(false);
		stoktemplate.add(stokBukuTemplate);
		
		stokBarangTemplate = new JPanel();
		stokBarangTemplate.setLayout(null);
		stokBarangTemplate.setBounds(0,50,1040,550);
		//stokBarangTemplate.setVisible(false);
		stoktemplate.add(stokBarangTemplate);
		
		var stokBukuTab = TemplateButton();
		stokBukuTab.setLocation(70,0);
		stokBukuTab.setText("Buku");
		stokBukuTab.addActionListener(e->{
			stokBarangTemplate.setVisible(false);
			stokBukuTemplate.setVisible(true);
		});
		stoktemplate.add(stokBukuTab);
	
		var stokBarangTab = TemplateButton();
		stokBarangTab.setLocation(220,0);
		stokBarangTab.setText("Barang");
		stokBarangTab.addActionListener(e->{
			
			stokBukuTemplate.setVisible(false);
			stokBarangTemplate.setVisible(true);
		});
		stoktemplate.add(stokBarangTab);
		
		StokBukuList();
		StokBarangList();
		stokBarangTemplate.setVisible(false);
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(stoktemplate.getSize());
		unscaledImage = new ImageIcon("./col2-2.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		stoktemplate.add(gradcol);
		
	}

	void StokBukuList() {
		stobulis = new JPanel();
		stobulis.setLayout(null);
		stobulis.setSize(1040,600);
		stobulis.setBackground(col1);
		stokBukuTemplate.add(stobulis);
		
		var inputPanelButton = TemplateButton();
		inputPanelButton.setBounds(10,10,150,30);
		inputPanelButton.setText("Input / Edit");
		inputPanelButton.addActionListener(e->{
			stobuin.setVisible(true);
			stobulis.setVisible(false);
		});
		stobulis.add(inputPanelButton);
		
		JLabel imglabel = new JLabel();
		imglabel.setBounds(980,50,50,65);
		imglabel.setOpaque(true);
		stobulis.add(imglabel);
		
		JTable table = new JTable();
		table.setModel(bukumodel);
		table.setFocusable(false);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int i = table.getSelectedRow();
				TableModel modeli = table.getModel();
				
				try {
		        	Koneksi();
		        	sql = "SELECT gambar FROM stok_buku WHERE kode_buku = '"+modeli.getValueAt(i, 0).toString()+"'";
		        	rs = stmt.executeQuery(sql);
		        	
		        	imgfile = new File("./bukuimg.bin");
		        	fos = new FileOutputStream(imgfile);
		        	
		        	if(rs.next()) {
		        		is = rs.getBinaryStream("gambar");
		        		byte[] buffer = new byte[1024];
		        		while(is.read(buffer)>0) {
		        			fos.write(buffer);
		        		}
		        	}
		        	con.close();
		        }catch(Exception e1) {
		        	System.out.println(e1);
		        }
		        unscaledImage = new ImageIcon("./bukuimg.bin");
				image = unscaledImage.getImage().getScaledInstance(imglabel.getWidth(), imglabel.getHeight(), Image.SCALE_SMOOTH);
				scaledImage = new ImageIcon(image);
				imglabel.setIcon(scaledImage);
			}
		});
		
		JScrollPane sp = new JScrollPane();
		sp.setBounds(10,50,970,490);
		sp.setViewportView(table);
		stobulis.add(sp);

		var cariLabel = LabelField();
		cariLabel.setLocation(185,15);
		cariLabel.setText("Cari");
		cariLabel.setFont(new Font(null, Font.BOLD, 15));
		stobulis.add(cariLabel);
		
		var cariTfield = InputTextField();
		cariTfield.setLocation(220,12);
		cariTfield.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				searchBuku();
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				searchBuku();
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				searchBuku();
				
			}
			public void searchBuku() {
				bukumodel.setRowCount(0);
				try {
					Koneksi();
					rs = stmt.executeQuery("SELECT `kode_buku`, `penulis`, `tahun`, `judul`, `kota`, `penerbit`, `kategori`, `jumlah` FROM stok_buku WHERE kode_buku like '%"+cariTfield.getText()+"%' or penulis like '%"+cariTfield.getText()+"%' or judul like '%"+cariTfield.getText()+"%' or kota like '%"+cariTfield.getText()+"%' or  penerbit like '%"+cariTfield.getText()+"%' or kategori like '%"+cariTfield.getText()+"%'");
					while (rs.next()) {
						bukumodel.addRow(new Object[] {
								rs.getString("kode_buku"),
								rs.getString("penulis"),
								rs.getString("tahun"),
								rs.getString("judul"),
								rs.getString("kota"),
								rs.getString("penerbit"),
								rs.getString("kategori"),
								rs.getString("jumlah")
						});
					}
					con.close();
				}
				catch(Exception e1) {
					return;
				}
			}
			
		});
		stobulis.add(cariTfield);
		
		export5 = new JButton("Export");
		export5.setText("Export");
		export5.setBounds(930,10,100,30);
		export5.addActionListener(e->{
			expanel = new myExportPanel();
			expanel.filedir = new File("./multiperpus/stok_buku/");

			expanel.filecsv = new File("./multiperpus/stok_buku/stok_buku.CSV");
			expanel.csv = expanel.filecsv.getAbsolutePath().replace("\\", "/");
			expanel.rcsv = "SELECT 'kode_buku', 'penulis', 'tahun', 'judul', 'kota', 'penerbit', 'kategori', 'jumlah' "
					+ "UNION ALL SELECT kode_buku, penulis, tahun, judul, kota, penerbit, kategori, jumlah "
					+ "FROM stok_buku INTO OUTFILE '"+expanel.csv
					+ "' fields terminated by ';' lines terminated by '\n'".replace("\\", "/");
			
			expanel.filesql = new File("./multiperpus/stok_buku/stok_buku.sql");
			expanel.sql = expanel.filesql.getAbsolutePath().replace("\\", "/");
			expanel.rsql = "./xampp/mysql/bin/mysqldump -u root multiperpus stok_buku -r "+expanel.sql;
			
		});
		stobulis.add(export5);
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(stobulis.getSize());
		unscaledImage = new ImageIcon("./col2-3.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		stobulis.add(gradcol);
		
		StokBukuInput();
		stobuin.setVisible(false);
		
		TampilBuku();
	}
	
	void StokBukuInput() {
		
		stobuin = new JPanel();
		stobuin.setLayout(null);
		stobuin.setSize(1040,600);
		stobuin.setBackground(col1);
		stobuin.setVisible(false);
		stokBukuTemplate.add(stobuin);
		
		var listPanelButon = TemplateButton();
		listPanelButon.setBounds(10,10,150,30);
		listPanelButon.setText("List Buku");
		listPanelButon.addActionListener(e->{
			
			stobulis.setVisible(true);
			stobuin.setVisible(false);
			
		});
		stobuin.add(listPanelButon);
		
		var label = LabelField();
		label.setLocation(50,50);
		label.setText("Kode Buku");
		stobuin.add(label);
		
		var label1 = LabelField();
		label1.setLocation(50,80);
		label1.setText("Penyusun");
		stobuin.add(label1);
		
		var label2 = LabelField();
		label2.setLocation(50,110);
		label2.setText("Tahun");
		stobuin.add(label2);
		
		var label3 = LabelField();
		label3.setLocation(50,140);
		label3.setText("Judul");
		stobuin.add(label3);
		
		var label4 = LabelField();
		label4.setLocation(50,170);
		label4.setText("Kota");
		stobuin.add(label4);
		
		var label5 = LabelField();
		label5.setLocation(50,200);
		label5.setText("Penerbit");
		stobuin.add(label5);
		
		var label6 = LabelField();
		label6.setLocation(50,230);
		label6.setText("Kategori");
		stobuin.add(label6);
		
		var label7 = LabelField();
		label7.setLocation(50,260);
		label7.setText("Jumlah");
		stobuin.add(label7);
		
		
		//...........................................TEXTFIELDS..............................................//
		
		var tfield = InputTextField();
		tfield.setLocation(185,50);
		tfield.setEnabled(false);
		stobuin.add(tfield);
		
		var tfield1 = InputTextField();
		tfield1.setLocation(185,80);
		stobuin.add(tfield1);
		
		var tfield2 = InputTextField();
		tfield2.setLocation(185,110);
		stobuin.add(tfield2);
		
		var tfield3 = InputTextField();
		tfield3.setLocation(185,140);
		stobuin.add(tfield3);
		
		var tfield4 = InputTextField();
		tfield4.setLocation(185,170);
		stobuin.add(tfield4);
		
		var tfield5 = InputTextField();
		tfield5.setLocation(185,200);
		stobuin.add(tfield5);
		
		String[] kategori = {"","Fiksi", "Non-Fiksi", "Komik", "Kitab Suci"};
		JComboBox cbbox = new JComboBox(kategori);
		cbbox.setSize(125,30);
		cbbox.setLocation(185,230);
		stobuin.add(cbbox);
		
		var tfield7 = InputTextField();
		tfield7.setLocation(185,260);
		stobuin.add(tfield7);
		
		JLabel imglabel = new JLabel();
		imglabel.setBounds(690,50,300,400);
		imglabel.setOpaque(true);
		imglabel.setText("Gambar");
		imglabel.setHorizontalAlignment(JLabel.CENTER);
		stobuin.add(imglabel);
		
		JTable table = new JTable();
		table.setModel(bukumodel);
		table.setFocusable(false);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				
				TableModel modeli = table.getModel();
		        tfield.setText(modeli.getValueAt(i, 0).toString());
		        tfield1.setText(modeli.getValueAt(i, 1).toString());
		        tfield2.setText(modeli.getValueAt(i, 2).toString());
		        tfield3.setText(modeli.getValueAt(i, 3).toString());
		        tfield4.setText(modeli.getValueAt(i, 4).toString());
		        tfield5.setText(modeli.getValueAt(i, 5).toString());
		        cbbox.setSelectedItem(modeli.getValueAt(i, 6).toString());
		        tfield7.setText(modeli.getValueAt(i, 7).toString());
		        
		        try {
		        	Koneksi();
		        	sql = "select gambar from stok_buku where kode_buku = '"+modeli.getValueAt(i, 0).toString()+"'";
		        	rs = stmt.executeQuery(sql);
		        	
		        	imgfile = new File("./bukuimg.bin");
		        	fos = new FileOutputStream(imgfile);
		        	
		        	if(rs.next()) {
		        		is = rs.getBinaryStream("gambar");
		        		byte[] buffer = new byte[1024];
		        		while(is.read(buffer)>0) {
		        			fos.write(buffer);
		        		}
		        	}
		        	con.close();
		        }catch(Exception e1) {
		        	System.out.println(e1);
		        }
		        unscaledImage = new ImageIcon("./bukuimg.bin");
				image = unscaledImage.getImage().getScaledInstance(imglabel.getWidth(), imglabel.getHeight(), Image.SCALE_SMOOTH);
				scaledImage = new ImageIcon(image);
				imglabel.setIcon(scaledImage);
			}
		});
		
		JScrollPane sp = new JScrollPane();
		sp.setBounds(50,320,625,130);
		sp.setViewportView(table);
		stobuin.add(sp);
		
		var imgChooserButton = TemplateButton();
		imgChooserButton.setText("Tambah/Ubah Gambar");
		imgChooserButton.setLocation(690,450);
		imgChooserButton.setSize(300,TemplateButton().getHeight());
		imgChooserButton.addActionListener(e->{
			fchooser = new JFileChooser();
			fchooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files ('jpg','jpeg','jpe','png','pns')", "jpg", "jpeg","jpe", "png", "pns"));
			fchooser.setAcceptAllFileFilterUsed(true);
			
			int response = fchooser.showOpenDialog(null);
			if(response == JFileChooser.APPROVE_OPTION) {
				imgfile = new File(fchooser.getSelectedFile().getAbsolutePath());
				unscaledImage = new ImageIcon(imgfile.getAbsolutePath());
				image = unscaledImage.getImage().getScaledInstance(imglabel.getWidth(), imglabel.getHeight(), Image.SCALE_SMOOTH);
				scaledImage = new ImageIcon(image);
				imglabel.setIcon(scaledImage);
			}
		});
		stobuin.add(imgChooserButton);
		
		var insButton = TemplateButton();
		insButton.setLocation(50,450);
		insButton.setText("Insert");
		insButton.addActionListener(e->{
			if(tfield3.getText().equals("") || tfield7.getText().equals("") || imglabel.getIcon()==null) {
				JOptionPane.showMessageDialog(null, "Lengkapi formulir minimal judul, jumlah dan gambar.");
			}else {
				
				String kdbuku;
				
				if(tfield.getText().equals("")) {
					LocalDateTime localDate = LocalDateTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddhhmmssa");
					String formattedString = localDate.format(formatter);
					kdbuku = ("N"+formattedString);
				}else {
					kdbuku = tfield.getText();
				}
				
				String path = imgfile.getAbsolutePath();
				path = path.replace("\\", "/");
				//System.out.println(path);
				
				try {
					Koneksi();
					sql = "insert into stok_buku values('"+kdbuku+"','"+tfield1.getText()+"','"+tfield2.getText()+"','"+tfield3.getText()
					+"','"+tfield4.getText()+"','"+tfield5.getText()+"','"+cbbox.getSelectedItem()+"','"+tfield7.getText()+"',LOAD_FILE('"+path+"'))";
					
					stmt.executeUpdate(sql);
					con.close();
					
					//JOptionPane.showMessageDialog(null, "Buku "+tfield3.getText()+" telah disimpan");
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "<html>Error pada saat menyimpan "+tfield3.getText()+": <br>"+e1);
				}
				
			}
			TampilBuku();
			
		});
		stobuin.add(insButton);
		
		var upButton = TemplateButton();
		upButton.setLocation(200,450);
		upButton.setText("Update");
		upButton.addActionListener(e->{
			String path = imgfile.getAbsolutePath();
			path = path.replace("\\", "/");
			
			int i = table.getSelectedRow();
			TableModel modeli = table.getModel();
	        
			try {
				Koneksi();
				sql = "update stok_buku set kode_buku='"+tfield.getText()+"',penulis='"+tfield1.getText()+"',tahun='"+tfield2.getText()+"',judul='"+tfield3.getText()
				+"',kota='"+tfield4.getText()+"',penerbit='"+tfield5.getText()+"',kategori='"+cbbox.getSelectedItem()+"',jumlah='"+tfield7.getText()
				+"',gambar=LOAD_FILE('"+path+"') where kode_buku='"+modeli.getValueAt(i, 0).toString()+"'";
				
				stmt.executeUpdate(sql);
				con.close();
			}catch(Exception e1){
				System.out.println(e1);
			}
			JOptionPane.showMessageDialog(null, "Buku "+tfield3.getText()+" telah diupdate");
			TampilBuku();
		});
		stobuin.add(upButton);
		
		var delButton = TemplateButton();
		delButton.setLocation(350,450);
		delButton.setText("Delete");
		delButton.addActionListener(e->{
			int confirm = JOptionPane.showConfirmDialog(null, "<html>Data yang dihapus tidak akan bisa dikembalikan.<br>Hapus data "+tfield3.getText()+"?", "Penghapusan Data Buku",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(confirm==0) {
				try {
					Koneksi();
					sql = "DELETE FROM stok_buku WHERE kode_buku = '"+tfield.getText()+"'";
					
					stmt.executeUpdate(sql);
					con.close();
					
				}catch(Exception e1) {
					e1.printStackTrace();
					return;
				}
				JOptionPane.showMessageDialog(null, "Buku "+tfield3.getText()+" telah dihapus");
			}
			TampilBuku();
			
			tfield.setText(null);
			tfield1.setText(null);
			tfield2.setText(null);
			tfield3.setText(null);
			tfield4.setText(null);
			tfield5.setText(null);
			tfield7.setText(null);
			imglabel.setIcon(null);
		});
		stobuin.add(delButton);
		
		var cariLabel = LabelField();
		cariLabel.setLocation(185,15);
		cariLabel.setText("Cari");
		cariLabel.setFont(new Font(null, Font.BOLD, 15));
		stobuin.add(cariLabel);
		
		var cariTfield = InputTextField();
		cariTfield.setLocation(220,12);
		cariTfield.getDocument().addDocumentListener(new DocumentListener() {
			
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				searchBuku();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				searchBuku();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				searchBuku();
			}
			public void searchBuku() {
				
				bukumodel.setRowCount(0);
				try {
					Koneksi();
					rs = stmt.executeQuery("SELECT `kode_buku`, `penulis`, `tahun`, `judul`, `kota`, `penerbit`, `kategori`, `jumlah` FROM stok_buku WHERE kode_buku like '%"+cariTfield.getText()+"%' or penulis like '%"+cariTfield.getText()+"%' or judul like '%"+cariTfield.getText()+"%' or kota like '%"+cariTfield.getText()+"%' or  penerbit like '%"+cariTfield.getText()+"%' or kategori like '%"+cariTfield.getText()+"%'");
					while (rs.next()) {
						bukumodel.addRow(new Object[] {
								rs.getString("kode_buku"),
								rs.getString("penulis"),
								rs.getString("tahun"),
								rs.getString("judul"),
								rs.getString("kota"),
								rs.getString("penerbit"),
								rs.getString("kategori"),
								rs.getString("jumlah")
						});
					}
					con.close();
				}
				catch(Exception e1) {
					return;
				}
			}
			
		});
		stobuin.add(cariTfield);

		var clearButton = TemplateButton();
		clearButton.setBounds(50,290,150,30);
		clearButton.setText("Clear");
		clearButton.addActionListener(e->{
			tfield.setText(null);
			tfield1.setText(null);
			tfield2.setText(null);
			tfield3.setText(null);
			tfield4.setText(null);
			tfield5.setText(null);
			cbbox.setSelectedItem("");
			tfield7.setText(null);
			imglabel.setIcon(null);
			cariTfield.setText(null);
		});
		stobuin.add(clearButton);
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(stobuin.getSize());
		unscaledImage = new ImageIcon("./col2-3.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		stobuin.add(gradcol);
		
		TampilBuku();
		
	}

	void StokBarangList() {
		stobalis = new JPanel();
		stobalis.setLayout(null);
		stobalis.setSize(1040,600);
		stobalis.setBackground(col1);
		stokBarangTemplate.add(stobalis);
		
		var inputPanelButton = TemplateButton();
		inputPanelButton.setBounds(10,10,150,30);
		inputPanelButton.setText("Input / Edit");
		inputPanelButton.addActionListener(e->{
			stobain.setVisible(true);
			stobalis.setVisible(false);
		});
		stobalis.add(inputPanelButton);
		
		JLabel imglabel = new JLabel();
		imglabel.setBounds(980,50,50,65);
		imglabel.setOpaque(true);
		stobalis.add(imglabel);
		
		JTable table = new JTable();
		table.setModel(barangmodel);
		table.setFocusable(false);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				TableModel modeli = table.getModel();
		        
		        try {
		        	Koneksi();
		        	sql = "select gambar from stok_barang where kode_barang = '"+modeli.getValueAt(i, 0).toString()+"'";
		        	rs = stmt.executeQuery(sql);
		        	
		        	imgfile = new File("./bargimg.bin");
		        	fos = new FileOutputStream(imgfile);
		        	
		        	if(rs.next()) {
		        		is = rs.getBinaryStream("gambar");
		        		byte[] buffer = new byte[1024];
		        		while(is.read(buffer)>0) {
		        			fos.write(buffer);
		        		}
		        	}
		        	con.close();
		        }catch(Exception e1) {
		        	//JOptionPane.showMessageDialog(null, e1);
		        	//return;
		        }
		        unscaledImage = new ImageIcon("./bargimg.bin");
				image = unscaledImage.getImage().getScaledInstance(imglabel.getWidth(), imglabel.getHeight(), Image.SCALE_SMOOTH);
				scaledImage = new ImageIcon(image);
				imglabel.setIcon(scaledImage);
			}
		});
		
		JScrollPane sp = new JScrollPane();
		sp.setBounds(10,50,970,490);
		sp.setViewportView(table);
		stobalis.add(sp);
		
		var cariLabel = LabelField();
		cariLabel.setLocation(185,15);
		cariLabel.setText("Cari");
		cariLabel.setFont(new Font(null, Font.BOLD, 15));
		stobalis.add(cariLabel);
		
		var cariTfield = InputTextField();
		cariTfield.setLocation(220,12);
		cariTfield.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				searchBarang();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				searchBarang();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				searchBarang();
			}
			
			public void searchBarang() {
				if(cariTfield.getText().equals("")) {
					TampilBarang();
				}else {
					barangmodel.setRowCount(0);
					try {
						Koneksi();
						rs = stmt.executeQuery("SELECT * FROM stok_barang WHERE kode_barang like '%"+cariTfield.getText()+"%' or nama_barang like '%"+cariTfield.getText()+"%' or merek like '%"+cariTfield.getText()+"%' or kadaluarsa like '%"+cariTfield.getText()+"%' or kategori like '%"+cariTfield.getText()+"%'");
						while (rs.next()) {
							barangmodel.addRow(new Object[] {
									rs.getString("kode_barang"),
									rs.getString("nama_barang"),
									rs.getString("merek"),
									rs.getString("kadaluarsa"),
									rs.getString("kategori"),
									rs.getString("jumlah")
							});
						}
						con.close();
					}
					catch(Exception e1) {
						return;
					}
				}
			}
			
		});
		stobalis.add(cariTfield);

		export4 = new JButton("Export");
		export4.setBounds(930,10,100,30);
		export4.addActionListener(e->{
			expanel = new myExportPanel();
			expanel.filedir = new File("./multiperpus/stok_barang/");

			expanel.filecsv = new File("./multiperpus/stok_barang/stok_barang.CSV");
			expanel.csv = expanel.filecsv.getAbsolutePath().replace("\\", "/");
			expanel.rcsv = "SELECT 'kode_barang','nama_barang','merek','kadaluarasa','kategori','jumlah' "
					+ "UNION ALL SELECT kode_barang, nama_barang, merek, kadaluarsa, kategori, jumlah "
					+ "FROM stok_barang INTO OUTFILE '"+expanel.csv
					+ "' fields terminated by ';' lines terminated by '\n'".replace("\\", "/");
			
			expanel.filesql = new File("./multiperpus/stok_barang/stok_barang.sql");
			expanel.sql = expanel.filesql.getAbsolutePath().replace("\\", "/");
			expanel.rsql = "./xampp/mysql/bin/mysqldump -u root multiperpus stok_barang -r "+expanel.sql;
			
			
		});
		stobalis.add(export4);
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(stobalis.getSize());
		unscaledImage = new ImageIcon("./col2-3.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		stobalis.add(gradcol);
		
		StokBarangInput();
		stobain.setVisible(false);
		
		TampilBarang();
		
	}
	
	void StokBarangInput() {
		
		stobain = new JPanel();
		stobain.setLayout(null);
		stobain.setSize(1040,600);
		stobain.setBackground(col1);
		stobain.setVisible(false);
		
		stokBarangTemplate.add(stobain);
		
		var listPanelButon = TemplateButton();
		listPanelButon.setBounds(10,10,150,30);
		listPanelButon.setText("List Barang");
		listPanelButon.addActionListener(e->{
			
			stobalis.setVisible(true);
			stobain.setVisible(false);
			
		});
		stobain.add(listPanelButon);
		
		var label = LabelField();
		label.setLocation(50,50);
		label.setText("Kode Barang");
		stobain.add(label);
		
		var label1 = LabelField();
		label1.setLocation(50,80);
		label1.setText("Nama Barang");
		stobain.add(label1);
		
		var label2 = LabelField();
		label2.setLocation(50,110);
		label2.setText("Merek");
		stobain.add(label2);
		
		var label3 = LabelField();
		label3.setLocation(50,140);
		label3.setText("Kadaluarsa");
		stobain.add(label3);
		
		var label4 = LabelField();
		label4.setLocation(50,170);
		label4.setText("Kategori");
		stobain.add(label4);
		
		var label5 = LabelField();
		label5.setLocation(50,200);
		label5.setText("Jumlah");
		stobain.add(label5);
		
		//...........................................TEXTFIELDS..............................................//
		
		var tfield = InputTextField();
		tfield.setLocation(185,50);
		tfield.setEnabled(false);
		stobain.add(tfield);
		
		var tfield1 = InputTextField();
		tfield1.setLocation(185,80);
		stobain.add(tfield1);
		
		var tfield2 = InputTextField();
		tfield2.setLocation(185,110);
		stobain.add(tfield2);
		
		tglfield5 = new JTextField();
		var tfield3 = tglfield5;
		tfield3.setBounds(185,140,100,30);
		tfield3.setEnabled(false);
		stobain.add(tfield3);
		
		JLabel xlbl = new JLabel("  X  ");
		xlbl.setBounds(75,2,22,20);
		xlbl.setHorizontalAlignment(JLabel.RIGHT);
		xlbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tfield3.setText(null);
			}

		});
		tfield3.add(xlbl);
		
		JButton dateButton = new JButton("...");
		dateButton.setBackground(col2);
		dateButton.setForeground(Color.white);
		dateButton.setBounds(285,140,25,30);
		dateButton.addActionListener(e->{
			new myDatePicker5();
		});
		stobain.add(dateButton);
		
		String[] kategori = {"","Elektronik","Olahraga","Alat Tulis", "Alat Peraga", "Peralatan", "UKS"};
		JComboBox cbbox = new JComboBox(kategori);
		cbbox.setSize(125,30);
		cbbox.setLocation(185,170);
		stobain.add(cbbox);
		
		var tfield5 = InputTextField();
		tfield5.setLocation(185,200);
		stobain.add(tfield5);
		
		JLabel imglabel = new JLabel("Gambar");
		imglabel.setHorizontalAlignment(JLabel.CENTER);
		imglabel.setBounds(690,50,300,400);
		imglabel.setOpaque(true);
		stobain.add(imglabel);
		
		var imgChooserButton = TemplateButton();
		imgChooserButton.setText("Tambah/Ubah Gambar");
		imgChooserButton.setLocation(690,450);
		imgChooserButton.setSize(300,TemplateButton().getHeight());
		imgChooserButton.addActionListener(e->{
			fchooser = new JFileChooser();
			fchooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files ('jpg','jpeg','jfif','png','pns')", "jpg", "jpeg","jpe", "png", "pns"));
			fchooser.setAcceptAllFileFilterUsed(true);
			
			int response = fchooser.showOpenDialog(null);
			if(response == JFileChooser.APPROVE_OPTION) {
				imgfile = new File(fchooser.getSelectedFile().getAbsolutePath());
				unscaledImage = new ImageIcon(imgfile.getAbsolutePath());
				image = unscaledImage.getImage().getScaledInstance(imglabel.getWidth(), imglabel.getHeight(), Image.SCALE_SMOOTH);
				scaledImage = new ImageIcon(image);
				imglabel.setIcon(scaledImage);
			}
		});
		stobain.add(imgChooserButton);
		
		//barangmodel = new DefaultTableModel();
		
		JTable table = new JTable();
		table.setModel(barangmodel);
		table.setFocusable(false);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				TableModel modeli = table.getModel();
		        
				tfield.setText(modeli.getValueAt(i, 0).toString());
		        tfield1.setText(modeli.getValueAt(i, 1).toString());
		        tfield2.setText(modeli.getValueAt(i, 2).toString());
		        tfield3.setText(modeli.getValueAt(i, 3).toString());
		        cbbox.setSelectedItem(modeli.getValueAt(i, 4).toString());
		        tfield5.setText(modeli.getValueAt(i, 5).toString());
		        try {
		        	Koneksi();
		        	sql = "select gambar from stok_barang where kode_barang = '"+modeli.getValueAt(i, 0).toString()+"'";
		        	rs = stmt.executeQuery(sql);
		        	
		        	imgfile = new File("./bargimg.bin");
		        	fos = new FileOutputStream(imgfile);
		        	
		        	if(rs.next()) {
		        		is = rs.getBinaryStream("gambar");
		        		byte[] buffer = new byte[1024];
		        		while(is.read(buffer)>0) {
		        			fos.write(buffer);
		        		}
		        	}
		        	con.close();
		        }catch(Exception e1) {
		        	//JOptionPane.showMessageDialog(null, e1);
		        	//return;
		        }
		        unscaledImage = new ImageIcon("./bargimg.bin");
				image = unscaledImage.getImage().getScaledInstance(imglabel.getWidth(), imglabel.getHeight(), Image.SCALE_SMOOTH);
				scaledImage = new ImageIcon(image);
				imglabel.setIcon(scaledImage);
			}
		});
		
		JScrollPane sp = new JScrollPane();
		sp.setBounds(50,260,625,190);
		sp.setViewportView(table);
		stobain.add(sp);
		
		var insButton = TemplateButton();
		insButton.setLocation(50,450);
		insButton.setText("Insert");
		insButton.addActionListener(e->{
			if(tfield1.getText().equals("") || tfield5.getText().equals("") || imglabel.getIcon()==null) {
				JOptionPane.showMessageDialog(null, "Lengkapi formulir minimal nama barang, jumlah dan gambar");
				
			}else {
				
				String kdbarang;
				
				if(tfield.getText().equals("")) {
					LocalDateTime localDate = LocalDateTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddhhmmssa");
					String formattedString = localDate.format(formatter);
					kdbarang = ("N"+formattedString);
				}else {
					kdbarang = tfield.getText();
				}
				
				String path = imgfile.getAbsolutePath();
				path = path.replace("\\", "/");
				
				try {
					Koneksi();
					sql = "INSERT INTO stok_barang VALUES ('"+kdbarang+"','"+tfield1.getText()+"','"+tfield2.getText()+"','"+tfield3.getText()+"','"+cbbox.getSelectedItem()+"','"+tfield5.getText()+"',LOAD_FILE('"+path+"'))";
					
					stmt.executeUpdate(sql);
					con.close();
				}catch(Exception e1) {
					//JOptionPane.showMessageDialog(null, e1);
					//return;
				}
//				JOptionPane.showMessageDialog(null, "Barang "+tfield1.getText()+" telah disimpan");
			}
			TampilBarang();
		});
		stobain.add(insButton);
		
		var upButton = TemplateButton();
		upButton.setLocation(200,450);
		upButton.setText("Update");
		upButton.addActionListener(e->{
			String kdbarang;
			if(tfield.getText().equals("")) {
				LocalDateTime localDate = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddhhmmss");
				String formattedString = localDate.format(formatter);
				kdbarang = ("n"+formattedString);
			}else {
				kdbarang = tfield.getText();
			}
			
			String path = imgfile.getAbsolutePath();
			path = path.replace("\\", "/");
			
			int i = table.getSelectedRow();
			TableModel modeli = table.getModel();
	        
			try {
				Koneksi();
				sql = "UPDATE stok_barang SET kode_barang='"+kdbarang+"',nama_barang='"+tfield1.getText()+"',merek='"+tfield2.getText()+"',kadaluarsa='"+tfield3.getText()+"',kategori='"+cbbox.getSelectedItem()+"',jumlah='"+tfield5.getText()+"',gambar=LOAD_FILE('"+path+"') WHERE kode_barang='"+modeli.getValueAt(i, 0).toString()+"'";
				
				stmt.executeUpdate(sql);
				con.close();
			}catch(Exception e1){
				//JOptionPane.showMessageDialog(null, e1);
				//return;
			}
			JOptionPane.showMessageDialog(null, "Barang "+tfield1.getText()+" telah diupdate");
			TampilBarang();
		});
		stobain.add(upButton);
		
		var delButton = TemplateButton();
		delButton.setLocation(350,450);
		delButton.setText("Delete");
		delButton.addActionListener(e->{
			int confirm = JOptionPane.showConfirmDialog(null, "<html>Data yang dihapus tidak akan bisa dikembalikan.<br>Hapus data "+tfield1.getText()+"?", "Penghapusan Data Barang",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(confirm==0) {
				try {
					Koneksi();
					sql = "DELETE FROM stok_barang WHERE kode_barang = '"+tfield.getText()+"'";
					
					stmt.executeUpdate(sql);
					con.close();
					
				}catch(Exception e1) {
					//JOptionPane.showMessageDialog(null, e1);
					//return;
				}
				JOptionPane.showMessageDialog(null, "Barang "+tfield1.getText()+" telah dihapus.");
			}
			TampilBarang();
			
			tfield.setText(null);
			tfield1.setText(null);
			tfield2.setText(null);
			tfield3.setText(null);
			cbbox.setSelectedItem("");
			tfield5.setText(null);
			imglabel.setIcon(null);
		});
		stobain.add(delButton);
		
		var cariLabel = LabelField();
		cariLabel.setLocation(185,15);
		cariLabel.setText("Cari");
		cariLabel.setFont(new Font(null, Font.BOLD, 15));
		stobain.add(cariLabel);
		
		var cariTfield = InputTextField();
		cariTfield.setLocation(220,12);
		cariTfield.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				searchBarang();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				searchBarang();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				searchBarang();
			}
			
			public void searchBarang() {
//				if(cariTfield.getText().equals("")) {
//					TampilBarang();
//				}else {
//					
//				}
				barangmodel.setRowCount(0);
				try {
					Koneksi();
					rs = stmt.executeQuery("SELECT * FROM stok_barang WHERE kode_barang like '%"+cariTfield.getText()+"%' or nama_barang like '%"+cariTfield.getText()+"%' or merek like '%"+cariTfield.getText()+"%' or kadaluarsa like '%"+cariTfield.getText()+"%' or kategori like '%"+cariTfield.getText()+"%'");
					while (rs.next()) {
						barangmodel.addRow(new Object[] {
								rs.getString("kode_barang"),
								rs.getString("nama_barang"),
								rs.getString("merek"),
								rs.getString("kadaluarsa"),
								rs.getString("kategori"),
								rs.getString("jumlah")
						});
					}
					con.close();
				}
				catch(Exception e1) {
					return;
				}
			}
			
		});
		stobain.add(cariTfield);

		var clearButton = TemplateButton();
		clearButton.setLocation(50,230);
		clearButton.setSize(150,30);
		clearButton.setText("Clear");
		clearButton.addActionListener(e->{
			tfield.setText(null);
			tfield1.setText(null);
			tfield2.setText(null);
			tfield3.setText(null);
			cbbox.setSelectedItem("");
			tfield5.setText(null);
			imglabel.setIcon(null);
			cariTfield.setText(null);
		});
		stobain.add(clearButton);
		
		JLabel gradcol = new JLabel();
		gradcol.setSize(stobain.getSize());
		unscaledImage = new ImageIcon("./col2-3.png");
		image = unscaledImage.getImage().getScaledInstance(gradcol.getWidth(), gradcol.getHeight(), Image.SCALE_SMOOTH);
		scaledImage = new ImageIcon(image);
		gradcol.setIcon(scaledImage);
		stobain.add(gradcol);
		
		TampilBarang();
		
	}

}
	
	
