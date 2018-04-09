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
import mv.MapViewerApp;
import static mv.MapViewerPropertyType.MV_MAP_PANE;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_SELECTED;

/**
 *
 * @author McKillaGorilla
 */
public class MapViewerData implements AppDataComponent {
    // THE APP ITSELF
    MapViewerApp app;

    // THE PANE WHERE WE'RE PUTTING ALL THE POLYGONS
    Pane map;

    // THE POLYGONS
    int subregionId;
    HashMap<Integer, ObservableList<Polygon>> subregions;
    
    // LINE THICKNESS AT SCALE 1.0
    final double DEFAULT_LINE_THICKNESS = 1.0;

    /**
     * Constructor can only be called after the workspace
     * has been initialized because it retrieves the map pane.
     */
    public MapViewerData(MapViewerApp initApp) {
        app = initApp;
        subregions = new HashMap();
        map = (Pane)app.getGUIModule().getGUINode(MV_MAP_PANE);
    }    
  
    public ObservableList<Polygon> getSubregion(int id) {
        return subregions.get(id);
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
    
    public double numOfPolyPoints(double x, double y, Pane map){
        double num = 0;
        double longx = longToX(x);
        double longy = latToY(y);
        
        return num;
    }
}