import java.math.BigInteger;
import java.lang.Math;

// IF_{p^2}
public class FiniteFieldEl {

	private BigInteger p; // p=3(mod 4)
	public BigInteger[] array; 
	
	public FiniteFieldEl(BigInteger[] array) {
		
		p = FrameI.p;
		this.array = array;
	}
	
	public BigInteger get(int i) {
		
		return array[i];
	}
	
	public void set(int i, BigInteger value) {
		
		array[i] = value;
	}
	
	public int degree() {
		
		for(int i=array.length-1; i>=0; i--) {
			if (array[i] != BigInteger.ZERO) {
				return i;
			}
		}
		
		return 0;
	}
	
	public boolean equals(FiniteFieldEl y) {
		
		return ((this.get(0).equals(y.get(0))) && (this.get(1).equals(y.get(1))));
	}
	
	public FiniteFieldEl add(FiniteFieldEl y) {
		
		int lengthx = this.array.length;
		int lengthy = y.array.length;
		int max = Math.max(lengthx,lengthy);
		int min = Math.min(lengthx,lengthy);
		FiniteFieldEl output = new FiniteFieldEl(new BigInteger[max]);
		
		for (int i=0; i<min; i++) {
			output.set(i,(this.get(i).mod(p).add(y.get(i).mod(p))).mod(p));
		}
		
		for (int i = min; i<max; i++) {
			if (lengthx < lengthy) {
				output.set(i,y.get(i).mod(p));
			}
			else {
				output.set(i,this.get(i).mod(p));
			}
		}
		
		return output;
	}
	
	public FiniteFieldEl negate() {

		FiniteFieldEl output = new FiniteFieldEl(new BigInteger[] {BigInteger.ZERO,BigInteger.ZERO});
		output.set(0,this.get(0).negate().mod(p));
		output.set(1,this.get(1).negate().mod(p));
		
		return output;
	}
		
	public FiniteFieldEl multiply(FiniteFieldEl y) {
		
		int lengthx = this.array.length;
		int lengthy = y.array.length;
		int length = lengthx+lengthy;
		FiniteFieldEl outputHelper = new FiniteFieldEl(new BigInteger[length-1]);
		FiniteFieldEl output = new FiniteFieldEl(new BigInteger[lengthx]);
		
		for (int i=0; i<outputHelper.array.length; i++) {
			outputHelper.set(i,BigInteger.ZERO);
		}
		
		FiniteFieldEl f = new FiniteFieldEl(new BigInteger[] {BigInteger.ONE,BigInteger.ZERO,BigInteger.ONE});
		
		for (int i=0; i<lengthx; i++) {
			for (int j=0; j<lengthy; j++) {
				BigInteger helper = this.get(i).multiply(y.get(j)).mod(p);
				outputHelper.set(i+j,outputHelper.get(i+j).add(helper).mod(p));
			}
		}
		
		if(outputHelper.degree() >= f.degree()) { 
			BigInteger a = outputHelper.get(2);
			BigInteger b = outputHelper.get(1);
			BigInteger c = outputHelper.get(0);
			outputHelper.set(0,c.add(a.negate()));
			outputHelper.set(1,b);
		}
		
		for (int i=0; i<outputHelper.array.length; i++) {
			outputHelper.set(i,outputHelper.get(i).mod(p)); 
		}
		
		output.set(0,outputHelper.get(0));
		output.set(1,outputHelper.get(1));
		
        return output;
	}
	
	public FiniteFieldEl inverse() {
		
		FiniteFieldEl output = new FiniteFieldEl(new BigInteger[2]);
		BigInteger b = this.get(0).mod(p);
		BigInteger a = this.get(1).mod(p);
		
		if (a.equals(BigInteger.ZERO)) {
			output.set(0,b.modInverse(p));
			output.set(1,a);
			
			return output;
		}
		else {
			BigInteger c = b.multiply(b).multiply(a.modInverse(p)).multiply(a.modInverse(p));
			BigInteger d = BigInteger.ONE.add(c);
			BigInteger outputHelper = d.modInverse(p);
			BigInteger x0Helper = b.multiply(a.modInverse(p)).multiply(a.modInverse(p));
			BigInteger x1Helper = (a.modInverse(p)).negate().mod(p);
			output.set(0,outputHelper.multiply(x0Helper).mod(p));
			output.set(1,outputHelper.multiply(x1Helper).mod(p));
		
			return output;
		}
	}
	
	public FiniteFieldEl squareMultiply(BigInteger e) {
		
		FiniteFieldEl output = this;
		String eBinary = e.toString(2);
		int eLength = eBinary.length();
		
		for (int i=eLength-2; i>=0; i--) {
			output = output.multiply(output);
			
			if (eBinary.substring(eLength-i-1,eLength-i).equals("1")) {
				output = output.multiply(this);
			}
		}
		
		return output;
	}
}