package animation;

import java.awt.BorderLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;

import jama.Matrix;


public class AnimationMain {
	private static final int TOTAL_FRAMES = 121;
	
	public static void main(String[] args) {
		Matrix l = new Matrix(new double[][] {
				{0.0,  0.0,  0.0},
				{4.0,  0.0,  0.0},
				{4.0,  1.33, 0.0},
				{1.33, 1.33, 0.0},
				{1.33, 6.0,  0.0},
				{0.0,  6.0,  0.0},
				{0.0,  0.0,  0.0}
				});
		l = l.transpose();
		
		Matrix u = new Matrix(new double[][] {
				{0.0,  0.0,  0.0},
				{4.0,  0.0,  0.0},
				{4.0,  6.0,  0.0},
				{2.66, 6.0,  0.0},
				{2.66, 1.33, 0.0},
				{1.33, 1.33, 0.0},
				{1.33, 6.0,  0.0},
				{0.0,  6.0,  0.0},
				{0.0,  0.0,  0.0}
				});
		
//		Matrix u = new Matrix(new double[][] {
//				{0.0, 1.0, 0.0},
//				{0.0, 1.0, 4.0},
//				{4.0, 1.0, 4.0},
//				{4.0, 5.0, 4.0},
//				{0.0, 5.0, 4.0},
//				{0.0, 1.0, 4.0},
//				{0.0, 1.0, 0.0},
//				{4.0, 1.0, 0.0},
//				{4.0, 1.0, 4.0},
//				{4.0, 1.0, 0.0},
//				{4.0, 5.0, 0.0},
//				{0.0, 5.0, 0.0},
//				{0.0, 5.0, 4.0},
//				{0.0, 5.0, 0.0},
//				{4.0, 5.0, 0.0},
//				{4.0, 5.0, 4.0},
//				{4.0, 1.0, 4.0},
//				{0.0, 1.0, 4.0},
//				{0.0, 5.0, 4.0},
//				{0.0, 5.0, 0.0},
//				{0.0, 1.0, 0.0},
//				{4.0, 1.0, 0.0},
//				{4.0, 5.0, 0.0},	
//		});
		u = u.transpose();
		
		Matrix z = new Matrix(new double[][] {
				{0.0,  0.0,  0.0},
				{4.0,  0.0,  0.0},
				{4.0,  1.33, 0.0},
				{1.33, 1.33, 0.0},
				{4.0,  4.66, 0.0},
				{4.0,  6.0,  0.0},
				{0.0,  6.0,  0.0},
				{0.0,  4.66, 0.0},
				{2.66, 4.66, 0.0},
				{0.0,  1.33, 0.0},
				{0.0,  0.0,  0.0}
				});
		z = z.transpose();
		
		LetterRotationAnimator animator = new LetterRotationAnimator(l, u, z);
		
		// print
		PrintWriter pw;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter("AnimationOutput.txt")));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
			return;
		}

		for (int t = 0; t < TOTAL_FRAMES; t++) {
			animator.print(pw, t, TOTAL_FRAMES);
		}
		pw.close();
		
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
