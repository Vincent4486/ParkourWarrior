package com.vincent.parkour_warrior.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.net.URL;
import java.util.ArrayList;

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
    public ArrayList<Integer> mapNumber;
	public ArrayList<String> mapPath;
	public ArrayList<Integer> mapType;
	public ArrayList<Boolean> isDefaultMap;
	public ArrayList<Boolean> haveFinishedMap;
	public ArrayList<Integer> recordTimeMinutes;
	public ArrayList<Integer> recordTimeSeconds;
	public ArrayList<Integer> recordTimeMiliseconds;
	
	public int currentMapState = 0;
	public int currentMap = 0;
	public final int title = 0;
	public final int play = 1;
	public final int defaultPlayMap = 1;
	public final int customPlayMap = 2;
	
	Thread thread;
	URL soundURL;
	Clip soundClip;
	
	public Player player;
	public TileManager tileManager;
	public PropertiesData propertiesData;
	public TitleScreen titleScreen;
	
	public ParkourMain() {
		
		mapNumber = new ArrayList<>();
		mapPath = new ArrayList<>();
	    mapType = new ArrayList<>();
	    isDefaultMap = new ArrayList<>();
	    haveFinishedMap = new ArrayList<>();
	    recordTimeMinutes = new ArrayList<>();
	    recordTimeSeconds = new ArrayList<>();
	    recordTimeMiliseconds = new ArrayList<>();
	    
	    propertiesData = new PropertiesData(this);
	    propertiesData.loadProperties();
	    titleScreen = new TitleScreen(this);
		player = new Player(this);
	    tileManager = new TileManager(this);
	    
		tileManager.loadMap(mapPath);
		
		thread = new Thread(this);
		
		getSound();
		
		soundClip.start();
		soundClip.loop(Clip.LOOP_CONTINUOUSLY);
	
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
		
		if(currentMapState == play) {
			
			tileManager.drawTile(graphics2D);
			player.drawPlayer(graphics2D);
			
		}
		else if(currentMapState == title) {
			
			titleScreen.drawTitleScreen(graphics2D);
			
		}
	
		graphics2D.dispose();
		
	}
	
	public void getSound() {
		
        soundURL = getClass().getResource("/sound/ParkourWarrior.wav");
		
		try {
			
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
			soundClip = AudioSystem.getClip();
			soundClip.open(audioInputStream);
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}

}
