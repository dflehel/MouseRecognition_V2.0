/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouserecognition;

import java.util.ArrayList;
import java.util.Arrays;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

   

/**
 *
 * @author Denes
 */
public class LehellFeature implements IFeature{
    public static final ArrayList<String> att = new ArrayList<String>(Arrays.asList("Euklideszi"	,"Xtav"	,"Ytav"	,"Pontonkentitav"	,"Sebesseg"	,"Xsebesseg"	,"Ysebesseg"	,"Atlagsebesseg"	,"Xatlagszabesseg"	,"Yatlagsebesseg"	,"Gyorsulas"	,"Xgyorsulas"	,"Ygyorsulas"	,"Pontonkentigyorsulas"	,"Xpontonkentigyosulas"	,"Ypontonkentigyorsulas"	,"jerkue"	,"jerkpontonkenti"	,"ossszszog"	,"atlagszogseb"	,"mozgaspontok"	,"milyen"	,"melyikgomb"));
   private double Euklideszi;	
   private double Xtav;	
   private double Ytav;	
   private double Pontonkentitav;	
   private double Sebesseg;	 
   private double Xsebesseg;	
   private double Ysebesseg;	
   private double Atlagsebesseg;	
   private double Xatlagszabesseg;	
   private double Yatlagsebesseg;	
   private double Gyorsulas;	
   private double Xgyorsulas;	
   private double Ygyorsulas;	
   private double Pontonkentigyorsulas;	
   private double Xpontonkentigyosulas;	
   private double Ypontonkentigyorsulas;	
   private double jerkue;	
   private double jerkpontonkenti;	
   private double ossszszog;	
   private double atlagszogseb;	
   private int mozgaspontok;	
   private int milyen;	
   private int melyikgomb;
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
        return "Feature{" + "Euklideszi=" + Euklideszi + ", Xtav=" + Xtav + ", Ytav=" + Ytav + ", Pontonkentitav=" + Pontonkentitav + ", Sebesseg=" + Sebesseg + ", Xsebesseg=" + Xsebesseg + ", Ysebesseg=" + Ysebesseg + ", Atlagsebesseg=" + Atlagsebesseg + ", Xatlagszabesseg=" + Xatlagszabesseg + ", Yatlagsebesseg=" + Yatlagsebesseg + ", Gyorsulas=" + Gyorsulas + ", Xgyorsulas=" + Xgyorsulas + ", Ygyorsulas=" + Ygyorsulas + ", Pontonkentigyorsulas=" + Pontonkentigyorsulas + ", Xpontonkentigyosulas=" + Xpontonkentigyosulas + ", Ypontonkentigyorsulas=" + Ypontonkentigyorsulas + ", jerkue=" + jerkue + ", jerkpontonkenti=" + jerkpontonkenti + ", ossszszog=" + ossszszog + ", atlagszogseb=" + atlagszogseb + ", mozgaspontok=" + mozgaspontok + ", milyen=" + milyen + ", melyikgomb=" + melyikgomb + '}';
    }

    
    public Instance getInstance(){
//        ArrayList<Attribute> attr = new ArrayList<>();
//        for(int i =0;i<att.size();++i){
//            attr.add(new Attribute(att.get(i)));
//        }
          //this.values =
          return new DenseInstance(1.0,this.values);
    }
   
    
    
    public LehellFeature() {
        this.values = new double[Settings.DFL_NUM_FEATURES];
    }

    public LehellFeature(double Euklideszi, double Xtav, double Ytav, double Pontonkentitav, double Sebesseg, double Xsebesseg, double Ysebesseg, double Atlagsebesseg, double Xatlagszabesseg, double Yatlagsebesseg, double Gyorsulas, double Xgyorsulas, double Ygyorsulas, double Pontonkentigyorsulas, double Xpontonkentigyosulas, double Ypontonkentigyorsulas, double jerkue, double jerkpontonkenti, double ossszszog, double atlagszogseb, int mozgaspontok, int milyen, int melyikgomb) {
        this.Euklideszi = Euklideszi;
        this.Xtav = Xtav;
        this.Ytav = Ytav;
        this.Pontonkentitav = Pontonkentitav;
        this.Sebesseg = Sebesseg;
        this.Xsebesseg = Xsebesseg;
        this.Ysebesseg = Ysebesseg;
        this.Atlagsebesseg = Atlagsebesseg;
        this.Xatlagszabesseg = Xatlagszabesseg;
        this.Yatlagsebesseg = Yatlagsebesseg;
        this.Gyorsulas = Gyorsulas;
        this.Xgyorsulas = Xgyorsulas;
        this.Ygyorsulas = Ygyorsulas;
        this.Pontonkentigyorsulas = Pontonkentigyorsulas;
        this.Xpontonkentigyosulas = Xpontonkentigyosulas;
        this.Ypontonkentigyorsulas = Ypontonkentigyorsulas;
        this.jerkue = jerkue;
        this.jerkpontonkenti = jerkpontonkenti;
        this.ossszszog = ossszszog;
        this.atlagszogseb = atlagszogseb;
        this.mozgaspontok = mozgaspontok;
        this.milyen = milyen;
        this.melyikgomb = melyikgomb;
    }

    public double getEuklideszi() {
        return Euklideszi;
    }

    public void setEuklideszi(double Euklideszi) {
        this.Euklideszi = Euklideszi;
        this.values[0]=Euklideszi;
    }

    public double getXtav() {
        return Xtav;
    }

    public void setXtav(double Xtav) {
        this.Xtav = Xtav;
        this.values[1]=Xtav;
    }

    public double getYtav() {
        return Ytav;
    }

    public void setYtav(double Ytav) {
        this.Ytav = Ytav;
        this.values[2]=Ytav;
    }

    public double getPontonkentitav() {
        return Pontonkentitav;
    }

    public void setPontonkentitav(double Pontonkentitav) {
        this.Pontonkentitav = Pontonkentitav;
        this.values[3]=Pontonkentitav;
    }

    public double getSebesseg() {
        return Sebesseg;
    }

    public void setSebesseg(double Sebesseg) {
        this.Sebesseg = Sebesseg;
        this.values[4]= Sebesseg;
    }

    public double getXsebesseg() {
        return Xsebesseg;
    }

    public void setXsebesseg(double Xsebesseg) {
        this.Xsebesseg = Xsebesseg;
        this.values[5]=Xsebesseg;
    }

    public double getYsebesseg() {
        return Ysebesseg;
    }

    public void setYsebesseg(double Ysebesseg) {
        this.Ysebesseg = Ysebesseg;
        this.values[6]=Ysebesseg;
    }

    public double getAtlagsebesseg() {
        return Atlagsebesseg;
    }

    public void setAtlagsebesseg(double Atlagsebesseg) {
        this.Atlagsebesseg = Atlagsebesseg;
        this.values[7]=Atlagsebesseg;
    }

    public double getXatlagszabesseg() {
        return Xatlagszabesseg;
    }

    public void setXatlagszabesseg(double Xatlagszabesseg) {
        this.Xatlagszabesseg = Xatlagszabesseg;
        this.values[8]=Xatlagszabesseg;
    }

    public double getYatlagsebesseg() {
        return Yatlagsebesseg;
    }

    public void setYatlagsebesseg(double Yatlagsebesseg) {
        this.Yatlagsebesseg = Yatlagsebesseg;
        this.values[9]=Yatlagsebesseg;
    }

    public double getGyorsulas() {
        return Gyorsulas;
    }

    public void setGyorsulas(double Gyorsulas) {
        this.Gyorsulas = Gyorsulas;
        this.values[10]=Gyorsulas;
    }

    public double getXgyorsulas() {
        return Xgyorsulas;
    }

    public void setXgyorsulas(double Xgyorsulas) {
        this.Xgyorsulas = Xgyorsulas;
        this.values[11]=Xgyorsulas;
    }

    public double getYgyorsulas() {
        return Ygyorsulas;
    }

    public void setYgyorsulas(double Ygyorsulas) {
        this.Ygyorsulas = Ygyorsulas;
        this.values[12]=Ygyorsulas;
    }

    public double getPontonkentigyorsulas() {
        return Pontonkentigyorsulas;
    }

    public void setPontonkentigyorsulas(double Pontonkentigyorsulas) {
        this.Pontonkentigyorsulas = Pontonkentigyorsulas;
        this.values[13]=Pontonkentigyorsulas;
    }

    public double getXpontonkentigyosulas() {
        return Xpontonkentigyosulas;
    }

    public void setXpontonkentigyosulas(double Xpontonkentigyosulas) {
        this.Xpontonkentigyosulas = Xpontonkentigyosulas;
        this.values[14]=Xpontonkentigyosulas;
    }

    public double getYpontonkentigyorsulas() {
        return Ypontonkentigyorsulas;
    }

    public void setYpontonkentigyorsulas(double Ypontonkentigyorsulas) {
        this.Ypontonkentigyorsulas = Ypontonkentigyorsulas;
        this.values[15]=Ypontonkentigyorsulas;
    }

    public double getJerkue() {
        return jerkue;
    }

    public void setJerkue(double jerkue) {
        this.jerkue = jerkue;
        this.values[16]=jerkue;
    }

    public double getJerkpontonkenti() {
        return jerkpontonkenti;
    }

    public void setJerkpontonkenti(double jerkpontonkenti) {
        this.jerkpontonkenti = jerkpontonkenti;
        this.values[17]=jerkpontonkenti;
    }

    public double getOssszszog() {
        return ossszszog;
    }

    public void setOssszszog(double ossszszog) {
        this.ossszszog = ossszszog;
        this.values[18]=ossszszog;
    }

    public double getAtlagszogseb() {
        return atlagszogseb;
    }

    public void setAtlagszogseb(double atlagszogseb) {
        this.atlagszogseb = atlagszogseb;
        this.values[19]=atlagszogseb;
    }

    public int getMozgaspontok() {
        return mozgaspontok;
    }

    public void setMozgaspontok(int mozgaspontok) {
        this.mozgaspontok = mozgaspontok;
        this.values[20]=(double)mozgaspontok;
    }

    public int getMilyen() {
        return milyen;
    }

    public void setMilyen(int milyen) {
        this.milyen = milyen;
        this.values[21]=(double)milyen;
    }

    public int getMelyikgomb() {
        return melyikgomb;
    }

    public void setMelyikgomb(int melyikgomb) {
        this.melyikgomb = melyikgomb;
        this.values[22]=(double)melyikgomb;
    }

    @Override
    public void ExtractFeatures(ArrayList<IEvent> events) {
            this.events = events;
            this.setAtlagsebesseg(this.attalagsebesseg());
            this.setAtlagszogseb(this.atlagszogseb());
            this.setEuklideszi(this.eukledeszi());
            this.setGyorsulas(this.gyorsulas());
            this.setJerkpontonkenti(this.jerkpontonkenti());
            this.setJerkue(this.jerkeu());
            this.setMelyikgomb(this.milyengob());
            this.setMilyen(this.melyikmozgas());
            this.setMozgaspontok(this.mozgapontok());
            this.setOssszszog(this.osszszog());
            this.setPontonkentigyorsulas(this.pontonkentigyorsulas());
            this.setPontonkentitav(this.pontonkentitavolsag());
            this.setSebesseg(this.sebesseg());
            this.setXatlagszabesseg(this.xatlagsebesseg());
            this.setXgyorsulas(this.xgyorsulas());
            this.setXpontonkentigyosulas(this.xpontonkentigyorsulas());
            this.setXsebesseg(this.xsebesseg());
            this.setXtav(this.xtav());
            this.setYatlagsebesseg(this.yatlagsebesseg());
            this.setYgyorsulas(this.ygyorsulas());
            this.setYpontonkentigyorsulas(this.ypontonkentigyorsulas());
            this.setYtav(this.ytav());
            this.setYsebesseg(this.ysebesseg());
    }
    
      private double sebesseg(){
            int x1 = this.events.get(0).getX();
            int x2 = this.events.get(this.events.size()-1).getX();
            int y1 = this.events.get(0).getY();
            int y2 = this.events.get(this.events.size()-1).getY();
            long time1 = this.events.get(0).getTime();
            long time2 = this.events.get(this.events.size()-1).getTime();
            Double distance = Math.sqrt((Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2)));
            distance = (distance/(time2-time1));
            return distance;
    }
    
    private double eukledeszi(){
            int x1 = this.events.get(0).getX();
            int x2 = this.events.get(this.events.size()-1).getX();
            int y1 = this.events.get(0).getY();
            int y2 = this.events.get(this.events.size()-1).getY();
            Double distance = Math.sqrt((Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2)));
            return distance;
    }
    
    private double xtav(){
         int x1 = this.events.get(0).getX();
         int x2 = this.events.get(this.events.size()-1).getX();
         Double distance = Math.sqrt((Math.pow((x2-x1), 2)));
         return distance;
    }
    
    private double ytav(){
            int y1 = this.events.get(0).getY();
            int y2 = this.events.get(this.events.size()-1).getY();
            Double distance = Math.sqrt((Math.pow((y2-y1), 2)));
            return distance;
    }
    
    private double xsebesseg(){
         int x1 = this.events.get(0).getX();
            int x2 = this.events.get(this.events.size()-1).getX();
            long time1 = this.events.get(0).getTime();
            long time2 = this.events.get(this.events.size()-1).getTime();
            Double distance = Math.sqrt((Math.pow((x2-x1), 2)));
            distance = (distance/(time2-time1));
            return distance;
    }
    
    private double ysebesseg(){
            int y1 = this.events.get(0).getY();
            int y2 = this.events.get(this.events.size()-1).getY();
            long time1 = this.events.get(0).getTime();
            long time2 = this.events.get(this.events.size()-1).getTime();
            Double distance = Math.sqrt((Math.pow((y2-y1), 2)));
            distance = (distance/(time2-time1));
            return distance;
    }
    
    private double pontonkentitavolsag(){
            Double result = 0.0;
            int x1 = this.events.get(0).getX();
            int y1 = this.events.get(0).getY();
            int x2 = 0;
            int y2 = 0;
            for (int i =1;i<this.events.size();++i){
                x2 = this.events.get(i).getX();
                y2 = this.events.get(i).getY();
                result +=  Math.sqrt((Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2)));
                x1 = x2;
                y1 = y2;
            }
            return result;
    }
    
    private double attalagsebesseg(){
         Double result = 0.0;
            int x1 = this.events.get(0).getX();
            int y1 = this.events.get(0).getY();
            long time1 = this.events.get(0).getTime();
            int x2 = 0;
            int y2 = 0;
            long time2 = 0;
            for (int i =1;i<this.events.size();++i){
                x2 = this.events.get(i).getX();
                y2 = this.events.get(i).getY();
                time2 = this.events.get(i).getTime();
                  Double distance =Math.sqrt((Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2)));
                result += (distance/(time2-time1));
                x1 = x2;
                y1 = y2;
                time1 = time2;
            }
            result = result/this.events.size();
            return result;
    }
    
    private double xatlagsebesseg(){
         Double result = 0.0;
            int x1 = this.events.get(0).getX();
            long time1 = this.events.get(0).getTime();
            int x2 = 0;
            long time2 = 0;
            for (int i =1;i<this.events.size();++i){
                x2 = this.events.get(i).getX();
                time2 = this.events.get(i).getTime();
               Double distance =Math.sqrt((Math.pow((x2-x1), 2)));
                result += (distance/(time2-time1));
                x1 = x2;
                time1 = time2;
            }
            result = result/this.events.size();
            return result;
    }
    
    private double yatlagsebesseg(){
         Double result = 0.0;
            int y1 = this.events.get(0).getY();
            long time1 = this.events.get(0).getTime();
            int y2 = 0;
            long time2 = 0;
            for (int i =1;i<this.events.size();++i){
                y2 = this.events.get(i).getY();
                time2 = this.events.get(i).getTime();
                  Double distance =Math.sqrt((Math.pow((y2-y1), 2)));
                result += (distance/(time2-time1));
                y1 = y2;
                time1 = time2;
            }
            result = result/this.events.size();
            return result;
    }
    
    private double gyorsulas(){
         int x1 = this.events.get(0).getX();
            int x2 = this.events.get(this.events.size()-1).getX();
            int y1 = this.events.get(0).getY();
            int y2 = this.events.get(this.events.size()-1).getY();
            long time1 = this.events.get(0).getTime();
            long time2 = this.events.get(this.events.size()-1).getTime();
            Double distance = Math.sqrt((Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2)));
            distance = (distance/(time2-time1));
            distance = (distance/(time2-time1));
            return distance;
    }
    
    private double xgyorsulas(){
         int x1 = this.events.get(0).getX();
            int x2 = this.events.get(this.events.size()-1).getX();
            long time1 = this.events.get(0).getTime();
            long time2 = this.events.get(this.events.size()-1).getTime();
            Double distance = Math.sqrt((Math.pow((x2-x1), 2)));
            distance = (distance/(time2-time1));
            distance = (distance/(time2-time1));
            return distance;
    }
    
    private double ygyorsulas(){
            int y1 = this.events.get(0).getY();
            int y2 = this.events.get(this.events.size()-1).getY();
            long time1 = this.events.get(0).getTime();
            long time2 = this.events.get(this.events.size()-1).getTime();
            Double distance = Math.sqrt((Math.pow((y2-y1), 2)));
            distance = (distance/(time2-time1));
            distance = (distance/(time2-time1));
            return distance;
    }
    
    private double pontonkentigyorsulas(){
         Double result = 0.0;
            int x1 = this.events.get(0).getX();
            int y1 = this.events.get(0).getY();
            long time1 = this.events.get(0).getTime();
            int x2 = 0;
            int y2 = 0;
            long time2 = 0;
            for (int i =1;i<this.events.size();++i){
                x2 = this.events.get(i).getX();
                y2 = this.events.get(i).getY();
                time2 = this.events.get(i).getTime();
                  Double distance =Math.sqrt((Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2)));
                   distance = (distance/(time2-time1));
                result += (distance/(time2-time1));
                x1 = x2;
                y1 = y2;
                time1 = time2;
            }
            result = result/this.events.size();
            return result;
    }
    
    private double xpontonkentigyorsulas(){
         Double result = 0.0;
            int x1 = this.events.get(0).getX();
            long time1 = this.events.get(0).getTime();
            int x2 = 0;
            long time2 = 0;
            for (int i =1;i<this.events.size();++i){
                x2 = this.events.get(i).getX();
                time2 = this.events.get(i).getTime();
                  Double distance =Math.sqrt((Math.pow((x2-x1), 2)));
                   distance = (distance/(time2-time1));
                result += (distance/(time2-time1));
                x1 = x2;
                time1 = time2;
            }
            result = result/this.events.size();
            return result;
    }
    
    private double ypontonkentigyorsulas(){
         Double result = 0.0;
            int y1 = this.events.get(0).getY();
            long time1 = this.events.get(0).getTime();
            int y2 = 0;
            long time2 = 0;
            for (int i =1;i<this.events.size();++i){
                y2 = this.events.get(i).getY();
                time2 = this.events.get(i).getTime();
                  Double distance =Math.sqrt((Math.pow((y2-y1), 2)));
                   distance = (distance/(time2-time1));
                result += (distance/(time2-time1));
                y1 = y2;
                time1 = time2;
            }
            result = result/this.events.size();
            return result;
    }
    
    
    private double jerkeu(){
         int x1 = this.events.get(0).getX();
            int x2 = this.events.get(this.events.size()-1).getX();
            int y1 = this.events.get(0).getY();
            int y2 = this.events.get(this.events.size()-1).getY();
            long time1 = this.events.get(0).getTime();
            long time2 = this.events.get(this.events.size()-1).getTime();
            Double distance = Math.sqrt((Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2)));
            distance = (distance/(time2-time1));
            distance = (distance/(time2-time1));
            distance = (distance/(time2-time1));
            return distance;
    }
    
    private double jerkpontonkenti(){
        Double result = 0.0;
            int x1 = this.events.get(0).getX();
            int y1 = this.events.get(0).getY();
            long time1 = this.events.get(0).getTime();
            int x2 = 0;
            int y2 = 0;
            long time2 = 0;
            for (int i =1;i<this.events.size();++i){
                x2 = this.events.get(i).getX();
                y2 = this.events.get(i).getY();
                time2 = this.events.get(i).getTime();
                  Double distance =Math.sqrt((Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2)));
                   distance = (distance/(time2-time1));
                   distance = (distance/(time2-time1));
                result += (distance/(time2-time1));
                x1 = x2;
                y1 = y2;
                time1 = time2;
            }
            result = result/this.events.size();
            return result;
    }
    
    private int mozgapontok(){
        Integer s = this.events.size();
        return s;
     }
    
    private double osszszog(){
         Double result = 0.0;
         int x1 = this.events.get(0).getX();
         int y1 = this.events.get(0).getY();
         int x2 = 0;
         int y2 = 0;
         for (int i  = 1 ; i<this.events.size();++i){
            double disty = Math.sqrt(Math.pow((y2-y1), 2));
            double distx = Math.sqrt(Math.pow((x2-x1), 2));
             result += Math.atan2(disty, distx);
         }
         return result;
    }
    
    private double atlagosszog(){
        Double result = 0.0;
         int x1 = this.events.get(0).getX();
         int y1 = this.events.get(0).getY();
         int x2 = 0;
         int y2 = 0;
         for (int i  = 1 ; i<this.events.size();++i){
           double  disty = Math.sqrt(Math.pow((y2-y1), 2));
           double  distx = Math.sqrt(Math.pow((x2-x1), 2));
             result += Math.atan2(disty, distx);
         }
         result = result / this.events.size();
         return result;
    }
    
    private double atlagszogseb(){
          Double result = 0.0;
            int x1 = this.events.get(0).getX();
            int y1 = this.events.get(0).getY();
            long time1 = this.events.get(0).getTime();
            int  x2 = this.events.get(1).getX();
            int    y2 = this.events.get(1).getY();
            long    time2 = this.events.get(1).getTime();
            double  disty = Math.sqrt(Math.pow((y2-y1), 2));
            double  distx = Math.sqrt(Math.pow((x2-x1), 2));
           double szog = Math.atan2(disty, distx);
              for (int i =2;i<this.events.size();++i){
                x2 = this.events.get(i).getX();
                y2 = this.events.get(i).getY();
                time2 = this.events.get(i).getTime();
                disty = Math.sqrt(Math.pow((y2-y1), 2));
                distx = Math.sqrt(Math.pow((x2-x1), 2));
                Double distance = Math.atan2(disty, distx) - szog;
                result += distance/(time1-time2);
                }
              result = result/this.events.size();
              return result;
    }
    
    private int melyikmozgas(){
        for (int i=0;i< this.events.size();++i){
            if(this.events.get(i).getActiontype().equalsIgnoreCase("Drag")){
                return 0;
            }
        }
        if (this.events.get(this.events.size()-1).getActiontype().equalsIgnoreCase("Move")){
            return 2;
        }
        return 1;
    }
    
    private int milyengob(){
        if (this.events.get(this.events.size()-1).getButtonype().equalsIgnoreCase("Left")){
            return 1;
        }
         if (this.events.get(this.events.size()-1).getButtonype().equalsIgnoreCase("Right")){
            return 2;
        }
           return 0;
        }
    

    
}
