package com.vincent.parkour_warrior.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class NonPlayingScreens{
	
	ParkourMain parkourMain;
	
	public NonPlayingScreens(ParkourMain parkourMain){
		
		this.parkourMain = parkourMain;
		
	}
	
	public void drawTitleScreen(Graphics2D graphics2D) {
		
		graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 70));
		graphics2D.setColor(Color.darkGray);
		graphics2D.drawString("Parkour Warrior", 
		parkourMain.screenWidth / 2 - (int)graphics2D.getFontMetrics().getStringBounds("Parkour Warrior", graphics2D).getWidth() / 2 + 5, 
		parkourMain.tileSize * 3 + 5);
		graphics2D.setColor(Color.white);
		graphics2D.drawString("Parkour Warrior", 
		        parkourMain.screenWidth / 2 - (int)graphics2D.getFontMetrics().getStringBounds("Parkour Warrior", graphics2D).getWidth() / 2, 
		        parkourMain.tileSize * 3);
		
		graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40));

		graphics2D.setColor(Color.white);
		graphics2D.drawString("PLAY", 
		parkourMain.screenWidth / 2 - (int)graphics2D.getFontMetrics().getStringBounds("PLAY", graphics2D).getWidth() / 2, 
		parkourMain.tileSize * 7);

		graphics2D.drawString("CREDITS", 
		parkourMain.screenWidth / 2 - (int)graphics2D.getFontMetrics().getStringBounds("CREDITS", graphics2D).getWidth() / 2, 
		parkourMain.tileSize * 7 + 48);
        
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 20));

		graphics2D.setColor(Color.white);
		graphics2D.drawString("Scroll to choose", 
		parkourMain.screenWidth / 2 - (int)graphics2D.getFontMetrics().getStringBounds("Scroll to choose", graphics2D).getWidth() / 2, 
		parkourMain.tileSize * 10 + 30);
		
	}
	
    public void drawChooseMapScreen(Graphics2D graphics2D) {
		
	}

    public void drawCreditsScreen(Graphics2D graphics2D) {
	
    }

}