import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Calculation extends JDialog implements ActionListener{
	private JLabel lmethod;
	private JTextArea method;
	private JScrollPane sc;
	private ButtonGroup change;
	private JRadioButton wc, stat, par;
	String mode = Main.mode;

	private String worstCase = """
	\n
	WORST-CASE ANALYSIS:\n
	What is the most extreme dimensional range possible if all contributors simultaneously reach their maximum limits?
	It is assumed that each contributor takes its worst value within its tolerance range.
	In this case, there is a single process, which is only affected by GD&Ts, since they do not contribute to the negative sum.
	Two cases:

	Case 1 – Contributors				
	 Symmetrical tolerances
	 • A: +17.1 mm (±0.25)				
	 • B: –19.1 mm (±0.10)		
	 • C: +74.1 mm (±0.55)		

	Case 2
	 Asymmetrical tolerances + GD&T
	 • A (base): +10.00 mm (+0.30 / –0.10) 
	 • B (recess): -5.00 mm (+0.10 / –0.20)
	 • C (cover): +25.00 mm (+0.50 / –0.30) 
	 • Flatness: ±0.05 mm 
	 • Parallelism: ±0.10 mm 
	 • Concentricity: ±0.08 mm 
	 • Position: ±0.12 mm
	______________________________________________________________________________

	 STEP 1: Target Dimension 
	 	If symmetrical tolerances: 
	 	Nom = (+/- dim.cont1) + (+/- dim.cont2) + (+/- dim.cont3 )….
		 Case 1:
	 	 Nom = 17.1 - 19.1 + 74.1 = 72.1 mm
			
	 	If asymmetrical:
	 	1.1 Mean calculation to determine Target
	 	 Mean = Nominal + (Positive Tol – Negative Tol) / 2
		  Case 2:
	 	 1 -> 10.00 + (0.30 – 0.10) / 2 = 10.10 (positive)
		  2 -> 5.00 + (0.10 – 0.20) / 2 = 4.95 (negative)
		  3 -> 25.00 + (0.50 – 0.30) / 2 = 25.10 (positive)
			
		 1.2 Target Dimension Calculation:
		  Target Dimension = (+/- dim.cont1) + (+/- dim.cont2) + …..
		  Case 2:
		  Nom = +10.10 – 4.95 + 25.10 + 0 (GD&Ts) = 30.25 mm 
		  *This will be the dimension shown in the final result.
		
		  *******************************************************************************
		  But BE CAREFUL! When calculating the average, tolerances become **symmetric**,
		  which means the value now has two options: 	
      		    • ((tol1) + (tol2)) / 2
      		    • Ti / 2
		  *******************************************************************************
		 1.3 New Symmetric Tolerances:
		 ((tol1) + (tol2)) / 2
		  10.10 ± ((0.3 + 0.1) / 2) = ±0.2
 		  4.95 ± ((0.1 + 0.2) / 2) = ±0.15
	 	  25.10 ± ((0.5 + 0.3) / 2) = ±0.4
			
	 STEP 2: Calculate the extremes of each contributor: 
	 	Maximum = direction * nominal + Positive Tol
		Minimum = direction * nominal - Negative Tol
		
		 Case 1:
		  A max: 17.1 + 0.25 = 17.35
		  A min:  17.1 - 0.25 = 16.85
		  B max: -19.1 + 0.1 = -19
		  B min: -19.1 - 0.1 = -19.2
		  C max: 74.1 + 0.55 = 74.65
		  C min: 74.1 - 0.55 = 73.55
		
		 Case 2:
		  A max: 10.1 + 0.2 = 10.3
		  A min:  10.1 - 0.2 = 9.9
		  B max: -4.95 + 0.15 = -4.8
		  B min:  -4.95 - 0.15 = -5.1
	 	 C max: 25.1 + 0.4 = 25.5
	 	 C min: 25.1 - 0.4 = 24.7
	
	 	 (Nominal value of GD&Ts = 0)
	 	  Flat max: 0.05
		  Flat min: 0.05
	 	  Par max: 0.10
	  	  Par min: 0.10
		  Con max: 0.08
		  Con min: 0.08
		  Pos max: 0.12
		  Pos min: 0.12

	 STEP 3: Add totals (nominal + max tol, min tol)\n
		 TotMax = (+/- max.cont1) + (+/- max.cont2) + (+/- max.cont3)….
		 TotMin = (+/- min.cont1) + (+/- min.cont2) + (+/- min.cont3)….
		
		 Case 1:
		  Tol max = 17.35 - 19 + 74.65 = 73
	 	  Tol min = 16.85 - 19.2 + 73.55 = 71.2
		
		 Case 2:
	 	  Tol max = 10.3 - 4.8 + 25.5 + 0.05 + 0.1 + 0.08 + 0.12 = 31.35
		  Tol min =  9.9 - 5.1 + 24.7 + 25.5 + 0.05 + 0.1 + 0.08 + 0.12 = 29.15
		
	 STEP 4: Results
		 Positive Tol = totmax - nom
		 Negative Tol = nom - totmin
	
		 Case 1:
		  Pos Tol = 73 - 72.1 = 0.9
		  Neg Tol = 72.1 - 71.2 = 0.9
		  Result: 72.1 ± 0.9 [73 , 71.2]
		
		 Case 2: 
		  Pos Tol = 31.35 - 30.25 = 1.1
		  Neg Tol = 30.25 - 29.15 = 1.1
		  Result: 30.25 ± 1.1 [31.35; 29.15]

	""";

	private String statistical = """
	\n
	STATISTICAL ANALYSIS\n
	In this idealized model:
	Only two processes: one for symmetric tolerances, another for asymmetric ones.
	GD&T falls under the first (symmetric, no direction):
	For the purposes of 1D tolerance analysis, you don’t need to differentiate the specific type of GD&T.
	All of them can be represented as non-directional contributors, 0 ± x, and their only role is to add variability to the final result.
	There's no need for a third process unless you want to include physics, materials, or actual 3D geometry.
		
	 Symmetric contributors + GD&Ts                     
	 EXAMPLE: Random Part                                 
  	  1  A  +  17.1 ± 0.25 
  	  2  B  –  19.1 ± 0.10
 	  3  C  +  74.1 ± 0.55  

	Asymmetric contributors 
	 EXAMPLE: Random part\n
  	  1  A (base) + 10.00; +0.30 –0.10
  	  2  B (cutout) – 5.00; +0.10 –0.20
 	  3  C (cover) + 25.00; +0.50 –0.30
	
	 STEP 1: Calculation of target dimension and total tolerance
		 If Target Dimension symmetrical:
  		  Target Dimension = (+/- dim.cont1) + (+/- dim.cont2) + (+/- dim.cont3 )…
  	 	  Td = 17.1 - 19.1 + 74.1 = 72.1 mm
	
			SUBSTEP 1: Total Tolerance (Ti) of each contributor
  	 	  Ti = Positive Tolerance + Negative Tolerance (in positive)
   		    Ti1 = 0.25 + 0.25 = 0.5
    		    Ti2 = 0.1 + 0.1 = 0.2
    		    Ti3 = 0.55 + 0.55 = 1.1
	
		 If Target Dimension asymmetric:
  		  SUBSTEP 1: Compute actual contributor means:
    	   		1 -> 10.00 + (0.30 – 0.10)/2 = 10.10 (positive)
    	   		2 -> 5.00 + (0.10 – 0.20)/2 = 4.95 (negative)
   	   		3 -> 25.00 + (0.50 – 0.30)/2 = 25.10 (positive)
		
  		  SUBSTEP 2: Compute target dimension:
    		   Target mean = +10.10 − 4.95 + 25.10 = 30.25 mm
    		   This will be the dimension shown in the final result.
	
  		   But NOTE! When calculating the mean, the tolerances become symmetric, 
		   	meaning the values are converted to:
    	 	   • ((tol1) + (tol2)) / 2
    		   • Ti / 2
		
  		  SUBSTEP 3: New symmetric tolerances:
    	 	   10.10 ± ((0.3+0.1)/2) = 0.2
    		   4.95 ± ((0.1+0.2)/2) = 0.15
    		   5.10 ± ((0.5+0.3)/2) = 0.4
		
		  	SUBSTEP 4: Total Tolerance (Ti) of each contributor:
  	  	   Ti = Positive Tolerance + Negative Tolerance (in positive)
   	    	   Ti1 = 0.2 + 0.2 = 0.4
   	    	   Ti2 = 0.15 + 0.15 = 0.3
		  	 Ti3 = 0.4 + 0.4 = 0.8

	_______________________________________________________________________________

	 *Starting from here only the symmetrical case is used as an example.
	 STEP 3: Apply formula depending on distribution
	
	 	Distribution: code (Name) -> Formula σi -> Short comment
	 	-----------------------------------------------------------------------------
	 	RV (Rectangular) -> σi = Ti / √12 (3.4641) -> Uniform, all values equally likely
	 	DV (Triangular) -> σi = Ti / √24 (4.89898) -> Peak at center, lower at extremes
	 	TV1 (Trapezoidal 1) -> σi = √(10 × Ti² / 192) -> Between rectangular and triangular
	 	TV2 (Trapezoidal 2) -> σi = √(5 × Ti² / 108)  -> More centered than TV1
	 	TV3 (Trapezoidal 3) -> σi = √(13 × Ti² / 300) -> Even more concentrated
	 	NV3S (Normal 3σ) -> σi = Ti / 6 ±3σ -> covers all of Ti
	 	NV4S (Normal 4σ) -> σi = Ti / 8 ±4σ -> tighter than 3σ
	 	NV6S (Normal 6σ) -> σi = Ti / 12 ±6σ -> even tighter
	 	-----------------------------------------------------------------------------
	
	 	 RV -> σi1 = 0.5 / √12 (3.4641) = 0.1443;   σi2 = 0.2 / √12 (3.4641) = 0.0577;   σi3 = 1.1 / √12 (3.4641) = 0.3175
	 	 DV -> σi1 = 0.5 / √24 (4.89898) = 0.1020;   σi2 = 0.2 / √24 (4.89898) = 0.0408;   σi3 = 1.1 / √24 (4.89898) = 0.2245
	  	TV1 -> σi1 = √(10 × 〖0.5〗^2  / 192) = 0.1141;   σi2 = √(10 × 〖0.2〗^2  / 192) = 0.0456;   σi3 = √(10 × 〖1.1〗^2  / 192) = 0.2510
	  	TV2 -> σi1 = √(5 × 〖0.5〗^2  / 108) = 0.1076;   σi2 = √(5 × 〖0.2〗^2  / 108) = 0.0430;   σi3 = √(5 × 〖1.1〗^2  / 108) = 0.2366
	  	TV3 -> σi1 = √(13 × 〖0.5〗^2  / 300) = 0.1040;   σi2 =√(13 × 〖0.2〗^2  / 300) = 0.0415;   σi3 = √(13 × 〖1.1〗^2  / 300) = 0.2290
	  	NV3S -> 0.5 / 6 = 0.0833;   σi2 = 0.2 / 6 = 0.0333;   σi3 = 1.1 /6 = 0.1833
	  	NV4S -> 0.5 / 8 = 0.0625;   σi2 = 0.2 / 8 = 0.0250;   σi3 = 1.1 /8 = 0.1375
	  	NV6S -> 0.5 / 12 = 0.04167;   σi2 = 0.2 / 12 = 0.01667;   σi3 = 1.1 /12 = 0.09167
	
	 STEP 4: Calculation of Standar deviation
	 	σt = √(〖σi1〗^2+ 〖σi2〗^2+ 〖σi3〗^2 )  -> despues de distribuccion
	 	 RV -> σt = √(〖0.1443〗^2+ 〖0.0577〗^2+ 〖0.3175〗^2 )=0.3533
	 	 DV -> σt = √(〖0.1020〗^2+ 〖0.0408〗^2+ 〖0.2245〗^2 )=0.25
	 	 TV1 -> σt = √(〖0.1141〗^2+ 〖0.0456〗^2+ 〖0.2510〗^2 )=0.2795
	 	 TV2 -> σt = √(〖0.1076〗^2+ 〖0.0430〗^2+ 〖0.2366〗^2 )=0.2635
	 	 TV3 -> σt = √(〖0.1040〗^2+ 〖0.0415〗^2+ 〖0.2290〗^2 )=0.2550
	 	 NV3S -> σt = √(〖0.0833〗^2+ 〖0.0333〗^2+ 〖0.1833〗^2 )=0.2040
	 	 NV4S -> σt = √(〖0.0625〗^2+ 〖0.0250〗^2+ 〖0.1375〗^2 )=0.1531
	 	 NV6S -> σt = √(〖0.04167〗^2+ 〖0.01667〗^2+ 〖0.09167〗^2 )=0.1021
		
	 STEP 5: Calculate tolerance range:
	 	Depending on the distribution chosen for target:
	 	Standard in engineering: NV4S 
	 	Final tolerance = 
  	  RV -> (2 * 3^0,5) * σt
  	  DV -> (2 * 6^0,5) * σt
 	  TV1 -> (2 * (48 / 10)^0,5) * σt
 	  TV2 -> (2 * (27 / 5)^0,5) * σt
 	  TV3 -> (2 * (75 / 13)^0,5) * σt
 	  NV3S -> 6 * σt
 	  NV4S -> 8 * σt
 	  NV6S -> 12 * σt

	 RESULT: Nominal ± tolerance range [lower limit, upper limit]

	""";
	
	String parameters = """
	\n
	LIST OF ANOMALIES TO WARN ABOUT\n
	Input errors (logical or formatting)
    	- Positive or negative tolerance is empty or non-numeric
    	- Negative tolerance entered as a negative value (should be positive)
   	- Nominal value is empty or invalid
    	- Direction not specified (+ / – / neutral)
    	- Distribution type not selected
    	- Zero tolerance on both sides (contributor has no effect)
    	- Contributor with duplicate name (risk of confusion)
    	- Total number of contributors is zero or insufficient for analysis
	
	Statistical / design anomalies
    	- All contributors have the same direction (+ or –)
      	 “Warning: all dimensions affect in the same direction. Is this correct?”
	
	Analysis integrity alerts
    	- Statistical result exceeds the worst-case result (should always be less than or equal)
	
	""";

	public Calculation(Frame parent){
		super(parent, "Tolerance calculation", true);
		
		setLayout(null);
		setIconImage(new ImageIcon(getClass().getResource("images/icon_supplier.png")).getImage()); //Set Icon image
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //does not close Main
		setSize(900, 470);
		setLocationRelativeTo(null);
		setResizable(false);

		if(mode.equals("night")){
			getContentPane().setBackground(new Color(33,  35,  37));
		}
		
		//label design
		lmethod = new JLabel("Method used for tolerance stack-up calculation:");
		lmethod.setBounds(20, 10, 500, 20);
		lmethod.setFont(new Font("Work Sans", 3, 16));
		if(mode.equals("night")){
			lmethod.setForeground(new Color(200, 200, 200));

		} else{
			lmethod.setForeground(new Color(90, 90, 90));	
	
		}
		add(lmethod);
		
		//text field design
		method = new JTextArea(worstCase);
		method.setEditable(false);
		if(mode.equals("night")){
			method.setForeground(new Color(255, 255, 255));
			method.setBackground(new Color(63,  65,  66));	
		}
		sc = new JScrollPane(method);
		sc.setBounds(20, 40, 840, 355);
		add(sc);

		//radio button design
		change = new ButtonGroup();
		wc = new JRadioButton("Worst-case analysis");
		wc.setBounds(430, 10, 150, 20);
		wc.addActionListener(this);
		wc.setSelected(true);
		wc.setOpaque(false);
		if(mode.equals("night")){
			wc.setForeground(new Color(200, 200, 200));

		} else{
			wc.setForeground(new Color(90, 90, 90));	
	
		}
		add(wc);
		change.add(wc);

		stat = new JRadioButton("Statistical analysis");
		stat.setBounds(595, 10, 150, 20);
		stat.addActionListener(this);
		stat.setOpaque(false);
		if(mode.equals("night")){
			stat.setForeground(new Color(200, 200, 200));

		} else{
			stat.setForeground(new Color(90, 90, 90));	
	
		}
		add(stat);
		change.add(stat);

		par = new JRadioButton("Parammeters");
		par.setBounds(750, 10, 150, 20);
		par.addActionListener(this);
		par.setOpaque(false);
		if(mode.equals("night")){
			par.setForeground(new Color(200, 200, 200));

		} else{
			par.setForeground(new Color(90, 90, 90));	
	
		}
		add(par);
		change.add(par);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == wc){
			method.setText(worstCase);
			method.setCaretPosition(0);
			sc.getVerticalScrollBar().setValue(0);

		}else if(e.getSource() == stat){
			method.setText(statistical);
			method.setCaretPosition(0);
			sc.getVerticalScrollBar().setValue(0);
		
		}else{
			method.setText(parameters);
			method.setCaretPosition(0);
			sc.getVerticalScrollBar().setValue(0);
		
		}
	}
}