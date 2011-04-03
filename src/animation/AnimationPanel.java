package animation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AnimationPanel extends JPanel implements ActionListener, AnimationControlPanelDelegate {
	private AnimationPanelDelegate delegate;
	
	private LetterRotationAnimator animator;
	private Timer animationTimer;
	
	private final int FRAME_RATE = 42;
	private final int TOTAL_FRAMES = 121;
	private int t = 0;
	
	public AnimationPanel(LetterRotationAnimator animator) {
		this.animator = animator;
		this.setPreferredSize(new Dimension(animator.getWidth(), animator.getHeight()));
		
		animationTimer = new Timer(FRAME_RATE, this);
	}
	
	public void setDelegate(AnimationPanelDelegate delegate) {
		this.delegate = delegate;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		animator.draw(g, t, TOTAL_FRAMES);

		delegate.timeChanged(t, animationTimer.isRunning());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == animationTimer) {
			repaint();
			t++;
			
			if (t >= TOTAL_FRAMES-1) {
				animationTimer.stop();
				delegate.animationEnded();
			}
		}
	}

	/*
	 * -------------------------------------
	 * AnimationControlPanelDelegate Methods
	 * -------------------------------------
	 */
	
	public boolean animate() {
		if (animationTimer.isRunning()) {
			animationTimer.stop();
			
			delegate.timeChanged(t, animationTimer.isRunning());
		} else {
			if (t == TOTAL_FRAMES-1) {
				t = 0;
			}
			
			animationTimer.start();
		}
		
		return animationTimer.isRunning();
	}
	
	public void showFrame(int t) {
		if (t < 0 || t > TOTAL_FRAMES-1) {
			JOptionPane.showMessageDialog(null, "Invalid frame. Frame must be greater than 0 and less than 121.");
			
			// force text field back to previous value
			delegate.timeChanged(this.t, animationTimer.isRunning());
			
			return;
		}
		
		this.t = t;
		repaint();
	}

	public void saveCurrentFrame() {
		BufferedImage bufferedImage = new BufferedImage(animator.getWidth(), animator.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D image = bufferedImage.createGraphics();
		animator.draw(image, t, TOTAL_FRAMES);
				
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setSelectedFile(new File("" + t + ".png"));

		int returnVal = fileChooser.showSaveDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();

			try {
				ImageIO.write(bufferedImage, "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void showNextFrame() {
		t++;
		repaint();
	}

	public void showPrevFrame() {
		t--;
		repaint();
	}
}

interface AnimationPanelDelegate {
	public void timeChanged(int t, boolean isAnimating);
	public void animationEnded();
}