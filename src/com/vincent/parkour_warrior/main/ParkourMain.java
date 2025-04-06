package com.vincent.parkour_warrior.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ParkourMain extends JPanel implements Runnable{
	
	final int originalTileSize = 16;
	final int scale = 3;
	
	/*
	 * width == column
	 * height == row
	 * vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
	 */
	
	public final int tileSize = originalTileSize * scale;
	public final int maxWidthTiles = 16;
	public final int maxHeightTiles = 11;
	public final int screenWidth = tileSize * maxWidthTiles;
	public final int screenHeight = tileSize * maxHeightTiles;
	public final int maxWorldColumn = 68;
	public final int maxWorldRow = 11;
	public final int worldWidth = tileSize * maxWorldColumn;
	public final int worldHeight = tileSize * maxWorldRow;
	
	/*
	 * public int recordTimeMiliseconds is 2 digit miliseconds.
	 * public int currentMap means current map number, number is in properties file.
	 */
    public int[] mapNumber = {};
	public String[] mapPath = {"/map/map0.txt"};
	public boolean[] isDefaltMap = {};
	public boolean[] isStartMap = {};
	public boolean[] haveFinishedMap = {};
	public int[] recordTimeMinutes = {};
	public int[] recordTimeSeconds = {};
	public int[] recordTimeMiliseconds = {};
	
	public int currentMap = 0;
	public int choosedMap = 2;
	public final int titleScreen = 0;
	public final int chooseMap = 1;
	public final int credits = 2;
	public final int playing = 3;
	
	Thread thread;
	URL soundURL;
	Clip sound;
	Font pixel;
	
	public Player player = new Player(this);
	public TileManager tileManager = new TileManager(this);
	public PropertiesData propertiesData = new PropertiesData(this);
	public NonPlayingScreens nonPlayingScreens = new NonPlayingScreens(this);
	
	public ParkourMain() {
		
		thread = new Thread(this);
		
		soundURL = getClass().getResource("/sound/ParkourWarrior.wav");
		
		try {
			
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
			sound = AudioSystem.getClip();
			sound.open(audioInputStream);
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
		sound.start();
		sound.loop(Clip.LOOP_CONTINUOUSLY);
	
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(player);
		this.setFocusable(true);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		double drawInterval = 1000000000 / 60;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(thread != null) {
			
			update();
			
			repaint();
			 
			try {
				
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;
				
				if(remainingTime < 0) {remainingTime = 0;}
				
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				
			}
			
		}
		
	}
	
	public void update() {
		
		player.updatePlayer();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D graphics2D = (Graphics2D)g;
		
		switch(currentMap) {
		case titleScreen:
			nonPlayingScreens.drawTitleScreen(graphics2D);
			break;
		case chooseMap:
			nonPlayingScreens.drawChooseMapScreen(graphics2D);
			break;
		case credits:
			nonPlayingScreens.drawCreditsScreen(graphics2D);
			break;
		case playing:
			tileManager.drawTile(graphics2D);
			player.drawPlayer(graphics2D);
			break;
		}

		graphics2D.dispose();
		
	}

}
