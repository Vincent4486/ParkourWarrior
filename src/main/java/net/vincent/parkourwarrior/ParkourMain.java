package net.vincent.parkourwarrior;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * The central class of Parkour Warrior.
 * <p>
 * This class initialized the {@code JPanel} and {@code Graphics2D} for the
 * game's drawer and frame to draw. This class also defines global fields for
 * the use of the other classes, and create instances of other classes of this
 * game in order to make the initialization not stuck in an infinite loop.
 * </p>
 * @author Vincent4486
 * @version 1.2
 * @since 1.0
 */

@SuppressWarnings("serial")
public class ParkourMain extends JPanel implements Runnable {

   /**
    * This is the original tile size for tiles in Parkour Warrior.
    * @since 1.0
    */
   final int originalTileSize = 16;

   /**
    * This is the scale for the original tile size, meaning the real tile size
    * is
    * {@code tileSize = originalTileSize * scale}.
    * @since 1.0
    */
   final int scale = 3;

   /*
    * width == column
    * height == row
    * vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
    */

   /**
    * This is mentioned at {@code final int scale}, and it is the final tile
    * size for this game.
    * @since 1.0
    */
   public final int tileSize = originalTileSize * scale;

   /**
    * This is the maximum amount of tiles allowed in a row.
    * @since 1.0
    */
   public final int maxWidthTiles = 16;

   /**
    * This is the maximum amount of tiles allowed in a column.
    * @since 1.0
    */
   public final int maxHeightTiles = 11;

   /**
    * This is the maximum screen width in pixels, which is {@code maxWidthTiles
    * * tileSize}
    * @since 1.0
    */
   public final int screenWidth = tileSize * maxWidthTiles;

   /**
    * This is the screen maximum height in pixels, which is {@code
    * maxHeightTiles * tileSize}
    * @since 1.0
    */
   public final int screenHeight = tileSize * maxHeightTiles;

   /**
    * This is the maximum columns of ties that can be in a world.
    * @since 1.1
    */
   public final int maxWorldColumn = 68;

   /**
    * This is the maximum rows of tiles that can be in a world.
    * @since 1.1
    */
   public final int maxWorldRow = 11;

   /*
    * public int recordTimeMiliseconds is 2 digit miliseconds.
    * public int currentMap means current map number, number is in properties
    * file.
    */

   /**
    * This is the map number for the program to load the maps,
    * defined for {@code PropertiesData} and {@code TileManager}.
    * @since 1.1
    */
   public ArrayList<Integer> mapNumber;

   /**
    * This is the path to maps in the system, defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.1
    */
   public ArrayList<String> mapPath;

   /**
    * This is the type of map, defined for
    * {@code PropertiesData} and {@code TileManager},
    * currently unused.
    * @since 1.1
    */
   public ArrayList<Integer> mapType;

   /**
    * This is the type of map (e.g. default), defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.1
    */
   public ArrayList<Boolean> isDefaultMap;

   /**
    * This is for Graphics2D to see if the it need to
    * print time next to the map, defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.1
    */
   public ArrayList<Boolean> haveFinishedMap;

   /**
    * The shortest minutes used to finish a map, defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.1
    */
   public ArrayList<Integer> recordTimeMinutes;

   /**
    * The shortest seconds used to finish a map, defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.1
    */
   public ArrayList<Integer> recordTimeSeconds;

   /**
    * The shortest milliseconds used to finish a map, defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.1
    */
   public ArrayList<Integer> recordTimeMiliseconds;

   /**
    * The end index, defined for
    * {@code PropertiesData} and {@code TileManager}.
    * @since 1.1
    */
   public ArrayList<Integer> endIndex;

   /**
    * To determine if the program needs to play music.
    * @since 1.0
    */
   public boolean musicOn;

   /**
    * To indicate the current map state.
    * <p>
    * For loop to determine call which draw method (e.g. title screen, playing,
    * finish screen).
    * </p>
    * @since 1.1
    */
   public int currentMapState = 0;

   /**
    * To determine which map is the player currently playing.
    * @since 1.1
    */
   public int currentMap = 1;

   /**
    * The number for title map state.
    * @since 1.1
    */
   public final int title = 0;

   /**
    * The number for playing map state.
    * @since 1.1
    */
   public final int play = 1;

   /**
    * The number for the finish screen.
    * @since 1.1
    */
   public final int finish = 2;

   /**
    * The number for the default maps, which is inside the JAR.
    * @since 1.1
    */
   public final int defaultPlayMap = 1;

   /**
    * The number for custom maps.
    * <p>
    * The map can be anywhere in the system
    * that the program have permission for.
    * </p>
    * @since 1.1
    */
   public final int customPlayMap = 2;

   /**
    * The time when the timer is started, for time calculation.
    * @since 1.2
    */
   public long timerStartTime;

   /**
    * The FPS of the game, currently 60.
    * @since 1.0
    */
   public final double FPS = 1_000_000_000 / 60;

   /**
    * The {@code Thread} for game.
    * @since 1.0
    */
   Thread thread;

   /**
    * The {@code URL} for music of game.
    * @since 1.1
    */
   URL soundURL;

   /**
    * The {@code Clip} for the music to play.
    * @since 1.1
    */
   Clip soundClip;

   /**
    * The {ImageIcon} for the game.
    * @since 1.0
    */
   public ImageIcon ico;

   /**
    * Instance declaration for {@code Player} class, used by all.
    * @since 1.0
    */
   public Player player;

   /**
    * Instance declaration for {@code TileManager} class, used by all.
    * @since 1.0
    */
   public TileManager tileManager;

   /**
    * Instance declaration for {@code PropertiesData}, used by all.
    * @since 1.1
    */
   public PropertiesData propertiesData;

   /**
    * Instance declaration for {@code TitleScreen} class, used by all.
    * @since 1.2
    */
   public TitleScreen titleScreen;

   /**
    * Instance declaration for {@code ParkourTime} class, used by all.
    * @since 1.1
    */
   public ParkourTimer parkourTimer;

   /**
    * Instance declaration for {@code FinishScreen} class, used by all.
    * @since 1.2
    */
   public FinishScreen finishScreen;

   /**
    * The instance declaration for {@code KeyHandler}, used by all.
    * @since 1.0
    */
   public KeyHandler keyHandler;

   /**
    * The font used by the game.
    * @since 1.2
    */
   public Font pixelFont;

   /**
    * The constructor for class {@code ParkourMain}.
    * <p>
    * This constructor defines instances of each class for all classes to use,
    * for preventing loops and conserving memory. This constructor also calls
    * startup methods since this runs only after the {@code JFrame} is created,
    * for example the game's main {@code Thread}.
    * </p>
    * @since 1.0
    */
   public ParkourMain() {

      ico = new ImageIcon(
         Objects.requireNonNull(getClass().getResource("/player/right1.png")));

      mapNumber = new ArrayList<>();
      mapPath = new ArrayList<>();
      mapType = new ArrayList<>();
      isDefaultMap = new ArrayList<>();
      haveFinishedMap = new ArrayList<>();
      recordTimeMinutes = new ArrayList<>();
      recordTimeSeconds = new ArrayList<>();
      recordTimeMiliseconds = new ArrayList<>();
      endIndex = new ArrayList<>();

      propertiesData = new PropertiesData(this);
      propertiesData.loadProperties();

      parkourTimer = new ParkourTimer(this);
      titleScreen = new TitleScreen(this);
      finishScreen = new FinishScreen(this);
      player = new Player(this);
      tileManager = new TileManager(this);
      keyHandler = new KeyHandler(this);

      tileManager.loadMap(mapPath);

      thread = new Thread(this);

      getSound("/sound/ParkourWarrior.wav");
      getPixelFont("/font/Pixel.ttf");

      soundClip.start();
      musicOn = true;
      soundClip.loop(Clip.LOOP_CONTINUOUSLY);

      this.setPreferredSize(new Dimension(screenWidth, screenHeight));
      this.setBackground(Color.black);
      this.setDoubleBuffered(true);
      this.addKeyListener(keyHandler);
      this.setFocusable(true);
   }
   /**
    * The method from {@code Runnable} interface, to control what
    * would be done each frame.
    * @since 1.0
    */
   @Override
   public void run() {
      // TODO Auto-generated method stub

      double drawInterval = 1000000000 / 60;
      double nextDrawTime = System.nanoTime() + drawInterval;

      while (thread != null) {

         update();

         repaint();

         try {

            double remainingTime = nextDrawTime - System.nanoTime();
            remainingTime = remainingTime / 1000000;

            if (remainingTime < 0) {
               remainingTime = 0;
            }

            Thread.sleep((long)remainingTime);

            nextDrawTime += drawInterval;

         } catch (InterruptedException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
         }
      }
   }

   /**
    * The method to call updates on updates on values of the game,
    * like the player position.
    * @since 1.0
    */
   public void update() {

      if (currentMapState == play) {

         player.updatePlayer();
         parkourTimer.runTimer(timerStartTime, FPS);
      }
   }

   /**
    * The method for calling paint methods in each state for each class.
    * @param g the {@code Graphics} object to protect
    * @since 1.0
    */
   public void paintComponent(Graphics g) {

      super.paintComponent(g);

      Graphics2D graphics2D = (Graphics2D)g;

      graphics2D.setFont(pixelFont);

      if (currentMapState == play) {

         tileManager.drawTile(graphics2D);
         player.drawPlayer(graphics2D);
         parkourTimer.drawTimer(graphics2D);

      } else if (currentMapState == title)

         titleScreen.drawTitleScreen(graphics2D);

      else if (currentMapState == finish) {

         finishScreen.drawFinishScreen(graphics2D);
      }
      graphics2D.dispose();
   }

   /**
    * The method to get the sound file from {@code res} and puts the
    * audio into a {@code Clip}.
    * @param path Path to the audio inside the JAR.
    * @since 1.1
    */
   public void getSound(String path) {

      soundURL = Objects.requireNonNull(getClass().getResource(path));

      try {

         AudioInputStream audioInputStream =
            AudioSystem.getAudioInputStream(soundURL);
         soundClip = AudioSystem.getClip();
         soundClip.open(audioInputStream);

      } catch (IOException e) {

         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      }
   }

   /**
    * The method to get the font file in {@code res} and
    * create a {@code Font} for the program to use.
    * @since 1.2
    * @param path The path to the font file.
    */
   public void getPixelFont(String path) {

      try {

         pixelFont = Font.createFont(
            Font.TRUETYPE_FONT,
            Objects.requireNonNull(getClass().getResourceAsStream(path)));

      } catch (FontFormatException e) {

         // TODO Auto-generated catch block
         e.printStackTrace();

      } catch (IOException e) {

         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
