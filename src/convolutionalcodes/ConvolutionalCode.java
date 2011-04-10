package convolutionalcodes;

import jama.Matrix;

public class ConvolutionalCode {
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
	}
}