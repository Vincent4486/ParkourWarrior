package net.vincent.parkourwarrior;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class ParkourTimer {

   ParkourMain parkourMain;

   public int endTimeMilis = 0;
   public int endTimeSeconds = 0;
   public int endTimeMinutes = 0;

   public String endTimeMinutesStr;
   public String endTimeSecondsStr;
   public String endTimeMilisecondsStr;

   public long timerTimeMinutes = 0;
   public long timerTimeSeconds = 0;
   public long timerTimeMiliseconds = 0;

   public String timerTimeMinutesStr;
   public String timerTimeSecondsStr;
   public String timerTimeMilisecondsStr;

   public ParkourTimer(ParkourMain parkourMain) {

      this.parkourMain = parkourMain;
   }

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

   public void drawTimer(Graphics2D graphics2D) {

      graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 36));
      graphics2D.setColor(Color.black);
      graphics2D.drawString(timerTimeMinutesStr + ":" + timerTimeSecondsStr +
                               "." + timerTimeMilisecondsStr,
                            50, 50);
   }

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
