import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.Random;
import java.io.*; 

public class FrameI extends JFrame implements ActionListener, ItemListener { 
	
	private static final long serialVersionUID = 22122009;
	static public IpFile file;
	static public BigInteger p;
	static public BigInteger a;
	static public BigInteger b;
	static public BigInteger m;
	static public ECPoint P;
	static public BigInteger s;
	static public BigInteger r;
	
	// SETUP 
	JPanel setup = new JPanel();
	JPanel setup1 = new JPanel();
	JPanel setup2 = new JPanel();
	JPanel setup3 = new JPanel();
	JRadioButton rbSetup1 = new JRadioButton("Fixed IBS-parameters.");
	JRadioButton rbSetup2 = new JRadioButton("Randomly choosen IBS-parameters (m \u2243");
	String[] sBit = {"2", "4", "8", "16", "32", "64", "128"};
	SpinnerListModel smPBit = new SpinnerListModel(sBit);
	JSpinner spBit = new JSpinner(smPBit);
	JLabel lblrbSetup2 = new JLabel(" Bit).");
	JRadioButton rbSetup3 = new JRadioButton("Load your own IBS-parameter-File:");
	JRadioButton[] rbSetupArray = {rbSetup1, rbSetup2, rbSetup3};
	ButtonGroup bgSetup = new ButtonGroup();
	JTextArea txtUpdate = new JTextArea("C:\\");
	JButton bnSetup = new JButton("Setup");
	
	String[] setupStrings = {"p", "a", "b", "m", "E(IF_p\u00B2)[m]", "(IF_p\u00B2)*", "P", "s", "P_pub", "e'", "\u03C4'", "H\u2081", "H\u2082"};
	JComboBox cbSetup = new JComboBox(setupStrings);
	JTextArea txtInfo = new JTextArea();
	JLabel lblInfo = new JLabel();
			
	JRadioButton rbWeil = new JRadioButton("Weil-pairing");
	JRadioButton rbTate = new JRadioButton("Tate-pairing",true);
	JRadioButton[] rbPairingArray = {rbWeil, rbTate};
	ButtonGroup bgPairing = new ButtonGroup();
	
	JLabel lblThem1 = new JLabel("Identity-Based");
	JLabel lblThem2 = new JLabel("Signature Scheme");
	JLabel lblThem3 = new JLabel("by Cha and Cheon");
	JLabel lblPic = new JLabel(new ImageIcon("Images\\signature.png"));
	JLabel lblAuthor = new JLabel("\u00A9 Karin Schaller");
	
	// EXTRACT 
	JPanel extract = new JPanel();
	JPanel extract1 = new JPanel();
	JPanel extract2 = new JPanel();
	JLabel lblPubk = new JLabel("public-key pubk = ");
	JTextArea txtPubk = new JTextArea(); 
	JRadioButton rbExtract1 = new JRadioButton("ASCII",true);
	JRadioButton rbExtract2 = new JRadioButton("Binary");
	JRadioButton[] rbExtractArray = {rbExtract1, rbExtract2};
	ButtonGroup bgExtract = new ButtonGroup();
	String messageExtract = "ascii";
	JButton bnExtract = new JButton("Extract");
	
	JLabel lblQE = new JLabel ("Q = H\u2081(pubk)");
	JButton bnQEPress = new JButton("="); 
	JTextArea txtQE = new JTextArea();
	JLabel lblPrik = new JLabel ("private-key prik = s•Q = ");
	JTextArea txtPrik = new JTextArea();
	
	ECPoint Prik;
	
	// SIGN 
	JPanel sign = new JPanel();
	JPanel sign1 = new JPanel();
	JPanel sign2 = new JPanel();
	JLabel lblMessageS = new JLabel("message M = ");
	JTextArea txtMessageS = new JTextArea();
	JRadioButton rbSign1 = new JRadioButton("ASCII",true);
	JRadioButton rbSign2 = new JRadioButton("Binary");
	JRadioButton[] rbSignArray = {rbSign1, rbSign2};
	ButtonGroup bgSign = new ButtonGroup();
	String messageSign = "ascii";
	JButton bnSign = new JButton("Sign");
	
	JLabel lblQS = new JLabel ("Q = H\u2081(pubk)");
	JButton bnQSPress = new JButton("="); 
	JTextArea txtQS = new JTextArea();
	JLabel lblr = new JLabel ("random r = ");
	JTextArea txtr = new JTextArea();
	JLabel lblU = new JLabel ("U = r•Q = ");
	JTextArea txtU = new JTextArea();
	JLabel lblhS = new JLabel ("h = H\u2082(M,U)");
	JButton bnhSPress = new JButton("="); 
	JTextArea txthS = new JTextArea();
	JLabel lblV = new JLabel ("V = (r+h mod m)•prik = ");
	JTextArea txtV = new JTextArea();
	JLabel lblSignatureS = new JLabel ("signature <U,V> = ");
	JTextArea txtSignatureS = new JTextArea();
	
	ECPoint U;
	ECPoint V;
	
	// VERIFY 
	JPanel verify = new JPanel();
	JPanel verify1 = new JPanel();
	JPanel verify2 = new JPanel();
	JLabel lblMessageV = new JLabel("message M = ");
	JTextArea txtMessageV = new JTextArea();
	JRadioButton rbVerify1 = new JRadioButton("ASCII",true);
	JRadioButton rbVerify2 = new JRadioButton("Binary");
	JRadioButton[] rbVerifyArray = {rbVerify1, rbVerify2};
	ButtonGroup bgVerify = new ButtonGroup();
	String messageVerify = "ascii";
	JButton bnVerify = new JButton("Verify");
	JLabel lblSignatureV = new JLabel ("signature <U,V> = ");
	JTextArea txtSignatureV = new JTextArea();
	
	JLabel lblhV = new JLabel ("h = H\u2082(M,U)");
	JButton bnhVPress = new JButton("="); 
	JTextArea txthV = new JTextArea();
	JLabel lblQV = new JLabel ("Q = H\u2081(pubk)");
	JButton bnQVPress = new JButton("="); 
	JTextArea txtQV = new JTextArea();
	JLabel lblT = new JLabel("T = U+h•Q = ");
	JTextArea txtT = new JTextArea();
	JLabel lblg = new JLabel("\u03C4'(P,V)");
	JButton bngPress = new JButton("="); 
	JTextArea txtg = new JTextArea();
	JLabel lblgg = new JLabel("\u03C4'(P_pub,T)");
	JButton bnggPress = new JButton("="); 
	JTextArea txtgg = new JTextArea();
	JLabel lblOutput = new JLabel("The signature is ");
	JLabel txtQutput = new JLabel();
	JLabel lblOutput2 = new JLabel(".");
	
	ECPoint T;
	ECPoint Ppub;
	ECPoint S1;
	ECPoint S2;
	
	public FrameI() {
		
		super("Identity-Based Signature Scheme");
		JTabbedPane tp = new JTabbedPane();
		tp.setTabPlacement(JTabbedPane.LEFT);
		Container cp = getContentPane();
		cp.add(tp);

		// SETUP
		tp.addTab("Setup", setup);
		setup.setLayout(null);
		
		setup.add(setup1);
		setup1.setLayout(null);
		setup1.setBorder(BorderFactory.createTitledBorder("IBS-parameters"));
		setup1.setBounds(6,0,468,170);
		
		for(int i=0; i<3; i++) {
			setup1.add(rbSetupArray[i]);
			bgSetup.add(rbSetupArray[i]);
			rbSetupArray[i].setFocusable(false);
			rbSetupArray[i].addActionListener(this);
			rbSetupArray[i].addItemListener(this);
		}

		rbSetupArray[0].setBounds(9,30,200,20);
		rbSetupArray[1].setBounds(9,62,260,20);
		rbSetupArray[2].setBounds(9,94,300,20);
		
		setup1.add(spBit);
		spBit.setBounds(269,62,45,20);
		spBit.setValue("16");
		((JSpinner.DefaultEditor)spBit.getEditor()).getTextField().setEditable(false);
		((JSpinner.DefaultEditor)spBit.getEditor()).getTextField().setBackground(Color.WHITE);
		setup1.add(lblrbSetup2);
		lblrbSetup2.setBounds(316,62,50,20);

		JScrollPane spTxtBrowse = new JScrollPane(txtUpdate);
		setup1.add(spTxtBrowse);
		spTxtBrowse.setBounds(30,120,310,20);
		txtUpdate.setLineWrap(true);
		txtUpdate.setWrapStyleWord(true);
		
		setup1.add(bnSetup);
		bnSetup.setBounds(365,31,75,109); 
		bnSetup.addActionListener(this);
		bnSetup.setFocusable(false);
		
		setup.add(setup2);
		setup2.setLayout(null);
		setup2.setBorder(BorderFactory.createTitledBorder("Details"));
		setup2.setBounds(6,175,468,174);
		
		setup2.add(cbSetup);
		cbSetup.setBounds(15,30,110,20);
		cbSetup.setBackground(Color.white);
		cbSetup.setMaximumRowCount(5);
		cbSetup.addActionListener(this);
		
		JScrollPane spTxtInfo = new JScrollPane(txtInfo);
		setup2.add(spTxtInfo);
		spTxtInfo.setBounds(150,30,290,40);
		txtInfo.setLineWrap(true);
		txtInfo.setWrapStyleWord(true);
		txtInfo.setEditable(false);
		txtInfo.setBackground(new Color(238,238,238));
		txtInfo.setText("N/A");
		
		setup2.add(lblInfo);
		lblInfo.setBorder(BorderFactory.createTitledBorder("Information"));
		lblInfo.setBounds(148,80,294,64);
		
		lblInfo.setText("N/A");
		
		setup.add(setup3);
		setup3.setLayout(null);
		setup3.setBorder(BorderFactory.createTitledBorder("Pairing-type"));
		setup3.setBounds(485,0,116,170);
		
		for(int i=0; i<2; i++) {
			bgPairing.add(rbPairingArray[i]);
			setup3.add(rbPairingArray[i]);
			rbPairingArray[i].addActionListener(this);
		}

		rbPairingArray[0].setBounds(9,30,100,20);
		rbPairingArray[1].setBounds(9,62,100,20);
		rbWeil.setFocusable(false);
		rbTate.setFocusable(false);
		
		setup.add(lblThem1);
		setup.add(lblThem2);
		setup.add(lblThem3);
		setup.add(lblAuthor);
		setup.add(lblPic);
		lblThem1.setBounds(503,178,100,20);
		lblThem2.setBounds(491,200,120,20);
		lblThem3.setBounds(493,222,120,20);
		lblPic.setBounds(492,237,100,100);
		lblAuthor.setBounds(497,326,100,30);
		
		// EXTRACT 
		tp.addTab("Extract", extract);
		extract.setLayout(null);
		
		extract.add(extract1);
		extract1.setLayout(null);
		extract1.setBorder(BorderFactory.createTitledBorder("Public-key input"));
		extract1.setBounds(6,0,595,170);
	
		extract1.add(lblPubk);
		lblPubk.setBounds(13,30,110,20);
		
		JScrollPane spTxtPubk = new JScrollPane(txtPubk);
		extract1.add(spTxtPubk);
		spTxtPubk.setBounds(120,31,320,35);
		txtPubk.setLineWrap(true);
		txtPubk.setWrapStyleWord(true);
		
		for(int i=0; i<2; i++) {
			extract1.add(rbExtractArray[i]);
			bgExtract.add(rbExtractArray[i]);
			rbExtractArray[i].setFocusable(false);
			rbExtractArray[i].addActionListener(this);
		}
		
		rbExtractArray[0].setBounds(488,30,65,20);
		rbExtractArray[1].setBounds(488,62,65,20);
		
		extract1.add(bnExtract);
		bnExtract.setBounds(492,107,75,35);
		bnExtract.addActionListener(this);
		bnExtract.setFocusable(false);
		
		extract.add(extract2);
		extract2.setLayout(null);
		extract2.setBorder(BorderFactory.createTitledBorder("Private-key calculation"));
		extract2.setBounds(6,175,595,174);
		
		extract2.add(lblQE);
		lblQE.setBounds(10,20,80,20);
		
		extract2.add(bnQEPress);
		bnQEPress.setBounds(85,20,45,20);
		bnQEPress.addActionListener(this);
		bnQEPress.setBackground(new Color(238,238,238));
		bnQEPress.setFocusable(false); 
		
		JScrollPane spTxtQE = new JScrollPane(txtQE);
		extract2.add(spTxtQE);
		spTxtQE.setBounds(135,20,305,20);
		txtQE.setLineWrap(true);
		txtQE.setWrapStyleWord(true);
		txtQE.setEditable(false);
		txtQE.setBackground(new Color(238,238,238));
		
		extract2.add(lblPrik);
		lblPrik.setBounds(10,45,140,20);
		
		JScrollPane spTxtPrik = new JScrollPane(txtPrik);
		extract2.add(spTxtPrik);
		spTxtPrik.setBounds(150,45,290,20);
		txtPrik.setLineWrap(true);
		txtPrik.setWrapStyleWord(true);
		txtPrik.setEditable(false);
		txtPrik.setBackground(new Color(238,238,238));
		
		txtQE.setText("N/A");
		txtPrik.setText("N/A");	
		
		// SIGN 
		tp.addTab("Sign", sign);
		sign.setLayout(null);

		sign.add(sign1);
		sign1.setLayout(null);
		sign1.setBorder(BorderFactory.createTitledBorder("Message input"));
		sign1.setBounds(6,0,595,170); 
		
		sign1.add(lblMessageS);
		lblMessageS.setBounds(13,30,80,20); 
		
		JScrollPane spTxtMessageS = new JScrollPane(txtMessageS);
		sign1.add(spTxtMessageS);
		spTxtMessageS.setBounds(120,31,320,111); 
		txtMessageS.setLineWrap(true);
		txtMessageS.setWrapStyleWord(true);
		
		for(int i=0; i<2; i++) {
			sign1.add(rbSignArray[i]);
			bgSign.add(rbSignArray[i]);
			rbSignArray[i].setFocusable(false);
			rbSignArray[i].addActionListener(this);
		}
		
		rbSignArray[0].setBounds(488,30,65,20);
		rbSignArray[1].setBounds(488,62,65,20);
		
		sign1.add(bnSign);
		bnSign.setBounds(492,107,75,35);
		bnSign.addActionListener(this);
		bnSign.setFocusable(false);
		
		sign.add(sign2);
		sign2.setLayout(null);
		sign2.setBorder(BorderFactory.createTitledBorder("Signature calculation"));
		sign2.setBounds(6,175,595,174);
		
		sign2.add(lblQS);
		lblQS.setBounds(10,20,80,20);
		
		sign2.add(bnQSPress);
		bnQSPress.setBounds(85,20,45,20);
		bnQSPress.addActionListener(this);
		bnQSPress.setBackground(new Color(238,238,238));
		bnQSPress.setFocusable(false); 
		
		JScrollPane spTxtQS = new JScrollPane(txtQS);
		sign2.add(spTxtQS);
		spTxtQS.setBounds(135,20,305,20);
		txtQS.setLineWrap(true);
		txtQS.setWrapStyleWord(true);
		txtQS.setEditable(false);
		txtQS.setBackground(new Color(238,238,238));
		
		sign2.add(lblr);
		lblr.setBounds(10,45,65,20);
		
		JScrollPane spTxtr = new JScrollPane(txtr);
		sign2.add(spTxtr);
		spTxtr.setBounds(77,45,363,20);
		txtr.setLineWrap(true);
		txtr.setWrapStyleWord(true);
		txtr.setEditable(false);
		txtr.setBackground(new Color(238,238,238));
		
		sign2.add(lblU);
		lblU.setBounds(10,70,60,20);
		
		JScrollPane spTxtU = new JScrollPane(txtU);
		sign2.add(spTxtU);
		spTxtU.setBounds(70,70,370,20);
		txtU.setLineWrap(true);
		txtU.setWrapStyleWord(true);
		txtU.setEditable(false);
		txtU.setBackground(new Color(238,238,238));
		
		sign2.add(lblhS);
		lblhS.setBounds(10,95,75,20);
		
		sign2.add(bnhSPress);
		bnhSPress.setBounds(75,95,45,20);
		bnhSPress.addActionListener(this);
		bnhSPress.setBackground(new Color(238,238,238));
		bnhSPress.setFocusable(false); 
		
		JScrollPane spTxthS = new JScrollPane(txthS);
		sign2.add(spTxthS);
		spTxthS.setBounds(125,95,315,20);
		txthS.setLineWrap(true);
		txthS.setWrapStyleWord(true);
		txthS.setEditable(false);
		txthS.setBackground(new Color(238,238,238));
		
		sign2.add(lblV);
		lblV.setBounds(10,120,135,20);
		
		JScrollPane spTxtV = new JScrollPane(txtV);
		sign2.add(spTxtV);
		spTxtV.setBounds(145,120,295,20);
		txtV.setLineWrap(true);
		txtV.setWrapStyleWord(true);
		txtV.setEditable(false);
		txtV.setBackground(new Color(238,238,238));
		
		sign2.add(lblSignatureS);
		lblSignatureS.setBounds(10,145,105,20);
		
		JScrollPane spTxtSignatureS = new JScrollPane(txtSignatureS);
		sign2.add(spTxtSignatureS);
		spTxtSignatureS.setBounds(115,145,325,20);
		txtSignatureS.setLineWrap(true);
		txtSignatureS.setWrapStyleWord(true);
		txtSignatureS.setEditable(false);
		txtSignatureS.setBackground(new Color(238,238,238));
		
		txtQS.setText("N/A");
		txtr.setText("N/A");
		txtU.setText("N/A");
		txthS.setText("N/A");
		txtV.setText("N/A");
		txtSignatureS.setText("N/A");
		
		// VERIFY 
		tp.addTab("Verify", verify);
		verify.setLayout(null);

		verify.add(verify1);
		verify1.setLayout(null);
		verify1.setBorder(BorderFactory.createTitledBorder("Message and signature"));
		verify1.setBounds(6,0,595,170);
		
		verify1.add(lblMessageV);
		lblMessageV.setBounds(13,30,80,20);
		
		JScrollPane spTxtMessageV = new JScrollPane(txtMessageV);
		verify1.add(spTxtMessageV);
		spTxtMessageV.setBounds(120,31,320,68); 
		txtMessageV.setLineWrap(true);
		txtMessageV.setWrapStyleWord(true);
		
		verify1.add(lblSignatureV);
		lblSignatureV.setBounds(13,106,105,20);
		
		JScrollPane sptxtSignatureV = new JScrollPane(txtSignatureV);
		verify1.add(sptxtSignatureV);
		sptxtSignatureV.setBounds(120,107,320,35);
		txtSignatureV.setLineWrap(true);
		txtSignatureV.setWrapStyleWord(true);
		txtSignatureV.setEditable(false);
		txtSignatureV.setBackground(new Color(238,238,238));
		
		for(int i=0; i<2; i++) {
			verify1.add(rbVerifyArray[i]);
			bgVerify.add(rbVerifyArray[i]);
			rbVerifyArray[i].setFocusable(false);
			rbVerifyArray[i].addActionListener(this);
		}
		
		rbVerifyArray[0].setBounds(488,30,65,20);
		rbVerifyArray[1].setBounds(488,62,65,20);
		
		verify1.add(bnVerify);
		bnVerify.setBounds(492,107,75,35);
		bnVerify.addActionListener(this);
		bnVerify.setFocusable(false);
		
		verify.add(verify2);
		verify2.setLayout(null);
		verify2.setBorder(BorderFactory.createTitledBorder("Signature validation"));
		verify2.setBounds(6,175,595,174);

		verify2.add(lblhV);
		lblhV.setBounds(10,20,70,20);
		
		verify2.add(bnhVPress);
		bnhVPress.setBounds(75,20,45,20);
		bnhVPress.addActionListener(this);
		bnhVPress.setBackground(new Color(238,238,238));
		bnhVPress.setFocusable(false); 
		
		JScrollPane spTxthV = new JScrollPane(txthV);
		verify2.add(spTxthV);
		spTxthV.setBounds(125,20,315,20);
		txthV.setLineWrap(true);
		txthV.setWrapStyleWord(true);
		txthV.setEditable(false);
		txthV.setBackground(new Color(238,238,238));
		
		verify2.add(lblQV);
		lblQV.setBounds(10,45,80,20);
		
		verify2.add(bnQVPress);
		bnQVPress.setBounds(85,45,45,20);
		bnQVPress.addActionListener(this);
		bnQVPress.setBackground(new Color(238,238,238));
		bnQVPress.setFocusable(false);
		
		JScrollPane spTxtQV = new JScrollPane(txtQV);
		verify2.add(spTxtQV);
		spTxtQV.setBounds(135,45,305,20);
		txtQV.setLineWrap(true);
		txtQV.setWrapStyleWord(true);
		txtQV.setEditable(false);
		txtQV.setBackground(new Color(238,238,238));
		
		verify2.add(lblT);
		lblT.setBounds(10,70,80,20);
		
		JScrollPane spTxtT = new JScrollPane(txtT);
		verify2.add(spTxtT);
		spTxtT.setBounds(85,70,355,20);
		txtT.setLineWrap(true);
		txtT.setWrapStyleWord(true);
		txtT.setEditable(false);
		txtT.setBackground(new Color(238,238,238));
		
		verify2.add(lblg);
		lblg.setBounds(10,95,40,20);
		
		verify2.add(bngPress);
		bngPress.setBounds(50,95,45,20);
		bngPress.addActionListener(this);
		bngPress.setBackground(new Color(238,238,238));
		bngPress.setFocusable(false);
		
		JScrollPane spTxtg = new JScrollPane(txtg);
		verify2.add(spTxtg);
		spTxtg.setBounds(100,95,340,20);
		txtg.setLineWrap(true);
		txtg.setWrapStyleWord(true);
		txtg.setEditable(false);
		txtg.setBackground(new Color(238,238,238));
		
		verify2.add(lblgg);
		lblgg.setBounds(10,120,75,20);
		
		verify2.add(bnggPress);
		bnggPress.setBounds(75,120,45,20);
		bnggPress.addActionListener(this);
		bnggPress.setBackground(new Color(238,238,238));
		bnggPress.setFocusable(false);
		
		JScrollPane spTxtgg = new JScrollPane(txtgg);
		verify2.add(spTxtgg);
		spTxtgg.setBounds(125,120,315,20);
		txtgg.setLineWrap(true);
		txtgg.setWrapStyleWord(true);
		txtgg.setEditable(false);
		txtgg.setBackground(new Color(238,238,238));
		
		verify2.add(lblOutput);
		lblOutput.setBounds(10,145,105,20);
		
		verify2.add(txtQutput);
		txtQutput.setBounds(105,145,70,20);
		txtQutput.setForeground(Color.black);
		txtQutput.setText("………");
		
		verify2.add(lblOutput2);
		lblOutput2.setBounds(150,145,5,20);
		
		txthV.setText("N/A");
		txtQV.setText("N/A");
		txtT.setText("N/A");
		txtg.setText("N/A");
		txtgg.setText("N/A");
	}
	
	public void itemStateChanged(ItemEvent e) { 
		
		if (e.getItem() == rbSetup1 || e.getItem() == rbSetup2 || e.getItem() == rbSetup3) {
			p = null;
			txtInfo.setText("N/A");
			lblInfo.setText("N/A");
			
			// EXTRACT
			rbExtract1.setSelected(true);
			txtPubk.setText("");
			txtQE.setText("N/A");
			txtPrik.setText("N/A");
			// SIGN
			rbSign1.setSelected(true);
			txtMessageS.setText("");
			txtQS.setText("N/A");
			txtr.setText("N/A");
			txtU.setText("N/A");
			txthS.setText("N/A");
			txtV.setText("N/A");
			txtSignatureS.setText("N/A");
			// VERIFY
			rbVerify1.setSelected(true);
			txtMessageV.setText("");
			txtSignatureV.setText("");
			txthV.setText("N/A");
			txtQV.setText("N/A");
			txtT.setText("N/A");
			txtg.setText("N/A");
			txtgg.setText("N/A");
			txtQutput.setForeground(Color.black);
			txtQutput.setText("………");
		}
    }
        
	public void actionPerformed(ActionEvent event) {
		
		// SETUP 
		if (event.getSource() == bnSetup) {
			if (!rbSetup1.isSelected() && !rbSetup2.isSelected() && !rbSetup3.isSelected()) {
				JOptionPane jopSetup = new JOptionPane("Make a choice.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagSetup = jopSetup.createDialog(jopSetup,"Error");
				diagSetup.setLocation(this.getLocation().x+320,this.getLocation().y+60);
				diagSetup.setVisible(true);
			}
			else {
				if(rbSetup1.isSelected()) {
					p = new BigInteger("631");
					a = new BigInteger("34");
					b = BigInteger.ZERO;
					m = new BigInteger("79");
					P = new ECPoint(new FiniteFieldEl(new BigInteger[] {new BigInteger("8"), BigInteger.ZERO}),new FiniteFieldEl(new BigInteger[] {new BigInteger("28"), BigInteger.ZERO}),false);
					s = new BigInteger("51");
					r = new BigInteger("26");
					Ppub = P.multiplyECPoint(s);
				}
			
				if(rbSetup2.isSelected()) {              
					int bit = new Integer(spBit.getValue().toString());
					BigInteger[] primArray = randomSpecialPrime(bit);
					p = primArray[1];
					a = new BigInteger(p.bitLength(),new Random()).mod(p);
					
					while (a.equals(BigInteger.ZERO) || new BigInteger("4").multiply(a.modPow(new BigInteger("3"),p)).mod(p).equals(BigInteger.ZERO)) {
						a = new BigInteger(p.bitLength(),new Random()).mod(p);
					}
					
					b = BigInteger.ZERO;
					m = primArray[0];
					P = ECPoint.randomTorsionECPoint(p,a,b,m); 
					s = new BigInteger(m.bitLength(),new Random()).mod(m);
					
					while (s.equals(BigInteger.ZERO)) {
						s = new BigInteger(p.bitLength(),new Random()).mod(m);
					}
					
					r = new BigInteger(m.bitLength(),new Random()).mod(m);
					
					while (r.equals(BigInteger.ZERO)) {
						r = new BigInteger(p.bitLength(),new Random()).mod(m);
					}
					
					Ppub = P.multiplyECPoint(s);
					
					// EXTRACT
					rbExtract1.setSelected(true);
					txtPubk.setText("");
					txtQE.setText("N/A");
					txtPrik.setText("N/A");
					// SIGN
					rbSign1.setSelected(true);
					txtMessageS.setText("");
					txtQS.setText("N/A");
					txtr.setText("N/A");
					txtU.setText("N/A");
					txthS.setText("N/A");
					txtV.setText("N/A");
					txtSignatureS.setText("N/A");
					// VERIFY
					rbVerify1.setSelected(true);
					txtMessageV.setText("");
					txtSignatureV.setText("");
					txthV.setText("N/A");
					txtQV.setText("N/A");
					txtT.setText("N/A");
					txtg.setText("N/A");
					txtgg.setText("N/A");
					txtQutput.setForeground(Color.black);
					txtQutput.setText("………");
				}
			
				if(rbSetup3.isSelected()) {
					try {new RandomAccessFile(txtUpdate.getText(),"r");
					file = new IpFile(txtUpdate.getText());
					boolean validFile = false;
					
					if (txtUpdate.getText().endsWith(".ipf")) {
						validFile = true;
					}
					
					if (!validFile) {
						JOptionPane jopSetup = new JOptionPane("Choose .ipf-file.", JOptionPane.ERROR_MESSAGE); 
						JDialog diagSetup = jopSetup.createDialog(jopSetup,"Error");
						diagSetup.setLocation(this.getLocation().x+320,this.getLocation().y+60);
						diagSetup.setVisible(true);
						p = null; 
						txtInfo.setText("N/A");
						lblInfo.setText("N/A");
					}
					else {
						p = file.get('p'); 
						a = file.get('a').mod(p);
						b = file.get('b');
						m = file.get('m');
						P = new ECPoint(new FiniteFieldEl(new BigInteger[] {file.get('x'), BigInteger.ZERO}),new FiniteFieldEl(new BigInteger[] {file.get('y'), BigInteger.ZERO}),false);
						s = file.get('s').mod(m);
						r = file.get('r').mod(m);
						
						// EXTRACT
						rbExtract1.setSelected(true);
						txtPubk.setText("");
						txtQE.setText("N/A");
						txtPrik.setText("N/A");
						// SIGN
						rbSign1.setSelected(true);
						txtMessageS.setText("");
						txtQS.setText("N/A");
						txtr.setText("N/A");
						txtU.setText("N/A");
						txthS.setText("N/A");
						txtV.setText("N/A");
						txtSignatureS.setText("N/A");
						// VERIFY
						rbVerify1.setSelected(true);
						txtMessageV.setText("");
						txtSignatureV.setText("");
						txthV.setText("N/A");
						txtQV.setText("N/A");
						txtT.setText("N/A");
						txtg.setText("N/A");
						txtgg.setText("N/A");
						txtQutput.setForeground(Color.black);
						txtQutput.setText("………");
					
						if (!(p.mod(new BigInteger("4")).equals(new BigInteger("3"))) || !(b.equals(BigInteger.ZERO)) ||  !(m.isProbablePrime(15)) || !((p.add(BigInteger.ONE)).mod(m).equals(BigInteger.ZERO)) || (p.subtract(BigInteger.ONE)).mod(m).equals(BigInteger.ZERO) || s.equals(BigInteger.ZERO) || r.equals(BigInteger.ZERO) || !P.onCurve(m)) {
							if (!(p.mod(new BigInteger("4")).equals(new BigInteger("3")))) {
								JOptionPane jopSetup = new JOptionPane("Choose another p, because p is not congruent 3 modulo 4.", JOptionPane.ERROR_MESSAGE); 
								JDialog diagSetup = jopSetup.createDialog(jopSetup,"Error");
								diagSetup.setLocation(this.getLocation().x+320,this.getLocation().y+60);
								diagSetup.setVisible(true);
								p = null; 
								txtInfo.setText("N/A");
								lblInfo.setText("N/A");
							}
							else {
								if (a.equals(BigInteger.ZERO) || new BigInteger("4").multiply(a.modPow(new BigInteger("3"),p)).mod(p).equals(BigInteger.ZERO)) {
									JOptionPane jopSetup = new JOptionPane("Choose a \u2262 0 (mod p) and 4a\u00B3 + 27b\u00B2 \u2262 0 (mod p).", JOptionPane.ERROR_MESSAGE); 
									JDialog diagSetup = jopSetup.createDialog(jopSetup,"Error");
									diagSetup.setLocation(this.getLocation().x+320,this.getLocation().y+60);
									diagSetup.setVisible(true);
									p = null; 
									txtInfo.setText("N/A");
									lblInfo.setText("N/A");
								}
								else {
									if (!(b.equals(BigInteger.ZERO))) {
										JOptionPane jopSetup = new JOptionPane("Choose b = 0.", JOptionPane.ERROR_MESSAGE); 
										JDialog diagSetup = jopSetup.createDialog(jopSetup,"Error");
										diagSetup.setLocation(this.getLocation().x+320,this.getLocation().y+60);
										diagSetup.setVisible(true);
										p = null; 
										txtInfo.setText("N/A");
										lblInfo.setText("N/A");
									}
									else {
										if (!(m.isProbablePrime(15)) || !((p.add(BigInteger.ONE)).mod(m).equals(BigInteger.ZERO)) || (p.subtract(BigInteger.ONE)).mod(m).equals(BigInteger.ZERO)) {
											JOptionPane jopSetup = new JOptionPane("Choose another m. m has to be prime, must divide p+1 and not divide p-1.", JOptionPane.ERROR_MESSAGE); 
											JDialog diagSetup = jopSetup.createDialog(jopSetup,"Error");
											diagSetup.setLocation(this.getLocation().x+320,this.getLocation().y+60);
											diagSetup.setVisible(true);
											p = null; 
											txtInfo.setText("N/A");
											lblInfo.setText("N/A");
										}
										else {
											if (!P.onCurve(m)) {
												JOptionPane jopSetup = new JOptionPane("Choose another P with <P> = E(IF_p)[m].", JOptionPane.ERROR_MESSAGE); 
												JDialog diagSetup = jopSetup.createDialog(jopSetup,"Error");
												diagSetup.setLocation(this.getLocation().x+320,this.getLocation().y+60);
												diagSetup.setVisible(true);
												p = null; 
												txtInfo.setText("N/A");
												lblInfo.setText("N/A");
											}
											else {
												if (s.equals(BigInteger.ZERO)) {
													JOptionPane jopSetup = new JOptionPane("Choose another s which is not congruent 0 modulo m.", JOptionPane.ERROR_MESSAGE); 
													JDialog diagSetup = jopSetup.createDialog(jopSetup,"Error");
													diagSetup.setLocation(this.getLocation().x+320,this.getLocation().y+60);
													diagSetup.setVisible(true);
													p = null; 
													txtInfo.setText("N/A");
													lblInfo.setText("N/A");
												}
												else {
													if (r.equals(BigInteger.ZERO)) {
														JOptionPane jopSetup = new JOptionPane("Choose another r which is not congruent 0 modulo m.", JOptionPane.ERROR_MESSAGE); 
														JDialog diagSetup = jopSetup.createDialog(jopSetup,"Error");
														diagSetup.setLocation(this.getLocation().x+320,this.getLocation().y+60);
														diagSetup.setVisible(true);
														p = null; 
														txtInfo.setText("N/A");
														lblInfo.setText("N/A");
													}
												}
											}
										}
									}
								}
							}
						}
						else {
							Ppub = P.multiplyECPoint(s);
						}
					} 
					}
					catch (FileNotFoundException e) {       
						JOptionPane jopSetup = new JOptionPane("Choose a valid datapath.", JOptionPane.ERROR_MESSAGE); 
						JDialog diagSetup = jopSetup.createDialog(jopSetup,"Error");
						diagSetup.setLocation(this.getLocation().x+320,this.getLocation().y+60);
						diagSetup.setVisible(true);
						p = null; 
						txtInfo.setText("N/A");
						lblInfo.setText("N/A");
					}
				}
			}	
		}
		
		if (cbSetup.getSelectedItem().equals(setupStrings[0]) || cbSetup.getSelectedItem().equals(setupStrings[1])
				|| cbSetup.getSelectedItem().equals(setupStrings[2]) || cbSetup.getSelectedItem().equals(setupStrings[3])
				|| cbSetup.getSelectedItem().equals(setupStrings[4]) || cbSetup.getSelectedItem().equals(setupStrings[5])
				|| cbSetup.getSelectedItem().equals(setupStrings[6]) || cbSetup.getSelectedItem().equals(setupStrings[7])
				|| cbSetup.getSelectedItem().equals(setupStrings[8]) || cbSetup.getSelectedItem().equals(setupStrings[9])
				|| cbSetup.getSelectedItem().equals(setupStrings[10]) || cbSetup.getSelectedItem().equals(setupStrings[11])
				|| cbSetup.getSelectedItem().equals(setupStrings[12])) {
			
			if (p == null) {
				txtInfo.setText("N/A");
				lblInfo.setText("N/A");
			}
			else {
				if(cbSetup.getSelectedItem().equals(setupStrings[0])) {
					txtInfo.setText(p.toString());
					lblInfo.setText("<HTML><BODY>Elliptic curve E: y\u00B2 = x\u00B3+ax+b over IF_p with <BR>p \u2261 3 (mod 4)</BODY></HTML>");
					
				}
				
				if(cbSetup.getSelectedItem().equals(setupStrings[1])) {
					txtInfo.setText(a.toString()); 
					lblInfo.setText("Elliptic curve E: y\u00B2 = x\u00B3+ax+b over IF_p");
				}
				
				if(cbSetup.getSelectedItem().equals(setupStrings[2])) {
					txtInfo.setText(b.toString()); 
					lblInfo.setText("Elliptic curve E: y\u00B2 = x\u00B3+ax+b over IF_p");
				}
				
				if(cbSetup.getSelectedItem().equals(setupStrings[3])) {
					txtInfo.setText(m.toString()); 
					lblInfo.setText("m prime with m\u2223p+1, m\u2224p-1, m\u2223p\u00B2-1");
				}
				
				if(cbSetup.getSelectedItem().equals(setupStrings[4])) {
					txtInfo.setText("E(IF_" + p.toString() + "\u00B2)[" + m.toString() + "]");
					lblInfo.setText("<HTML><BODY>Set of m-torsion points (subgroup of E(IF_p\u00B2))</BODY></HTML>");
				}
				
				if(cbSetup.getSelectedItem().equals(setupStrings[5])) {
					txtInfo.setText("(IF_" + p.toString() + "\u00B2)*");
					lblInfo.setText("Multiplicative group of the finite field IF_p\u00B2");
				}
				
				if(cbSetup.getSelectedItem().equals(setupStrings[6])) {
					txtInfo.setText("(" + P.getX().get(0) + "," + P.getY().get(0) + ")");
					lblInfo.setText("Elliptic curve point in E(IF_p) with <P> = E(IF_p)[m]");
				}
				
				if(cbSetup.getSelectedItem().equals(setupStrings[7])) {
					txtInfo.setText(s.toString());
					lblInfo.setText("Master key in (IF_m)*");
				}
				
				if(cbSetup.getSelectedItem().equals(setupStrings[8])) {
					txtInfo.setText("(" + P.multiplyECPoint(s).getX().get(0) + "," + P.multiplyECPoint(s).getY().get(0) + ")");
					lblInfo.setText("P_pub = s•P \u220A E(IF_p)[m]*");
				}
				
				if(cbSetup.getSelectedItem().equals(setupStrings[9])) {
					txtInfo.setText("vide infra");
					lblInfo.setText("<HTML><BODY>Modified Weil-pairing <BR>e': E[m] x E[m] \u2192 \u03BC_m</BODY></HTML>");
				}
				
				if(cbSetup.getSelectedItem().equals(setupStrings[10])) {
					txtInfo.setText("vide infra");
					lblInfo.setText("<HTML><BODY>Modified Tate-pairing <BR>\u03C4': E(IF_p\u00B2)[m] x E(IF_p\u00B2)/mE(IF_p\u00B2) \n \u2192 \u03BC_m</BODY></HTML>");
				}
				
				if(cbSetup.getSelectedItem().equals(setupStrings[11])) {
					txtInfo.setText("vide infra");
					lblInfo.setText("<HTML><BODY>Hash function <BR>H\u2081: {0,1}* \u2192 E(IF_p\u00B2)[m]</BODY></HTML>");
				}
				
				if(cbSetup.getSelectedItem().equals(setupStrings[12])) {
					txtInfo.setText("vide infra");
					lblInfo.setText("<HTML><BODY>Hash function <BR>H\u2082: {0,1}* x E(IF_p\u00B2)[m] \u2192 \u2124_m</BODY></HTML>");
				}
			}
		}

		if(setup.isShowing()){
			if (rbWeil.isSelected() || rbTate.isSelected()) {
				// VERIFY
				txthV.setText("N/A");
				txtQV.setText("N/A");
				txtT.setText("N/A");
				txtg.setText("N/A");
				txtgg.setText("N/A");
				txtQutput.setForeground(Color.black);
				txtQutput.setText("………");
			}
		}
		
		if (rbWeil.isSelected()) {
			lblg.setText("e'(P,V)");
			lblgg.setText("e'(P_pub,T)");
		}
		
		if (rbTate.isSelected()) {
			lblg.setText("\u03C4'(P,V)");
			lblgg.setText("\u03C4'(P_pub,T)");
		}
		
		// EXTRACT 
		if(rbExtract1.isSelected()) {
			if (messageExtract.equals("binary")) {
				Converter.BinaryToAscii(txtPubk);
				messageExtract = "ascii";
			}
			
			txtPubk.setEnabled(true);
		}
		
		if(rbExtract2.isSelected()) {
			if (messageExtract.equals("ascii")) {
				Converter.AsciiToBinary(txtPubk);
				messageExtract = "binary";
			}
			
			txtPubk.setEnabled(false);
		}
		
		if (event.getSource() == bnExtract) {
			if (p == null) { 
				JOptionPane jopSetup3 = new JOptionPane("Step back and press \"Setup\".", JOptionPane.ERROR_MESSAGE); 
				JDialog diagSetup3 = jopSetup3.createDialog(jopSetup3,"Error");
				diagSetup3.setLocation(this.getLocation().x+320,this.getLocation().y+60);
				diagSetup3.setVisible(true);
			}
			
			if (txtPubk.getText().length() == 0 && !(p == null)) {
				JOptionPane jopExtract = new JOptionPane("Choose a public-key.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagExtract = jopExtract.createDialog(jopExtract,"Error");
				diagExtract.setLocation(this.getLocation().x+320,this.getLocation().y+60);
				diagExtract.setVisible(true);
			}
			
			if (txtPubk.getText().length() != 0 && !(p == null)) { 
				ECPoint QE = HashFunction.hash1(txtPubk,messageExtract,P,m);
				Prik = QE.multiplyECPoint(s);
				if (QE.getNeutral()) {
					txtQE.setText("neutral element");
					txtPrik.setText("neutral element");
				}
				else {
					txtQE.setText("(" + QE.getX().get(0) + "," + QE.getY().get(0) + ")");
					txtPrik.setText(s + "•" + "(" + QE.getX().get(0) + "," + QE.getY().get(0) + ")" + " = " + "(" + Prik.getX().get(0) + "," + Prik.getY().get(0) + ")");
				}
			}
			
			// SIGN
			txtQS.setText("N/A"); 
			txtr.setText("N/A");
			txtU.setText("N/A");
			txthS.setText("N/A");
			txtV.setText("N/A");
			txtSignatureS.setText("N/A");
			// VERIFY
			txtSignatureV.setText("");
			txthV.setText("N/A");
			txtQV.setText("N/A");
			txtT.setText("N/A");
			txtg.setText("N/A");
			txtgg.setText("N/A");
			txtQutput.setForeground(Color.black);
			txtQutput.setText("………");	
		}
		
		if (event.getSource() == bnQEPress) {
			if (txtQE.getText().equals("N/A")) {
				JOptionPane jopQEPress = new JOptionPane("No data available.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagQEPress = jopQEPress.createDialog(jopQEPress,"Error");
				diagQEPress.setLocation(this.getLocation().x+300,this.getLocation().y+180);
				diagQEPress.setVisible(true);
			}
			else {
				HashDialog hash1Diag = new HashDialog(this,txtPubk,messageExtract,m,P);
				hash1Diag.setVisible(true);
			}
		}
		
		// SIGN 
		if(rbSign1.isSelected()) {
			if (messageSign.equals("binary")) {
				Converter.BinaryToAscii(txtMessageS);
				messageSign = "ascii";
			}
			
			txtMessageS.setEnabled(true);
		}
		
		if(rbSign2.isSelected()) {
			if (messageSign.equals("ascii")) {
				Converter.AsciiToBinary(txtMessageS);
				messageSign = "binary";
			}
			
			txtMessageS.setEnabled(false);
		}
		
		if (event.getSource() == bnSign) {
			if (p == null) {
				JOptionPane jopSetup3 = new JOptionPane("Step back and press \"Setup\".", JOptionPane.ERROR_MESSAGE); 
				JDialog diagSetup3 = jopSetup3.createDialog(jopSetup3,"Error");
				diagSetup3.setLocation(this.getLocation().x+320,this.getLocation().y+60);
				diagSetup3.setVisible(true);
			}
			
			if (!(p == null) && txtPrik.getText().equals("N/A")) { 
				JOptionPane jopExtract2 = new JOptionPane("Step back to \"Extract\" and create your private-key.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagExtract2 = jopExtract2.createDialog(jopExtract2,"Error");
				diagExtract2.setLocation(this.getLocation().x+320,this.getLocation().y+60);
				diagExtract2.setVisible(true);
			}
			
			if (!(p == null) && !(txtPrik.getText().equals("N/A")) && txtMessageS.getText().length() == 0) { 
				JOptionPane jopSign = new JOptionPane("Choose a message to sign.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagSign = jopSign.createDialog(jopSign,"Error");
				diagSign.setLocation(this.getLocation().x+320,this.getLocation().y+60);
				diagSign.setVisible(true);		
			}
			
			if (!(p == null) && !(txtPrik.getText().equals("N/A")) && !(txtMessageS.getText().length() == 0)) { 
				txtMessageV.setText(txtMessageS.getText());
				messageVerify = messageSign;
				if (messageVerify.equals("ascii")) {
					rbVerify1.setSelected(true);
				}
				else rbVerify2.setSelected(true);
				
				// VERIFY
				txthV.setText("N/A");
				txtQV.setText("N/A");
				txtT.setText("N/A");
				txtg.setText("N/A");
				txtgg.setText("N/A");
				txtQutput.setForeground(Color.black);
				txtQutput.setText("………");
			
				ECPoint QS = HashFunction.hash1(txtPubk, messageExtract,P,m);
				U = QS.multiplyECPoint(r);
				BigInteger h = HashFunction.hash2(txtMessageS, messageSign,U,m);
				V = Prik.multiplyECPoint(r.add(h).mod(m));
				txtr.setText(r.toString());
				txthS.setText(h.toString());
				
				if (QS.getNeutral()) {
					txtQS.setText("neutral element");
					txtU.setText("neutral element");
					txtV.setText("neutral element");
					txtSignatureS.setText("(neutral element, neutral element)");
					txtSignatureV.setText("(neutral element, neutral element)");
				}
				else {
					txtQS.setText("(" + QS.getX().get(0) + "," + QS.getY().get(0) + ")");
					txtU.setText(r + "•" + "(" + QS.getX().get(0) + "," + QS.getY().get(0) + ")" + " = " + "(" + U.getX().get(0) + "," + U.getY().get(0) + ")");
					
					if (V.getNeutral()) {
						txtV.setText("neutral element");
						txtSignatureS.setText("<" + "(" + U.getX().get(0) + "," + U.getY().get(0) + ")" + "," + "neutral element)" + ">");
						txtSignatureV.setText("<" + "(" + U.getX().get(0) + "," + U.getY().get(0) + ")" + "," + "neutral element)" + ">");
					}
					else {
						txtV.setText("(" + r + "+" + h + " mod " + m + ")•" + "(" + Prik.getX().get(0) + "," + Prik.getY().get(0) + ") = (" + V.getX().get(0) + "," + V.getY().get(0) + ")");
						txtSignatureS.setText("<" + "(" + U.getX().get(0) + "," + U.getY().get(0) + ")" + "," + "(" + V.getX().get(0) + "," + V.getY().get(0) + ")" + ">");
						txtSignatureV.setText("<" + "(" + U.getX().get(0) + "," + U.getY().get(0) + ")" + "," + "(" + V.getX().get(0) + "," + V.getY().get(0) + ")" + ">");
					}
				}
			}
		}
		
		if (event.getSource() == bnQSPress) {
			if (txtQS.getText().equals("N/A")) {
				JOptionPane jopQEPress = new JOptionPane("No data available.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagQEPress = jopQEPress.createDialog(jopQEPress,"Error");
				diagQEPress.setLocation(this.getLocation().x+300,this.getLocation().y+180);
				diagQEPress.setVisible(true);
			}
			else {
				HashDialog hash1Diag = new HashDialog(this,txtPubk,messageExtract,m,P);
				hash1Diag.setVisible(true);
			}
		}
		
		if (event.getSource() == bnhSPress) {
			if (txthS.getText().equals("N/A")) {
				JOptionPane jopQEPress = new JOptionPane("No data available.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagQEPress = jopQEPress.createDialog(jopQEPress,"Error");
				diagQEPress.setLocation(this.getLocation().x+300,this.getLocation().y+180);
				diagQEPress.setVisible(true);
			}
			else {
				HashDialog hash2Diag = new HashDialog(this,txtMessageS,messageSign,U,m);
				hash2Diag.setVisible(true);
			}
		}

		// VERIFY 
		if(rbVerify1.isSelected()) {
			if (messageVerify.equals("binary")) {
				Converter.BinaryToAscii(txtMessageV);
				messageVerify = "ascii";
			}
			
			txtMessageV.setEnabled(true);
		}
		
		if(rbVerify2.isSelected()) {
			if (messageVerify.equals("ascii")) {
				Converter.AsciiToBinary(txtMessageV);
				messageVerify = "binary";
			}
			
			txtMessageV.setEnabled(false);
		}
		
		if (event.getSource() == bnVerify) {
			if (p == null) {
				JOptionPane jopSetup3 = new JOptionPane("Step back and press \"Setup\".", JOptionPane.ERROR_MESSAGE); 
				JDialog diagSetup3 = jopSetup3.createDialog(jopSetup3,"Error");
				diagSetup3.setLocation(this.getLocation().x+320,this.getLocation().y+60);
				diagSetup3.setVisible(true);
			}
			
			if (!(p == null) && txtPrik.getText().equals("N/A")) { 
				JOptionPane jopExtract2 = new JOptionPane("Step back to \"Extract\" and create your private-key.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagExtract2 = jopExtract2.createDialog(jopExtract2,"Error");
				diagExtract2.setLocation(this.getLocation().x+320,this.getLocation().y+60);
				diagExtract2.setVisible(true);
			}
			
			if (!(p == null) && !(txtPrik.getText().equals("N/A")) && txtSignatureS.getText().equals("N/A")) { 
				JOptionPane jopSign2 = new JOptionPane("Step back to \"Sign\" and sign a message.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagSign2 = jopSign2.createDialog(jopSign2,"Error");
				diagSign2.setLocation(this.getLocation().x+320,this.getLocation().y+60);
				diagSign2.setVisible(true);	
			}
			
			if (!(p == null) && !(txtPrik.getText().equals("N/A")) && !(txtSignatureS.getText().equals("N/A")) && txtMessageV.getText().length() == 0) { 
				JOptionPane jopSign3 = new JOptionPane("Add a message to verify.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagSign3 = jopSign3.createDialog(jopSign3,"Error");
				diagSign3.setLocation(this.getLocation().x+320,this.getLocation().y+60);
				diagSign3.setVisible(true);		
			}
			
			if (!(p == null) && !(txtPrik.getText().equals("N/A")) && !(txtSignatureS.getText().equals("N/A")) && !(txtMessageV.getText().length() == 0)) { 
				// VERIFY
				txthV.setText("N/A");
				txtQV.setText("N/A");
				txtT.setText("N/A");
				txtg.setText("N/A");
				txtgg.setText("N/A");
				txtQutput.setForeground(Color.black);
				txtQutput.setText("………");
				
				BigInteger h = HashFunction.hash2(txtMessageV, messageVerify,U,m);
				ECPoint QV = HashFunction.hash1(txtPubk, messageExtract,P,m);
				T = U.addECPoint(QV.multiplyECPoint(h));
				txthV.setText(h.toString());
				
				if (QV.getNeutral()) {
					txtQV.setText("neutral element");	
				}
				else {
					txtQV.setText("(" + QV.getX().get(0) + "," + QV.getY().get(0) + ")");
				}
				
				if (T.getNeutral()) {
					txtT.setText("neutral element");
				}
				else {
					if (U.getNeutral()) {
						txtT.setText(h.toString() + "•(" + QV.getX().get(0) + "," + QV.getY().get(0) + ") = (" + T.getX().get(0) + "," + T.getY().get(0) + ")");
					}
					else {
						txtT.setText("(" + U.getX().get(0) + "," + U.getY().get(0) + ")" + "+" + h.toString() + "•(" + QV.getX().get(0) + "," + QV.getY().get(0) + ") = (" + T.getX().get(0) + "," + T.getY().get(0) + ")");				
					}
				}
				
				// Weil-pairing  
				if (rbWeil.isSelected()) {
					S1 = ECPoint.randomECPoint(p,a,b);
					
					while (S1.getNeutral() || S1.negateECPoint().getNeutral() || S1.multiplyECPoint(m).getNeutral() || S1.negateECPoint().multiplyECPoint(m).getNeutral() || S1.equals(S1.negateECPoint()) || P.addECPoint(S1.negateECPoint()).equals(S1) || V.distortion().addECPoint(S1).equals(S1.negateECPoint()) || V.distortion().addECPoint(S1).equals(P.addECPoint(S1.negateECPoint())) || V.distortion().addECPoint(S1).getNeutral() || P.addECPoint(S1.negateECPoint()).getNeutral()) {		
						S1 = ECPoint.randomECPoint(p,a,b);
					}
							
					FiniteFieldEl g = Pairing.weil(P,V,S1,p,a,m);
					
					if(V.getNeutral()) {
						txtg.setText("e'((" + P.getX().get(0) + "," + P.getY().get(0) + ")" + ", neutral element) = " + g.get(0) + "+" + g.get(1) + "X");
					}
					else {
						txtg.setText("e'((" + P.getX().get(0) + "," + P.getY().get(0) + ")" + ",(" + V.getX().get(0) + "," + V.getY().get(0) + ")) = " + g.get(0) + "+" + g.get(1) + "X");
					}
					
					S2 = ECPoint.randomECPoint(p,a,b);
					
					while (S2.getNeutral() || S2.negateECPoint().getNeutral() || S2.multiplyECPoint(m).getNeutral() || S2.negateECPoint().multiplyECPoint(m).getNeutral() || S2.equals(S2.negateECPoint()) || Ppub.addECPoint(S2.negateECPoint()).equals(S2) || T.distortion().addECPoint(S2).equals(S2.negateECPoint()) || T.distortion().addECPoint(S2).equals(Ppub.addECPoint(S2.negateECPoint())) || T.distortion().addECPoint(S2).getNeutral() || Ppub.addECPoint(S1.negateECPoint()).getNeutral()) {		
						S2 = ECPoint.randomECPoint(p,a,b);
					}
					
					FiniteFieldEl gg = Pairing.weil(Ppub,T,S2,p,a,m);
					
					if(T.getNeutral()) {
						txtgg.setText("e'((" + Ppub.getX().get(0) + "," + Ppub.getY().get(0) + ")" + ", neutral element) = " + gg.get(0) + "+" + gg.get(1) + "X");
					}
					else {
						txtgg.setText("e'((" + Ppub.getX().get(0) + "," + Ppub.getY().get(0) + ")" + ",(" + T.getX().get(0) + "," + T.getY().get(0) + ")) = " + gg.get(0) + "+" + gg.get(1) + "X");
					}
						
					boolean equal = g.equals(gg);
					String result;
					
					if (equal) {
						result = "   valid";
						txtQutput.setForeground(new Color(0,128,0));
					}
					else { 
						result = " invalid";
						txtQutput.setForeground(Color.red);
					}
					
					txtQutput.setText(result);
				}
				// Tate-pairing
				else {
					S1 = ECPoint.randomECPoint(p,a,b);
					
					while (S1.getNeutral() || S1.multiplyECPoint(m).getNeutral() || S1.equals(P) || V.distortion().addECPoint(S1).equals(P) || V.distortion().addECPoint(S1).getNeutral()) {		
						S1 = ECPoint.randomECPoint(p,a,b);
					}
					
					FiniteFieldEl g = Pairing.tate(P,V,S1,p,a,m);
					
					if(V.getNeutral()) {
						txtg.setText("\u03C4'((" + P.getX().get(0) + "," + P.getY().get(0) + ")" + ", neutral element) = " + g.get(0) + "+" + g.get(1) + "X");
					}
					else {
						txtg.setText("\u03C4'((" + P.getX().get(0) + "," + P.getY().get(0) + ")" + ",(" + V.getX().get(0) + "," + V.getY().get(0) + ")) = " + g.get(0) + "+" + g.get(1) + "X");
					}
					
					S2 = ECPoint.randomECPoint(p,a,b);
					
					while (S2.getNeutral() || S2.multiplyECPoint(m).getNeutral() || S2.equals(Ppub) || T.distortion().addECPoint(S2).equals(Ppub) || T.distortion().addECPoint(S2).getNeutral()) {		
						S2 = ECPoint.randomECPoint(p,a,b);
					}
					
					FiniteFieldEl gg = Pairing.tate(Ppub,T,S2,p,a,m);
					
					if(T.getNeutral()) {
						txtgg.setText("\u03C4'((" + Ppub.getX().get(0) + "," + Ppub.getY().get(0) + ")" + ", neutral element) = " + gg.get(0) + "+" + gg.get(1) + "X");
					}
					else {
						txtgg.setText("\u03C4'((" + Ppub.getX().get(0) + "," + Ppub.getY().get(0) + ")" + ",(" + T.getX().get(0) + "," + T.getY().get(0) + ")) = " + gg.get(0) + "+" + gg.get(1) + "X");
					}
					
					boolean equal = g.equals(gg);
					String result;
					
					if (equal) {
						result = "   valid";
						txtQutput.setForeground(new Color(0,128,0));
					}
					else { 
						result = " invalid";
						txtQutput.setForeground(Color.red);
					}
					
					txtQutput.setText(result);
				}
			}
		}
		
		if (event.getSource() == bnhVPress) {
			if (txthV.getText().equals("N/A")) {
				JOptionPane jopQEPress = new JOptionPane("No data available.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagQEPress = jopQEPress.createDialog(jopQEPress,"Error");
				diagQEPress.setLocation(this.getLocation().x+300,this.getLocation().y+180);
				diagQEPress.setVisible(true);
			}
			else {
				HashDialog hash2Diag = new HashDialog(this,txtMessageV,messageVerify,U,m);
				hash2Diag.setVisible(true);
			}
		}
		
		if (event.getSource() == bnQVPress) {
			if (txtQV.getText().equals("N/A")) {
				JOptionPane jopQEPress = new JOptionPane("No data available.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagQEPress = jopQEPress.createDialog(jopQEPress,"Error");
				diagQEPress.setLocation(this.getLocation().x+300,this.getLocation().y+180);
				diagQEPress.setVisible(true);
			}
			else {
				HashDialog hash1Diag = new HashDialog(this,txtPubk,messageExtract,m,P);
				hash1Diag.setVisible(true);
			}
		}
		
		if (event.getSource() == bngPress) {
			if (txtg.getText().equals("N/A")) {
				JOptionPane jopQEPress = new JOptionPane("No data available.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagQEPress = jopQEPress.createDialog(jopQEPress,"Error");
				diagQEPress.setLocation(this.getLocation().x+300,this.getLocation().y+180);
				diagQEPress.setVisible(true);
			}
			else {
				if (rbTate.isSelected()) {
					PairingDialog pairing1Diag = new PairingDialog(this,P,V,S1,p,a,m);
					pairing1Diag.setVisible(true);
				}
				else {
					if (rbWeil.isSelected()) {
						PairingDialog pairing1Diag = new PairingDialog(this,p,a,m,P,V,S1);
						pairing1Diag.setVisible(true);
					}
				}
			}
		}
		
		if (event.getSource() == bnggPress) {
			if (txtgg.getText().equals("N/A")) {
				JOptionPane jopQEPress = new JOptionPane("No data available.", JOptionPane.ERROR_MESSAGE); 
				JDialog diagQEPress = jopQEPress.createDialog(jopQEPress,"Error");
				diagQEPress.setLocation(this.getLocation().x+300,this.getLocation().y+180);
				diagQEPress.setVisible(true);
			}
			else {
				if (rbTate.isSelected()) {
					PairingDialog pairing1Diag = new PairingDialog(this,Ppub,T,S2,p,a,m);
					pairing1Diag.setVisible(true);
				}
				else {
					if (rbWeil.isSelected()) {
						PairingDialog pairing1Diag = new PairingDialog(this,p,a,m,Ppub,T,S2);
						pairing1Diag.setVisible(true);
					}
				}
			}
		}
	}

	public BigInteger randomPrime(int bit) {  

		BigInteger m = new BigInteger(bit, new Random());	
		int mBit = m.bitLength();
		
		while (mBit-bit < 0) {
			m = new BigInteger(bit, new Random());
			mBit = m.bitLength();
		}
		
		while (!m.isProbablePrime(15)) {
			m = m.add(BigInteger.ONE);
		}
		
		return m;
	}
	
	public BigInteger[] randomSpecialPrime(int bit) {  // p+1 = 4*m mit p=3(mod 4) and m not divide p-1
		
		BigInteger[] arrayOutput = new BigInteger[2];
		BigInteger m = randomPrime(bit); 
		BigInteger p = (m.multiply(new BigInteger("4"))).subtract(BigInteger.ONE);
		
		while ((!p.isProbablePrime(15)) || (!p.mod(new BigInteger("4")).equals(new BigInteger("3"))) || ((p.subtract(BigInteger.ONE)).mod(m)).equals(BigInteger.ZERO)) {
			m = randomPrime(bit);
			p = (m.multiply(new BigInteger("4"))).subtract(BigInteger.ONE);
		}
		
		arrayOutput[0] = m;
		arrayOutput[1] = p;
		
		return arrayOutput;
	}

	public static void main(String[] args) {
		
		FrameI frame = new FrameI();
		frame.setLocation(100,100);
		frame.setSize(704,400);
		frame.setVisible(true);      
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       }
}