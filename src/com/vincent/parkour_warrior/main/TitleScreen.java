package com.vincent.parkour_warrior.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class TitleScreen{
	
	ParkourMain parkourMain;
	
	public TitleScreen(ParkourMain parkourMain) {
		
		this.parkourMain = parkourMain;
		
	}
	
	public void drawTitleScreen(Graphics2D graphics2D) {
		
		graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 78));
		graphics2D.setColor(Color.white);
		graphics2D.drawString("Parkour Warrior", centerText("Parkour Warrior", graphics2D), 150);
		
	}
	
	public int centerText(String text, Graphics2D graphics2D) {
		
		int x = parkourMain.screenWidth / 2 - (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth() / 2;
		return x;
		
	}

}