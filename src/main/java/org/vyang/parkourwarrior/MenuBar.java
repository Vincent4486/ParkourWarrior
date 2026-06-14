package org.vyang.parkourwarrior;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * @version 1.4
 * @since 1.4
 */
public class MenuBar extends JMenuBar implements ActionListener {
   /**
    * Reference to the main game panel.
    * @since 1.1
    */
   ParkourMain parkourMain;

   /**
    * The constructor of this class which creates the reference
    * to the main instance and initializes the menu bar with
    * available options.
    * @param parkourMain The reference of main game instance
    */
   public MenuBar(ParkourMain parkourMain) {
      this.parkourMain = parkourMain;

      loadMenus();
   }

   /**
    * Loads the menu into the menu bar base on the avalible
    * menus in local class {@code Menus}
    *
    * @since 1.4
    */
   private void loadMenus() {
      Menus menus = new Menus();

      this.add(menus.file);
      this.add(menus.edit);
      this.add(menus.view);
      this.add(menus.window);
      this.add(menus.help);
   }

   @Override
   public void actionPerformed(ActionEvent e) {}

   /**
    * Defines all menu labels and menu
    * items shown in the menu bar
    *
    * @author Vincent4486
    * @version 1.4
    * @since 1.4
    */
   private class Menus {
      /**
       * Declaration of the file menu
       *
       * @since 1.4
       */
      JMenu file;

      /**
       * Declaration of the edit menu
       *
       * @since 1.4
       */
      JMenu edit;

      /**
       * Declaration of the view menu
       *
       * @since 1.4
       */
      JMenu view;

      /**
       * Declaration of the window menu
       *
       * @since 1.4
       */
      JMenu window;

      /**
       * Declaration of the help menu
       *
       * @since 1.4
       */
      JMenu help;

      /**
       * The constructor of the {@code Menus} class
       * which instantiates the menus
       *
       * @since 1.4
       */
      public Menus() {
         file = new JMenu("File");
         edit = new JMenu("Edit");
         view = new JMenu("View");
         window = new JMenu("Window");
         help = new JMenu("Help");
      }
   }
}
