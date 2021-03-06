package mv.data;

import djf.components.AppDataComponent;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import mv.RegioVincoMapMakerApp;
import static mv.MapMakerPropertyType.MV_MAP_PANE;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_SELECTED;

/**
 *
 * @author McKillaGorilla
 */
public class rvmmData implements AppDataComponent {
    // THE APP ITSELF
    RegioVincoMapMakerApp app;

    // THE PANE WHERE WE'RE PUTTING ALL THE POLYGONS
    Pane map;
    // THE POLYGONS
    int subregionId;
    HashMap<Integer, ObservableList<Polygon>> subregions;
    
    // LINE THICKNESS AT SCALE 1.0
    final double DEFAULT_LINE_THICKNESS = 0.1;

    // for save
    String regionName;
    boolean haveCapital;
    boolean haveflags;
    boolean haveleaders;
    HashMap<String, Color> subRegionToColorMappings;
    
    /**
     * Constructor can only be called after the workspace
     * has been initialized because it retrieves the map pane.
     */
    public rvmmData(RegioVincoMapMakerApp initApp) {
        app = initApp;
        subregions = new HashMap();
        map = (Pane)app.getGUIModule().getGUINode(MV_MAP_PANE);
    }
  
    public String getRegionName(){
        return regionName;
    }
    public boolean getHaveCapital(){
        return haveCapital;
    }
    public boolean getHaveflags(){
        return haveflags;
    }
    public boolean getHaveLeaders(){
        return haveleaders;
    }
    public ObservableList<Polygon> getSubregion(int id) {
        return subregions.get(id);
    }
    public int numOfSubregion(){
        return subregions.size();
    }
//    public int getNumOfImages(){
//    }
    public Pane getMap(){
        return map;
    }
    public HashMap<String, Color> getSubRegionToColorMappings(){
        return subRegionToColorMappings;
    }
    public RegioVincoMapMakerApp getApp(){
        return app;
    }
    
    @Override
    public void reset() {
        // CLEAR THE DATA
        subregions.clear();
        subregionId = 0;
        
        // AND THE POLYGONS THEMSELVES
        Rectangle ocean = (Rectangle)map.getChildren().get(0);
        map.getChildren().clear();
        map.getChildren().add(ocean);
        
//        subRegionToColorMappings.clear();
//        String regionName = "";
//        boolean haveCapital= false; 
//        boolean haveflags= false; 
//        boolean haveleaders= false; 
//        
    }
    
    public void setRegionName(String newName){
        regionName = newName;
    }
    
    /**
     * For adding polygons to the map.
    */
    public void addSubregion(ArrayList<ArrayList<Double>> rawPolygons) {
        ObservableList<Polygon> subregionPolygons = FXCollections.observableArrayList();
        for (int i = 0; i < rawPolygons.size(); i++) {
            ArrayList<Double> rawPolygonPoints = rawPolygons.get(i);
            Polygon polygonToAdd = new Polygon();
            ObservableList<Double> transformedPolygonPoints = polygonToAdd.getPoints();
            for (int j = 0; j < rawPolygonPoints.size(); j+=2) {
                double longX = rawPolygonPoints.get(j);
                double latY = rawPolygonPoints.get(j+1);
                double x = longToX(longX);
                double y = latToY(latY);
                transformedPolygonPoints.addAll(x, y);
            }
            subregionPolygons.add(polygonToAdd);
            polygonToAdd.getStyleClass().add(CLASS_MV_MAP);
            polygonToAdd.setStroke(Color.BLACK);
            polygonToAdd.setStrokeWidth(DEFAULT_LINE_THICKNESS);
            polygonToAdd.setUserData(subregionId);
            map.getChildren().add(polygonToAdd);
        }
        subregions.put(subregionId, subregionPolygons);
        subregionId++;
    }

    /**
     * This calculates and returns the x pixel value that corresponds to the
     * xCoord longitude argument.
     */
    private double longToX(double longCoord) {
        double paneHeight = map.getHeight();
        double unitDegree = paneHeight/180;
        double paneWidth = map.getWidth();
        double newLongCoord = (longCoord + 180) * unitDegree;
        return newLongCoord;
    }

    /**
     * This calculates and returns the y pixel value that corresponds to the
     * yCoord latitude argument.
     */
    private double latToY(double latCoord) {
        // DEFAULT WILL SCALE TO THE HEIGHT OF THE MAP PANE
        double paneHeight = map.getHeight();
        
        // WE ONLY WANT POSITIVE COORDINATES, SO SHIFT BY 90
        double unitDegree = paneHeight/180;
        double newLatCoord = (latCoord + 90) * unitDegree;
        return paneHeight - newLatCoord;
    }
    
}