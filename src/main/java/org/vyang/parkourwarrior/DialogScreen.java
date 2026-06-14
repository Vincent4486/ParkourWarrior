package org.vyang.parkourwarrior;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.function.Consumer;
import javax.imageio.ImageIO;

/**
 * Dialog screen class for Parkour Warrior.
 * <p>
 * This class handles rendering of overlay dialogs on the game screen,
 * supporting both informational messages (with a simple "Press ENTER to
 * proceed" prompt) and yes/no selection prompts. Navigation and results
 * are handled via a callback mechanism.
 * </p>
 *
 * @author Vincent4486
 * @version 1.4
 * @since 1.4
 */
public class DialogScreen {

   /**
    * Reference to the main game panel.
    * @since 1.4
    */
   ParkourMain parkourMain;

   /**
    * The current dialog mode determining which type of prompt to display.
    * @since 1.4
    */
   public int dialogueOption = 0;

   /**
    * Constant for yes/no selection dialog.
    * @since 1.4
    */
   public static final int DIALOGUE_OPTION_YES_NO = 0;

   /**
    * Constant for dialog with only a yes option.
    * @since 1.4
    */
   public static final int DIALOGUE_OPTION_YES = 1;

   /**
    * Constant for dialog with only a no option.
    * @since 1.4
    */
   public static final int DIALOGUE_OPTION_NO = 2;

   /**
    * Constant for informational dialog without selection.
    * @since 1.4
    */
   public static final int DIALOGUE_OPTION_NONE = 3;

   /**
    * The currently highlighted selection (yes or no).
    * @since 1.4
    */
   public int currentSelection = 0;

   /**
    * Constant for the yes selection state.
    * @since 1.4
    */
   public static final int DIALOGUE_CURRENT_YES = 1;

   /**
    * Constant for the no selection state.
    * @since 1.4
    */
   public static final int DIALOGUE_CURRENT_NO = 0;

   /**
    * The target map state to transition to when yes is selected
    * (used as fallback when no callback is set).
    * @since 1.4
    */
   public int gotoMapState = 0;

   /**
    * The map state to return to when no is selected
    * (used as fallback when no callback is set).
    * @since 1.4
    */
   public int initialMapState = 0;

   /**
    * Callback invoked with the dialog result ({@code true} for yes,
    * {@code false} for no). When set, this takes precedence over
    * {@code gotoMapState} and {@code initialMapState}.
    * @since 1.4
    */
   public Consumer<Boolean> callback = null;

   /**
    * The title text displayed at the top of the dialog.
    * @since 1.4
    */
   String title;

   /**
    * The body text displayed in the center of the dialog.
    * @since 1.4
    */
   String text;

   /**
    * Constructs a new {@code DialogScreen} with a reference to
    * the main game panel.
    *
    * @param parkourMain the main game panel instance
    * @since 1.4
    */
   public DialogScreen(ParkourMain parkourMain) { this.parkourMain = parkourMain; }

   /**
    * Draws the dialog screen based on the current {@code dialogueOption}.
    * <p>
    * Displays an informational dialog when set to {@code DIALOGUE_OPTION_NONE},
    * or a selection dialog (yes/no) for other modes.
    * </p>
    *
    * @param graphics2D the {@code Graphics2D} context to draw on
    * @since 1.4
    */
   public void drawDialogScreen(Graphics2D graphics2D) {
      if (dialogueOption == DIALOGUE_OPTION_NONE) {
         drawDialogScreenInform(graphics2D, this.title, this.text);
      } else {
         drawDialogScreenOption(graphics2D, this.title, this.text);
      }
   }

   /**
    * Draws an informational dialog with a title, message text,
    * and a "Press ENTER to proceed" prompt.
    *
    * @param graphics2D the {@code Graphics2D} context to draw on
    * @param title the dialog title to display
    * @param text the dialog body text to display
    * @since 1.4
    */
   private void drawDialogScreenInform(Graphics2D graphics2D, String title,
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

   /**
    * Draws a selection dialog with yes/no options centered as a block,
    * highlighting the currently selected option in bold.
    *
    * @param graphics2D the {@code Graphics2D} context to draw on
    * @param title the dialog title to display
    * @param text the dialog body text to display
    * @since 1.4
    */
   private void drawDialogScreenOption(Graphics2D graphics2D, String title,
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
      int yesWidth =
         graphics2D.getFontMetrics().stringWidth(yesText);
      int noWidth =
         graphics2D.getFontMetrics().stringWidth(noText);
      int gap = 20;
      int blockWidth = yesWidth + gap + noWidth;
      int blockStartX = (768 - blockWidth) / 2;
      int optionY = 320;
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
         graphics2D.drawString(yesText, blockStartX, optionY);

         if (currentSelection == DIALOGUE_CURRENT_NO) {
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 24));
         } else {
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24));
         }
         graphics2D.drawString(noText, blockStartX + yesWidth + gap, optionY);
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
