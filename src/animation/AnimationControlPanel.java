package animation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;

public class AnimationControlPanel extends JPanel implements AnimationPanelDelegate {
	private AnimationControlPanelDelegate delegate;
	
	private JButton prevButton;
	private JButton animateButton;
	private JButton nextButton;
	private JTextField frameField;
	private JButton frameButton;
	private JButton saveButton;
	
	public AnimationControlPanel() {
		AnimationControlPanelActionListener listener = new AnimationControlPanelActionListener();
		
		prevButton = new JButton("Previous Frame");
		prevButton.addActionListener(listener);
		
		animateButton = new JButton("Play");
		animateButton.addActionListener(listener);
		
		nextButton = new JButton("Next Frame");
		nextButton.addActionListener(listener);
		
		frameField = new JTextField(5);
		frameField.setText("0");
		frameField.addActionListener(listener);
		
		frameButton = new JButton("Set Frame");
		frameButton.addActionListener(listener);
		
		saveButton = new JButton("Save Frame");
		saveButton.addActionListener(listener);
		
		add(prevButton);
		add(animateButton);
		add(nextButton);
		add(frameField);
		add(frameButton);
		add(saveButton);
	}
	
	public void setDelegate(AnimationControlPanelDelegate delegate) {
		this.delegate = delegate;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, getWidth(), 1);
	}
	
	private class AnimationControlPanelActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == animateButton) {
				if (delegate.animate()) {
					animateButton.setText("Pause");
				} else {
					animateButton.setText("Play");
				}
			}
			
			else if (e.getSource() == prevButton) {
				delegate.showPrevFrame();
			}
			
			else if (e.getSource() == nextButton) {
				delegate.showNextFrame();
			}
			
			else if (e.getSource() == frameField || e.getSource() == frameButton) {
				int t;
				try {
					t = Integer.parseInt(frameField.getText());
				} catch (NumberFormatException exc) {
					exc.printStackTrace();
					
					JOptionPane.showMessageDialog(null, "Invalid frame. Frame must be greater than 0 and less than 121.");
					
					return;
				}
				delegate.showFrame(t);
			}
			
			else if (e.getSource() == saveButton) {
				delegate.saveCurrentFrame();
			}
		}
	}
	
	/*
	 * -------------------------------------
	 * AnimationPanelDelegate Methods
	 * -------------------------------------
	 */

	public void timeChanged(int t, boolean isAnimating) {
		if (isAnimating) {
			prevButton.setEnabled(false);
			nextButton.setEnabled(false);
		} else if (t == 0) {
			prevButton.setEnabled(false);
			nextButton.setEnabled(true);
		} else if (t == 120) {
			nextButton.setEnabled(false);
			prevButton.setEnabled(true);
		} else {
			prevButton.setEnabled(true);
			nextButton.setEnabled(true);
		}
		
		saveButton.setEnabled(!isAnimating);
		frameField.setEnabled(!isAnimating);
		frameButton.setEnabled(!isAnimating);
		
		frameField.setText("" + t);
	}

	public void animationEnded() {
		animateButton.setText("Play");
	}
}

interface AnimationControlPanelDelegate {
	public boolean animate();
	public void showNextFrame();
	public void showPrevFrame();
	public void showFrame(int t);
	public void saveCurrentFrame();
}
