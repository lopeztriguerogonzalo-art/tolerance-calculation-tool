import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class First extends JFrame implements ActionListener{
	private JLabel changeMode, logo, subtitle, lname, lproyectName, warning1, warning2;
	private JTextField name, proyectName;
	private JRadioButton day, night;
	private ButtonGroup bg;
	private JButton confirm;
	private Color background, daylabel, daytextfield, daytextfieldformat, daybutton, daybuttonformat;
	private ImageIcon image, imageNight;
	private Image sizeAdjust, sizeAdjust2;
	public static String mode = "day";
	public static String sname = "";
	public static String sproyectName = "";

	public First(){
		setLayout(null);
		setTitle("Tolerance Calculation Tool");
		setIconImage(new ImageIcon(getClass().getResource("images/icon_supplier.png")).getImage()); //Set Icon image
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//label design
		image = new ImageIcon("images/logo_supplier_first_day.png");
		imageNight = new ImageIcon("images/logo_supplier_first_night.png");
		logo = new JLabel();
		logo.setBounds(95, 70, 280, 140);
		add(logo);
		sizeAdjust = image.getImage().getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH);
		sizeAdjust2 = imageNight.getImage().getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH);
		logo.setIcon(new ImageIcon(sizeAdjust));

		subtitle = new JLabel("Tolerance Calculation Tool");
		subtitle.setBounds(107, 210, 300, 60);
		subtitle.setFont(new Font("Work Sans", 1, 20));
		subtitle.setForeground(new Color(90, 90, 90)); 
		add(subtitle);

		lname = new JLabel("Name: ");
		lname.setBounds(85, 315, 200, 20);
		lname.setFont(new Font("Work Sans", 1, 16));
		lname.setForeground(new Color(90, 90, 90));
		add(lname);
		
		lproyectName = new JLabel("Proyect Name: ");
		lproyectName.setBounds(85, 400, 200, 20);
		lproyectName.setFont(new Font("Work Sans", 1, 16));
		lproyectName.setForeground(new Color(90, 90, 90));
		add(lproyectName);

		changeMode = new JLabel("Change Mode: ");
		changeMode.setBounds(107, 20, 150, 20);
		changeMode.setFont(new Font("Work Sans", 1, 12));
		changeMode.setForeground(new Color(90, 90, 90));
		add(changeMode);

		warning1 = new JLabel("");
		warning1.setBounds(85, 375, 250, 20);
		warning1.setFont(new Font("Work Sans", 1, 10));
		warning1.setForeground(new Color(255, 0, 0));
		add(warning1);

		warning2 = new JLabel("");
		warning2.setBounds(85, 460, 300, 20);
		warning2.setFont(new Font("Work Sans", 1, 10));
		warning2.setForeground(new Color(255, 0, 0));
		add(warning2);

		
		//text field design
		name = new JTextField();
		name.setBounds(85, 345, 300, 30);
		add(name);

		proyectName = new JTextField();
		proyectName.setBounds(85, 430, 300, 30);
		add(proyectName);

		//radio button design
		bg = new ButtonGroup();

		day = new JRadioButton("Day Mode");
		day.setBounds(200, 20, 90, 20);
		day.setFont(new Font("Work Sans", 1, 12));
		day.setForeground(new Color(90, 90, 90));
		day.setSelected(true);
		day.setOpaque(false);
		day.addActionListener(this);
		add(day);
		bg.add(day);
		
		night = new JRadioButton("Night Mode");
		night.setBounds(290, 20, 150, 20);
		night.setFont(new Font("Work Sans", 1, 12));
		night.setForeground(new Color(90, 90, 90));
		night.setOpaque(false);
		night.addActionListener(this);
		add(night);
		bg.add(night);

		//button design
		confirm = new JButton("Confirm");
		confirm.setBounds(185, 520, 100, 40);
		confirm.setFont(new Font("Work Sans", 1, 14));
		confirm.addActionListener(this);
		add(confirm);

		//Colors -> setup day mode

		Color background = getContentPane().getBackground();
		Color daylabel = subtitle.getForeground();
		Color daytextfield = name.getBackground();
		Color daytextfieldformat = name.getForeground();
		Color daybuttonformat = confirm.getForeground();
		Color daybutton = confirm.getBackground();

	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == confirm){
			//if there is nothing written show a message
			if(name.getText().trim().equals("") || proyectName.getText().trim().equals("")){
				if(name.getText().trim().equals("")){
					warning1.setText("Please introduce a Name before continuing.");		
				}

				if(proyectName.getText().trim().equals("")){
					warning2.setText("Please introduce a Proyect Name before continuing");
				}
				
			}else{
				//get name and proyect name on Strings
				sname = name.getText();
				sproyectName = proyectName.getText();
					
				//execute Main interface while First remains invisible
				this.setVisible(false);
				Main i = new Main();
				i.setBounds(0, 0, 1330, 800);
				i.setVisible(true);
				i.setLocationRelativeTo(null);
				i.setResizable(false);
			}

		}else if(e.getSource() == day){
			//Arrays
			JLabel[] labels = {changeMode, logo, subtitle, lname, lproyectName};
			JTextField[] textfs = {name, proyectName};
			JRadioButton[] rbuttons = {day, night};

			mode = "day";
			getContentPane().setBackground(background);

			logo.setIcon(new ImageIcon(sizeAdjust));

			//labels
			for(JLabel label : labels){
				label.setForeground(daylabel);
			}

			//button
			confirm.setForeground(daybuttonformat);
			confirm.setBackground(daybutton);		

			//textfields
			for(JTextField textfield : textfs){
				textfield.setForeground(daytextfieldformat);
				textfield.setBackground(daytextfield);			
			}
			
			//radiobuttons
			for(JRadioButton rbutton : rbuttons){
				rbutton.setForeground(daylabel);

			}

		}else if(e.getSource() == night){
			//Arrays
			JLabel[] labels = {changeMode, logo, subtitle, lname, lproyectName};
			JTextField[] textfs = {name, proyectName};
			JRadioButton[] rbuttons = {day, night};

			mode = "night";
			getContentPane().setBackground(new Color(33, 35, 37));

			logo.setIcon(new ImageIcon(sizeAdjust2));

			//labels
			for(JLabel label : labels){
				label.setForeground(new Color(200, 200, 200));
			}

			//button
			confirm.setForeground(new Color(200, 200, 200));
			confirm.setBackground(new Color(41, 48, 56));		

			//textfields
			for(JTextField textfield : textfs){
				textfield.setForeground(new Color(255, 255, 255));
				textfield.setBackground(new Color(63, 65, 66));			
			}
			
			//radiobuttons
			for(JRadioButton rbutton : rbuttons){
				rbutton.setForeground(new Color(200, 200, 200));

			}
		}
	}

	public static void main(String args[]){
		First f = new First();
		f.setBounds(0, 0, 500, 680);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);	

	}
}