package com.vincent.parkour_warrior.main;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TileManager {
	
	ParkourMain parkourMain;
	public Tile[] tile;
	
	int mapTileNumber[][];
	
	public int tileNumber;
	
	public TileManager(ParkourMain parkourMain) {
		
		this.parkourMain = parkourMain;
		
		tile = new Tile[15];
		
		mapTileNumber = new int[parkourMain.maxWorldColumn][parkourMain.maxWorldRow];
		
		getTile();
		
		loadMap(parkourMain.mapPath);
		
	}
	
	public void drawTile(Graphics2D graphics2D) {
		
		/*
		 * width == column
		 * height == row
		 */
		
		int column = 0;
		int row = 0;
		int y = 0;
		
		while(column < parkourMain.maxWorldColumn && row < parkourMain.maxHeightTiles) {
			
			tileNumber = mapTileNumber[column][row];
			
			int worldX = column * parkourMain.tileSize;
			int screenX = worldX - parkourMain.player.worldX + parkourMain.player.screenX;
			
			graphics2D.drawImage(tile[tileNumber].image, screenX, y, parkourMain.tileSize, parkourMain.tileSize, null);
			column ++;
			
			if(column == parkourMain.maxWorldColumn) {
				
				column = 0;
				row++;
				y += parkourMain.tileSize;
				
			}
			
		}
		
	}
	
	public void loadMap(ArrayList<String> mapPath) {
		
		try {
			
			InputStream inputStream = getClass().getResourceAsStream(mapPath.get(0));
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			int column = 0;
			int row = 0;
			
			while(column < parkourMain.maxWorldColumn && row < parkourMain.maxWorldRow) {

				String line = bufferedReader.readLine();
				
				while(column < parkourMain.maxWorldColumn) {

					String numbers[] = line.split(" ");
					
					int number = Integer.parseInt(numbers[column]);
					
					mapTileNumber[column][row] = number;
					
					column++;
					
				}
				
				if(column == parkourMain.maxWorldColumn) {
					
					column = 0;
					row ++;
					
				}
				
			}
			
		    bufferedReader.close();
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	public void getTile() {
		
		try {

			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tile/brick.png"));
			tile[0].solidTile = true;
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tile/cloud.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tile/flag.png"));
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tile/grass.png"));
			tile[3].solidTile = true;
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tile/sand.png"));
			tile[4].solidTile = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tile/sky.png"));
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tile/water.png"));
			tile[6].solidTile = true;
			
			/*
			 * Below is barrier layer for boarder of map.
			 * vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
			 */
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tile/brick.png"));
			tile[7].solidTile = true;
			
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tile/sky.png"));
			tile[8].solidTile = true;
			
			/*
			 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
			 */
			
		}catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

}
