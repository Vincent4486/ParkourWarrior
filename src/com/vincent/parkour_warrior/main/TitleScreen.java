package com.vincent.parkour_warrior.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class TitleScreen{
	
	ParkourMain parkourMain;
	
	public int choosedMap = 0;
	
	public TitleScreen(ParkourMain parkourMain) {
		
		this.parkourMain = parkourMain;
		
	}
	
	public void drawTitleScreen(Graphics2D graphics2D) {
		
		graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 78));
		graphics2D.setColor(Color.white);
		graphics2D.drawString("Parkour Warrior", centerText("Parkour Warrior", graphics2D), 150);
		
		int centerIndex = choosedMap; // Center the selected map
        int startIndex = Math.max(0, centerIndex - 1); // Start index for three maps
        int endIndex = Math.min(parkourMain.mapPath.size() - 1, centerIndex + 1); // End index for three maps
        
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24));

        // Draw the selected map
        graphics2D.setColor(Color.white);
        graphics2D.drawString("Select a Map:", centerText("Select a Map:", graphics2D), 312);

        // Draw three maps centered around the selected map
        for (int i = startIndex; i <= endIndex; i++) {
            int yPosition = 8*48 + (i - centerIndex) * 30; // Calculate vertical position based on selection
            if (i == choosedMap) {
            	graphics2D.setColor(Color.red); // Highlight selected map
            } else {
            	graphics2D.setColor(Color.white);
            }
            graphics2D.drawString(parkourMain.mapPath.get(i), centerText(parkourMain.mapPath.get(i), graphics2D), yPosition); // Draw each map
        }
		
	}
	
	public int centerText(String text, Graphics2D graphics2D) {
		
		int x = parkourMain.screenWidth / 2 - (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth() / 2;
		return x;
		
	}

}