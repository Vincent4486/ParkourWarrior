package org.vyang.parkourwarrior;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Properties data handler for Parkour Warrior.
 * <p>
 * This class manages the game's persistent data by reading and
 * writing to a properties file. It stores map configurations,
 * record times, and other game settings that persist between
 * sessions.
 * </p>
 *
 * @author Vincent4486
 * @version 1.3
 * @since 1.1
 */
public class MapManager {

   /**
    * Reference to the main game panel.
    * @since 1.1
    */
   ParkourMain parkourMain;

   /**
    * The absolute file path to the properties file.
    * @since 1.1
    */
   String filePath = System.getProperty("user.home") +
                     "/.config/ParkourWarrior/maps.properties";

   /**
    * The array of game maps loaded from the properties file.
    * @since 1.5
    */
   public ArrayList<Map> gameMaps;

   /**
    * Constructs a new {@code MapManager} with a reference to
    * the main game panel.
    *
    * @param parkourMain the main game panel instance
    * @since 1.1
    */
   public MapManager(ParkourMain parkourMain) {
      this.parkourMain = parkourMain;

      gameMaps = new ArrayList<>();
      loadMapProperties();
   }

   /**
    * Loads map and game data from the properties file.
    * <p>
    * Reads each numbered property entry and parses it into
    * map number, path, type, default status, finish status,
    * record times, and end positions, populating the
    * corresponding lists in {@code ParkourMain}.
    * </p>
    *
    * @since 1.1
    */
   public void loadMapProperties() {
      /*
       * Expected properties file format for each key:
       * map_number map_path map_type is_default_map have_finished_map
       * record_time_minutes record_time_seconds record_time_milis end_position
       * For example:
       * 0=1 /map/map0.txt 1 true false 0 0 0 400
       */
      System.out.println("Attempting to load properties file from: " +
                         filePath);
      try (FileInputStream propertiesFile = new FileInputStream(filePath)) {
         Properties properties = new Properties();
         properties.load(propertiesFile);

         int number = 0;
         while (properties.getProperty(Integer.toString(number)) != null) {
            String dataLine = properties.getProperty(Integer.toString(number));
            String[] data = dataLine.split(" ");

            int mapNumber = Integer.parseInt(data[0]);
            String rawPath = data[1].trim();

            // Adjust the map path so it points to /res/map/...
            String mapPath;
            /*if (!rawPath.startsWith("/res")) {
                if (!rawPath.startsWith("/")) {
                    mapPath = "/res/" + rawPath;
                } else {
                    mapPath = "/res" + rawPath;
                }
            } else {
                mapPath = rawPath;
            }*/
            mapPath = rawPath;

            int mapType = Integer.parseInt(data[2]);
            boolean isDefault = Boolean.parseBoolean(data[3]);
            boolean haveFinished = Boolean.parseBoolean(data[4]);
            int recordMinutes = Integer.parseInt(data[5]);
            int recordSeconds = Integer.parseInt(data[6]);
            int recordMilis = Integer.parseInt(data[7]);
            int endIndex = Integer.parseInt(data[8]);

            // Add the data to the corresponding list in ParkourMain.
            Map map = new Map();
            map.mapNumber = mapNumber;
            map.mapPath = mapPath;
            map.mapType = mapType;
            map.isDefaultMap = isDefault;
            map.haveFinishedMap = haveFinished;
            map.recordTimeMinutes = recordMinutes;
            map.recordTimeSeconds = recordSeconds;
            map.recordTimeMiliseconds = recordMilis;
            map.endIndex = endIndex;
            this.gameMaps.add(map);

            number++;
         }

         System.out.println("Loaded properties successfully.");
         // Optionally: now call the map-loading method using
         // parkourMain.mapPath parkourMain.loadMaps(parkourMain.mapPath);

      } catch (FileNotFoundException e) {
         System.out.println(
            "Properties file not found. Creating a new one at: " + filePath);
         createPropertiesFile();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Saves the current map and game data to the properties file.
    * <p>
    * Writes all map entries from {@code ParkourMain} lists back
    * to the properties file, preserving the current game state.
    * </p>
    *
    * @since 1.1
    */
   public void saveMapProperties() {
      System.out.println("Saving properties to: " + filePath);
      try (FileOutputStream propertiesFile = new FileOutputStream(filePath)) {
         Properties properties = new Properties();

         for (int num = 0; num < this.gameMaps.size(); num++) {
            String propertiesValue =
               this.gameMaps.get(num).mapNumber + " " +
               this.gameMaps.get(num).mapPath + " " +
               this.gameMaps.get(num).mapType + " " +
               this.gameMaps.get(num).isDefaultMap + " " +
               this.gameMaps.get(num).haveFinishedMap + " " +
               this.gameMaps.get(num).recordTimeMinutes + " " +
               this.gameMaps.get(num).recordTimeSeconds + " " +
               this.gameMaps.get(num).recordTimeMiliseconds + " " +
               this.gameMaps.get(num).endIndex;

            properties.setProperty(Integer.toString(num), propertiesValue);
         }

         properties.store(propertiesFile, "Parkour Warrior Properties");
         System.out.println("Properties file saved successfully.");
      } catch (FileNotFoundException e) {
         createPropertiesFile();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Creates a new properties file with default map entries.
    * <p>
    * This method is called when no existing properties file is
    * found. It writes default map configurations and then
    * reloads the properties.
    * </p>
    *
    * @since 1.1
    */
   public void createPropertiesFile() {
      System.out.println("Creating properties file at: " + filePath);

      try (FileOutputStream propertiesFile = new FileOutputStream(filePath)) {
         Properties properties = new Properties();
         // Sample properties entry with key "0"
         String propertiesValue1 = "1 /map/map0.txt 1 true false 0 0 0 2740";
         properties.setProperty("0", propertiesValue1);

         String propertiesValue2 = "2 /map/map1.txt 1 true false 0 0 0 3017";
         properties.setProperty("1", propertiesValue2);

         String propertiesValue3 = "3 /map/map2.txt 1 true false 0 0 0 3100";
         properties.setProperty("2", propertiesValue3);

         properties.store(propertiesFile, "Parkour Warrior Sample Properties");
         System.out.println("Properties file created successfully.");
         // Reload properties after file creation.
         loadMapProperties();
      } catch (IOException e) {
         System.err.println("Error writing properties file: " + e.getMessage());
         e.printStackTrace();
      }
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
}
