/*package vector_quantizer;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
	// write your code here

        ImgControl i = new ImgControl();
        int [][] pix = i.getPixels("C:/Users/lamya/Desktop/PowerPuff.png");
        // int pix[][] =new int[6][6];
        /*  pix[0][0]= 1;
        pix[0][1]= 2;
        pix[0][2]= 7;
        pix[0][3]= 9;
        pix[0][4]= 4;
        pix[0][5]= 11;
        pix[1][0]= 3;
        pix[1][1]= 4;
        pix[1][2]= 6;
        pix[1][3]= 6;
        pix[1][4]= 12;
        pix[1][5]= 12;

        pix[2][0]= 4;
        pix[2][1]= 9;
        pix[2][2]= 15;
        pix[2][3]= 14;
        pix[2][4]= 9;
        pix[2][5]= 9;

        pix[3][0]= 10;
        pix[3][1]= 10;
        pix[3][2]= 20;
        pix[3][3]= 18;
        pix[3][4]= 8;
        pix[3][5]= 8;

        pix[4][0]= 4;
        pix[4][1]= 3;
        pix[4][2]= 17;
        pix[4][3]= 16;
        pix[4][4]= 1;
        pix[4][5]= 4;

        pix[5][0]= 4;
        pix[5][1]= 5;
        pix[5][2]= 18;
        pix[5][3]= 18;
        pix[5][4]= 5;
        pix[5][5]= 6;*/

        /*int height = pix.length;
        int width = pix[0].length;
        int g= findGCD(height, width);
        System.out.println( "user has to provide vector size with this ratio (Height : width) : " + height/g + " : " + width/g );
        System.out.println( "user has to provide vector size with this ratio (Height : width) : " + height + " : " + width);
        System.out.println("user has to provide number of vectors which is power of 2 e.q 2 , 4 , 8 , 16");

        /// todo test the check functions
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int h = sc.nextInt();
        int w= sc.nextInt();
        if(checkInputsize(height, width , h , w) && checkVectorSize(x , height, width , h,w)) {
            System.out.println("hey ");
            Quantizer q = new Quantizer(pix, x, h, w);
            q.run();
        }
        else {
            System.out.println("Bye");
        }
//        i.writeImg(pix , "/home/tw3/Desktop/college/3 year/multimedia/vector_quantization for image/tst2/", "02");
       // Vector<Integer > v = new Vector<Integer>();
      //  v.add(0);
      //  getMean(nimg ,v , h,w);

    }

    private static boolean  checkInputsize(int height1  , int width1 , int height2 , int width2){ /// todo  check if old width is divisible by vector width , same for height , independently
        return height1%height2 ==0 && width1%width2 == 0 && height1>= height2 && width1 >=width2 ;
    }
    private  static boolean checkVectorSize(int n ,int h , int w , int vh , int vw){ /// todo check if number of vectors with the dimensions provided smaller than image
        if(n<1) /// todo add erros msgs
            return false;
        int c= (int)log2(n) ;   /// rounding down the c
        return (1<<c) == n && vw*vh*n <= h*w;    /// checking 2^c == n , to check if n is power of 2 and n*size of vector <= size of image

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

}*/
