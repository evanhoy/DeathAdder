/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathadder;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author evanhoy
 */
public class PickUp extends GridObject {

    public PickUp(int x, int y, Color color, CellDataProviderIntf cellData) {
        super(x, y, color, cellData);
    }
    @Override
    public void draw(Graphics graphics) {
   //     graphics.setColor(getColor());
        graphics.setColor(Color.PINK);
        graphics.fill3DRect(getCellData().getSystemCoordX(getX(), getY()),
                getCellData().getSystemCoordY(getX(), getY()),
                getCellData().getCellWidth(),
                getCellData().getCellHeight(),
                true);
    }
    
}
