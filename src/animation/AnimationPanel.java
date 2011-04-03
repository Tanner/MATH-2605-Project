package animation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class AnimationPanel extends JPanel implements ActionListener {
	private LetterRotationAnimator animator;
	private Timer animationTimer;
	
	private final int FRAME_RATE = 42;
	private final int TOTAL_FRAMES = 121;
	private int t = 0;
	
	public AnimationPanel(LetterRotationAnimator animator) {
		this.animator = animator;
		this.setPreferredSize(new Dimension(animator.getWidth(), animator.getHeight()));
		
		animationTimer = new Timer(FRAME_RATE, this);
		animationTimer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		animator.draw(g, t, TOTAL_FRAMES);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == animationTimer) {
			repaint();			
			t++;
			
			if (t == TOTAL_FRAMES-1) {
				animationTimer.stop();
			}
		}
	}
}
