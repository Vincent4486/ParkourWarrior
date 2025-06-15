package com.vincent.parkour_warrior.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesData {
    
    ParkourMain parkourMain;
    // Build the absolute file path based on the current working directory.
    String filePath = System.getProperty("user.dir") + "/ParkourWarrior.properties";
    
    public PropertiesData(ParkourMain parkourMain) {
        this.parkourMain = parkourMain;
    }
    
    public void loadProperties() {
        /*
         * Expected properties file format for each key:
         * map_number map_path map_type is_default_map have_finished_map record_time_minutes record_time_seconds record_time_milis
         * For example: 
         * 0=1 /map/map0.txt 1 true false 0 0 0
         */
        System.out.println("Attempting to load properties file from: " + filePath);
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
                if (!rawPath.startsWith("/res")) {
                    if (!rawPath.startsWith("/")) {
                        mapPath = "/res/" + rawPath;
                    } else {
                        mapPath = "/res" + rawPath;
                    }
                } else {
                    mapPath = rawPath;
                }
                
                int mapType = Integer.parseInt(data[2]);
                boolean isDefault = Boolean.parseBoolean(data[3]);
                boolean haveFinished = Boolean.parseBoolean(data[4]);
                int recordMinutes = Integer.parseInt(data[5]);
                int recordSeconds = Integer.parseInt(data[6]);
                int recordMilis = Integer.parseInt(data[7]);
                
                // Add the data to the corresponding lists in ParkourMain.
                parkourMain.mapNumber.add(mapNumber);
                parkourMain.mapPath.add(mapPath);
                parkourMain.mapType.add(mapType);
                parkourMain.isDefaultMap.add(isDefault);
                parkourMain.haveFinishedMap.add(haveFinished);
                parkourMain.recordTimeMinutes.add(recordMinutes);
                parkourMain.recordTimeSeconds.add(recordSeconds);
                parkourMain.recordTimeMiliseconds.add(recordMilis);
                
                number++;
            }
            
            System.out.println("Loaded properties successfully.");
            // Optionally: now call the map-loading method using parkourMain.mapPath
            // parkourMain.loadMaps(parkourMain.mapPath);
            
        } catch (FileNotFoundException e) {
            System.out.println("Properties file not found. Creating a new one at: " + filePath);
            createPropertiesFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void saveProperties() {
        System.out.println("Saving properties to: " + filePath);
        try (FileOutputStream propertiesFile = new FileOutputStream(filePath)) {
            Properties properties = new Properties();
            
            for (int num = 0; num < parkourMain.mapNumber.size(); num++) {
                String propertiesValue = 
                        parkourMain.mapNumber.get(num) + " " +
                        parkourMain.mapPath.get(num) + " " +  
                        parkourMain.mapType.get(num) + " " +
                        parkourMain.isDefaultMap.get(num) + " " +
                        parkourMain.haveFinishedMap.get(num) + " " +
                        parkourMain.recordTimeMinutes.get(num) + " " +
                        parkourMain.recordTimeSeconds.get(num) + " " +
                        parkourMain.recordTimeMiliseconds.get(num);
                
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
    
    public void createPropertiesFile() {
        System.out.println("Creating properties file at: " + filePath);
        try (FileOutputStream propertiesFile = new FileOutputStream(filePath)) {
            Properties properties = new Properties();
            // Sample properties entry with key "0"
            String propertiesValue = "1 /map/map0.txt 1 true false 0 0 0";
            properties.setProperty("0", propertiesValue);
            
            properties.store(propertiesFile, "Parkour Warrior Sample Properties");
            System.out.println("Properties file created successfully.");
            // Reload properties after file creation.
            loadProperties();
        } catch (IOException e) {
            System.err.println("Error writing properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Empty methods for further functionality:
    public void addMap() {
        // Implementation here
    }
    
    public void removeMap() {
        // Implementation here
    }
}

