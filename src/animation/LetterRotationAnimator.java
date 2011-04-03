package animation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.PrintWriter;

import jama.Matrix;


public class LetterRotationAnimator {
	private Matrix m1;
	private Matrix m2;
	private Matrix m3;
		
	private final int WIDTH = 680;
	private final int HEIGHT = 320;
	private final int MARGIN = 20;
	private final int STROKE_WIDTH = 2;
	private final int SIZE_MULTIPLIER = 50;
	private final int LETTER_WIDTH = 200;
	
	public LetterRotationAnimator(Matrix m1, Matrix m2, Matrix m3) {
		this.m1 = m1;
		this.m2 = m2;
		this.m3 = m3;
	}
	
	private static Matrix yRotatedMatrix(Matrix m, final int ROTATIONS, final int T, final int TOTAL_FRAMES) {
		double angle = T * (ROTATIONS * Math.PI*2)/(TOTAL_FRAMES-1);

		Matrix translationMatrix = new Matrix(m.getRowDimension(), m.getColumnDimension());
		for (int c = 0; c < translationMatrix.getColumnDimension(); c++) {
			// T
			translationMatrix.set(1, c, 3.0);
		}
				
		Matrix rotationMatrix = new Matrix(new double[][] {
				{1.0, 0.0, 0.0},
				{0.0, Math.cos(angle), -Math.sin(angle)},
				{0.0, Math.sin(angle), Math.cos(angle)}
				});
		Matrix rotatedMatrix = rotationMatrix.times(m.minus(translationMatrix)).plus(translationMatrix);
		return rotatedMatrix;
	}
	
	private static Matrix xRotatedMatrix(Matrix m, final int ROTATIONS, final int T, final int TOTAL_FRAMES) {
		double angle = T * (ROTATIONS * Math.PI*2)/(TOTAL_FRAMES-1);

		Matrix translationMatrix = new Matrix(m.getRowDimension(), m.getColumnDimension());
		for (int c = 0; c < translationMatrix.getColumnDimension(); c++) {
			// T
			translationMatrix.set(0, c, 2.0);
		}
				
		Matrix rotationMatrix = new Matrix(new double[][] {
				{Math.cos(angle), 0.0, -Math.sin(angle)},
				{0.0, 1.0, 0.0},
				{Math.sin(angle), 0.0, Math.cos(angle)}
				});
		Matrix rotatedMatrix = rotationMatrix.times(m.minus(translationMatrix)).plus(translationMatrix);
		return rotatedMatrix;
	}
	
	public void draw(Graphics g, final int T, final int TOTAL_FRAMES) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    
	    g2d.setColor(Color.WHITE);
	    g2d.fillRect(0, 0, WIDTH + MARGIN*2, HEIGHT + MARGIN*2);
	    
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				
		Matrix[] matrices = new Matrix[] {
				xRotatedMatrix(m1, 3, T, TOTAL_FRAMES),
				xRotatedMatrix(m2, 2, T, TOTAL_FRAMES),
				yRotatedMatrix(m3, 5, T, TOTAL_FRAMES)
				};
		
		int x_displacement = 20;
		for (Matrix m : matrices) {
			double[][] arr = m.getArray();
			for (int i = 1; i < arr[0].length; i++) {
				g2d.drawLine(x_displacement + (int)(arr[0][i-1] * SIZE_MULTIPLIER),
						HEIGHT + MARGIN - (int)(arr[1][i-1] * SIZE_MULTIPLIER),
						x_displacement + (int)(arr[0][i] * SIZE_MULTIPLIER),
						HEIGHT + MARGIN - (int)(arr[1][i] * SIZE_MULTIPLIER));
			}
			
			x_displacement += LETTER_WIDTH + MARGIN*2;
		}
		
		g2d.setColor(Color.DARK_GRAY);
		g2d.setFont(new Font("Arial", Font.PLAIN, 13));
		g2d.drawString("Frame " + T, MARGIN, MARGIN + 5);
	}
	
	public void print(PrintWriter pw, final int T, final int TOTAL_FRAMES) {
		pw.println("-------------------");
		pw.println("Frame " + T);
		pw.println("-------------------");
		
		Matrix[] matrices = new Matrix[] {
				yRotatedMatrix(m1, 3, T, TOTAL_FRAMES),
				xRotatedMatrix(m2, 2, T, TOTAL_FRAMES),
				yRotatedMatrix(m3, 5, T, TOTAL_FRAMES)
				};
		
		for (int i = 0; i < matrices.length; i++) {
			if (i == 0) {
				pw.println("L");
			} else if (i == 1) {
				pw.println("U");
			} else if (i == 2) {
				pw.println("Z");
			}
			
			matrices[i].print(pw, 6, 2);
		}
	}

	public int getWidth() {
		return WIDTH + MARGIN*2;
	}
	
	public int getHeight() {
		return HEIGHT + MARGIN*2;
	}
}
