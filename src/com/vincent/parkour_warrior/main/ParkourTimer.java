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
	int currentMap = parkourMain.currentMap;
	int recordMinutes = parkourMain.recordTimeMinutes.get(currentMap);
	int recordSeconds = parkourMain.recordTimeSeconds.get(currentMap);
	int recordMilis = parkourMain.recordTimeMiliseconds.get(currentMap);

	// If the record is zero, treat it as uninitialized and set to max value
	if (recordMinutes == 0 && recordSeconds == 0 && recordMilis == 0) {
		recordMinutes = Integer.MAX_VALUE;
		recordSeconds = Integer.MAX_VALUE;
		recordMilis = Integer.MAX_VALUE;
		parkourMain.recordTimeMinutes.set(currentMap, Integer.MAX_VALUE);
		parkourMain.recordTimeSeconds.set(currentMap, Integer.MAX_VALUE);
		parkourMain.recordTimeMiliseconds.set(currentMap, Integer.MAX_VALUE);
	}

	if (
		timerTimeMinutes < recordMinutes ||
		(timerTimeMinutes == recordMinutes && timerTimeSeconds < recordSeconds) ||
		(timerTimeMinutes == recordMinutes && timerTimeSeconds == recordSeconds && timerTimeMiliseconds < recordMilis)
	) {
		parkourMain.recordTimeMinutes.set(currentMap, (int)timerTimeMinutes);
		parkourMain.recordTimeSeconds.set(currentMap, (int)timerTimeSeconds);
		parkourMain.recordTimeMiliseconds.set(currentMap, (int)timerTimeMiliseconds);
	}
	}

}
