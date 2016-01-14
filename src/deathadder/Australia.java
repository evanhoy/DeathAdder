/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathadder;

import audio.AudioPlayer;
import environment.Direction;
import environment.Environment;
import grid.Grid;
import images.ResourceTools;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author evanhoy
 */
class Australia extends Environment implements CellDataProviderIntf {

    private Grid grid;
    private Snake slitherin;
    private ArrayList<Barrier> barriers;
    private ArrayList<PickUp> speeds;
    private ArrayList<PickUp> healthPotions;

    public Australia() {
        this.setBackground(ResourceTools.loadImageFromResource("deathadder/forest.JPG"));

        grid = new Grid(66, 33, 20, 20, new Point(25, 25), Color.YELLOW);
        slitherin = new Snake(Direction.LEFT, grid);

        barriers = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            barriers.add(new Barrier(getRandom(grid.getColumns()), getRandom(grid.getRows()), Color.BLACK, this));
        }

        speeds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            speeds.add(new PickUp(getRandom(grid.getColumns()), getRandom(grid.getRows()), Color.YELLOW, this, "SPEED", ResourceTools.loadImageFromResource("deathadder/speed.png")));
        }

        healthPotions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            healthPotions.add(new PickUp(getRandom(grid.getColumns()), getRandom(grid.getRows()), Color.RED, this, "HEALTH", ResourceTools.loadImageFromResource("deathadder/index.png")));
        }

    }

    private int getRandom(int max) {
        return (int) (Math.random() * max);
    }

    @Override
    public void initializeEnvironment() {
    }

    int SLOW = 6;
    int MEDIUM = 4;
    int FAST = 2;
    int BEAST = 1;

    int moveDelay = 0;
    int moveDelayLimit = MEDIUM;

    int healthDelay = 0;
    int healthDelayLimit = 100;

    @Override
    public void timerTaskHandler() {
        if (healthPotions != null) {
            //if counted to limit, then move snake and reset counter
            if (healthDelay >= healthDelayLimit) {
                healthDelay = 0;
                radomizeHealthLocations();
                moveDelayLimit = MEDIUM;
            } else {
                //else keep counting
                healthDelay++;
            }
        }

        if (slitherin != null) {
            //if counted to limit, then move snake and reset counter
            if (moveDelay >= moveDelayLimit) {
                moveDelay = 0;
                slitherin.move();
                checkIntersections();
            } else {
                //else keep counting
                moveDelay++;
            }
        }

    }

    private void radomizeHealthLocations() {
        for (PickUp health : healthPotions) {
            health.setX(grid.getRandomGridLocation().x);
            health.setY(grid.getRandomGridLocation().y);
        }
    }

    public void checkIntersections() {
        if (barriers != null) {
            for (Barrier barrier : barriers) {
                if (barrier.getLocation().equals(slitherin.getHead())) {
                    //System.out.println("HIT");
                    slitherin.addHealth(-10000);
                }
            }
        }

        if (healthPotions != null) {
            for (PickUp healthPickUp : healthPotions) {
                if (healthPickUp.getLocation().equals(slitherin.getHead())) {
                    AudioPlayer.play("/deathadder/coin.wav");
                    slitherin.addHealth(+20);
                }
            }
        }

        if (speeds != null) {
            for (PickUp pickUp : speeds) {
                if (pickUp.getLocation().equals(slitherin.getHead())) {
                    AudioPlayer.play("/deathadder/speed.wav");
                    moveDelayLimit = FAST;
                }
            }
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            slitherin.setDirection(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            slitherin.setDirection(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            slitherin.setDirection(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            slitherin.setDirection(Direction.RIGHT);
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (grid != null) {
            grid.paintComponent(graphics);
        }

        if (slitherin != null) {
            slitherin.draw(graphics);
        }

        if (barriers != null) {
            for (int i = 0; i < barriers.size(); i++) {
                barriers.get(i).draw(graphics);
            }
        }

        if (speeds != null) {
            for (int i = 0; i < speeds.size(); i++) {
                speeds.get(i).draw(graphics);
            }
        }

        if (healthPotions != null) {
            for (int i = 0; i < healthPotions.size(); i++) {
                healthPotions.get(i).draw(graphics);
            }
        }
    }

    @Override
    public int getSystemCoordX(int x, int y) {
        return grid.getCellSystemCoordinate(x, y).x;
    }

    @Override
    public int getSystemCoordY(int x, int y) {
        return grid.getCellSystemCoordinate(x, y).y;
    }

    @Override
    public int getCellWidth() {
        return grid.getCellWidth();
    }

    @Override
    public int getCellHeight() {
        return grid.getCellHeight();
    }

}
