/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouserecognition;

/**
 *
 * @author Denes
 */
public interface IEvent {

    public void setTime(long time);

    public void setButtonype(String buttonype);

    public void setActiontype(String actiontype);

    public void setX(int x);

    public void setY(int y);

    public long getTime();

    public String getButtonype();

    public String getActiontype();

    public int getX();

    public int getY();

}
