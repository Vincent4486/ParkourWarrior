package main.java.net.vincent.ParkourWarrior;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player implements KeyListener{
	
	ParkourMain parkourMain;
	
	public boolean askJump = false;
	public boolean jumping = false;
	public boolean falling = false;
	public boolean goRight = false;
	public boolean goLeft = false;
	public boolean sneaking = false; // Added for sneaking functionality
	public boolean collideUp;
	public boolean collideDown;
	public boolean collideLeft;
	public boolean collideRight;
	
	public int worldX = 480;
	public int worldY = 376; // Starting Y position, can be adjusted as needed
	public int maxJumpHeight;
	public int jumpHeight = 61;
	public int jumpSpeed = 5;
	public int fallSpeed = 4;
	public int walkSpeed = 6;
	public final int screenX;
	int imageCount = 0;
	int imageNumber = 0;
	
	int tileNumber1;
	int tileNumber2;
	
	/*
	 *In integer playerDirection:
	 *1 = playerDirection:left
	 *2 = playerDirection:right
	 */
	
	public int playerDirection = 2;
	
	BufferedImage leftImage1, rightImage1, leftImage2, rightImage2;
	Rectangle solidArea = new Rectangle();
	
	public Player(ParkourMain parkourMain) {
		
		solidArea.x = 6;
		solidArea.y = 0;
		solidArea.height = 48;
		solidArea.width = 36;
		
		this.parkourMain = parkourMain;
		
		screenX = parkourMain.screenWidth / 2 - parkourMain.tileSize / 2;
		
		getPlayer();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if(parkourMain.currentMapState == parkourMain.play) {
			
		   switch(e.getKeyCode()) {
		   case KeyEvent.VK_A:goLeft = true;break;
		   case KeyEvent.VK_D:goRight = true;break;
		   case KeyEvent.VK_SPACE:

			   if (!askJump) {
				
				   maxJumpHeight = worldY - jumpHeight;

				   askJump = true;
				
			   }
			   break;
		   case KeyEvent.VK_ESCAPE:parkourMain.currentMapState = parkourMain.title;worldX = 480; worldY= 376;break;
		   case KeyEvent.VK_SHIFT:sneaking = true;walkSpeed = 2;break;
		   }
		
		}
		
		if(parkourMain.currentMapState == parkourMain.title) {
			
			if (e.getKeyCode() == KeyEvent.VK_W) {
				
				parkourMain.titleScreen.choosedMap = (parkourMain.titleScreen.choosedMap - 1 + parkourMain.mapPath.size()) % parkourMain.mapPath.size();
			
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				
				parkourMain.titleScreen.choosedMap = (parkourMain.titleScreen.choosedMap + 1) % parkourMain.mapPath.size();
				
			} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				parkourMain.currentMap = parkourMain.titleScreen.choosedMap;
				parkourMain.currentMapState = parkourMain.play;
				parkourMain.timerStartTime = System.nanoTime();
				
			}else if (e.getKeyCode() == KeyEvent.VK_A) {
				
				parkourMain.propertiesData.addMap();
				
			}else if (e.getKeyCode() == KeyEvent.VK_D) {
				
				parkourMain.propertiesData.removeMap();
				
			}
			
		}
		if(e.getKeyCode() == KeyEvent.VK_Q) {
			
			if(parkourMain.musicOn == true) {
				
				parkourMain.musicOn = false;
				parkourMain.soundClip.stop();
				
			}else {
				
				parkourMain.musicOn = true;
				parkourMain.soundClip.start();;
				
			}
			
		}

		if(parkourMain.currentMapState == parkourMain.finish) {
			
			if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				
				parkourMain.currentMapState = parkourMain.title;
				
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:goLeft = false;break;
		case KeyEvent.VK_D:goRight = false;break;
		case KeyEvent.VK_SHIFT:sneaking = false; walkSpeed = 6; break; // Reset walk speed when sneaking is released
		}
		
	}
	
	public void updatePlayer() {
		
		parkourMain.parkourTimer.timerTimeMinutesStr = Long.toString(parkourMain.parkourTimer.timerTimeMinutes);
		parkourMain.parkourTimer.timerTimeSecondsStr = Long.toString(parkourMain.parkourTimer.timerTimeSeconds);
		parkourMain.parkourTimer.timerTimeMilisecondsStr = Long.toString(parkourMain.parkourTimer.timerTimeMiliseconds);

		if (goLeft) {
			playerDirection = 1;
		}

		if (goRight) {
			playerDirection = 2;
		}

		collideLeft = false;
		collideRight = false;

		detectCollisionUp();
		detectCollisionDown();
		detectCollisionLeft();
		detectCollisionRight();

		fall();
		jump();

		// Adjust movement to check for collisions at each step
		if (goRight && !collideRight) {
			for (int i = 0; i < walkSpeed; i++) {
				worldX++;
				detectCollisionRight();
				if (collideRight) {
					worldX--;
					break;
				}
			}
		}

		if (goLeft && !collideLeft) {
			for (int i = 0; i < walkSpeed; i++) {
				worldX--;
				detectCollisionLeft();
				if (collideLeft) {
					worldX++;
					break;
				}
			}
		}

		imageCount++;
		if (imageCount > 10) {
			imageNumber = (imageNumber == 1) ? 0 : 1;
			imageCount = 0;
		}

		System.out.println("Player X: " + worldX + ", Player Y: " + worldY);

		if (worldX > parkourMain.endIndex.get(parkourMain.currentMap)){

			parkourMain.currentMapState = parkourMain.finish; 
			parkourMain.parkourTimer.saveTime(); // Save the record time
			parkourMain.propertiesData.saveProperties();
			// Transition to finish screen

			worldX = 480;
			worldY = 376;
		}

	}
	
	public void drawPlayer(Graphics2D graphics2D) {
		
		BufferedImage image = null;
		
		switch(playerDirection) {
		case 1:
			if(imageNumber == 0) {image = leftImage1;}
			if(imageNumber == 1) {image = leftImage2;}
			break;
		case 2:
			if(imageNumber == 0) {image = rightImage1;}
			if(imageNumber == 1) {image = rightImage2;}
			break;
		}
		
		graphics2D.drawImage(image, screenX, worldY, parkourMain.tileSize, parkourMain.tileSize, null);
		
	}
	
	public void jump() {
		
		if (!falling && askJump && worldY > maxJumpHeight && !collideUp) {
	        for (int i = 0; i < jumpSpeed; i++) {
	            worldY--;
	            detectCollisionUp();
	            if (collideUp || worldY <= maxJumpHeight) {
	                break;
	            }
	        }
	        jumping = true;
	    } else {
	        jumping = false;
	        askJump = false;
	    }

	}
	
	public void fall() {
		
		if (!jumping && !collideDown) {
	        for (int i = 0; i < fallSpeed; i++) {
	            worldY++;
	            detectCollisionDown();
	            if (collideDown) {
	                break;
	            }
	        }
	        falling = true;
	    } else {
	        falling = false;
	    }

	}
	
	public void getPlayer() {
		
		try {
			
			leftImage1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
			rightImage1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
			leftImage2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
			rightImage2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
			
		}catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void detectCollisionUp() {
		
		if(worldY % parkourMain.tileSize == 0) {
			
			if(worldX % parkourMain.tileSize == 0) {
					
				tileNumber1 = parkourMain.tileManager.mapTileNumber[parkourMain.currentMap][worldX / parkourMain.tileSize][worldY / parkourMain.tileSize - 1];
				
				if(parkourMain.tileManager.tile[tileNumber1].solidTile == true) {
						
					collideDown = true;
						
				}else {
					
					collideDown = false;
					
				} 
					
			}else {
					
				tileNumber1 = parkourMain.tileManager.mapTileNumber[parkourMain.currentMap][(worldX - (worldX % parkourMain.tileSize)) / parkourMain.tileSize][worldY / parkourMain.tileSize - 1];
				tileNumber2 = parkourMain.tileManager.mapTileNumber[parkourMain.currentMap][(worldX - (worldX % parkourMain.tileSize)) / parkourMain.tileSize + 1][worldY / parkourMain.tileSize - 1];
				
				if(parkourMain.tileManager.tile[tileNumber1].solidTile == true || parkourMain.tileManager.tile[tileNumber2].solidTile == true) {
						
					collideDown = true;
						
				}else {
					
					collideDown = false;
					
				}
					
			}
			
		}else {
			
			collideDown = false;
			
		}
		
	}
	
	public void detectCollisionDown() {
		
		if(worldY % parkourMain.tileSize == 0) {
				
			if(worldX % parkourMain.tileSize == 0) {
					
				tileNumber1 = parkourMain.tileManager.mapTileNumber[parkourMain.currentMap][worldX / parkourMain.tileSize][worldY / parkourMain.tileSize + 1];

				if(parkourMain.tileManager.tile[tileNumber1].solidTile == true) {
						
					collideDown = true;
						
				}else {
					
					collideDown = false;
					
				} 
					
			}else {
					
				tileNumber1 = parkourMain.tileManager.mapTileNumber[parkourMain.currentMap][(worldX - (worldX % parkourMain.tileSize)) / parkourMain.tileSize][worldY / parkourMain.tileSize + 1];
				tileNumber2 = parkourMain.tileManager.mapTileNumber[parkourMain.currentMap][(worldX - (worldX % parkourMain.tileSize)) / parkourMain.tileSize + 1][worldY / parkourMain.tileSize + 1];

				if(parkourMain.tileManager.tile[tileNumber1].solidTile == true || parkourMain.tileManager.tile[tileNumber2].solidTile == true) {
						
					collideDown = true;
						
				}else {
					
					collideDown = false;
					
				}
					
			}
			
		}else {
			
			collideDown = false;
			
		}

	}
	
	public void detectCollisionLeft() {
		
		if(playerDirection == 1) {
			
			if(worldX % parkourMain.tileSize == 0) {
				
				if(worldY % parkourMain.tileSize == 0) {
					
					tileNumber1 = parkourMain.tileManager.mapTileNumber[parkourMain.currentMap][worldX / parkourMain.tileSize - 1][worldY / parkourMain.tileSize];
					
					if(parkourMain.tileManager.tile[tileNumber1].solidTile == true) {
						
						collideLeft = true;
						
					}
					
				}else {
					
					tileNumber1 = parkourMain.tileManager.mapTileNumber[parkourMain.currentMap][worldX / parkourMain.tileSize - 1][(worldY - (worldY % parkourMain.tileSize)) / parkourMain.tileSize];
					tileNumber2 = parkourMain.tileManager.mapTileNumber[parkourMain.currentMap][worldX / parkourMain.tileSize - 1][(worldY - (worldY % parkourMain.tileSize)) / parkourMain.tileSize + 1];
					
					if(parkourMain.tileManager.tile[tileNumber1].solidTile == true || parkourMain.tileManager.tile[tileNumber2].solidTile == true) {
						
						collideLeft = true;
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public void detectCollisionRight() {
		
		if(playerDirection == 2) {
			
			if(worldX % parkourMain.tileSize == 0) {
				
				if(worldY % parkourMain.tileSize == 0) {
					
					tileNumber1 = parkourMain.tileManager.mapTileNumber[parkourMain.currentMap][worldX / parkourMain.tileSize + 1][worldY / parkourMain.tileSize];
					
					if(parkourMain.tileManager.tile[tileNumber1].solidTile == true) {
						
						collideRight = true;
						
					}
					
				}else {
					
					tileNumber1 = parkourMain.tileManager.mapTileNumber[parkourMain.currentMap][worldX / parkourMain.tileSize + 1][(worldY - (worldY % parkourMain.tileSize)) / parkourMain.tileSize];
					tileNumber2 = parkourMain.tileManager.mapTileNumber[parkourMain.currentMap][worldX / parkourMain.tileSize + 1][(worldY - (worldY % parkourMain.tileSize)) / parkourMain.tileSize + 1];
					
					if(parkourMain.tileManager.tile[tileNumber1].solidTile == true || parkourMain.tileManager.tile[tileNumber2].solidTile == true) {
						
						collideRight = true;
						
					}
					
				}
				
			}
			
		}
		 
	}

}
