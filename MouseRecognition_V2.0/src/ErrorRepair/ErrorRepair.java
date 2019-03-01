/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ErrorRepair;

import Settings.DataCollectorSettings;
import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Denes
 */
public class ErrorRepair {

    private Instances data;

    public ErrorRepair() {
    }

    public ErrorRepair(Instances data) {
        this.data = data;
    }

    public Instances getData() {
        return data;
    }

    public void setData(Instances data) {
        this.data = data;
    }

    public Instances repairerror() {
        Instances repairedinstances = new Instances(this.data, DataCollectorSettings.numberofactions * 2);
        for (int actualinstanceindex = 0; actualinstanceindex < this.data.numInstances(); ++actualinstanceindex) {
            Instance ins = this.data.get(actualinstanceindex);
            boolean haveissue = false;
            for (int atributes = 0; atributes < ins.numAttributes(); ++atributes) {
                //  System.out.println(ins.attribute(atributes).numValues());
            }
            for (int i = 0; i < ins.toDoubleArray().length; ++i) {

                if (Double.isInfinite(ins.toDoubleArray()[i])) {
                    //   this.data.remove(ins);
                    haveissue = true;
                    break;
                }
                if (Double.isNaN(ins.toDoubleArray()[i])) {
                    //   this.data.remove(ins);
                    haveissue = true;
                    break;
                }
            }
            if (!haveissue) {
                repairedinstances.add(ins);
            }

        }
        System.out.println(repairedinstances);
        return repairedinstances;
    }

}
