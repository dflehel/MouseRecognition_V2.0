/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouserecognition;

import java.util.Queue;

/**
 *
 * @author Denes
 */
public interface IClassifier {

    public void classify();

    public void classify(Queue<IFeature> moves);
    
    public void classify(Queue<IFeature> moves,int numactions);
    
    public void classifyonemovment(IFeature move);
    
    

}