import java.math.BigInteger;
import java.util.Random;

public class ECPoint {
	
	private BigInteger p; 
	private FiniteFieldEl a;
	private FiniteFieldEl x; 
	private FiniteFieldEl y; 
	private boolean neutral; 
	
	public ECPoint(FiniteFieldEl x, FiniteFieldEl y, boolean neutral) {
		
		p = FrameI.p;
		a = new FiniteFieldEl(new BigInteger[] {FrameI.a, BigInteger.ZERO});
		this.x = new FiniteFieldEl(new BigInteger[] {x.get(0).mod(p),x.get(1).mod(p)});
		this.y = new FiniteFieldEl(new BigInteger[] {y.get(0).mod(p),y.get(1).mod(p)});
		this.neutral = neutral;
	}
	
	public ECPoint(ECPoint P) {
		
		this.x = P.getX();
		this.y = P.getY();
		this.neutral = P.getNeutral();
	}
	
	public FiniteFieldEl getX() {
		
		return x;
	}
	
	public FiniteFieldEl getY() {
		
		return y;
	}
	
	public boolean getNeutral() {
		
		return neutral;
	}
	
	public boolean equals(ECPoint Q) {
		
		 FiniteFieldEl Px = this.x;
		 FiniteFieldEl Py = this.y;
		 boolean Pneutral = this.neutral;
		 FiniteFieldEl Qx = Q.getX();
		 FiniteFieldEl Qy = Q.getY();
		 boolean Qneutral = Q.getNeutral();
		 
		 return (Px.equals(Qx) && Py.equals(Qy) && (Pneutral == Qneutral));
	}
	
	public ECPoint addECPoint(ECPoint P) {
		
		FiniteFieldEl x1 = this.x;
		FiniteFieldEl y1 = this.y;
		FiniteFieldEl x2 = P.getX();
		FiniteFieldEl y2 = P.getY();
		FiniteFieldEl two = new FiniteFieldEl(new BigInteger[] {new BigInteger("2"),BigInteger.ZERO});
		FiniteFieldEl three = new FiniteFieldEl(new BigInteger[] {new BigInteger("3"),BigInteger.ZERO});
		FiniteFieldEl neutral = new FiniteFieldEl(new BigInteger[] {BigInteger.ZERO,BigInteger.ZERO});
		
		if (this.getNeutral()) {
			return P;
		}
		
		if (P.getNeutral()) {
			return this;
		}
		
		if (x1.equals(x2) && y1.equals(y2.negate())) {
			return new ECPoint(neutral,neutral,true);
		}
		
		if (x1.equals(x2) && y1.equals(y2)) {
			FiniteFieldEl z = three.multiply(x1).multiply(x1).add(a);
			FiniteFieldEl n = two.multiply(y1);
			FiniteFieldEl lambda = z.multiply(n.inverse());
			FiniteFieldEl x3 = lambda.multiply(lambda).add(x1.negate()).add(x2.negate());
			FiniteFieldEl y3 = lambda.multiply(x1.add(x3.negate())).add(y1.negate());
			
			return new ECPoint(x3,y3,false);
		}
		else {
			FiniteFieldEl z = y2.add(y1.negate());
			FiniteFieldEl n = x2.add(x1.negate());
			FiniteFieldEl lambda = z.multiply(n.inverse());
			FiniteFieldEl x3 = lambda.multiply(lambda).add(x1.negate()).add(x2.negate());
			FiniteFieldEl y3 = lambda.multiply(x1.add(x3.negate())).add(y1.negate());
			
			return new ECPoint(x3,y3,false);
		}
	}
	
	public ECPoint negateECPoint() {  
		
		if (this.getNeutral()) {
			return this;
		}
		else {
			return new ECPoint(this.x,this.y.negate(),false);
		}
	}

	public ECPoint multiplyECPoint(BigInteger n) {
		
		FiniteFieldEl neutral = new FiniteFieldEl(new BigInteger[] {BigInteger.ZERO,BigInteger.ZERO});
		
		if (n.equals(BigInteger.ZERO)) {
			return new ECPoint(neutral,neutral,true);
		}
		
		if (n.equals(BigInteger.ONE)) {
			return this;
		}
		                                           
		if (n.signum() == -1) {
			ECPoint P = new ECPoint(x,y.negate(),false);
			
			return P.multiplyECPoint(n.negate());
		}
		
		if (n.testBit(0)) {
			return ((this.addECPoint(this)).multiplyECPoint(n.shiftRight(1))).addECPoint(this);
		}
		else {
			return (this.addECPoint(this)).multiplyECPoint(n.shiftRight(1));
		}
	}
	
	public static ECPoint randomECPoint(BigInteger p, BigInteger a, BigInteger b) { // in E(F_p)            
		
		BigInteger legendre = p.subtract(BigInteger.ONE).divide(new BigInteger("2"));
		BigInteger square = p.add(BigInteger.ONE).divide(new BigInteger("4"));
		BigInteger x = new BigInteger(p.bitLength(),new Random()).mod(p);
		BigInteger right = x.modPow(new BigInteger("3"),p).add(a.multiply(x).mod(p)).add(b.mod(p)).mod(p);
		
		while ((right.modPow(legendre,p).equals(p.subtract(BigInteger.ONE))) || (right.modPow(legendre,p).equals(BigInteger.ZERO))) {
			x = new BigInteger(p.bitLength(),new Random()).mod(p);
			right = x.modPow(new BigInteger("3"),p).add(a.multiply(x).mod(p)).add(b.mod(p)).mod(p);
		}
		
		BigInteger y = right.modPow(square,p);
	
		return new ECPoint(new FiniteFieldEl(new BigInteger[] {x,BigInteger.ZERO}),new FiniteFieldEl(new BigInteger[] {y,BigInteger.ZERO}),false);
	}
	
	public static ECPoint randomTorsionECPoint(BigInteger p, BigInteger a, BigInteger b, BigInteger m) { // in E(F_p)[m]
		
		BigInteger h = (p.add(BigInteger.ONE)).divide(m); 
		ECPoint Q = ECPoint.randomECPoint(p,a,b);
		
		while (Q.multiplyECPoint(h).getNeutral()) {
			Q = ECPoint.randomECPoint(p,a,b);
		}
		
		return Q.multiplyECPoint(h);
	}
	
	public boolean onCurve(BigInteger m) { // and a m-torsion point
		
		BigInteger x = this.getX().get(0);
		BigInteger y = this.getY().get(0);
		BigInteger l = y.modPow(new BigInteger("2"),p);
		BigInteger r = (x.modPow(new BigInteger("3"),p)).add(a.get(0).multiply(x).mod(p)).mod(p);
		
		if (l.equals(r)) {
			ECPoint R = this.multiplyECPoint(m);
			
			if (R.getNeutral()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public ECPoint distortion() { // (x,y) -> (-x,yX)
		
		ECPoint Q = new ECPoint(new FiniteFieldEl(new BigInteger[] {BigInteger.ZERO,BigInteger.ZERO}),new FiniteFieldEl(new BigInteger[] {BigInteger.ZERO,BigInteger.ZERO}),false);
		FiniteFieldEl Qx = this.getX(); 
		FiniteFieldEl Qy = this.getY(); 
		Q.getX().set(0,Qx.get(0).negate().mod(p)); 
		Q.getY().set(1,Qy.get(0));
		
		return Q;
	}
}