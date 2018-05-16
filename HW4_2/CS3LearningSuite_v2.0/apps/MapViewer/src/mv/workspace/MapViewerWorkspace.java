package mv.workspace;

import djf.components.AppWorkspaceComponent;
import djf.AppTemplate;
import static djf.modules.AppGUIModule.ENABLED;
import static djf.modules.AppGUIModule.FOCUS_TRAVERSABLE;
import static djf.modules.AppGUIModule.HAS_KEY_HANDLER;
import djf.ui.AppNodesBuilder;
import djf.ui.controllers.AppFileController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import properties_manager.PropertiesManager;
import mv.RegioVincoMapMakerApp;
import static mv.MapMakerPropertyType.MV_LABEL;
import static mv.MapMakerPropertyType.MV_MAP_HBOX1;
import static mv.MapMakerPropertyType.MV_MAP_HBOX2;
import static mv.MapMakerPropertyType.MV_MAP_PANE;
import static mv.MapMakerPropertyType.MV_MOVE_DOWN_BUTTON;
import static mv.MapMakerPropertyType.MV_MOVE_UP_BUTTON;
import static mv.MapMakerPropertyType.RVMM_BOTTOM1_1LABEL;
import static mv.MapMakerPropertyType.RVMM_BOTTOM1_2LABEL;
import static mv.MapMakerPropertyType.RVMM_BOTTOM1_3LABEL;
import static mv.MapMakerPropertyType.RVMM_BOTTOM1_4LABEL;
import static mv.MapMakerPropertyType.RVMM_BOTTOM1_5LABEL;
import static mv.MapMakerPropertyType.RVMM_BOTTOM1_COLORPICKER;
import static mv.MapMakerPropertyType.RVMM_BOTTOM1_SLIDER1;
import static mv.MapMakerPropertyType.RVMM_BOTTOM1_SLIDER2;
import static mv.MapMakerPropertyType.RVMM_BOTTOM1_SLIDER3;
import static mv.MapMakerPropertyType.RVMM_BOTTOM2_1LABEL;
import static mv.MapMakerPropertyType.RVMM_BOTTOM2_2LABEL;
import static mv.MapMakerPropertyType.RVMM_BOTTOM2_3LABEL;
import static mv.MapMakerPropertyType.RVMM_BOTTOM2_4LABEL;
import static mv.MapMakerPropertyType.RVMM_BOTTOM2_5LABEL;
import static mv.MapMakerPropertyType.RVMM_BOTTOM2_CHECKBOX;
import static mv.MapMakerPropertyType.RVMM_BOTTOM2_COLORPICKER;
import static mv.MapMakerPropertyType.RVMM_BOTTOM2_COMBOBOX;
import static mv.MapMakerPropertyType.RVMM_BOTTOM2_SLIDER1;
import static mv.MapMakerPropertyType.RVMM_BOTTOM2_SLIDER2;
import static mv.MapMakerPropertyType.RVMM_BOTTOM_RIGHTAREA1;
import static mv.MapMakerPropertyType.RVMM_BOTTOM_RIGHTAREA2;
import static mv.MapMakerPropertyType.RVMM_BOTTOM_RIGHTAREA3;
import static mv.MapMakerPropertyType.RVMM_BOTTOM_RIGHTAREA4;
import static mv.MapMakerPropertyType.RVMM_RIGHTAREA;
import static mv.MapMakerPropertyType.RVMM_TABLE;
import static mv.MapMakerPropertyType.RVMM_TABLECOL1;
import static mv.MapMakerPropertyType.RVMM_TABLECOL2;
import static mv.MapMakerPropertyType.RVMM_TABLECOL3;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_HBOX;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_ICON;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_OCEAN;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_VBOX_LABEL;
import static mv.workspace.style.MapViewerStyle.CLASS_RVMM_BOTTOMBOX_LABEL;
import static mv.workspace.style.MapViewerStyle.CLASS_RVMM_CHECKBOX;
import static mv.workspace.style.MapViewerStyle.CLASS_RVMM_TABLE;
import static mv.workspace.style.MapViewerStyle.CLASS_RVMM_TABLECOL;

/**
 *
 * @author McKillaGorilla
 */
public class MapViewerWorkspace extends AppWorkspaceComponent {
      
    
    public MapViewerWorkspace(RegioVincoMapMakerApp app) {
        super(app);
        // LAYOUT THE APP
        initLayout();
    }
    
    
    // THIS HELPER METHOD INITIALIZES ALL THE CONTROLS IN THE WORKSPACE
    private void initLayout() {
        // FIRST LOAD THE FONT FAMILIES FOR THE COMBO BOX
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // THIS WILL BUILD ALL OF OUR JavaFX COMPONENTS FOR US
        AppNodesBuilder workspaceBuilder = app.getGUIModule().getNodesBuilder();
        // Controller
        AppFileController controller = new AppFileController((AppTemplate)app);
        MapViewerController mvController = new MapViewerController((AppTemplate) app);
        // AND THIS WILL BE USED TO CLIP THE MAP SO WE CAN ZOOM
        BorderPane outerMapPane = new BorderPane();
        Rectangle clippingRectangle = new Rectangle();
        outerMapPane.setClip(clippingRectangle);
        
        Pane mapPane = new Pane();
        
       
        Pane leftArea = new Pane();
        VBox rightArea = workspaceBuilder.buildVBox(RVMM_RIGHTAREA, null, null, CLASS_RVMM_TABLE, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        HBox hbox1 = workspaceBuilder.buildHBox(MV_MAP_HBOX1, rightArea, null, CLASS_MV_MAP_HBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        hbox1.setAlignment(Pos.CENTER);
        Label vboxLabel = workspaceBuilder.buildLabel(MV_LABEL, hbox1, null, CLASS_MV_MAP_VBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button movedown = workspaceBuilder.buildIconButton(MV_MOVE_DOWN_BUTTON, hbox1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button moveup = workspaceBuilder.buildIconButton(MV_MOVE_UP_BUTTON, hbox1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        SplitPane sp = new SplitPane(leftArea,rightArea);
        Rectangle clipTable = new Rectangle();
        
        Pane clippedPane = new Pane(); // viewport
        outerMapPane.setCenter(sp);
        
        //Hbox1 -2
        TableView table = workspaceBuilder.buildTableView(RVMM_TABLE, rightArea, null, CLASS_RVMM_TABLE, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        TableColumn tableCol1 = workspaceBuilder.buildTableColumn(RVMM_TABLECOL1, table, CLASS_RVMM_TABLECOL);
        TableColumn tableCol2 = workspaceBuilder.buildTableColumn(RVMM_TABLECOL2, table, CLASS_RVMM_TABLECOL);
        TableColumn tableCol3 = workspaceBuilder.buildTableColumn(RVMM_TABLECOL3, table, CLASS_RVMM_TABLECOL);
        
        //Hbox1 -3
        HBox hbox2 = workspaceBuilder.buildHBox(MV_MAP_HBOX2, rightArea, null, CLASS_MV_MAP_HBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        VBox bottom1 = workspaceBuilder.buildVBox(RVMM_BOTTOM_RIGHTAREA1, hbox2, null, CLASS_RVMM_TABLE, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        VBox bottom2 = workspaceBuilder.buildVBox(RVMM_BOTTOM_RIGHTAREA2, hbox2, null, CLASS_RVMM_TABLE, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        VBox bottom3 = workspaceBuilder.buildVBox(RVMM_BOTTOM_RIGHTAREA3, hbox2, null, CLASS_RVMM_TABLE, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        VBox bottom4 = workspaceBuilder.buildVBox(RVMM_BOTTOM_RIGHTAREA4, hbox2, null, CLASS_RVMM_TABLE, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        
        //Hbox2 1
        Label backgroundGradient = workspaceBuilder.buildLabel(RVMM_BOTTOM1_1LABEL, bottom1, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label focusAngle = workspaceBuilder.buildLabel(RVMM_BOTTOM1_2LABEL, bottom1, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label centerX = workspaceBuilder.buildLabel(RVMM_BOTTOM1_3LABEL, bottom1, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label radius = workspaceBuilder.buildLabel(RVMM_BOTTOM1_4LABEL, bottom1, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label stop0Color = workspaceBuilder.buildLabel(RVMM_BOTTOM1_5LABEL, bottom1, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
       
        //Hbox2 2
        Slider bggradientSlider = workspaceBuilder.buildSlider(RVMM_BOTTOM1_SLIDER1, bottom2, null, CLASS_RVMM_CHECKBOX, 0, 360, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Slider fangleSlider = workspaceBuilder.buildSlider(RVMM_BOTTOM1_SLIDER2, bottom2, null, CLASS_RVMM_CHECKBOX, 0, 1920, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Slider centerXSlider = workspaceBuilder.buildSlider(RVMM_BOTTOM1_SLIDER3, bottom2, null, CLASS_RVMM_CHECKBOX, 0, 960, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        ColorPicker pickStop0Color = workspaceBuilder.buildColorPicker(RVMM_BOTTOM1_COLORPICKER, bottom2, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        
        //Hbox2 3
        Label Proportional = workspaceBuilder.buildLabel(RVMM_BOTTOM2_1LABEL, bottom3, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label focusDistance = workspaceBuilder.buildLabel(RVMM_BOTTOM2_2LABEL, bottom3, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label centerY = workspaceBuilder.buildLabel(RVMM_BOTTOM2_3LABEL, bottom3, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label cycleMethod = workspaceBuilder.buildLabel(RVMM_BOTTOM2_4LABEL, bottom3, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label stop1Color = workspaceBuilder.buildLabel(RVMM_BOTTOM2_5LABEL, bottom3, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        
        //Hbox2 4
        CheckBox proportionalCheckBox = workspaceBuilder.buildCheckBox(RVMM_BOTTOM2_CHECKBOX, bottom4, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Slider focuseDistanceSlider = workspaceBuilder.buildSlider(RVMM_BOTTOM2_SLIDER1, bottom4, null, CLASS_RVMM_CHECKBOX, 0, 1, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Slider centerYSlider = workspaceBuilder.buildSlider(RVMM_BOTTOM2_SLIDER2, bottom4, null, CLASS_RVMM_CHECKBOX, 0, 1080, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        ComboBox cycleMethodBox = workspaceBuilder.buildComboBox(RVMM_BOTTOM2_COMBOBOX, bottom4, fangleSlider, bottom4, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        ColorPicker pickStop1Color = workspaceBuilder.buildColorPicker(RVMM_BOTTOM2_COLORPICKER, bottom4, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        
        
//        sp.getItems().addAll(mapPane, table);
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
//        VBox vbox = workspaceBuilder.buildVBox(MV_MAP_VBOX, null, null, CLASS_MV_MAP_VBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
//        Label vboxLabel = workspaceBuilder.buildLabel(MV_LABEL, vbox, null, CLASS_MV_MAP_VBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
//        HBox hbox1 = workspaceBuilder.buildHBox(MV_MAP_HBOX1, vbox, null, CLASS_MV_MAP_HBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
//        HBox hbox2 = workspaceBuilder.buildHBox(MV_MAP_HBOX2, vbox, null, CLASS_MV_MAP_HBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
//        
         // AND PUT EVERYTHING IN THE WORKSPACE
        workspace = new BorderPane();
//        ((BorderPane)workspace).setLeft(vbox);
        ((BorderPane)workspace).setCenter(outerMapPane);
        
        // BUTTON
//        Button ResetZoom = workspaceBuilder.buildIconButton(MV_RESET_ZOOM_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
//        Button FitToPoly = workspaceBuilder.buildIconButton(MV_FITPLOY_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
//        Button ZoomOut = workspaceBuilder.buildIconButton(MV_ZOOM_OUT_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
//        Button ZoomIn = workspaceBuilder.buildIconButton(MV_ZOOM_IN_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
//        hbox1.getChildren().addAll(FitToPoly,ResetZoom,ZoomOut,ZoomIn);
//      
//        Button moveLeft = workspaceBuilder.buildIconButton(MV_MOVE_LEFT_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
//        Button moveRight = workspaceBuilder.buildIconButton(MV_MOVE_RIGHT_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
//        Button moveUp = workspaceBuilder.buildIconButton(MV_MOVE_UP_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
//        Button moveDown = workspaceBuilder.buildIconButton(MV_MOVE_DOWN_BUTTON, null, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
//        hbox2.getChildren().addAll(moveLeft,moveRight,moveUp,moveDown);
//        
//        mvController.processFitToPoly(mapPane, FitToPoly);
//        mvController.processResetZoom(mapPane, ResetZoom);
//        mvController.processZoomOut(mapPane, ZoomOut);
//        mvController.processZoomIn(mapPane, ZoomIn);
        
//        mvController.processMoveLeft(mapPane, moveLeft);
//        mvController.processMoveRight(mapPane, moveRight);
//        mvController.processMoveUp(mapPane, moveUp);
//        mvController.processMoveDown(mapPane, moveDown);
//        
    }
    
    @Override
    public void processWorkspaceKeyEvent(KeyEvent ke) {
        System.out.println("WORKSPACE REPONSE TO " + ke.getCharacter());
    }

    private void mouseEvent(Pane mapPane) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}