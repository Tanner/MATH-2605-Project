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
		
		Matrix testMatrix = new Matrix(new double[][]{{1, 2},{3, 4}});
		testMatrix.print(2, 2);
		powerMethod(testMatrix, 1);
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
				matrix[r][c] = rand.nextDouble() * (upperBound - lowerBound + 1) + lowerBound;
			}
		}
		matrices.add(new Matrix(matrix));
	}
	
	public static void powerMethod(Matrix matrix, int desiredAccuracy) {
		Matrix approximation = new Matrix(new double[][]{{1},{1}});
		
		int accuracy = 0;
		int iterations = 0;
		//while (accuracy <= desiredAccuracy)
		//{
			approximation = matrix.times(approximation);
			
			//Use's the Rayleigh Equation to solve for the eignvalue
			double eigenvalue = rayleighEquation(matrix, approximation);
			System.out.println(eigenvalue);
			
			iterations++;
		//}
	}
	
	public static void inversePowerMethod(Matrix matrix) {
		//
	}
	
	public static double rayleighEquation(Matrix matrix, Matrix eigenvector) {
		Matrix rayleighNominator = (matrix.times(eigenvector)).transpose().times(eigenvector);
		Matrix rayleighDenominator = eigenvector.transpose().times(eigenvector);
		
		return rayleighNominator.getArray()[0][0] / rayleighDenominator.getArray()[0][0];
	}
}
