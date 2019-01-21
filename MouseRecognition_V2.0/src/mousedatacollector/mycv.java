/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousedatacollector;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Denes
 */
public class mycv implements MouseListener{

    @Override
    public void mouseClicked(MouseEvent e) {
    System.out.println("c"+e.getLocationOnScreen());
    }

    @Override
    public void mousePressed(MouseEvent e) {
  System.out.println("p"+e.getLocationOnScreen());}

    @Override
    public void mouseReleased(MouseEvent e) {
System.out.println("r"+e.getLocationOnScreen());}

    @Override
    public void mouseEntered(MouseEvent e) {
  System.out.println("e"+e.getLocationOnScreen());}

    @Override
    public void mouseExited(MouseEvent e) {
 System.out.println("e"+e.getLocationOnScreen());}
    
}
