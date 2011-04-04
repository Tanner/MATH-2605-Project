package powermethod;

import jama.Matrix;

public class MatrixGroup {
	private Matrix matrix;
	private double dominantEigenvalue;
	private double recessiveEigenvalue;
	private int powerMethodIterations;
	private int inversePowerMethodIterations;
	
	public MatrixGroup(Matrix matrix) {
		this.matrix = matrix;
	}

	/**
	 * Returns the matrix.
	 * 
	 * @return The matrix
	 */
	public Matrix getMatrix() {
		return matrix;
	}

	/**
	 * Sets the matrix.
	 * 
	 * @param matrix The matrix to set
	 */
	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}

	/**
	 * Returns the dominant eigenvalue.
	 * 
	 * @return The dominant eigenvalue
	 */
	public double getDominantEignevalue() {
		return dominantEigenvalue;
	}

	/**
	 * Sets the dominant eigenvalue.
	 * 
	 * @param dominantEignevalue The dominant eigenvalue to set
	 */
	public void setDominantEigenvalue(double dominantEigenvalue) {
		this.dominantEigenvalue = dominantEigenvalue;
	}

	/**
	 * Returns the recessive eigenvalue.
	 * 
	 * @return The recessive eigenvalue
	 */
	public double getRecessiveEigenvalue() {
		return recessiveEigenvalue;
	}

	/**
	 * Sets the recessive eigenvalue.
	 * 
	 * @param recessiveEigenvalue The recessive eigenvalue to set
	 */
	public void setRecessiveEigenvalue(double recessiveEigenvalue) {
		this.recessiveEigenvalue = recessiveEigenvalue;
	}

	/**
	 * Returns the iterations in power method.
	 * 
	 * @return The powerMethodIterations
	 */
	public int getPowerMethodIterations() {
		return powerMethodIterations;
	}

	/**
	 * Sets the iterations in power method.
	 * 
	 * @param powerMethodIterations The power method iterations to set
	 */
	public void setPowerMethodIterations(int powerMethodIterations) {
		this.powerMethodIterations = powerMethodIterations;
	}

	/**
	 * Returns the iterations in inverse power method.
	 * 
	 * @return The inversePowerMethodIterations
	 */
	public double getInversePowerMethodIterations() {
		return inversePowerMethodIterations;
	}

	/**
	 * Sets the iterations in power method.
	 * 
	 * @param inversePowerMethodIterations The inverse power method iterations to set
	 */
	public void setInversePowerMethodIterations(int inversePowerMethodIterations) {
		this.inversePowerMethodIterations = inversePowerMethodIterations;
	}
}
