import java.math.BigInteger;

public class Miller {
	
	public static FiniteFieldEl miller(ECPoint P, ECPoint R, BigInteger m, BigInteger a) {
		
		FiniteFieldEl aa = new FiniteFieldEl(new BigInteger[] {a, BigInteger.ZERO});
		String sBinarym = m.toString(2);
		int mLength = sBinarym.length();
		ECPoint T = P;
		FiniteFieldEl f = new FiniteFieldEl(new BigInteger[] {BigInteger.ONE, BigInteger.ZERO});
		
		for(int i=mLength-2; i>=0; i--) {
			FiniteFieldEl gdouble = gDbl(T,R,aa);
			f = f.multiply(f).multiply(gdouble);
			T = T.addECPoint(T);
			
			if (sBinarym.substring(mLength-i-1,mLength-i).equals("1")) {
				FiniteFieldEl gaddition = gAdd(T,P,R,aa);
				f = f.multiply(gaddition);
				T = T.addECPoint(P);
			}
		}
		
		return f;	
	}
	
	public static FiniteFieldEl gDbl(ECPoint T, ECPoint R, FiniteFieldEl aa) {
		
		FiniteFieldEl three = new FiniteFieldEl(new BigInteger[] {new BigInteger("3"), BigInteger.ZERO});
		FiniteFieldEl two = new FiniteFieldEl(new BigInteger[] {new BigInteger("2"), BigInteger.ZERO});
		FiniteFieldEl Tx = T.getX();
		FiniteFieldEl Ty = T.getY();
		FiniteFieldEl Rx = R.getX();
		FiniteFieldEl Ry = R.getY();
		
		if (T.addECPoint(T).getNeutral()) {
			return Rx.add(Tx.negate());
		}
		else {
			FiniteFieldEl lambdan = three.multiply(Tx).multiply(Tx).add(aa);
			FiniteFieldEl lambdaz = two.multiply(Ty);
			FiniteFieldEl lambda = lambdan.multiply(lambdaz.inverse()); 
			FiniteFieldEl gnHelper = lambda.multiply(Rx.add(Tx.negate()));
			FiniteFieldEl gn = Ry.add(Ty.negate()).add(gnHelper.negate());
			FiniteFieldEl gzHelper = lambda.multiply(lambda);
			FiniteFieldEl gz = Rx.add(Tx).add(Tx).add(gzHelper.negate());
			
			return gn.multiply(gz.inverse());
		}
	}
	
	public static FiniteFieldEl gAdd(ECPoint T, ECPoint P, ECPoint R, FiniteFieldEl aa) {
		
		FiniteFieldEl Tx = T.getX();
		FiniteFieldEl Ty = T.getY();
		FiniteFieldEl Px = P.getX();
		FiniteFieldEl Py = P.getY();
		FiniteFieldEl Rx = R.getX();
		FiniteFieldEl Ry = R.getY();
		
		if (T.addECPoint(P).getNeutral()) {
			return Rx.add(Tx.negate());
		}
		
		if (T.equals(P)) {                       
			return Miller.gDbl(T,R,aa);
		}
		else {
			FiniteFieldEl lambdan = Py.add(Ty.negate());
			FiniteFieldEl lambdaz = Px.add(Tx.negate());
			FiniteFieldEl lambda = lambdan.multiply(lambdaz.inverse());
			FiniteFieldEl gnHelper = lambda.multiply(Rx.add(Tx.negate()));
			FiniteFieldEl gn = Ry.add(Ty.negate()).add(gnHelper.negate());
			FiniteFieldEl gzHelper = lambda.multiply(lambda);
			FiniteFieldEl gz = Rx.add(Tx).add(Px).add(gzHelper.negate());
			
			return gn.multiply(gz.inverse());
		}
	}
}