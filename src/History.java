import javax.swing.*;
import java.awt.*;

public class History extends JDialog{ 
	public static JLabel lhistory;
	public static JTextArea history;
	private JScrollPane sc;	
	String mode = Main.mode;

	public History(Frame parent){
		//ChatGPT help
		super(parent, "History", true); //call contructor from JDialog. true -> blocks Main while History is open
		
		setLayout(null);
		setIconImage(new ImageIcon(getClass().getResource("images/icon_supplier.png")).getImage()); //Set Icon image
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //does not close Main
		setSize(725, 350);
		setLocationRelativeTo(null);
		setResizable(false);

		if(mode.equals("night")){
			getContentPane().setBackground(new Color(33, 35, 37));
		}
		
		//label design
		lhistory = new JLabel("History of Calculations:");
		lhistory.setBounds(20, 10, 300, 20);
		lhistory.setFont(new Font("Work Sans", 3, 16));
		if(mode.equals("night")){
			lhistory.setForeground(new Color(200, 200, 200));

		} else{
			lhistory.setForeground(new Color(90, 90, 90));	
	
		}
		add(lhistory);
		
		//text field design
		history = new JTextArea("\n");
		history.setEditable(false);
		if(mode.equals("night")){
			history.setForeground(new Color(255, 255, 255));
			history.setBackground(new Color(63, 65, 66));	
		}
		sc = new JScrollPane(history);
		sc.setBounds(20, 40, 670, 255);
		add(sc);
		
		//add text from Main to History List
		history.setText(Main.textOnHistory);
	}
}