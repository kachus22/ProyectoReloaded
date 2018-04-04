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

public class Boss extends Item {

    private Game game;              // runs game    
    private int strength;           // how many times it needs to get hit
    private int tickCount;          // to tick
    private int direction;          // to know direction

    public Boss(int x, int y, int width, int height, int xSpeed, int ySpeed, Game game) {
        super(x, y, width, height, xSpeed, ySpeed); // assigns x and y, width and height
        this.game = game;
        this.strength = ThreadLocalRandom.current().nextInt(1, 5);
        tickCount = 0;
        direction = ThreadLocalRandom.current().nextInt(1, 5);
        if (direction < 3) {
            setX(game.getWidth() + 100);
        } else {
            setX(-100);
        }
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
     * Get direction value
     *
     * @return direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Set strength value
     *
     * @param strength
     */
    public void setDirection(int n) {
        this.direction = n;
    }

    /**
     * To update positions of the player for every tick
     */
    @Override
    public void tick() {
        tickCount++;
        if (tickCount > 10) {
            if (direction < 3) {
                setX(getX() - xSpeed);
            } else {
                setX(getX() + xSpeed);
            }
            tickCount = 0;
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
        if (this.strength == 3) {
            g.drawImage(Assets.brick[0], getX(), getY(), getWidth(), getHeight(), null);
        } else if (this.strength == 2) {
            g.drawImage(Assets.brick[1], getX(), getY(), getWidth(), getHeight(), null);
        } else {
            g.drawImage(Assets.brick[2], getX(), getY(), getWidth(), getHeight(), null);
        }
    }
}
