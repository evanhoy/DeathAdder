/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathadder;

import images.ResourceTools;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author evanhoy
 */
public class PickUp extends GridObject {

    public PickUp(int x, int y, CellDataProviderIntf cellData, String type, Image image) {
        super(x, y, cellData, type, Color.BLACK);
        this.image = image;
    }

    @Override
    public void draw(Graphics graphics) {

        int cellX, cellY, cellWidth, cellHeight;
        cellX = getCellData().getSystemCoordX(getX(), getY());
        cellY = getCellData().getSystemCoordY(getX(), getY());
        cellWidth = getCellData().getCellWidth();
        cellHeight = getCellData().getCellHeight();

        graphics.drawImage(image, cellX, cellY, cellWidth, cellHeight, null);

    }

    private Image image;

}
