/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousedatacollector;

import Settings.ClassifierSettings;
import Settings.DataCollectorSettings;
import Settings.GlobalSettings;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import mouserecognition.DFLDatasetEvent;
import mouserecognition.IEvent;

public class MouseDataCollector implements NativeMouseInputListener, NativeMouseWheelListener {

    long startTime = System.currentTimeMillis();
    private FileWriter file;
    private ArrayList<IEvent> eventlist = new ArrayList<IEvent>();
    private Label actionsnum;

    public Label getActionsnum() {
        return actionsnum;
    }

    public void setActionsnum(Label actionsnum) {
        this.actionsnum = actionsnum;
        this.displaynumberofactions();
    }

    private void displaynumberofactions() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // if you change the UI, do it here !
                MouseDataCollector.this.actionsnum.setText(DataCollectorSettings.numberofactions + " / " + DataCollectorSettings.minimumofmouseactions);
            }
        });
    }

    public void stop() {
        try {
            GlobalScreen.unregisterNativeHook();
            System.out.println(GlobalScreen.isNativeHookRegistered());
            this.finalize();
        } catch (NativeHookException ex) {
            Logger.getLogger(MouseDataCollector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(MouseDataCollector.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setWrite(FileWriter file) {
        this.file = file;
    }

    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        long estimatedTime = System.currentTimeMillis() - startTime;
        try {
            file.append(estimatedTime + "," + "Nobutton,Scrool," + e.getX() + "," + e.getX() + "\n");
            file.flush();
        } catch (IOException ex) {
            Logger.getLogger(MouseDataCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void nativeMouseClicked(NativeMouseEvent e) {
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        IEvent event = new DFLDatasetEvent();
        if (e.getButton() == 1) {
            long estimatedTime = System.currentTimeMillis() - startTime;
            try {
                file.append(estimatedTime + "," + "Left,Pressed," + e.getX() + "," + e.getY() + "\n");
                file.flush();
            } catch (IOException ex) {
                Logger.getLogger(MouseDataCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getButton() == 2) {
            long estimatedTime = System.currentTimeMillis() - startTime;
            try {
                file.append(estimatedTime + "," + "Right,Pressed," + e.getX() + "," + e.getY() + "\n");
                file.flush();
            } catch (IOException ex) {
                Logger.getLogger(MouseDataCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.eventlist.add(event);
        if (this.eventlist.size() > ClassifierSettings.NUM_EVENTS) {
            DataCollectorSettings.numberofactions = DataCollectorSettings.numberofactions + 1;
            this.displaynumberofactions();
            this.eventlist = new ArrayList<>();
        }

    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        IEvent event = new DFLDatasetEvent();
        if (e.getButton() == 1) {
            long estimatedTime = System.currentTimeMillis() - startTime;
            try {
                file.append(estimatedTime + "," + "Left,Released," + e.getX() + "," + e.getY() + "\n");
                file.flush();
            } catch (IOException ex) {
                Logger.getLogger(MouseDataCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getButton() == 2) {
            long estimatedTime = System.currentTimeMillis() - startTime;
            try {
                file.append(estimatedTime + "," + "Right,Released," + e.getX() + "," + e.getY() + "\n");
                file.flush();
            } catch (IOException ex) {
                Logger.getLogger(MouseDataCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.eventlist.add(event);
        if (this.eventlist.size() > ClassifierSettings.NUM_EVENTS) {
            DataCollectorSettings.numberofactions = DataCollectorSettings.numberofactions + 1;
            this.displaynumberofactions();
            this.eventlist = new ArrayList<>();
        }

    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        IEvent event = new DFLDatasetEvent();
        long estimatedTime = System.currentTimeMillis() - startTime;
        try {
            file.append(estimatedTime + "," + "NoButton,Move," + e.getX() + "," + e.getY() + "\n");
            file.flush();
        } catch (IOException ex) {
            Logger.getLogger(MouseDataCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.eventlist.add(event);
        if (this.eventlist.size() > ClassifierSettings.NUM_EVENTS) {
            if (this.eventlist.get(this.eventlist.size() - 1).getTime() - estimatedTime > ClassifierSettings.TIME_THRESHOLD) {
                DataCollectorSettings.numberofactions = DataCollectorSettings.numberofactions + 1;
                this.displaynumberofactions();
                this.eventlist = new ArrayList<>();
            }
        }
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        long estimatedTime = System.currentTimeMillis() - startTime;
        try {
            file.append(estimatedTime + "," + "NoButton,Drag," + e.getX() + "," + e.getY() + "\n");
            file.flush();
        } catch (IOException ex) {
            Logger.getLogger(MouseDataCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//	public static void main(String[] args) {
//            
//                DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
//                Date date = new Date();
//               
//                String s = dateFormat.format(date);
//            try{
//                FileWriter files = new FileWriter(s+".CSV");
//                files.append("client timestamp,button,state,x,y\n");
//            
//               try {
//                GlobalScreen.registerNativeHook();
//                Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
//                logger.setLevel(Level.OFF);
//
//                // Don't forget to disable the parent handlers.
//                logger.setUseParentHandlers(false);
//                }
//                catch (NativeHookException ex) {
//                System.err.println("There was a problem registering the native hook.");
//                System.err.println(ex.getMessage());
//                
//                System.exit(1);
//                }
//                
//                // Construct the example object.
//                MouseDataCollector example = new MouseDataCollector();
//                example.setWrite(files);
//                // Add the appropriate listeners.
//                GlobalScreen.addNativeMouseListener(example);
//                GlobalScreen.addNativeMouseMotionListener(example);
//                GlobalScreen.addNativeMouseWheelListener(example);
//                GlobalScreen.unregisterNativeHook();
//                }
//
//        catch (IOException ex) {
//                Logger.getLogger(MouseDataCollector.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (NativeHookException ex) {
//                Logger.getLogger(MouseDataCollector.class.getName()).log(Level.SEVERE, null, ex);
//            }
//}
}