/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathadder;

import environment.Direction;
import environment.Environment;
import grid.Grid;
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
    private ArrayList<PickUp> pickUps;

    public Australia() {
        this.setBackground(Color.GREEN);

        grid = new Grid(135, 75, 10, 10, new Point(25, 25), new Color(46, 139, 87, 128));
        slitherin = new Snake(Direction.LEFT, grid);
        
        barriers = new ArrayList<>();
        for (int i = 0; i < 90; i++) {
            barriers.add(new Barrier(getRandom(grid.getColumns()), getRandom(grid.getRows()), Color.BLACK, this));
        }

        pickUps = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            pickUps.add(new PickUp(getRandom(grid.getColumns()), getRandom(grid.getRows()), Color.RED, this));
        }

    }

    private int getRandom(int max) {
        return (int) (Math.random() * max);
    }

    @Override
    public void initializeEnvironment() {
    }

    int counter;
    int moveDelay = 0;
    int moveDelayLimit = 4;

    @Override
    public void timerTaskHandler() {
        if (slitherin != null) {
            //if counted to limit, then move snake and reset counter
            if (moveDelay >= moveDelayLimit) {
                moveDelay = 0;
                slitherin.move();
            } else {
                //else keep counting
                moveDelay++;
            }
            checkIntersections();

        }
    }

    public void checkIntersections() {
//check if the snake's head is inside a wall
        for (Barrier barrier : barriers) {
            if (barrier.getLocation().equals(slitherin.getHead())) {
                System.out.println("HIT ");
                slitherin.addHealth(-10000);
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
        if (pickUps!= null) {
            for (int i = 0; i < pickUps.size(); i++) {
                pickUps.get(i).draw(graphics);

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
