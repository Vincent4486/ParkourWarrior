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
		graphics2D.drawString("Parkour Warrior", centerTextX("Parkour Warrior", graphics2D), 150);
		
		int centerIndex = choosedMap; 
        int startIndex = Math.max(0, centerIndex - 1); 
        int endIndex = Math.min(parkourMain.mapPath.size() - 1, centerIndex + 1); 
        
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24));

        graphics2D.setColor(Color.white);
        graphics2D.drawString("Select a Map:", centerTextX("Select a Map:", graphics2D), 312);

        for (int i = startIndex; i <= endIndex; i++) {
            int yPosition = 8*48 + (i - centerIndex) * 30;
            if (i == choosedMap) {
            	graphics2D.setColor(Color.red); 
            } else {
            	graphics2D.setColor(Color.white);
            }
            graphics2D.drawString(parkourMain.mapPath.get(i), centerTextX(parkourMain.mapPath.get(i), graphics2D), yPosition); // Draw each map
        }
        
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 15));
        graphics2D.setColor(Color.white);
        graphics2D.drawString("Press W and S to move, press ENTER to select", centerTextX("Press W and S to move, press ENTER to select", graphics2D), 500);
        
	}
	
	public void drawPauseScreen(Graphics2D graphics2D) {
		
		graphics2D.setColor(Color.black);
		graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 78));
		graphics2D.drawString("PAUSED", centerTextX("PAUSED", graphics2D), centerTextY("PAUSED", graphics2D));
		
	}
	
	public int centerTextX(String text, Graphics2D graphics2D) {
		
		int x = parkourMain.screenWidth / 2 - (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth() / 2;
		return x;
		
	}
	
    public int centerTextY(String text, Graphics2D graphics2D) {
		
		int y = parkourMain.screenHeight / 2 - (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getHeight() / 2;
		return y;
		
	}

}