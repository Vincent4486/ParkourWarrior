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
        	
        	String recordTime = " >Record time: " + 
        	Integer.toString(parkourMain.recordTimeMinutes.get(i)) + ":" +
        	Integer.toString(parkourMain.recordTimeSeconds.get(i)) + "." +
        	Integer.toString(parkourMain.recordTimeMiliseconds.get(i)) + "<";
        	
        	String[] path = parkourMain.mapPath.get(i).split("/");
        	String nameEx = path[path.length - 1];
        	String name = nameEx.substring(0, nameEx.lastIndexOf("."));
            int yPosition = 8*48 + (i - centerIndex) * 30;
            
            if (i == choosedMap) {
            	
            	graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 30)); 
            	
            } else {
            	
            	graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24));
            	
            }
            
            graphics2D.drawString(name + recordTime, centerTextX(name + recordTime, graphics2D), yPosition); // Draw each map
            
        }
        
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 15));
        graphics2D.setColor(Color.white);
        graphics2D.drawString("Press W and S to move, press ENTER to select, press A to add map ,press D to remove map", centerTextX("Press W and S to move, press ENTER to select, press A to add map , press D to remove map", graphics2D), 500);
        
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