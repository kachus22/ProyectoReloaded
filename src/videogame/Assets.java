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
    //enemy
    public static BufferedImage enemy;        // to store enemy
    //bullet
    public static BufferedImage bullet;        // to store bullet
    //bullet
    public static BufferedImage barrera;        // to store bullet
    //bricks
    public static BufferedImage spritesBrick;   // to store brick sprites
    public static BufferedImage brick[];        // pictures
    //Sounds
    public static SoundClip backgroundSound;    // to store background sound
    public static SoundClip killedSound;        // to store killed sound
    public static SoundClip shootSound;         // to store shoot sound

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
        //enemy
        enemy = ImageLoader.loadImage("/images/Enemigo1.png");
        //bullet
        bullet = ImageLoader.loadImage("/images/bullet.png");
        //barrera
        barrera = ImageLoader.loadImage("/images/barrera.png");
        //bricks
        spritesBrick = ImageLoader.loadImage("/images/bricks.png");
        SpriteSheet brickSheet = new SpriteSheet(spritesBrick);
        brick = new BufferedImage[3];
        brick[0] = brickSheet.crop(0, 0, 80, 30);
        brick[1] = brickSheet.crop(90, 0, 80, 30);
        brick[2] = brickSheet.crop(180, 0, 80, 30);
        //Sounds
        backgroundSound = new SoundClip("/sounds/musicaFondo.wav");
        killedSound = new SoundClip("/sounds/invaderkilled.wav");
        shootSound = new SoundClip("/sounds/shoot.wav");
    }
}
