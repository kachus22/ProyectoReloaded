/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.image.BufferedImage;

public class Assets {

    //img general
    public static BufferedImage background;     // to store background image
    public static BufferedImage backgroundLose; // to store background Lose image
    public static BufferedImage backgroundWin;  // to store background Win image
    public static BufferedImage pause;          // to store pause image
    //player
    public static BufferedImage player;        // to store player
    
    /**
     * initializing the images of the game
     */
    public static void init() {
        //img general
        background = ImageLoader.loadImage("/images/Playa.jpg");
        backgroundLose = ImageLoader.loadImage("/images/restart.png");
        backgroundWin = ImageLoader.loadImage("/images/winbg.jpg");
        pause = ImageLoader.loadImage("/images/pause.png");

        //img objects
        //player
        player = ImageLoader.loadImage("/images/Gun.png");
    }
}
