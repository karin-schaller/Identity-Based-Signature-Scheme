import javax.swing.JTextArea;
import java.math.BigInteger;

public class Converter {

	public static void AsciiToBinary(JTextArea txt) {
		
			String sOutput = "";
			
			for(int i=0; i<txt.getText().length(); i++) {
				String sBinary = Integer.toBinaryString((int)txt.getText().charAt(i));
				String sHelper = "";
				
				for (int j=1; j<=8-sBinary.length(); j++) {
						sHelper += "0";
				}
				
				sOutput += sHelper + sBinary + " ";
			}
			
			txt.setText(sOutput);
	}
	
	public static void BinaryToAscii(JTextArea txt) {
		
		int length = txt.getText().length();
		String sOutput = "";
		String sFirst = "";
		String sRest = txt.getText();
		
		for (int i=1; i<=(length/9); i++) {
			sFirst = sRest.substring(0,8);
			int helper = 0;
			
			for (int j=0; j<8; j++) {
				if (sFirst.substring(j,j+1).equals("1")) {
					helper += Math.pow(2,7-j);
				}
			}
			
			sOutput += (char) helper;
			sRest = sRest.substring(9,sRest.length());
		}
		
		txt.setText(sOutput);	
	}
	
	public static void AsciiToBinaryWO(JTextArea txt) { // WO = without " "
		
		String sOutput = "";
		
		for(int i=0; i<txt.getText().length(); i++) {
			String sBinary = Integer.toBinaryString((int)txt.getText().charAt(i));
			String sHelper = "";
			
			for (int j=1; j<=8-sBinary.length(); j++) {
					sHelper += "0";
			}
			
			sOutput += sHelper + sBinary;
		}
		
		txt.setText(sOutput);
	}
	
	public static void BinaryToBinaryWO(JTextArea txt) { // WO = without " "
		
		int length = txt.getText().length();
		String sOutput = "";
		String sRest = txt.getText();
		
		for (int i=1; i<=(length/9); i++) {
			sOutput += sRest.substring(0,8);
			sRest = sRest.substring(9,sRest.length());
		}
		
		sOutput += sRest;
		txt.setText(sOutput);
	}
	
	public static BigInteger BinaryToBigInteger(JTextArea txt) {
		
		return new BigInteger(txt.getText(),2);
	}
	
	public static String BigIntegerToBinary(BigInteger b) {
	
		return b.toString(2);
	}
}