package animation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.io.PrintWriter;

import jama.Matrix;


public class LetterRotationAnimator {
	private Matrix[] sides;
		
	private final int WIDTH = 680;
	private final int HEIGHT = 320;
	private final int MARGIN = 20;
	private final int STROKE_WIDTH = 2;
	private final int SIZE_MULTIPLIER = 150;
	private static final int TOTAL_FRAMES = 121;
	
	public LetterRotationAnimator(Matrix[] sides) {
		this.sides = sides;
	}
	
	private static Matrix zRotatedMatrix(Matrix m, final int ROTATIONS, final int T, final int TOTAL_FRAMES) {
		double angle = T * (ROTATIONS * Math.PI*2)/(TOTAL_FRAMES-1);

		Matrix rotationMatrix = new Matrix(new double[][] {
				{Math.cos(angle), -Math.sin(angle), 0.0},
				{Math.sin(angle), Math.cos(angle), 0.0},
				{0.0, 0.0, 1.0}
				});
		
		return rotatedMatrix(m, rotationMatrix);
	}
	
	private static Matrix yRotatedMatrix(Matrix m, final int ROTATIONS, final int T, final int TOTAL_FRAMES) {
		double angle = T * (ROTATIONS * Math.PI*2)/(TOTAL_FRAMES-1);
				
		Matrix rotationMatrix = new Matrix(new double[][] {
				{1.0, 0.0, 0.0},
				{0.0, Math.cos(angle), -Math.sin(angle)},
				{0.0, Math.sin(angle), Math.cos(angle)}
				});

		return rotatedMatrix(m, rotationMatrix);
	}
	
	private static Matrix xRotatedMatrix(Matrix m, final int ROTATIONS, final int T, final int TOTAL_FRAMES) {
		double angle = T * (ROTATIONS * Math.PI*2)/(TOTAL_FRAMES-1);

		Matrix rotationMatrix = new Matrix(new double[][] {
				{Math.cos(angle), 0.0, -Math.sin(angle)},
				{0.0, 1.0, 0.0},
				{Math.sin(angle), 0.0, Math.cos(angle)}
				});

		return rotatedMatrix(m, rotationMatrix);
	}
	
	private static Matrix rotatedMatrix(Matrix m, Matrix r) {
		Matrix translationMatrix = new Matrix(m.getRowDimension(), m.getColumnDimension());
		for (int c = 0; c < translationMatrix.getColumnDimension(); c++) {
			// TODO stop using magic numbers for center of 3d object 
			
			// x
			translationMatrix.set(0, c, 0.5);
			// y
			translationMatrix.set(1, c, 0.5);
			// z
			translationMatrix.set(2, c, 0.5);
		}
		
		Matrix rotatedMatrix = r.times(m.minus(translationMatrix)).plus(translationMatrix);
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
				
		Matrix[] sidesRotated = sides.clone();
		for (int i = 0; i < sidesRotated.length; i++) {
			sidesRotated[i] = yRotatedMatrix(xRotatedMatrix(sidesRotated[i], 3, T, TOTAL_FRAMES), 3, T, TOTAL_FRAMES);
		}
		for (Matrix m : sidesRotated) {
			double[][] arr = m.getArray();
			Polygon side = new Polygon();
			for (int i = 0; i < arr[0].length; i++) {
				side.addPoint((WIDTH - SIZE_MULTIPLIER)/2 + (int)(arr[0][i]*SIZE_MULTIPLIER),
						(HEIGHT - SIZE_MULTIPLIER)/2 + (int)(HEIGHT -arr[1][i] * SIZE_MULTIPLIER) - HEIGHT/2);
			}
			g2d.drawPolygon(side);
		}
		
		g2d.setColor(Color.DARK_GRAY);
		g2d.setFont(new Font("Arial", Font.PLAIN, 13));
		g2d.drawString("Frame " + T, MARGIN, MARGIN + 5);
	}

	public int getWidth() {
		return WIDTH + MARGIN*2;
	}
	
	public int getHeight() {
		return HEIGHT + MARGIN*2;
	}
}
