package net.vincent.ParkourWarrior;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    ParkourMain parkourMain;

    public KeyHandler(ParkourMain parkourMain){
        this.parkourMain = parkourMain;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

        if(parkourMain.currentMapState == parkourMain.play) {

            switch(e.getKeyCode()) {
                case KeyEvent.VK_A:parkourMain.player.goLeft = true;break;
                case KeyEvent.VK_D:parkourMain.player.goRight = true;break;
                case KeyEvent.VK_SPACE:

                    if (!parkourMain.player.askJump) {

                        parkourMain.player.maxJumpHeight = parkourMain.player.worldY - parkourMain.player.jumpHeight;

                        parkourMain.player.askJump = true;

                    }
                    break;
                case KeyEvent.VK_ESCAPE:parkourMain.currentMapState = parkourMain.title;parkourMain.player.worldX = 480; parkourMain.player.worldY= 376;break;
                case KeyEvent.VK_SHIFT:parkourMain.player.sneaking = true;parkourMain.player.walkSpeed = 1;break;
            }

        }

        if(parkourMain.currentMapState == parkourMain.title) {

            if (e.getKeyCode() == KeyEvent.VK_W) {

                parkourMain.titleScreen.choosedMap = (parkourMain.titleScreen.choosedMap - 1 + parkourMain.mapPath.size()) % parkourMain.mapPath.size();

            } else if (e.getKeyCode() == KeyEvent.VK_S) {

                parkourMain.titleScreen.choosedMap = (parkourMain.titleScreen.choosedMap + 1) % parkourMain.mapPath.size();

            } else if(e.getKeyCode() == KeyEvent.VK_ENTER) {

                parkourMain.currentMap = parkourMain.titleScreen.choosedMap;
                parkourMain.currentMapState = parkourMain.play;
                parkourMain.timerStartTime = System.nanoTime();

            }else if (e.getKeyCode() == KeyEvent.VK_A) {

                parkourMain.propertiesData.addMap();

            }else if (e.getKeyCode() == KeyEvent.VK_D) {

                parkourMain.propertiesData.removeMap();

            }

        }
        if(e.getKeyCode() == KeyEvent.VK_Q) {

            if(parkourMain.musicOn == true) {

                parkourMain.musicOn = false;
                parkourMain.soundClip.stop();

            }else {

                parkourMain.musicOn = true;
                parkourMain.soundClip.start();;

            }

        }

        if(parkourMain.currentMapState == parkourMain.finish) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER)

                parkourMain.currentMapState = parkourMain.title;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

        switch(e.getKeyCode()) {
            case KeyEvent.VK_A:parkourMain.player.goLeft = false;parkourMain.player.momentumLeft = true;break;
            case KeyEvent.VK_D:parkourMain.player.goRight = false;parkourMain.player.momentumRight = true;break;
            case KeyEvent.VK_SHIFT:parkourMain.player.sneaking = false; parkourMain.player.walkSpeed = 6; break; // Reset walk speed when sneaking is released
        }

    }

}
