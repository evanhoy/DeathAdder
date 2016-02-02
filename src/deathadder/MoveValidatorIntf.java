/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathadder;

import java.awt.Point;

/**
 *
 * @author evanhoy
 */
public interface MoveValidatorIntf {
    public Point validate(Point proposedLocation);
}
