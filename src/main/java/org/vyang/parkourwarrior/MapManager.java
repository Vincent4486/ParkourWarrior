package org.vyang.parkourwarrior;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonParseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * JSON data handler for Parkour Warrior.
 * <p>
 * This class manages the game's persistent data by reading and
 * writing to a JSON file with {@code Gson}. It stores map
 * configurations, record times, and spawn positions that
 * persist between sessions.
 * </p>
 *
 * @author Vincent4486
 * @version 1.5
 * @since 1.1
 */
public class MapManager {

   /**
    * Reference to the main game panel.
    * @since 1.1
    */
   ParkourMain parkourMain;

   /**
    * The absolute file path to the JSON file.
    * @since 1.1
    */
   String filePath = System.getProperty("user.home") +
                     "/.config/ParkourWarrior/maps.json";

   /**
    * The array of game maps loaded from the JSON file.
    * @since 1.5
    */
   public ArrayList<Map> gameMaps;

   /**
    * The {@code Gson} instance used to read and write the JSON file.
    * @since 1.5
    */
   private final Gson gson;

   /**
    * Constructs a new {@code MapManager} with a reference to
    * the main game panel.
    *
    * @param parkourMain the main game panel instance
    * @since 1.1
    */
   public MapManager(ParkourMain parkourMain) {
      this.parkourMain = parkourMain;

      gson = createGson();
      gameMaps = new ArrayList<>();
      loadMaps();
   }

   /**
    * Loads the maps from the JSON file.
    *
    * @since 1.5
    */
   public void loadMaps() {

      System.out.println("Attempting to load maps file from: " + filePath);

      try (FileReader reader = new FileReader(filePath)) {

         MapData data = gson.fromJson(reader, MapData.class);

         if (data == null || data.maps == null || data.maps.isEmpty()) {
            System.out.println("Maps file is empty, creating a new one at: " +
                               filePath);
            createMapFile();
            return;
         }

         gameMaps = data.maps;

         System.out.println("Loaded maps successfully.");

      } catch (FileNotFoundException e) {

         System.out.println("Maps file not found, creating a new one at: " +
                            filePath);
         createMapFile();

      } catch (IOException | JsonParseException e) {

         System.out.println("Maps file is invalid, creating a new one at: " +
                            filePath);
         e.printStackTrace();
         backupMapFile();
         createMapFile();
      }
   }

   /**
    * Saves the game maps to the JSON file.
    *
    * @since 1.5
    */
   public void saveMaps() {

      System.out.println("Saving maps to: " + filePath);

      MapData data = new MapData();
      data.maps = gameMaps;

      try (FileWriter writer = new FileWriter(filePath)) {

         gson.toJson(data, writer);
         System.out.println("Maps file saved successfully.");

      } catch (FileNotFoundException e) {
         createMapFile();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Creates a new JSON file with the builtin maps.
    *
    * @since 1.5
    */
   public void createMapFile() {

      System.out.println("Creating maps file at: " + filePath);

      MapData data = new MapData();
      data.maps.add(createBuiltinMap(1, "/map/map0.txt", 2740));
      data.maps.add(createBuiltinMap(2, "/map/map1.txt", 3017));
      data.maps.add(createBuiltinMap(3, "/map/map2.txt", 3100));

      gameMaps = data.maps;

      try (FileWriter writer = new FileWriter(filePath)) {

         gson.toJson(data, writer);
         System.out.println("Maps file created successfully.");

      } catch (IOException e) {
         System.err.println("Error writing maps file: " + e.getMessage());
         e.printStackTrace();
      }
   }

   /**
    * Renames the maps file to a backup next to it.
    *
    * @since 1.5
    */
   private void backupMapFile() {

      File file = new File(filePath);
      File backup = new File(filePath + ".bak");

      if (file.exists()) {
         backup.delete();
         file.renameTo(backup);
      }
   }

   /**
    * Creates a builtin map with default times and spawn.
    *
    * @param mapNumber the number of the map
    * @param mapPath the path of the map file inside the JAR
    * @param endIndex the world X that finishes the map
    * @return the builtin map
    * @since 1.5
    */
   private Map createBuiltinMap(int mapNumber, String mapPath, int endIndex) {

      Map map = new Map();

      map.mapNumber = mapNumber;
      map.mapPath = mapPath;
      map.mapType = parkourMain.defaultPlayMap;
      map.isDefaultMap = true;
      map.endIndex = endIndex;

      return map;
   }

   /**
    * Creates the {@code Gson} instance for the maps file.
    *
    * @return the {@code Gson} instance
    * @since 1.5
    */
   private static Gson createGson() {

      GsonBuilder builder = new GsonBuilder();

      builder.registerTypeAdapter(Map.class, new MapInstanceCreator());
      builder.setPrettyPrinting();

      return builder.create();
   }

   /**
    * Adds a new map to the game.
    *
    * @since 1.2
    */
   public void addMap() {
      // Implementation here
   }

   /**
    * Removes the current map from the game.
    *
    * @since 1.2
    */
   public void removeMap() {
      // Implementation here
   }

   /**
    * Shows the map manager dialogue.
    *
    * @since 1.5
    */
   public void showMapManager() {
      javax.swing.SwingUtilities.invokeLater(() -> {
         MapManagerWindow managerWindow = new MapManagerWindow();
         managerWindow.setVisible(true); // This brings the window onto the screen
      });
   }

   /**
    * Inner class handling the UI window for custom map management.
    *
    * @since 1.5
    */
   @SuppressWarnings("serial")
   private class MapManagerWindow extends javax.swing.JFrame {
      /**
       * Constructs the map manager window.
       *
       * @since 1.5
       */
      public MapManagerWindow() {
         this.setTitle("Custom Map Manager");
         this.setSize(350, 200);

         this.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);

         this.setLocationRelativeTo(MapManager.this.parkourMain);
      }
   }

   /**
    * Root object of the maps JSON file.
    *
    * @since 1.5
    */
   private static class MapData {

      /**
       * The maps stored in the JSON file.
       * @since 1.5
       */
      ArrayList<Map> maps = new ArrayList<>();
   }

   /**
    * Creates maps with the default values of their fields.
    *
    * @since 1.5
    */
   private static class MapInstanceCreator implements InstanceCreator<Map> {

      /**
       * Creates a map with the default values of its fields.
       *
       * @param type the type of the instance to create
       * @return a new map
       * @since 1.5
       */
      @Override
      public Map createInstance(Type type) {
         return new Map();
      }
   }
}
