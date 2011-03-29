package animation;

import jama.Matrix;


public class AnimationMain {
	public static void main(String[] args) {
		double[][] A = {{0, 2}, {3, 4}};
		
		Matrix m = new Matrix(A);
		
		m.print(0, 1);
		
		m = m.transpose();
		
		m.print(0, 1);
	}
}
