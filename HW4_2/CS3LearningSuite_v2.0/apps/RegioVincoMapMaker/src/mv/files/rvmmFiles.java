package mv.files;

import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.modules.AppFileModule;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import mv.data.rvmmData;

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
        ArrayList<ImageView> images = rvmmdata.getImagesList();
        ArrayList<String> imagesPath = rvmmdata.getImagesPath();
        
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
        
        for(int i=0; i<images.size();i++){
            JsonObject imagesData = Json.createObjectBuilder()
                    .add(JSON_IMAGE_TRANSLATEX, images.get(i).getTranslateX())
                    .add(JSON_IMAGE_TRANSLATEY, images.get(i).getTranslateY())
                    .add(JSON_IMAGE_PATH, getRelativePath(imagesPath.get(i)))
                    .build();
            imagesArray.add(imagesData);
        }
        
        JsonObject mapViewerDataJSON = Json.createObjectBuilder()
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
	jsonWriter.writeObject(mapViewerDataJSON);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(mapViewerDataJSON);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        rvmmData mapData = (rvmmData)data;
        
	JsonObject json = loadJSONFile(filePath);
	
        int numSubregions = getDataAsInt(json, JSON_NUMBER_OF_SUBREGIONS);
        
        JsonArray jsonSubregionsArray = json.getJsonArray(JSON_SUBREGIONS);
        
        for (int subregionIndex = 0; subregionIndex < numSubregions; subregionIndex++) {
            JsonObject jsonSubregion = jsonSubregionsArray.getJsonObject(subregionIndex);
            int numSubregionPolygons = getDataAsInt(jsonSubregion, JSON_NUMBER_OF_SUBREGION_POLYGONS);
            ArrayList<ArrayList<Double>> subregionPolygonPoints = new ArrayList();
            for (int polygonIndex = 0; polygonIndex < numSubregionPolygons; polygonIndex++) {
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
        File f = new File(".");
        mapData.setRegionName(f.getName());
    }
    
    public String getRelativePath(String path){
        String lastPath = path.substring(0, path.lastIndexOf("/"));
        lastPath = lastPath.substring(0,path.lastIndexOf("/"));
        return "."+path.substring(lastPath.lastIndexOf("/"));
        
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
        
        JsonArrayBuilder subregions = Json.createArrayBuilder();
        
        
        for(int i=0;i<rvmmdata.getSubRegionToColorMappings().size(); i++){
        JsonObject subregions_object = Json.createObjectBuilder()
                .add(JSON_SUBREGIONS_NAME, rvmmdata.getRegionName())
                .add(JSON_SUBREGIONS_CAPTIAL,  rvmmdata.getHaveCapital())
                .add(JSON_SUBREGIONS_LEADER,  rvmmdata.getHaveLeaders())
                .add(JSON_SUBREGIONS_RED,  rvmmdata.getSubRegionToColorMappings().get(rvmmdata.getRegionName()).getRed())
                .add(JSON_SUBREGIONS_GREEN, rvmmdata.getSubRegionToColorMappings().get(rvmmdata.getRegionName()).getGreen())
                .add(JSON_SUBREGIONS_BLUE, rvmmdata.getSubRegionToColorMappings().get(rvmmdata.getRegionName()).getBlue()).build();
                subregions.add(subregions_object);
        }
                
        JsonObject rvmmViewerDataJSO = Json.createObjectBuilder()
                .add(JSON_MAP_NAME,rvmmdata.getRegionName())
                .add(JSON_CAPITALS, rvmmdata.getHaveCapital())
                .add(JSON_FLAGS, rvmmdata.getHaveflags())
                .add(JSON_LEADERS, rvmmdata.getHaveLeaders())
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
	
	// INIT THE WRITER
	OutputStream os = new FileOutputStream(savedFileName);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(rvmmViewerDataJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(savedFileName);
	pw.write(prettyPrinted);
	pw.close();
    }
    
  
    
    
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        
    }

}