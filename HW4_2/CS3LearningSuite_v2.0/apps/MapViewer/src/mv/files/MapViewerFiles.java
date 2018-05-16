package mv.files;

import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import mv.data.MapViewerData;

/**
 *
 * @author McKillaGorilla
 */
public class MapViewerFiles implements AppFileComponent {
    static final String JSON_NUMBER_OF_SUBREGIONS = "NUMBER_OF_SUBREGIONS";
    static final String JSON_SUBREGIONS = "SUBREGIONS";
    static final String JSON_SUBREGION_INDEX = "SUBREGION_INDEX";
    static final String JSON_NUMBER_OF_SUBREGION_POLYGONS = "NUMBER_OF_SUBREGION_POLYGONS";
    static final String JSON_SUBREGION_POLYGONS = "SUBREGION_POLYGONS";
    static final String JSON_SUBREGION_POLYGON = "SUBREGION_POLYGON";
    static final String JSON_POLYGON_POINTS = "VERTICES";
    static final String JSON_POLYGON_POINT_X = "X";
    static final String JSON_POLYGON_POINT_Y = "Y";
    
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
	
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject mapViewerDataJSO = Json.createObjectBuilder()
		// CURRENTLY IT SAVES NOTHING
		.build();
	
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
    }
    
    /**
     * This method is provided to satisfy the compiler, but it
     * is not used by this application.
     */
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        
    }
}