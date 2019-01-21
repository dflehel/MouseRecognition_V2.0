/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import java.util.ArrayList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Denes
 */
public class GlobalSettings {
    public static boolean mousetestisstarted = false;
    public static boolean mousecollectingtarted = false;
    public static ArrayList<Integer> probs = new ArrayList<>();
    public static boolean updating = false;
    public static XYChart.Series<String, Number> series = new XYChart.Series<>();
     public static boolean updatingsignature = false;
    public static XYChart.Series<String, Number> seriessignature = new XYChart.Series<>();
}
