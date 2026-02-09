package net.vincent.parkourwarrior;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Finish screen class for Parkour Warrior.
 * <p>
 * This class handles the rendering of the finish screen,
 * displayed when the player completes a map. It shows a
 * congratulatory message along with the completion time.
 * </p>
 *
 * @author Vincent4486
 * @version 1.3
 * @since 1.2
 */
public class FinishScreen {

   /**
    * Reference to the main game panel.
    * @since 1.2
    */
   ParkourMain parkourMain;

   /**
    * Constructs a new {@code FinishScreen} with a reference to
    * the main game panel.
    *
    * @param parkourMain the main game panel instance
    * @since 1.2
    */
   public FinishScreen(ParkourMain parkourMain) {
      this.parkourMain = parkourMain;
   }

   /**
    * Draws the finish screen.
    * <p>
    * Renders the background image, congratulatory message,
    * completion time, and navigation instructions.
    * </p>
    *
    * @param graphics2D the {@code Graphics2D} context to draw on
    * @since 1.2
    */
   public void drawFinishScreen(Graphics2D graphics2D) {
      // Draw the finish screen background and other elements here
      // This is a placeholder for the actual implementation
      try {

         BufferedImage background = null;
         background = ImageIO.read(
            getClass().getResourceAsStream("/tile/background.png"));
         graphics2D.drawImage(background, 0, 0, 768, 529, null);

      } catch (Exception e) {

         e.printStackTrace();
      }

      graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 78));
      graphics2D.setColor(new Color(0x35BFA3));
      graphics2D.drawString("Congratulations!",
                            centerTextX("Congratulations!", graphics2D), 150);

      graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24));
      graphics2D.setColor(new Color(0, 63, 100));
      graphics2D.drawString(
         "You finished in:", centerTextX("You finished in:", graphics2D), 280);
      graphics2D.drawString(
         parkourMain.parkourTimer.endTimeMinutesStr + ":" +
            parkourMain.parkourTimer.endTimeSecondsStr + "." +
            parkourMain.parkourTimer.endTimeMilisecondsStr,
         centerTextX(parkourMain.parkourTimer.endTimeMinutesStr + ":" +
                        parkourMain.parkourTimer.endTimeSecondsStr + "." +
                        parkourMain.parkourTimer.endTimeMilisecondsStr,
                     graphics2D),
         320);

      graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 20));
      graphics2D.setColor(new Color(0x24CBFF));
      graphics2D.drawString(
         "Press ENTER to exit to title screen",
         centerTextX("Press ENTER to eit to title screen", graphics2D), 500);

      // Additional finish screen elements can be added here
   }

   /**
    * Calculates the X position to center a text string horizontally.
    *
    * @param text the text to center
    * @param graphics2D the {@code Graphics2D} context for font metrics
    * @return the X coordinate for centered text
    * @since 1.2
    */
   int centerTextX(String text, Graphics2D graphics2D) {
      return (768 - graphics2D.getFontMetrics().stringWidth(text)) / 2;
   }
}
