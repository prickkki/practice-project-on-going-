package ro.simavi.aidbackend.utils;

public class ImageDataUtil {
    public static final double SIZE_IMAGE = 6;
    public static final int DIMENSIONX_IMAGE=1000;
    public static final int DIMENSIONY_IMAGE=1000;

    public boolean sizeImageVerification(double size){
        boolean validation = true;
        if(size > SIZE_IMAGE){
            validation = false;
        }
        return validation;
    }

    public boolean dimensionImageVerification(int dimensionX, int dimensionY){
        boolean validation = true;
        if((dimensionX > DIMENSIONX_IMAGE) || (dimensionY >= DIMENSIONY_IMAGE)){
            validation = false;
        }
        return validation;
    }
}
