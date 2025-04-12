package com.vincent.parkour_warrior.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesData {
	
	ParkourMain parkourMain;
	
	public PropertiesData(ParkourMain parkourMain) {
		
		this.parkourMain = parkourMain;
		
	}
	
	public void loadProperties() {
		
		/*
		 * properties file format:
		 * map_number, map_path, map_type, is_default_map, have_finished_map, record_time_minutes, record_time_seconds, record_time_milis
		 */
		
		try (FileInputStream propertiesFile = new FileInputStream("ParkourWarrior.properties")){

			Properties properties = new Properties();
			
			properties.load(propertiesFile);
			
			int number = 0;
			
			while(properties.getProperty(Integer.toString(number)) != null) {
				
				String[] data = properties.getProperty(Integer.toString(number)).split(" ");

				parkourMain.mapNumber.add(Integer.parseInt(data[0]));
				parkourMain.mapPath.add(data[1]);
				parkourMain.mapType.add(Integer.parseInt(data[2]));
			    parkourMain.isDefaultMap.add(Boolean.parseBoolean(data[3]));
				parkourMain.haveFinishedMap.add(Boolean.parseBoolean(data[4]));
				parkourMain.recordTimeMinutes.add(Integer.parseInt(data[5]));
				parkourMain.recordTimeSeconds.add(Integer.parseInt(data[6]));
				parkourMain.recordTimeMiliseconds.add(Integer.parseInt(data[7]));
					
				number++;
				
			}
			
		}catch(FileNotFoundException e) {
			
			createPropertiesFile();
			
		}catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void saveProperties() {
		
		try(FileOutputStream propertiesFile = new FileOutputStream("ParkourWarrior.properties")){
			
			Properties properties = new Properties();
			
			for(int num = 0; num < parkourMain.mapNumber.size(); num++) {
				
				String propertiesValue;
				
				propertiesValue = 
						Integer.toString(parkourMain.mapNumber.get(num)) + " " +
				        parkourMain.mapPath.get(num) + " " +
				        Integer.toString(parkourMain.mapType.get(num)) + " " +
			            Boolean.toString(parkourMain.isDefaultMap.get(num)) + " " +
			            Boolean.toString(parkourMain.haveFinishedMap.get(num)) + " " +
			            Integer.toString(parkourMain.recordTimeMinutes.get(num)) + " " +
			            Integer.toString(parkourMain.recordTimeSeconds.get(num)) + " " +
			            Integer.toString(parkourMain.recordTimeMiliseconds.get(num));
				
				properties.setProperty(Integer.toString(num), propertiesValue);
				
			}
			
			properties.store(propertiesFile, null);
			
		}catch(FileNotFoundException e) {
			
			createPropertiesFile();
			
		}catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void createPropertiesFile() {
		
		try (FileOutputStream propertiesFile = new FileOutputStream("ParkourWarrior.properties")){
			
			Properties properties = new Properties();
			
			for(int i = 1; i < 2; i++) {
				
				properties.setProperty(Integer.toString(i), 
						Integer.toString(i) +
						" /map/map" + Integer.toString(i - 1) + ".txt" +
						" 1"+
						" true" + 
						" false"+
						" 0 0 0"
						);
				
			}
			
			properties.store(propertiesFile, null);
			
			loadProperties();
			
		}catch(IOException e) {
			
			System.err.println(e.getMessage());
			
			e.printStackTrace();
			
		}
		
	}
	public void addMap() {}
	
	public void removeMap() {}

}
