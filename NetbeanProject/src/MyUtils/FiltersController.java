/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Rafael
 */
public class FiltersController {

    public FiltersController() {}
    
    private int clampColorValue(int val){
        if(val > 255){
            return 255;
        }
        if(val < 0){
            return 0;
        }
        return val;
    }
    
    private int convolveOnePixel(Kernel k, ArrayList<ArrayList<Integer>> r, ArrayList<ArrayList<Integer>> g, ArrayList<ArrayList<Integer>> b, boolean normal) throws RuntimeException{
        int rTotal = 0;
        int gTotal = 0;
        int bTotal = 0;
        // If all matrices and the kernel have the same dimensions then we can convolute.
        if(k.getHeight() == r.size() && r.size() == g.size() && g.size() == b.size() && k.getWidth() == r.get(0).size() && r.get(0).size() == g.get(0).size() && g.get(0).size() == b.get(0).size()){
            for(int i = 0 ; i < k.getHeight(); i++){
                for(int j = 0 ; j < k.getWidth(); j++){
                    rTotal += r.get(i).get(j) * k.getValue(j, i);
                    gTotal += g.get(i).get(j) * k.getValue(j, i);
                    bTotal += b.get(i).get(j) * k.getValue(j, i);
                }
            }

            if(normal){
                rTotal /= k.getSum();
                gTotal /= k.getSum();
                bTotal /= k.getSum();
            }
            rTotal = clampColorValue(rTotal);
            gTotal = clampColorValue(gTotal);
            bTotal = clampColorValue(bTotal);
        }else{
        // Error: Can't calculate convolution.
          throw new RuntimeException("Error en las dimensiones de la convolución.");  //JOptionPane.showMessageDialog(this, "¡ERROR: Error en la convolusion!");
        }
        return (255<<24) | (rTotal<<16) | (gTotal<<8) | bTotal;
    }
    
    private Kernel getGaussianKernel(int n, boolean vert){
        Kernel k;
        int[] values;
        int ppx;
        int ppy;
        int w;
        int h;

        if(vert){
            w = 1;
            h = n;
        }else{
            w = n;
            h = 1;
        }

        switch(n){
            case 2:
                ppx = ppy = 0;
                values = new int[]{1, 1};
                break;
            case 3:
                if(vert){       // Is it vertical?
                    ppx = 0;
                    ppy = 1;
                }else{          // It is horizontal.
                    ppx = 1;
                    ppy = 0;
                }
                values = new int[]{1, 2, 1};
                break;
            case 4:
                if(vert){       // Is it vertical?
                    ppx = 0;
                    ppy = 1;
                }else{          // It is horizontal.
                    ppx = 1;
                    ppy = 0;
                }
                values = new int[]{1, 3, 3, 1};
                break;
            case 5:
                if(vert){       // Is it vertical?
                    ppx = 0;
                    ppy = 2;
                }else{          // It is horizontal.
                    ppx = 2;
                    ppy = 0;
                }
                values = new int[]{1, 4, 6, 4, 1};
                break;
            case 6:
                if(vert){       // Is it vertical?
                    ppx = 0;
                    ppy = 2;
                }else{          // It is horizontal.
                    ppx = 2;
                    ppy = 0;
                }
                values = new int[]{1, 5, 10, 10, 5, 1};
                break;
            case 7:
                if(vert){       // Is it vertical?
                    ppx = 0;
                    ppy = 3;
                }else{          // It is horizontal.
                    ppx = 3;
                    ppy = 0;
                }
                values = new int[]{1, 6, 15, 20, 15, 6, 1};
                break;
            default:
                return null;
        }
        k = new Kernel(values, w, h, ppx, ppy);
        return k;
    }
    
    private void initWindows(Kernel k, int x, int y, BufferedImage img, ArrayList<ArrayList<Integer>> r, ArrayList<ArrayList<Integer>> g, ArrayList<ArrayList<Integer>> b){
        int fillerValue = 0;
        // Calculating the "image" coordinates of each (0,0) windows pixels.
        int xg = x - k.getPivotX();
        int yg = y - k.getPivotY();
        int pvalue;

        r.clear();
        g.clear();
        b.clear();
        for (int i = yg; i < k.getHeight() + yg; i++){
            ArrayList redRow = new ArrayList();
            ArrayList greenRow = new ArrayList();
            ArrayList blueRow = new ArrayList();
            for(int j = xg; j < k.getWidth() + xg; j++){
                // Calculating the "image" coordinates of each windows pixels
                //xg = x - ppixelX + j;
                //yg = y - ppixelY + i;

                // If any coordinate is negative or bigger than the image dimensions use the fillerValue
                // else use the image pixels color values.
                if ( j < 0 || j >= img.getWidth() || i < 0 || i >= img.getHeight() ){
                    redRow.add(fillerValue);
                    greenRow.add(fillerValue);
                    blueRow.add(fillerValue);
                }else{
                    pvalue = img.getRGB(j, i);
                    redRow.add((pvalue >> 16) & 0xff);
                    greenRow.add((pvalue >> 8) & 0xff);
                    blueRow.add(pvalue & 0xff);
                }
            }
            // Adding a complete row to the matrix.
            r.add(redRow);
            g.add(greenRow);
            b.add(blueRow);
        }
    }
    
    private void slideRightWindowsOnePixel(Kernel k, int x, int y, BufferedImage img, ArrayList<ArrayList<Integer>> r, ArrayList<ArrayList<Integer>> g, ArrayList<ArrayList<Integer>> b){
        int fillerValue = 0;
        // Calculating the "image" coordinates of each next-to-the-right windows pixels.
        int xg = k.getWidth() - k.getPivotX() + x;
        int yg;
        int pvalue;

        for (int i = 0; i < r.size(); i++) {
            // Calculating the "image" coordinates of each next-to-the-right windows pixels.
            yg = y - k.getPivotY() + i;

            // If any coordinate is negative or bigger than the image dimensions use the fillerValue
            // else use the image pixels color values.
            if ( xg < 0 || xg >= img.getWidth() || yg < 0 || yg >= img.getHeight() ){
                r.get(i).add(fillerValue);
                g.get(i).add(fillerValue);
                b.get(i).add(fillerValue);
            }else{
                pvalue = img.getRGB(xg, yg);
                r.get(i).add((pvalue >> 16) & 0xff);
                g.get(i).add((pvalue >> 8) & 0xff);
                b.get(i).add(pvalue & 0xff);
            }

            // Removing the first value of each row.
            r.get(i).remove(0);
            g.get(i).remove(0);
            b.get(i).remove(0);
        }
    }
    
    /**
    * Modifies an image giving it a Gaussian Blur effect with the kernel size and orientation provided.
    *
    * @param n Size of the kernel.
    * @param Vert Boolean, if true Vertical Gaussian Blur will be applied.
    * @param Horiz Boolean, if true Horizontal Gaussian Blur will be applied.
    * @param srcImage
    * @return 
    */
    public BufferedImage GaussianBlur( BufferedImage srcImage, int n, boolean Vert, boolean Horiz ) throws RuntimeException{
        BufferedImage destImage = srcImage;
        BufferedImage imgTemp2 = new BufferedImage(destImage.getWidth(), destImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        int i;
        int j;
      
        if (Horiz){
            Kernel krnlHoriz = getGaussianKernel(n, false);
            // These ArrayLists serve as sliding windows per color channel (Horizontal).
            ArrayList<ArrayList<Integer>> red = new ArrayList();
            ArrayList<ArrayList<Integer>> green = new ArrayList();
            ArrayList<ArrayList<Integer>> blue = new ArrayList();

            for(i = 0; i < destImage.getHeight(); i++){
                initWindows(krnlHoriz, 0, i, destImage, red, green, blue);
                for(j = 0; j < destImage.getWidth(); j++){
                    int newPixelValue = 0;
                    try{
                        newPixelValue = convolveOnePixel(krnlHoriz, red, green, blue, true);
                    }catch(RuntimeException re){
                        throw new RuntimeException("No se pudo aplicar filtro gaussiano.",re);
                    }
                    imgTemp2.setRGB(j, i, newPixelValue);

                    slideRightWindowsOnePixel(krnlHoriz, j, i, destImage, red, green, blue);
                }
            }
            destImage = imgTemp2;
        }

        if(Vert){
            Kernel krnlVert = getGaussianKernel(n, true);
            // These ArrayLists serve as sliding windows per color channel (Horizontal).
            ArrayList<ArrayList<Integer>> red = new ArrayList();
            ArrayList<ArrayList<Integer>> green = new ArrayList();
            ArrayList<ArrayList<Integer>> blue = new ArrayList();

            for(i = 0; i < destImage.getHeight(); i++){
                initWindows(krnlVert, 0, i, destImage, red, green, blue);
                for(j = 0; j < destImage.getWidth(); j++){
                    int newPixelValue = 0;
                    try{
                        newPixelValue = convolveOnePixel(krnlVert, red, green, blue, true);
                    }catch(RuntimeException re){
                        throw new RuntimeException("No se pudo aplicar filtro gaussiano.",re);
                    }
                    imgTemp2.setRGB(j, i, newPixelValue);

                    slideRightWindowsOnePixel(krnlVert, j, i, destImage, red, green, blue);
                }
            }
            destImage = imgTemp2;
        }
        return destImage;
    }
    
}
