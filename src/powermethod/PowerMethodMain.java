package powermethod;

import jama.Matrix;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.io.plots.DrawableWriter;
import de.erichseifert.gral.io.plots.DrawableWriterFactory;
import de.erichseifert.gral.plots.Plot;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;

public class PowerMethodMain {
	public static final int POWER_METHOD_MAX_ITERATIONS = 2000;
	public static final int ACCURACY = 5;
	
	private static final int IMAGE_HEIGHT = 800;
	private static final int IMAGE_WIDTH = 600;
	
	private static final String INVERSE_POWER_METHOD_FILE = "inversePowerMethod.png";
	private static final String POWER_METHOD_FILE = "powerMethod.png";
	
	public static void main(String[] args) {
		ArrayList<MatrixGroup> matrices = new ArrayList<MatrixGroup>();
		
		for (int i = 0; i < 1000; i++) {
			MatrixGroup matrixGroup = new MatrixGroup(generateRandomMatrix(-2, 2));
			
			double powerMethod[] = powerMethod(matrixGroup.getMatrix(), ACCURACY);
			double inversePowerMethod[] = inversePowerMethod(matrixGroup.getMatrix(), ACCURACY);
			
			matrixGroup.setDominantEigenvalue(powerMethod[0]);
			matrixGroup.setPowerMethodIterations((int)powerMethod[1]);
			
			matrixGroup.setRecessiveEigenvalue(inversePowerMethod[0]);
			matrixGroup.setInversePowerMethodIterations((int)inversePowerMethod[1]);
			
			matrices.add(matrixGroup);
		}
		
		paintPowerMethodPlot(matrices);
		paintInversePowerMethodPlot(matrices);
	}
	
	/**
	 * Plots a XY plot of all the MatrixGroup objects with the X-Axis being the matrix's determinant
	 * and the Y-Axis being the matrix's trace. The color of the points corresponds to the number of
	 * iterations for the power method.
	 * 
	 * Sources: http://trac.erichseifert.de/gral/
	 * 
	 * @param matrices ArrayList of MatrixGroup's to plot
	 */
	private static void paintPowerMethodPlot(ArrayList<MatrixGroup> matrices) {
		DataTable data = new DataTable(Double.class, Double.class, Integer.class);
		
		int minIterations = Integer.MAX_VALUE;
		int maxIterations = Integer.MIN_VALUE;
		for (MatrixGroup group : matrices) {
			int iterations = group.getPowerMethodIterations();
			if (iterations < minIterations) {
				minIterations = iterations;
			}
			if (iterations > maxIterations) {
				maxIterations = iterations;
			}
			
			double determinant = group.getMatrix().det();
			double trace = group.getMatrix().trace();
			
			data.add(determinant, trace, iterations);
		}
		
		XYPlot plot = createPlot(data, minIterations, maxIterations, "Power Method");
		savePlot(plot, POWER_METHOD_FILE);
		
		/*JFrame frame = new JFrame();
		frame.getContentPane().add(new InteractivePanel(plot));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.pack();
		frame.setVisible(true);*/
	}
	
	/**
	 * Plots a XY plot of all the MatrixGroup objects with the X-Axis being the matrix's determinant
	 * and the Y-Axis being the matrix's trace. The color of the points corresponds to the number of
	 * iterations for the inverse power method.
	 * 
	 * Sources: http://trac.erichseifert.de/gral/
	 * 
	 * @param matrices ArrayList of MatrixGroup's to plot
	 */
	private static void paintInversePowerMethodPlot(ArrayList<MatrixGroup> matrices) {
		DataTable data = new DataTable(Double.class, Double.class, Integer.class);
		
		int minIterations = Integer.MAX_VALUE;
		int maxIterations = Integer.MIN_VALUE;
		for (MatrixGroup group : matrices) {
			int iterations = group.getInversePowerMethodIterations();
			if (iterations < minIterations) {
				minIterations = iterations;
			}
			if (iterations > maxIterations) {
				maxIterations = iterations;
			}
			
			double determinant = group.getMatrix().det();
			double trace = group.getMatrix().trace();
			
			data.add(determinant, trace, iterations);
		}
		
		XYPlot plot = createPlot(data, minIterations, maxIterations, "Inverse Power Method");
		savePlot(plot, INVERSE_POWER_METHOD_FILE);
		
		/*JFrame frame = new JFrame();
		frame.getContentPane().add(new InteractivePanel(plot));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.pack();
		frame.setVisible(true);*/
	}
	
	/**
	 * Creates an XY plot using the given data and min/max iterations.
	 * 
	 * @param data Data for the matrices
	 * @param minIterations Lowest number of iterations
	 * @param maxIterations Greatest number of iterations
	 * @param title Title for the plot
	 * @return Created XYPlot
	 */
	public static XYPlot createPlot(DataTable data, int minIterations, int maxIterations, String title) {
		XYPlot plot = new XYPlot(data);
		
		plot.setPointRenderer(data, new IterationsPointRenderer());
		
		plot.getPointRenderer(data).setSetting(PointRenderer.COLOR, Color.RED);
		plot.getPointRenderer(data).setSetting(IterationsPointRenderer.COLOR_MIN, Color.CYAN);

		plot.getPointRenderer(data).setSetting(IterationsPointRenderer.VALUE_MIN, (double)minIterations);
		plot.getPointRenderer(data).setSetting(IterationsPointRenderer.VALUE_MAX, (double)maxIterations);
		
		plot.getAxisRenderer(XYPlot.AXIS_X).setSetting(AxisRenderer.LABEL, "Determinant");
		plot.getAxisRenderer(XYPlot.AXIS_Y).setSetting(AxisRenderer.LABEL, "Trace");
		
		plot.setSetting(XYPlot.TITLE, title);
		
		return plot;
	}
	
	/**
	 * Saves the given graph to the given file name.
	 * 
	 * @param plot Graph to save
	 * @param fileName Filename to save to
	 */
	public static void savePlot(XYPlot plot, String fileName) {
		plot.setBounds(0, 0, IMAGE_HEIGHT, IMAGE_WIDTH);
		
		DrawableWriter writer = DrawableWriterFactory.getInstance().get("image/png");
		try {
			writer.write(plot, new FileOutputStream(fileName), IMAGE_HEIGHT, IMAGE_WIDTH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	/**
	 * Determines the smallest eigenvalue via the inverse power method.
	 * 
	 * @param matrix Matrix to find the eigenvalue
	 * @param desiredAccuracy Number of digits accuracy requested
	 * @return Recessive eigenvalue and number of iterations
	 */
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
