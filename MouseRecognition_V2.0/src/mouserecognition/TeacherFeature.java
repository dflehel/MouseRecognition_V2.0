/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouserecognition;

import Settings.ClassifierSettings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Settings.ClassifierSettings.AM_NUM_FEATURES;
import weka.core.DenseInstance;
import weka.core.Instance;

/**
 *
 * @author manyi
 */
public class TeacherFeature implements IFeature {

    public static final String TEST_FILE = "test_user_1.arff";
    public static PrintStream ps;

    public static void printHeader() {
        try {
            ps = new PrintStream(TEST_FILE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TeacherFeature.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("header.arff"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TeacherFeature.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ps.println(line);
        }

    }

    static {
        printHeader();
    }

    @Override
    public void ExtractFeatures(ArrayList<IEvent> events) {
        this.events = cleanEvents(events);
        events = this.events;
        this.setAction_type(actionType());

        // pointwise velocoties
        int n = events.size();
        double v[] = new double[n - 1];
        double path[] = new double[n - 1];
        double vx[] = new double[n - 1];
        double vy[] = new double[n - 1];
        double t[] = new double[n - 1];
        double angles[] = new double[n - 1];

        double sum_of_angles = 0;
        double maxv = 0, maxvx = 0, maxvy = 0;
        double min_notnull_v = Integer.MAX_VALUE, min_notnull_vx = Integer.MAX_VALUE, min_notnull_vy = Integer.MAX_VALUE;
        double avgv = 0, avgvx = 0, avgvy = 0;
        for (int i = 0; i < n - 1; ++i) {
            double dx = Math.abs(events.get(i + 1).getX() - events.get(i).getX());
            double dy = Math.abs(events.get(i + 1).getY() - events.get(i).getY());
            double dt = events.get(i + 1).getTime() - events.get(i).getTime();;
            double d = Math.sqrt(dx * dx + dy * dy);
            path[i] = d;
            v[i] = d / dt;
            vx[i] = dx / dt;
            vy[i] = dy / dt;
            t[i] = dt;

            double angle = Math.atan2(dy, dx);
            angles[i] = angle;
            sum_of_angles += angle;

            avgv += v[i];
            avgvx += vx[i];
            avgvy += vy[i];

            if (v[i] > maxv) {
                maxv = v[i];
            }
            if (v[i] != 0 && v[i] < min_notnull_v) {
                min_notnull_v = v[i];
            }
            if (vx[i] > maxvx) {
                maxvx = vx[i];
            }
            if (vx[i] != 0 && vx[i] < min_notnull_vx) {
                min_notnull_vx = vx[i];
            }
            if (vy[i] > maxvy) {
                maxvy = vy[i];
            }
            if (vy[i] != 0 && vy[i] < min_notnull_vy) {
                min_notnull_vy = vy[i];
            }

        }
        avgv /= (n - 1);
        avgvx /= (n - 1);
        avgvy /= (n - 1);
        double sdv = stdev(v, avgv);
        double sdvx = stdev(vx, avgvx);
        double sdvy = stdev(vy, avgvy);

        this.setMean_v(avgv);
        this.setSd_v(sdv);
        this.setMin_v(min_notnull_v);
        this.setMax_v(maxv);

        this.setMean_vx(avgvx);
        this.setSd_vx(sdvx);
        this.setMin_vx(min_notnull_vx);
        this.setMax_vx(maxvx);

        this.setMean_vy(avgvy);
        this.setSd_vy(sdvy);
        this.setMin_vy(min_notnull_vy);
        this.setMax_vy(maxvy);

        this.setSum_of_angles(sum_of_angles);

        //# direction: -pi..pi
        double theta0 = Math.atan2(events.get(events.size() - 1).getY() - events.get(0).getY(), events.get(events.size() - 1).getX() - events.get(0).getX());
        double direction = computeDirection(theta0);
        this.setDirection(direction);

        // pointwise acceleration
        double a[] = new double[n - 2];
        double maxa = 0, avga = 0;
        double mina_not_null = a[0];
        double a_beg_time = t[0];
        boolean isStillPositive = true;
        for (int i = 0; i < a.length; ++i) {
            a[i] = (v[i + 1] - v[i]) / t[i + 1];
            avga += a[i];
            if (maxa > a[i]) {
                maxa = a[i];
            }
            if (a[i] != 0 && a[i] < mina_not_null) {
                mina_not_null = a[i];
            }
            if (isStillPositive && a[i] > 0) {
                a_beg_time += t[i + 1];
            } else {
                isStillPositive = false;
            }
        }
        avga /= (n - 2);
        double sda = stdev(a, avga);
        this.setMean_a(avga);
        this.setSd_a(sda);
        this.setMin_a(mina_not_null);
        this.setMax_a(maxa);

        long elapsedTime = events.get(events.size() - 1).getTime() - events.get(0).getTime();
        a_beg_time /= elapsedTime;

        this.setElapsed_time(elapsedTime);
        int numEvents = events.size();
        this.setNum_events(numEvents);
        this.setA_beg_time(a_beg_time);

        double traveledDistance = computeTraveledDistance(events);
        if (traveledDistance < ClassifierSettings.EPS) {
            return;
        }
        this.setTraveled_distance(traveledDistance);

        double endToEndLine = computeEndToEndLine(events);
        this.setEnd_to_end_line(endToEndLine);

        double efficiency = endToEndLine / traveledDistance;
        this.setEfficiency(efficiency);

        // Angular velocity
        double omega[] = new double[n - 1];
        omega[0] = angles[0] / t[0];
        double min_omega = omega[0];
        double max_omega = omega[0];
        double avg_omega = 0;
        for (int i = 1; i < n - 1; ++i) {
            omega[i] = (angles[i] - angles[i - 1]) / t[i];
            if (omega[i] < min_omega) {
                min_omega = omega[i];
            }
            if (omega[i] > max_omega) {
                max_omega = omega[i];
            }
            avg_omega += omega[i];
        }
        avg_omega /= (n - 1);
        double sd_omega = stdev(omega, avg_omega);

        this.setMean_omega(avg_omega);
        this.setSd_omega(sd_omega);
        this.setMin_omega(min_omega);
        this.setMax_omega(max_omega);
        // jerk
        double jerk[] = new double[n - 2];
        jerk[0] = a[0] / t[1];
        double min_jerk = jerk[0];
        double max_jerk = jerk[0];
        double avg_jerk = 0;
        for (int i = 1; i < n - 2; ++i) {
            jerk[i] = (a[i] - angles[i - 1]) / t[i + 1];
            if (jerk[i] < min_jerk) {
                min_jerk = jerk[i];
            }
            if (jerk[i] > max_jerk) {
                max_jerk = jerk[i];
            }
            avg_jerk += jerk[i];
        }
        avg_jerk /= (n - 2);
        double sd_jerk = stdev(jerk, avg_jerk);

        this.mean_jerk = avg_jerk;
        this.sd_jerk = sd_jerk;
        this.min_jerk = min_jerk;
        this.max_jerk = max_jerk;

        this.setMean_jerk(avg_jerk);
        this.setSd_jerk(sd_jerk);
        this.setMin_jerk(min_jerk);
        this.setMax_jerk(max_jerk);
//      curvature: defined by Gamboa&Fred, 2004
//      numCriticalPoints
        int numCriticalPoints = 0;
        double curvature[] = new double[n - 1];
        int i = 0;
        while (path[i] == 0) {
            ++i;
        }
        curvature[0] = angles[i] / path[i];
        ++i;
        double min_curvature = curvature[0];
        double max_curvature = curvature[0];
        double avg_curvature = curvature[0];
        int k = 0;
        for (; i < n - 1; ++i) {
            if (path[i] == 0) {
                continue;
            }
            ++k;
            curvature[k] = (angles[i] - angles[i - 1]) / (path[i]);

            avg_curvature += curvature[k];
            if (curvature[k] < min_curvature) {
                min_curvature = curvature[k];
            }
            if (curvature[k] > max_curvature) {
                max_curvature = curvature[k];
            }
            if (curvature[k] < ClassifierSettings.CURVATURE_THRESHOLD) {
                numCriticalPoints++;
            }
        }
        avg_curvature /= (k + 1);
        double sum = 0;
        for (int l = 0; l < (k + 1); ++l) {
            sum += (curvature[l] - avg_curvature) * (curvature[l] - avg_curvature);
        }
        double sd_curvature = Math.sqrt(sum / (k + 1));

        this.setNum_critical_points(numCriticalPoints);
        this.setLargest_deviation(largestDeviation(events));
        this.setMean_curv(avg_curvature);
        this.setSd_curv(sd_curvature);
        this.setMin_curv(min_curvature);
        this.setMax_curv(max_curvature);

        //System.out.println(this.toString());
        for (int j = 0; j < this.values.length; ++j) {
            ps.printf("%8.5f,", values[j]);
        }
        ps.println(1);
        //System.out.println("Baj van 2");

    }

    @Override
    public void finalize() {
        ps.close();
    }

    public static final ArrayList<String> att = new ArrayList<String>(Arrays.asList(
            "elapsed_time", "num_events", "action_type", "num_critical_points", "traveled_distance",
            "end_to_end_line", "largest_deviation", "efficiency", "a_beg_time", "sum_of_angles",
            "mean_v", "sd_v", "min_v", "max_v",
            "mean_vx", "sd_vx", "min_vx", "ax_vx",
            "mean_vy", "sd_vy", "min_vy", "max_vy",
            "mean_a", "sd_a", "min_a", "max_a",
            "mean_omega", "sd_omega", "min_omega", "ax_omega",
            "mean_jerk", "sd_jerk", "min_jerk", "max_jerk",
            "mean_curv", "sd_curv", "min_curv", "max_curv", "direction"
    ));

    private double elapsed_time;
    private double num_events;
    private double action_type;
    private double num_critical_points;
    private double traveled_distance;

    private double end_to_end_line;
    private double largest_deviation;
    private double efficiency;
    private double a_beg_time;
    private double sum_of_angles;

    private double mean_v, sd_v, min_v, max_v;
    private double mean_vx, sd_vx, min_vx, max_vx;
    private double mean_vy, sd_vy, min_vy, max_vy;
    private double mean_a, sd_a, min_a, max_a;
    private double mean_omega, sd_omega, min_omega, max_omega;
    private double mean_jerk, sd_jerk, min_jerk, max_jerk;
    private double mean_curv, sd_curv, min_curv, max_curv;
    private double direction;

    private double[] values;

    private ArrayList<IEvent> events = new ArrayList<>();

    public ArrayList<IEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<IEvent> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        //return "TeacherFeature{" + "elapsed_time=" + elapsed_time + ", num_events=" + num_events + ", action_type=" + action_type + ", num_critical_points=" + num_critical_points + ", traveled_distance=" + traveled_distance + ", end_to_end_line=" + end_to_end_line + ", largest_deviation=" + largest_deviation + ", efficiency=" + efficiency + ", a_beg_time=" + a_beg_time + ", sum_of_angles=" + sum_of_angles + ", mean_v=" + mean_v + ", sd_v=" + sd_v + ", min_v=" + min_v + ", max_v=" + max_v + ", mean_vx=" + mean_vx + ", sd_vx=" + sd_vx + ", min_vx=" + min_vx + ", max_vx=" + max_vx + ", mean_vy=" + mean_vy + ", sd_vy=" + sd_vy + ", min_vy=" + min_vy + ", max_vy=" + max_vy + ", mean_a=" + mean_a + ", sd_a=" + sd_a + ", min_a=" + min_a + ", max_a=" + max_a + ", mean_omega=" + mean_omega + ", sd_omega=" + sd_omega + ", min_omega=" + min_omega + ", max_omega=" + max_omega + ", mean_jerk=" + mean_jerk + ", sd_jerk=" + sd_jerk + ", min_jerk=" + min_jerk + ", max_jerk=" + max_jerk + ", mean_curv=" + mean_curv + ", sd_curv=" + sd_curv + ", min_curv=" + min_curv + ", max_curv=" + max_curv + ", values=" + values + ", events=" + events + '}';
        return "TeacherFeature{  direction=" + direction + ", action_type=" + action_type + " Start: " + this.events.get(0).getX() + ", " + this.events.get(0).getY() + " Stop: " + this.events.get(events.size() - 1).getX() + ", " + this.events.get(events.size() - 1).getY();
    }

    @Override
    public Instance getInstance() {
        return new DenseInstance(1.0, this.values);
    }

    public TeacherFeature() {
        this.values = new double[AM_NUM_FEATURES];
    }

//    public TeacherFeature(double elapsed_time, double num_events, double action_type, double num_critical_points, double traveled_distance, double end_to_end_line, double largest_deviation, double efficiency, double a_beg_time, double sum_of_angles, double mean_v, double sd_v, double min_v, double max_v, double mean_vx, double sd_vx, double min_vx, double max_vx, double mean_vy, double sd_vy, double min_vy, double max_vy, double mean_a, double sd_a, double min_a, double max_a, double mean_omega, double sd_omega, double min_omega, double max_omega, double mean_jerk, double sd_jerk, double min_jerk, double max_jerk, double mean_curv, double sd_curv, double min_curv, double max_curv) {
//        this.elapsed_time = elapsed_time;
//        this.num_events = num_events;
//        this.action_type = action_type;
//        this.num_critical_points = num_critical_points;
//        this.traveled_distance = traveled_distance;
//        this.end_to_end_line = end_to_end_line;
//        this.largest_deviation = largest_deviation;
//        this.efficiency = efficiency;
//        this.a_beg_time = a_beg_time;
//        this.sum_of_angles = sum_of_angles;
//        this.mean_v = mean_v;
//        this.sd_v = sd_v;
//        this.min_v = min_v;
//        this.max_v = max_v;
//        this.mean_vx = mean_vx;
//        this.sd_vx = sd_vx;
//        this.min_vx = min_vx;
//        this.max_vx = max_vx;
//        this.mean_vy = mean_vy;
//        this.sd_vy = sd_vy;
//        this.min_vy = min_vy;
//        this.max_vy = max_vy;
//        this.mean_a = mean_a;
//        this.sd_a = sd_a;
//        this.min_a = min_a;
//        this.max_a = max_a;
//        this.mean_omega = mean_omega;
//        this.sd_omega = sd_omega;
//        this.min_omega = min_omega;
//        this.max_omega = max_omega;
//        this.mean_jerk = mean_jerk;
//        this.sd_jerk = sd_jerk;
//        this.min_jerk = min_jerk;
//        this.max_jerk = max_jerk;
//        this.mean_curv = mean_curv;
//        this.sd_curv = sd_curv;
//        this.min_curv = min_curv;
//        this.max_curv = max_curv;
//    }
    public double getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(double elapsed_time) {
        this.elapsed_time = elapsed_time;
        this.values[0] = elapsed_time;
    }

    public double getNum_events() {
        return num_events;
    }

    public void setNum_events(double num_events) {
        this.num_events = num_events;
        this.values[1] = num_events;
    }

    public double getAction_type() {
        return action_type;
    }

    public void setAction_type(double action_type) {
        this.action_type = action_type;
        this.values[2] = this.action_type;
    }

    public double getNum_critical_points() {
        return num_critical_points;
    }

    public void setNum_critical_points(double num_critical_points) {
        this.num_critical_points = num_critical_points;
        this.values[3] = this.num_critical_points;
    }

    public double getTraveled_distance() {
        return traveled_distance;
    }

    public void setTraveled_distance(double traveled_distance) {
        this.traveled_distance = traveled_distance;
        this.values[4] = this.traveled_distance;
    }

    public double getEnd_to_end_line() {
        return end_to_end_line;
    }

    public void setEnd_to_end_line(double end_to_end_line) {
        this.end_to_end_line = end_to_end_line;
        this.values[5] = this.end_to_end_line;
    }

    public double getLargest_deviation() {
        return largest_deviation;
    }

    public void setLargest_deviation(double largest_deviation) {
        this.largest_deviation = largest_deviation;
        this.values[6] = this.largest_deviation;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
        this.values[7] = this.efficiency;
    }

    public double getA_beg_time() {
        return a_beg_time;
    }

    public void setA_beg_time(double a_beg_time) {
        this.a_beg_time = a_beg_time;
        this.values[8] = this.a_beg_time;
    }

    public double getSum_of_angles() {
        return sum_of_angles;
    }

    public void setSum_of_angles(double sum_of_angles) {
        this.sum_of_angles = sum_of_angles;
        this.values[9] = this.sum_of_angles;
    }

    public double getMean_v() {
        return mean_v;
    }

    public void setMean_v(double mean_v) {
        this.mean_v = mean_v;
        this.values[10] = this.mean_v;
    }

    public double getSd_v() {
        return sd_v;
    }

    public void setSd_v(double sd_v) {
        this.sd_v = sd_v;
        this.values[11] = this.sd_v;
    }

    public double getMin_v() {
        return min_v;
    }

    public void setMin_v(double min_v) {
        this.min_v = min_v;
        this.values[12] = this.min_v;
    }

    public double getMax_v() {
        return max_v;
    }

    public void setMax_v(double max_v) {
        this.max_v = max_v;
        this.values[13] = this.max_v;
    }

    public double getMean_vx() {
        return mean_vx;
    }

    public void setMean_vx(double mean_vx) {
        this.mean_vx = mean_vx;
        this.values[14] = this.mean_vx;
    }

    public double getSd_vx() {
        return sd_vx;
    }

    public void setSd_vx(double sd_vx) {
        this.sd_vx = sd_vx;
        this.values[15] = this.sd_vx;
    }

    public double getMin_vx() {
        return min_vx;
    }

    public void setMin_vx(double min_vx) {
        this.min_vx = min_vx;
        this.values[16] = this.min_vx;
    }

    public double getMax_vx() {
        return max_vx;
    }

    public void setMax_vx(double max_vx) {
        this.max_vx = max_vx;
        this.values[17] = this.max_vx;
    }

    public double getMean_vy() {
        return mean_vy;
    }

    public void setMean_vy(double mean_vy) {
        this.mean_vy = mean_vy;
        this.values[18] = this.mean_vy;
    }

    public double getSd_vy() {
        return sd_vy;
    }

    public void setSd_vy(double sd_vy) {
        this.sd_vy = sd_vy;
        this.values[19] = this.sd_vy;
    }

    public double getMin_vy() {
        return min_vy;
    }

    public void setMin_vy(double min_vy) {
        this.min_vy = min_vy;
        this.values[20] = this.min_vy;
    }

    public double getMax_vy() {
        return max_vy;
    }

    public void setMax_vy(double max_vy) {
        this.max_vy = max_vy;
        this.values[21] = this.max_vy;
    }

    public double getMean_a() {
        return mean_a;
    }

    public void setMean_a(double mean_a) {
        this.mean_a = mean_a;
        this.values[22] = this.mean_a;
    }

    public double getSd_a() {
        return sd_a;
    }

    public void setSd_a(double sd_a) {
        this.sd_a = sd_a;
        this.values[23] = this.sd_a;
    }

    public double getMin_a() {
        return min_a;
    }

    public void setMin_a(double min_a) {
        this.min_a = min_a;
        this.values[24] = this.mean_a;
    }

    public double getMax_a() {
        return max_a;
    }

    public void setMax_a(double max_a) {
        this.max_a = max_a;
        this.values[25] = this.max_a;
    }

    public double getMean_omega() {
        return mean_omega;
    }

    public void setMean_omega(double mean_omega) {
        this.mean_omega = mean_omega;
        this.values[26] = this.mean_omega;
    }

    public double getSd_omega() {
        return sd_omega;
    }

    public void setSd_omega(double sd_omega) {
        this.sd_omega = sd_omega;
        this.values[27] = this.sd_omega;
    }

    public double getMin_omega() {
        return min_omega;
    }

    public void setMin_omega(double min_omega) {
        this.min_omega = min_omega;
        this.values[28] = this.min_omega;
    }

    public double getMax_omega() {
        return max_omega;
    }

    public void setMax_omega(double max_omega) {
        this.max_omega = max_omega;
        this.values[29] = this.max_omega;
    }

    public double getMean_jerk() {
        return mean_jerk;
    }

    public void setMean_jerk(double mean_jerk) {
        this.mean_jerk = mean_jerk;
        this.values[30] = this.mean_jerk;
    }

    public double getSd_jerk() {
        return sd_jerk;
    }

    public void setSd_jerk(double sd_jerk) {
        this.sd_jerk = sd_jerk;
        this.values[31] = this.sd_jerk;
    }

    public double getMin_jerk() {
        return min_jerk;
    }

    public void setMin_jerk(double min_jerk) {
        this.min_jerk = min_jerk;
        this.values[32] = this.mean_jerk;
    }

    public double getMax_jerk() {
        return max_jerk;
    }

    public void setMax_jerk(double max_jerk) {
        this.max_jerk = max_jerk;
        this.values[33] = this.max_jerk;
    }

    public double getMean_curv() {
        return mean_curv;
    }

    public void setMean_curv(double mean_curv) {
        this.mean_curv = mean_curv;
        this.values[34] = this.mean_curv;
    }

    public double getSd_curv() {
        return sd_curv;
    }

    public void setSd_curv(double sd_curv) {
        this.sd_curv = sd_curv;
        this.values[35] = this.sd_curv;
    }

    public double getMin_curv() {
        return min_curv;
    }

    public void setMin_curv(double min_curv) {
        this.min_curv = min_curv;
        this.values[36] = this.min_curv;
    }

    public double getMax_curv() {
        return max_curv;
    }

    public void setMax_curv(double max_curv) {
        this.max_curv = max_curv;
        this.values[37] = this.max_curv;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
        this.values[38] = direction;
    }

    private int actionType() {
        for (int i = 0; i < this.events.size(); ++i) {
            if (this.events.get(i).getActiontype().equalsIgnoreCase("Drag")) {
                // DD - Drag and Drop action
                return 4;
            }
        }
        // MM - Mouse Move action
        if (this.events.get(this.events.size() - 1).getActiontype().equalsIgnoreCase("Move")) {
            return 2;
        }
        // PC - Point Click action
        return 3;
    }

    public static double computeTraveledDistance(ArrayList<IEvent> events) {
        double s = 0;
        int start = 0;
        int stop = events.size() - 1;
        for (int i = start; i < stop; ++i) {
            double dx = events.get(i).getX() - events.get(i + 1).getX();
            double dy = events.get(i).getY() - events.get(i + 1).getY();
            s += Math.sqrt(dx * dx + dy * dy);
        }
        return s;
    }

    public static double computeEndToEndLine(ArrayList<IEvent> events) {
        int start = 0;
        int stop = events.size() - 1;
        double dx = events.get(stop).getX() - events.get(start).getX();
        double dy = events.get(stop).getY() - events.get(start).getY();
        double s = Math.sqrt(dx * dx + dy * dy);
        return s;
    }

    public static double largestDeviation(ArrayList<IEvent> events) {
        int n = events.size();
        //line (x_0,y_0) and (x_n-1,y_n-1): ax + by + c
        double a = events.get(n - 1).getY() - events.get(0).getY();
        double b = events.get(0).getX() - events.get(n - 1).getX();
        double c = events.get(0).getY() * events.get(n - 1).getX() - events.get(0).getX() * events.get(n - 1).getY();
        // M(x0,y0), distance(M, ax+by+c)= abs(a*x0+b*y0+c)/sqrt(a^2+b^2)
        double denominator = Math.sqrt(a * a + b * b);
        if (denominator == 0) {
            return 0;
        }
        double maxDist = 0;
        for (int i = 1; i < events.size() - 1; ++i) {
            double dist = a * events.get(i).getX() + b * events.get(i).getY() + c;
            if (dist > maxDist) {
                maxDist = dist;
            }
        }
        return maxDist / denominator;

    }

    public static double stdev(double a[], double mean) {
        double s = 0;
        for (int i = 0; i < a.length; ++i) {
            double dif = a[i] - mean;
            s += dif * dif;
        }
        s /= a.length;
        return Math.sqrt(s);
    }

    public static double min_not_null(double array[]) {
        double min_value = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != 0 && array[i] < min_value) {
                min_value = array[i];
            }
        }
        return min_value;
    }

    //    # directions: 0..7
//# Ahmed & Traore, IEEE TDSC2007
    public static double computeDirection(double theta) {
        if (0 <= theta && theta < Math.PI / 4) {
            return 0;
        }
        if (Math.PI / 4 <= theta && theta < Math.PI / 2) {
            return 1;
        }
        if (Math.PI / 2 <= theta && theta < 3 * Math.PI / 4) {
            return 2;
        }
        if (3 * Math.PI / 4 <= theta && theta < Math.PI) {
            return 3;
        }
        if (-Math.PI / 4 <= theta && theta < 0) {
            return 7;
        }
        if (-Math.PI / 2 <= theta && theta < -Math.PI / 4) {
            return 6;
        }
        if (-3 * Math.PI / 4 <= theta && theta < -Math.PI / 2) {
            return 5;
        }
        if (-Math.PI <= theta && theta < -3 * Math.PI / 4) {
            return 4;
        }
        return 0;
    }

    private ArrayList<IEvent> cleanEvents(ArrayList<IEvent> events) {
        this.events = new ArrayList<IEvent>();
        //An action does not start with Released mouse event 
        int index = 0;
        while (index < events.size() && events.get(index).getActiontype().equals("Released")) {
            ++index;
        }
        this.events.add(events.get(index));

        for (; index < events.size(); ++index) {
            IEvent event = events.get(index);
            if (event.getTime() != this.events.get(this.events.size() - 1).getTime()) {
                this.events.add(event);

            }
        }
        return this.events;
    }

}