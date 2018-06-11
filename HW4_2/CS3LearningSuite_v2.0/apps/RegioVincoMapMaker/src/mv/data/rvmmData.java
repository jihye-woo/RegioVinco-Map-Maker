package mv.data;

import djf.components.AppDataComponent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javax.imageio.ImageIO;
import mv.RegioVincoMapMakerApp;
import static mv.MapMakerPropertyType.MV_MAP_PANE;
import static mv.MapMakerPropertyType.RVMM_LEFT_MAP;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP;
import static mv.workspace.style.MapViewerStyle.CLASS_RVMM_SELECTEDIMAGE;

/**
 *
 * @author McKillaGorilla
 */
public class rvmmData implements AppDataComponent {
    // THE APP ITSELF
    RegioVincoMapMakerApp app;
    
    String filePath;
    
    double locationX; double locationY;
    // THE PANE WHERE WE'RE PUTTING ALL THE POLYGONS
    Pane map; Pane leftArea;
    // THE POLYGONS
    int subregionId;
    HashMap<Integer, ObservableList<Polygon>> subregions;
    // LINE THICKNESS AT SCALE 1.0
    final double DEFAULT_LINE_THICKNESS = 0.05;
    
    // for save
    boolean haveCapital=true;
    boolean haveflags=true;
    boolean haveleaders=true;
    HashMap<String, Color> subRegionToColorMappings;
    ArrayList<imageContainer> images;
    ImageView selectedImage;
    
    /**
     * Constructor can only be called after the workspace
     * has been initialized because it retrieves the map pane.
     */
    public rvmmData(RegioVincoMapMakerApp initApp) {
        app = initApp;
        subregions = new HashMap();
        map = (Pane) app.getGUIModule().getGUINode(MV_MAP_PANE);
        images = new ArrayList<imageContainer>();
    }
    public RegioVincoMapMakerApp getApp(){
        return app;
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
    public Pane getMap(){
        return map;
    }
    public HashMap<String, Color> getSubRegionToColorMappings(){
        return subRegionToColorMappings;
    }
    public void setFilePath(String currentFilePath){
        filePath = currentFilePath;
    }
    public String getFilePath(){
        return filePath;
    }
    
    
    @Override
    public void reset() {
        // CLEAR THE DATA
        subregions.clear();
        subregionId = 0;
        
        // AND THE POLYGONS THEMSELVES
        leftArea = (Pane) map.getParent();
        map = (Pane) leftArea.getChildren().get(0);
        Rectangle ocean = (Rectangle)map.getChildren().get(0);
        leftArea.getChildren().clear();
        leftArea.getChildren().add(map);
        map.getChildren().clear();
        map.getChildren().add(ocean);
       
        images.clear();
//        subRegionToColorMappings.clear();
//        String regionName = "";
//        boolean haveCapital= false;
//        boolean haveflags= false;
//        boolean haveleaders= false;
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
    
    public void addImage(String imagePath, double x, double y){
        BufferedImage bimage;
        File file = new File(imagePath);
        imageContainer imagecon;
        try {
            bimage = ImageIO.read(file);
            ImageIO.write(bimage, "png", file);
            Image image = SwingFXUtils.toFXImage(bimage, null);
            ImageView iv = new ImageView(image);
            iv.setTranslateX(x);
            iv.setTranslateY(y);
            leftArea = (Pane) map.getParent();
            leftArea.getChildren().add(iv);
            imagecon= new imageContainer(image, imagePath, x, y);
            images.add(imagecon);
            iv.setOnMousePressed(e1->{
                iv.setCursor(Cursor.HAND);
                locationX = e1.getX();
                locationY = e1.getY();
                iv.setOnMouseExited(e3->{
                    iv.setCursor(Cursor.DEFAULT);
                });
            });
            iv.setOnMouseDragged(e2->{
                double deltaX = e2.getX()-locationX;
                double deltaY = e2.getY()-locationY;
                double movedlocationX = iv.getTranslateX()+deltaX;
                double movedlocationY = iv.getTranslateY()+deltaY;
                iv.setTranslateX(movedlocationX);
                iv.setTranslateY(movedlocationY);
                imagecon.setLocationX(x);
                imagecon.setLocationY(y);
            });
            iv.setOnMouseClicked(e3->{
                selectedImage(iv);
            });
        } catch (IOException ex) {
            Logger.getLogger(rvmmData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void removeImage(imageContainer image){
        images.remove(image);
    }
    
    public ArrayList<imageContainer> getImages(){
        return images;
    }
    
    public void selectedImage(ImageView currentImage){
        if(selectedImage!= null){
            selectedImage.setScaleX(selectedImage.getScaleX()-0.05);
            selectedImage.setScaleY(selectedImage.getScaleY()-0.05);
        }
        selectedImage = currentImage;
        
        currentImage.setScaleX(currentImage.getScaleX()+0.05);
        currentImage.setScaleY(currentImage.getScaleY()+0.05);
        
    }
    
    
}