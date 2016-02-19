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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author evanhoy
 */
class Australia extends Environment implements CellDataProviderIntf, MoveValidatorIntf {

    private Grid grid;
    private Snake slitherin;
    private ArrayList<Barrier> barriers;
    private ArrayList<PickUp> speeds;
    private ArrayList<PickUp> healthPotions;
    private GameState state;
    private MySoundManager soundManager;
    private int score;

    public Australia() {
        this.setBackground(Color.GREEN);

        setState(GameState.MENU);;

        grid = new Grid(60, 27, 22, 22, new Point(25, 25), Color.GREEN);

        slitherin = new Snake(Direction.RIGHT, Color.BLUE, grid, this);
        barriers = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            barriers.add(new Barrier(getRandom(grid.getColumns()), getRandom(grid.getRows()), Color.BLACK, this, "BARRIER"));
            {
                if (barriers.get(i).getLocation().equals(slitherin.getHead())) {
                    barriers.add(new Barrier(getRandom(grid.getColumns()), getRandom(grid.getRows()), Color.BLACK, this, "BARRIER"));
                }
            }
        }

        speeds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            speeds.add(new PickUp(getRandom(grid.getColumns()), getRandom(grid.getRows()), this, "SPEED", ResourceTools.loadImageFromResource("deathadder/speed.png")));
            {
                if (speeds.get(i).getLocation().equals(barriers.get(i).getLocation())) {
                    speeds.add(new PickUp(getRandom(grid.getColumns()), getRandom(grid.getRows()), this, "SPEED", ResourceTools.loadImageFromResource("deathadder/speed.png")));
                }
            }
        }

        healthPotions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            healthPotions.add(new PickUp(getRandom(grid.getColumns()), getRandom(grid.getRows()), this, "HEALTH", ResourceTools.loadImageFromResource("deathadder/index.png")));
            {

                if (healthPotions.get(i).getLocation().equals(barriers.get(i).getLocation())) {
                    healthPotions.add(new PickUp(getRandom(grid.getColumns()), getRandom(grid.getRows()), this, "SPEED", ResourceTools.loadImageFromResource("deathadder/speed.png")));
                }
            }
        }
        soundManager = MySoundManager.getSoundManager();
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
        if (getState() == GameState.GAME) {

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
                setScore(getScore() + 1);

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
                    slitherin.addHealth(-10000);
                    setState(GameState.END);
                }
            }
        }

        if (healthPotions != null) {
            for (PickUp healthPickUp : healthPotions) {
                if (healthPickUp.getLocation().equals(slitherin.getHead())) {
                    AudioPlayer.play("/deathadder/health.wav");
                    slitherin.addHealth(+20);
                    slitherin.addGrowthCounter(1);
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
    public void keyPressedHandler(KeyEvent e
    ) {

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            slitherin.setDirection(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            slitherin.setDirection(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            slitherin.setDirection(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            slitherin.setDirection(Direction.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            if (getState() == GameState.GAME) {
                setState(GameState.PAUSE);
            } else if (getState() == GameState.PAUSE) {
                setState(GameState.GAME);
            }
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e
    ) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e
    ) {
        if (getState() == GameState.MENU) {
            if (new Rectangle(505, 310, 300, 90).contains(e.getPoint())) {

            } else {
            }
            setState(GameState.GAME);

        }
        if (getState() == GameState.END) {
            if (new Rectangle(505, 310, 300, 90).contains(e.getPoint())) {

            } else {
            }
            setState(GameState.MENU);
        }
    }

    @Override
    public void paintEnvironment(Graphics graphics
    ) {
        if ((getState() == GameState.PAUSE) || (getState() == GameState.GAME)) {

            if (grid != null) {
                grid.paintComponent(graphics);
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

            if (slitherin != null) {
                slitherin.draw(graphics);
            }

            if (getState() == GameState.GAME) {
                Font fnt0 = new Font("help", Font.BOLD, 25);
                graphics.setFont(fnt0);
                graphics.setColor(Color.YELLOW);
                graphics.drawString("P = PAUSE", 1215, 650);

                Font fnt1 = new Font("help", Font.BOLD, 25);
                graphics.setFont(fnt1);
                graphics.setColor(Color.YELLOW);
                graphics.drawString("ARROWS = DIRECTION", 1070, 680);

                Font fnt2 = new Font("score", Font.ITALIC, 60);
                graphics.setFont(fnt2);
                graphics.setColor(Color.RED);
                graphics.drawString(" SCORE: " + getScore(), 5, 675);
            }
            if (getState() == GameState.PAUSE) {
                Font fnt0 = new Font("arial", Font.ITALIC, 70);
                graphics.setFont(fnt0);
                graphics.setColor(Color.WHITE);
                graphics.drawString("PAUSE", 540, 380);
            }
        } else if (getState() == GameState.MENU) {

            graphics.draw3DRect(505, 310, 300, 90, true);
            Font fnt0 = new Font("arial", Font.BOLD, 70);
            graphics.setFont(fnt0);
            graphics.setColor(Color.BLACK);

            graphics.drawString("START", 540, 380);

        }
        if (getState() == GameState.END) {
            Font fnt0 = new Font("help", Font.BOLD, 70);
            graphics.setFont(fnt0);
            graphics.setColor(Color.GREEN);
            graphics.drawString("GAME OVER", 480, 380);

            Font fnt1 = new Font("help", Font.BOLD, 20);
            graphics.setFont(fnt1);
            graphics.setColor(Color.WHITE);
            graphics.drawString("Click for MENU", 480, 420);

            this.setBackground(Color.BLACK);
        }
    }

    @Override
    public int getSystemCoordX(int x, int y
    ) {
        return grid.getCellSystemCoordinate(x, y).x;
    }

    @Override
    public int getSystemCoordY(int x, int y
    ) {
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

//<editor-fold defaultstate="collapsed" desc="MoveValidatorIntf">
    @Override
    public Point validate(Point proposedLocation
    ) {
        //assess and adjust proposedLocation
        // if snake.x less than zero, then kill him, stop teh damn game, and mock the player for being weak
        if (proposedLocation.x == 0) {
            slitherin.setDirection(Direction.RIGHT);
        } else if (proposedLocation.x >= grid.getColumns()) {
            slitherin.setDirection(Direction.LEFT);
        }
        if (proposedLocation.y == 0) {
            slitherin.setDirection(Direction.DOWN);
        } else if (proposedLocation.y >= grid.getRows()) {
            slitherin.setDirection(Direction.UP);
        }

        return proposedLocation;
    }
//</editor-fold>

    /**
     * @return the state
     */
    public GameState getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(GameState state) {
        this.state = state;

        if (state == GameState.MENU) {
            this.setBackground(Color.GREEN);
            AudioPlayer.play("/deathadder/Dankstorm.wav");
        } else if (state == GameState.PAUSE) {
            this.setBackground(ResourceTools.loadImageFromResource("deathadder/Desert.JPG"));
        } else if (state == GameState.END) {
            this.setBackground(Color.BLACK);
        } else if (state == GameState.GAME) {
            this.setBackground(ResourceTools.loadImageFromResource("deathadder/Desert.JPG"));
        }
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }
}
