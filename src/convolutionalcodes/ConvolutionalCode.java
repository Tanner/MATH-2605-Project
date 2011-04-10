package convolutionalcodes;

import jama.Matrix;

public class ConvolutionalCode {
	public static Matrix generateA(int n) {
		/*Matrix a_y0 = new Matrix(n, n, 0);
		double[][] a_y0_array = a_y0.getArray();
		
		for (int i = 0; i < n; i++)
		{
			if (i > 1)
				a_y0_array[i][i - 2] = 10;
			if (i > 2)
				a_y0_array[i][i - 3] = 10;
			a_y0_array[i][i] = 10;
		}
		
		Matrix a_y1 = new Matrix(n, n, 0);
		double[][] a_y1_array = a_y1.getArray();
		
		for (int i = 0; i < n; i++)
		{
			if (i > 0)
				a_y1_array[i][i - 1] = 1;
			if (i > 2)
				a_y1_array[i][i - 3] = 1;
			a_y1_array[i][i] = 1;
		}*/
		
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
		
		for (int i = 0; i < a_y_array.length; i++) {
			for (int j = 0; j < a_y_array[i].length; j++) {
				if (a_y_array[i][j] > 10) {
					a_y_array[i][j] = a_y_array[i][j] % 2;
				}
			}
		}
		
		return a_y;
	}
	
	public static void main(String args[]) {
		double[][] xArray = new double[5][1];
		xArray[0][0] = 1.0;
		xArray[1][0] = 0.0;
		xArray[2][0] = 1.0;
		xArray[3][0] = 1.0;
		xArray[4][0] = 0.0;
		Matrix x = new Matrix(xArray);
		Matrix a = ConvolutionalCode.generateA(xArray.length);
		System.out.println("A:");
		a.print(xArray[0].length, 1);
		
		System.out.println("Y:");
		a.times(x).print(xArray[0].length, 1);
	}
}