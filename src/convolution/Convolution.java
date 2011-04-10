package convolution;

import jama.Matrix;

public class Convolution {
	public static final int ITERATIONS = 200;
	
	public static void main(String[] args) {
		Matrix y = convolude(new Matrix(new double[][]{{1,0,1,1,1,0,1,1,1,0,0,0,1,1,1,1,0,1,0,1,0}}).transpose());
		y.print(4, 2);
		
		jacobiIteration(y).print(20, 5);
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
				
		return results;
	}
	
	public static Matrix jacobiIteration(Matrix b) {
		//This is A.
		Matrix y0 = new Matrix(new double[][]{{1.0, 1.0, 0.0, 1.0}});
		Matrix y1 = new Matrix(new double[][]{{1.0, 0.0, 1.0, 1.0}});
		Matrix a = new Matrix(y0.getColumnDimension(), y0.getColumnDimension());//4, y0.getColumnDimension());
		a.setMatrix(0, 0, 0, y0.getColumnDimension() - 1, y0);
		a.setMatrix(1, 1, 0, y1.getColumnDimension() - 1, y1);
				
		Matrix s = Matrix.identity(a.getRowDimension(), a.getColumnDimension());
		s.setMatrix(0, 1, 0, y0.getColumnDimension()-1, a);
		
		s.print(10, 10);
		
		Matrix t = s.minus(a);
		
		Matrix x = new Matrix(a.getRowDimension(), 1, 0);
		Matrix xPlus = (Matrix)x.clone();
		
		Matrix newB = new Matrix(b.getColumnDimension(), 1);
		for (int i = 0; i < newB.getColumnDimension(); i++) {
			newB.set(i, 0, b.get(i, 0) * 10 + b.get(i, 1));
		}
		b = newB;
		
		for (int i = 0; i < ITERATIONS; i++) {
			
			xPlus = t.times(x).plus(b);
			
			for (int j = 0; j < xPlus.getRowDimension(); j++) {
				xPlus.set(j, 0, xPlus.get(j, 0) / s.get(j, j));
			}
			
			x = (Matrix)xPlus.clone();
		}
		
		return x;
	}
}
