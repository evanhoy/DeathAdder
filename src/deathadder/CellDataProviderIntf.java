/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathadder;

/**
 *
 * @author evanhoy
 */
public interface CellDataProviderIntf {

    public int getCellWidth();

    public int getCellHeight();

    public int getSystemCoordX(int x, int y);

    public int getSystemCoordY(int x, int y);
}
