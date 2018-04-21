/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyUtils;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import javax.imageio.ImageIO;
import javax.imageio.*;
import static java.lang.Math.*;

/**
 *
 * @author Raquel Escalante
 * @author Rafael Vasquez
 */
public class Histogram {
    //General matrix for histograms
    private int[][] bins;
    //Maximum value per channel to normalize every channel for the interface
    private int maxRed;
    private int maxGreen;
    private int maxBlue;

    public Histogram(){
        //Initializers
        this.bins = new int [4][256];
        for (int i = 0; i < 256; i++) {
            this.bins[0][i] = this.bins[1][i] = this.bins[2][i] = 0;
            maxRed = 0;
            maxGreen = 0;
            maxBlue = 0;
        }        
    }
    
    public void setBins(BufferedImage image, String col){
        
        //Getting the values from the pixel
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                //Getting the colors from every pixel
                int p = image.getRGB(x,y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                
                if (col.equals("GRAY")){
                    this.bins[0][r]++; //Bin just for luminosity         
                }else if (col.equals("COLOR")){
                    this.bins[0][r]++; //Bin for red channel
                    this.bins[1][g]++; //Bin for Green channel
                    this.bins[2][b]++; //Bin for Blue channel
                }
                
                //Obtaining maximum values for every channel
                if (this.bins[0][r] > maxRed){
                    this.maxRed = bins[0][r];
                }

                if (this.bins[1][g] > maxGreen){
                    this.maxGreen = bins[1][g];
                }

                if (this.bins[2][b] > maxBlue){
                    this.maxBlue = bins[2][b];
                }                
            }
        }      
    }
    
    public void normalizeHistograms(String col){
        //
        if (col.equals("GRAY")){
            for (int i = 0; i < 256; i++){
                this.bins[0][i] = Math.round((float)(this.bins[0][i])/this.maxRed*100);
            }       
        }else if (col.equals("COLOR")){
            for (int i = 0; i < 256; i++){
                this.bins[0][i] = Math.round((float)(this.bins[0][i])/this.maxRed*100);
                this.bins[1][i] = Math.round((float)(this.bins[1][i])/this.maxGreen*100);
                this.bins[2][i] = Math.round((float)(this.bins[2][i])/this.maxBlue*100);        
            }   
        }       
    }
    
    public int[] getGrayHistogram(){
        return this.bins[0];
    }
    
    public int[] getRedHistogram(){
        return this.bins[0];
    }
    
    public int[] getGreenHistogram(){
        return this.bins[1];
    }
    
    public int[] getBlueHistogram(){
        return this.bins[2];
    }
    
}
