/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signaturerecognation;

import java.awt.image.BufferedImage;
import weka.filters.unsupervised.instance.imagefilter.BinaryPatternsPyramidFilter;
import weka.filters.unsupervised.instance.imagefilter.EdgeHistogramFilter;

/**
 *
 * @author Denes
 */
public class a extends EdgeHistogramFilter {

    public a() {
        super();
    }
    
    
    
    
    @Override
    protected double[] getFeatures(BufferedImage img){
       return super.getFeatures(img);
    }
}
