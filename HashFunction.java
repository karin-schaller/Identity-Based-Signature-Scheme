import javax.swing.*;
import java.math.BigInteger;

public class HashFunction {
	
	public static BigInteger hashHelper(JTextArea txt, BigInteger m) {
		
		int txtLength = txt.getText().length();
		int mLength = m.bitLength();
		int boarder = 2*mLength;
		
		if (txtLength>boarder) {
			txt.setText(txt.getText().substring(txtLength-boarder,txtLength));
		}
		
		BigInteger scalar = Converter.BinaryToBigInteger(txt).mod(m);

		return scalar;
	}
	
	public static ECPoint hash1(JTextArea txt, String mode, ECPoint P, BigInteger m) { 
		
		JTextArea txtNew = new JTextArea();
		txtNew.setText(txt.getText());
		
		if (mode.equals("ascii")) {
			Converter.AsciiToBinaryWO(txtNew);
		}
		
		if ((mode.equals("binary")) && (txtNew.getText().substring(8,9).equals(" "))) {
			Converter.BinaryToBinaryWO(txtNew);
		}
		
		return P.multiplyECPoint(hashHelper(txtNew,m));
	}
	
	public static BigInteger hash2(JTextArea txt, String mode, ECPoint point, BigInteger m) {
		
		JTextArea txtFirst = new JTextArea();
		txtFirst.setText(txt.getText());
		
		if (mode.equals("ascii")) {
			Converter.AsciiToBinaryWO(txtFirst);
		}
		
		if ((mode.equals("binary")) && (txtFirst.getText().substring(8,9).equals(" "))) {
			Converter.BinaryToBinaryWO(txtFirst);
		}
		
		BigInteger helperFirst = HashFunction.hashHelper(txtFirst,m);
		
		JTextArea txtSecond = new JTextArea();
		String sXY = Converter.BigIntegerToBinary(point.getX().get(0)) + Converter.BigIntegerToBinary(point.getY().get(0));
		txtSecond.setText(sXY);
		BigInteger helperSecond = HashFunction.hashHelper(txtSecond,m); 

		return helperFirst.add(helperSecond).mod(m);
	}
}