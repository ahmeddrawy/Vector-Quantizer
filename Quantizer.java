package vector_quantizer;

import javafx.util.Pair;

import java.io.*;
import java.nio.charset.IllegalCharsetNameException;
import java.util.HashMap;
import java.util.Vector;

public class Quantizer {
    int nVectors = 0 ;
    int vHeight = 0 ;
    int vWidth = 0 ;
    int iHeight = 0 ;
    int iWidth = 0 ;
    int[][] pixels  ;
    Cluster img;

    Vector<myVector> vectorizedImg ;
    ///  todo revise
    Cluster[] codeBook;
    int bookSz = 0 ;
    Quantizer(int [][] img , int nVectors , int vHeight , int vWidth){
        this.pixels = img ;
        this.nVectors = nVectors ;
        this.vHeight = vHeight;
        this.vWidth = vWidth;
        this.iHeight = img.length;
        this.iWidth = img[0].length;
        this.vectorizedImg = new Vector<>();
        codeBook = new Cluster[nVectors];
    }
    Quantizer(){
        /// redunant
    }
    void run(String path ){

        NormalizeImage();
        buildQunatizerBook();
        finish(12);
        writetofile(path);


    }
    private void writetofile(String path ){
        /// todo write code book here
//        PrintStream p = null;
        DataOutputStream p = null;
        System.out.println("1");
        try {
            p = new DataOutputStream(new FileOutputStream(path) );
            p.writeInt(nVectors);
            p.writeInt(vHeight);
            p.writeInt(vWidth);
            p.writeInt(iHeight);
            p.writeInt(iWidth);
            System.out.println("2");
            for (int k = 0 ; k < bookSz ; ++k){
                codeBook[k].getMean().getvPixels();
                for (int i = 0 ; i < vHeight; ++i){
                    /// write to file
                    /// todo write this to file codeBook[k].getMean()[i][j]; /// this is one pixel of the code book
                    for (int j = 0 ; j < vWidth; ++j) {
                        p.writeInt(codeBook[k].getMean().getPixel(i , j));
                    }
                }

            }
            System.out.println("3");
            for (int i = 0 ; i <vectorizedImg.size() ; ++i) {
                int indx = -1;
                for (int j = 0; j < codeBook.length; ++j) { /// sequential search
                    if (codeBook[j].hasVector(vectorizedImg.elementAt(i))) {
                        indx = j;
                        break;
                    }
                }/// todo write i , indx
                p.writeInt(i);
                p.writeInt(indx);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finished");

    }
    private void readFromFile(String path ){

        /// read from file the data of codebook
        /// we need to read
        //          original size of image width and height
        ///         vector size
        ///         number of vectors


        /// todo read vheight, vwidth , number of vectors,
        if(!new File(path).canRead()){
            System.out.println("cant read");
            return ;
        }
        try {
            DataInputStream f = new DataInputStream(new FileInputStream(path));

            try {
                nVectors =  f.readInt();
                vHeight = f.readInt();
                vWidth = f.readInt();
                iHeight = f.readInt();
                iWidth= f.readInt();
                codeBook = new Cluster[nVectors];
                for(int i = 0 ; i < nVectors ; ++i){
                    int [][] temp = new int[vHeight][vWidth];
                    for (int j = 0 ; j < vHeight ; ++j){
                        for (int k = 0 ; k < vWidth ; ++k){
                            /// cin >> temp[j][k]
                            temp[j][k] = f.readInt();
                        }
                    }

                    codeBook[i] = new Cluster(new myVector(vHeight,vWidth , temp) , vHeight,vWidth);

                }

                int vecSZ = (iHeight*iWidth) / (vWidth*vHeight); /// read this , image converted to vectors you can get these from image coordinates h*w/(vh*vw)
                HashMap<Integer,Pair<Integer , Integer > > coord = getCoordinates();
                pixels = new int[iHeight ] [ iWidth];
                for (int i = 0 ; i <vecSZ ; ++i) {
                    /// read  indx1 , indx2
                    int indx1=0 ,indx2 = 0  ; /// todo read those
                    indx1 = f.readInt();
                    indx2 = f.readInt();
                    Pair<Integer , Integer> p = coord.get(indx1);
                    for (int ii = 0 ; ii < vHeight; ++ii){

                        for (int jj =0 ; jj < vWidth ; ++jj){
                            int b = codeBook[indx2].getMean().getPixel(ii,jj);
                            b= (b<<8) | b;
                            b = (b<<16) | b;

                            pixels[ii+p.getKey()][jj+p.getValue()] =b;
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /// read code book



    }

    void decompresse(String path , String name){
        readFromFile(path);
        writedImage(path , name);

    }


    private void  NormalizeImage(){ /// take the image and convert it to a vector of 1D vectos
        HashMap<Pair<Integer , Integer >,Integer > indices = getIndices() ;
        int[][][] normalizedImg = new int[indices.size()][vHeight][vWidth];
        for (int i = 0 ; i < iHeight ; i+=vHeight ){ /// moving by vector size iHeight
            for (int j = 0 ; j < iWidth ; j+=vWidth){
                for (int ii = 0 ; ii < vHeight ; ii++ ){
                    for (int jj = 0 ; jj  < vWidth ; jj++){
                        int indx = indices.get(new Pair<>(i , j));
                        normalizedImg[indx][ii][jj] = pixels[i+ii][j+jj];

                    }
                }

            }

        }

        for (int i = 0 ; i < indices.size() ; ++i){
            myVector v= new myVector(vHeight,vWidth , normalizedImg[i]);

            vectorizedImg.add(v);
        }
        img = new Cluster(vectorizedImg, vHeight ,vWidth);



        return ;   /// return array of vectors [indx of vector][i][j]
    }
    private HashMap<Pair<Integer , Integer >,Integer >  getIndices() {
        int cnt = 0 ;
        HashMap<Pair<Integer , Integer >,Integer > ret = new HashMap<Pair<Integer , Integer >,Integer >();
        for (int i = 0; i < iHeight; i += vHeight) { /// moving by vector size height
            for (int j = 0; j < iWidth; j += vWidth) {
                ret.put(new Pair<Integer, Integer>(i, j), cnt++);

            }

        }
        return ret;
    }
    private HashMap<Integer,  Pair<Integer , Integer > >  getCoordinates() {
        int cnt = 0 ;
        HashMap<Integer,Pair<Integer , Integer > > ret = new HashMap<Integer,Pair<Integer , Integer > >();
        for (int i = 0; i < iHeight; i += vHeight) { /// moving by vector size height
            for (int j = 0; j < iWidth; j += vWidth) {
                ret.put( cnt++, new Pair<Integer, Integer>(i, j));

            }

        }
        return ret;
    }

    private void buildQunatizerBook(){
        codeBook[0] = img;
        bookSz=1  ;
        while(bookSz < nVectors){
            int n = bookSz;
            for (int i = 0 ; i < n ; ++i){
                /// split and add 2 clusters one at i and one after n
                Vector<Cluster> vec= codeBook[i].splitCluster();
                codeBook[i] = vec.elementAt(0);
                codeBook[i+n] = vec.elementAt(1);

            }

            /// todo do i have to check if the
            bookSz*=2;
            putinClusters();


        }

    }
    private void putinClusters(){
        for(int i = 0 ; i <bookSz; ++i){
            codeBook[i].clear();
        }

        for (myVector v : vectorizedImg){
            long   mn = Long.MAX_VALUE ;
            int i = 0;
            for (int j= 0 ; j < bookSz;  ++j){
                long  d = codeBook[j].getDistance(v);
                if(d < mn ){
                    i = j;
                    mn = d;
                }

            }
            codeBook[i].addVector(v);

        }
        for(int i = 0 ; i <bookSz; ++i){
            codeBook[i].initMean();
        }

    }

    private void finish(int n ){
        for (int i = 0 ; i < n ; ++i){
            for (Cluster c: codeBook) {
                c.updateMean();
                c.clear();
            }
            putinClusters();

        }
        // result();
    }
    private void result(){
        HashMap<Integer,Pair<Integer , Integer > > coord = getCoordinates();

//        int [][]ret = new int[iHeight][iWidth];
        for (int i = 0 ; i <vectorizedImg.size() ; ++i){

            Pair<Integer , Integer> p = coord.get(i);
            int indx= -1 ;
            for (int j = 0 ; j < codeBook.length; ++j){ /// sequential search
                if(codeBook[j].hasVector(vectorizedImg.elementAt(i))){
                    indx= j;
                    break;
                }
            }
            //System.out.println("codebook "+ codeBook.length + " sz " + bookSz +" indx "+ indx + " pair "+ p.getKey() + " " + p.getValue());
            for (int ii = 0 ; ii < vHeight; ++ii){

                for (int jj =0 ; jj < vWidth ; ++jj){
                    int b = codeBook[indx].getMean().getPixel(ii,jj);
                    b= (b<<8) | b;
                    b = (b<<16) | b;

                    pixels[ii+p.getKey()][jj+p.getValue()] =b;
                }
            }
        }
        //    ImgControl i = new ImgControl();
        //  i.writeImg(ret , "/home/tw3/Desktop/college/3 year/multimedia/vector_quantization for image/tst2/" , "finish000");


    }
    private   void writedImage(String path, String name){
        ImgControl i = new ImgControl();
        i.writeImg(pixels , path , name);
    }
}
