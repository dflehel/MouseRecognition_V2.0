package mouserecognition;

import Settings.ClassifierSettings;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Denes
 */
public class DataCollector implements NativeMouseInputListener, NativeMouseWheelListener {

    private Queue<ArrayList<IEvent>> mouseActions = new LinkedList<ArrayList<IEvent>>();
    private ArrayList<IEvent> eventlist = new ArrayList<IEvent>();
    private Thread t;

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    public DataCollector() {
    }

    public Queue<ArrayList<IEvent>> getMouseActions() {
        return mouseActions;
    }

    public void setEvents(Queue<ArrayList<IEvent>> events) {
        this.mouseActions = events;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    private long startTime = System.currentTimeMillis();

    @Override
    public void nativeMouseClicked(NativeMouseEvent nme) {
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nme) {
        long estimatedTime = System.currentTimeMillis() - startTime;
        IEvent event = new DFLDatasetEvent();
        event.setTime(estimatedTime);
        //event.setTime(nme.getWhen());
        event.setX(nme.getX());
        event.setY(nme.getY());
        if (nme.getButton() == 1) {
            event.setButtonype("Left");
        }
        if (nme.getButton() == 2) {
            event.setButtonype("Right");
        }
        event.setActiontype("Pressed");
        this.eventlist.add(event);

        if (this.eventlist.size() > ClassifierSettings.NUM_EVENTS) {
            this.mouseActions.add(this.eventlist);
            synchronized (this.mouseActions) {
                this.mouseActions.notify();
            }
            this.eventlist = new ArrayList<>();
        }

    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nme) {
        long estimatedTime = System.currentTimeMillis() - startTime;
        IEvent event = new DFLDatasetEvent();
        event.setTime(estimatedTime);
        //event.setTime(nme.getWhen());
        event.setX(nme.getX());
        event.setY(nme.getY());
        if (nme.getButton() == 1) {
            event.setButtonype("Left");
        }
        if (nme.getButton() == 2) {
            event.setButtonype("Right");
        }

        event.setActiontype("Released");
        this.eventlist.add(event);

        if (this.eventlist.size() > ClassifierSettings.NUM_EVENTS) {
            this.mouseActions.add(this.eventlist);
            synchronized (this.mouseActions) {
                this.mouseActions.notify();
            }

        }
        this.eventlist = new ArrayList<>();
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nme) {
        long estimatedTime = System.currentTimeMillis() - startTime;
        IEvent event = new DFLDatasetEvent();
        event.setTime(estimatedTime);
        //event.setTime(nme.getWhen());
        event.setX(nme.getX());
        event.setY(nme.getY());
        event.setButtonype("NoButton");
        event.setActiontype("Moved");
        //***
        if (this.eventlist.size() > ClassifierSettings.NUM_EVENTS) {
            if (this.eventlist.get(this.eventlist.size() - 1).getTime() - event.getTime() > ClassifierSettings.TIME_THRESHOLD) {
                if (this.mouseActions.isEmpty()) {
                    this.mouseActions.add(this.eventlist);
                    synchronized (this.mouseActions) {
                        this.mouseActions.notify();
                    }
                } else {
                    synchronized (this.mouseActions) {
                        this.mouseActions.add(this.eventlist);
                    }
                }
                this.eventlist = new ArrayList<>();
            }
        }
        this.eventlist.add(event);

    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nme) {
        long estimatedTime = System.currentTimeMillis() - startTime;
        IEvent event = new DFLDatasetEvent();
        event.setTime(estimatedTime);
        //event.setTime(nme.getWhen());
        event.setX(nme.getX());
        event.setY(nme.getY());
        event.setButtonype("NoButton");
        event.setActiontype("Drag");
        this.eventlist.add(event);

    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent nmwe) {
        //long estimatedTime = System.currentTimeMillis() - startTime;
    }

}