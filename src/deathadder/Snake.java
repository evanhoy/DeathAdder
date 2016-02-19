
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

    /**
     * @return the HEAD_POSITION
     */
    public static int getHEAD_POSITION() {
        return HEAD_POSITION;
    }

    /**
     * @param aHEAD_POSITION the HEAD_POSITION to set
     */
    public static void setHEAD_POSITION(int aHEAD_POSITION) {
        HEAD_POSITION = aHEAD_POSITION;
    }
//doesn't respect grid boundaries
//doesn't delete tail when moving
    //doesn't move too fast
    //doesn't respect self collision

    public Snake(Direction direction, Color color, Grid grid, MoveValidatorIntf moveValidator) {
        this.direction = direction;
        this.grid = grid;
        this.color = color;

        //create the snake body
        body = new ArrayList<>();
        body.add(new Point(20, 10));
        body.add(new Point(20, 11));
        body.add(new Point(20, 12));

        this.moveValidator = moveValidator;
    }

    public void draw(Graphics graphics) {

        graphics.setColor(getBodyColor());

        for (int i = 0; i < getBody().size(); i++) {
            graphics.fillOval(getGrid().getCellSystemCoordinate(getBody().get(i)).x,
                    getGrid().getCellSystemCoordinate(getBody().get(i)).y,
                    getGrid().getCellWidth(),
                    getGrid().getCellHeight());
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
            getBody().add(getHEAD_POSITION(), moveValidator.validate(newHead));

            //delete the tail
            if (growthCounter > 0) {
                growthCounter--;
            } else {
                getBody().remove(getBody().size() - 1);
            }

        }
    }

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private static int HEAD_POSITION = 0;

    private Direction direction = Direction.RIGHT;
    private ArrayList<Point> body;
    private Grid grid;
    private Color bodyColor = Color.BLUE;

    private int health = 100;
    private int growthCounter;
    private final Color color;
    private final MoveValidatorIntf moveValidator;

    public Point getHead() {
        return getBody().get(getHEAD_POSITION());

    }

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
        this.setHealth(this.getHealth() + health);
    }

    public boolean isAlive() {
        return (getHealth() > 0);

    }

    /**
     * @return the body
     */
    public ArrayList<Point> getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(ArrayList<Point> body) {
        this.body = body;
    }

    /**
     * @return the grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * @param grid the grid to set
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /**
     * @return the bodyColor
     */
    public Color getBodyColor() {
        return bodyColor;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return the growthCounter
     */
    public int getGrowthCounter() {
        return growthCounter;
    }

    /**
     * @param growth the growth to add to the snake
     */
    public void addGrowthCounter(int growth) {
        this.growthCounter += growth;
    }
    //</editor-fold>
}
