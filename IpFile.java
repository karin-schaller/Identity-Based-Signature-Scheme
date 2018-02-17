import java.math.BigInteger;
import java.io.*;

public class IpFile {
	
	private RandomAccessFile ipf;
	
	public IpFile(String filename) {
	
		try {ipf = new RandomAccessFile(filename,"r");} catch(IOException e){}
	}
	
	public BigInteger get(char param) {

		char[] ipfArray = {'p','a','b','m','x','y','s','r'};
		String line= "";
		
		for (int i=0; i<8; i++) {
			try {line = ipf.readLine();} catch(IOException e){};
			
			if (param==ipfArray[i]) {
				try {ipf.seek(0);} catch(IOException e){};
				
				return new BigInteger(line);
			}
		}
		
		return null;
	}
}