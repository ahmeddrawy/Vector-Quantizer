package vector_quantizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImgControl {



    ImgControl(){

    }

    public int[][] getPixels(String filePath )  {
        File f= new File(filePath);
        int [][] Pixels = null ;
        BufferedImage img  ;
        try {
            img = ImageIO.read(f);
            int width = img.getWidth();
            int height = img.getHeight();
            Pixels = new int[height][width];
            for (int i = 0; i < width; ++i)
                for (int j = 0; j < height; ++j) {
                    int rgb = img.getRGB(i, j);
                    int blue = rgb & 0xff;
                    int v = blue ;
                    //int msk = 0;
                    //blue = (blue <<8) | blue ;
                    //blue = (blue<<16) | blue ;

                    Pixels[j][i] = blue;  /// i , j reversed


                }

        }
        catch (IOException e){
            e.printStackTrace();
        }


        return Pixels;
    }
    public void writeImg(int[][] Pixels, String path, String filePath){
        int height = Pixels.length;
        int width = Pixels[0].length;
        BufferedImage img  = new BufferedImage(width , height ,BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; ++i)
            for (int j = 0; j < height; ++j) {
                img.setRGB(i , j , Pixels[j][i]);
                //            Pixels[j][i] = rgb & 0xffffff;  /// i , j reversed


            }
        File f = new File(filePath);
        try {
            ImageIO.write(img , "png" , f);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
