package animation;

import java.awt.BorderLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;

import jama.Matrix;


public class AnimationMain {
	
	public static void main(String[] args) {
		Matrix[] cubeSides = new Matrix[6];
		cubeSides[0] = new Matrix(new double[][]{{0.0, 0.0, 0.0},
									  {0.0, 1.0, 0.0},
									  {1.0, 1.0, 0.0},
									  {1.0, 0.0, 0.0},
									  {0.0, 0.0, 0.0}}).transpose();
		cubeSides[1] = new Matrix(new double[][]{{0.0, 0.0, 1.0},
									  {0.0, 1.0, 1.0},
									  {1.0, 1.0, 1.0},
									  {1.0, 0.0, 1.0},
									  {0.0, 0.0, 1.0}}).transpose();
		cubeSides[2] = new Matrix(new double[][]{{0.0, 0.0, 0.0},
									  {0.0, 0.0, 1.0},
									  {0.0, 1.0, 1.0},
									  {0.0, 1.0, 0.0},
									  {0.0, 0.0, 0.0}}).transpose();
		cubeSides[3] = new Matrix(new double[][]{{1.0, 0.0, 0.0},
									  {1.0, 0.0, 1.0},
									  {1.0, 1.0, 1.0},
									  {1.0, 1.0, 0.0},
									  {1.0, 0.0, 0.0}}).transpose();
		cubeSides[4] = new Matrix(new double[][]{{1.0, 0.0, 0.0},
									  {1.0, 0.0, 1.0},
									  {1.0, 1.0, 1.0},
									  {1.0, 1.0, 0.0},
									  {1.0, 0.0, 0.0}}).transpose();
		cubeSides[5] = new Matrix(new double[][]{{0.0, 1.0, 0.0},
									  {1.0, 1.0, 0.0},
									  {1.0, 1.0, 1.0},
									  {0.0, 1.0, 1.0},
									  {0.0, 1.0, 0.0}}).transpose();
		
		LetterRotationAnimator animator = new LetterRotationAnimator(cubeSides);
		
		// GUI
		AnimationPanel panel = new AnimationPanel(animator);
		AnimationControlPanel controlPanel = new AnimationControlPanel();
		
		panel.setDelegate(controlPanel);
		controlPanel.setDelegate(panel);
		
		JFrame f = new JFrame("Animation from Scratch");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		
		f.add(panel, BorderLayout.NORTH);
		f.add(controlPanel, BorderLayout.SOUTH);
		
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
