/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Files {

    public static void loadFile(Game g) {
        BufferedReader br = null;
        FileReader fr = null;
        String line;
        try {
            fr = new FileReader("save.txt");
            br = new BufferedReader(fr);
            line = br.readLine();
            // Separate by commas
            String[] elements = line.split(",");
            // Player
            g.getPlayer().setX(Integer.parseInt(elements[0])); // Set x 
            g.getPlayer().setY(Integer.parseInt(elements[1])); // Set y
            g.getPlayer().setXSpeed(Integer.parseInt(elements[2])); // Set x speed
            g.getPlayer().setYSpeed(Integer.parseInt(elements[3])); // Set y speed
            g.getPlayer().setWidth(Integer.parseInt(elements[4])); // Set width
            // Number of bricks
            line = br.readLine();
            int numberBricks = Integer.parseInt(line);
            // Clean bricks
            g.getBricks().clear();
            // Add every brick saved
            for (int i = 0; i < numberBricks; i++) {
                // Reading
                line = br.readLine();
                elements = line.split(",");
                int x = Integer.parseInt(elements[0]);
                int y = Integer.parseInt(elements[1]);
                int xspeed = Integer.parseInt(elements[2]);
                int yspeed = Integer.parseInt(elements[3]);
                int strength = Integer.parseInt(elements[4]);
                int count = Integer.parseInt(elements[5]);
                int row = Integer.parseInt(elements[6]);
                boolean isfriend = Boolean.parseBoolean(elements[7]);
                Brick brick = new Brick(
                        x, y, // x position, y position
                        40, 40, // width, height
                        xspeed, yspeed, // xspeed, yspeed
                        g);
                brick.setStrength(strength);
                brick.setTickCount(count);
                brick.setRows(row);
                if (isfriend) {
                    brick.setFriend();
                }
                g.getBricks().add(brick);
            }
            // Number of bullets
            line = br.readLine();
            int numberBullets = Integer.parseInt(line);
            // Clean bullets
            g.getBullets().clear();
            // Add every bullet saved
            for (int i = 0; i < numberBullets; i++) {
                // Reading
                line = br.readLine();
                elements = line.split(",");
                int x = Integer.parseInt(elements[0]);
                int y = Integer.parseInt(elements[1]);
                int xspeed = Integer.parseInt(elements[2]);
                int yspeed = Integer.parseInt(elements[3]);
                boolean isenemy = Boolean.parseBoolean(elements[4]);
                boolean ispower = Boolean.parseBoolean(elements[5]);
                int width = Integer.parseInt(elements[6]);
                int height = Integer.parseInt(elements[7]);
                Bullet bullet = new Bullet(
                        x, y, // x position, y position
                        width, height, // width, height
                        xspeed, yspeed, // xspeed, yspeed
                        g);
                if (isenemy) {
                    bullet.setEnemy();
                }
                if (ispower) {
                    bullet.setPower();
                }
                g.getBullets().add(bullet);
            }
            //Boss
            line = br.readLine();
            int bossStatus = Integer.parseInt(line);
            if (bossStatus == 0) {
                g.clearBoss();
            } else {
                line = br.readLine();
                elements = line.split(",");
                int x = Integer.parseInt(elements[0]);
                int y = Integer.parseInt(elements[1]);
                int xspeed = Integer.parseInt(elements[2]);
                int yspeed = Integer.parseInt(elements[3]);
                int direction = Integer.parseInt(elements[4]);
                if (g.getBossAlive()) {

                    g.getBoss().setX(x);
                    g.getBoss().setY(y);
                    g.getBoss().setXSpeed(xspeed);
                    g.getBoss().setYSpeed(yspeed);
                    g.getBoss().setDirection(direction);
                } else {
                    g.loadBoss(x, y, xspeed, yspeed, direction);
                }
                // Reading
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void saveFile(Game g) {
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter("save.txt"));
            // Get player - 1st line
            printWriter.println("" + g.getPlayer().getX() + "," + g.getPlayer().getY()
                    + "," + g.getPlayer().getXSpeed() + "," + g.getPlayer().getYSpeed()
                    + "," + g.getPlayer().getWidth());
            // Get brick count - 2nd line
            printWriter.println("" + g.getBricks().size());
            // Get brick info
            for (Brick brick : g.getBricks()) {
                printWriter.println("" + brick.getX() + "," + brick.getY()
                        + "," + brick.getXSpeed() + "," + brick.getYSpeed()
                        + "," + brick.getStrength() + "," + brick.getTickCount()
                        + "," + brick.getRows()
                        + "," + brick.isFriend());
            }
            // Get bullet count
            printWriter.println("" + g.getBullets().size());
            // Get brick info
            for (Bullet bullet : g.getBullets()) {
                printWriter.println("" + bullet.getX() + "," + bullet.getY()
                        + "," + bullet.getXSpeed() + "," + bullet.getYSpeed()
                        + "," + bullet.isEnemy() + "," + bullet.isPower()
                        + "," + bullet.getWidth() + "," + bullet.getHeight());
            }
            if (g.getBossAlive()) {
                printWriter.println("" + 1);
                printWriter.println("" + g.getBoss().getX() + "," + g.getBoss().getY()
                        + "," + g.getBoss().getXSpeed() + "," + g.getBoss().getYSpeed()
                        + "," + g.getBoss().getDirection());
            } else {
                printWriter.println("" + 0);
            }
            printWriter.close();
        } catch (IOException ioe) {
            //Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ioe);
            System.out.println("No space left on disk" + ioe.toString());
        }

    }
}
