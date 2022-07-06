package perpus9;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.time.*;
import java.util.*;

import javax.swing.*;

public class myTimePicker extends JFrame{
	Color col1 = new Color(0,100,150);
	Color col2 = new Color(0,70,100);
	
	Dimension dframe = new Dimension(240,70);
	
	int dtk=0, mnt=0, jam=0;
	String date;
	
	JLabel panel = new JLabel();
	JLabel dtklbl, mntlbl, jamlbl;
	
	myTimePicker(){
		this.setResizable(false);
		this.setUndecorated(true);
		this.setLocation(MyFrame.jamfield.getLocationOnScreen().x,MyFrame.jamfield.getLocationOnScreen().y);
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
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(dframe);
		panel.setBackground(col2);
		this.add(panel);
		
		jamlbl = label();jamlbl.setText("jam");
		panel.add(jamlbl);
		mntlbl = label();mntlbl.setText("mnt");
		panel.add(mntlbl);
		dtklbl = label();dtklbl.setText("dtk");
		panel.add(dtklbl);
		
		JButton xBtn = new JButton("X");
		xBtn.setPreferredSize(new Dimension(35,26));
		xBtn.setFocusable(false);
		xBtn.addActionListener(e->{
			Dispose();
		});
		panel.add(xBtn);
		
		//jam
		ArrayList<String> year_tmp = new ArrayList<String>();
		for(int i=1; i <= 24; i++) {
		    year_tmp.add(i+"");
		}
		JComboBox cbyear = new JComboBox(year_tmp.toArray());
		cbyear.setBounds(0,16,65,25);
		cbyear.addActionListener(e->{
			jam = Integer.parseInt((String) cbyear.getSelectedItem());
			jamlbl.setText(jam+"");
		});
		panel.add(cbyear);
		
		
		//menit
		ArrayList<String> month_tmp = new ArrayList<String>();
		for(int i=1; i <= 59; i++) {
		    month_tmp.add(i+"");
		}
		JComboBox cbmonth = new JComboBox(month_tmp.toArray());
		cbmonth.setBounds(70,16,50,25);
		cbmonth.addActionListener(e->{
			mnt = cbmonth.getSelectedIndex()+1;
			mntlbl.setText(mnt+"");
		});
		panel.add(cbmonth);
		
		
		//detik
		ArrayList<String> date_tmp = new ArrayList<String>();
		for(int i=1; i <= 59; i++) {
		    date_tmp.add(i+"");
		}
		JComboBox cbdate = new JComboBox(date_tmp.toArray());
		cbdate.setBounds(145,16,50,25);
		cbdate.addActionListener(e->{
			dtk = Integer.parseInt((String) cbdate.getSelectedItem());
			dtklbl.setText(dtk+"");
		});
		panel.add(cbdate);
		
		JButton OkBtn = new JButton("Ok");
		OkBtn.setSize(cbdate.getPreferredSize());
		OkBtn.setFocusable(false);
		OkBtn.addActionListener(e->{
			try {
			LocalTime time = LocalTime.of(jam,mnt,dtk);
			MyFrame.jamfield.setText(time+"");
			Dispose();
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
