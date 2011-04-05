package animation;

import java.awt.Color;

import jama.Matrix;

public class Side {
	private Matrix matrix;
	private Color color;
	
	public Side(Matrix matrix) {
		this.matrix = matrix;
	}
	
	public Side(Matrix matrix, Color color) {
		this(matrix);
		this.color = color;
	}
	
	public Matrix getMatrix() {
		return matrix;
	}
	
	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
