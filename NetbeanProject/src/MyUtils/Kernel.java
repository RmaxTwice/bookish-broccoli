/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyUtils;

import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author Raquel Escalante
 * @author Rafael Vasquez
 */
public class Kernel {
    private ArrayList<ArrayList<Integer>> values;
    private int width;
    private int height;
    private int pivotX;
    private int pivotY;
    private int sumValues;

    public Kernel(int[] vals, int width, int height, int pivotX, int pivotY) {
        if (width * height == vals.length){
            ArrayList<Integer> tmp;
            int val;

            this.width = width;
            this.height = height;
            this.pivotX = pivotX;
            this.pivotY = pivotY;
            this.sumValues = 0;

            for(int i = 0; i < height; i++){
                tmp = new ArrayList();
                for(int j = 0; j < width; j++){
                    val = vals[width * i + j];
                    this.sumValues += abs(val);
                    tmp.add(val);
                }
                this.values.add(tmp);
            }
        }else{
            this.values = null;
        }
    }
    
    public int getValue(int x, int y){
        if(x < 0 || y < 0 || x >= this.width || y >= this.height ){
            return -999999;
        }
        return this.values.get(y).get(x);
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public int getPivotX(){
        return this.pivotX;
    }
    
    public int getPivotY(){
        return this.pivotY;
    }
    
    public int getSum(){
        return this.sumValues;
    }
}
