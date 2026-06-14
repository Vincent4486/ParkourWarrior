package org.vyang.parkourwarrior;

/**
 * GameMap contating all data of a map.
 * @author Vincent4486
 * @version 1.5
 * @since 1.5
 */
public class GameMap {

   /*
    * public int recordTimeMiliseconds is 2 digit miliseconds.
    * public int currentMap means current map number, number is in properties
    * file.
    */

   /**
    * This is the map number for the program to load the maps,
    * defined for {@code PropertiesData} and {@code TileManager}.
    * @since 1.5
    */
   public int mapNumber;

   /**
    * This is the path to maps in the system, defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.5
    */
   public String mapPath;

   /**
    * This is the type of map, defined for
    * {@code PropertiesData} and {@code TileManager},
    * currently unused.
    * @since 1.5
    */
   public int mapType;

   /**
    * This is the type of map (e.g. default), defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.5
    */
   public boolean isDefaultMap;

   /**
    * This is for Graphics2D to see if the it need to
    * print time next to the map, defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.5
    */
   public boolean haveFinishedMap;

   /**
    * The shortest minutes used to finish a map, defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.5
    */
   public int recordTimeMinutes;

   /**
    * The shortest seconds used to finish a map, defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.5
    */
   public int recordTimeSeconds;

   /**
    * The shortest milliseconds used to finish a map, defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.5
    */
   public int recordTimeMiliseconds;

   /**
    * The end index, defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.5
    */
   public int endIndex;

   /**
    * The constructor of class {@code GameMap} which is not used.
    * @since 1.5
    */
   public GameMap() {}
}
