package mv.workspace;

import djf.components.AppWorkspaceComponent;
import djf.AppTemplate;
import static djf.modules.AppGUIModule.ENABLED;
import static djf.modules.AppGUIModule.DISABLED;
import static djf.modules.AppGUIModule.FOCUS_TRAVERSABLE;
import static djf.modules.AppGUIModule.HAS_KEY_HANDLER;
import djf.ui.AppNodesBuilder;
import djf.ui.controllers.AppFileController;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import static javafx.scene.DepthTest.DISABLE;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import properties_manager.PropertiesManager;
import mv.MapViewerApp;
import static mv.MapViewerPropertyType.MV_LABEL;
import static mv.MapViewerPropertyType.MV_MAP_HBOX1;
import static mv.MapViewerPropertyType.MV_MAP_HBOX2;
import static mv.MapViewerPropertyType.MV_MAP_PANE;
import static mv.MapViewerPropertyType.MV_MAP_VBOX;
import static mv.MapViewerPropertyType.MV_MOVE_DOWN_BUTTON;
import static mv.MapViewerPropertyType.MV_MOVE_LEFT_BUTTON;
import static mv.MapViewerPropertyType.MV_MOVE_RIGHT_BUTTON;
import static mv.MapViewerPropertyType.MV_MOVE_UP_BUTTON;
import static mv.MapViewerPropertyType.MV_RESET_ZOOM_BUTTON;
import static mv.MapViewerPropertyType.MV_FITPLOY_BUTTON;
import static mv.MapViewerPropertyType.MV_RESET_ZOOM_BUTTON;
import static mv.MapViewerPropertyType.MV_ZOOM_IN_BUTTON;
import static mv.MapViewerPropertyType.MV_ZOOM_OUT_BUTTON;
import static mv.workspace.style.MapViewerStyle.BUTTON_TAG_WIDTH;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_HBOX;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_ICON;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_OCEAN;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_VBOX;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_VBOX_LABEL;

/**
 *
 * @author McKillaGorilla
 */
public class MapViewerWorkspace extends AppWorkspaceComponent {
    WebView webView;
    WebEngine engine;
    double originalX;
    double originalY;
    double currentX = 0;
    double currentY = 0;
    double scale = 1.0;
    double worldHeight = 0;
    double worldWidth = 0;
    double viewPortX = 0;
    double viewPortY = 0;
    double worldvpX = 0;
    double worldvpy = 0;
    double numOfPolygons = 0;
    String webViewHTML ="";
      
    
    public MapViewerWorkspace(MapViewerApp app) {
        super(app);
        // LAYOUT THE APP
        webView = new WebView();
        engine = webView.getEngine();
        initLayout();
    }
    
    
    // THIS HELPER METHOD INITIALIZES ALL THE CONTROLS IN THE WORKSPACE
    private void initLayout() {
        // FIRST LOAD THE FONT FAMILIES FOR THE COMBO BOX
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // THIS WILL BUILD ALL OF OUR JavaFX COMPONENTS FOR US
        AppNodesBuilder workspaceBuilder = app.getGUIModule().getNodesBuilder();
        // THIS IS WHERE WE'LL DRAW THE MAP
        Pane mapPane = new Pane();
        
       
        // Controller
        AppFileController controller = new AppFileController((AppTemplate)app);
        MapViewerController mvController = new MapViewerController((AppTemplate) app);
        // AND THIS WILL BE USED TO CLIP THE MAP SO WE CAN ZOOM
        BorderPane outerMapPane = new BorderPane();
        Rectangle clippingRectangle = new Rectangle();
        outerMapPane.setClip(clippingRectangle);
        Pane clippedPane = new Pane(); // viewport
        outerMapPane.setCenter(clippedPane);
        clippedPane.getChildren().add(mapPane);
        
        
        
        Rectangle ocean = new Rectangle();
        mapPane.getChildren().add(ocean);
        ocean.getStyleClass().add(CLASS_MV_MAP_OCEAN);
        app.getGUIModule().addGUINode(MV_MAP_PANE, mapPane);
        mapPane.minWidthProperty().bind(outerMapPane.widthProperty());
        mapPane.maxWidthProperty().bind(outerMapPane.widthProperty());
        mapPane.minHeightProperty().bind(outerMapPane.heightProperty());
        mapPane.maxHeightProperty().bind(outerMapPane.heightProperty());
        outerMapPane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            clippingRectangle.setWidth(newValue.getWidth());
            clippingRectangle.setHeight(newValue.getHeight());
            ocean.setWidth(newValue.getHeight()*2);
            ocean.setHeight(newValue.getHeight());
        });
        
        // VBOX
        VBox vbox = workspaceBuilder.buildVBox(MV_MAP_VBOX, null, null, CLASS_MV_MAP_VBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label vboxLabel = workspaceBuilder.buildLabel(MV_LABEL, vbox, null, CLASS_MV_MAP_VBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        HBox hbox1 = workspaceBuilder.buildHBox(MV_MAP_HBOX1, vbox, null, CLASS_MV_MAP_HBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        HBox hbox2 = workspaceBuilder.buildHBox(MV_MAP_HBOX2, vbox, null, CLASS_MV_MAP_HBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        
         // AND PUT EVERYTHING IN THE WORKSPACE
        workspace = new BorderPane();
        ((BorderPane)workspace).setLeft(vbox);
        ((BorderPane)workspace).setCenter(outerMapPane);
        
        // BUTTON
       
        Button ResetZoom = workspaceBuilder.buildIconButton(MV_RESET_ZOOM_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button FitToPoly = workspaceBuilder.buildIconButton(MV_FITPLOY_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button ZoomOut = workspaceBuilder.buildIconButton(MV_ZOOM_OUT_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button ZoomIn = workspaceBuilder.buildIconButton(MV_ZOOM_IN_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        hbox1.getChildren().addAll(FitToPoly,ResetZoom,ZoomOut,ZoomIn);
      
        Button moveLeft = workspaceBuilder.buildIconButton(MV_MOVE_LEFT_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button moveRight = workspaceBuilder.buildIconButton(MV_MOVE_RIGHT_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button moveUp = workspaceBuilder.buildIconButton(MV_MOVE_UP_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button moveDown = workspaceBuilder.buildIconButton(MV_MOVE_DOWN_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        hbox2.getChildren().addAll(moveLeft,moveRight,moveUp,moveDown);
        
        
        // Mouse Evenet
         mapPane.setOnMousePressed(e->{
            System.out.println(e.getX());
            mapPane.setCursor(Cursor.CROSSHAIR);
            originalX = e.getX();
            originalY = e.getY();
        });
        mapPane.setOnMouseDragged(e->{
            double transX = e.getX()-originalX;
            double transY = e.getY()-originalY;
            mapPane.setTranslateX(mapPane.getTranslateX()+transX);
            mapPane.setTranslateY(mapPane.getTranslateY()+transY);
            app.getGUIModule().getGUINode(MV_RESET_ZOOM_BUTTON).setDisable(false);
             app.getFileModule().markAsEdited(true);
        });
        mapPane.setOnMouseReleased(e->{
            mapPane.setCursor(Cursor.DEFAULT);
        });
        mapPane.setOnScroll(e->{
            double scroll = e.getDeltaY();
            double currentx = e.getX();
            double currenty = e.getY();
            double centerX = mapPane.getWidth()/2;
            double centerY = mapPane.getHeight()/2;
            if(scroll < 0) { 
               if(mapPane.getScaleX() > 1.0){
                mapPane.setScaleX(0.5*mapPane.getScaleX());
                mapPane.setScaleY(0.5*mapPane.getScaleY());
                mapPane.setTranslateX(-(mapPane.getScaleX()*(centerX-currentx))+mapPane.getTranslateX());
                mapPane.setTranslateY(-(mapPane.getScaleY()*(centerY-currenty))+mapPane.getTranslateY());
                app.getFileModule().markAsEdited(true);
               }
            }
            else{ 
                mapPane.setTranslateX((mapPane.getScaleX()*(centerX-currentx))+mapPane.getTranslateX());
                mapPane.setTranslateY((mapPane.getScaleY()*(centerY-currenty))+mapPane.getTranslateY()); 
                mapPane.setScaleX(2*mapPane.getScaleX());
                mapPane.setScaleY(2*mapPane.getScaleY());
                 app.getFileModule().markAsEdited(true);
            }
            
            if(mapPane.getScaleX() <= 1){
                app.getGUIModule().getGUINode(MV_ZOOM_OUT_BUTTON).setDisable(true);
            }
            else{
                app.getGUIModule().getGUINode(MV_ZOOM_OUT_BUTTON).setDisable(false);
            }
            app.getGUIModule().getGUINode(MV_RESET_ZOOM_BUTTON).setDisable(false);
        });
        
     
        // WEB VIEW
        webView.setMaxWidth(300);
        webViewHTML += mvController.fronthtmlCode();
        webViewHTML +=
                scale + "<br>" +
                clippedPane.widthProperty().get()+ "<br>"+
                clippedPane.heightProperty().get() + "<br>"+
                ocean.getWidth() +"<br>" +
                ocean.getHeight() +"<br>" +
                currentX +"<br>" +
                currentY +"<br>" +
                0.0 + "% <br>" +
                0.0 + "% <br>" +
                0.0 +"<br>"+
                0.0 +"<br>"+
                numOfPolygons+"<br>"
                ;
        webViewHTML += mvController.rearhtmlCode();
        engine.loadContent(webViewHTML);
        vbox.getChildren().add(webView);
        
        mapPane.setOnMouseMoved(e->{
            currentX = e.getX();
            currentY = e.getY();
            for(int i=0 ;i<mapPane.getChildren().size();i++){
                
                if(i==0){
                     numOfPolygons = 0;
                }
                else{
                    Polygon p = (Polygon) mapPane.getChildren().get(i);
                    if(p.isHover()){
                        numOfPolygons = p.getPoints().size()/2;
                    }
                }
            }
            // num of Polygons
            
            scale = mapPane.getScaleX();
            double viewPortWidth = mapPane.getWidth();
            double viewPortHeight = mapPane.getHeight();
            worldWidth = ocean.getWidth();
            worldHeight = ocean.getHeight();
            currentX = e.getX();
            currentY = e.getY();
            double mousePerX = Math.round(100*Math.abs(viewPortX/viewPortWidth));
            double mousePerY = Math.round(100*Math.abs(viewPortY/viewPortHeight));
            double worldViewportX = mvController.getWorldViewPortX(mapPane);
            double worldViewportY = mvController.getWorldViewPortY(mapPane);
            worldViewportX = Double.parseDouble(String.format("%.2f", worldViewportX));
            worldViewportY = Double.parseDouble(String.format("%.2f", worldViewportY));
            currentX = Double.parseDouble(String.format("%.2f", currentX));
            currentY = Double.parseDouble(String.format("%.2f", currentY));
            
            engine.loadContent(mvController.fronthtmlCode()+
                scale + "<br>" +
                clippedPane.widthProperty().get()+ "<br>"+
                clippedPane.heightProperty().get() + "<br>"+
                worldWidth +"<br>" +
                worldHeight +"<br>"+
                currentX +"<br>" +
                currentY +"<br>" +
                mousePerX + "% <br>" +
                mousePerY + "% <br>" +
                worldViewportX+ "<br>" +
                worldViewportY+ "<br>" +
                numOfPolygons+"<br>"
                   +mvController.rearhtmlCode()
            );
        });
        clippedPane.setOnMouseMoved(eh->{
                viewPortX = eh.getX();
                viewPortY = eh.getY();
        });
        
        mvController.processFitToPoly(mapPane, FitToPoly);
        mvController.processResetZoom(mapPane, ResetZoom);
        mvController.processZoomOut(mapPane, ZoomOut);
        mvController.processZoomIn(mapPane, ZoomIn);
        
        mvController.processMoveLeft(mapPane, moveLeft);
        mvController.processMoveRight(mapPane, moveRight);
        mvController.processMoveUp(mapPane, moveUp);
        mvController.processMoveDown(mapPane, moveDown);
        
    }
    
    @Override
    public void processWorkspaceKeyEvent(KeyEvent ke) {
        System.out.println("WORKSPACE REPONSE TO " + ke.getCharacter());
    }

    private void mouseEvent(Pane mapPane) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}