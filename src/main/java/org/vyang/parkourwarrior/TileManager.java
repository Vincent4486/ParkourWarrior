package org.vyang.parkourwarrior;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import javax.imageio.ImageIO;

/**
 * Tile manager class for Parkour Warrior.
 * <p>
 * This class manages all tiles in the game world, including
 * loading tile images, reading map data from resource files,
 * and drawing the visible tiles on screen relative to the
 * player's position.
 * </p>
 *
 * @author Vincent4486
 * @version 1.5
 * @since 1.0
 */
public class TileManager {

   /**
    * Reference to the main game panel.
    * @since 1.0
    */
   ParkourMain parkourMain;

   /**
    * Array of all available tile types.
    * @since 1.0
    */
   public Tile[] tile;

   /**
    * Three-dimensional array storing tile numbers for each map.
    * <p>
    * Indexed as {@code [mapIndex][column][row]}, where the columns
    * are allocated from the length of that map.
    * </p>
    * @since 1.0
    */
   int mapTileNumber[][][];

   /**
    * The current tile number being processed.
    * @since 1.0
    */
   public int tileNumber;

   /**
    * Constructs a new {@code TileManager} with a reference to the
    * main game panel, initializes the tile array and map data array,
    * and loads all tile images.
    *
    * @param parkourMain the main game panel instance
    * @since 1.0
    */
   public TileManager(ParkourMain parkourMain) {

      this.parkourMain = parkourMain;

      tile = new Tile[15];

      mapTileNumber =
         new int[parkourMain.mapManager.gameMaps.size()][][];

      for (int mapIndex = 0;
           mapIndex < parkourMain.mapManager.gameMaps.size(); mapIndex++) {

         int columnCount = parkourMain.getMapColumnCount(
            parkourMain.mapManager.gameMaps.get(mapIndex));

         mapTileNumber[mapIndex] =
            new int[columnCount][parkourMain.maxWorldRow];
      }

      getTile();
   }

   /**
    * Draws the visible tiles on screen.
    * <p>
    * Renders the tiles that are on screen relative to the player's
    * position using a camera offset. Columns outside the map repeat
    * the first or the last column of the map, so the screen is never
    * left black at the borders. The repeated columns are drawn only,
    * the collision of the player still ends at the map borders.
    * </p>
    *
    * @param graphics2D the {@code Graphics2D} context to draw on
    * @since 1.0
    */
   public void drawTile(Graphics2D graphics2D) {

      /*
       * width == column
       * height == row
       */

      int tileSize = parkourMain.tileSize;
      int cameraX = parkourMain.player.worldX - parkourMain.player.screenX;
      int columnCount = mapTileNumber[parkourMain.currentMap].length;

      int firstColumn = Math.floorDiv(cameraX, tileSize);
      int lastColumn =
         Math.floorDiv(cameraX + parkourMain.screenWidth - 1, tileSize);

      for (int row = 0; row < parkourMain.maxHeightTiles; row++) {

         int y = row * tileSize;

         for (int column = firstColumn; column <= lastColumn; column++) {

            int mapColumn =
               Math.min(Math.max(column, 0), columnCount - 1);

            tileNumber =
               mapTileNumber[parkourMain.currentMap][mapColumn][row];

            int screenX = column * tileSize - cameraX;

            graphics2D.drawImage(tile[tileNumber].image, screenX, y,
                                 tileSize, tileSize, null);
         }
      }
   }

   /**
    * Updates the tile state each frame.
    *
    * @since 1.0
    */
   public void updateTile() {}

   /**
    * Loads map data from the specified resource paths.
    * <p>
    * Reads each map file line by line, parsing space-separated
    * tile numbers into the {@code mapTileNumber} array.
    * </p>
    *
    * @since 1.1
    */
   public void loadMap() {
      int mapIndex = 0;
      for (Map map : parkourMain.mapManager.gameMaps) {
         String path = map.mapPath;
         InputStream inputStream = null;

         if (map.mapType == parkourMain.customPlayMap) {
            String userHome = System.getProperty("user.home");
            java.io.File customMapFile = new java.io.File(
               userHome + "/.config/ParkourWarrior/maps/" + path);

            System.out.println(
               "Attempting to load custom map from config directory: " +
               customMapFile.getAbsolutePath());

            if (customMapFile.exists() && !customMapFile.isDirectory()) {
               try {
                  inputStream = new java.io.FileInputStream(customMapFile);
               } catch (IOException e) {
                  System.err.println("Failed to open custom map file: " +
                                     customMapFile.getAbsolutePath());
               }
            } else {
               System.err.println("Custom map file does not exist: " +
                                  customMapFile.getAbsolutePath());
            }

         } else if (map.mapType == parkourMain.defaultPlayMap) {
            System.out.println("Attempting to load internal default map: " +
                               path);
            inputStream = getClass().getResourceAsStream(path);

            if (inputStream == null) {
               String fixedPath =
                  path.startsWith("/") ? path.substring(1) : path;
               inputStream =
                  getClass().getClassLoader().getResourceAsStream(fixedPath);
            }
         }

         if (inputStream == null) {
            System.err.println("CRITICAL: Failed to load map index " +
                               mapIndex + " (" + path + ")");
            mapIndex++;
            continue;
         }

         try (BufferedReader bufferedReader =
                 new BufferedReader(new InputStreamReader(inputStream))) {
            int row = 0;
            String line;
            while ((line = bufferedReader.readLine()) != null &&
                   row < parkourMain.maxWorldRow) {
               String[] tokens = line.split(" ");
               for (int column = 0; column < mapTileNumber[mapIndex].length &&
                                    column < tokens.length;
                    column++) {
                  try {
                     int tileNumber = Integer.parseInt(tokens[column]);
                     mapTileNumber[mapIndex][column][row] = tileNumber;
                  } catch (NumberFormatException e) {
                     System.err.println("Error parsing number at map " +
                                        mapIndex + ", row " + row + ", col " +
                                        column);
                  }
               }
               row++;
            }
         } catch (IOException e) {
            System.err.println("Error reading map " + path);
            e.printStackTrace();
         }

         mapIndex++;
      }
   }

   /**
    * Loads all tile images from the resources and assigns
    * their solid properties.
    *
    * @since 1.0
    */
   public void getTile() {

      try {

         tile[0] = new Tile();
         tile[0].image = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/tile/brick.png")));
         tile[0].solidTile = true;

         tile[1] = new Tile();
         tile[1].image = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/tile/cloud.png")));

         tile[2] = new Tile();
         tile[2].image = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/tile/flag.png")));

         tile[3] = new Tile();
         tile[3].image = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/tile/grass.png")));
         tile[3].solidTile = true;

         tile[4] = new Tile();
         tile[4].image = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/tile/sand.png")));
         tile[4].solidTile = true;

         tile[5] = new Tile();
         tile[5].image = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/tile/sky.png")));

         tile[6] = new Tile();
         tile[6].image = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/tile/water.png")));
         tile[6].solidTile = true;

         /*
          * Below is barrier layer for boarder of map.
          * vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
          */

         tile[7] = new Tile();
         tile[7].image = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/tile/brick.png")));
         tile[7].solidTile = true;

         tile[8] = new Tile();
         tile[8].image = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream("/tile/sky.png")));
         tile[8].solidTile = true;

         /*
          * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
          */

      } catch (IOException e) {

         e.printStackTrace();
      }
   }
}
