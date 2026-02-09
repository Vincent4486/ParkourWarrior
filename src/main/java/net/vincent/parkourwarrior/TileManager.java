package net.vincent.parkourwarrior;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;

public class TileManager {

   ParkourMain parkourMain;
   public Tile[] tile;

   int mapTileNumber[][][];

   public int tileNumber;

   public TileManager(ParkourMain parkourMain) {

      this.parkourMain = parkourMain;

      tile = new Tile[15];

      mapTileNumber =
         new int[parkourMain.mapNumber.size()][parkourMain.maxWorldColumn]
                [parkourMain.maxWorldRow];

      getTile();
   }

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

   public void updateTile() {}

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
