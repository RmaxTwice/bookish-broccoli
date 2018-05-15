/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyUtils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Rafael Vasquez
 * @author Raquel Escalante
 */
public class FiltersController {

    public FiltersController() {}

    private int bilinealInterpolation(int colorNW, int colorNE, int colorSW, int colorSE, double a, double b){
        int NWNEr = (int)((1 - a) * ((colorNW >> 16) & 0xff) + a * ((colorNE >> 16) & 0xff));
        int NWNEg = (int)((1 - a) * ((colorNW >> 8) & 0xff) + a * ((colorNE >> 8) & 0xff));
        int NWNEb = (int)((1 - a) * (colorNW & 0xff) + a * (colorNE & 0xff));

        int SWSEr = (int)((1 - a) * ((colorSW >> 16) & 0xff) + a * ((colorSE >> 16) & 0xff));
        int SWSEg = (int)((1 - a) * ((colorSW >> 8) & 0xff) + a * ((colorSE >> 8) & 0xff));
        int SWSEb = (int)((1 - a) * (colorSW & 0xff) + a * (colorSE & 0xff));

        int rTotal = clampColorValue((int)((1 - b) * NWNEr  + b * SWSEr));
        int gTotal = clampColorValue((int)((1 - b) * NWNEg  + b * SWSEg));
        int bTotal = clampColorValue((int)((1 - b) * NWNEb  + b * SWSEb));

        return (255<<24) | (rTotal<<16) | (gTotal<<8) | bTotal;
    }

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
        // If all matrices and the kernel have the same dimensions then we can convolve.
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

    private int convolveAndCompareOnePixel(Kernel kx, Kernel ky, ArrayList<ArrayList<Integer>> r, ArrayList<ArrayList<Integer>> g, ArrayList<ArrayList<Integer>> b) throws RuntimeException{
        int rx = 0;
        int ry = 0;
        int rTotal = 0;
        int gx = 0;
        int gy = 0;
        int gTotal = 0;
        int bx = 0;
        int by = 0;
        int bTotal = 0;
        // If all matrices and the kernel have the same dimensions then we can convolute.
        if(kx.getHeight() == ky.getHeight() && kx.getHeight() == r.size() && r.size() == g.size() && g.size() == b.size() && kx.getWidth() == ky.getWidth() && kx.getWidth() == r.get(0).size() && r.get(0).size() == g.get(0).size() && g.get(0).size() == b.get(0).size()){
            for(int i = 0 ; i < kx.getHeight(); i++){
                for(int j = 0 ; j < kx.getWidth(); j++){
                    rx += r.get(i).get(j) * kx.getValue(j, i);
                    gx += g.get(i).get(j) * kx.getValue(j, i);
                    bx += b.get(i).get(j) * kx.getValue(j, i);
                    ry += r.get(i).get(j) * ky.getValue(j, i);
                    gy += g.get(i).get(j) * ky.getValue(j, i);
                    by += b.get(i).get(j) * ky.getValue(j, i);
                }
            }
            rTotal = Math.abs(rx)+  Math.abs(ry);
            gTotal = Math.abs(gx)+  Math.abs(gy);
            bTotal = Math.abs(bx)+  Math.abs(by);
            rTotal = clampColorValue(rTotal);
            gTotal = clampColorValue(gTotal);
            bTotal = clampColorValue(bTotal);
        }else{
        // Error: Can't calculate convolution.
          throw new RuntimeException("Error en las dimensiones de la convolución.");  //JOptionPane.showMessageDialog(this, "¡ERROR: Error en la convolusion!");
        }
        return (255<<24) | (rTotal<<16) | (gTotal<<8) | bTotal;
    }

    private int medianOperationOnePixel(ArrayList<ArrayList<Integer>> r, ArrayList<ArrayList<Integer>> g, ArrayList<ArrayList<Integer>> b){
        ArrayList<Integer> tmpR = new ArrayList();
        ArrayList<Integer> tmpG = new ArrayList();
        ArrayList<Integer> tmpB = new ArrayList();
        boolean odd = true;
        int rTotal;
        int gTotal;
        int bTotal;

        if(r.size()*r.get(0).size() % 2 == 0){ // If h * w of the windows is an even number...
            odd = false;
        }
        for(int i = 0; i < r.size(); i++){
            tmpR.addAll(r.get(i));
            tmpG.addAll(g.get(i));
            tmpB.addAll(b.get(i));
        }
        Collections.sort(tmpR);
        Collections.sort(tmpG);
        Collections.sort(tmpB);
        int indx = tmpR.size() / 2;
        if(odd){
              rTotal = tmpR.get(indx);
              gTotal = tmpG.get(indx);
              bTotal = tmpB.get(indx);
        }else{
              rTotal = (tmpR.get(indx) + tmpR.get(indx-1)) / 2;
              gTotal = (tmpG.get(indx) + tmpG.get(indx-1)) / 2;
              bTotal = (tmpB.get(indx) + tmpB.get(indx-1)) / 2;
        }
        rTotal = clampColorValue(rTotal);
        gTotal = clampColorValue(gTotal);
        bTotal = clampColorValue(bTotal);

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

    private Kernel getAverageKernel(int w, int h){
        Kernel k;
        int[] values = new int[w*h];
        int ppx = (w % 2 == 0) ? w / 2 - 1 : w / 2;
        int ppy = (h % 2 == 0) ? h / 2 - 1 : h / 2;

        Arrays.fill(values, 1);
        k = new Kernel(values, w, h, ppx, ppy);

        return k;
    }

    private int[] getRotatedImageDimensions(int[] BBcoords){
        //BBcoords: 0 = minX, 1 = maxX, 2 = minY, 3 = maxY;
        // 0 = width, 1 = height.
        if(BBcoords.length == 4){
            int[] whArray = new int[2];
            whArray[0] = BBcoords[1] - BBcoords[0];
            whArray[1] = BBcoords[3] - BBcoords[2];
            return whArray;
        }
        return null;
    }

    private int[] getRotatedImageBoundingBox(int srcWidth, int srcHeight, double cosDegrees, double sinDegrees, double cx, double cy){
            int maxX = -9999999;
            int minX = 9999999;
            int maxY = -9999999;
            int minY = 9999999;
            int buffer = 2;
            //0 = minX, 1 = maxX, 2 = minY, 3 = maxY;
            int[] res = new int[4];
            // Pairs of coordinates(x,y) of NW, NE, SW and SE corners of the image.
            int[] cornerCoords = new int[8];

            // Forward Mapping
            cornerCoords[0] = (int)(cosDegrees * (0 - cx) - sinDegrees * (0 - cy) + cx );
            cornerCoords[1] = (int)(sinDegrees * (0 - cx) + cosDegrees * (0 - cy )+ cy );

            cornerCoords[2] = (int)(cosDegrees * (srcWidth - 1 - cx) - sinDegrees * (0 - cy) + cx );
            cornerCoords[3] = (int)(sinDegrees * (srcWidth - 1 - cx) + cosDegrees * (0 - cy )+ cy );

            cornerCoords[4] = (int)(cosDegrees * (0 - cx) - sinDegrees * (srcHeight - 1 - cy) + cx );
            cornerCoords[5] = (int)(sinDegrees * (0 - cx) + cosDegrees * (srcHeight - 1 - cy )+ cy );

            cornerCoords[6] = (int)(cosDegrees * (srcWidth - 1 - cx) - sinDegrees * (srcHeight - 1 - cy) + cx );
            cornerCoords[7] = (int)(sinDegrees * (srcWidth - 1 - cx) + cosDegrees * (srcHeight - 1 - cy )+ cy );

            for(int i = 0; i < 4; i++){
                if (cornerCoords[i*2] < minX){
                    minX  = cornerCoords[i*2];
                }
                if (cornerCoords[i*2] > maxX){
                    maxX  = cornerCoords[i*2];
                }
                if (cornerCoords[i*2 + 1] < minY){
                    minY  = cornerCoords[i*2  + 1];
                }
                if (cornerCoords[i*2 + 1] > maxY){
                    maxY  = cornerCoords[i*2  + 1];
                }
            }
            res[0] = minX - buffer;
            res[1] = maxX + buffer;
            res[2] = minY - buffer;
            res[3] = maxY + buffer;

            return res;
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
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @param n Size of the kernel.
    * @param Vert Boolean, if true Vertical Gaussian Blur will be applied.
    * @param Horiz Boolean, if true Horizontal Gaussian Blur will be applied.
    * @return A BufferedImage reference modified with a Gaussian blur.
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

    /**
    * Modifies an image giving it a Mean Blur effect with the kernel dimensions provided.
    *
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @param w width of the kernel to be applied.
    * @param h height of the kernel to be applied.
    * @return A BufferedImage reference modified with a Mean blur.
    */
    public BufferedImage MeanBlur(BufferedImage srcImage, int w, int h) throws RuntimeException {
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        int i;
        int j;

        Kernel krnl = getAverageKernel(w, h);
        // These ArrayLists serve as sliding windows per color channel (Horizontal).
        ArrayList<ArrayList<Integer>> red = new ArrayList();
        ArrayList<ArrayList<Integer>> green = new ArrayList();
        ArrayList<ArrayList<Integer>> blue = new ArrayList();

        for(i = 0; i < srcImage.getHeight(); i++){
            initWindows(krnl, 0, i, srcImage, red, green, blue);
            for(j = 0; j < srcImage.getWidth(); j++){
                int newPixelValue = 0;
                try{
                    newPixelValue = convolveOnePixel(krnl, red, green, blue, true);
                }catch(RuntimeException re){
                    throw new RuntimeException("No se pudo aplicar filtro promedio.",re);
                }
                destImage.setRGB(j, i, newPixelValue);

                slideRightWindowsOnePixel(krnl, j, i, srcImage, red, green, blue);
            }
        }
        return destImage;
    }

    /**
    * Modifies an image giving it a Median effect with the kernel dimensions provided.
    *
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @param w width of the kernel to be applied.
    * @param h height of the kernel to be applied.
    * @return A BufferedImage reference modified with a Median blur.
    */
    public BufferedImage MedianFilter(BufferedImage srcImage, int w, int h){
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        int i;
        int j;

        Kernel krnl = getAverageKernel(w, h);
        // These ArrayLists serve as sliding windows per color channel (Horizontal).
        ArrayList<ArrayList<Integer>> red = new ArrayList();
        ArrayList<ArrayList<Integer>> green = new ArrayList();
        ArrayList<ArrayList<Integer>> blue = new ArrayList();

        for(i = 0; i < srcImage.getHeight(); i++){
            initWindows(krnl, 0, i, srcImage, red, green, blue);
            for(j = 0; j < srcImage.getWidth(); j++){
                int newPixelValue = medianOperationOnePixel(red, green, blue);
                destImage.setRGB(j, i, newPixelValue);
                slideRightWindowsOnePixel(krnl, j, i, srcImage, red, green, blue);
            }
        }
        return destImage;
    }

    /**
    * Calculates an images border gradients based on the orientation provided using Prewitt kernels.
    *
    * Orientation must be one of the following parameters or a null pointer will be returned.
    * 1 = Vertical ; 2 = Horizontal; 3 = Diagonal (/); 4 = Diagonal (\); 5 = All combined 
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @param orientation orientation of the kernel to be applied.
    * @return A BufferedImage reference to an image detailing the given images border gradients against a black background.
    */
    public BufferedImage Prewitt(BufferedImage srcImage, int orientation){
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        int i;
        int j;
        int[] vals;
        Kernel krnl = null;
        Kernel krnl1, krnl2, krnl3, krnl4;

        vals = new int[]{-1, 0, 1, -1, 0, 1, -1, 0, 1};
        krnl1 = new Kernel(vals, 3, 3, 1, 1);           //"Vertical |"
        vals = new int[]{-1, -1, -1, 0, 0, 0, 1, 1, 1};
        krnl2 = new Kernel(vals, 3, 3, 1, 1);           //"Horizontal -"
        vals = new int[]{-1, -1, 0, -1, 0, 1, 0, 1, 1};
        krnl3 = new Kernel(vals, 3, 3, 1, 1);           //"Diagonal /":
        vals = new int[]{0, 1, 1, -1, 0, 1, -1, -1, 0};
        krnl4 = new Kernel(vals, 3, 3, 1, 1);           //"Diagonal \\"

        switch(orientation){
            case 1:
                krnl = krnl1;
                break;
            case 2:
                krnl = krnl2;
                break;
            case 3:
                krnl = krnl3;
                break;
            case 4:
                krnl = krnl4;
            case 5:
                break;
            default:
                return null;
        }

        // These ArrayLists serve as sliding windows per color channel (Horizontal).
        ArrayList<ArrayList<Integer>> red = new ArrayList();
        ArrayList<ArrayList<Integer>> green = new ArrayList();
        ArrayList<ArrayList<Integer>> blue = new ArrayList();

        for(i = 0; i < srcImage.getHeight(); i++){
            initWindows(krnl1, 0, i, srcImage, red, green, blue);
            for(j = 0; j < srcImage.getWidth(); j++){
                int newPixelValue1 = 0;
                int newPixelValue2 = 0;
                int newPixelValue3 = 0;
                int newPixelValue4 = 0;

                if(orientation == 5){
                    try{
                        newPixelValue1 = convolveOnePixel(krnl1, red, green, blue, false);
                        newPixelValue2 = convolveOnePixel(krnl2, red, green, blue, false);
                        newPixelValue3 = convolveOnePixel(krnl3, red, green, blue, false);
                        newPixelValue4 = convolveOnePixel(krnl4, red, green, blue, false);
                    }catch(RuntimeException re){
                        throw new RuntimeException("No se pudo aplicar filtro Prewitt.",re);
                    }

                    int newPixelVal = Math.max(newPixelValue1, Math.max(newPixelValue2, Math.max(newPixelValue3, newPixelValue4)));
                    destImage.setRGB(j, i, newPixelVal);
                }else{
                    try{
                        newPixelValue1 = convolveOnePixel(krnl, red, green, blue, false);
                    }catch(RuntimeException re){
                        throw new RuntimeException("No se pudo aplicar filtro Prewitt.",re);
                    }
                    destImage.setRGB(j, i, newPixelValue1);
                }
                slideRightWindowsOnePixel(krnl1, j, i, srcImage, red, green, blue);
            }
        }
        return destImage;
    }

    /**
    * Calculates an images border gradients based on the orientation provided using Sobel kernels.
    *
    * Orientation must be one of the following parameters or a null pointer will be returned.
    * 1 = Vertical ; 2 = Horizontal; 5 = All combined.
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @param orientation orientation of the kernel to be applied.
    * @return A BufferedImage reference to an image detailing the given images border gradients against a black background.
    */
    public BufferedImage Sobel(BufferedImage srcImage, int orientation){
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        int i;
        int j;
        int[] vals;
        Kernel krnl = null;
        Kernel krnl1, krnl2;

        vals = new int[]{-1, 0, 1, -2, 0, 2, -1, 0, 1};
        krnl1 = new Kernel(vals, 3, 3, 1, 1);           //"Vertical"
        vals = new int[]{-1, -2, -1, 0, 0, 0, 1, 2, 1};
        krnl2 = new Kernel(vals, 3, 3, 1, 1);           //"Horizontal"
        switch(orientation){
            case 1:
                krnl = krnl1;
                break;
            case 2:
                krnl = krnl2;
            case 5:
                break;
            default:
                return null;
        }
        // These ArrayLists serve as sliding windows per color channel (Horizontal).
        ArrayList<ArrayList<Integer>> red = new ArrayList();
        ArrayList<ArrayList<Integer>> green = new ArrayList();
        ArrayList<ArrayList<Integer>> blue = new ArrayList();

        for(i = 0; i < srcImage.getHeight(); i++){
            initWindows(krnl1, 0, i, srcImage, red, green, blue);
            for(j = 0; j < srcImage.getWidth(); j++){
                int newPixelVal = 0;

                if(orientation == 5){
                    try{
                        newPixelVal = convolveAndCompareOnePixel(krnl2, krnl1, red, green, blue);
                        //newPixelValue2 = convolveOnePixel(krnl2, red, green, blue, false);
                    }catch(RuntimeException re){
                        throw new RuntimeException("No se pudo aplicar filtro Sobel.",re);
                    }
                    destImage.setRGB(j, i, newPixelVal);
                }else{
                    try{
                        newPixelVal = convolveOnePixel(krnl, red, green, blue, false);
                    }catch(RuntimeException re){
                        throw new RuntimeException("No se pudo aplicar filtro Sobel.",re);
                    }
                    destImage.setRGB(j, i, newPixelVal);
                }
                slideRightWindowsOnePixel(krnl1, j, i, srcImage, red, green, blue);
            }
        }
        return destImage;
    }

    /**
    * Calculates an images border gradients using the result from both Roberts kernels (\ and /).
    *
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @return A BufferedImage reference to an image detailing the given images border gradients against a black background.
    */
    public BufferedImage Roberts(BufferedImage srcImage){
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        int i;
        int j;
        int[] vals;
        Kernel krnl1, krnl2;

        vals = new int[]{1, 0, 0, -1};
        krnl1 = new Kernel(vals, 2, 2, 0, 0);   //"Vertical |"
        vals = new int[]{0, 1, -1, 0};
        krnl2 = new Kernel(vals, 2, 2, 0, 0);   //"Horizontal -"

        // These ArrayLists serve as sliding windows per color channel (Horizontal).
        ArrayList<ArrayList<Integer>> red = new ArrayList();
        ArrayList<ArrayList<Integer>> green = new ArrayList();
        ArrayList<ArrayList<Integer>> blue = new ArrayList();

        for(i = 0; i < srcImage.getHeight(); i++){
            initWindows(krnl1, 0, i, srcImage, red, green, blue);
            for(j = 0; j < srcImage.getWidth(); j++){
                int newPixelVal;

                try{
                    newPixelVal = convolveAndCompareOnePixel(krnl2, krnl1, red, green, blue);
                    //newPixelValue2 = convolveOnePixel(krnl2, red, green, blue, false);
                }catch(RuntimeException re){
                    throw new RuntimeException("No se pudo aplicar filtro Prewitt.",re);
                }
                destImage.setRGB(j, i, newPixelVal);

                slideRightWindowsOnePixel(krnl1, j, i, srcImage, red, green, blue);
            }
        }
        return destImage;
    }

    /**
    * Calculates an images border gradients using the Laplacian 3x3 kernel.
    *
    *  The kernel used is: {-1, -1, -1, -1, 8, -1, -1, -1, -1}
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @return A BufferedImage reference to an image detailing the given images border gradients against a black background.
    */
    public BufferedImage Laplacian(BufferedImage srcImage){
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        int i;
        int j;
        int[] vals;
        Kernel krnl;

        vals = new int[]{-1, -1, -1, -1, 8, -1, -1, -1, -1};
        //vals = new int[]{0, -1, 0, -1, 4, -1, 0, -1, 0};
        krnl = new Kernel(vals, 3, 3, 1, 1);

        // These ArrayLists serve as sliding windows per color channel (Horizontal).
        ArrayList<ArrayList<Integer>> red = new ArrayList();
        ArrayList<ArrayList<Integer>> green = new ArrayList();
        ArrayList<ArrayList<Integer>> blue = new ArrayList();

        for(i = 0; i < srcImage.getHeight(); i++){
            initWindows(krnl, 0, i, srcImage, red, green, blue);
            for(j = 0; j < srcImage.getWidth(); j++){
                int newPixelVal;

                try{
                    newPixelVal = convolveOnePixel(krnl, red, green, blue, false);
                }catch(RuntimeException re){
                    throw new RuntimeException("No se pudo aplicar filtro Laplaciano del Gaussiano.",re);
                }
                destImage.setRGB(j, i, newPixelVal);

                slideRightWindowsOnePixel(krnl, j, i, srcImage, red, green, blue);
            }
        }
        return destImage;
    }

    /**
    * Convolves an image using the custom kernel supplied.
    *
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @param k reference of a custom kernel to convolve the image with.
    * @return A BufferedImage reference resulting from a custom convolution.
    */
    public BufferedImage CustomFilter(BufferedImage srcImage, Kernel k){
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        int i;
        int j;
        // These ArrayLists serve as sliding windows per color channel (Horizontal).
        ArrayList<ArrayList<Integer>> red = new ArrayList();
        ArrayList<ArrayList<Integer>> green = new ArrayList();
        ArrayList<ArrayList<Integer>> blue = new ArrayList();

        for(i = 0; i < srcImage.getHeight(); i++){
            initWindows(k, 0, i, srcImage, red, green, blue);
            for(j = 0; j < srcImage.getWidth(); j++){
                int newPixelValue = 0;

                try{
                    newPixelValue = convolveOnePixel(k, red, green, blue, false);
                }catch(RuntimeException re){
                    throw new RuntimeException("No se pudo aplicar filtro Prewitt.",re);
                }
                destImage.setRGB(j, i, newPixelValue);

                slideRightWindowsOnePixel(k, j, i, srcImage, red, green, blue);
            }
        }
        return destImage;
    }

    /**
    * Produces an image with a zoom (in or out) effect from the original image.
    *
    * This zoom only uses pixel replication as it's interpolation method.
    * If percentage less or equal to zero a null image will be returned.
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @param percentage The amount to zoom in (percentage > 100) or zoom out (percentage < 100).
    * @return A BufferedImage reference resulting from a zoom in or out operation on the original image.
    */
    public BufferedImage Zoom(BufferedImage srcImage, int percentage){
        if(percentage <= 0)
            return null;
        
        float zoomFactor = (float)percentage / 100;
        int newWidth = Math.round(srcImage.getWidth() * zoomFactor);
        int newHeight = Math.round(srcImage.getHeight() * zoomFactor);
        BufferedImage destImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_3BYTE_BGR);
        int  pvalue;
        float ycoord,xcoord;

        // USING NEAREST NEIGHBOR OR PIXEL REPLICATION
        for(int i = 0; i < newHeight; i++){
            for(int j = 0; j < newWidth; j++){
                ycoord = i / zoomFactor;
                xcoord = j / zoomFactor;
                pvalue = srcImage.getRGB((int)xcoord, (int)ycoord);
                destImage.setRGB(j, i, pvalue);
            }
        }
        return destImage;
    }

    /**
    * Produces an scaled version of the source image according to the provided scaling factors and interpolation type.
    *
    * The only valid values for TYPE are: 0 = Pixel replication; 1 = Bilinear
    * If any ratio is less or equal to zero a null image will be returned.
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
     * @param wPctg The amount to scale up (wPctg > 100) or down (wPctg < 100) in the horizontal axis.
     * @param hPctg The amount to scale up (hPctg > 100) or down (hPctg < 100) in the vertical axis.
     * @param TYPE  Type of interpolation to use when computing the resulting images colors.
    * @return A BufferedImage reference to an scaled version of the original image.
    */
    public BufferedImage Scale(BufferedImage srcImage, int wPctg, int hPctg, int TYPE){
        // TYPE: 0 = pixel replication, 1 = bilineal interpolation
        float scalingFactorX = (float)wPctg / 100;
        float scalingFactorY = (float)hPctg / 100;
        int newWidth = Math.round(srcImage.getWidth() * scalingFactorX);
        int newHeight = Math.round(srcImage.getHeight() * scalingFactorY);
        BufferedImage imgTemp = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_3BYTE_BGR);
        int  pvalue = 0;
        float ycoord,xcoord;
        int nwX, nwY, neX, neY, swX, swY, seX, seY;
        float a, b;
        int colorNW, colorNE, colorSW, colorSE;

        for(int i = 0; i < newHeight; i++){
            for(int j = 0; j < newWidth; j++){
                switch(TYPE){
                    case 0:
                        //USING NEAREST NEIGHBOR OR PIXEL REPLICATION
                        ycoord = i / scalingFactorY;
                        xcoord = j / scalingFactorX;
                        pvalue = srcImage.getRGB((int)xcoord, (int)ycoord);
                        break;
                    case 1:
                        // USING BILINEAR INTERPOLATION
                        nwX = (int)(j / scalingFactorX);
                        nwY = (int)(i / scalingFactorY);
                        colorNW = srcImage.getRGB(nwX, nwY);
                        if(nwX + 1 < srcImage.getWidth()){
                            neX = nwX + 1;
                            a = (neX - nwX) * scalingFactorX;
                            a = (j % a)/ a;
                        }else{
                            neX =nwX;
                            a = 1;
                        }
                        neY = nwY;
                        colorNE = srcImage.getRGB(neX, neY);
                        swX = nwX;
                        if(nwY + 1 < srcImage.getHeight()){
                            swY = nwY + 1;
                            b = (swY - nwY) * scalingFactorY;
                            b = (i % b) / b;
                        }else{
                            swY = nwY;
                            b = 1;
                        }
                        colorSW = srcImage.getRGB(swX, swY);
                        seX = neX;
                        seY = swY;
                        colorSE = srcImage.getRGB(seX, seY);
                        pvalue = bilinealInterpolation(colorNW, colorNE, colorSW, colorSE, a, b);
                }
                imgTemp.setRGB(j, i, pvalue);
            }
        }
        return imgTemp;
    }

    /**
    * Produces a rotated clock-wise version of the source image according to the angle and interpolation type provided.
    *
    * The only valid values for TYPE are: 0 = Pixel replication; 1 = Bilinear
    * If degrees is less to zero  or larger than 360 a null image will be returned.
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
     * @param degrees clockwise rotation degrees.
     * @param clip  indicates whether the resulting image will be clipped or enlarged to accomodate the rotated image.
     * @param TYPE  Type of interpolation to use when computing the resulting images colors.
    * @return A BufferedImage reference to a rotated version of the original image.
    */
    public BufferedImage FreeRotation(BufferedImage srcImage, int degrees, boolean clip, int TYPE){
        // TYPE: 0 = pixel replication, 1 = bilineal interpolation
        double cosDegrees = Math.cos(Math.toRadians(degrees));
        double sinDegrees = Math.sin(Math.toRadians(degrees));
        int newWidth;
        int newHeight;
        int pvalue;
        double cx = srcImage.getWidth() / 2 + 0.5;
        double cy = srcImage.getHeight() / 2  + 0.5;
        int newX;
        int newY;
        double trueX;
        double trueY;
        int nwX, nwY, neX, neY, swX, swY, seX, seY;
        double a, b;
        int colorNW , colorNE, colorSW, colorSE;
        int[] coordsBB = new int[]{0,0,0,0,0,0,0,0};
        BufferedImage destImage;

        //colorNW = colorNE = colorSW = colorSE = -16777216;
        if(clip){
            newWidth = srcImage.getWidth();
            newHeight = srcImage.getHeight();
        }else{
            //coordsBB: 0 = minX, 1 = maxX, 2 = minY, 3 = maxY;
            coordsBB = getRotatedImageBoundingBox(srcImage.getWidth(), srcImage.getHeight(), cosDegrees, sinDegrees, cx, cy);
            int[] dims = getRotatedImageDimensions(coordsBB);
            newWidth = dims[0];
            newHeight = dims[1];
        }
        destImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_3BYTE_BGR);

        switch(TYPE){
            case 0: //USING NEAREST NEIGHBOR OR PIXEL REPLICATION
                for(int y = 0 + coordsBB[2]; y < newHeight + coordsBB[2]; y++){
                    for(int x = 0 + coordsBB[0]; x < newWidth + coordsBB[0]; x++){
                        // Backwards Mapping
                        newX = (int)(cosDegrees * (x - cx) + sinDegrees * (y - cy) + cx );
                        newY = (int)(-sinDegrees * (x - cx) + cosDegrees * (y - cy )+ cy );
                        if(newX >= 0 && newX < srcImage.getWidth() && newY >= 0 && newY < srcImage.getHeight()){
                            pvalue = srcImage.getRGB(newX, newY);
                            destImage.setRGB(x - coordsBB[0] , y - coordsBB[2], pvalue);
                        }
                    }
                }
                break;
            case 1: //USING Bilineal Interpolation
                for(int y = 0 + coordsBB[2]; y < newHeight + coordsBB[2]; y++){
                    for(int x = 0 + coordsBB[0]; x < newWidth + coordsBB[0]; x++){
                        trueX = cosDegrees * (x - cx) + sinDegrees * (y - cy) + cx;
                        trueY = -sinDegrees * (x - cx) + cosDegrees * (y - cy )+ cy;
                        nwX = (int)Math.floor(trueX);
                        nwY = (int)Math.floor(trueY);
                        if(nwX < 0 || nwX >= srcImage.getWidth() || nwY < 0 || nwY >= srcImage.getHeight()){
                            continue;
                        }
                        colorNW = srcImage.getRGB(nwX, nwY);
                        if(nwX + 1 < srcImage.getWidth()){
                            neX = nwX + 1;
                            a = trueX - (double)nwX;
                        }else{
                            neX =nwX;
                            a = 1;
                        }
                        neY = nwY;
                        colorNE = srcImage.getRGB(neX, neY);
                        swX = nwX;
                        if(nwY + 1 < srcImage.getHeight()){
                            swY = nwY + 1;
                            b = trueY - (double)nwY;
                        }else{
                            swY = nwY;
                            b = 1;
                        }
                        colorSW = srcImage.getRGB(swX, swY);
                        seX = neX;
                        seY = swY;
                        colorSE = srcImage.getRGB(seX, seY);
                        pvalue = bilinealInterpolation(colorNW, colorNE, colorSW, colorSE, a, b);
                        destImage.setRGB(x - coordsBB[0] , y - coordsBB[2], pvalue);
                    }
                }
        }
        return destImage;
    }

    /**
    * Produces an inverse image color-wise from the original one.
    *
    * This function assumes the BufferedImage is not null
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @return A BufferedImage reference to an image with inverse colors.
    */
    public BufferedImage Negative(BufferedImage srcImage){
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int y = 0; y < srcImage.getHeight(); y++){
            for(int x = 0; x < srcImage.getWidth(); x++){
                // Unpacking the data of each pixel with masks.
                int p = srcImage.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                // Inverting the colors of the pixel per sample.
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                // Packing back the pixel data
                p = (a<<24) | (r<<16) | (g<<8) | b;
                destImage.setRGB(x, y, p);
            }
        }
        return destImage;
    }

    /**
    * Produces an gray scaled image from the original one.
    *
    * This function assumes the BufferedImage is not null
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @return A BufferedImage reference to a gray scale image.
    */
    public BufferedImage GrayScale(BufferedImage srcImage){
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int y = 0; y < srcImage.getHeight(); y++){
            for(int x = 0; x < srcImage.getWidth(); x++){
                int avg;
                // Unpacking the data of each pixel with masks.
                int p = srcImage.getRGB(x,y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                // Calculating average per pixel
                avg = (r+g+b)/3;
                // Packing back the pixel data
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;
                destImage.setRGB(x, y, p);
            }
        }
        return destImage;
    }

    /**
    * Produces an black and white image from the original one, thresholded according to the given value.
    *
    * This function assumes the BufferedImage is not null
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @param threshold the threshold value to compare color levels to.
    * @return A BufferedImage reference to a black and white image.
    */
    public BufferedImage ThresholdBlackAndWhite(BufferedImage srcImage, int threshold){
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int y = 0; y < srcImage.getHeight(); y++){
            for(int x = 0; x < srcImage.getWidth(); x++){
                int avg;
                // Unpacking the data of each pixel with masks.
                int p = srcImage.getRGB(x,y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                //Calculating average per pixel
                avg = (r+g+b)/3;
                //Sorting average into range according to threshold
                if (avg < threshold){
                    avg = 0;
                }else{
                    avg = 255;
                }
                //Packing back the pixel data
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;
                destImage.setRGB(x, y, p);
            }
        }
        return destImage;
    }

    /**
    * Produces an 90 degrees clockwise rotated image from the original one.
    *
    * This function assumes the BufferedImage is not null
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @return A BufferedImage reference to a rotated image.
    */
    public BufferedImage CWRotate90Degrees(BufferedImage srcImage){
        BufferedImage destImage = new BufferedImage(srcImage.getHeight(), srcImage.getWidth(), BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < srcImage.getWidth(); i++){
            for(int j = 0; j < srcImage.getHeight(); j++){
                destImage.setRGB(srcImage.getHeight() - j - 1, i, srcImage.getRGB(i, j));
            }
        }
        return destImage;
    }

    /**
    * Produces an 90 degrees counter clockwise rotated image from the original one.
    *
    * This function assumes the BufferedImage is not null
    * @param srcImage BufferedImage reference to the source image to be affected by the filter.
    * @return A BufferedImage reference to a rotated image.
    */
    public BufferedImage CCWRotate90Degrees(BufferedImage srcImage){
        BufferedImage destImage = new BufferedImage(srcImage.getHeight(), srcImage.getWidth(), BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < srcImage.getWidth(); i++){
            for(int j = 0; j < srcImage.getHeight(); j++){
                destImage.setRGB(j, srcImage.getWidth() - 1 - i, srcImage.getRGB(i, j));
            }
        }
        return destImage;
    }
    
    public BufferedImage AdjustBrightness(BufferedImage srcImage, int bright){
       BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int y = 0; y < srcImage.getHeight(); y++){
            for(int x = 0; x < srcImage.getWidth(); x++){
                // Unpacking the data of each pixel with masks.
                int p = srcImage.getRGB(x,y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                //Adding the brightness value to each pixel
                r = r + bright;
                g = g + bright;
                b = b + bright;
                
                //Clamping in case of saturation
                r = clampColorValue(r);
                g = clampColorValue(g);
                b = clampColorValue(b);
                
                //Packing back the pixel data
                p = (a<<24) | (r<<16) | (g<<8) | b;
                destImage.setRGB(x, y, p);
            }
        }
        return destImage;
   }
    
    public BufferedImage AdjustContrast(BufferedImage srcImage, int bpp){
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        //Lookup table
        int[][] histLUT;
        //Creating the histogram
        if (bpp < 24){
            Histogram srcHist = new Histogram ();
            srcHist.setBins(srcImage, "GRAY");
            histLUT = srcHist.histLUTEqualization(srcImage, "GRAY");
        }else{
            Histogram srcHist = new Histogram ();
            srcHist.setBins(srcImage, "COLOR");
            histLUT = srcHist.histLUTEqualization(srcImage, "COLOR");
        }    
        
        
        for(int y = 0; y < srcImage.getHeight(); y++){
            for(int x = 0; x < srcImage.getWidth(); x++){
                // Unpacking the data of each pixel with masks.
                int p = srcImage.getRGB(x,y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                
                if (bpp < 24){
                    r = g = b = histLUT[0][r];
                }else{
                    r = histLUT[0][r];
                    g = histLUT[1][g];
                    b = histLUT[2][b];
                }
                
                //Packing back the pixel data
                p = (a<<24) | (r<<16) | (g<<8) | b;
                destImage.setRGB(x, y, p);             
            }
        }       
        return destImage;
    }
    
    public BufferedImage ReduceColorsBitsPerChannel(BufferedImage srcImage, int shiftAmount){
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int y = 0; y < srcImage.getHeight(); y++){
            for(int x = 0; x < srcImage.getWidth(); x++){
                // Unpacking the data of each pixel with masks.
                int p = srcImage.getRGB(x,y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                r = (r >> shiftAmount) << shiftAmount;
                g = (g >> shiftAmount) << shiftAmount;
                b = (b >> shiftAmount) << shiftAmount;

                //Packing back the pixel data
                p = (a<<24) | (r<<16) | (g<<8) | b;
                destImage.setRGB(x, y, p);             
            }
        }

        return destImage;
    }

}
