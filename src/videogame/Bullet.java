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

/**
 *
 * @author kachu
 */
public class Bullet extends Item {

    private Game game;              // runs game   
    private boolean enemy;          // to know if enemy shoot
    private boolean power;          // to know if power shoot

    public Bullet(int x, int y, int width, int height, int xSpeed, int ySpeed, Game game) {
        super(x, y, width, height, xSpeed, ySpeed); // assigns x and y, width and height
        this.game = game;
        enemy = false;
    }

    /**
     * To know if it's power bullet
     *
     * @return power
     */
    public boolean isPower() {
        return power;
    }

    /**
     * To set power bullet
     *
     * @param power
     */
    public void setPower() {
        power = true;
    }

    /**
     * To know if it's enemy bullet
     *
     * @return enemy
     */
    public boolean isEnemy() {
        return enemy;
    }

    /**
     * To set enemy bullet
     *
     * @param enemy
     */
    public void setEnemy() {
        enemy = true;
    }

    /**
     * To update positions of the player for every tick
     */
    @Override
    public void tick() {
        setY(getY() - ySpeed);
    }

    /**
     * To paint the enemy
     *
     * @param g <b>Graphics</b> object to paint the enemy
     */
    @Override
    public void render(Graphics g) {
        //Change Assets based on crashed status.
        g.drawImage(Assets.bullet, getX(), getY(), getWidth(), getHeight(), null);
    }
}
