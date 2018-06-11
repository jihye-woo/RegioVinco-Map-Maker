/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.data;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Jihye
 */
public class imageContainer {
    ImageView image;
    double locationX;
    double locationY;
    String imagePath;
    String imageName;
    
    public imageContainer(Image images, String imagePath, double x, double y){
        this.image = new ImageView(images);
        this.imagePath = imagePath;
        imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
        locationX = x;
        locationY = y;
    }
      
    public void setLocationX(double x){
        image.setTranslateX(x);
        locationX = x;
    }
    public void setLocationY(double y){
        image.setTranslateX(y);
        locationY = y;
    }
    public double getLocationX(){
        return locationX;
    }
    public double getLocationY(){
        return locationY;
    } 
    public String getImageName(){
        return imageName;
    }
    public String getImagePath(){
        return imagePath;
    }
    public ImageView getImage(){
        return image;
    }
    
}
