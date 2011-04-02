package powermethod;

import java.util.ArrayList;
import java.util.Random;

import jama.Matrix;

public class PowerMethodMain {
	public static final int POWER_METHOD_MAX_ITERATIONS = 1000;
	
	public static void main(String[] args) {
		ArrayList<Matrix> matrices = new ArrayList<Matrix>();
		
		for (int i = 0; i < 1000; i++) {
			generateRandomMatrix(matrices, -2, 2);
		}
		
		Matrix testMatrix = new Matrix(new double[][]{{1, 2},{3, 4}});
		testMatrix.print(2, 2);
		System.out.println("Dominant Eigenvalue: "+powerMethod(testMatrix, 3));
	}
	
	public static void generateRandomMatrix(ArrayList<Matrix> matrices, int lowerBound, int upperBound) {
		//Generate a 2×2 matrix with random components evenly distributed in an interval [a,b]
		if (matrices == null) {
			matrices = new ArrayList<Matrix>();
		}
		
		if (lowerBound > upperBound) {
			throw new IllegalArgumentException("Invalid bounds.");
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
	
	public static double powerMethod(Matrix matrix, int desiredAccuracy) {
		Matrix approximation = new Matrix(new double[][]{{1},{1}});

		int iterations = 0;
		
		double relativeError = Double.MAX_VALUE;
		double previousEigenvalue = 0;
		double eigenvalue = 0;
		
		while (relativeError * 100 > (0.5 * Math.pow(10,2 - desiredAccuracy)) && iterations < POWER_METHOD_MAX_ITERATIONS)
		{
			//Approximate!
			approximation = matrix.times(approximation);
			
			//Use's the Rayleigh Equation to solve for the eigenvalue
			eigenvalue = rayleighEquation(matrix, approximation);
			
			//Calculate the accuracy
			relativeError = Math.abs((eigenvalue - previousEigenvalue) / eigenvalue);
			previousEigenvalue = eigenvalue;
			
			iterations++;
		}
		
		return eigenvalue;
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
