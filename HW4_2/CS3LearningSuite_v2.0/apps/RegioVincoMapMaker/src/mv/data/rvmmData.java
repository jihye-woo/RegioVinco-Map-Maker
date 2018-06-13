package mv.data;

import djf.components.AppDataComponent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javax.imageio.ImageIO;
import mv.RegioVincoMapMakerApp;
import static mv.MapMakerPropertyType.MV_MAP_PANE;
import mv.workspace.rvmmDialogController;
import mv.workspace.rvmmWorkspace;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP;

/**
 *
 * @author McKillaGorilla
 */
public class rvmmData implements AppDataComponent {
    // THE APP ITSELF
    RegioVincoMapMakerApp app;
    
    String filePath;
    double locationX; double locationY;
    // THE PANE 
    Pane map; Pane leftArea;
    // THE POLYGONS
    int subregionId;
    HashMap<Integer, ObservableList<Polygon>> subregions; // id
    ObservableList<SubRegionInfo> infoRegions; //id
//    HashMap<String, Integer> colorCodeGetter; //name -> color
    // Color and Thickness
    ColorAndThicknessInfo colorController;
    // Images
    ArrayList<ImageInfo> images;
    ImageView selectedImage;
    int selectedPolygonID;
    /**
     * Constructor can only be called after the workspace
     * has been initialized because it retrieves the map pane.
     */
    public rvmmData(RegioVincoMapMakerApp initApp) {
        app = initApp;
        subregions = new HashMap();
        map = (Pane) app.getGUIModule().getGUINode(MV_MAP_PANE);
        images = new ArrayList<ImageInfo>();
        colorController = new ColorAndThicknessInfo();
        infoRegions = FXCollections.observableArrayList();
//        colorCodeGetter = new HashMap<String, Integer>();
        selectedPolygonID = -1;
    }
//    public void setcolorCodeGetter(String subRegionName, int colorCode){
//        colorCodeGetter.put(subRegionName, colorCode);
//    }
    public ObservableList<SubRegionInfo> getSubRegionInfo(){
        return infoRegions;
    }
    public SubRegionInfo getEachSubRegionsInfo(int id){
        return infoRegions.get(id);
    }
    public RegioVincoMapMakerApp getApp(){
        return app;
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
    public void setFilePath(String currentFilePath){
        filePath = currentFilePath;
    }
    public String getFilePath(){
        return filePath;
    }
    public ColorAndThicknessInfo getColorController(){
        return colorController;
    }
    // current polygon Id Selection
    public void polygonSelecting(int selectedPolygon){
        Polygon p;
        if(selectedPolygon < 0){
            if(selectedPolygonID >= 0){
                p = getSubregion(selectedPolygonID).get(0);
                int colorCode = getEachSubRegionsInfo((int)p.getUserData()).getColor();
                p.getStyleClass().add(CLASS_MV_MAP);
                p.setFill(Color.grayRgb(colorCode));
                selectedPolygonID = -1;
            }
        }
        else if(selectedPolygon != selectedPolygonID){
            if(selectedPolygonID != -1){
            p = getSubregion(selectedPolygonID).get(0);
            int colorCode = getEachSubRegionsInfo((int)p.getUserData()).getColor();
              String GREY_STYLE ="-fx-fill: rgb("+colorCode+","+colorCode+","+colorCode+")";
              p.getStyleClass().add(CLASS_MV_MAP);
              p.setFill(Color.grayRgb(colorCode));
            }
            selectedPolygonID = selectedPolygon;
            p = getSubregion(selectedPolygonID).get(0);
//            String clickedStyle = "-fx-fill:  radial-gradient(radius 180%,  red , derive(red, -30%), derive(red, 30%));";
            p.getStyleClass().clear();
            p.setFill(Color.RED);
        }
        
        if(selectedPolygonID >=0){
            rvmmWorkspace workspace = (rvmmWorkspace) app.getWorkspaceComponent();
            workspace.getTable().getSelectionModel().select(subregionId);
        }
    }
    
    public int currentSelectedPolygon(){
        return selectedPolygonID;
    }
    public void swapHashMapValue(int currentKey, int targetKey){
        ObservableList<Polygon> instantValue = getSubregion(targetKey);
        subregions.replace(targetKey, subregions.get(currentKey));
        subregions.replace(currentKey, instantValue);
        polygonSelecting(targetKey);
    }
    @Override
    public void reset() {
        // CLEAR THE DATA
        subregions.clear();
        infoRegions.clear();
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
    }
    
    /**
     * For adding polygons to the map.
     */
    public void addSubregion(ArrayList<ArrayList<Double>> rawPolygons,String subRegionName, String SubregionCaptial, String SubregionLeader, int colorCode) {
        ObservableList<Polygon> subregionPolygons = FXCollections.observableArrayList();
        ArrayList<Integer> rgbNumList = randomNumGenertator(rawPolygons.size());
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
            polygonToAdd.setFill(Color.grayRgb(colorCode));
            polygonToAdd.getStyleClass().add(CLASS_MV_MAP);
            polygonToAdd.strokeProperty().bind(colorController.LINE_COLOR);
            polygonToAdd.strokeWidthProperty().bind(colorController.LINE_THICKNESS);
            polygonToAdd.setUserData(subregionId);
            map.getChildren().add(polygonToAdd);
            polygonToAdd.setOnMouseClicked(e->{
                if(e.getButton().equals(MouseButton.PRIMARY)){
                    int id = (int)polygonToAdd.getUserData();
                    polygonSelecting(id);
                    rvmmWorkspace workspace = (rvmmWorkspace) app.getWorkspaceComponent();
                    workspace.getTable().getSelectionModel().select(id);
                    if(e.getClickCount() ==2){
                        rvmmDialogController controller = new rvmmDialogController(app);
                        controller.processEditSubregion((int)polygonToAdd.getUserData());
                    }
                }
            });
        }
        SubRegionInfo info = new SubRegionInfo(subRegionName, SubregionCaptial, SubregionLeader);
        info.setColor(colorCode);
        infoRegions.add(subregionId, info);
        subregions.put(subregionId, subregionPolygons);
        subregionId++;
    }
    
    private ArrayList<Integer> randomNumGenertator(int sizeOfSubregions){
        ArrayList<Integer> ranN = new ArrayList<Integer>();
        Random r = new Random();
        while(ranN.size() < sizeOfSubregions){
            int rannum = r.nextInt(254)+1;
            if(!ranN.contains(rannum)){
                ranN.add(rannum);
            }
        }
        return ranN;
    }
    public void randomizePainter(){
        ArrayList<Integer> ranN = randomNumGenertator(subregions.size());
        for(int i=0;i<subregions.size(); i++){
            setColorAndHover(getSubregion(i).get(0), ranN.get(i));
        }
    }
    public void setColorAndHover(Polygon p, int rgbCode){
        String subregionName = getEachSubRegionsInfo((int)p.getUserData()).getSubregion();
        infoRegions.get((int)p.getUserData()).setColor(rgbCode);
        p.setFill(Color.grayRgb(rgbCode));
//        String GREY_STYLE ="-fx-fill: rgb("+rgbCode+","+rgbCode+","+rgbCode+")";
//            String HOVERED_STYLE = "-fx-fill:  radial-gradient(radius 180%,  blue , derive(blue, -30%), derive(blue, 30%));";
//            p.styleProperty()
//                    .bind(Bindings.when(p.hoverProperty())
//                            .then(new SimpleStringProperty(HOVERED_STYLE))
//                            .otherwise(new SimpleStringProperty(GREY_STYLE)));
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
        ImageInfo imagecon;
        try {
            bimage = ImageIO.read(file);
            ImageIO.write(bimage, "png", file);
            Image image = SwingFXUtils.toFXImage(bimage, null);
            ImageView iv = new ImageView(image);
            iv.setTranslateX(x);
            iv.setTranslateY(y);
            leftArea = (Pane) map.getParent();
            leftArea.getChildren().add(iv);
            imagecon= new ImageInfo(image, imagePath, x, y);
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
    
    public void removeImage(ImageInfo image){
        images.remove(image);
    }
    
    public ArrayList<ImageInfo> getImages(){
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
    public ImageView getSelectedImage(){
        return selectedImage;
    }
    
    
}