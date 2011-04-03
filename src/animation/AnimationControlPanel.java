package animation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;

public class AnimationControlPanel extends JPanel implements AnimationPanelDelegate {
	private AnimationControlPanelDelegate delegate;
	
	private JButton animateButton;
	private JTextField frameField;
	private JButton frameButton;
	private JButton saveButton;
	
	public AnimationControlPanel() {
		AnimationControlPanelActionListener listener = new AnimationControlPanelActionListener();
		
		animateButton = new JButton("Play");
		animateButton.addActionListener(listener);
		
		frameField = new JTextField(5);
		frameField.setText("0");
		frameField.addActionListener(listener);
		
		frameButton = new JButton("Set Frame");
		frameButton.addActionListener(listener);
		
		saveButton = new JButton("Save Frame");
		saveButton.addActionListener(listener);
		
		add(animateButton);
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
			} else if (e.getSource() == frameField || e.getSource() == frameButton) {
				int t;
				try {
					t = Integer.parseInt(frameField.getText());
				} catch (NumberFormatException exc) {
					exc.printStackTrace();
					
					JOptionPane.showMessageDialog(null, "Invalid frame. Frame must be greater than 0 and less than 121.");
					
					return;
				}
				delegate.showFrame(t);
			} else if (e.getSource() == saveButton) {
				delegate.saveCurrentFrame();
			}
		}
	}
	
	/*
	 * -------------------------------------
	 * AnimationPanelDelegate Methods
	 * -------------------------------------
	 */

	public void timeChanged(int t) {
		frameField.setText("" + t);
	}

	public void animationEnded() {
		animateButton.setText("Play");
	}
}

interface AnimationControlPanelDelegate {
	public boolean animate();
	public void showFrame(int t);
	public void saveCurrentFrame();
}
