import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.BigInteger;
import java.awt.Color;
import javax.swing.text.*;  

public class HashDialog extends Dialog implements ActionListener, WindowListener {

	private static final long serialVersionUID = 83677265;
	private BigInteger m;
	private ECPoint P;
	private ECPoint U;
	
	JTextPane txtHash1 = new JTextPane();
	JTextArea txtBinary1 = new JTextArea();
	JTextArea txtBinary2 = new JTextArea();
	BigInteger BigInteger1;
	BigInteger BigInteger2;
	JTextPane txtHash2 = new JTextPane();
	String s1;
	String s2;
	String s3;
	String s4;
	String s5;
	String s6;
	JLabel lblHash1 = new JLabel("Step 1 of 6");
	String[] sHashArray1 = {"Step 1 of 6","Step 2 of 6","Step 3 of 6","Step 4 of 6","Step 5 of 6","Step 6 of 6"};
	int counter1 = 1;
	JButton bnBeg = new JButton(new ImageIcon("Images\\beg.png"));
	JButton bnBack = new JButton(new ImageIcon("Images\\back.png"));
	JButton bnEnd = new JButton(new ImageIcon("Images\\end.png"));
	JButton bnForw = new JButton(new ImageIcon("Images\\forw.png"));
	
	JTextPane txtHash3 = new JTextPane();
	JTextArea txtBinary3 = new JTextArea();
	JTextArea txtBinary4 = new JTextArea();
	BigInteger BigInteger3;
	BigInteger BigInteger4;
	JTextPane txtHash4 = new JTextPane();
	String s7;
	String s8;
	String s9;
	String s10;
	String s11;
	JLabel lblHash2 = new JLabel("Step 1 of 7");
	String[] sHashArray2 = {"Step 1 of 7","Step 2 of 7","Step 3 of 7","Step 4 of 7","Step 5 of 7","Step 6 of 7","Step 7 of 7"};
	int counter2 = 1;
	JButton bnBeg2 = new JButton(new ImageIcon("Images\\beg.png"));
	JButton bnBack2 = new JButton(new ImageIcon("Images\\back.png"));
	JButton bnEnd2 = new JButton(new ImageIcon("Images\\end.png"));
	JButton bnForw2 = new JButton(new ImageIcon("Images\\forw.png"));
	
	// Hashfunction 1 
	public HashDialog(JFrame owner, JTextArea txtPubk, String messageExtract, BigInteger m2, ECPoint P2) {
		
		super(owner, "Hash function H\u2081", true);
		setResizable(false);
		Point location = owner.getLocation();
		setSize(342,244);
		setLocation(location.x+550, location.y+225);
		setLayout(null);
		addWindowListener(this);
		
		m = m2;
		P = P2;
		
		txtBinary1.setText(txtPubk.getText());
		
		if (messageExtract.equals("ascii")) {
			Converter.AsciiToBinary(txtBinary1);
		}
		
		Converter.BinaryToBinaryWO(txtBinary1);

		if (txtBinary1.getText().length() > m.bitLength()*2) {
			txtBinary2.setText(txtBinary1.getText().substring(txtBinary1.getText().length() - m.bitLength()*2));
		}
		else {
			txtBinary2.setText(txtBinary1.getText());
		}
		
		BigInteger1 = Converter.BinaryToBigInteger(txtBinary2);
		BigInteger2 = Converter.BinaryToBigInteger(txtBinary2).mod(m);
		
		s1 = "Binary representation of your public-key pubk with a blue marked area of length " + m.bitLength()*2 + " = 2 * bitlength of m = 2*" + m.bitLength() + ".";
		s2 = "The last " +  m.bitLength()*2 + " = 2 * bitlength of m = 2*" + m.bitLength() + " bits of the binary representation of your public-key pubk.";
		s3 = "Decimal representation of the given binary representation modulo m.";
		s4 = "Result of " + Converter.BinaryToBigInteger(txtBinary2) + " (mod " + m +").";
		s5 = "Result of the modulo-calculation multiplied by P.";
		s6 = "Result of " + BigInteger2 + "•(" + P.getX().get(0) + "," + P.getY().get(0) + ") and H\u2081(pubk).";
		
		// Layout
		JScrollPane spTxtHash1 = new JScrollPane(txtHash1);
		add(spTxtHash1);
		spTxtHash1.setBounds(11,33,320,105);
		txtHash1.setEditable(false);
		txtHash1.setBackground(new Color(238,238,238));
		txtHash1.setText(txtBinary1.getText());
		StyledDocument sd1 = txtHash1.getStyledDocument();
		MutableAttributeSet ma1 = new SimpleAttributeSet();
		StyleConstants.setBackground(ma1,new Color(204,218,231));
		
		if (txtBinary1.getText().length() > m.bitLength()*2) {
			sd1.setCharacterAttributes(txtBinary1.getText().length() - m.bitLength()*2,txtBinary1.getText().length(),ma1,false);
		}
		else {
			sd1.setCharacterAttributes(0,txtBinary1.getText().length(),ma1,false);
		}
		
		JScrollPane spTxtHash2 = new JScrollPane(txtHash2);
		add(spTxtHash2);
		spTxtHash2.setBounds(11,148,320,45);
		txtHash2.setEditable(false);
		txtHash2.setBackground(new Color(238,238,238));
		txtHash2.setText(s1);
		
		add(bnBeg);
		bnBeg.setBounds(11,208,40,25);
		bnBeg.addActionListener(this);
		bnBeg.setFocusable(false);
		
		add(bnBack);
		bnBack.setBounds(75,208,40,25);
		bnBack.addActionListener(this);
		bnBack.setFocusable(false);
		
		add(lblHash1);
		lblHash1.setBounds(141,208,60,25);
		
		add(bnForw);
		bnForw.setBounds(227,208,40,25);
		bnForw.addActionListener(this);
		bnForw.setFocusable(false);
		
		add(bnEnd);
		bnEnd.setBounds(291,208,40,25);
		bnEnd.addActionListener(this);
		bnEnd.setFocusable(false);
	}
	
	// Hashfunction 2
	public HashDialog(JFrame owner, JTextArea txtMessage, String messageSign, ECPoint U2, BigInteger m2) {
		
		super(owner, "Hash function H\u2082", true);
		setResizable(false);
		Point location = owner.getLocation();
		setSize(430,259);
		setLocation(location.x+550, location.y+225);
		setLayout(null);
		addWindowListener(this);
		
		m = m2;
		U = U2;
		
		txtBinary1.setText(txtMessage.getText());
		
		if (messageSign.equals("ascii")) {
			Converter.AsciiToBinary(txtBinary1);
		}
		
		Converter.BinaryToBinaryWO(txtBinary1);

		if (txtBinary1.getText().length() > m.bitLength()*2) {
			txtBinary2.setText(txtBinary1.getText().substring(txtBinary1.getText().length() - m.bitLength()*2));
		}
		else {
			txtBinary2.setText(txtBinary1.getText());
		}
		
		BigInteger1 = Converter.BinaryToBigInteger(txtBinary2);
		BigInteger2 = Converter.BinaryToBigInteger(txtBinary2).mod(m);
		
		s1 = "Binary representation of your message M with a blue marked area of length " + m.bitLength()*2 + " = 2 * bitlength of m = 2*" + m.bitLength() + ".";
		s2 = "The last " +  m.bitLength()*2 + " = 2 * bitlength of m = 2*" + m.bitLength() + " bits of the binary representation of your message M.";
		s3 = "Decimal representation of the given binary representation modulo m.";
		s4 = "Result of " + Converter.BinaryToBigInteger(txtBinary2) + " (mod " + m +").";
		
		txtBinary3.setText(Converter.BigIntegerToBinary(U.getX().get(0)) + Converter.BigIntegerToBinary(U.getY().get(0)));

		if (txtBinary3.getText().length() > m.bitLength()*2) {
			txtBinary4.setText(txtBinary3.getText().substring(txtBinary3.getText().length() - m.bitLength()*2));
		}
		else {
			txtBinary4.setText(txtBinary3.getText());
		}
		
		BigInteger3 = Converter.BinaryToBigInteger(txtBinary4);
		BigInteger4 = Converter.BinaryToBigInteger(txtBinary4).mod(m);
		
		s5 = "Concatenated binary representations of the x-coordinate of U (=" + U.getX().get(0) + ") and the y-coordinate of U (=" + U.getY().get(0) + ") with a blue marked area of length " + m.bitLength()*2 + " = 2 * bitlength of m = 2*" + m.bitLength() + ".";
		s6 = "The last " +  m.bitLength()*2 + " = 2 * bitlength of m = 2*" + m.bitLength() + " bits of the concatenated binary representation of U.";
		s7 = "Decimal representation of the given binary representation modulo m.";
		s8 = "Result of " + Converter.BinaryToBigInteger(txtBinary4) + " (mod " + m +").";
		s9 = "Addition of the two results of step 4 modulo m.";
		s10 = "Result of " + BigInteger2 + "+" + BigInteger4 + " (mod " + m +").";
		s11 = "Result of " + BigInteger2.add(BigInteger4) + " (mod " + m +") and H\u2082(M,U).";
		
		// Layout
		JScrollPane spTxtHash1 = new JScrollPane(txtHash1);
		add(spTxtHash1);
		spTxtHash1.setBounds(11,33,200,105); 
		txtHash1.setEditable(false);
		txtHash1.setBackground(new Color(238,238,238));
		txtHash1.setText(txtBinary1.getText());
		StyledDocument sd1 = txtHash1.getStyledDocument();
		MutableAttributeSet ma1 = new SimpleAttributeSet();
		StyleConstants.setBackground(ma1,new Color(204,218,231));
		
		if (txtBinary1.getText().length() > m.bitLength()*2) {
			sd1.setCharacterAttributes(txtBinary1.getText().length() - m.bitLength()*2, txtBinary1.getText().length(), ma1, false);
		}
		else {
			sd1.setCharacterAttributes(0, txtBinary1.getText().length(), ma1, false);
		}
		
		JScrollPane spTxtHash2 = new JScrollPane(txtHash2);
		add(spTxtHash2);
		spTxtHash2.setBounds(11,148,200,60); 
		txtHash2.setEditable(false);
		txtHash2.setBackground(new Color(238,238,238));
		txtHash2.setText(s1);
		
		JScrollPane spTxtHash3 = new JScrollPane(txtHash3);
		add(spTxtHash3);
		spTxtHash3.setBounds(219,33,200,105);
		txtHash3.setEditable(false);
		txtHash3.setBackground(new Color(238,238,238));
		txtHash3.setText(txtBinary3.getText());
		StyledDocument sd2 = txtHash3.getStyledDocument();
		MutableAttributeSet ma2 = new SimpleAttributeSet();
	    StyleConstants.setBackground(ma2,new Color(238,238,238));
	    sd2.setCharacterAttributes(0,txtHash3.getText().length(),ma2,false);
		StyledDocument sd3 = txtHash3.getStyledDocument();
		MutableAttributeSet ma3 = new SimpleAttributeSet();
		StyleConstants.setBackground(ma3,new Color(204,218,231));
		
		if (txtBinary3.getText().length() > m.bitLength()*2) {
			sd3.setCharacterAttributes(txtBinary3.getText().length() - m.bitLength()*2,txtBinary3.getText().length(),ma3,false);
		}
		else {
			sd3.setCharacterAttributes(0,txtBinary3.getText().length(), ma3, false);
		}
		
		JScrollPane spTxtHash4 = new JScrollPane(txtHash4);
		add(spTxtHash4);
		spTxtHash4.setBounds(219,148,200,60);
		txtHash4.setEditable(false);
		txtHash4.setBackground(new Color(238,238,238));
		txtHash4.setText(s5);
		
		add(bnBeg2);
		bnBeg2.setBounds(11,223,50,25);
		bnBeg2.addActionListener(this);
		bnBeg2.setFocusable(false);
		
		add(bnBack2);
		bnBack2.setBounds(98,223,50,25);
		bnBack2.addActionListener(this);
		bnBack2.setFocusable(false);
		
		add(lblHash2);
		lblHash2.setBounds(186,223,60,25);
		
		add(bnForw2);
		bnForw2.setBounds(282,223,50,25);
		bnForw2.addActionListener(this);
		bnForw2.setFocusable(false);
		
		add(bnEnd2);
		bnEnd2.setBounds(369,223,50,25);
		bnEnd2.addActionListener(this);	
		bnEnd2.setFocusable(false);
	}
	
	public void actionPerformed(ActionEvent event) {
		
		// Hashfunction 1 
		if (event.getSource() == bnBeg) {
			counter1 = 1;
			lblHash1.setText(sHashArray1[counter1-1]);
		}
		
		if (event.getSource() == bnBack) {
			if (counter1 > 1) {
				counter1 = counter1 - 1;
			}
			lblHash1.setText(sHashArray1[counter1-1]);
		}
		
		if (event.getSource() == bnForw) {
			if (counter1 < 6) {
				counter1 = counter1 + 1;
			}
			lblHash1.setText(sHashArray1[counter1-1]);
		}
		
		if (event.getSource() == bnEnd) {
			counter1 = 6; 
			lblHash1.setText(sHashArray1[counter1-1]);
		}
		
		if (event.getSource() == bnBeg || event.getSource() == bnBack || event.getSource() == bnForw || event.getSource() == bnEnd) {
			if (counter1 == 1) {
				txtHash1.setText(txtBinary1.getText());
				StyledDocument sd1 = txtHash1.getStyledDocument();
				MutableAttributeSet ma1 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma1,new Color(238,238,238));
				sd1.setCharacterAttributes(0, txtHash1.getText().length(), ma1, false);
				StyledDocument sd2 = txtHash1.getStyledDocument();
				MutableAttributeSet ma2 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma2,new Color(204,218,231));
				
				if (txtBinary1.getText().length() > m.bitLength()*2) {
					sd2.setCharacterAttributes(txtBinary1.getText().length() - m.bitLength()*2,txtBinary1.getText().length(),ma2,false);
				}
				else {
					sd2.setCharacterAttributes(0,txtBinary1.getText().length(),ma2,false);
				}
				
				txtHash2.setText(s1);
			}
			
			if (counter1 == 2) {
				txtHash1.setText(txtBinary2.getText());
				StyledDocument sd1 = txtHash1.getStyledDocument();
				MutableAttributeSet ma1 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma1,new Color(204,218,231));
				sd1.setCharacterAttributes(0,txtBinary2.getText().length(),ma1,false);
				
				txtHash2.setText(s2);
			}
			
			if (counter1 == 3) {
				txtHash1.setText(BigInteger1 + " (mod " + m + ")");
				StyledDocument sd1 = txtHash1.getStyledDocument();
				MutableAttributeSet ma1 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma1,new Color(238,238,238));
				sd1.setCharacterAttributes(0,txtHash1.getText().length(),ma1,false);
				
				txtHash2.setText(s3);
			}
			
			if (counter1 == 4) {
				txtHash1.setText(BigInteger2.toString());
				StyledDocument sd1 = txtHash1.getStyledDocument();
				MutableAttributeSet ma1 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma1,new Color(238,238,238));
				sd1.setCharacterAttributes(0,txtHash1.getText().length(),ma1,false);
				
				txtHash2.setText(s4);
			}
			
			if (counter1 == 5) {
				txtHash1.setText(BigInteger2 + "•" + "(" + P.getX().get(0) + ", " + P.getY().get(0) + ")");
				StyledDocument sd1 = txtHash1.getStyledDocument();
				MutableAttributeSet ma1 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma1,new Color(238,238,238));
				sd1.setCharacterAttributes(0,txtHash1.getText().length(),ma1,false);
				
				txtHash2.setText(s5);
			}
			
			if (counter1 == 6) {
				if (BigInteger2.equals(BigInteger.ZERO)) {
					txtHash1.setText("neutral element");
				}
				else {
					txtHash1.setText("(" + P.multiplyECPoint(BigInteger2).getX().get(0) + "," + P.multiplyECPoint(BigInteger2).getY().get(0) + ")"); 
				}
				
				StyledDocument sd1 = txtHash1.getStyledDocument();
				MutableAttributeSet ma1 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma1,new Color(238,238,238));
				sd1.setCharacterAttributes(0,txtHash1.getText().length(),ma1,false);
		    
				txtHash2.setText(s6);
			}
		}
		
		// Hashfunction 2
		if (event.getSource() == bnBeg2) {
			counter2 = 1;
			lblHash2.setText(sHashArray2[counter2-1]);
		}
		
		if (event.getSource() == bnBack2) {
			if (counter2 > 1) {
				counter2 = counter2 - 1;
			}
			lblHash2.setText(sHashArray2[counter2-1]);
		}
		
		if (event.getSource() == bnForw2) {
			if (counter2 < 7) {
				counter2 = counter2 + 1;
			}
			lblHash2.setText(sHashArray2[counter2-1]);
		}
		
		if (event.getSource() == bnEnd2) {
			counter2 = 7; 
			lblHash2.setText(sHashArray2[counter2-1]);
		}

		if (event.getSource() == bnBeg2 || event.getSource() == bnBack2 || event.getSource() == bnForw2 || event.getSource() == bnEnd2) {
			if (counter2 == 1) {
				txtHash1.setText(txtBinary1.getText());
				StyledDocument sd1 = txtHash1.getStyledDocument();
				MutableAttributeSet ma1 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma1,new Color(238,238,238));
				sd1.setCharacterAttributes(0, txtHash1.getText().length(), ma1, false);
				StyledDocument sd2 = txtHash1.getStyledDocument();
				MutableAttributeSet ma2 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma2,new Color(204,218,231));
				
				if (txtBinary1.getText().length() > m.bitLength()*2) {
					sd2.setCharacterAttributes(txtBinary1.getText().length() - m.bitLength()*2,txtBinary1.getText().length(),ma2,false);
				}
				else {
					sd2.setCharacterAttributes(0,txtBinary1.getText().length(),ma2,false);
				}
			
				txtHash2.setText(s1);
			
				txtHash3.setText(txtBinary3.getText());
				StyledDocument sd3 = txtHash3.getStyledDocument();
				MutableAttributeSet ma3 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma3,new Color(238,238,238));
				sd3.setCharacterAttributes(0, txtHash3.getText().length(), ma3, false);
				StyledDocument sd4 = txtHash3.getStyledDocument();
				MutableAttributeSet ma4 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma4,new Color(204,218,231));
				
				if (txtBinary3.getText().length() > m.bitLength()*2) {
					sd4.setCharacterAttributes(txtBinary3.getText().length() - m.bitLength()*2,txtBinary3.getText().length(),ma4,false);
				}
				else {
					sd4.setCharacterAttributes(0,txtBinary3.getText().length(),ma4,false);
				}
				
				txtHash4.setText(s5);
			}
			
			if (counter2 == 2) {
				txtHash1.setText(txtBinary2.getText());
				StyledDocument sd1 = txtHash1.getStyledDocument();
				MutableAttributeSet ma1 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma1,new Color(204,218,231));
				sd1.setCharacterAttributes(0,txtBinary2.getText().length(),ma1,false);
		    
				txtHash2.setText(s2);
		    
				txtHash3.setText(txtBinary4.getText());
				StyledDocument sd2 = txtHash3.getStyledDocument();
				MutableAttributeSet ma2 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma2,new Color(204,218,231));
				sd2.setCharacterAttributes(0,txtBinary4.getText().length(),ma2,false);
				
				txtHash4.setText(s6);
			}
			
			if (counter2 == 3) {
				txtHash1.setText(BigInteger1 + " (mod " + m + ")");
				StyledDocument sd1 = txtHash1.getStyledDocument();
				MutableAttributeSet ma1 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma1,new Color(238,238,238));
				sd1.setCharacterAttributes(0,txtHash1.getText().length(),ma1,false);
		  	 
				txtHash2.setText(s3);
		    
				txtHash3.setText(BigInteger3 + " (mod " + m + ")");
				StyledDocument sd2 = txtHash3.getStyledDocument();
				MutableAttributeSet ma2 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma2,new Color(238,238,238));
				sd2.setCharacterAttributes(0,txtHash3.getText().length(),ma2,false);
				
				txtHash4.setText(s7);
			}
			
			if (counter2 == 4) {
				txtHash1.setText(BigInteger2.toString());
				StyledDocument sd1 = txtHash1.getStyledDocument();
				MutableAttributeSet ma1 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma1,new Color(238,238,238));
				sd1.setCharacterAttributes(0,txtHash1.getText().length(),ma1,false);
		    
				txtHash2.setText(s4);
		    
				txtHash3.setText(BigInteger4.toString());
				StyledDocument sd2 = txtHash3.getStyledDocument();
				MutableAttributeSet ma2 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma2,new Color(238,238,238));
				sd2.setCharacterAttributes(0,txtHash3.getText().length(),ma2,false);
		    
				txtHash4.setText(s8);
			}
			
			if (counter2 == 5) {
				txtHash1.setText(BigInteger2 + "+" + BigInteger4 + " (mod " + m + ")");
				StyledDocument sd1 = txtHash1.getStyledDocument();
				MutableAttributeSet ma1 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma1,new Color(238,238,238));
				sd1.setCharacterAttributes(0,txtHash1.getText().length(),ma1,false);
		    
				txtHash2.setText(s9);
		    
				txtHash3.setText("");
				StyledDocument sd2 = txtHash3.getStyledDocument();
				MutableAttributeSet ma2 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma2,new Color(238,238,238));
				sd2.setCharacterAttributes(0,txtHash3.getText().length(),ma2,false);
		    
				txtHash4.setText("");
			}
			
			if (counter2 == 6) {
				txtHash1.setText(BigInteger2.add(BigInteger4) + " (mod " + m + ")");
				StyledDocument sd1 = txtHash1.getStyledDocument();
				MutableAttributeSet ma1 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma1,new Color(238,238,238));
				sd1.setCharacterAttributes(0,txtHash1.getText().length(),ma1,false);
		    
				txtHash2.setText(s10);
		    
				txtHash3.setText("");
				StyledDocument sd2 = txtHash3.getStyledDocument();
				MutableAttributeSet ma2 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma2,new Color(238,238,238));
				sd2.setCharacterAttributes(0,txtHash3.getText().length(),ma2,false);
		    
				txtHash4.setText("");
			}
			
			if (counter2 == 7) {
				txtHash1.setText(BigInteger2.add(BigInteger4).mod(m).toString());
				StyledDocument sd1 = txtHash1.getStyledDocument();
				MutableAttributeSet ma1 = new SimpleAttributeSet();
				StyleConstants.setBackground(ma1,new Color(238,238,238));
				sd1.setCharacterAttributes(0,txtHash1.getText().length(),ma1,false);
			    
			    txtHash2.setText(s11);
		    
			    txtHash3.setText("");
			    StyledDocument sd2 = txtHash3.getStyledDocument();
			    MutableAttributeSet ma2 = new SimpleAttributeSet();
			    StyleConstants.setBackground(ma2,new Color(238,238,238));
			    sd2.setCharacterAttributes(0, txtHash3.getText().length(),ma2,false);
		    
			    txtHash4.setText("");
			}
		}
	}

	public void windowActivated(WindowEvent e) {}
	
	public void windowClosed(WindowEvent e) {}
	
	public void  windowClosing(WindowEvent e) {
		
		setVisible(false);
		dispose();
	}
	
	public void windowDeactivated(WindowEvent e) {}
	
	public void windowDeiconified(WindowEvent e) {}
	
	public void windowIconified(WindowEvent e) {}
	
	public void windowOpened(WindowEvent e) {}
}