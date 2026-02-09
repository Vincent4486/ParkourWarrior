package net.vincent.parkourwarrior;

import javax.swing.*;

/**
 * The entry point for the Parkour Warrior game.
 * <p>
 * This class initializes the game window, sets up the main game panel,
 * and starts the game thread. It uses Swing to create a fixed-size window
 * and embeds the {@code ParkourMain} component, which handles game logic and
 * rendering.
 * </p>
 *
 * @author Vincent
 * @version 1.0
 * @since v1.0
 */
public class ParkourWarrior {

   /**
    * Launches the Parkour Warrior game.
    * <p>
    * This method creates the main application window, attaches the game panel,
    * sets the window icon, and starts the game thread.
    * </p>
    *
    * @param args command-line arguments (not used)
    */
   public static void main(String[] args) {
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