package com.vincent.parkour_warrior.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TitleScreen{
	
	ParkourMain parkourMain;
	
	public int choosedMap = 0;
	
	public TitleScreen(ParkourMain parkourMain) {
		
		this.parkourMain = parkourMain;
		
	}
	
	public void drawTitleScreen(Graphics2D graphics2D) {
		
		try {
			
			BufferedImage background = null;
			background = ImageIO.read(getClass().getResourceAsStream("/tile/background.png"));
			for(int y = 0; y < 33; y++) {
				
				for(int x = 0; x < 48; x++) {
					
					graphics2D.drawImage(background, null, x * 16, y * 16);
					
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
		}
		
		graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 78));
		graphics2D.setColor(new Color(0x35BFA3));
		graphics2D.drawString("Parkour Warrior", centerTextX("Parkour Warrior", graphics2D), 150);
		
		int centerIndex = choosedMap; 
        int startIndex = Math.max(0, centerIndex - 1); 
        int endIndex = Math.min(parkourMain.mapPath.size() - 1, centerIndex + 1); 
        
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24));
        graphics2D.setColor(Color.cyan);
        graphics2D.drawString("Select a Map:", centerTextX("Select a Map:", graphics2D), 260);

        for (int i = startIndex; i <= endIndex; i++) {
        	
        	String recordTime = " >Record time: " + 
        	Integer.toString(parkourMain.recordTimeMinutes.get(i)) + ":" +
        	Integer.toString(parkourMain.recordTimeSeconds.get(i)) + "." +
        	Integer.toString(parkourMain.recordTimeMiliseconds.get(i)) + "<";
        	
        	String[] path = parkourMain.mapPath.get(i).split("/");
        	String nameEx = path[path.length - 1];
        	String name = nameEx.substring(0, nameEx.lastIndexOf("."));
            int yPosition = 8 * 44 + (i - centerIndex) * 30;
            
            if (i == choosedMap) {
            	
            	graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 30)); 
            	
            } else {
            	
            	graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24));
            	
            }
            
            graphics2D.drawString(name + recordTime, centerTextX(name + recordTime, graphics2D), yPosition); // Draw each map
            
        }
        
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 10));
        graphics2D.setColor(new Color(0x24CBFF));
        graphics2D.drawString("Press W and S to move, press ENTER to select", centerTextX("Press W and S to move, press ENTER to select", graphics2D), 495);
        graphics2D.drawString("Press Q to toggle music", centerTextX("Press G to toggle music", graphics2D), 505);
        graphics2D.drawString("Press ESC to exit a game without completing", centerTextX("Press ESC to exit a game without completing", graphics2D), 515);
        graphics2D.drawString("Press A to add map ,press D to remove current map", centerTextX("Press A to add map ,press D to remove current map", graphics2D), 525);
        
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