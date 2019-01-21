/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouserecognition;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 *
 * @author Denes
 */
public class MouseRecognition {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        // TODO code application logic here
////         try {
////                GlobalScreen.registerNativeHook();
////                Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
////                }
////                catch (NativeHookException ex) {
////                System.err.println("There was a problem registering the native hook.");
////                System.err.println(ex.getMessage());
////                
////                System.exit(1);
////                }
////                
////                DataCollector datacollector = new DataCollector();
////                GlobalScreen.addNativeMouseListener(datacollector);
////                GlobalScreen.addNativeMouseMotionListener(datacollector);
////                GlobalScreen.addNativeMouseWheelListener(datacollector);
//        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
//        Date date = new Date();
//
//        String ss = dateFormat.format(date);
//        Queue<ArrayList<IEvent>> events = new LinkedList<ArrayList<IEvent>>();
//        FileWriter fileWriter;
//        Display d;
//        d = new Display();
//        d.setVisible(true);
//        try {
//            fileWriter = new FileWriter("kimenet_" + ss + ".csv");
//            fileWriter.append(Settings.FILE_HEADER.toString());
//            Queue<IFeature> moves = new LinkedList<IFeature>();
//            
//          /*  Thread classificationThread = new Thread("Classifier") {
//                public void run() {
//                    System.out.println("run by: " + getName());
//                    IClassifier classifier = new DFLRandomForestClassifier(moves, fileWriter, d);
//                      //   classifier.setFile(fileWriter);
//                     //    classifier.setMoves(moves);
//                     //   classifier.setDisplay(d);
//                    classifier.classify();
//                }
//            };*/
//
//            Thread featureExtractionThread = new Thread("Feature extractor") {
//                public void run() {
//                    System.out.println("run by: " + getName());
//                    Extraction extraction = new Extraction();
//                    extraction.setEventslist(events);
//                    extraction.setMoves(moves);
//                    IClassifier classifier = new DFLRandomForestClassifier(moves, fileWriter, d);
//                   // classifier.setFile(fileWriter);
//                  //  classifier.setMoves(moves);
//                  //  classifier.setDisplay(d);
//                    extraction.setClassifier(classifier);
//                    extraction.featuresextraction();
//                }
//            };
//
//            Thread dataCollectorThread = new Thread("Data collector") {
//                public void run() {
//                    try {
//                        GlobalScreen.registerNativeHook();
//                        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
//                        logger.setLevel(Level.OFF);
//
//                        // Don't forget to disable the parent handlers.
//                        logger.setUseParentHandlers(false);
//                    } catch (NativeHookException ex) {
//                        System.err.println("There was a problem registering the native hook.");
//                        System.err.println(ex.getMessage());
//                        System.exit(1);
//                    }
//
//                    DataCollector datacollector = new DataCollector();
//                    datacollector.setEvents(events);
//                    datacollector.setT(featureExtractionThread);
//                    GlobalScreen.addNativeMouseListener(datacollector);
//                    GlobalScreen.addNativeMouseMotionListener(datacollector);
//                    GlobalScreen.addNativeMouseWheelListener(datacollector);
//                    System.out.println("run by: " + getName());
//                }
//            };
//        //    classificationThread.start();
//            dataCollectorThread.start();
//            featureExtractionThread.start();
//        //    classificationThread.join();
//            dataCollectorThread.join();
//            featureExtractionThread.join();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(MouseRecognition.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(MouseRecognition.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

}
