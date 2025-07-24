package main.java.net.vincent.ParkourWarrior;

import javax.swing.*;

public class ParkourWarrior {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame frame = new JFrame("Parkour Warrior");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		ParkourMain parkourMain = new ParkourMain();
		frame.add(parkourMain);
		frame.setIconImage(parkourMain.ico.getImage());
		
		frame.pack();
		frame.setVisible(true);
		
		parkourMain.thread.start();

	}

}
