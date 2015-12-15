/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathadder;

import environment.Direction;
import grid.Grid;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author evanhoy
 */
public class Snake {
//doesn't respect grid boundaries
//doesn't delete tail when moving
    //doesn't move too fast
    //doesn't respect self collision

    public Snake(Direction direction, Grid grid) {
        this.direction = direction;
        this.grid = grid;

        //create the snake body
        body = new ArrayList<>();
        body.add(new Point(5, 5));
        body.add(new Point(5, 4));
        body.add(new Point(5, 3));
    }

    public void draw(Graphics graphics) {

        graphics.setColor(bodyColor);

        for (int i = 0; i < body.size(); i++) {
            graphics.fillOval(grid.getCellSystemCoordinate(body.get(i)).x,
                    grid.getCellSystemCoordinate(body.get(i)).y,
                    grid.getCellWidth(),
                    grid.getCellHeight());
        }
    }

    public void move() {
        if (isAlive()) {

//make a copy of the current head location
            Point newHead = new Point(getHead());
            if (getDirection() == Direction.LEFT) {
                newHead.x--;
            } else if (getDirection() == Direction.RIGHT) {
                newHead.x++;
            } else if (getDirection() == Direction.DOWN) {
                newHead.y++;
            } else if (getDirection() == Direction.UP) {
                newHead.y--;
            }
//add new Head
            body.add(HEAD_POSITION, newHead);

            //delete the tail
            body.remove(body.size() - 1);

        }
    }
    private static final int HEAD_POSITION = 0;

    public Point getHead() {
        return body.get(HEAD_POSITION);

    }

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private Direction direction = Direction.LEFT;
    private ArrayList<Point> body;
    private Grid grid;
    private Color bodyColor = Color.RED;

    private int health = 100;

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @param bodyColor the bodyColor to set
     */
    public void setBodyColor(Color bodyColor) {
        this.bodyColor = bodyColor;
    }
    //</editor-fold>

    /**
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void addHealth(int health) {
        this.health += health;
    }

    public boolean isAlive() {
        return (health > 0);

    }

}
