package net.vincent.parkourwarrior;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
 * @version 1.3
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
    * Indexed as {@code [mapIndex][column][row]}.
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
         new int[parkourMain.mapNumber.size()][parkourMain.maxWorldColumn]
                [parkourMain.maxWorldRow];

      getTile();
   }

   /**
    * Draws the visible tiles on screen.
    * <p>
    * Iterates through the tile map array and renders each tile
    * relative to the player's position using a camera offset.
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

      int column = 0;
      int row = 0;
      int y = 0;

      while (column < parkourMain.maxWorldColumn &&
             row < parkourMain.maxHeightTiles) {

         tileNumber = mapTileNumber[parkourMain.currentMap][column][row];

         int worldX = column * parkourMain.tileSize;
         int screenX =
            worldX - parkourMain.player.worldX + parkourMain.player.screenX;

         graphics2D.drawImage(tile[tileNumber].image, screenX, y,
                              parkourMain.tileSize, parkourMain.tileSize, null);
         column++;

         if (column == parkourMain.maxWorldColumn) {

            column = 0;
            row++;
            y += parkourMain.tileSize;
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
    * @param mapPaths the list of resource paths to the map files
    * @since 1.1
    */
   public void loadMap(ArrayList<String> mapPaths) {
      int mapIndex = 0;
      for (String path : mapPaths) {
         System.out.println("Attempting to load map from resource: " + path);
         InputStream inputStream =
            Objects.requireNonNull(getClass().getResourceAsStream(path));
         if (inputStream == null) {
            System.out.println(
               " getResourceAsStream returned null, trying ClassLoader.");
            inputStream = getClass().getClassLoader().getResourceAsStream(
               (path.startsWith("/") ? path.substring(1) : path));
         }
         if (inputStream == null) {
            System.err.println("Resource not found: " + path);
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
               for (int column = 0; column < parkourMain.maxWorldColumn &&
                                    column < tokens.length;
                    column++) {
                  try {
                     int tileNumber = Integer.parseInt(tokens[column]);
                     mapTileNumber[mapIndex][column][row] = tileNumber;
                  } catch (NumberFormatException e) {
                     System.err.println("Error parsing number at map " +
                                        mapIndex + ", row " + row +
                                        ", column " + column);
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
