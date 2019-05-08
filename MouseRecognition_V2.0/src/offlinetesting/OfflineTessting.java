/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package offlinetesting;

import Settings.ClassifierSettings;
import static Settings.ClassifierSettings.NUM_TO_CLASSIFY;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.LinkedBlockingDeque;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import mouserecognition.DFLDatasetEvent;
import mouserecognition.IEvent;
import mouserecognition.IFeature;
import mouserecognition.LehelFeature;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instances;

/**
 *
 * @author Denes
 */
public class OfflineTessting {
    
    String file;
    private RandomForest randomforest;
    private ArrayList<Double> output = new ArrayList<>();
    private ArrayList<Attribute> att = new ArrayList<>();
    private Instances instances;
    private Instances toclassify;
    private int counter = 0;

    private ProgressBar bar;
    private ListView list;
    private Label lab;
    private Queue<IFeature> moves = new LinkedList<IFeature>();

    private Queue<Double> probabilities = new LinkedBlockingDeque<>(NUM_TO_CLASSIFY);
    
    public OfflineTessting(String file ){
        this.file = file;
    }

    public Queue<IFeature> getMoves() {
        splitmoves();
        return moves;
    }

    public void setMoves(Queue<IFeature> moves) {
        this.moves = moves;
    }
    
    private  void splitmoves (){
             Scanner sc = null;
        try {
            sc = new Scanner(new File(this.file));
        } catch (FileNotFoundException ex) {
            System.out.println("Allomany megnyitasi hiba!!");
            System.exit(1);
        }
        int counter =0;
        LehelFeature move = new LehelFeature();
        sc.nextLine();
        while ((sc.hasNextLine()) && (counter<50)) {
            String line = sc.nextLine();
            IEvent e = new DFLDatasetEvent();
            StringTokenizer stk = new StringTokenizer(line, ",");
            while (stk.hasMoreTokens()) {
                e.setTime(Long.parseLong(stk.nextToken()));
                e.setButtonype(stk.nextToken());
                e.setActiontype(stk.nextToken());
                e.setX(Integer.parseInt(stk.nextToken()));
                e.setY(Integer.parseInt(stk.nextToken()));
            }
            if (move.getEvents().size() > ClassifierSettings.NUM_EVENTS){
                if(e.getActiontype().toString().equalsIgnoreCase("Released")){
                    move.getEvents().add(e);
                    move.ExtractFeatures(move.getEvents());
                    this.moves.add(move);
                    move = new LehelFeature();
                    counter = counter + 1;
                }
                if(e.getActiontype().toString().equalsIgnoreCase("Pressed")){
                    move.getEvents().add(e);
                    move.ExtractFeatures(move.getEvents());
                    this.moves.add(move);
                    move = new LehelFeature();
                    counter = counter + 1;
                }
                if (move.getEvents().isEmpty() == false){
                if( (move.getEvents().get(move.getEvents().size()-1).getTime() - e.getTime()  > ClassifierSettings.TIME_THRESHOLD)){
                    move.getEvents().add(e);
                    move.ExtractFeatures(move.getEvents());
                    this.moves.add(move);
                    move = new LehelFeature();
                    counter = counter + 1;
                }
                }
                move.getEvents().add(e);
            }
            else{
                move.getEvents().add(e);
            }
        }
        System.out.println(counter);
    }

    
}
