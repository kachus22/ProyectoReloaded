/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class Game implements Runnable {

    private BufferStrategy bs;          // to have several buffers when displaying
    private Graphics g;                 // to paint objects
    private Display display;            // to display in the game
    String title;                       // title of the window
    private int width;                  // width of the window
    private int height;                 // height of the window
    private Thread thread;              // thread to create the game
    private boolean running;            // to set the game
    private Player player;              // to use a player
    private boolean bossAlive;          // to know boss status
    private int score;                  // to save score
    private int lives;                  // to save lives
    private ArrayList<Brick> bricks;    // to use bricks
    private ArrayList<Bullet> bullets;  // to use bullets
    private ArrayList<Brick> barreras;  // to use barriers
    private Boss boss;                  // to use a boss
    private KeyManager keyManager;      // to manage the keyboard
    private boolean gameOver;           // to manage when player loses
    private boolean gameWin;            // to manage when player wins
    private boolean paused;             // to manage when paused
    private boolean change;             // to know if enemy reached edges
    private int tickCount;              // to know when boss appears
    private int tickCountEnemy;         // to know when enemy could 
    private int tickPower;              // to count when to pop power
    private boolean powerActive;              // to count when to pop power

    /**
     * to create title, width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        gameOver = false;
        gameWin = false;
        paused = false;
        lives = 3;
    }

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * To get if boss is alive
     *
     * @return bossAlive
     */
    public boolean getBossAlive() {
        return bossAlive;
    }

    /**
     * To set if boss is alive
     *
     * @param boss
     */
    public void setBossAlive(boolean b) {
        bossAlive = b;
    }

    /**
     * To get boss
     *
     * @return boss
     */
    public Boss getBoss() {
        return boss;
    }

    /**
     * To get boss
     *
     * @return boss
     */
    public void clearBoss() {
        boss = new Boss(
                0, 0, // x position, y position
                1, 1, // width, height
                1, 1, // xspeed, yspeed
                this);
        boss = null;
        setBossAlive(false);
        tickCount = 0;
    }

    public void loadBoss(int x1, int y1, int xspeed, int yspeed, int direction) {
        bossAlive = true;
        boss = new Boss(
                getWidth() / 2 - 50, 38, // x position, y position
                80, 30, // width, height
                25, 2, // xspeed, yspeed
                this);
        boss.setX(x1);
        boss.setY(y1);
        boss.setXSpeed(xspeed);
        boss.setYSpeed(yspeed);
        boss.setDirection(direction);
        tickCount = 301;
    }

    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, width, height);
        Assets.init();
        player = new Player(
                getWidth() / 2 - 50, getHeight() - 50, // x position, y position
                100, 35, // width, height
                10, 0, // xspeed, yspeed
                this);
        bricks = new ArrayList<Brick>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                bricks.add(new Brick(
                        getWidth() / 3 - 80 + (i * 45), 60 + (j * 50), // x position, y position
                        40, 40, // width, height
                        15, 1, // xspeed, yspeed
                        this));
            }
        }
        // barriers
        barreras = new ArrayList<Brick>();
        for (int k = 100; k <= 1000; k = k + 300) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 3; j++) {
                    Brick barrera = new Brick(k + (i * 40), 550 + (j * 40), // x position, y position 
                            40, 40, // width, height
                            0, 0, // xspeed, yspeed
                            this);
                    barrera.setFriend();
                    barreras.add(barrera);
                }
            }
        }
        bullets = new ArrayList<Bullet>();
        tickCount = 0;
        tickCountEnemy = 0;
        display.getJframe().addKeyListener(keyManager);
        bossAlive = false;
        Assets.backgroundSound.play();
    }

    private void restart() {
        player = new Player(
                getWidth() / 2 - 80, getHeight() - 50, // x position, y position
                100, 35, // width, height
                10, 0, // xspeed, yspeed
                this);
        bricks = new ArrayList<Brick>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                bricks.add(new Brick(
                        getWidth() / 3 - 35 + (i * 90), 60 + (j * 40), // x position, y position
                        40, 40, // width, height
                        15, 1, // xspeed, yspeed
                        this));
            }
        }
        bullets = new ArrayList<Bullet>();
        bossAlive = false;
        boss = null;
        tickCount = 0;
        lives = 3;
    }

    @Override
    public void run() {
        init();
        // frames per second
        int fps = 60;
        //  time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        //  initializing delta
        double delta = 0;
        //  define now to use inside the loop
        long now;
        //intializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            //  setting the time now to the actual time
            now = System.nanoTime();
            //  acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            //  updating the last time
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

    /**
     * To get Player
     *
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * To get Bricks
     *
     * @return bricks
     */
    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    /**
     * To get Bullets
     *
     * @return bricks
     */
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    /**
     * To get KeyManager
     *
     * @return keyManager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    private void tick() {
        // Always check keyManager
        keyManager.tick();
        // If game already started
        if (!gameOver && !gameWin) {
            // Check for pause
            if (keyManager.getP()) {
                if (!paused) {
                    paused = true;
                } else {
                    paused = false;
                }
            }
            // Items things
            if (!paused) {
                if (!bossAlive) {
                    tickCount++;
                    tickPower++;
                }
                if (tickCount > 300 && !bossAlive && !powerActive) {
                    bossAlive = true;
                    boss = new Boss(
                            getWidth() / 2 - 50, 38, // x position, y position
                            80, 30, // width, height
                            25, 2, // xspeed, yspeed
                            this);
                }
                if (tickPower == 300) {
                    Bullet power = new Bullet(
                            this.player.getXMiddle() - 20, this.getPlayer().getY(),//set x and y position
                            18, getHeight() + 5000, //set width and height
                            0, 4, //set xSpeed and ySpeed
                            this);
                    power.setPower();
                    powerActive = true;
                    bullets.add(power);
                }
                if (bossAlive) {
                    boss.tick();
                    if (boss.getX() < -180 || boss.getXFinal() > getWidth() + 180) {
                        boss = null;
                        bossAlive = false;
                        tickCount = 0;
                    }
                }
                player.tick();
                // Check if there are any bricks
                if (bricks.size() < 1) {
                    gameWin = true;
                }
                //check if a bullet must be added
                if (this.getKeyManager().getSpace()) {
                    bullets.add(new Bullet(
                            this.player.getXMiddle() - 20, this.player.getY(),//set x and y position
                            18, 18, //set width and height
                            0, 4, //set xSpeed and ySpeed
                            this));
                    Assets.shootSound.play();
                }
                // tick bricks
                Iterator itr = bricks.iterator();
                while (itr.hasNext()) {
                    Brick brick = (Brick) itr.next();
                    if (tickCountEnemy % 500 == 0) {
                        int r = ThreadLocalRandom.current().nextInt(1, 9999); // To create some enemy bullet
                        if (r > 9995) {
                            Bullet enemybullet = new Bullet(
                                    brick.getXMiddle() - 20, brick.getYFinal(),//set x and y position
                                    18, 18, //set width and height
                                    0, -2, //set xSpeed and ySpeed
                                    this);
                            enemybullet.setEnemy();
                            bullets.add(enemybullet);
                        }
                    }
                    brick.tick();
                    if (brick.getXFinal() > getWidth() - 50) {
                        change = true;
                    }
                    if (brick.getX() < 50) {
                        change = true;
                    }
                }
                // Reached edges, change direction and move down
                if (change) {
                    Iterator itr2 = bricks.iterator();
                    while (itr2.hasNext()) {
                        // change direction and move down
                        Brick brick2 = (Brick) itr2.next();
                        brick2.setXSpeed(brick2.getXSpeed() * -1);
                        brick2.setY(brick2.getY() + 1);
                        brick2.setRows(brick2.getRows() + 1);
                    }
                    change = false;
                }
                // tick bullets
                itr = bullets.iterator();
                while (itr.hasNext()) {
                    Bullet bullet = (Bullet) itr.next();
                    bullet.tick();
                    // check if the bullet crashes
                    Iterator itr2 = bricks.iterator();
                    while (itr2.hasNext()) {
                        Brick brick = (Brick) itr2.next();
                        if (bullet.intersects(brick) && !bullet.isEnemy() && !brick.isFriend()) {
                            if (brick.getStrength() > 2) {
                                score += 40;
                            } else if (brick.getStrength() > 1) {
                                score += 20;
                            } else {
                                score += 10;
                            }
                            //due issues with itr, we reset it
                            bricks.remove(brick);
                            itr2 = bricks.iterator();
                            if (!bullet.isPower()) {
                                bullets.remove(bullet);
                                itr = bullets.iterator();
                            }
                            Assets.killedSound.play();
                        }
                        if (bullet.intersects(boss)) {
                            //boss killed
                            score += 250;
                            boss = null;
                            bossAlive = false;
                            tickCount = 0;
                            bullets.remove(bullet);
                            itr = bullets.iterator();
                            Assets.killedSound.play();
                        }
                        if (bullet.intersects(player) && bullet.isEnemy()) {
                            lives--;
                            if(lives > 1){
                                gameOver = true;
                            }
                            bullets.remove(bullet);
                            itr = bullets.iterator();
                            Assets.killedSound.play();
                        }
                    }
                    // tick barreras
                    Iterator itr3 = barreras.iterator();
                    while (itr3.hasNext()) {
                        Brick brick = (Brick) itr3.next();
                        if (bullet.intersects(brick) && brick.isFriend()) {
                            if (!bullet.isPower()) {
                                bullets.remove(bullet);
                                itr = bullets.iterator();
                            }
                            Assets.killedSound.play();
                            if (brick.getStrength() > 1) {
                                brick.setStrength(brick.getStrength() - 1);
                            } else {
                                //due issues with itr, we reset it
                                barreras.remove(brick);
                                itr3 = barreras.iterator();
                            }

                        }
                    }
                    // Check if bullet is out of screen
                    if (bullet.getY() < -50 || bullet.getY() > getHeight() + 50 && !bullet.isPower()) {
                        bullets.remove(bullet);
                        itr = bullets.iterator();
                    }
                    if (powerActive) {
                        if (tickPower > 330) {
                            tickPower = 0;
                            powerActive = false;
                            bullets.remove(bullet);
                            itr = bullets.iterator();
                        }
                    }
                }
            } // If paused
            else {
                // Load file
                if (keyManager.getL()) {
                    Files.loadFile(this);
                }
                // Save file
                if (keyManager.getS()) {
                    Files.saveFile(this);
                }
            }
        } // If GameOver (win or lose)
        else {
            // Restart game
            if (keyManager.getR()) {
                restart();
                gameOver = false;
                gameWin = false;
            }
        }
    }

    private void render() {
        // get the buffer strategy from display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display
        every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
         */

        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            if (!gameOver && !gameWin) {
                g.drawImage(Assets.background, 0, 0, width, height, null);
                player.render(g);
                if (bossAlive) {
                    boss.render(g);
                }
                // Brick iterator to show all
                Iterator itr = bricks.iterator();
                while (itr.hasNext()) {
                    Brick brick = (Brick) itr.next();
                    brick.render(g);
                }
                itr = bullets.iterator();
                while (itr.hasNext()) {
                    Bullet bullet = (Bullet) itr.next();
                    bullet.render(g);
                }
                Iterator itr2 = barreras.iterator();
                while (itr2.hasNext()) {
                    Brick brick = (Brick) itr2.next();
                    brick.render(g);
                }
            } // Player lost
            else if (gameOver) {
                g.drawImage(Assets.backgroundLose, 0, 0, width, height, null);
            } // Player won
            else {
                g.drawImage(Assets.backgroundWin, 0, 0, width, height, null);
            }
            //Display info
            g.setFont(new Font("Consolas", Font.BOLD, 22));
            g.setColor(Color.white);
            g.drawString("SCORE", 18, 22);
            g.setColor(Color.green);
            g.drawString("" + score, 95, 22);
            g.setColor(Color.white);
            g.drawString("Press 'P' to pause", 295, 22);
            g.drawString("LIVES " + lives + "", 655, 22);
            g.setColor(Color.green);
            //g.drawImage(Assets.pause, 650, 22, 76, 50, null);
            if (paused) {
                g.setFont(new Font("Consolas", Font.BOLD, 30));
                g.setColor(Color.black);
                g.drawImage(Assets.pause, width / 2 - 38, height / 2 - 38, 76, 76, null);
                g.drawString("Press 'L' to load game", width / 2 - 130, height / 2 + 100);
                g.drawString("Press 'S' to save game", width / 2 - 130, height / 2 + 150);
            }
            bs.show();
            g.dispose();
        }

    }

    /**
     * setting the thread for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
