package vector_quantizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QuantGUI {
    private JPanel panel1;
    private JTextField CompImgPath;
    private JButton compressButton;
    private JTextField DecompPath;
    private JButton decompressButton;
    private JTextField DecompFileName;
    private JTextField CompPath;
    private JTextField vWidth;
    private JTextField vHeight;
    private JTextField nVector;
    private BufferedImage compressedImage;
    // private File CompressFile;
   // private File DecompFile;

    public QuantGUI() {


        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = Integer.parseInt(nVector.getText());
                int h = Integer.parseInt(vHeight.getText());
                int w = Integer.parseInt(vWidth.getText());
                String Image = CompImgPath.getText();
                String CompPa = CompPath.getText();
                //CompressFile = new File(CompPath);
                //String path = CompressFile.getAbsolutePath();

                ImgControl i = new ImgControl();
                //int [][] pix = i.getPixels(path);
                int [][] pix = i.getPixels(Image);

                int height = pix.length;
                int width = pix[0].length;
                int g= findGCD(height, width);
                if(checkInputsize(height, width , h , w) && checkVectorSize(n , height, width , h,w)) {
                    Quantizer q= new Quantizer (pix, n, h, w);
                    q.run(CompPa);
                }
                else {
                    System.out.printf("hey ");
                }
            }
        });

        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Decpath = DecompPath.getText();
                String Name = DecompFileName.getText();
                Quantizer q = new Quantizer();
                q.decompresse(Decpath, Name);
                try {
                    compressedImage = ImageIO.read(new File(Name));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Vector Quantization");
        jFrame.setContentPane(new QuantGUI().panel1);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private static boolean  checkInputsize(int height1  , int width1 , int height2 , int width2){
        return height1%height2 ==0 && width1%width2 == 0 && height1>= height2 && width1 >=width2 ;
    }

    private  static boolean checkVectorSize(int n ,int h , int w , int vh , int vw){
        if(n<1)
            return false;
        int c= (int)log2(n);
        return (1<<c) == n && vw*vh*n <= h*w;

    }

    public static double log2(int n ){
        return Math.log(n)/Math.log(2);
    }

    private static int findGCD(int number1, int number2) {
        //base case
        if(number2 == 0){
            return number1;
        }
        return findGCD(number2, number1%number2);
    }
}
