import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.BigInteger;
import java.awt.Color;

public class PairingDialog extends Dialog implements ActionListener, WindowListener {
	
	private static final long serialVersionUID = 76766982;
	
	// Tate-pairing
	JTextPane txtPairing1 = new JTextPane();
	JTextPane txtPairing2 = new JTextPane();
	String sTate1;
	String sTate2;
	String sTate3;
	String sTate4;
	BigInteger e;
	FiniteFieldEl fP_QS;
	FiniteFieldEl fP_S;
	FiniteFieldEl f;
	FiniteFieldEl result;
	String s1;
	String s2;
	String s3;
	String s4;
	String s5;
	String s6;
	String s7;
	JLabel lblPairing1 = new JLabel("Step 1 of 7");
	String[] sPairingArray1 = {"Step 1 of 7","Step 2 of 7","Step 3 of 7","Step 4 of 7","Step 5 of 7","Step 6 of 7","Step 7 of 7"};
	int counter1 = 1;
	JButton bnBeg = new JButton(new ImageIcon("Images\\beg.png"));
	JButton bnBack = new JButton(new ImageIcon("Images\\back.png"));
	JButton bnEnd = new JButton(new ImageIcon("Images\\end.png"));
	JButton bnForw = new JButton(new ImageIcon("Images\\forw.png"));
	
	// Weil-pairing
	String sWeil1;
	String sWeil2;
	String sWeil3;
	String sWeil4;
	String sWeil5;
	String sWeil6;
	FiniteFieldEl f1;
	FiniteFieldEl f2;
	FiniteFieldEl f12;
	FiniteFieldEl f3;
	FiniteFieldEl f4;
	FiniteFieldEl f43;
	FiniteFieldEl f5;
	FiniteFieldEl f6;
	FiniteFieldEl f56;
	FiniteFieldEl f7;
	FiniteFieldEl f8;
	FiniteFieldEl f87;
	FiniteFieldEl n1;
	FiniteFieldEl n2;
	FiniteFieldEl result2;
	String s8;
	String s9;
	String s10;
	String s11;
	String s12;
	String s13;
	JLabel lblPairing2 = new JLabel("Step 1 of 6");
	String[] sPairingArray2 = {"Step 1 of 6","Step 2 of 6","Step 3 of 6","Step 4 of 6","Step 5 of 6","Step 6 of 6"};
	int counter2 = 1;
	JButton bnBeg2 = new JButton(new ImageIcon("Images\\beg.png"));
	JButton bnBack2 = new JButton(new ImageIcon("Images\\back.png"));
	JButton bnEnd2 = new JButton(new ImageIcon("Images\\end.png"));
	JButton bnForw2 = new JButton(new ImageIcon("Images\\forw.png"));
	
	// Tate-pairing
	public PairingDialog(JFrame owner, ECPoint P, ECPoint Q, ECPoint S, BigInteger p, BigInteger a, BigInteger m) {
		
		super(owner, "Modified Tate-pairing \u03C4'", true);
		setResizable(false);
		Point location = owner.getLocation();
		setSize(342,244);
		setLocation(location.x+550, location.y+225);
		setLayout(null);
		addWindowListener(this);
		
		ECPoint dist = Q.distortion();
		sTate1 = "\u03C4'((" + P.getX().get(0) + "," + P.getY().get(0) + "),(" + Q.getX().get(0) + "," + Q.getY().get(0) + "))";
		sTate2 = "\u03C4((" + P.getX().get(0) + "," + P.getY().get(0) + "),(" + dist.getX().get(0) + "," + dist.getY().get(1) + "X))";
		sTate3 = "\u03C4((" + P.getX().get(0) + "," + P.getY().get(0) + "),(" + dist.getX().get(0) + "," + dist.getY().get(1) + "X))^((" + p + "\u00B2 -1)/" + m + ")";
		e = (p.multiply(p).subtract(BigInteger.ONE)).divide(m);
		sTate4 = "(f_(" + P.getX().get(0) + "," + P.getY().get(0) + ")((" + dist.addECPoint(S).getX().get(0) + "+" + dist.addECPoint(S).getX().get(1) + "X," + dist.addECPoint(S).getY().get(0) + "+" + dist.addECPoint(S).getY().get(1) + "X)) / f_(" + P.getX().get(0) + "," + P.getY().get(0) + ")((" + S.getX().get(0) + "," + S.getY().get(0) + ")))^" + e.toString();

		fP_QS = Miller.miller(P,dist.addECPoint(S),m,a);
		fP_S = Miller.miller(P,S,m,a);
		f = fP_QS.multiply(fP_S.inverse());
		result = f.squareMultiply(e);
		
		s1 = "The modified Tate-pairing \u03C4': E(IF_" + p.toString() + "\u00B2)[" + m.toString() + "] x E(IF_" + p.toString() + "\u00B2)/" + m.toString() + "E(IF_" + p.toString() + "\u00B2) \u2192 \u03BC_" + m.toString() + ".";
		s2 = "The reduced Tate-pairing with a distortion map \u03D5: E \u2192 E : (x,y) \u21A6 (-x,yX) applied to the second argument.";
		s3 = "The Tate-pairing to the power of (p\u00B2-1)/m.";
		s4 = "Formula to compute the Tate-pairing using rational functions on E with special divisors.";
		s5 = "Values of the rational functions on E at the given points using Miller's Algorithm.";
		s6 = "Result of (" + fP_QS.get(0).toString() + "+" + fP_QS.get(1).toString() + "X / " + fP_S.get(0).toString() + "+" + fP_S.get(1).toString() + "X)^" + e.toString() + " calculated in IF_" + p.toString() + "\u00B2.";
		s7 = "Result of the modified Tate-pairing in \u03BC_" + m.toString() + "\u2286 (IF_" + p.toString() + "\u00B2)*.";
		
		// Layout
		JScrollPane spTxtPairing1 = new JScrollPane(txtPairing1);
		add(spTxtPairing1);
		spTxtPairing1.setBounds(11,33,320,105);
		txtPairing1.setEditable(false);
		txtPairing1.setBackground(new Color(238,238,238));
		txtPairing1.setText(sTate1);
		
		JScrollPane spTxtPairing2 = new JScrollPane(txtPairing2);
		add(spTxtPairing2);
		spTxtPairing2.setBounds(11,148,320,45); 
		txtPairing2.setEditable(false);
		txtPairing2.setBackground(new Color(238,238,238));
		txtPairing2.setText(s1);
		
		add(bnBeg);
		bnBeg.setBounds(11,208,40,25);
		bnBeg.addActionListener(this);
		bnBeg.setFocusable(false);
		
		add(bnBack);
		bnBack.setBounds(75,208,40,25);
		bnBack.addActionListener(this);
		bnBack.setFocusable(false);
		
		add(lblPairing1);
		lblPairing1.setBounds(141,208,60,25);
		
		add(bnForw);
		bnForw.setBounds(227,208,40,25);
		bnForw.addActionListener(this);
		bnForw.setFocusable(false);
		
		add(bnEnd);
		bnEnd.setBounds(291,208,40,25);
		bnEnd.addActionListener(this);
		bnEnd.setFocusable(false);
	}
	
	// Weil-pairing
	public PairingDialog(JFrame owner, BigInteger p, BigInteger a, BigInteger m, ECPoint P, ECPoint Q, ECPoint S) {
		
		super(owner, "Modified Weil-pairing e'", true);
		setResizable(false);
		Point location = owner.getLocation();
		setSize(342,244);
		setLocation(location.x+550, location.y+225);
		setLayout(null);
		addWindowListener(this);
		
		ECPoint dist = Q.distortion();
		sWeil1 = "e'((" + P.getX().get(0) + "," + P.getY().get(0) + "),(" + Q.getX().get(0) + "," + Q.getY().get(0) + "))";
		sWeil2 = "e((" + P.getX().get(0) + "," + P.getY().get(0) + "),(" + dist.getX().get(0) + "," + dist.getY().get(1) + "X))";
		sWeil3 = "(f_(" + P.addECPoint(S.negateECPoint()).getX().get(0) + "," + P.addECPoint(S.negateECPoint()).getY().get(0) + ")((" + dist.addECPoint(S).getX().get(0) + "+" + dist.addECPoint(S).getX().get(1) + "X," + dist.addECPoint(S).getY().get(0) + "+" + dist.addECPoint(S).getY().get(1) + "X)) / f_(" + P.addECPoint(S.negateECPoint()).getX().get(0) + "," + P.addECPoint(S.negateECPoint()).getY().get(0) + ")((" + S.getX().get(0) + "," + S.getY().get(0) + "))) * " +
					"(f_(" + S.negateECPoint().getX().get(0) + "," + S.negateECPoint().getY().get(0) + ")((" + S.getX().get(0) + "," + S.getY().get(0) + ")) / f_(" + S.negateECPoint().getX().get(0) + "," + S.negateECPoint().getY().get(0) + ")((" + dist.addECPoint(S).getX().get(0) + "+" + dist.addECPoint(S).getX().get(1) + "X," + dist.addECPoint(S).getY().get(0) + "+" + dist.addECPoint(S).getY().get(1) + "X))) * " +
					"(f_(" + S.getX().get(0) + "," + S.getY().get(0) + ")((" + P.addECPoint(S.negateECPoint()).getX().get(0) + "," + P.addECPoint(S.negateECPoint()).getY().get(0) + ")) / f_(" + S.getX().get(0) + "," + S.getY().get(0) + ")((" + S.negateECPoint().getX().get(0) + "," + S.negateECPoint().getY().get(0) + "))) * " +
					"(f_(" + dist.addECPoint(S).getX().get(0) + "+" + dist.addECPoint(S).getX().get(1) + "X," + dist.addECPoint(S).getY().get(0) + "+" + dist.addECPoint(S).getY().get(1) + "X)((" + S.negateECPoint().getX().get(0) + "," + S.negateECPoint().getY().get(0) + ")) / f_(" + dist.addECPoint(S).getX().get(0) + "+" + dist.addECPoint(S).getX().get(1) + "X," + dist.addECPoint(S).getY().get(0) + "+" + dist.addECPoint(S).getY().get(1) + "X)((" + P.addECPoint(S.negateECPoint()).getX().get(0) + "," + P.addECPoint(S.negateECPoint()).getY().get(0) + ")))";
						
		// f_(P-S)(...)
		f1 = Miller.miller(P.addECPoint(S.negateECPoint()),dist.addECPoint(S),m,a);
		f2 = Miller.miller(P.addECPoint(S.negateECPoint()),S,m,a);
		f12 = f1.multiply(f2.inverse());
		
		// f_(-S)(..)
		f3 = Miller.miller(S.negateECPoint(),dist.addECPoint(S),m,a);
		f4 = Miller.miller(S.negateECPoint(),S,m,a);
		f43 = f4.multiply(f3.inverse());
		
		// f_S(...)
		f5 = Miller.miller(S,P.addECPoint(S.negateECPoint()),m,a);
		f6 = Miller.miller(S,S.negateECPoint(),m,a);
		f56 = f5.multiply(f6.inverse());
		
		// f_(Q+S)(...)
		f7 = Miller.miller(dist.addECPoint(S),P.addECPoint(S.negateECPoint()),m,a);
		f8 = Miller.miller(dist.addECPoint(S),S.negateECPoint(),m,a);
		f87 = f8.multiply(f7.inverse());
		
		n1 = f12.multiply(f43);
		n2 = f56.multiply(f87);
		result2 =  n1.multiply(n2);
		
		sWeil4 = "((" + f1.get(0).toString() + "+" + f1.get(1).toString() + "X) / (" + f2.get(0).toString() + "+" + f2.get(1).toString() + "X)) * ((" + f4.get(0).toString() + "+" + f4.get(1).toString() + "X) / (" + f3.get(0).toString() + "+" + f3.get(1).toString() + "X)) * ((" + f5.get(0).toString() + "+" + f5.get(1).toString() + "X) / (" + f6.get(0).toString() + "+" + f6.get(1).toString() + "X)) * ((" + f8.get(0).toString() + "+" + f8.get(1).toString() + "X) / (" + f7.get(0).toString() + "+" + f7.get(1).toString() + "X))";
		sWeil5 = "(" + n1.get(0).toString() + "+" + n1.get(1).toString() + "X) * (" + n2.get(0).toString() + "+" + n2.get(1).toString() + "X)";
		sWeil6 = result2.get(0).toString() + "+" + result2.get(1).toString() + "X";
		
		s8 = "The modified Weil-pairing e': E[" + m.toString() + "] x E[" + m.toString() + "] \u2192 \u03BC_" + m.toString() + ".";
		s9 = "The Weil-pairing with a distortion map \u03D5: E \u2192 E : (x,y) \u21A6 (-x,yX) applied to the second argument.";
		s10 = "Formula to compute the Weil-pairing using rational functions on E with special divisors.";
		s11 = "Values of the rational functions on E at the given points using Miller's Algorithm.";
		s12 = "Results of (" + f12.get(0).toString() + "+" + f12.get(1).toString() + "X) * (" + f43.get(0).toString() + "+" + f43.get(1).toString() + "X) and (" + f56.get(0).toString() + "+" + f56.get(1).toString() + "X) * (" + f87.get(0).toString() + "+" + f87.get(1).toString() + "X)" + " calculated in IF_" + p.toString() + "\u00B2.";
		s13 = "Result of the modified Weil-pairing in \u03BC_" + m.toString() + "\u2286 (IF_" + p.toString() + "\u00B2)*.";
		
		// Layout
		JScrollPane spTxtPairing1 = new JScrollPane(txtPairing1);
		add(spTxtPairing1);
		spTxtPairing1.setBounds(11,33,320,105);
		txtPairing1.setEditable(false);
		txtPairing1.setBackground(new Color(238,238,238));
		txtPairing1.setText(sWeil1);
		
		JScrollPane spTxtPairing2 = new JScrollPane(txtPairing2);
		add(spTxtPairing2);
		spTxtPairing2.setBounds(11,148,320,45); 
		txtPairing2.setEditable(false);
		txtPairing2.setBackground(new Color(238,238,238));
		txtPairing2.setText(s8);
		
		add(bnBeg2);
		bnBeg2.setBounds(11,208,40,25);
		bnBeg2.addActionListener(this);
		bnBeg2.setFocusable(false);
		
		add(bnBack2);
		bnBack2.setBounds(75,208,40,25);
		bnBack2.addActionListener(this);
		bnBack2.setFocusable(false);
		
		add(lblPairing2);
		lblPairing2.setBounds(141,208,60,25);
		
		add(bnForw2);
		bnForw2.setBounds(227,208,40,25);
		bnForw2.addActionListener(this);
		bnForw2.setFocusable(false);
		
		add(bnEnd2);
		bnEnd2.setBounds(291,208,40,25);
		bnEnd2.addActionListener(this);
		bnEnd2.setFocusable(false);
	}
	
	public void actionPerformed(ActionEvent event) {
		
		// Tate-pairing
		if (event.getSource() == bnBeg) {
			counter1 = 1;
			lblPairing1.setText(sPairingArray1[counter1-1]);
		}
		
		if (event.getSource() == bnBack) {
			if (counter1 > 1) {
				counter1 = counter1 - 1;
			}
			lblPairing1.setText(sPairingArray1[counter1-1]);
		}
		
		if (event.getSource() == bnForw) {
			if (counter1 < 7) {
				counter1 = counter1 + 1;
			}
			lblPairing1.setText(sPairingArray1[counter1-1]);
		}
		
		if (event.getSource() == bnEnd) {
			counter1 = 7; 
			lblPairing1.setText(sPairingArray1[counter1-1]);
		}
		
		if (event.getSource() == bnBeg || event.getSource() == bnBack || event.getSource() == bnForw || event.getSource() == bnEnd) {
			if (counter1 == 1) {
				txtPairing1.setText(sTate1);
				
				txtPairing2.setText(s1);
			}
			if (counter1 == 2) {
				txtPairing1.setText(sTate2);
				
			    txtPairing2.setText(s2);
			}
			
			if (counter1 == 3) {
				txtPairing1.setText(sTate3);
				
			    txtPairing2.setText(s3);
			}

			if (counter1 == 4) {
				txtPairing1.setText(sTate4);
				
			    txtPairing2.setText(s4);
			}

			if (counter1 == 5) {
				txtPairing1.setText("((" + fP_QS.get(0).toString() + "+" + fP_QS.get(1).toString() + "X) / (" + fP_S.get(0).toString() + "+" + fP_S.get(1).toString() + "X))^" + e.toString());
				
			    txtPairing2.setText(s5);
			}

			if (counter1 == 6) {
				txtPairing1.setText("(" + f.get(0).toString() + "+" + f.get(1).toString() + "X)^" + e.toString());
				
			    txtPairing2.setText(s6);
			}
			
			if (counter1 == 7) {
				txtPairing1.setText(result.get(0).toString() + "+" + result.get(1).toString() + "X");
				
			    txtPairing2.setText(s7);
			}
		}
		
		// Weil-pairing
		if (event.getSource() == bnBeg2) {
			counter2 = 1;
			lblPairing2.setText(sPairingArray2[counter2-1]);
		}
		
		if (event.getSource() == bnBack2) {
			if (counter2 > 1) {
				counter2 = counter2 - 1;
			}
			lblPairing2.setText(sPairingArray2[counter2-1]);
		}
		
		if (event.getSource() == bnForw2) {
			if (counter2 < 6) {
				counter2 = counter2 + 1;
			}
			lblPairing2.setText(sPairingArray2[counter2-1]);
		}
		
		if (event.getSource() == bnEnd2) {
			counter2 = 6; 
			lblPairing2.setText(sPairingArray2[counter2-1]);
		}
		
		if (event.getSource() == bnBeg2 || event.getSource() == bnBack2 || event.getSource() == bnForw2 || event.getSource() == bnEnd2) {
			if (counter2 == 1) {
				txtPairing1.setText(sWeil1);
				
				txtPairing2.setText(s8);
			}
			
			if (counter2 == 2) {
				txtPairing1.setText(sWeil2);
				
			    txtPairing2.setText(s9);
			}
			
			if (counter2 == 3) {
				txtPairing1.setText(sWeil3);
				
			    txtPairing2.setText(s10);
			}

			if (counter2 == 4) {
				txtPairing1.setText(sWeil4);
				
			    txtPairing2.setText(s11);
			}

			if (counter2 == 5) {
				txtPairing1.setText(sWeil5);
				
			    txtPairing2.setText(s12);
			}

			if (counter2 == 6) {
				txtPairing1.setText(sWeil6);
				
			    txtPairing2.setText(s13);
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