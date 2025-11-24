import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main extends JFrame implements ActionListener, ChangeListener{

	private ImageIcon image, imageNight;
	private Image sizeAdjust, sizeAdjust2;
	private JLabel logo, title, subtitle, targetName, changeMode, 
		lname, ldirection, lnominal, lupper, llower, 
		ltype, ldistribution, mm1, mm2, mm3, contributorList, 
		lwc, lstat, tolerancechain, lupload, luser, warningName, 
		warningNom, warningUp, warningLow, ltotalDistribution;
	private JButton remove, removeimage, add, calculate, empty, close, upload;
	private JTextField tname, tnominal, tupper, tlower, target;
	private JTextArea list, wc, stat;
	private JScrollPane sc, sc2, sc3;
	private JComboBox cdirection, ctype, cdistribution, ctotalDistribution;
	private JRadioButton day, night;
	private ButtonGroup bg;
	private JMenuBar menu;
	private JMenu file, mcalculate, view, about;
	private JMenuItem save, iclose, history, icalculate, iday, inight, author, tolcalc;
	private JCheckBox confirmation, negativeUp, negativeLow;
	private Color daybackground, daytitle, daysubtitle, daychange, daytolerancechain, 
		daytolerancechainformat, daylabel, daybutton, daybuttonformat, daytextf, 
		daytextfformat, daytexta, daytextaformat, daycombo, daycomboformat, dayradio, 
		dayradioformat, daymenubar, daymenu, daymenuformat, dayitem, dayitemformat, 
		daycheck, daycheckformat;

	//for event button "add"
	private int counter = 1;
	private List<String> nameCheck = new ArrayList<>();
	private List<String> directions = new ArrayList<>();
	private List<Float> targetDim = new ArrayList<>();
	private List<Float> upperTol = new ArrayList<>();
	private List<Float> lowerTol = new ArrayList<>();
	private List<Float> sigmas = new ArrayList<>();

	//for event button "remove"
	private String textBeforeAdd = "";
	private int previousNumber = 0;
	private List<String> previousText = new ArrayList<>();
	private List<Integer> counters = new ArrayList<>();

	//for window "History"
	private String thistory = ""; //this needs to be read by history interface
	public static String textOnHistory = "";
	public static String mode = First.mode; //Because status comes from first window
	private JLabel historyLabel = History.lhistory;
	private JTextArea historyTextArea = History.history;

	private String name = First.sname;
	private String proyectName = First.sproyectName;

	public Main(){
		setLayout(null);
		setTitle("Tolerance Calculation Tool");
		setIconImage(new ImageIcon(getClass().getResource("images/icon_supplier.png")).getImage()); //Set Icon image
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		//Menu Bar Design
		menu = new JMenuBar();
		setJMenuBar(menu);

		file = new JMenu("File");
		menu.add(file);

		mcalculate = new JMenu("Calculate");
		menu.add(mcalculate);

		view = new JMenu("View");
		menu.add(view);

		about = new JMenu("About");
		menu.add(about);

		save = new JMenuItem("Save as Image");
		save.addActionListener(this);
		file.add(save);

		history = new JMenuItem("History");
		history.addActionListener(this);
		file.add(history);

		iclose = new JMenuItem("Close");
		iclose.addActionListener(this);
		file.add(iclose);

		icalculate = new JMenuItem("Calculate");
		icalculate.addActionListener(this);
		mcalculate.add(icalculate);

		iday = new JMenuItem("Day Mode");
		iday.addActionListener(this);
		view.add(iday);

		inight = new JMenuItem("Night Mode");
		inight.addActionListener(this);
		view.add(inight);

		author = new JMenuItem("Author");
		author.addActionListener(this);
		about.add(author);

		tolcalc = new JMenuItem("Tolerance Calculation");
		tolcalc.addActionListener(this);
		about.add(tolcalc);
		

		//label design
		image = new ImageIcon("images/logo_supplier_main_day.png");
		imageNight = new ImageIcon("images/logo_supplier_main_night.png");
		logo = new JLabel();
		logo.setBounds(35, 30, 350, 130);
		add(logo);
		sizeAdjust = image.getImage().getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH);
		sizeAdjust2 = imageNight.getImage().getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH);
		logo.setIcon(new ImageIcon(sizeAdjust));

		title = new JLabel("Tolerance Calculation Tool");
		title.setBounds(450, 15, 700, 120);
		title.setFont(new Font("Work Sans", 1, 40)); //0: normal, 1: negrita, 2: cursiva, 3: negrita+cursiva, 18 -> pixel size
		title.setForeground(new Color(40, 40, 40));
		add(title);

		subtitle = new JLabel("Proyect name: " + proyectName);
		subtitle.setBounds(505, 80, 700, 80);
		subtitle.setFont(new Font("Work Sans", 1, 20));
		subtitle.setForeground(new Color(90, 90, 90));
		add(subtitle);

		targetName = new JLabel("Target dimension name: ");
		targetName.setBounds(505, 115, 400, 80);
		targetName.setFont(new Font("Work Sans", 1, 16));
		targetName.setForeground(new Color(90, 90, 90));
		add(targetName);

		changeMode = new JLabel("Change Mode: ");
		changeMode.setBounds(530, 10, 200, 20);
		add(changeMode);

		lname = new JLabel("Measure Name:");
		lname.setBounds(35, 200, 200, 20);
		lname.setFont(new Font("Work Sans", 3, 15));
		lname.setForeground(new Color(90, 90, 90));
		add(lname);

		ldirection = new JLabel("Direction:");
		ldirection.setBounds(300, 200, 100, 20);
		ldirection.setFont(new Font("Work Sans", 3, 15));
		ldirection.setForeground(new Color(90, 90, 90));
		add(ldirection);

		lnominal = new JLabel("Nominal Measure:");
		lnominal.setBounds(430, 200, 150, 20);
		lnominal.setFont(new Font("Work Sans", 3, 15));
		lnominal.setForeground(new Color(90, 90, 90));
		add(lnominal);

		lupper = new JLabel("Upper Tolerance:");
		lupper.setBounds(610, 187, 150, 20);
		lupper.setFont(new Font("Work Sans", 3, 15));
		lupper.setForeground(new Color(90, 90, 90));
		add(lupper);

		llower = new JLabel("Lower Tolerance:");
		llower.setBounds(790, 187, 150, 20);
		llower.setFont(new Font("Work Sans", 3, 15));
		llower.setForeground(new Color(90, 90, 90));
		add(llower);

		ltype = new JLabel("Type:");
		ltype.setBounds(970, 200, 200, 20);
		ltype.setFont(new Font("Work Sans", 3, 15));
		ltype.setForeground(new Color(90, 90, 90));
		add(ltype);

		ldistribution = new JLabel("Distribution:");
		ldistribution.setBounds(1150, 200, 200, 15);
		ldistribution.setFont(new Font("Work Sans", 3, 15));
		ldistribution.setForeground(new Color(90, 90, 90));
		add(ldistribution);

		mm1 = new JLabel("mm");
		mm1.setBounds(560, 235, 50, 20);
		mm1.setForeground(new Color(90, 90, 90));
		add(mm1);

		mm2 = new JLabel("mm");
		mm2.setBounds(740, 235, 50, 20);
		mm2.setForeground(new Color(90, 90, 90));
		add(mm2);

		mm3 = new JLabel("mm");
		mm3.setBounds(920, 235, 50, 20);
		mm3.setForeground(new Color(90, 90, 90));
		add(mm3);

		contributorList = new JLabel("Contributor List: ");
		contributorList.setBounds(610, 280, 150, 20);
		contributorList.setFont(new Font("Work Sans", 3, 15));
		contributorList.setForeground(new Color(90, 90, 90));
		add(contributorList);

		lwc = new JLabel("Worst-Case Analysis: ");
		lwc.setBounds(800, 633, 200, 20);
		lwc.setFont(new Font("Work Sans", 3, 15));
		lwc.setForeground(new Color(90, 90, 90));
		add(lwc);

		lstat = new JLabel("Statistical Analysis: ");
		lstat.setBounds(800, 673, 200, 20);
		lstat.setFont(new Font("Work Sans", 3, 15));
		lstat.setForeground(new Color(90, 90, 90));
		add(lstat);

		tolerancechain = new JLabel("Upload your Tolerance Chain here.", JLabel.CENTER);
		tolerancechain.setBounds(35, 310, 550, 390);
		tolerancechain.setFont(new Font("Work Sans", 3, 15));
		tolerancechain.setForeground(new Color(90, 90, 90));
		tolerancechain.setBorder(BorderFactory.createLineBorder(Color.GRAY)); //Codigo creado por ChatGPT
		tolerancechain.setOpaque(true);
		tolerancechain.setBackground(new Color(255, 255, 255));
		add(tolerancechain);

		lupload = new JLabel("Look for a file: ");
		lupload.setBounds(35, 280, 200, 20);
		lupload.setFont(new Font("Work Sans", 3, 15));
		lupload.setForeground(new Color(90, 90, 90));
		add(lupload);
		
		luser = new JLabel("Welcome Back " + name);
		luser.setBounds(1120, 10, 200, 20);
		add(luser);

		warningName = new JLabel("");
		warningName.setBounds(35, 260, 200, 15);
		warningName.setFont(new Font("Work Sans", 3, 10));
		warningName.setForeground(new Color(255, 0, 0));
		add(warningName);

		warningNom = new JLabel("");
		warningNom.setBounds(430, 260, 300, 15);
		warningNom.setFont(new Font("Work Sans", 3, 10));
		warningNom.setForeground(new Color(255, 0, 0));
		add(warningNom);

		warningUp = new JLabel("");
		warningUp.setBounds(610, 260, 300, 15);
		warningUp.setFont(new Font("Work Sans", 3, 10));
		warningUp.setForeground(new Color(255, 0, 0));
		add(warningUp);

		warningLow = new JLabel("");
		warningLow.setBounds(790, 260, 300, 15);
		warningLow.setFont(new Font("Work Sans", 3, 10));
		warningLow.setForeground(new Color(255, 0, 0));
		add(warningLow);

		ltotalDistribution = new JLabel("Distribution for Target Dimension:");
		ltotalDistribution.setBounds(885, 575, 300, 25); 
		ltotalDistribution.setFont(new Font("Work Sans", 3, 15));
		ltotalDistribution.setForeground(new Color(90, 90, 90));
		add(ltotalDistribution);


		//text field design: tname, tnominal, tupper, tlower
		tname = new JTextField();
		tname.setBounds(35, 230, 230, 25);
		add(tname);
		
		tnominal = new JTextField();
		tnominal.setBounds(430, 230, 125, 25);
		add(tnominal);

		tupper = new JTextField();
		tupper.setBounds(610, 230, 125, 25); 
		add(tupper);

		tlower = new JTextField();
		tlower.setBounds(790, 230, 125, 25);
		add(tlower);

		target = new JTextField();
		target.setBounds(710, 145, 242, 25);
		add(target);


		//combo box design: cdirection, ctype, cdistribution
		cdirection = new JComboBox();
		cdirection.setBounds(300, 230, 100, 25);
		add(cdirection);
		cdirection.addItem("+");
		cdirection.addItem("-");

		ImageIcon dim = new ImageIcon("images/dim.png");
		ImageIcon ang = new ImageIcon("images/symbol-angularity.jpg");
		ImageIcon circ = new ImageIcon("images/symbol-circularity.jpg");
		ImageIcon conc = new ImageIcon("images/symbol-concentricity.jpg");
		ImageIcon cyl = new ImageIcon("images/symbol-cylindricity.jpg");
		ImageIcon flat = new ImageIcon("images/symbol-flatness.jpg");
		ImageIcon para = new ImageIcon("images/symbol-paralellism.jpg");
		ImageIcon perp = new ImageIcon("images/symbol-perpendicularity.jpg");
		ImageIcon pos = new ImageIcon("images/symbol-position.jpg");
		ImageIcon prof1 = new ImageIcon("images/symbol-profileLine.jpg");
		ImageIcon prof2 = new ImageIcon("images/symbol-profileSurface.jpg");
		ImageIcon run1 = new ImageIcon("images/symbol-runout.jpg");
		ImageIcon str = new ImageIcon("images/symbol-straightness.jpg");
		ImageIcon sym = new ImageIcon("images/symbol-symmetry.jpg");
		ImageIcon run2 = new ImageIcon("images/symbol-totalRunout.jpg");
		ctype = new JComboBox();
		ctype.setBounds(970, 230, 150, 25);
		add(ctype);

		// Añadir "Dimensional" como solo texto
		ctype.addItem("Dimensional");
		// Añadir símbolos como JLabel con imagen + texto
		ctype.addItem(new JLabel("Angularity", ang, JLabel.LEFT));
		ctype.addItem(new JLabel("Circularity", circ, JLabel.LEFT));
		ctype.addItem(new JLabel("Concentricity", conc, JLabel.LEFT));
		ctype.addItem(new JLabel("Cylindricity", cyl, JLabel.LEFT));
		ctype.addItem(new JLabel("Flatness", flat, JLabel.LEFT));
		ctype.addItem(new JLabel("Parallelism", para, JLabel.LEFT));
		ctype.addItem(new JLabel("Perpendicularity", perp, JLabel.LEFT));
		ctype.addItem(new JLabel("Position", pos, JLabel.LEFT));
		ctype.addItem(new JLabel("Profile of a Line", prof1, JLabel.LEFT));
		ctype.addItem(new JLabel("Profile of a Surface", prof2, JLabel.LEFT));
		ctype.addItem(new JLabel("Runout", run1, JLabel.LEFT));
		ctype.addItem(new JLabel("Total Runout", run2, JLabel.LEFT));
		ctype.addItem(new JLabel("Straightness", str, JLabel.LEFT));
		ctype.addItem(new JLabel("Symmetry", sym, JLabel.LEFT));

		//Customized renderer (creado por ChatGPT, modificado y simplificado por mi)
		ctype.setRenderer(new DefaultListCellRenderer() {
    			public Component getListCellRendererComponent(
        			JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        			if (value instanceof JLabel) {
            				JLabel item = (JLabel) value;
            				label.setIcon(item.getIcon());
            				label.setText(item.getText());
        			} else {
            				label.setText(value.toString());
        			}

        			return label;
    			}
		});

		cdistribution = new JComboBox();
		cdistribution.setBounds(1150, 230, 130, 25);
		add(cdistribution);
		cdistribution.addItem("RV - Rectangular");
		cdistribution.addItem("DV - Triangular");
		cdistribution.addItem("TV1 - Trapezoidal 1");
		cdistribution.addItem("TV2 - Trapezoidal 2");
		cdistribution.addItem("TV3 - Trapezoidal 3");
		cdistribution.addItem("NV3S - 3σ");
		cdistribution.addItem("NV4S - 4σ");
		cdistribution.addItem("NV6S - 6σ");

		
		ctotalDistribution = new JComboBox();
		ctotalDistribution.setBounds(1150, 575, 130, 25); 
		add(ctotalDistribution);
		ctotalDistribution.addItem("RV - Rectangular");
		ctotalDistribution.addItem("DV - Triangular");
		ctotalDistribution.addItem("TV1 - Trapezoidal 1");
		ctotalDistribution.addItem("TV2 - Trapezoidal 2");
		ctotalDistribution.addItem("TV3 - Trapezoidal 3");
		ctotalDistribution.addItem("NV3S - 3σ");
		ctotalDistribution.addItem("NV4S - 4σ");
		ctotalDistribution.addItem("NV6S - 6σ");
		ctotalDistribution.setSelectedIndex(6);

		//radio button design: day, night & ButtonGroup bg
		bg = new ButtonGroup();

		day = new JRadioButton("Day Mode");
		day.setBounds(630, 10, 100, 20);
		day.setOpaque(false);
		day.addActionListener(this);
		add(day);
		bg.add(day);

		night = new JRadioButton("Night Mode");
		night.setBounds(730, 10, 100, 20);
		night.setOpaque(false);
		night.addActionListener(this);
		add(night);
		bg.add(night);

			if(mode.equals("day")){
				day.setSelected(true);
			}else{
				night.setSelected(true);
			}

		//Text Area design: list, wc, stat + Scrollpane sc, sc2,sc3
		list = new JTextArea("\n");
		list.setEditable(false);
		sc = new JScrollPane(list);
		sc.setBounds(610, 310, 670, 255);
		add(sc);
		
		wc = new JTextArea();
		wc.setEditable(false);
		sc2 = new JScrollPane(wc);
		sc2.setBounds(970, 630, 310, 30);
		add(sc2);

		stat = new JTextArea();
		stat.setEditable(false);
		sc3 = new JScrollPane(stat);
		sc3.setBounds(970, 670, 310, 30);
		add(sc3);


		//Check Box design: confirmation, negativeUp, negativeLow 
		confirmation = new JCheckBox("I am finished entering data.");
		confirmation.setBounds(610, 570, 200, 15);
		confirmation.setFont(new Font("Work Sans", 1, 12));
		confirmation.setForeground(new Color(90, 90, 90));
		confirmation.setOpaque(false);
		confirmation.addChangeListener(this);
		add(confirmation);

		negativeUp = new JCheckBox("Negative");
		negativeUp.setBounds(610, 213, 150, 15);
		negativeUp.setFont(new Font("Work Sans", 1, 12));
		negativeUp.setForeground(new Color(90, 90, 90));
		negativeUp.setOpaque(false);
		add(negativeUp);

		negativeLow = new JCheckBox("Negative");
		negativeLow.setBounds(790, 213, 150, 15);
		negativeLow.setFont(new Font("Work Sans", 1, 12));
		negativeLow.setForeground(new Color(90, 90, 90));
		negativeLow.setSelected(true); //positive upper and negative lower by defect.
		negativeLow.setOpaque(false);
		add(negativeLow);

		//Button design: remove, removeimage, add, calculate, empty, close, upload
		upload = new JButton("Upload");
		upload.setBounds(485, 280, 100, 20);
		upload.addActionListener(this);
		add(upload);

		removeimage = new JButton("Remove");
		removeimage.setBounds(375, 280, 100, 20); 
		removeimage.addActionListener(this);
		add(removeimage);

		remove = new JButton("Remove Previous");
		remove.setBounds(1020, 280, 150, 20);
		remove.addActionListener(this);
		add(remove);

		add = new JButton("Add");
		add.setBounds(1180, 280, 100, 20);
		add.addActionListener(this);
		add(add);

		calculate = new JButton("Calculate");
		calculate.setBounds(610, 630, 150, 30);
		calculate.setEnabled(false);
		calculate.addActionListener(this);
		add(calculate);

		empty = new JButton("Empty");
		empty.setBounds(910, 280, 100, 20);
		empty.addActionListener(this);
		add(empty);

		close = new JButton("Close");
		close.setBounds(610, 670, 150, 30);
		close.addActionListener(this);
		add(close);

		//Color setup- day mode
		Color daybackground = getContentPane().getBackground();

		Color daytitle = title.getForeground();
		Color daysubtitle = subtitle.getForeground();
		Color daychange = changeMode.getForeground();
		Color daytolerancechain = tolerancechain.getBackground();
		Color daytolerancechainformat = tolerancechain.getForeground();
		Color daylabel = close.getForeground();

		Color daybutton = close.getBackground();
		Color daybuttonformat = close.getForeground();

		Color daytextf = tname.getBackground();
		Color daytextfformat = tname.getForeground();

		Color daytexta = list.getBackground();
		Color daytextaformat = list.getForeground();

		Color daycombo = cdirection.getBackground();
		Color daycomboformat = cdirection.getForeground();

		Color dayradio = day.getBackground();
		Color dayradioformat = day.getForeground();

		Color daymenubar = menu.getBackground();
		Color daymenu = file.getBackground();
		Color daymenuformat = file.getForeground();
		Color dayitem = save.getBackground();
		Color dayitemformat = save.getForeground();

		Color daycheck = confirmation.getBackground();
		Color daycheckformat = confirmation.getForeground();


	}
	@Override
	public void stateChanged(ChangeEvent e){
		//event CheckBox confirmation
		if(confirmation.isSelected() == true){
			if(night.isSelected()){
				calculate.setForeground(new Color(255, 255, 255));
				calculate.setBackground(new Color(41, 48, 56));
				calculate.setEnabled(true);
			}
			calculate.setEnabled(true);

		} else if(confirmation.isSelected() == false){
			if(night.isSelected()){
				calculate.setForeground(new Color(255, 255, 255));
				calculate.setBackground(new Color(33, 35, 37));
				calculate.setEnabled(false);

			}
			calculate.setEnabled(false);
		}

		//event night mode: bring mode from previous interface (First)
		if(night.isSelected()){
			JLabel[] labels = {
				 logo, title, subtitle, targetName, changeMode, lname, ldirection, 
				 lnominal, lupper, llower, ltype, ldistribution, 
				 mm1, mm2, mm3, contributorList, lwc, lstat, 
				 lupload, luser, ltotalDistribution
			};

			JButton[] buttons = {remove, removeimage, add, calculate, empty, close, upload};
			JTextField[] textfields = {tname, tnominal, tupper, tlower, target};
			JTextArea[] textareas = {list, wc, stat};
			JComboBox[] combos = {cdirection, ctype, cdistribution, ctotalDistribution};
			JRadioButton[] radios = {day, night};
			JMenuItem[] items = {save, iclose, history, icalculate, iday, inight, author, tolcalc};

			NightMode(labels, buttons, textfields, textareas, combos, radios, items, tolerancechain, menu, file, mcalculate, view, about, confirmation, negativeUp, negativeLow);	
			
			//Debug button calculate
			if(confirmation.isSelected()){
				calculate.setForeground(new Color(255, 255, 255));
				calculate.setBackground(new Color(41, 48, 56));
				calculate.setEnabled(true);				

			}else{
				calculate.setForeground(new Color(255, 255, 255));
				calculate.setBackground(new Color(33, 35, 37));
				calculate.setEnabled(false);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		//event for button upload
		if(e.getSource() == upload){
			//file chooser design: filechooser
			JFileChooser filechooser = new JFileChooser();
			FileNameExtensionFilter imagesonly = new FileNameExtensionFilter("jpg", "png", "jpeg"); //only images allowed
			filechooser.setFileFilter(imagesonly);
			filechooser.setDialogTitle("Open Tolerance Chain");
			int result = filechooser.showOpenDialog(null);

			if(result == filechooser.APPROVE_OPTION){
				File fileToGet = new File(filechooser.getSelectedFile().getAbsolutePath()); //collect file and its path
				ImageIcon image = new ImageIcon(fileToGet.getAbsolutePath()); //Collect the file in an image using URL
				Image sizeOfImage = image.getImage().getScaledInstance(tolerancechain.getWidth(), tolerancechain.getHeight(), Image.SCALE_SMOOTH); //ChatGPT:Resize image with an Image Object
				tolerancechain.setIcon(new ImageIcon(sizeOfImage)); //set the image with new size in a new ImageIcon
				tolerancechain.setText(""); //Remove Text

			}
		
		//event button removeimage
		}else if(e.getSource() == removeimage){
			tolerancechain.setIcon(null);
			tolerancechain.setText("Upload your Tolerance Chain here.");

		//event menu item Save file
		} else if(e.getSource() == save){
			if(!wc.getText().equals("") && !stat.getText().equals("")){
				JFileChooser filechooser = new JFileChooser();
				filechooser.setDialogTitle("Save Report as an Image");
				int result = filechooser.showSaveDialog(null);

				if(result == filechooser.APPROVE_OPTION){
					try{
						File fileToSave = new File(filechooser.getSelectedFile().getAbsolutePath());
						fileToSave = new File(fileToSave.getAbsolutePath() + ".png"); //save it as .png ALWAYS
				
						//Capture window and paste it in a Buffered Image (ChatGPT):
						//Buffered image: blank image in memory, graphics2D = paint on Buffered image, ImageIO = buffered image to actual file
						BufferedImage screenshot = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB); //type: standard, without transparency

						Graphics2D g2d = screenshot.createGraphics();
						this.paint(g2d); //take content on JFramne to be painted on interface Buffered Image
						g2d.dispose(); 

						ImageIO.write(screenshot, "png", fileToSave);//Take buffered image and paste it in file.jpg we just created
						JOptionPane.showMessageDialog(this, "File saved as an image successfully!");

					}catch(Exception ex){
						//Java forces to consider exception in case of error when ImageIO is saved.
						JOptionPane.showMessageDialog(this, "Error in saving due to: " + ex.getMessage());

						/*
						* Possible exceptions due to:
						* The disk is full
						* You don't have write permissions
						* Invalid path, etc.
						*/
					}
				}
			
			} else{
				JOptionPane.showMessageDialog(this, "Please complete the analysis before saving the report.");

			}

		//event night mode
		}else if(e.getSource() == inight || e.getSource() == night){
			JLabel[] labels = {
				 logo, title, subtitle, targetName, changeMode, lname, ldirection, 
				 lnominal, lupper, llower, ltype, ldistribution, 
				 mm1, mm2, mm3, contributorList, lwc, lstat, 
				 lupload, luser, ltotalDistribution
			};

			JButton[] buttons = {remove, removeimage, add, calculate, empty, close, upload};
			JTextField[] textfields = {tname, tnominal, tupper, tlower, target};
			JTextArea[] textareas = {list, wc, stat};
			JComboBox[] combos = {cdirection, ctype, cdistribution, ctotalDistribution};
			JRadioButton[] radios = {day, night};
			JMenuItem[] items = {save, iclose, history, icalculate, iday, inight, author, tolcalc};

			night.setSelected(true);
			confirmation.setSelected(false);

			NightMode(labels, buttons, textfields, textareas, combos, radios, items, tolerancechain, menu, file, mcalculate, view, about, confirmation, negativeUp, negativeLow);

		//event day mode
		}else if(e.getSource() == iday || e.getSource() == day){
			//Arrays
			JLabel[] labels = {
				 logo, lname, ldirection, lnominal, lupper, llower, ltype,
				 ldistribution, mm1, mm2, mm3, contributorList, lwc,
				 lstat, lupload, luser, ltotalDistribution
			};

			JButton[] buttons = {remove, removeimage, add, calculate, empty, close, upload};
			JTextField[] textfields = {tname, tnominal, tupper, tlower, target};
			JTextArea[] textareas = {list, wc, stat};
			JComboBox[] combos = {cdirection, ctype, cdistribution, ctotalDistribution};
			JRadioButton[] radios = {day, night};
			JMenuItem[] items = {save, iclose, icalculate, history, iday, inight, author, tolcalc};

			//Colors to be restored
			Color[] colors = {
   				daybackground, daytitle, daysubtitle, daychange,
    				daytolerancechain, daytolerancechainformat, daylabel,
    				daybutton, daybuttonformat, daytextf, daytextfformat,
    				daytexta, daytextaformat, daycombo, daycomboformat,
   				dayradio, dayradioformat, daymenubar, daymenu, daymenuformat,
    				dayitem, dayitemformat, daycheck, daycheckformat
			};

			day.setSelected(true);
			confirmation.setSelected(false);

			DayMode(colors, labels, buttons, textfields, textareas, combos, radios, items, tolerancechain, menu, file, mcalculate, view, about, confirmation, negativeUp, negativeLow);

		//event button close & menuItem iclose
		}else if(e.getSource() == close || e.getSource() == iclose){
			System.exit(0);

		//event button empty
		}else if(e.getSource() == empty){
			target.setText("");
			list.setText("\n");
			wc.setText(" ");
			stat.setText(" ");
			confirmation.setSelected(false);
			nameCheck = new ArrayList<>();
			directions = new ArrayList<>();
			targetDim = new ArrayList<>();
			upperTol = new ArrayList<>();
			lowerTol = new ArrayList<>();
			sigmas = new ArrayList<>();
			counter = 1;

		//event button remove
		}else if(e.getSource() == remove){

			//The event will be run if no exception is detected.
			try{
				nameCheck.remove(nameCheck.size() - 1);
				directions.remove(directions.size() - 1);
				targetDim.remove(targetDim.size() - 1);
				upperTol.remove(upperTol.size() - 1);
				lowerTol.remove(lowerTol.size() - 1);
				sigmas.remove(sigmas.size() - 1);
				previousText.remove(previousText.size() - 1);
				try{
					textBeforeAdd = previousText.get(previousText.size() - 1);

				}catch (Exception ex){
					textBeforeAdd = "";

				}
				counters.remove(counters.size() - 1);
				list.setText(textBeforeAdd);
				counter = counters.get(counters.size() - 1) + 1; //start next number

			//for ex = IndexOutOfBoundsException
			}catch (Exception ex){
				JOptionPane.showMessageDialog(this, "The list is empty.");
				list.setText("\n");
				wc.setText(" ");
				stat.setText(" ");
				confirmation.setSelected(false);
				counter = 1;

			}

		//event button add
		}else if(e.getSource() == add){		

			/*Get text from:
			* JLabel warningName, warningNom, warningUp, warnigLow
			* JTextField tname, tnominal, tupper, tlower, 
			* JComboBox cdirection, ctype, cdistribution
			*/

			warningName.setText("");
			warningNom.setText("");
			warningUp.setText("");
			warningLow.setText("");

			//int counter defined after class.
			
			String number = String.valueOf(counter);
			
			//check if Itemselected in combobox ctype is a label -> help with ChatGPT
			Object selectedItem = ctype.getSelectedItem();
			String ctypetext = "";
				if(selectedItem instanceof JLabel){ //replicate same method as personalized renderer
					ctypetext = "Geometrical (" + ((JLabel) selectedItem).getText() + ")"; //treat selectedItem as JLabel

				}else{
					ctypetext = ctype.getSelectedItem().toString();

				}

			//check if tolerance is symmetric or not
			String uppertol = tupper.getText().trim();
			String lowertol = tlower.getText().trim();
			String toltext = "";

				if(uppertol.equals(lowertol)){
					toltext = " ± " + uppertol;

				}else{
					if(negativeUp.isSelected() && negativeLow.isSelected()){
						toltext = " - " + uppertol + ", - " + lowertol;

					}else if(!negativeUp.isSelected() && negativeLow.isSelected()){
						toltext = " + " + uppertol + ", - " + lowertol;						

					}else{
						toltext = " + " + uppertol + ", + " + lowertol;						

					}
				}	
			
			String t =
				"  " + number + ". " + 				
				tname.getText().trim() + ": " +
				cdirection.getSelectedItem().toString().trim() + " " +
				tnominal.getText().trim() + "mm" + 
				toltext + " | " +
				"Type of tolerance: " + ctypetext.trim() + " | " +
				"Distribution: " + cdistribution.getSelectedItem().toString().trim();

			//Is it empty?
			if(!tname.getText().trim().equals("") && !tnominal.getText().trim().equals("") && !tupper.getText().trim().equals("") && !tlower.getText().trim().equals("")){
				boolean checkNom = false;
				boolean checkUp = false;
				boolean checkLow = false;
				String nomtoFloat = tnominal.getText();
				String upToFloat = tupper.getText();
				String lowToFloat = tlower.getText();
				float nominal = 0;
				float upper = 0;
				float lower = 0;

				try{
					nominal = Float.parseFloat(nomtoFloat);
					checkNom = true;

				}catch (Exception ex){
					checkNom = false;
					warningNom.setText("Please introduce a valid number.");
				}

				try{
					upper = Float.parseFloat(upToFloat);
					checkUp = true;

				}catch (Exception ex){
					checkUp = false;
					warningUp.setText("Please introduce a valid number.");
				}

				try{
					lower = Float.parseFloat(lowToFloat);
					checkLow = true;

				}catch (Exception ex){
					checkLow = false;
					warningLow.setText("Please introduce a valid number.");
				}
				
				//Is the nominal value, upper and lower tolerances a float?
				if(checkNom == true && checkUp == true && checkLow == true){

					//Are the nominal or both contributors negative
					if(!(nominal < 0) && !(upper < 0) && !(lower < 0)){
						//Are both lower and upper tolerances = 0?
						if(upper == 0 && lower == 0){
							warningUp.setText("Cannot be both 0.");
							warningLow.setText("Cannot be both 0.");
							
						//GD&T control
						}else if((nominal != 0 || (upper == 0 && lower == 0) || !uppertol.equals(lowertol)) && !ctypetext.equals("Dimensional")){

							if(nominal != 0){
								warningNom.setText("For Geometric, nominal must be 0.");
							}

							if(upper == 0 && lower == 0){
								warningUp.setText("Cannot be both 0.");
								warningLow.setText("Cannot be both 0.");
							}

							if(!uppertol.equals(lowertol)){
								warningNom.setText("For Geometric, tolerances must be symmetric.");								
							}

							if(nominal != 0 && !uppertol.equals(lowertol)){
								warningNom.setText("For Geometric, nominal must be 0 and tolerances symmetric.");								
							}

						}else{
							//Check if both are positive or negative and lower or upper is greater than upper or lower
							//Control at: negativeUp, negativeLow
							if((negativeUp.isSelected() && negativeLow.isSelected()) && (upper >= lower)){
								if(upper == lower){
									warningUp.setText("Cannot be equal than lower.");
									warningLow.setText("Cannot be equal than upper.");

								}else{
									warningUp.setText("Upper cannot be larger than lower in this case.");

								}
								
							}else{
								if((!negativeUp.isSelected() && !negativeLow.isSelected()) && (lower >= upper)){
									if(upper == lower){
										warningUp.setText("Cannot be equal than lower.");
										warningLow.setText("Cannot be equal than upper.");

									}else{
										warningLow.setText("Lower cannot be larger than upper in this case.");

									}
										

								}else if(negativeUp.isSelected() && !negativeLow.isSelected()){
									warningUp.setText("Upper cannot be negative while lower positive.");

								}else{
									//Is there any repeated name on the contributor list?
									String currentName = tname.getText().trim();
									boolean repeated = false;
									for(String name : nameCheck){
										if(name.equalsIgnoreCase(currentName)){
											repeated = true;
											break;
										}	
									}

									//Are the tolerances larger than nominal value?
									if(((nominal < upper || nominal < lower) && ctypetext.equals("Dimensional"))){
										int choice = JOptionPane.showConfirmDialog(
        										this, 
        										"One or more tolerances are greater than the nominal value. Do you want to proceed anyway?",
        										"Warning",
       											JOptionPane.YES_NO_OPTION,
        										JOptionPane.WARNING_MESSAGE
    										);

										if(choice == JOptionPane.NO_OPTION){
											return;
										}	
									}

									if(repeated){
									//works the same way as JFileChooser. Help from ChatGPT
										int choice = JOptionPane.showConfirmDialog(
        										this, 
        										"Two contributors have the same name. Do you want to proceed anyway?",
        										"Warning",
       											JOptionPane.YES_NO_OPTION,
        										JOptionPane.WARNING_MESSAGE
    										);

										if(choice == JOptionPane.NO_OPTION){
											return;
										}					
									}
									nameCheck.add(currentName); //if user accepts currentName is added to ArrayList
									System.out.println("upper before = " + upper);
									System.out.println("lower before = " + lower);
							
									//Check if tolerances are symmetric or not and do mean if necessary
									if(upper == lower){

										//Check direction of the dimensional value
										if(cdirection.getSelectedItem().toString().trim().equals("-")){
											nominal = -(nominal);
											targetDim.add(nominal); //dimensional value is added to array for dimensionals- negative

										}else{
											targetDim.add(nominal); //dimensional value is added to array for dimensionals- positive	
							
										}

									}else{
										if(negativeUp.isSelected() && negativeLow.isSelected()){
											nominal = nominal - ((upper + lower) /2); //mean to get new nominal
											upper = (lower - upper) / 2; //upper and lower become symmetric tolerances
											lower = upper;
										
										}else if(!negativeUp.isSelected() && !negativeLow.isSelected()){
											nominal = nominal + ((upper + lower) /2); //mean to get new nominal
											upper = (upper - lower) / 2; //upper and lower become symmetric tolerances
											lower = upper;	
									
										}else{
											nominal = nominal + ((upper - lower) /2); //mean to get new nominal
											upper = (upper + lower) / 2; //upper and lower become symmetric tolerances
											lower = upper;
										}

										if(cdirection.getSelectedItem().toString().trim().equals("-")){
											nominal = -(nominal);
											targetDim.add(nominal); //dimensional value is added to array for dimensionals- negative

										}else{
											targetDim.add(nominal); //dimensional value is added to array for dimensionals- positive	
							
										}		
									}

									System.out.println("nominal = " + nominal);
									System.out.println("upper = " + upper);
									System.out.println("lower = " + lower);

									//Maximun and minimun value of each contributor (Worst-Case)
									float max = 0;
									float min = 0;
	
									max = nominal + upper;
									min = nominal - lower;
									
									System.out.println("max = " + max);
									System.out.println("min = " + min);
									
									upperTol.add(max);
									lowerTol.add(min);
									

									//Total Tolerance range and distribution (Statistical) 
									float sigma = 0, totalTol = 0;
									totalTol = upper + lower;
									System.out.println("Ti = " + totalTol);

									totalTol = totalTol * totalTol;
									System.out.println("Ti^2 = " + totalTol);

									if(cdistribution.getSelectedItem().toString().trim().equals("RV - Rectangular")){
										sigma = totalTol / 12;
										sigmas.add(sigma);
					
									}else if(cdistribution.getSelectedItem().toString().trim().equals("DV - Triangular")){
										sigma = totalTol / 24;
										sigmas.add(sigma);

									}else if(cdistribution.getSelectedItem().toString().trim().equals("TV1 - Trapezoidal 1")){
										sigma = (10 * totalTol) / 192;
										sigmas.add(sigma);

									}else if(cdistribution.getSelectedItem().toString().trim().equals("TV2 - Trapezoidal 2")){
										sigma = (5 * totalTol) / 108;
										sigmas.add(sigma);

									}else if(cdistribution.getSelectedItem().toString().trim().equals("TV3 - Trapezoidal 3")){
										sigma = (13 * totalTol) / 300;
										sigmas.add(sigma);								

									}else if(cdistribution.getSelectedItem().toString().trim().equals("NV3S - 3σ")){
										sigma = totalTol / 36;
										sigmas.add(sigma);

									}else if(cdistribution.getSelectedItem().toString().trim().equals("NV4S - 4σ")){
										sigma = totalTol / 64;
										sigmas.add(sigma);

									}else if(cdistribution.getSelectedItem().toString().trim().equals("NV6S - 6σ")){
										sigma = totalTol / 144;
										sigmas.add(sigma);

									}
									System.out.println("");

									//Add contributors to contributor List
									String textOnTA = list.getText();
									textOnTA += t + "\n";			
									previousText.add(textOnTA); //for event button remove
									directions.add(cdirection.getSelectedItem().toString());
									counters.add(counter);
									list.setText(textOnTA);
									counter++;
									tname.setText("");
									tnominal.setText("");
									tupper.setText("");
									tlower.setText("");
								
								}
							}
						}

					}else{
						if(nominal < 0){
							warningNom.setText("Cannot be negative.");
						}

						if(upper < 0){
							warningUp.setText("Cannot be negative.");
						}

						if(lower < 0){
							warningLow.setText("Cannot be negative.");
						}
					}
				}

			}else{
				if(tname.getText().trim().equals("")){
					warningName.setText("Please introduce a name.");
				}

				if(tnominal.getText().trim().equals("")){
					warningNom.setText("Please introduce a number.");
				}

				if(tupper.getText().trim().equals("")){
					warningUp.setText("Please introduce a number.");
				}

				if(tlower.getText().trim().equals("")){
					warningLow.setText("Please introduce a number.");
				}
			}

		//event button calculate
		} else if(e.getSource() == calculate || e.getSource() == icalculate){
			//Check if list is empty before calculating
			if(list.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "There are not enough contributors to perform an analysis.");
				confirmation.setSelected(false);

			//Check if user has given a name to target dimension before calculating
			} else if(target.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "Please name your target dimension.");
				confirmation.setSelected(false);

			//Check if all contributors have the same direction
			} else{
				List<Integer> binarychain = new ArrayList<>();
				int binary = 0;
				int sum = 0;

				for(String direction : directions){
					if(direction.equals("+")){
						binary = 1;
						binarychain.add(binary);

					}else{
						binary = 0;
						binarychain.add(binary);
					}
				}

				for(int i : binarychain){
					sum += i;
				}
				System.out.println("");
				System.out.println(directions);
				System.out.println(binarychain);
				System.out.println("binary chain sum: " + sum);

				if(sum == binarychain.size() || sum == 0){
					int choice = JOptionPane.showConfirmDialog(
   						this,   
  						"All contributos contribute in the same direction.\nDo you want to review your chain before calculating?",              
   						"Warning",                
    						JOptionPane.YES_NO_OPTION,            
    						JOptionPane.WARNING_MESSAGE             
					);

					if(choice == JOptionPane.YES_OPTION){
						return;
					}

				}

				//If Total distribution is not standard -> throw pop up to confirm
				if(!ctotalDistribution.getSelectedItem().toString().trim().equals("NV4S - 4σ")){
					int choice = JOptionPane.showConfirmDialog(
        					this, 
        					"Standard distribution for Target Dimension is NV4S - 4σ.\n Do you want to proceed anyway with current distribution? ",
        					"Warning",
       						JOptionPane.YES_NO_OPTION,
        					JOptionPane.WARNING_MESSAGE
    					);

					if(choice == JOptionPane.NO_OPTION){
						return;
					}	
				}

			
				//Worst-case analysis
					float targetDimension = 0, upperTolerance = 0, lowerTolerance = 0;

					//Target Dimension calculation
					for(float value : targetDim){
						targetDimension += value;

					}

					//Uper Tolerance calculation
					for(float value : upperTol){
						upperTolerance += value;

					}

					//Lower Tolerance calculation
					for(float value : lowerTol){
						lowerTolerance += value;

					}

					float posTolerance = upperTolerance - targetDimension;
					float negTolerance = targetDimension - lowerTolerance;

					String resultNom = String.format("%.2f", targetDimension);
					String resultUpper = String.format("%.2f", posTolerance);
					String resultLower = String.format("%.2f", negTolerance);
					String resultMax = String.format("%.2f", upperTolerance);
					String resultMin = String.format("%.2f", lowerTolerance);
				
					if(resultUpper.equals(resultLower)){
						wc.setText(
							" " + resultNom + 
							" ± " + resultUpper +
							"  [" + resultMax +
							" ; " + resultMin + "] "
						);
			
					}else{
						wc.setText(
							" " + resultNom + 
							" + " + resultUpper +
							" ; - " + resultLower +
							"  [" + resultMax +
							" ; " + resultMin + "] "
						);
					}

				//Statistical analysis
					double toleranceRange = 0;
				
					//Sum of sigmas calculation
					for(float value : sigmas){
						toleranceRange += value;

					}
	
					//Standard deviation
					toleranceRange = Math.sqrt(toleranceRange);
					System.out.println("");
					System.out.println(sigmas);
					System.out.println(toleranceRange);
				
					//Tolerance range calculation
						if(ctotalDistribution.getSelectedItem().toString().trim().equals("RV - Rectangular")){
							toleranceRange = (Math.pow(3, 0.5)) * toleranceRange;
					
						}else if(ctotalDistribution.getSelectedItem().toString().trim().equals("DV - Triangular")){
							toleranceRange = (Math.pow(6, 0.5)) * toleranceRange;

						}else if(ctotalDistribution.getSelectedItem().toString().trim().equals("TV1 - Trapezoidal 1")){
							toleranceRange = (Math.pow((48/10), 0.5)) * toleranceRange;

						}else if(ctotalDistribution.getSelectedItem().toString().trim().equals("TV2 - Trapezoidal 2")){
							toleranceRange = (Math.pow((27/5), 0.5)) * toleranceRange;

						}else if(ctotalDistribution.getSelectedItem().toString().trim().equals("TV3 - Trapezoidal 3")){
							toleranceRange = (Math.pow((75/13), 0.5)) * toleranceRange;		

						}else if(ctotalDistribution.getSelectedItem().toString().trim().equals("NV3S - 3σ")){
							toleranceRange = 3 * toleranceRange;

						}else if(ctotalDistribution.getSelectedItem().toString().trim().equals("NV4S - 4σ")){
							toleranceRange = 4 * toleranceRange;

						}else if(ctotalDistribution.getSelectedItem().toString().trim().equals("NV6S - 6σ")){
							toleranceRange = 6 * toleranceRange;

						}
				
					//Convert float to double
					double statTargetDimension = targetDimension;
					double statPosTolerance = statTargetDimension + toleranceRange;
					double statNegTolerance = statTargetDimension - toleranceRange;
						
					String resultStatRange = String.format("%.3f", toleranceRange);
					String resultStatMax = String.format("%.3f", statPosTolerance);
					String resultStatMin = String.format("%.3f", statNegTolerance);

					stat.setText(
						" " + resultNom + 
						" ± " + resultStatRange +
						"  [" + resultStatMax +
						" ; " + resultStatMin + "] "
					);

					//Check if statistical > worst-case (should not be)
					if(toleranceRange > posTolerance){
						JOptionPane.showMessageDialog(
							this, 
							"The statistical range is bigger than the worst-case range.\n"
						 	+ "You might have to review your analysis.\n" 
						 	+ "TIP: Check distributions."
						);
					}

					thistory = 
						" " + target.getText().trim().toUpperCase() + "\n" +
						" Contributors: \n" + list.getText() + "\n" +
						" Distribution on target: " + ctotalDistribution.getSelectedItem().toString() + "\n" +
						" " + "Worst-case analysis: " + wc.getText() + "\n" +
						" " + "Statistical analysis: " + wc.getText() + "\n" +
						"\n ____________________________________________________________________________\n" + "\n";

					textOnHistory += thistory;
			}

		//event item history
		} else if(e.getSource() == history){
			if(day.isSelected()){
				mode = "day";

			}else{
				mode = "night";	
			
			}

			History h = new History(this); //needs to be opened in this class
			h.setVisible(true);

		//event item tolcalc
		} else if(e.getSource() == tolcalc){
			if(day.isSelected()){
				mode = "day";

			}else{
				mode = "night";	
			
			}

			Calculation c = new Calculation(this); //needs to be opened in this class
			c.setVisible(true);	

		} else if(e.getSource() == author){
			JOptionPane.showMessageDialog(this, "Author: Gonzalo Lopez \nJuly 2025 \nV 1.0.0 \n");
		
		}
	}

	public void NightMode(
		JLabel[] labels,
 		JButton[] buttons, 
		JTextField[] textfields, 
		JTextArea[] textareas, 
		JComboBox[] combos, 
		JRadioButton[] radios, 
		JMenuItem[] items, 
		JLabel tolerancechain,	
		JMenuBar menu, 	
		JMenu file, 
		JMenu mcalculate, 
		JMenu view, 
		JMenu about, 
		JCheckBox confirmation,
		JCheckBox negativeUp, 
		JCheckBox negativeLow
	){
		//Como no es static -> this funciona
		getContentPane().setBackground(new Color(33, 35, 37));

		logo.setIcon(new ImageIcon(sizeAdjust2));

		//checkbox
		confirmation.setForeground(new Color(200, 200, 200));
		negativeUp.setForeground(new Color(200, 200, 200));
		negativeLow.setForeground(new Color(200, 200, 200));

		//labels
		for(JLabel label : labels){ //enhanced for loop: Recorre el array labels y por cada objeto de tipo JLabel que se encuentre (JLabel label), le aplica.
			label.setForeground(new Color(200, 200, 200));
		}

		tolerancechain.setForeground(new Color(200, 200, 200));
		tolerancechain.setBackground(new Color(63, 65, 66));

		//buttons
		for(JButton button : buttons){
			button.setForeground(new Color(200, 200, 200));
			button.setBackground(new Color(41, 48, 56));		
		}

		//textfields
		for(JTextField textfield : textfields){
			textfield.setForeground(new Color(255, 255, 255));
			textfield.setBackground(new Color(63, 65, 66));			
		}

		//textareas
		for(JTextArea textarea : textareas){
			textarea.setForeground(new Color(255, 255, 255));
			textarea.setBackground(new Color(63, 65, 66));			
		}

		//combos
		for(JComboBox combo : combos){
			combo.setForeground(new Color(200, 200, 200));
			combo.setBackground(new Color(63, 65, 66));			
		}

		//radios
		for(JRadioButton radio : radios){ 
			radio.setForeground(new Color(200, 200, 200));
		}

		//menu
		menu.setBackground(new Color(33, 35, 37));

		file.setForeground(new Color(200, 200, 200));
		file.setBackground(new Color(33, 35, 37));

		mcalculate.setForeground(new Color(200, 200, 200));
		calculate.setBackground(new Color(33, 35, 37));

		view.setForeground(new Color(200, 200, 200));
		view.setBackground(new Color(33, 35, 37));

		about.setForeground(new Color(200, 200, 200));
		about.setBackground(new Color(33, 35, 37));

		//items
		for (JMenuItem item : items) {
    			item.setForeground(new Color(200, 200, 200));
    			item.setBackground(new Color(33, 35, 37));
		}
		repaint();
		revalidate();
	}

	public void DayMode(
		Color[] colors,
    		JLabel[] labels, 
    		JButton[] buttons, 
    		JTextField[] textfields, 
    		JTextArea[] textareas, 
    		JComboBox[] combos, 
    		JRadioButton[] radios, 
    		JMenuItem[] items, 
    		JLabel tolerancechain, 
    		JMenuBar menu, 
    		JMenu file, 
    		JMenu mcalculate, 
    		JMenu view, 
    		JMenu about, 
    		JCheckBox confirmation,
		JCheckBox negativeUp, 
		JCheckBox negativeLow
	) {
		getContentPane().setBackground(colors[0]);

		logo.setIcon(new ImageIcon(sizeAdjust));

		//checkbox
		confirmation.setForeground(colors[23]);
		confirmation.setBackground(colors[22]);

		negativeUp.setForeground(colors[23]);
		negativeUp.setBackground(colors[22]);

		negativeLow.setForeground(colors[23]);
		negativeLow.setBackground(colors[22]);

		//labels
		for(JLabel label : labels){ 
			label.setForeground(colors[6]);
		}

		tolerancechain.setBackground(new Color(255, 255, 255)); //Modified to fit defect
		tolerancechain.setForeground(colors[5]);
		title.setForeground(colors[1]);
		subtitle.setForeground(new Color(90, 90, 90)); //Modified to fit defect
		targetName.setForeground(new Color(90, 90, 90));
		changeMode.setForeground(colors[3]);
		luser.setForeground(colors[3]);

		//buttons
		for(JButton button : buttons){
			button.setBackground(colors[7]);
			button.setForeground(colors[8]);		
		}

		//textfields
		for(JTextField textfield : textfields){
			textfield.setBackground(new Color(255, 255, 255)); //Modified to fit defect
			textfield.setForeground(colors[10]);			
		}

		//textareas
		for(JTextArea textarea : textareas){
			textarea.setBackground(new Color(255, 255, 255)); //Modified to fit defect
			textarea.setForeground(colors[12]);			
		}

		//combos
		for(JComboBox combo : combos){
			combo.setBackground(colors[13]);
			combo.setForeground(colors[14]);			
		}

		//radios
		for(JRadioButton radio : radios){ 
			radio.setBackground(colors[15]);
			radio.setForeground(colors[16]);
		}

		//menu
		menu.setBackground(colors[17]);

		file.setForeground(colors[19]);
		file.setBackground(colors[18]);

		mcalculate.setForeground(colors[19]);
		calculate.setBackground(colors[18]);

		view.setForeground(colors[19]);
		view.setBackground(colors[18]);

		about.setForeground(colors[19]);
		about.setBackground(colors[18]);

		//items
		for (JMenuItem item : items) {
    			item.setForeground(colors[21]);
    			item.setBackground(colors[20]);
		}
	}
}