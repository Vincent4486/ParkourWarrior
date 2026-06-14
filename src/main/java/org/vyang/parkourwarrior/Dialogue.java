package org.vyang.parkourwarrior;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Dialogue {
   ParkourMain parkourMain;

   public int dialogueOption = 0;
   public static final int DIALOGUE_OPTION_YES_NO = 0;
   public static final int DIALOGUE_OPTION_YES = 1;
   public static final int DIALOGUE_OPTION_NO = 2;
   public static final int DIALOGUE_OPTION_NONE = 3;

   public int currentSelection = 0;
   public static final int DIALOGUE_CURRENT_YES = 1;
   public static final int DIALOGUE_CURRENT_NO = 0;

   public Dialogue(ParkourMain parkourMain) { this.parkourMain = parkourMain; }

   public void drawDialogue(Graphics2D graphics2D, String title, String text) {
      if (dialogueOption == DIALOGUE_OPTION_NONE) {
         drawDialogueInform(graphics2D, title, text);
      } else {
         drawDialogueOption(graphics2D, title, text);
      }
   }

   private void drawDialogueInform(Graphics2D graphics2D, String title,
                                   String text) {
      try {

         BufferedImage background = null;
         background = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/tile/background.png")));
         graphics2D.drawImage(background, 0, 0, 768, 529, null);

      } catch (Exception e) {
         e.printStackTrace();
      }

      graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 78));
      graphics2D.setColor(new Color(0x35BFA3));
      graphics2D.drawString(title, centerTextX(title, graphics2D), 150);

      graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24));
      graphics2D.setColor(new Color(0, 63, 100));
      graphics2D.drawString(text, centerTextX(text, graphics2D), 280);

      graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 20));
      graphics2D.setColor(new Color(0x24CBFF));
      graphics2D.drawString("Press ENTER to proceed",
                            centerTextX("Press ENTER to proceed", graphics2D),
                            500);
   }

   private void drawDialogueOption(Graphics2D graphics2D, String title,
                                   String text) {
      try {

         BufferedImage background = null;
         background = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/tile/background.png")));
         graphics2D.drawImage(background, 0, 0, 768, 529, null);

      } catch (Exception e) {
         e.printStackTrace();
      }

      graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 78));
      graphics2D.setColor(new Color(0x35BFA3));
      graphics2D.drawString(title, centerTextX(title, graphics2D), 150);

      graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24));
      graphics2D.setColor(new Color(0, 63, 100));
      graphics2D.drawString(text, centerTextX(text, graphics2D), 280);

      String yesText = "Yes";
      String noText = "No";
      if (dialogueOption == DIALOGUE_OPTION_NO) {
         graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 24));
         graphics2D.drawString(noText, centerTextX(text, graphics2D), 280);
      } else if (dialogueOption == DIALOGUE_OPTION_YES) {
         graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 24));
         graphics2D.drawString(yesText, centerTextX(text, graphics2D), 280);
      } else if (dialogueOption == DIALOGUE_OPTION_YES_NO) {
         if (currentSelection == DIALOGUE_CURRENT_YES) {
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 24));
         } else {
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24));
         }
         graphics2D.drawString(yesText, centerTextX(text, graphics2D), 280);

         if (currentSelection == DIALOGUE_CURRENT_NO) {
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 24));
         } else {
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24));
         }
         graphics2D.drawString(noText, centerTextX(text, graphics2D), 280);
      }

      graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 20));
      graphics2D.setColor(new Color(0x24CBFF));
      graphics2D.drawString(
         "A/D to move, ENTER to select",
         centerTextX("A/D to move, ENTER to select", graphics2D), 500);
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
