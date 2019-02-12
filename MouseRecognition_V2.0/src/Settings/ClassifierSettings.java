/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import javafx.scene.control.ProgressBar;

/**
 *
 * @author Denes
 */
public class ClassifierSettings {

    // Classifier
    public static final boolean LOAD_MODEL = false;
    // hany egermuvelet utan kell dontest hozni - ezt ne modositsuk
    public static final int NUM_ACTIONS = 1;
    // legtobb hany action-t osztalyozunk
    public static final int NUM_TO_CLASSIFY = 10;

    // Feature Extraction
    public static final int DFL_NUM_FEATURES = 24;
    public static final int AM_NUM_FEATURES = 39;

    // Mouse Data Segmentation
    public static final int NUM_EVENTS = 10;
    public static final int TIME_THRESHOLD = 10000;
    // kiserleti cellal
    public static final boolean USE_ONLY_DRAG_AND_DROP = true;

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    public static final int WHICH_FEATURES = 0;// 0: lehel feature
    //public static final int WHICH_FEATURES =1;//1: teacher feature

    //CSV file header
    public static final String FILE_HEADER = "";

    // Constants for feature extraction
    public static final double CURVATURE_THRESHOLD = 5.0E-4;
    public static double EPS = 1.0E-5;
    //public static final int NUM_FEATURES = 39;

    public static final String RED_BAR = "red-bar";

    public static final String GREEN_BAR = "green-bar";
    public static final String[] barColorStyleClasses = {RED_BAR, GREEN_BAR};

    public static void setBarStyleClass(ProgressBar bar, String barStyleClass) {
        bar.getStyleClass().removeAll(barColorStyleClasses);
        bar.getStyleClass().add(barStyleClass);
    }

}
