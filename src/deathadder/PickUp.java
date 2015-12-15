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

    public PickUp(int x, int y, Color color, CellDataProviderIntf cellData, String type, Image image) {
        super(x, y, color, cellData, type);
        this.image = image;

    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(getColor());

        graphics.drawImage(image, getCellData().getSystemCoordX(getX(), getY()),
                getCellData().getSystemCoordY(getX(), getY()),
                getCellData().getCellWidth(),
                getCellData().getCellHeight(), null);

    }

    private Image image;

}
