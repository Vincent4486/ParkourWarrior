package net.vincent.parkourwarrior;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Timer class for Parkour Warrior.
 * <p>
 * This class manages the in-game timer, tracking elapsed time
 * during gameplay, rendering the timer on screen, and saving
 * record times when a map is completed.
 * </p>
 *
 * @author Vincent4486
 * @version 1.3
 * @since 1.1
 */
public class ParkourTimer {

   /**
    * Reference to the main game panel.
    * @since 1.1
    */
   ParkourMain parkourMain;

   /**
    * The end time milliseconds when a map is completed.
    * @since 1.1
    */
   public int endTimeMilis = 0;

   /**
    * The end time seconds when a map is completed.
    * @since 1.1
    */
   public int endTimeSeconds = 0;

   /**
    * The end time minutes when a map is completed.
    * @since 1.1
    */
   public int endTimeMinutes = 0;

   /**
    * Formatted string for the end time minutes.
    * @since 1.1
    */
   public String endTimeMinutesStr;

   /**
    * Formatted string for the end time seconds.
    * @since 1.1
    */
   public String endTimeSecondsStr;

   /**
    * Formatted string for the end time milliseconds.
    * @since 1.1
    */
   public String endTimeMilisecondsStr;

   /**
    * The current timer minutes.
    * @since 1.1
    */
   public long timerTimeMinutes = 0;

   /**
    * The current timer seconds.
    * @since 1.1
    */
   public long timerTimeSeconds = 0;

   /**
    * The current timer milliseconds.
    * @since 1.1
    */
   public long timerTimeMiliseconds = 0;

   /**
    * Formatted string for the current timer minutes.
    * @since 1.1
    */
   public String timerTimeMinutesStr;

   /**
    * Formatted string for the current timer seconds.
    * @since 1.1
    */
   public String timerTimeSecondsStr;

   /**
    * Formatted string for the current timer milliseconds.
    * @since 1.1
    */
   public String timerTimeMilisecondsStr;

   /**
    * Constructs a new {@code ParkourTimer} with a reference to
    * the main game panel.
    *
    * @param parkourMain the main game panel instance
    * @since 1.1
    */
   public ParkourTimer(ParkourMain parkourMain) {

      this.parkourMain = parkourMain;
   }

   /**
    * Runs the timer by calculating elapsed time since the start.
    * <p>
    * Computes minutes, seconds, and milliseconds from the elapsed
    * nanoseconds and formats them into display strings.
    * </p>
    *
    * @param startTime the start time in nanoseconds
    * @param FPS the current frames per second
    * @since 1.1
    */
   public void runTimer(long startTime, double FPS) {

      long currentTime = System.nanoTime();

      long elapsedTime = currentTime - startTime;

      timerTimeMiliseconds = (elapsedTime / 1_000_000) % 1000;
      timerTimeSeconds = ((elapsedTime / 1_000_000) / 1000) % 60;
      timerTimeMinutes = ((elapsedTime / 1_000_000) / 1000 / 60);

      // Format the time strings
      timerTimeMinutesStr = Long.toString(timerTimeMinutes);
      timerTimeSecondsStr = String.format("%02d", timerTimeSeconds);
      timerTimeMilisecondsStr = String.format("%03d", timerTimeMiliseconds);

      // System.out.println(timerTimeMiliseconds + " | " +
      // timerTimeMilisecondsStr + " | " + timerTimeSeconds + " | " +
      // timerTimeSecondsStr);
   }

   /**
    * Draws the timer on the screen.
    *
    * @param graphics2D the {@code Graphics2D} context to draw on
    * @since 1.1
    */
   public void drawTimer(Graphics2D graphics2D) {

      graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 36));
      graphics2D.setColor(Color.black);
      graphics2D.drawString(timerTimeMinutesStr + ":" + timerTimeSecondsStr +
                               "." + timerTimeMilisecondsStr,
                            50, 50);
   }

   /**
    * Saves the current timer as the end time and updates
    * the record time if it is faster than the previous record.
    *
    * @since 1.1
    */
   public void saveTime() {

      int currentMap = parkourMain.currentMap;
      int recordMinutes = parkourMain.recordTimeMinutes.get(currentMap);
      int recordSeconds = parkourMain.recordTimeSeconds.get(currentMap);
      int recordMilis = parkourMain.recordTimeMiliseconds.get(currentMap);

      endTimeMilis = (int)timerTimeMiliseconds;
      endTimeSeconds = (int)timerTimeSeconds;
      endTimeMinutes = (int)timerTimeMinutes;

      System.out.println("End time: " + endTimeMinutes + ":" + endTimeSeconds +
                         "." + endTimeMilis);

      endTimeMilisecondsStr = String.format("%03d", endTimeMilis);
      endTimeSecondsStr = String.format("%02d", endTimeSeconds);
      endTimeMinutesStr = String.format("%02d", endTimeMinutes);

      if (recordMilis == 0 && recordSeconds == 0 && recordMinutes == 0) {

         recordMilis = Integer.MAX_VALUE;
         recordMinutes = Integer.MAX_VALUE;
         recordSeconds = Integer.MAX_VALUE;
      }

      if (endTimeMinutes < recordMinutes) {

         parkourMain.recordTimeMinutes.set(currentMap, endTimeMinutes);
         parkourMain.recordTimeSeconds.set(currentMap, endTimeSeconds);
         parkourMain.recordTimeMiliseconds.set(currentMap, endTimeMilis);

      } else if (endTimeMinutes == recordMinutes &&
                 endTimeSeconds < recordSeconds) {

         parkourMain.recordTimeMinutes.set(currentMap, endTimeMinutes);
         parkourMain.recordTimeSeconds.set(currentMap, endTimeSeconds);
         parkourMain.recordTimeMiliseconds.set(currentMap, endTimeMilis);

      } else if (endTimeMinutes == recordMinutes &&
                 endTimeSeconds == recordSeconds &&
                 endTimeMilis < recordMilis) {

         parkourMain.recordTimeMinutes.set(currentMap, endTimeMinutes);
         parkourMain.recordTimeSeconds.set(currentMap, endTimeSeconds);
         parkourMain.recordTimeMiliseconds.set(currentMap, endTimeMilis);
      }
   }
}
