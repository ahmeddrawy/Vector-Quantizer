package vector_quantizer;

import java.util.Vector;

public class Cluster {
    myVector Mean = null ;
    Vector<myVector> clustered = new Vector<>() ;
    int vHeight =0 ;
    int vWidth = 0 ;
    Cluster(myVector v ,int h , int w){
        Mean = v;
        vHeight = h;
        vWidth = w;

    }
    Cluster(Vector<myVector> cl ,int h , int w){
        clustered = new Vector<>(cl);
        vHeight = h;
        vWidth = w;

        Mean = initMean();
    }
    Cluster(){
        clustered = new Vector<>();

    }

    public myVector initMean() {
        myVector ret= new myVector(vHeight , vWidth);

        for (int j = 0; j < ret.vHeight; j++) {
            for (int k = 0; k < ret.vWidth; k++) {
                for (int i = 0; i < clustered.size(); ++i) {
                    ret.setPixel(j,k ,ret.getPixel(j,k)  + clustered.elementAt(i).getPixel(j,k));
                }
                int rPixel =(int) Math.round( (double) ret.getPixel(j,k) / clustered.size());
                ret.setPixel(j,k ,rPixel );
            }
        }
        return ret;
    }
    public void updateMean(){
        this.Mean = initMean();
    }
    public long getDistance(myVector v){ /// todo check if overflow

        long ret= 0 ;
        for (int i = 0 ; i < Mean.getvHeight() ; ++i){

            for (int j= 0 ; j < Mean.getvWidth() ; ++j){
                ret+=(v.getPixel(i,j) - Mean.getPixel(i,j)) *(v.getPixel(i,j) - Mean.getPixel(i,j));

            }
        }
        return (long )Math.round(Math.sqrt(ret));
    }
    public void clear(){
        if(clustered!= null)
            clustered.clear();
    }
    public void addVector(myVector v){
        clustered.add(v);
    }

    public Vector<Cluster> splitCluster(){
        Vector<Cluster> ret = new Vector<>();
        int c= 0 ;
        for (int i =0 ; i < 2;  ++i){
            if(i == 0 ) c = 1 ;
            else c = -1;
            myVector v = new myVector(Mean.getvHeight(),Mean.getvWidth() , Mean.getvPixels());
            v.updateVectorbyValue(c);

            ret.add(new Cluster(v, v.getvHeight() , v.getvWidth()));
        }

        return ret;

    }
    public boolean hasVector(myVector a){
        for(myVector v : clustered){
            if(v.equal(a))
                return true ;
        }
        return false ;
    }

    public myVector getMean() {
        return Mean;
    }
}
