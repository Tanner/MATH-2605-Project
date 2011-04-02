package powermethod;

import java.util.ArrayList;
import java.util.Random;

import jama.Matrix;

public class PowerMethodMain {
	public static void main(String[] args) {
		ArrayList<Matrix> matrices = new ArrayList<Matrix>();
		
		for (int i = 0; i < 1000; i++) {
			generateRandomMatrix(matrices, -2, 2);
		}
	}
	
	public static void generateRandomMatrix(ArrayList<Matrix> matrices, int lowerBound, int upperBound) {
		//Generate a 2×2 matrix with random components evenly distributed in an interval [a,b]
		if (matrices == null) {
			matrices = new ArrayList<Matrix>();
		}
		
		if (lowerBound > upperBound) {
			return;
			//throw new Exception("Invalid bounds.");
		}
		
		Random rand = new Random();
		
		double[][] matrix = new double[2][2];
		for (int r = 0; r < matrix.length; r++) {
			for (int c = 0; c < matrix[r].length; c++) {
				matrix[r][c] = rand.nextInt(upperBound - lowerBound + 1) + lowerBound;
			}
		}
		matrices.add(new Matrix(matrix));
	}
	
	public static void powerMethod(Matrix matrix) {
		//
	}
	
	public static void inversePowerMethod(Matrix matrix) {
		//
	}
}
