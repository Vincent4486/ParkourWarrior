package net.vincent.parkourwarrior;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

/**
 * The player class for Parkour Warrior.
 * <p>
 * This class handles the player's movement, jumping, falling,
 * collision detection, and rendering. It manages the player's
 * position in the world and responds to keyboard input to
 * control the character.
 * </p>
 *
 * @author Vincent4486
 * @version 1.3
 * @since 1.0
 */
public class Player {

   /**
    * Reference to the main game panel.
    * @since 1.0
    */
   ParkourMain parkourMain;

   /**
    * Whether the player has requested a jump.
    * @since 1.0
    */
   public boolean askJump = false;

   /**
    * Whether the player is currently jumping.
    * @since 1.0
    */
   public boolean jumping = false;

   /**
    * Whether the player is currently falling.
    * @since 1.0
    */
   public boolean falling = false;

   /**
    * Whether the player is moving right.
    * @since 1.0
    */
   public boolean goRight = false;

   /**
    * Whether the player is moving left.
    * @since 1.0
    */
   public boolean goLeft = false;

   /**
    * Whether the player is currently sneaking.
    * @since 1.2
    */
   public boolean sneaking = false;

   /**
    * Whether the player is colliding upward.
    * @since 1.0
    */
   public boolean collideUp;

   /**
    * Whether the player is colliding downward.
    * @since 1.0
    */
   public boolean collideDown;

   /**
    * Whether the player is colliding to the left.
    * @since 1.0
    */
   public boolean collideLeft;

   /**
    * Whether the player is colliding to the right.
    * @since 1.0
    */
   public boolean collideRight;

   /**
    * The player's X position in the world.
    * @since 1.0
    */
   public int worldX = 480;

   /**
    * The player's Y position in the world.
    * @since 1.0
    */
   public int worldY = 376;

   /**
    * The maximum height the player can reach during a jump.
    * @since 1.0
    */
   public int maxJumpHeight;

   /**
    * The height of the player's jump in pixels.
    * @since 1.0
    */
   public int jumpHeight = 61;

   /**
    * The speed of the player's jump in pixels per frame.
    * @since 1.0
    */
   public int jumpSpeed = 4;

   /**
    * The speed of the player's fall in pixels per frame.
    * @since 1.0
    */
   public int fallSpeed = 5;

   /**
    * The walking speed of the player in pixels per frame.
    * @since 1.0
    */
   public int walkSpeed = 6;

   /**
    * The player's X position on screen, used for camera offset.
    * @since 1.0
    */
   public final int screenX;

   /**
    * Counter for sprite animation frame timing.
    * @since 1.0
    */
   int imageCount = 0;

   /**
    * The current sprite animation frame number.
    * @since 1.0
    */
   int imageNumber = 0;

   /**
    * First tile number used for collision detection.
    * @since 1.0
    */
   int tileNumber1;

   /**
    * Second tile number used for collision detection.
    * @since 1.0
    */
   int tileNumber2;

   /**
    * Whether the player has leftward momentum after releasing the left key.
    * @since 1.3
    */
   public boolean momentumLeft = false;

   /**
    * Whether the player has rightward momentum after releasing the right key.
    * @since 1.3
    */
   public boolean momentumRight = false;

   /**
    * Counter for leftward momentum ticks.
    * @since 1.3
    */
   public int momentumCountLeft = 0;

   /**
    * Counter for rightward momentum ticks.
    * @since 1.3
    */
   public int momentumCountRight = 0;

   /**
    * The maximum number of ticks for momentum to last.
    * @since 1.3
    */
   public static final int MAX_MOMENTUM_TICKS = 8;

   /**
    * The player's facing direction.
    * <p>
    * 1 = left, 2 = right.
    * </p>
    * @since 1.0
    */
   public int playerDirection = 2;

   /**
    * Sprite images for the player's left and right animations.
    * @since 1.0
    */
   BufferedImage leftImage1, rightImage1, leftImage2, rightImage2;

   /**
    * The solid area rectangle used for collision detection.
    * @since 1.0
    */
   Rectangle solidArea = new Rectangle();

   /**
    * Constructs a new {@code Player} with a reference to the main
    * game panel and initializes the player's collision area and
    * screen position.
    *
    * @param parkourMain the main game panel instance
    * @since 1.0
    */
   public Player(ParkourMain parkourMain) {

      solidArea.x = 6;
      solidArea.y = 0;
      solidArea.height = 48;
      solidArea.width = 36;

      this.parkourMain = parkourMain;

      screenX = parkourMain.screenWidth / 2 - parkourMain.tileSize / 2;

      getPlayer();
   }

   /**
    * Updates the player's state each frame.
    * <p>
    * This method handles movement, collision detection, jumping,
    * falling, momentum, animation cycling, and map completion checks.
    * </p>
    *
    * @since 1.0
    */
   public void updatePlayer() {

      parkourMain.parkourTimer.timerTimeMinutesStr =
         Long.toString(parkourMain.parkourTimer.timerTimeMinutes);
      parkourMain.parkourTimer.timerTimeSecondsStr =
         Long.toString(parkourMain.parkourTimer.timerTimeSeconds);
      parkourMain.parkourTimer.timerTimeMilisecondsStr =
         Long.toString(parkourMain.parkourTimer.timerTimeMiliseconds);

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

      if (worldX > parkourMain.endIndex.get(parkourMain.currentMap)) {

         parkourMain.currentMapState = parkourMain.finish;
         parkourMain.parkourTimer.saveTime(); // Save the record time
         parkourMain.propertiesData.saveProperties();
         // Transition to finish screen

         worldX = 480;
         worldY = 376;
      }
      System.out.println(momentumRight);

      if (momentumRight) {
         if (!momentumRight)
            return;

         int burst = calculateBurstSpeed(momentumCountRight);
         for (int i = 0; i < burst; i++) {
            worldX++;
            detectCollisionRight();
            if (collideRight) {
               // hit wall: step back & kill momentum
               worldX--;
               momentumRight = false;
               momentumCountRight = 0;
               return;
            }
         }

         // advance the counter (and expire if too long)
         momentumCountRight++;
         if (momentumCountRight >= MAX_MOMENTUM_TICKS) {
            momentumRight = false;
            momentumCountRight = 0;
         }
      }

      if (momentumLeft) {
         if (!momentumLeft)
            return;

         int burst = calculateBurstSpeed(momentumCountLeft);
         for (int i = 0; i < burst; i++) {
            worldX--;
            detectCollisionLeft();
            if (collideLeft) {
               worldX++;
               momentumLeft = false;
               momentumCountLeft = 0;
               return;
            }
         }

         momentumCountLeft++;
         if (momentumCountLeft >= MAX_MOMENTUM_TICKS) {
            momentumLeft = false;
            momentumCountLeft = 0;
         }
      }
   }

   /**
    * Draws the player sprite on the screen.
    *
    * @param graphics2D the {@code Graphics2D} context to draw on
    * @since 1.0
    */
   public void drawPlayer(Graphics2D graphics2D) {

      BufferedImage image = null;

      switch (playerDirection) {
      case 1:
         if (imageNumber == 0) {
            image = leftImage1;
         }
         if (imageNumber == 1) {
            image = leftImage2;
         }
         break;
      case 2:
         if (imageNumber == 0) {
            image = rightImage1;
         }
         if (imageNumber == 1) {
            image = rightImage2;
         }
         break;
      }

      graphics2D.drawImage(image, screenX, worldY, parkourMain.tileSize,
                           parkourMain.tileSize, null);
   }

   /**
    * Handles the player's jump logic.
    * <p>
    * Moves the player upward if a jump has been requested and
    * the player has not reached the maximum jump height or
    * collided with a tile above.
    * </p>
    *
    * @since 1.0
    */
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

   /**
    * Handles the player's falling logic.
    * <p>
    * Moves the player downward when not jumping and not
    * colliding with a tile below.
    * </p>
    *
    * @since 1.0
    */
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

   /**
    * Loads the player's sprite images from the resources.
    *
    * @since 1.0
    */
   public void getPlayer() {

      try {

         leftImage1 = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/player/left1.png")));
         rightImage1 = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/player/right1.png")));
         leftImage2 = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/player/left2.png")));
         rightImage2 = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/player/right2.png")));

      } catch (IOException e) {

         e.printStackTrace();
      }
   }

   /**
    * Detects collision above the player.
    * <p>
    * Checks whether the tiles directly above the player are solid,
    * and updates the {@code collideDown} flag accordingly.
    * </p>
    *
    * @since 1.0
    */
   public void detectCollisionUp() {

      if (worldY % parkourMain.tileSize == 0) {

         if (worldX % parkourMain.tileSize == 0) {

            tileNumber1 = parkourMain.tileManager
                             .mapTileNumber[parkourMain.currentMap]
                                           [worldX / parkourMain.tileSize]
                                           [worldY / parkourMain.tileSize - 1];

            if (parkourMain.tileManager.tile[tileNumber1].solidTile == true) {

               collideDown = true;

            } else {

               collideDown = false;
            }

         } else {

            tileNumber1 =
               parkourMain.tileManager
                  .mapTileNumber[parkourMain.currentMap]
                                [(worldX - (worldX % parkourMain.tileSize)) /
                                 parkourMain.tileSize]
                                [worldY / parkourMain.tileSize - 1];
            tileNumber2 =
               parkourMain.tileManager
                  .mapTileNumber[parkourMain.currentMap]
                                [(worldX - (worldX % parkourMain.tileSize)) /
                                    parkourMain.tileSize +
                                 1][worldY / parkourMain.tileSize - 1];

            if (parkourMain.tileManager.tile[tileNumber1].solidTile == true ||
                parkourMain.tileManager.tile[tileNumber2].solidTile == true) {

               collideDown = true;

            } else {

               collideDown = false;
            }
         }

      } else {

         collideDown = false;
      }
   }

   /**
    * Detects collision below the player.
    * <p>
    * Checks whether the tiles directly below the player are solid,
    * and updates the {@code collideDown} flag accordingly.
    * </p>
    *
    * @since 1.0
    */
   public void detectCollisionDown() {

      if (worldY % parkourMain.tileSize == 0) {

         if (worldX % parkourMain.tileSize == 0) {

            tileNumber1 = parkourMain.tileManager
                             .mapTileNumber[parkourMain.currentMap]
                                           [worldX / parkourMain.tileSize]
                                           [worldY / parkourMain.tileSize + 1];

            if (parkourMain.tileManager.tile[tileNumber1].solidTile == true) {

               collideDown = true;

            } else {

               collideDown = false;
            }

         } else {

            tileNumber1 =
               parkourMain.tileManager
                  .mapTileNumber[parkourMain.currentMap]
                                [(worldX - (worldX % parkourMain.tileSize)) /
                                 parkourMain.tileSize]
                                [worldY / parkourMain.tileSize + 1];
            tileNumber2 =
               parkourMain.tileManager
                  .mapTileNumber[parkourMain.currentMap]
                                [(worldX - (worldX % parkourMain.tileSize)) /
                                    parkourMain.tileSize +
                                 1][worldY / parkourMain.tileSize + 1];

            if (parkourMain.tileManager.tile[tileNumber1].solidTile == true ||
                parkourMain.tileManager.tile[tileNumber2].solidTile == true) {

               collideDown = true;

            } else {

               collideDown = false;
            }
         }

      } else {

         collideDown = false;
      }
   }

   /**
    * Detects collision to the left of the player.
    * <p>
    * Checks whether the tiles to the left of the player are solid,
    * and updates the {@code collideLeft} flag accordingly.
    * </p>
    *
    * @since 1.0
    */
   public void detectCollisionLeft() {

      if (playerDirection == 1) {

         if (worldX % parkourMain.tileSize == 0) {

            if (worldY % parkourMain.tileSize == 0) {

               tileNumber1 =
                  parkourMain.tileManager
                     .mapTileNumber[parkourMain.currentMap]
                                   [worldX / parkourMain.tileSize - 1]
                                   [worldY / parkourMain.tileSize];

               if (parkourMain.tileManager.tile[tileNumber1].solidTile ==
                   true) {

                  collideLeft = true;
               }

            } else {

               tileNumber1 =
                  parkourMain.tileManager
                     .mapTileNumber[parkourMain.currentMap]
                                   [worldX / parkourMain.tileSize - 1]
                                   [(worldY - (worldY % parkourMain.tileSize)) /
                                    parkourMain.tileSize];
               tileNumber2 =
                  parkourMain.tileManager
                     .mapTileNumber[parkourMain.currentMap]
                                   [worldX / parkourMain.tileSize - 1]
                                   [(worldY - (worldY % parkourMain.tileSize)) /
                                       parkourMain.tileSize +
                                    1];

               if (parkourMain.tileManager.tile[tileNumber1].solidTile ==
                      true ||
                   parkourMain.tileManager.tile[tileNumber2].solidTile ==
                      true) {

                  collideLeft = true;
               }
            }
         }
      }
   }

   /**
    * Detects collision to the right of the player.
    * <p>
    * Checks whether the tiles to the right of the player are solid,
    * and updates the {@code collideRight} flag accordingly.
    * </p>
    *
    * @since 1.0
    */
   public void detectCollisionRight() {

      if (playerDirection == 2) {

         if (worldX % parkourMain.tileSize == 0) {

            if (worldY % parkourMain.tileSize == 0) {

               tileNumber1 =
                  parkourMain.tileManager
                     .mapTileNumber[parkourMain.currentMap]
                                   [worldX / parkourMain.tileSize + 1]
                                   [worldY / parkourMain.tileSize];

               if (parkourMain.tileManager.tile[tileNumber1].solidTile ==
                   true) {

                  collideRight = true;
               }

            } else {

               tileNumber1 =
                  parkourMain.tileManager
                     .mapTileNumber[parkourMain.currentMap]
                                   [worldX / parkourMain.tileSize + 1]
                                   [(worldY - (worldY % parkourMain.tileSize)) /
                                    parkourMain.tileSize];
               tileNumber2 =
                  parkourMain.tileManager
                     .mapTileNumber[parkourMain.currentMap]
                                   [worldX / parkourMain.tileSize + 1]
                                   [(worldY - (worldY % parkourMain.tileSize)) /
                                       parkourMain.tileSize +
                                    1];

               if (parkourMain.tileManager.tile[tileNumber1].solidTile ==
                      true ||
                   parkourMain.tileManager.tile[tileNumber2].solidTile ==
                      true) {

                  collideRight = true;
               }
            }
         }
      }
   }

   /**
    * Calculates the burst speed for momentum based on the tick count.
    * <p>
    * The speed decreases as the momentum count increases, simulating
    * a deceleration effect. Speed is halved when sneaking.
    * </p>
    *
    * @param count the current momentum tick count
    * @return the burst speed in pixels per frame
    * @since 1.3
    */
   private int calculateBurstSpeed(int count) {
      int speed;
      if (count < 2)
         speed = 6;
      else if (count < 5)
         speed = 4;
      else if (count < 8)
         speed = 2;
      else
         speed = 1;

      // halve speed when sneaking
      if (sneaking) {
         speed = Math.max(1, speed / 2);
      }
      return speed;
   }
}
