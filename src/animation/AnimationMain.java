package animation;

import javax.swing.JFrame;

import jama.Matrix;


public class AnimationMain {
	
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
		
		AnimationPanel panel = new AnimationPanel(animator);
		
		JFrame f = new JFrame("Animator");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//f.setResizable(false);
		f.add(panel);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
