package powermethod;

import java.util.ArrayList;
import java.util.Random;

import jama.Matrix;

public class PowerMethodMain {
	public static void main(String[] args) {
		ArrayList<Matrix> matrices = new ArrayList<Matrix>();
		
		generateRandomMatrices(matrices, 1000, -2, 2);
	}
	
	public static void generateRandomMatrices(ArrayList<Matrix> matrices, int amount, int lowerBound, int upperBound) {
		//Generate a number of 2×2 matrices with random components evenly distributed in an interval [a,b]
		
		Random rand = new Random();
		
		for (int i = 0; i < amount; i++) {
			double[][] matrix = new double[2][2];
			for (int r = 0; r < matrix.length; r++) {
				for (int c = 0; c < matrix[r].length; c++) {
					matrix[r][c] = rand.nextInt(upperBound - lowerBound + 1) + lowerBound;
				}
			}
			matrices.add(new Matrix(matrix));
		}
	}
}
