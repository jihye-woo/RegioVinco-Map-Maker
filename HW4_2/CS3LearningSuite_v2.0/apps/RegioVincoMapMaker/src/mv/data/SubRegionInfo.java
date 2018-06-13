/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author Jihye
 */
public class SubRegionInfo {
    SimpleStringProperty Subregion = new SimpleStringProperty();
    SimpleStringProperty Capital = new SimpleStringProperty();
    SimpleStringProperty Leader = new SimpleStringProperty();
    String subregionName;
//    Color color;
//    boolean haveCapital = false;
//    boolean haveFlags = false;
//    boolean haveLeaders = false;
    
    public SubRegionInfo(String Subregion, String Capital, String Leader){
//        this.polygonID = polygonID;
//        this.color = color;
        this.Subregion.set(Subregion);
        this.Capital.set(Capital);
        this.Leader.set(Leader);
        subregionName = Subregion;
    }
    // getter
   
    public String getSubregion(){
        return Subregion.get();
    }
    public String getCapital(){
        return Capital.get();
    }
    public String getLeader(){
        return Leader.get();
    }
//    public boolean getHaveCapital(){
//        return haveCapital;
//    }
//    public boolean getHaveflags(){
//        return haveFlags;
//    }
//    public boolean getHaveLeaders(){
//        return haveLeaders;
//    }
    
    // setter 
  
    public void setSubregion(String Subregion){
        this.Subregion.set(Subregion);
    }
    public void setCapital(String Capital){
        this.Capital.set(Capital);
    }
    public void setLeader(String Leader){
        this.Leader.set(Leader);
    }
    
//    public void setHaveCapital(boolean haveCapital){
//        this.haveCapital = haveCapital;
//    }
//    public void setHaveflags(boolean haveFlags){
//        this.haveFlags = haveFlags;
//    }
//    public void setHaveLeaders(boolean haveLeaders){
//        this.haveLeaders = haveLeaders;
//    }
}
