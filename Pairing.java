import java.math.BigInteger;

public class Pairing {

	public static FiniteFieldEl weil(ECPoint P, ECPoint QQ, ECPoint S, BigInteger p, BigInteger a, BigInteger m) {
		
		ECPoint Q = QQ.distortion();
		
		// f_(P-S)(...)
		FiniteFieldEl f1 = Miller.miller(P.addECPoint(S.negateECPoint()),Q.addECPoint(S),m,a);
		FiniteFieldEl f2 = Miller.miller(P.addECPoint(S.negateECPoint()),S,m,a);
		FiniteFieldEl f_1 = f1.multiply(f2.inverse());
		
		// f_(-S)(..)
		FiniteFieldEl f3 = Miller.miller(S.negateECPoint(),Q.addECPoint(S),m,a);
		FiniteFieldEl f4 = Miller.miller(S.negateECPoint(),S,m,a);
		FiniteFieldEl f_2 = f3.multiply(f4.inverse());
		
		FiniteFieldEl n1 = f_1.multiply(f_2.inverse());
		
		// f_S(...)
		FiniteFieldEl f5 = Miller.miller(S,P.addECPoint(S.negateECPoint()),m,a);
		FiniteFieldEl f6 = Miller.miller(S,S.negateECPoint(),m,a);
		FiniteFieldEl f_3 = f5.multiply(f6.inverse());
		
		// f_(Q+S)(...)
		FiniteFieldEl f7 = Miller.miller(Q.addECPoint(S),P.addECPoint(S.negateECPoint()),m,a);
		FiniteFieldEl f8 = Miller.miller(Q.addECPoint(S),S.negateECPoint(),m,a);
		FiniteFieldEl f_4 = f7.multiply(f8.inverse());
		
		FiniteFieldEl n2 = f_3.multiply(f_4.inverse());
		
		return n1.multiply(n2);
	}
	
	public static FiniteFieldEl tate(ECPoint P, ECPoint QQ, ECPoint S, BigInteger p, BigInteger a, BigInteger m) {
		
		ECPoint Q = QQ.distortion();
		
		FiniteFieldEl fP_QS = Miller.miller(P,Q.addECPoint(S),m,a);
		FiniteFieldEl fP_S = Miller.miller(P,S,m,a);
		FiniteFieldEl f = fP_QS.multiply(fP_S.inverse());
		BigInteger e = (p.multiply(p).subtract(BigInteger.ONE)).divide(m);

		return f.squareMultiply(e);
	}
}