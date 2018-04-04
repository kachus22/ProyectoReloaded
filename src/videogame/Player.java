/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Item {

    private Game game;              // runs game
    private boolean power;          // to control power status
    private boolean powerSize;      // to control size status
    private boolean powerSpeed;     // to control speed status
    private int tickSize;           // to keep count of tick size power
    private int tickSpeed;          // to keep count of tick speed power
    private int widthOriginal;      // to store original width value
    private int heightOriginal;     // to store original height value
    private int xSpeedOriginal;     // to store original x speed value
    private int ySpeedOriginal;     // to store original y speed value

    public Player(int x, int y, int width, int height, int xSpeed, int ySpeed, Game game) {
        super(x, y, width, height, xSpeed, ySpeed); // assigns x and y, width and height
        this.game = game;
        power = false;
        this.tickSize = 0;
        this.tickSpeed = 0;
        widthOriginal = width;
        heightOriginal = height;
        xSpeedOriginal = xSpeed;
        ySpeedOriginal = ySpeed;
    }

    /**
     * To restart values to the first ones
     *
     * @param n
     */
    public void restartStats(int n) {
        if (n == 1) {
            width = widthOriginal;
        } else {
            xSpeed = xSpeedOriginal;
            ySpeed = ySpeedOriginal;
        }
    }

    /**
     * Get tickSize value
     *
     * @return tickSize
     */
    public int getTickSize() {
        return tickSize;
    }

    /**
     * Get tickSize value
     *
     * @return tickSize
     */
    public int getTickSpeed() {
        return tickSpeed;
    }

    /**
     * Set tickSize value
     *
     * @param tickSize
     */
    public void setTickSize(int t) {
        tickSize = t;
    }

    /**
     * Set tickSpeed value
     *
     * @param tickSpeed
     */
    public void setTickSpeed(int t) {
        tickSpeed = t;
    }

    /**
     * Get power value
     *
     * @return power
     */
    public boolean getPower() {
        return power;
    }

    /**
     * Set power value
     *
     * @param power
     */
    public void setPower(boolean b) {
        power = b;
    }

    /**
     * Get powerSize value
     *
     * @return powerSize
     */
    public boolean getPowerSize() {
        return powerSize;
    }

    /**
     * Set powerSpeed value
     *
     * @return powerSpeed
     */
    public boolean getPowerSpeed() {
        return powerSpeed;
    }

    /**
     * Set powerSize value
     *
     * @param powerSize
     */
    public void setPowerSize(boolean b) {
        powerSize = b;
    }

    /**
     * Set powerSpeed value
     *
     * @param powerSpeed
     */
    public void setPowerSpeed(boolean b) {
        powerSpeed = b;
    }

    /**
     * Get widthOriginal value
     *
     * @return widthOriginal
     */
    public int getWidthOriginal() {
        return widthOriginal;
    }

    /**
     * Get xSpeedOriginal value
     *
     * @return xSpeedOriginal
     */
    public int getXSpeedOriginal() {
        return xSpeedOriginal;
    }

    @Override
    public void tick() {
        if (power) {
            if (powerSize) {
                tickSize++;
                if (tickSize > 300) {
                    tickSize = 0;
                    powerSize = false;
                    restartStats(1);
                }
            }
            if (powerSpeed) {
                tickSpeed++;
                if (tickSpeed > 300) {
                    tickSpeed = 0;
                    powerSpeed = false;
                    restartStats(1);
                }
            }
        }
        //Movement
        if (game.getKeyManager().getLeft()) {
            setX(getX() - xSpeed);
        }
        if (game.getKeyManager().getRight()) {
            setX(getX() + xSpeed);
        }

        // reset x position and y position if colision
        if (getX() + getWidth() >= game.getWidth()) {
            setX(game.getWidth() - getWidth());
        } else if (getX() <= 0) {
            setX(0);
        }
        if (getY() + getHeight() >= game.getHeight()) {
            setY(game.getHeight() - getHeight());
        } else if (getY() <= 1) {
            setY(1);
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), 20);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);

    }

}
