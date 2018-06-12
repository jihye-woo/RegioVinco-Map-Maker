/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.data;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author Jihye
 */
public class SubRegionInfo {
    String nameOfSubrgion;
    Color color;
    boolean haveCapital;
    boolean haveFlags;
    boolean haveLeaders;
    
    public SubRegionInfo(Color color){
//        this.polygonID = polygonID;
        this.color = color;
        haveCapital = false;
        haveFlags = false;
        haveLeaders = false;
    }
    // getter
    public boolean getHaveCapital(){
        return haveCapital;
    }
    public boolean getHaveflags(){
        return haveFlags;
    }
    public boolean getHaveLeaders(){
        return haveLeaders;
    }
    public String nameOfSubregion(){
        return nameOfSubrgion;
    }
    
    
    // setter 
    public void setSubregionName(String nameOfSubrgion){
        nameOfSubrgion= nameOfSubrgion;
    }
    public void setHaveCapital(boolean haveCapital){
        this.haveCapital = haveCapital;
    }
    public void setHaveflags(boolean haveFlags){
        this.haveFlags = haveFlags;
    }
    public void setHaveLeaders(boolean haveLeaders){
        this.haveLeaders = haveLeaders;
    }
}
