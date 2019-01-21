/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouserecognition;

import java.util.ArrayList;
import weka.core.Instance;

/**
 *
 * @author Denes
 */
/** Az implementalt osztalyban el kell tarolni a featurek ertekeit amit majd a get instanccel kerek le a classifierben */
public interface IFeature {
    
    /**Ez a fuggveny vissza adja az instancet a featurekbol*/
     public Instance getInstance();
     
     /**A fuggveny felelosege az hogy megcsinalja a featurek kinyereset egy eventbol */
     public void ExtractFeatures(ArrayList<IEvent> events);
     
}
