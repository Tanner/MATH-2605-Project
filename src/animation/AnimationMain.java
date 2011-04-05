package animation;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import jama.Matrix;


public class AnimationMain {
	
	public static void main(String[] args) {
		Side[] cubeSides = new Side[6];
		cubeSides[0] = new Side(new Matrix(new double[][]{{0.0, 0.0, 0.0},
									  {0.0, 1.0, 0.0},
									  {1.0, 1.0, 0.0},
									  {1.0, 0.0, 0.0},
									  {0.0, 0.0, 0.0}}).transpose(), Color.RED);
		cubeSides[1] = new Side(new Matrix(new double[][]{{0.0, 0.0, 1.0},
									  {0.0, 1.0, 1.0},
									  {1.0, 1.0, 1.0},
									  {1.0, 0.0, 1.0},
									  {0.0, 0.0, 1.0}}).transpose(), Color.BLUE);
		cubeSides[2] = new Side(new Matrix(new double[][]{{0.0, 0.0, 0.0},
									  {0.0, 0.0, 1.0},
									  {0.0, 1.0, 1.0},
									  {0.0, 1.0, 0.0},
									  {0.0, 0.0, 0.0}}).transpose(), Color.GREEN);
		cubeSides[3] = new Side(new Matrix(new double[][]{{1.0, 0.0, 0.0},
									  {1.0, 0.0, 1.0},
									  {1.0, 1.0, 1.0},
									  {1.0, 1.0, 0.0},
									  {1.0, 0.0, 0.0}}).transpose(), Color.YELLOW);
		cubeSides[4] = new Side(new Matrix(new double[][]{{1.0, 0.0, 0.0},
									  {1.0, 0.0, 1.0},
									  {1.0, 1.0, 1.0},
									  {1.0, 1.0, 0.0},
									  {1.0, 0.0, 0.0}}).transpose(), Color.PINK);
		cubeSides[5] = new Side(new Matrix(new double[][]{{0.0, 1.0, 0.0},
									  {1.0, 1.0, 0.0},
									  {1.0, 1.0, 1.0},
									  {0.0, 1.0, 1.0},
									  {0.0, 1.0, 0.0}}).transpose(), Color.ORANGE);
		
		for (Side s : cubeSides) {
			s.setOpacity(1);
		}
		
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
