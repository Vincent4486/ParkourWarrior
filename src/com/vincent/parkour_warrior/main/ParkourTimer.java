package com.vincent.parkour_warrior.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class ParkourTimer {
	
	ParkourMain parkourMain;
	
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

	}
	
	public void drawTimer(Graphics2D graphics2D) {
		
		graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 36));
		graphics2D.setColor(Color.black);
		graphics2D.drawString(timerTimeMinutesStr+":"+timerTimeSecondsStr+"."+timerTimeMilisecondsStr, 50, 50);
		
	}
	
	public void saveTime() {
		
		if(timerTimeMinutes < parkourMain.recordTimeMinutes.get(parkourMain.currentMap)) {
			
			parkourMain.recordTimeMinutes.set(parkourMain.currentMap, (int)timerTimeMinutes);
			
		}else if(timerTimeSeconds < parkourMain.recordTimeSeconds.get(parkourMain.currentMap)) {
			
			parkourMain.recordTimeSeconds.set(parkourMain.currentMap, (int)timerTimeSeconds);
			
		}else if(timerTimeMiliseconds < parkourMain.recordTimeMiliseconds.get(parkourMain.currentMap)) {
			
			parkourMain.recordTimeMiliseconds.set(parkourMain.currentMap, (int)timerTimeMiliseconds);
			
		}
		//System.out.println(parkourMain.recordTimeMiliseconds.get(parkourMain.currentMap));
		parkourMain.propertiesData.saveProperties();
		
	}

}
