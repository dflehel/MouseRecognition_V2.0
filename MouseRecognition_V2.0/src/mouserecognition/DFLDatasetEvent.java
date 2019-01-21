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
public class DFLDatasetEvent implements IEvent {

    private long time;
    private String buttonype;
    private String actiontype;
    private int x;
    private int y;

    public DFLDatasetEvent(long time, String buttonype, String actiontype, int x, int y) {
        this.time = time;
        this.buttonype = buttonype;
        this.actiontype = actiontype;
        this.x = x;
        this.y = y;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setButtonype(String buttonype) {
        this.buttonype = buttonype;
    }

    public void setActiontype(String actiontype) {
        this.actiontype = actiontype;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public DFLDatasetEvent() {
    }

    public long getTime() {
        return time;
    }

    public String getButtonype() {
        return buttonype;
    }

    public String getActiontype() {
        return actiontype;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
