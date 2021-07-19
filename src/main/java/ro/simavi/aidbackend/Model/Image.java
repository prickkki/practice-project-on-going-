package ro.simavi.aidbackend.Model;

public class Image {

    private String imageName;
    private double size;
    private int dimensionX;
    private int dimensionY;

    public String getImageName() {
        return this.imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public double getSize() {
        return this.size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getDimensionX() {
        return this.dimensionX;
    }

    public void setDimensionX(int dimensionX) {
        this.dimensionX = dimensionX;
    }

    public int getDimensionY() {
        return this.dimensionY;
    }

    public void setDimensionY(int dimensionY) {
        this.dimensionY = dimensionY;
    }

    public Image(String imageName, double size, int dimensionX, int dimensionY) {
        this.imageName = imageName;
        this.size = size;
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
    }
    public Image(){

    }
    public Image(double size){
        this.size = size;
    }
    public Image(int dimensionX, int dimensionY) {
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
    }
}
