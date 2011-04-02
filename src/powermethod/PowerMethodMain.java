package powermethod;

import java.util.ArrayList;
import java.util.Random;

import jama.Matrix;

public class PowerMethodMain {
	public static final int POWER_METHOD_MAX_ITERATIONS = 1000;
	public static final int ACCURACY = 5;
	
	public static void main(String[] args) {
		ArrayList<MatrixGroup> matrices = new ArrayList<MatrixGroup>();
		
		for (int i = 0; i < 1000; i++) {
			MatrixGroup matrixGroup = new MatrixGroup(generateRandomMatrix(-2, 2));
			
			double powerMethod[] = powerMethod(matrixGroup.getMatrix(), ACCURACY);
			double inversePowerMethod[] = inversePowerMethod(matrixGroup.getMatrix(), ACCURACY);
			
			matrixGroup.setDominantEigenvalue(powerMethod[0]);
			matrixGroup.setPowerMethodIterations(powerMethod[1]);
			
			matrixGroup.setRecessiveEigenvalue(inversePowerMethod[0]);
			matrixGroup.setInversePowerMethodIterations(inversePowerMethod[1]);
			
			matrices.add(matrixGroup);
		}
		
		Matrix testMatrix = new Matrix(new double[][]{{1, 2},{3, 4}});
		testMatrix.print(2, 2);
		System.out.println("Dominant Eigenvalue: "+powerMethod(testMatrix, 3));
		System.out.println("Recessive Eigenvalue: "+inversePowerMethod(testMatrix, 3));
	}
	
	/**
	 * Generate a random 2x2 matrix with values between a lower and upper bound.
	 * 
	 * @param lowerBound Lower bound for the values in the matrix
	 * @param upperBound Matrix bound for the values in the matrix
	 */
	public static Matrix generateRandomMatrix(int lowerBound, int upperBound) {
		//Generate a 2×2 matrix with random components evenly distributed in an interval [a,b]		
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
		
		return new Matrix(matrix);
	}
	
	/**
	 * Determines the largest eigenvalue via the power method.
	 * 
	 * Sources: http://college.cengage.com/mathematics/larson/elementary_linear/4e/shared/downloads/c10s3.pdf
	 * 			http://www.scribd.com/doc/9710973/Chapter-10-Eigenvalues-and-Eigenvectors
	 * 
	 * @param matrix Matrix to find the eigenvalue
	 * @param desiredAccuracy Number of digits accuracy requested
	 * @return Dominant eigenvalue and number of iterations
	 */
	public static double[] powerMethod(Matrix matrix, int desiredAccuracy) {
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
		
		return new double[]{eigenvalue, iterations};
	}
	
	public static double[] inversePowerMethod(Matrix matrix, int desiredAccuracy) {
		Matrix approximation = new Matrix(new double[][]{{1},{1}});

		int iterations = 0;
		
		double relativeError = Double.MAX_VALUE;
		double previousEigenvalue = 0;
		double eigenvalue = 0;
		
		while (relativeError * 100 > (0.5 * Math.pow(10,2 - desiredAccuracy)) && iterations < POWER_METHOD_MAX_ITERATIONS)
		{
			//Approximate!
			approximation = matrix.inverse().times(approximation);
			
			//Use's the Rayleigh Equation to solve for the eigenvalue
			eigenvalue = rayleighEquation(matrix, approximation);
			
			//Calculate the accuracy
			relativeError = Math.abs((eigenvalue - previousEigenvalue) / eigenvalue);
			previousEigenvalue = eigenvalue;
			
			iterations++;
		}
		
		return new double[]{eigenvalue, iterations};
	}
	
	/**
	 * Solve for the eigenvalue given the matrix and its corresponding eigenvector.
	 * 
	 * @param matrix Matrix that goes with the eigenvalue and the eigenvector
	 * @param eigenvector Eigenvector of the eigenvalue to find
	 * @return Eigenvalue
	 */
	public static double rayleighEquation(Matrix matrix, Matrix eigenvector) {
		Matrix rayleighNominator = (matrix.times(eigenvector)).transpose().times(eigenvector);
		Matrix rayleighDenominator = eigenvector.transpose().times(eigenvector);
		
		return rayleighNominator.getArray()[0][0] / rayleighDenominator.getArray()[0][0];
	}
}
