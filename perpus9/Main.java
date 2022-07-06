package perpus9;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	
	public static JFrame myframe;
	
	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(null, "error while loading Nimbus look and feel");
		}
		
		try {
			Runtime.getRuntime().exec("./xampp/apache_start.bat");
			Runtime.getRuntime().exec("./xampp/mysql_start.bat");
			myframe = new MyFrame();
//			myframe.setVisible(false);
			
			//optional password
//			new PassFrame();
		}catch(Exception e) {
			return;
		}

	}

}
