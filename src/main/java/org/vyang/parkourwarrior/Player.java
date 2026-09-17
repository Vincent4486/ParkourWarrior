package org.vyang.parkourwarrior;

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
 * control the character. The movement is simulated by a velocity
 * based physics model, which applies gravity, acceleration,
 * friction, and a terminal falling speed every frame.
 * </p>
 *
 * @author Vincent4486
 * @version 1.5
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
    * The highest position the player may reach during a jump.
    * <p>
    * Retained for compatibility with {@code KeyHandler}, the jump
    * impulse is derived from {@code jumpHeight} by the physics.
    * </p>
    * @since 1.0
    */
   public int maxJumpHeight;

   /**
    * The height of the player's jump in pixels.
    * @since 1.0
    */
   public int jumpHeight = 61;

   /**
    * The rising speed of the player's jump in pixels per frame,
    * which the gravity of the jump is derived from.
    * @since 1.0
    */
   public int jumpSpeed = 4;

   /**
    * The terminal falling speed of the player in pixels per frame.
    * @since 1.0
    */
   public int fallSpeed = 5;

   /**
    * The walking speed of the player in pixels per frame, which
    * is the maximum horizontal speed of the physics.
    * @since 1.0
    */
   public int walkSpeed = 6;

   /**
    * The acceleration of gravity in pixels per second squared.
    * @since 1.5
    */
   private final double gravity;

   /**
    * The upward velocity of a jump in pixels per second.
    * @since 1.5
    */
   private final double jumpVelocity;

   /**
    * The terminal velocity of a fall in pixels per second.
    * @since 1.5
    */
   private final double maxFallSpeed;

   /**
    * The current horizontal velocity in pixels per second.
    * @since 1.5
    */
   private double velocityX = 0;

   /**
    * The current vertical velocity in pixels per second.
    * @since 1.5
    */
   private double velocityY = 0;

   /**
    * The horizontal sub-pixel distance not yet applied to worldX.
    * @since 1.5
    */
   private double remainderX = 0;

   /**
    * The vertical sub-pixel distance not yet applied to worldY.
    * @since 1.5
    */
   private double remainderY = 0;

   /**
    * The X position of the player before the current frame.
    * @since 1.5
    */
   private int previousWorldX = 480;

   /**
    * The Y position of the player before the current frame.
    * @since 1.5
    */
   private int previousWorldY = 376;

   /**
    * The fixed timestep of the physics in seconds, for 60 frames per second.
    * @since 1.5
    */
   private static final double TIME_STEP = 1.0 / 60.0;

   /**
    * The number of physics frames in one second.
    * @since 1.5
    */
   private static final double FRAMES_PER_SECOND = 60.0;

   /**
    * The horizontal acceleration on the ground in pixels per second squared.
    * @since 1.5
    */
   private static final double GROUND_ACCELERATION = 2400.0;

   /**
    * The horizontal acceleration in the air in pixels per second squared.
    * @since 1.5
    */
   private static final double AIR_ACCELERATION = 1200.0;

   /**
    * The horizontal deceleration on the ground in pixels per second squared.
    * @since 1.5
    */
   private static final double GROUND_FRICTION = 2400.0;

   /**
    * The horizontal deceleration in the air in pixels per second squared.
    * @since 1.5
    */
   private static final double AIR_DRAG = 600.0;

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
    * Whether the player has leftward momentum after releasing the left key.
    * <p>
    * Retained for compatibility with {@code KeyHandler}, the sliding is
    * applied by the friction of the physics instead.
    * </p>
    * @since 1.3
    */
   public boolean momentumLeft = false;

   /**
    * Whether the player has rightward momentum after releasing the right key.
    * <p>
    * Retained for compatibility with {@code KeyHandler}, the sliding is
    * applied by the friction of the physics instead.
    * </p>
    * @since 1.3
    */
   public boolean momentumRight = false;

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

      // Derive gravity and the impulse from the tuning in pixels per frame
      double riseTime = jumpHeight / (jumpSpeed * FRAMES_PER_SECOND);

      gravity = 2.0 * jumpHeight / (riseTime * riseTime);

      // Compensate the half step the discrete rise loses to the integration
      double gravityStep = gravity * TIME_STEP;

      jumpVelocity =
         (gravityStep + Math.sqrt(gravityStep * gravityStep +
                                  8.0 * gravity * jumpHeight)) / 2.0;
      maxFallSpeed = fallSpeed * FRAMES_PER_SECOND;

      getPlayer();
   }

   /**
    * Updates the player's state each frame.
    * <p>
    * This method applies the physics of the player, which handles
    * gravity, acceleration, friction, movement, collision detection,
    * jumping, falling, animation cycling, and map completion checks.
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

      syncExternalTeleport();

      handleJump();
      applyGravity();
      applyHorizontalForces();

      moveHorizontal();
      moveVertical();

      updateCollisionFlags();

      jumping = !collideDown && velocityY < 0;
      falling = !collideDown && velocityY > 0;

      imageCount++;
      if (imageCount > 10) {
         imageNumber = (imageNumber == 1) ? 0 : 1;
         imageCount = 0;
      }

      if (worldX > parkourMain.mapManager.gameMaps.get(parkourMain.currentMap)
                      .endIndex) {

         parkourMain.currentMapState = parkourMain.dialogue;
         parkourMain.dialogScreen.title = "Congratulations!";
         parkourMain.dialogScreen.text = "You finished in " +
                                         parkourMain.parkourTimer
                                             .timerTimeMinutesStr +
                                         ":" +
                                         parkourMain.parkourTimer
                                             .timerTimeSecondsStr +
                                         "." +
                                         parkourMain.parkourTimer
                                             .timerTimeMilisecondsStr;
         parkourMain.dialogScreen.dialogueOption = DialogScreen.DIALOGUE_OPTION_NONE;
         parkourMain.dialogScreen.callback = (yes) -> {
            parkourMain.currentMapState = parkourMain.title;
         };
         parkourMain.parkourTimer.saveTime(); // Save the record time
         parkourMain.mapManager.saveMapProperties();
         // Transition to finish screen

         spawnPlayer();
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
    * Applies a jump impulse when a jump is requested on the ground.
    * <p>
    * The request is consumed every frame, so a jump only happens
    * on a frame where the player stands on a solid tile.
    * </p>
    *
    * @since 1.5
    */
   private void handleJump() {

      if (askJump && collideDown) {
         velocityY = -jumpVelocity;
         jumping = true;
      }

      askJump = false;
   }

   /**
    * Accelerates the player downward with gravity.
    * <p>
    * The falling speed is limited to the terminal velocity of the
    * player, which stands in for the drag of the air.
    * </p>
    *
    * @since 1.5
    */
   private void applyGravity() {

      velocityY += gravity * TIME_STEP;

      if (velocityY > maxFallSpeed) {
         velocityY = maxFallSpeed;
      }
   }

   /**
    * Accelerates and decelerates the player horizontally.
    * <p>
    * Input accelerates the player towards the walking speed, with
    * the lower air acceleration giving reduced air control. With
    * no input, friction slides the player to a stop, and the lower
    * drag of the air keeps the momentum of a jump.
    * </p>
    *
    * @since 1.5
    */
   private void applyHorizontalForces() {

      double maxSpeed = walkSpeed * FRAMES_PER_SECOND;

      if (goLeft == goRight) {

         double friction;
         if (collideDown) {
            friction = GROUND_FRICTION;
         } else {
            friction = AIR_DRAG;
         }

         if (velocityX > 0) {
            velocityX = Math.max(0, velocityX - friction * TIME_STEP);
         } else {
            velocityX = Math.min(0, velocityX + friction * TIME_STEP);
         }

      } else {

         double acceleration;
         if (collideDown) {
            acceleration = GROUND_ACCELERATION;
         } else {
            acceleration = AIR_ACCELERATION;
         }

         if (goLeft) {
            velocityX -= acceleration * TIME_STEP;
         } else {
            velocityX += acceleration * TIME_STEP;
         }
      }

      if (velocityX > maxSpeed) {
         velocityX = maxSpeed;
      } else if (velocityX < -maxSpeed) {
         velocityX = -maxSpeed;
      }
   }

   /**
    * Moves the player horizontally by the current velocity.
    * <p>
    * The distance is applied one pixel at a time, so a solid tile
    * stops the player on the pixel it is reached, and the sub-pixel
    * distance is kept for the next frame.
    * </p>
    *
    * @since 1.5
    */
   private void moveHorizontal() {

      remainderX += velocityX * TIME_STEP;

      int distance = (int)remainderX;

      if (distance == 0) {
         return;
      }

      remainderX -= distance;

      int step = Integer.signum(distance);

      for (int i = 0; i < Math.abs(distance); i++) {

         worldX += step;

         if (collidesAt(0, 0)) {
            worldX -= step;
            velocityX = 0;
            remainderX = 0;
            break;
         }
      }
   }

   /**
    * Moves the player vertically by the current velocity.
    * <p>
    * The distance is applied one pixel at a time, so a solid tile
    * stops a jump or a fall on the pixel it is reached, and the
    * sub-pixel distance is kept for the next frame.
    * </p>
    *
    * @since 1.5
    */
   private void moveVertical() {

      remainderY += velocityY * TIME_STEP;

      int distance = (int)remainderY;

      if (distance == 0) {
         return;
      }

      remainderY -= distance;

      int step = Integer.signum(distance);

      for (int i = 0; i < Math.abs(distance); i++) {

         worldY += step;

         if (collidesAt(0, 0)) {
            worldY -= step;
            velocityY = 0;
            remainderY = 0;
            break;
         }
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
    * Updates the collision flags around the player.
    * <p>
    * Each flag is set when a solid tile is within one pixel of that
    * side of the player, so the flags do not depend on the direction
    * the player is facing.
    * </p>
    *
    * @since 1.5
    */
   private void updateCollisionFlags() {

      collideUp = collidesAt(0, -1);
      collideDown = collidesAt(0, 1);
      collideLeft = collidesAt(-1, 0);
      collideRight = collidesAt(1, 0);
   }

   /**
    * Checks whether the player overlaps a solid tile when offset.
    *
    * @param offsetX the horizontal offset in pixels
    * @param offsetY the vertical offset in pixels
    * @return {@code true} if any overlapped tile is solid
    * @since 1.5
    */
   private boolean collidesAt(int offsetX, int offsetY) {

      int size = parkourMain.tileSize;

      int left = Math.floorDiv(worldX + offsetX, size);
      int right = Math.floorDiv(worldX + offsetX + size - 1, size);
      int top = Math.floorDiv(worldY + offsetY, size);
      int bottom = Math.floorDiv(worldY + offsetY + size - 1, size);

      for (int column = left; column <= right; column++) {
         for (int row = top; row <= bottom; row++) {
            if (isSolidTile(column, row)) {
               return true;
            }
         }
      }

      return false;
   }

   /**
    * Checks whether the tile at the given coordinates is solid.
    *
    * @param column the column of the tile
    * @param row the row of the tile
    * @return {@code true} if the tile is solid or outside the map
    * @since 1.5
    */
   private boolean isSolidTile(int column, int row) {

      if (column < 0 || row < 0 || column >= parkourMain.maxWorldColumn ||
          row >= parkourMain.maxWorldRow) {
         return true;
      }

      int tileNumber =
         parkourMain.tileManager
            .mapTileNumber[parkourMain.currentMap][column][row];

      return parkourMain.tileManager.tile[tileNumber].solidTile;
   }

   /**
    * Clears the velocity and the sub-pixel movement of the player.
    *
    * @since 1.5
    */
   private void resetMotion() {

      velocityX = 0;
      velocityY = 0;
      remainderX = 0;
      remainderY = 0;
   }

   /**
    * Moves the player to the spawn position of the current map.
    * <p>
    * The spawn is read from the current {@code Map}, and the
    * velocity, the sub-pixel movement, and any jump request are
    * cleared.
    * </p>
    *
    * @since 1.5
    */
   public void spawnPlayer() {

      Map map = parkourMain.mapManager.gameMaps.get(parkourMain.currentMap);

      worldX = map.playerInitX;
      worldY = map.playerInitY;

      askJump = false;

      resetMotion();
   }

   /**
    * Clears the player's motion when the position is set externally.
    * <p>
    * A teleport such as a respawn moves the player further than the
    * physics can in one frame, which discards the old velocity.
    * </p>
    *
    * @since 1.5
    */
   private void syncExternalTeleport() {

      if (Math.abs(worldX - previousWorldX) > parkourMain.tileSize ||
          Math.abs(worldY - previousWorldY) > parkourMain.tileSize) {
         resetMotion();
      }

      previousWorldX = worldX;
      previousWorldY = worldY;
   }
}
