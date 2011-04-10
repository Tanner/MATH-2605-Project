package convolutionalcodes;

import jama.Matrix;

public class ConvolutionalCode {
	private final static int ITERATIONS = 300;
	
	public static void main(String args[]) {
		double[][] xArray = new double[8][1];
		xArray[0][0] = 1.0;
		xArray[1][0] = 0.0;
		xArray[2][0] = 1.0;
		xArray[3][0] = 1.0;
		xArray[4][0] = 0.0;

		xArray[5][0] = 0.0;
		xArray[6][0] = 0.0;
		xArray[7][0] = 0.0;
		Matrix x = new Matrix(xArray);
		Matrix a = ConvolutionalCode.generateA(xArray.length);
		System.out.println("A:");
		a.print(xArray[0].length, 0);
		
		System.out.println("Y:");
		Matrix y = a.times(x);
		
		double[][] yArray = y.getArray();
		for (int i = 0; i < yArray.length; i++) {
			for (int j = 0; j < yArray[i].length; j++) {
				yArray[i][j] = yArray[i][j] > 11 ? yArray[i][j] % 2 : yArray[i][j];
			}
		}
		
		y.print(xArray[0].length, 0);
		
		System.out.println("X:");
		jacobiIteration(y).print(y.getArray().length, 5);
	}
	
	public static Matrix generateA(int n) {		
		Matrix a_y = new Matrix(n, n, 0);
		double[][] a_y_array = a_y.getArray();
		
		for (int i = 0; i < n; i++)
		{
			//Y 0
			if (i > 1)
				a_y_array[i][i - 2] += 10;
			if (i > 2)
				a_y_array[i][i - 3] += 10;
			a_y_array[i][i] += 10;
			
			//Y 1
			if (i > 0)
				a_y_array[i][i - 1] += 1;
			if (i > 2)
				a_y_array[i][i - 3] += 1;
			a_y_array[i][i] += 1;
		}
		
		return a_y;
	}
	
	public static Matrix jacobiIteration(Matrix b) {
		//Ax = b
		Matrix a = generateA(b.getRowDimension());
		
		Matrix s = new Matrix(a.getRowDimension(), a.getColumnDimension(), 0);
		for (int i = 0; i < a.getRowDimension(); i++) {
			s.set(i, i, a.get(i, i));
		}
		
		Matrix t = s.minus(a);
		
		Matrix x = new Matrix(t.getColumnDimension(), 1, 0);
		Matrix xPlus = (Matrix)x.clone();
		
		for (int i = 0; i < ITERATIONS; i++) {
			xPlus = t.times(x).plus(b);
			
			for (int j = 0; j < xPlus.getRowDimension(); j++) {
				xPlus.set(j, 0, xPlus.get(j, 0) / s.get(j, j));
			}
			
			x = new Matrix(xPlus.getArrayCopy());
		}
		
		return x;
	}
}