/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathadder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author evanhoy
 */
public class Barrier extends GridObject {

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(getColor());
        graphics.fill3DRect(getCellData().getSystemCoordX(getX(), getY()),
                getCellData().getSystemCoordY(getX(), getY()),
                getCellData().getCellWidth(),
                getCellData().getCellHeight(),
                true);
    }

    public Barrier(int x, int y, Color color, CellDataProviderIntf cellData, String type) {
        super(x, y, cellData, type, color);
    }
}
