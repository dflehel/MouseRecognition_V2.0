/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import Main.Starter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 *
 * @author Denes
 */
public class DataCollectorSettings implements Serializable {

    public DataCollectorSettings() {
    }

    public int getMinimumofmouseactions() {
        return this.minimumofmouseactions;
    }

    public void setMinimumofmouseactions(int minimumofmouseactions) {
        this.minimumofmouseactions = minimumofmouseactions;
    }

    public int getNumberofactions() {
        return this.numberofactions;
    }

    public void setNumberofactions(int numberofactions) {
        this.numberofactions = numberofactions;
    }

    public static int minimumofmouseactions = 5000;
    public static int numberofactions = 0;
    public static Preferences preferenc = Preferences.userRoot();

    public static void loaddata() {
        try {

            preferenc.importPreferences(new FileInputStream("numberofactions.xml"));
            numberofactions = preferenc.getInt("actionnum", 0);
        } catch (Exception ex) {
            Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, ex);
            preferenc = Preferences.userRoot();
            preferenc.putInt("actionnum", 0);
            numberofactions = 0;
            savedata();

        }

    }

    public static void savedata() {
        try {
            preferenc.putInt("actionnum", numberofactions);
            preferenc.flush();
            preferenc.exportNode(new FileOutputStream("numberofactions.xml"));

        } catch (Exception ex) {
            Logger.getLogger(DataCollectorSettings.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}