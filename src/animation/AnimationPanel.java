package animation;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class AnimationPanel extends JPanel {
	LetterRotationAnimator animator;
	
	public AnimationPanel(LetterRotationAnimator animator) {
		this.animator = animator;
		this.setPreferredSize(new Dimension(animator.getWidth(), animator.getHeight()));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		animator.draw(g);
	}
}
