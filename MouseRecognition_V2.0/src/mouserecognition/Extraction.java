/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouserecognition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import static mouserecognition.Settings.NUM_ACTIONS;

/**
 *
 * @author Denes
 */
public class Extraction {

    private Queue<ArrayList<IEvent>> eventslist = new LinkedList<ArrayList<IEvent>>();
    //itt megvan csinalva hogy apalajaban minden csak IFeatures interface tipusu osztalyokat fogad
    private Queue<IFeature> moves = new LinkedList<IFeature>();
    private ArrayList<IEvent> events = new ArrayList<>();
    private String csvfile;
    private IClassifier classifier;

    public Extraction(IClassifier classifier) {
        this.classifier = classifier;
    }

    public IClassifier getClassifier() {
        return classifier;
    }

    public void setClassifier(IClassifier classifier) {
        this.classifier = classifier;
    }


    
    
    
    

    public Queue<ArrayList<IEvent>> getEventslist() {
        return eventslist;
    }

    public void setEventslist(Queue<ArrayList<IEvent>> eventslist) {
        this.eventslist = eventslist;
    }


    public Queue<IFeature> getMoves() {
        return moves;
    }

    public void setMoves(Queue<IFeature> moves) {
        this.moves = moves;
    }

    public void featuresextraction() {
        while (true) {
            if (this.eventslist.isEmpty()) {
                try {
                    synchronized (this.eventslist) {
                        this.eventslist.wait();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Extraction.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                synchronized (this.eventslist) {
                    this.events = this.eventslist.poll();
                }
                //A csere eseten a megfelelo osztalyt kell peldanyositani
                IFeature feature = null;
                if (Settings.WHICH_FEATURES == 0) {
                    feature = new LehellFeature();
                } else {

                    feature = new TeacherFeature();
                }
                //Annak meg kell hivni az extract featuret
                feature.ExtractFeatures(events);
                this.moves.add(feature);
                this.classifier.classify(moves);
                /*synchronized (this.moves) {
                    //majd a hjozza adas
                    this.moves.add(feature);
                }
                if (this.moves.size() == NUM_ACTIONS) {
                    synchronized (this.moves) {
                        this.moves.notify();
                    }
                }*/
                
            }
        }

    }

    public ArrayList<IEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<IEvent> events) {
        this.events = events;
    }

    public String getCsvfile() {
        return csvfile;
    }

    public void setCsvfile(String csvfile) {
        this.csvfile = csvfile;
    }

    public Extraction(String csvfile) {
        this.csvfile = csvfile;
    }

    public Extraction() {
    }

}
