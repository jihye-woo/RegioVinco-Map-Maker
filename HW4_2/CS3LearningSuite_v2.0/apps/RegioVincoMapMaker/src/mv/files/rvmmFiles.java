package mv.files;

import static djf.AppPropertyType.APP_EXPORT_PAGE;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.modules.AppFileModule;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import static mv.MapMakerPropertyType.MV_MAP_PANE;
import static mv.MapMakerPropertyType.RVMM_LEFT_MAP;
import mv.data.ImageInfo;
import mv.data.rvmmData;
import properties_manager.PropertiesManager;

/**
 *
 * @author McKillaGorilla
 */
public class rvmmFiles implements AppFileComponent {
    static final String JSON_MAP_NAME = "name";
    static final String JSON_CAPITALS = "subregions_have_capitals";
    static final String JSON_FLAGS = "subregions_have_flags";
    static final String JSON_LEADERS = "subregions_have_leaders";
    static final String JSON_SUBREGIONS_DATA = "subregions";
    
    static final String JSON_SUBREGIONS_NAME = "name";
    static final String JSON_SUBREGIONS_CAPTIAL = "capital";
    static final String JSON_SUBREGIONS_LEADER = "leader";
    static final String JSON_SUBREGIONS_RED = "red";
    static final String JSON_SUBREGIONS_GREEN = "green";
    static final String JSON_SUBREGIONS_BLUE = "blue";
    static final String JSON_POLYGON_POINT = "polygon_points";
    
    static final String JSON_IMAGE = "image";
    static final String JSON_IMAGE_NAME = "image_name"; 
    static final String JSON_NUM_OF_IMAGE = "number_of_image";
    static final String JSON_IMAGE_TRANSLATEX = "image_translateX";
    static final String JSON_IMAGE_TRANSLATEY = "image_translateY";
    static final String JSON_IMAGE_PATH = "image_path";
    
    static final String JSON_NUMBER_OF_SUBREGIONS = "NUMBER_OF_SUBREGIONS";
    static final String JSON_SUBREGIONS = "SUBREGIONS";
    static final String JSON_SUBREGION_INDEX = "SUBREGION_INDEX";
    static final String JSON_NUMBER_OF_SUBREGION_POLYGONS = "NUMBER_OF_SUBREGION_POLYGONS";
    static final String JSON_SUBREGION_POLYGONS = "SUBREGION_POLYGONS";
    static final String JSON_SUBREGION_POLYGON = "SUBREGION_POLYGON";
    static final String JSON_POLYGON_POINTS = "VERTICES";
    static final String JSON_POLYGON_POINT_X = "X";
    static final String JSON_POLYGON_POINT_Y = "Y";
    static final String JSON_SCALE_X = "SCALE_X";
    static final String JSON_SCALE_Y = "SCALE_Y";
    static final String JSON_TRANSLATE_X = "TRANSLATE_X";
    static final String JSON_TRANSLATE_Y = "TRANSLATE_Y";
    
    
    // image size 
    //  arraybuilder images
    // Object image
    // - translateX, Y, path
    /**
     * This method is for saving user work.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */
     @Override
      public void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
	rvmmData rvmmdata = (rvmmData)data;
        AppFileModule appfilemodule = rvmmdata.getApp().getFileModule();
//        if(filePath.contains("raw_map_data")){
//            int index = appfilemodule.getWorkFile().getName().lastIndexOf(".");
//            String fileName = appfilemodule.getWorkFile().getName().substring(0,index);
//
//            Path path = Paths.get("../RegioVincoMapMaker/work/"+fileName);
//            String pathForSave = path.toFile().getCanonicalPath();
//            File f = new File(pathForSave, appfilemodule.getWorkFile().getName());
//            f.mkdirs();
//            Files.copy(appfilemodule.getWorkFile().toPath(), f.toPath(), REPLACE_EXISTING);
//            filePath = f.getAbsolutePath();
//        }
        ArrayList<ImageInfo> images = rvmmdata.getImages();
        
	// NOW BUILD THE JSON ARRAY FOR THE LIST
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder subregions = Json.createArrayBuilder();
        JsonArrayBuilder subregions_polygons_list = Json.createArrayBuilder();
        JsonArrayBuilder imagesArray = Json.createArrayBuilder();
        
        Color c = Color.color(0,0,0);
      
        for(int i=0; i<rvmmdata.numOfSubregion(); i++){
            ObservableList<Polygon> polygons = rvmmdata.getSubregion(i);
            for(int z=0; z< polygons.size(); z++){
            JsonArrayBuilder subregions_polygons_points = Json.createArrayBuilder();
       
            Polygon polygon = polygons.get(z);
                for(int j =0; j<polygon.getPoints().size();j+=2){
                JsonObject subregions_polygons_point = Json.createObjectBuilder()
                        .add(JSON_POLYGON_POINT_X, XTolong(polygon.getPoints().get(j), rvmmdata.getMap()))
                        .add(JSON_POLYGON_POINT_Y, YToLat(polygon.getPoints().get(j+1), rvmmdata.getMap())).build();
                subregions_polygons_points.add(subregions_polygons_point);
                }
                JsonObject plusColor = Json.createObjectBuilder()
                        .add(JSON_SUBREGIONS_RED,  c.getRed())
                        .add(JSON_SUBREGIONS_GREEN, c.getGreen())
                        .add(JSON_SUBREGIONS_BLUE, c.getBlue())
                        .add(JSON_POLYGON_POINT,subregions_polygons_points).build();
                
                subregions_polygons_list.add(plusColor);
            }
             JsonObject subregions_polygons_obj = Json.createObjectBuilder()
                     .add(JSON_NUMBER_OF_SUBREGION_POLYGONS, polygons.size())
                     .add(JSON_SUBREGION_POLYGONS,subregions_polygons_list)
                     .build();
             subregions.add(subregions_polygons_obj);
        }
        Pane leftArea = (Pane) rvmmdata.getMap().getParent();
        
        for(int i=0; i<images.size();i++){
            JsonObject imagesData = Json.createObjectBuilder()
                    .add(JSON_IMAGE_TRANSLATEX, leftArea.getChildren().get(i+1).getTranslateX())
                    .add(JSON_IMAGE_TRANSLATEY, leftArea.getChildren().get(i+1).getTranslateY())
                    .add(JSON_IMAGE_PATH, images.get(i).getImagePath())
                    .build();
            imagesArray.add(imagesData);
        }
        JsonObject rvmmDataJSON = Json.createObjectBuilder()
                .add(JSON_NUMBER_OF_SUBREGIONS, rvmmdata.numOfSubregion())
                .add(JSON_SCALE_X, rvmmdata.getMap().getScaleX())
                .add(JSON_SCALE_Y, rvmmdata.getMap().getScaleY())
                .add(JSON_TRANSLATE_X, rvmmdata.getMap().getTranslateX())
                .add(JSON_TRANSLATE_Y, rvmmdata.getMap().getTranslateY())
                .add(JSON_SUBREGIONS, subregions)
                .add(JSON_NUM_OF_IMAGE, images.size())
                .add(JSON_IMAGE,imagesArray)
                .build();
       
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(rvmmDataJSON);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(rvmmDataJSON);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException{
        rvmmData mapData = (rvmmData)data;
        mapData.reset();
        
        ArrayList<Double> locationOfImages = new ArrayList<Double>();
        // LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
        mapData.setFilePath(filePath);
        
        // THIS IS THE TOTAL NUMBER OF SUBREGIONS, EACH WITH
        // SOME NUMBER OF POLYGONS
        int numSubregions = getDataAsInt(json, JSON_NUMBER_OF_SUBREGIONS);
        JsonArray jsonSubregionsArray = json.getJsonArray(JSON_SUBREGIONS);
        
        double mapScaleX = getDataAsDouble(json, JSON_SCALE_X);
        mapData.getMap().setScaleX(mapScaleX);
        double mapScaleY = getDataAsDouble(json, JSON_SCALE_Y);
        mapData.getMap().setScaleY(mapScaleY);
        
        double mapTranslatesX = getDataAsDouble(json, JSON_TRANSLATE_X);
        mapData.getMap().setTranslateX(mapTranslatesX);
        double mapTranslatesY = getDataAsDouble(json, JSON_TRANSLATE_Y);
        mapData.getMap().setTranslateY(mapTranslatesY);
        
    // GO THROUGH ALL THE SUBREGIONS
        for (int subregionIndex = 0; subregionIndex < numSubregions; subregionIndex++) {
            // MAKE A POLYGON LIST FOR THIS SUBREGION
            JsonObject jsonSubregion = jsonSubregionsArray.getJsonObject(subregionIndex);
            int numSubregionPolygons = getDataAsInt(jsonSubregion, JSON_NUMBER_OF_SUBREGION_POLYGONS);
            ArrayList<ArrayList<Double>> subregionPolygonPoints = new ArrayList();
            // GO THROUGH ALL OF THIS SUBREGION'S POLYGONS
            for(int polygonIndex = 0; polygonIndex < numSubregionPolygons; polygonIndex++) {
                // GET EACH POLYGON (IN LONG/LAT GEOGRAPHIC COORDINATES)
                JsonArray jsonPolygon = jsonSubregion.getJsonArray(JSON_SUBREGION_POLYGONS);
                JsonObject polysinfo = jsonPolygon.getJsonObject(polygonIndex);
                double red = getDataAsDouble(polysinfo, JSON_SUBREGIONS_RED);
                double green = getDataAsDouble(polysinfo, JSON_SUBREGIONS_GREEN);
                double blue = getDataAsDouble(polysinfo, JSON_SUBREGIONS_BLUE);
                Color c = Color.color(red,green,blue);
                JsonArray pointsArray = polysinfo.getJsonArray(JSON_POLYGON_POINT);
                ArrayList<Double> polygonPointsList = new ArrayList();
                for (int pointIndex = 0; pointIndex < pointsArray.size(); pointIndex++) {
                    JsonObject point = pointsArray.getJsonObject(pointIndex);
                    double pointX = point.getJsonNumber(JSON_POLYGON_POINT_X).doubleValue();
                    double pointY = point.getJsonNumber(JSON_POLYGON_POINT_Y).doubleValue();
                    polygonPointsList.add(pointX);
                    polygonPointsList.add(pointY);
                }
                subregionPolygonPoints.add(polygonPointsList);
            }
            mapData.addSubregion(subregionPolygonPoints);
        }
        
        int numOfImages = getDataAsInt(json, JSON_NUM_OF_IMAGE);
        JsonArray jsonimagesArray = json.getJsonArray(JSON_IMAGE);
        ArrayList<ImageInfo> imageData = mapData.getImages();
            for(int i=0; i<numOfImages; i++){
            JsonObject image = jsonimagesArray.getJsonObject(i);
             double imageTranslateX = image.getJsonNumber(JSON_IMAGE_TRANSLATEX).doubleValue();
             double imageTranslateY = image.getJsonNumber(JSON_IMAGE_TRANSLATEY).doubleValue();
            JsonString imagePath =  image.getJsonString(JSON_IMAGE_PATH);
            String absolPath = filePath;
            for(i=0; i<3;i++){
               int lastIndex = absolPath.lastIndexOf("\\");
               absolPath = absolPath.substring(0,lastIndex);
            }
            
            mapData.addImage(absolPath+imagePath.getString(), imageTranslateX, imageTranslateY);
            }
    }
    public void SavedData(AppDataComponent data, String filePath) throws IOException {
        rvmmData mapData = (rvmmData)data;
        mapData.reset();
        
        // LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
	
        // THIS IS THE TOTAL NUMBER OF SUBREGIONS, EACH WITH
        // SOME NUMBER OF POLYGONS
        int numSubregions = getDataAsInt(json, JSON_NUMBER_OF_SUBREGIONS);
        JsonArray jsonSubregionsArray = json.getJsonArray(JSON_SUBREGIONS);

        // GO THROUGH ALL THE SUBREGIONS
        for (int subregionIndex = 0; subregionIndex < numSubregions; subregionIndex++) {
            // MAKE A POLYGON LIST FOR THIS SUBREGION
            JsonObject jsonSubregion = jsonSubregionsArray.getJsonObject(subregionIndex);
            int numSubregionPolygons = getDataAsInt(jsonSubregion, JSON_NUMBER_OF_SUBREGION_POLYGONS);
            ArrayList<ArrayList<Double>> subregionPolygonPoints = new ArrayList();
            // GO THROUGH ALL OF THIS SUBREGION'S POLYGONS
            for (int polygonIndex = 0; polygonIndex < numSubregionPolygons; polygonIndex++) {
                // GET EACH POLYGON (IN LONG/LAT GEOGRAPHIC COORDINATES)
                JsonArray jsonPolygon = jsonSubregion.getJsonArray(JSON_SUBREGION_POLYGONS);
                JsonArray pointsArray = jsonPolygon.getJsonArray(polygonIndex);
                ArrayList<Double> polygonPointsList = new ArrayList();
                for (int pointIndex = 0; pointIndex < pointsArray.size(); pointIndex++) {
                    JsonObject point = pointsArray.getJsonObject(pointIndex);
                    double pointX = point.getJsonNumber(JSON_POLYGON_POINT_X).doubleValue();
                    double pointY = point.getJsonNumber(JSON_POLYGON_POINT_Y).doubleValue();
                    polygonPointsList.add(pointX);
                    polygonPointsList.add(pointY);
                }
                subregionPolygonPoints.add(polygonPointsList);
            }
            mapData.addSubregion(subregionPolygonPoints);
        }
        
    }    
    public String getRelativePath(String path){
        String lastPath = path.substring(0, path.lastIndexOf("\\"));
        lastPath = lastPath.substring(0,path.lastIndexOf("\\"));
        return path.substring(lastPath.lastIndexOf("\\"));
        
    }
    
      public double XTolong(double x, Pane map){
        double paneHeight = map.getHeight();
        double unitDegree = paneHeight/180;
        double paneWidth = map.getWidth();
        double newX = (x/unitDegree)-180;
        return newX;
    }
    
    public double YToLat(double y, Pane map){
        double paneHeight = map.getHeight();
        double unitDegree = paneHeight/180;
        double newY = ((paneHeight- y)/unitDegree)-90;
        return newY;
    }
    
    public double getDataAsDouble(JsonObject json, String dataName) {
	JsonValue value = json.get(dataName);
	JsonNumber number = (JsonNumber)value;
	return number.bigDecimalValue().doubleValue();	
    }
    
    public int getDataAsInt(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber)value;
        return number.bigIntegerValue().intValue();
    }
    
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    @Override
    public void exportData(AppDataComponent data, String savedFileName) throws IOException {
        rvmmData rvmmdata = (rvmmData)data;
        AppFileModule appfilemodule = rvmmdata.getApp().getFileModule();
        int index = appfilemodule.getWorkFile().getName().lastIndexOf(".");
        String fileName = appfilemodule.getWorkFile().getName().substring(0,index);
       if(!(savedFileName.contains("export"))){
            Path path = Paths.get("../RegioVincoMapMaker/export/"+fileName);
            String pathForSave = path.toFile().getCanonicalPath();
            File f = new File(pathForSave, appfilemodule.getWorkFile().getName());
            f.mkdirs();
            Files.copy(appfilemodule.getWorkFile().toPath(), f.toPath(), REPLACE_EXISTING);
            savedFileName = f.getAbsolutePath();
        }
        JsonArrayBuilder subregions = Json.createArrayBuilder();
        Color c = Color.color(0,0,0);
        
        for(int i=0;i<rvmmdata.numOfSubregion(); i++){
        JsonObject subregions_object = Json.createObjectBuilder()
                .add(JSON_SUBREGIONS_NAME, "")
                .add(JSON_SUBREGIONS_CAPTIAL,  "")
                .add(JSON_SUBREGIONS_LEADER,  "")
                .add(JSON_SUBREGIONS_RED,  c.getRed())
                .add(JSON_SUBREGIONS_GREEN, c.getGreen())
                .add(JSON_SUBREGIONS_BLUE, c.getBlue())
                .build();
                subregions.add(subregions_object);
        }
                
        JsonObject rvmmViewerDataJSO = Json.createObjectBuilder()
                .add(JSON_MAP_NAME, fileName)
                .add(JSON_CAPITALS, true)
                .add(JSON_FLAGS, true)
                .add(JSON_LEADERS, true)
                .add(JSON_SUBREGIONS_DATA, subregions)
                .build();
        
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(rvmmViewerDataJSO);
	jsonWriter.close();

        // NOW BUILD THE JSON ARRAY FOR THE LIST
	savedFileName = savedFileName.substring(0,savedFileName.length()-5)+".rvm";
        
	// INIT THE WRITER
	OutputStream os = new FileOutputStream(savedFileName);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(rvmmViewerDataJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(savedFileName);
	pw.write(prettyPrinted);
	pw.close();
        
        Pane mapPane = (Pane) rvmmdata.getMap();
        savedFileName = savedFileName.substring(0, savedFileName.length()-4);
        SnapshotParameters sp = new SnapshotParameters();
        WritableImage imageToWirte = new WritableImage(802, 536);
        WritableImage snapshot = (WritableImage) mapPane.snapshot(sp, imageToWirte);
        File exportedImage = new File(savedFileName.substring(0,savedFileName.length())+".png");
        
        String html = "<html><body><h2>"+ savedFileName +"</h2> <img src=\""+savedFileName+".png\"> </body> </html>";
        String htmlPath = savedFileName +".html";
        
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", exportedImage);
            File htmlFile = new File(htmlPath);
            FileWriter fw = new FileWriter(htmlFile);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(html);
            out.close();
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(APP_EXPORT_PAGE, htmlPath);
            props.setPropertiesDataPath(htmlPath);
        } catch (IOException e) {
            e.getMessage();
        }
        
        Pane exportMap = rvmmdata.getMap();
        
        ImageIO.write(SwingFXUtils.fromFXImage(imageToWirte, null), "png", exportedImage);
    }
  
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        
    }

    @Override
    public void loadSavedData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}