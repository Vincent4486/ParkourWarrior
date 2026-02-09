package net.vincent.parkourwarrior;

import java.awt.image.BufferedImage;

/**
 * Tiles class for each tile.
 * <p>
 * This class is the parent of every tile, which are the blocks
 * displayed inside the game, each {@code Tile} contains a
 * {@code BufferedImage} of that tile for {@code Graphics2D} to
 * draw. This also consist of a {@code boolean} which determines
 * if a tile is solid.
 * </p>
 * @author Vincent4486
 * @version 1.2
 * @since 1.0
 */
public class Tile {

   /**
    * The {@code BufferedImage} to store the image of a tile.
    * @since 1.0
    */
   public BufferedImage image;

   /**
    * To see if a tile is solid.
    * @since 1.0
    */
   public boolean solidTile = false;
}
