package net.vincent.ParkourWarrior;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class FinishScreen {

   ParkourMain parkourMain;

   public FinishScreen(ParkourMain parkourMain) {
      this.parkourMain = parkourMain;
   }

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

   int centerTextX(String text, Graphics2D graphics2D) {
      return (768 - graphics2D.getFontMetrics().stringWidth(text)) / 2;
   }
}
