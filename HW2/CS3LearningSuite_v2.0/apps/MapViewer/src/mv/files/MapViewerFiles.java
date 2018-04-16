package mv.files;

import static djf.AppPropertyType.APP_EXPORT_PAGE;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javax.imageio.ImageIO;
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
import mv.MapViewerApp;
import mv.data.MapViewerData;
import mv.workspace.MapViewerWorkspace;
import properties_manager.PropertiesManager;
import static mv.MapViewerPropertyType.MV_RESET_ZOOM_BUTTON;
import static mv.MapViewerPropertyType.MV_ZOOM_OUT_BUTTON;

/**
 *
 * @author McKillaGorilla
 */
public class MapViewerFiles implements AppFileComponent {
    static final String JSON_NUMBER_OF_SUBREGIONS = "NUMBER_OF_SUBREGIONS";
    static final String JSON_SUBREGIONS = "SUBREGIONS";
    static final String JSON_SUBREGION_INDEX = "SUBREGION_INDEX";
    static final String JSON_NUMBER_OF_SUBREGION_POLYGONS = "NUMBER_OF_SUBREGION_POLYGONS";
    static final String JSON_SUBREGION_POLYGONS = "SUBREGION_POLYGONS";//
    static final String JSON_SUBREGION_POLYGON = "SUBREGION_POLYGON";
    static final String JSON_POLYGON_POINTS = "VERTICES";
    static final String JSON_POLYGON_POINT_X = "X"; //
    static final String JSON_POLYGON_POINT_Y = "Y"; //
    static final String JSON_SCALE_X = "SCALE_X";
    static final String JSON_SCALE_Y = "SCALE_Y";
    static final String JSON_TRANSLATE_X = "TRANSLATE_X";
    static final String JSON_TRANSLATE_Y = "TRANSLATE_Y";
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
	MapViewerData mapViewerData = (MapViewerData)data;
        
	// NOW BUILD THE JSON ARRAY FOR THE LIST
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder subregions = Json.createArrayBuilder();
        JsonArrayBuilder subregions_polygons_list = Json.createArrayBuilder();
        
        for(int i=0; i<mapViewerData.numOfSubregion(); i++){
            ObservableList<Polygon> polygons = mapViewerData.getSubregion(i);
            for(int z=0; z< polygons.size(); z++){
            JsonArrayBuilder subregions_polygons_points = Json.createArrayBuilder();
            Polygon polygon = polygons.get(z);
                for(int j =0; j<polygon.getPoints().size();j+=2){
                JsonObject subregions_polygons_point = Json.createObjectBuilder()
                        .add(JSON_POLYGON_POINT_X, XTolong(polygon.getPoints().get(j), mapViewerData.getMap()))
                        .add(JSON_POLYGON_POINT_Y, YToLat(polygon.getPoints().get(j+1), mapViewerData.getMap())).build();
                subregions_polygons_points.add(subregions_polygons_point);
                }
                subregions_polygons_list.add(subregions_polygons_points);
            }
             JsonObject subregions_polygons_obj = Json.createObjectBuilder()
                     .add(JSON_NUMBER_OF_SUBREGION_POLYGONS, polygons.size())
                     .add(JSON_SUBREGION_POLYGONS,subregions_polygons_list)
                     .build();
             subregions.add(subregions_polygons_obj);
        }
        
        JsonObject mapViewerDataJSO = Json.createObjectBuilder()
                .add(JSON_NUMBER_OF_SUBREGIONS, mapViewerData.numOfSubregion())
                .add(JSON_SCALE_X, mapViewerData.getMap().getScaleX())
                .add(JSON_SCALE_Y, mapViewerData.getMap().getScaleY())
                .add(JSON_TRANSLATE_X, mapViewerData.getMap().getTranslateX())
                .add(JSON_TRANSLATE_Y, mapViewerData.getMap().getTranslateY())
                .add(JSON_SUBREGIONS, subregions)
                .build();
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(mapViewerDataJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(mapViewerDataJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        // NOTE THAT WE ARE USING THE SIZE OF THE MAP
        MapViewerData mapData = (MapViewerData)data;
        mapData.reset();
        
        // LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
	
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
        
        if(mapScaleX <= 1){
            if(mapTranslatesX==0 && mapTranslatesY==0){
                mapData.getApp().getGUIModule().getGUINode(MV_RESET_ZOOM_BUTTON).setDisable(true);
            }
            mapData.getApp().getGUIModule().getGUINode(MV_ZOOM_OUT_BUTTON).setDisable(true);
        }
        
        
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
    
    
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    /**
     * This method would be used to export data to another format,
     * which we're not doing in this assignment.
     */
    @Override
    public void exportData(AppDataComponent data, String savedFileName) throws IOException {
        // YOU'LL NEED TO DEFINE THIS 
        MapViewerData mapViewerData = (MapViewerData)data;
        BorderPane outermap = (BorderPane) mapViewerData.getMap().getParent().getParent();
        savedFileName = savedFileName.substring(0, savedFileName.length()-5);
        SnapshotParameters sp = new SnapshotParameters();
        WritableImage image = (WritableImage) outermap.snapshot(sp, null);

        String PATH = "./export/";
        String directoryName = PATH +"/"+savedFileName;
        File exportDir = new File(directoryName);
        if(!exportDir.exists()){
            exportDir.mkdir();
            PATH += "/"+savedFileName+"/";
        }
        
        String html = "<html><body><h2>"+ savedFileName +"</h2> <img src=\""+savedFileName+".png\"> </body> </html>";
        String htmlPath = PATH +savedFileName +".html";
        String filePath = PATH +savedFileName +".png";
        File file = new File(filePath);
        
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
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
    }
    
    /** 
     * This method is provided to satisfy the compiler, but it
     * is not used by this application.
     */
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        
    }
}