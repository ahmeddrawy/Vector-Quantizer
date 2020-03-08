package vector_quantizer;

public class myVector {
    int vHeight = 0 ;
    int vWidth = 0 ;
    int[][] vPixels;
    myVector(int h , int w , int[][] pixels ){
        vPixels = new int[h][w];
        vHeight= h;
        vWidth =w;
        for (int i = 0 ; i < vHeight ; ++i){
            for (int j =0 ; j < vWidth ; ++j)
                vPixels[i][j] = pixels[i][j];
        }
    }
    myVector(int h, int w ){
        vHeight = h ;
        vWidth = w;
        vPixels= new int[vHeight][vWidth];
        setPixelszero();

    }
    myVector(myVector a){
        vHeight= a.getvHeight();
        vWidth = a.getvWidth();
        vPixels = new int[vHeight][vWidth];
        for (int i = 0 ; i < vHeight ; ++i){
            for (int j =0 ; j < vWidth ; ++j)
                vPixels[i][j] = a.getvPixels()[i][j];
        }
    }

    public int getvHeight() {
        return vHeight;
    }
    public int getvWidth() {
        return vWidth;
    }

    public int[][] getvPixels() {
        return vPixels;
    }
    public void setPixelszero(){
        for (int i = 0 ; i < vHeight ; ++i){
            for (int j =0 ; j < vWidth ; ++j)
                vPixels[i][j] = 0;
        }
    }
    public int getPixel(int i , int j){
        return vPixels[i][j];
    }
    public void setPixel(int i , int j , int v) {
        vPixels[i][j]  = v;
    }
    public void updateVectorbyValue(int constant){
        for (int i = 0 ; i < vHeight ; ++i){
            for (int j =0 ; j < vWidth ; ++j){
                if(constant >0)
                    vPixels[i][j] = Math.min(vPixels[i][j]  , vPixels[i][j] +  constant );
                else
                    vPixels[i][j] = Math.max(vPixels[i][j]  , vPixels[i][j] +  constant );
            }
        }

    }
    public  boolean equal(myVector a){

        for (int i = 0 ; i < vHeight ; ++i){
            for (int j = 0 ; j < vWidth ; ++j){

                if(this.vPixels[i][j] != a.getPixel(i,j))
                    return false ;
            }
        }
        return  true;
    }
    //public
}
