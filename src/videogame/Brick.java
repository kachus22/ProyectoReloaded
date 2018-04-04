/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;

public class Brick extends Item {

    private Game game;              // runs game    
    private int strength;           // how many times it needs to get hit
    private int power;              // if it hides a power
    private int tickCount;          // to tick
    private int rows;               // to know rows moved
    private boolean friend;         // to know if it's barrier

    public Brick(int x, int y, int width, int height, int xSpeed, int ySpeed, Game game) {
        super(x, y, width, height, xSpeed, ySpeed); // assigns x and y, width and height
        this.game = game;
        this.strength = ThreadLocalRandom.current().nextInt(1, 4);
        this.power = ThreadLocalRandom.current().nextInt(1, 9);
        tickCount = 0;
        rows = 0;
    }

    /**
     * To set enemy bullet
     *
     * @return enemy
     */
    public boolean isFriend() {
        return friend;
    }

    /**
     * To set enemy bullet
     *
     * @param enemy
     */
    public void setFriend() {
        friend = true;
    }

    /**
     * Get tickCount value
     *
     * @return tickCount
     */
    public int getTickCount() {
        return tickCount;
    }

    /**
     * Set tickCount value
     *
     * @param tickCount
     */
    public void setTickCount(int n) {
        this.tickCount = n;
    }

    /**
     * Get power value
     *
     * @return power
     */
    public int getPower() {
        return power;
    }

    /**
     * Get strength value
     *
     * @return strength
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Set strength value
     *
     * @param strength
     */
    public void setStrength(int n) {
        this.strength = n;
    }

    /**
     * Get rows value
     *
     * @return rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Set rows value
     *
     * @param rows
     */
    public void setRows(int n) {
        this.rows = n;
    }

    /**
     * To update positions of the player for every tick
     */
    @Override
    public void tick() {
        tickCount++;
        if (tickCount > 30) {
            setX(getX() + xSpeed);
            tickCount = 0;
            if (rows > 3) {
                setXSpeed(getXSpeed() + 5);
                rows = 0;
            }
        }
    }

    /**
     * To paint the enemy
     *
     * @param g <b>Graphics</b> object to paint the enemy
     */
    @Override
    public void render(Graphics g) {
        //Change Assets based on strength status.

        if (!isFriend()) {
            g.drawImage(Assets.enemy, getX(), getY(), getWidth(), getHeight(), null);
        } else {
            if (this.strength == 3) {
                g.drawImage(Assets.brick[0], getX(), getY(), getWidth(), getHeight(), null);
            } else if (this.strength == 2) {
                g.drawImage(Assets.brick[1], getX(), getY(), getWidth(), getHeight(), null);
            } else {
                g.drawImage(Assets.brick[2], getX(), getY(), getWidth(), getHeight(), null);
            }
            g.drawImage(Assets.barrera, getX(), getY(), getWidth(), getHeight(), null);
        }
    }
}
