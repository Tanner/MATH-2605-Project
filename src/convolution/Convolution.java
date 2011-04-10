package convolution;

import jama.Matrix;

public class Convolution {
	public static void main(String[] args) {
		convolude(new Matrix(new double[][]{{1.0, 0.0, 1.0, 1.0, 0.0}}).transpose());
	}
	
	public static Matrix convolude(Matrix x) {
		Matrix results = new Matrix(2, x.getRowDimension()+3);
		
		for (int t = 0; t < x.getRowDimension()+3; t++) {
			Matrix xPart = null;
						
			if (t >= x.getRowDimension()+1) {
				xPart = new Matrix(new double[][] {
						{x.get(t-3, 0),
							0.0,
							0.0,
							0.0}}).transpose();
			} else if (t >= x.getRowDimension()) {
				xPart = new Matrix(new double[][] {
						{x.get(t-3, 0),
							x.get(t-2, 0),
							0.0,
							0.0}}).transpose();
			} else if (t >= x.getRowDimension()-1) {
				xPart = new Matrix(new double[][] {
						{x.get(t-3, 0),
							x.get(t-2, 0),
							x.get(t-1, 0),
							0.0}}).transpose();
			} else if (t-3 >= 0) {
				xPart = new Matrix(new double[][] {
						{x.get(t-3, 0),
							x.get(t-2, 0),
							x.get(t-1, 0),
							x.get(t, 0)}}).transpose();
			} else if (t-2 >= 0) {
				xPart = new Matrix(new double[][] {
						{0.0,
							x.get(t-2, 0),
							x.get(t-1, 0),
							x.get(t, 0)}}).transpose();
			} else if (t-1 >= 0) {
				xPart = new Matrix(new double[][] {
						{0.0,
							0.0,
							x.get(t-1, 0),
							x.get(t, 0)}}).transpose();
			} else if (t >= 0){
				xPart = new Matrix(new double[][] {
						{0.0,
							0.0,
							0.0,
							x.get(t, 0)}}).transpose();
			}
			
			Matrix y0 = new Matrix(new double[][]{{1.0, 1.0, 0.0, 1.0}});
			Matrix y1 = new Matrix(new double[][]{{1.0, 0.0, 1.0, 1.0}});
			Matrix a = new Matrix(2, y0.getColumnDimension());
			a.setMatrix(0, 0, 0, a.getColumnDimension()-1, y0);
			a.setMatrix(1, 1, 0, a.getColumnDimension()-1, y1);
			
			Matrix result = a.times(xPart);
			
			for (int r = 0; r < result.getRowDimension(); r++) {
				for (int c = 0; c < result.getColumnDimension(); c++) {
					result.set(r, c, result.get(r, c)%2);
				}
			}
			
			results.setMatrix(0, 1, t, t, result);
		}
		
		results.print(10, 10);
		
		return results;
	}
}
