package perpus9;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.time.*;
import java.util.*;

import javax.swing.*;

public class myDatePicker1 extends JFrame{
	Color col1 = new Color(0,100,150);
	Color col2 = new Color(0,70,100);
	
	Dimension dframe = new Dimension(240,70);
	
	int tgl=0, bln=0, thn=0;
	String date;
	
	public static JLabel panel = new JLabel();
	JLabel tgllbl, blnlbl, thnlbl;
	
	myDatePicker1(){
		this.setResizable(false);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocation(MyFrame.tglfield1.getLocationOnScreen().x,MyFrame.tglfield1.getLocationOnScreen().y);
		this.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
			}
			@Override
			public void windowLostFocus(WindowEvent e) {
				Dispose();
			}
		});
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(dframe);
		panel.setBackground(col2);
		this.add(panel);
		
		thnlbl = label();thnlbl.setText("thn");
		panel.add(thnlbl);
		blnlbl = label();blnlbl.setText("bln");
		panel.add(blnlbl);
		tgllbl = label();tgllbl.setText("tgl");
		panel.add(tgllbl);
		
		JButton xBtn = new JButton("X");
		xBtn.setPreferredSize(new Dimension(35,26));
		xBtn.setFocusable(false);
		xBtn.addActionListener(e->{
			Dispose();
		});
		panel.add(xBtn);
		
		//tahun
		ArrayList<String> year_tmp = new ArrayList<String>();
		for(int i=Calendar.getInstance().get(Calendar.YEAR)+0; i >= Calendar.getInstance().get(Calendar.YEAR) - 9; i--) {
		    year_tmp.add(i+"");
		}
		JComboBox cbyear = new JComboBox(year_tmp.toArray());
		cbyear.setBounds(0,16,65,25);
		cbyear.addActionListener(e->{
			thn = Integer.parseInt((String) cbyear.getSelectedItem());
			thnlbl.setText(thn+"");
		});
		panel.add(cbyear);
		
		
		//bulan
		String[] blnlist = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Agu","Sep","Okt","Nov","Des"};
		JComboBox cbmonth = new JComboBox(blnlist);
		cbmonth.setBounds(70,16,50,25);
		cbmonth.addActionListener(e->{
			bln = cbmonth.getSelectedIndex()+1;
			blnlbl.setText(bln+"");
		});
		panel.add(cbmonth);
		
		
		//tanggal
		ArrayList<String> date_tmp = new ArrayList<String>();
		for(int i=1; i <= 31; i++) {
		    date_tmp.add(i+"");
		}
		JComboBox cbdate = new JComboBox(date_tmp.toArray());
		cbdate.setBounds(145,16,50,25);
		cbdate.addActionListener(e->{
			tgl = Integer.parseInt((String) cbdate.getSelectedItem());
			tgllbl.setText(tgl+"");
		});
		panel.add(cbdate);
		
		JButton OkBtn = new JButton("Ok");
		OkBtn.setSize(cbdate.getPreferredSize());
		OkBtn.setFocusable(false);
		OkBtn.addActionListener(e->{
			try {
				this.dispose();
				LocalDate date = LocalDate.of(thn,bln,tgl);
				MyFrame.tglfield1.setText(date+"");
				
			}catch(Exception e1) {
				JOptionPane.showMessageDialog(null, "tanggal belum dilengkapi");
			}
		});
		panel.add(OkBtn);
		
		ImageIcon unscaledImage = new ImageIcon("./col2.png");
		Image image = unscaledImage.getImage().getScaledInstance(panel.getPreferredSize().width, panel.getPreferredSize().height, Image.SCALE_SMOOTH);
		ImageIcon scaledImage = new ImageIcon(image);
		panel.setIcon(scaledImage);
		
		this.pack();
		this.setVisible(true);
	}
	
	void Dispose() {
		this.dispose();
	}

	JLabel label() {
		var lbl = new JLabel();
		lbl.setPreferredSize(new Dimension(60,16));
		lbl.setHorizontalAlignment(JLabel.CENTER);
		lbl.setBackground(col1);
		lbl.setForeground(Color.white);
		lbl.setOpaque(true);
		return lbl;
	}
}
