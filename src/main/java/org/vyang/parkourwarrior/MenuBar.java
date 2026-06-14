package org.vyang.parkourwarrior;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import javax.swing.*;

/**
 * The menu bar handler for ParkourWarrior
 * <p>
 * This class manages the menu bar system
 * of this game, which allows the user to
 * call specific functions on the game
 * via the menu bar instead of the buttons
 * </p>
 *
 * @author Vincent4486
 * @version 1.5
 * @since 1.4
 */
@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements ActionListener {

   /**
    * Reference to the main game panel.
    * @since 1.1
    */
   ParkourMain parkourMain;

   // Top-level dropdown menus
   private JMenu file;
   private JMenu edit;
   private JMenu window;
   private JMenu help;

   // Menu items
   private JMenuItem fileNew;
   private JMenuItem fileRestart;
   private JMenuItem fileQuit;

   private JMenuItem editManageMaps;
   private JMenuItem editToggleMusic;
   private JMenuItem editAddMap;
   private JMenuItem editRemoveMap;
   private JMenuItem editPreferences;

   private JMenuItem windowFullscreen;

   private JMenuItem helpControls;
   private JMenuItem helpDocumentation;
   private JMenuItem helpAbout;

   /**
    * The constructor of this class which creates the reference
    * to the main instance and initializes the menu bar with
    * available options.
    * @param parkourMain The reference of main game instance
    */
   public MenuBar(ParkourMain parkourMain) {
      this.parkourMain = parkourMain;

      initializeMenus();
      loadMenus();
   }

   /**
    * Instantiates the menus and their child items.
    */
   private void initializeMenus() {
      file = new JMenu("File");
      edit = new JMenu("Edit");
      window = new JMenu("Window");
      help = new JMenu("Help");

      fileNew = new JMenuItem("New Game");
      fileNew.setActionCommand("FILE_NEW");
      fileNew.addActionListener(this);

      fileRestart = new JMenuItem("Restart Map");
      fileRestart.setActionCommand("FILE_RESTART");
      fileRestart.addActionListener(this);

      fileQuit = new JMenuItem("Quit");
      fileQuit.setActionCommand("FILE_QUIT");
      fileQuit.addActionListener(this);

      file.add(fileNew);
      file.add(fileRestart);
      file.addSeparator();
      file.add(fileQuit);

      editManageMaps = new JMenuItem("Manage Maps");
      editManageMaps.setActionCommand("EDIT_MANMAPS");
      editManageMaps.addActionListener(this);

      editToggleMusic = new JMenuItem("Toggle Music");
      editToggleMusic.setActionCommand("EDIT_TOGGLE_MUSIC");
      editToggleMusic.addActionListener(this);

      editAddMap = new JMenuItem("Add Map");
      editAddMap.setActionCommand("EDIT_ADD_MAP");
      editAddMap.addActionListener(this);

      editRemoveMap = new JMenuItem("Remove Map");
      editRemoveMap.setActionCommand("EDIT_REMOVE_MAP");
      editRemoveMap.addActionListener(this);

      editPreferences = new JMenuItem("Preferences");
      editPreferences.setActionCommand("EDIT_PREFERENCES");
      editPreferences.addActionListener(this);

      edit.add(editToggleMusic);
      edit.addSeparator();
      edit.add(editManageMaps);
      edit.add(editAddMap);
      edit.add(editRemoveMap);
      edit.addSeparator();
      edit.add(editPreferences);

      windowFullscreen = new JMenuItem("Fullscreen");
      windowFullscreen.setActionCommand("WINDOW_FULLSCREEN");
      windowFullscreen.addActionListener(this);

      window.add(windowFullscreen);

      helpControls = new JMenuItem("Controls");
      helpControls.setActionCommand("HELP_CONTROLS");
      helpControls.addActionListener(this);

      helpDocumentation = new JMenuItem("Documentation");
      helpDocumentation.setActionCommand("HELP_DOCUMENTATION");
      helpDocumentation.addActionListener(this);

      helpAbout = new JMenuItem("About");
      helpAbout.setActionCommand("HELP_ABOUT");
      helpAbout.addActionListener(this);

      help.add(helpControls);
      help.add(helpDocumentation);
      help.addSeparator();
      help.add(helpAbout);
   }

   /**
    * Loads the components directly into this JMenuBar instance.
    *
    * @since 1.4
    */
   private void loadMenus() {
      this.add(file);
      this.add(edit);
      this.add(window);
      this.add(help);
   }

   /**
    * The central action processing method.
    * Every component configured with ".addActionListener(this)" routes here.
    */
   @Override
   public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();

      switch (command) {
      case "FILE_NEW":
         break;

      case "FILE_RESTART":
         break;

      case "FILE_QUIT":
         parkourMain.quit(0);
         break;

      case "EDIT_MANMAPS":
         parkourMain.mapManager.showMapManager();
         break;

      case "EDIT_TOGGLE_MUSIC":
         break;

      case "EDIT_ADD_MAP":
         break;

      case "EDIT_REMOVE_MAP":
         break;

      case "EDIT_PREFERENCES":
         break;

      case "WINDOW_FULLSCREEN":
         break;

      case "HELP_CONTROLS":
         break;

      case "HELP_DOCUMENTATION":
         try {
            Desktop.getDesktop().browse(
               new URI("https://docs.vyang.org/ParkourWarrior"));
         } catch (Exception ex) {
            ex.printStackTrace();
         }
         break;

      case "HELP_ABOUT":
         break;

      default:
         System.out.println("Unknown menu action: " + command);
         break;
      }
   }
}