package mv.workspace;

import djf.components.AppWorkspaceComponent;
import djf.AppTemplate;
import static djf.modules.AppGUIModule.ENABLED;
import static djf.modules.AppGUIModule.FOCUS_TRAVERSABLE;
import static djf.modules.AppGUIModule.HAS_KEY_HANDLER;
import djf.ui.AppNodesBuilder;
import djf.ui.controllers.AppFileController;
import java.io.File;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import static mv.MapMakerPropertyType.CREATEMAP_BUTTON;
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
import static mv.MapMakerPropertyType.RVMM_MAIN_TOOLBAR;
import static mv.MapMakerPropertyType.RVMM_RIGHTAREA;
import static mv.MapMakerPropertyType.RVMM_SUBTOOLBAR4;
import static mv.MapMakerPropertyType.RVMM_SUBTOOLBAR5;
import static mv.MapMakerPropertyType.RVMM_TABLE;
import static mv.MapMakerPropertyType.RVMM_TABLECOL1;
import static mv.MapMakerPropertyType.RVMM_TABLECOL2;
import static mv.MapMakerPropertyType.RVMM_TABLECOL3;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR1;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR2;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR3;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR4;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR5;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_ADDIMAGE;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_BOTTOMLEFT;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_CHANGE;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_EXIT;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_EXPORT;

import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_EXTEND;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_FITVIEWPORT;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_LOAD;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_REDO;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_REMOVEIMAGE;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_RESETVIEWPORT;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_SAVE;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_TEXT;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_TOPLEFT;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_BUTTON_UNDO;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_CHECKBOX1;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_CHECKBOX1_LABEL;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_CHECKBOX2;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_CHECKBOX2_LABEL;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_COLORPICKER;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_COLORPICKER_LABEL;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_SLIDER;
import static mv.MapMakerPropertyType.RVMM_TOOLBAR_SLIDER_LABEL;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_HBOX;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_ICON;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_OCEAN;
import static mv.workspace.style.MapViewerStyle.CLASS_MV_MAP_VBOX_LABEL;
import static mv.workspace.style.MapViewerStyle.CLASS_RFMM_BOTTOMBOX;
import static mv.workspace.style.MapViewerStyle.CLASS_RFMM_BOTTOMBOX2;
import static mv.workspace.style.MapViewerStyle.CLASS_RVMM_BOTTOMBOX_LABEL;
import static mv.workspace.style.MapViewerStyle.CLASS_RVMM_CHECKBOX;
import static mv.workspace.style.MapViewerStyle.CLASS_RVMM_TABLE;
import static mv.workspace.style.MapViewerStyle.CLASS_RVMM_TABLECOL;
import static mv.workspace.style.MapViewerStyle.CLASS_RVMM_TOOLBAR;
import static mv.workspace.style.MapViewerStyle.CLASS_RVMM_TOOLBAR_MAIN;

/**
 * @author McKillaGorilla
 */

public class rvmmWorkspace extends AppWorkspaceComponent {
    double originalX;
    double originalY;
    
    double locationX;
    double locationY;
    
    public rvmmWorkspace(RegioVincoMapMakerApp app) {
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
        rvmmDialogController dialogController = new rvmmDialogController((AppTemplate) app);
        rvmmButtonController buttonController = new rvmmButtonController((AppTemplate) app);
        // AND THIS WILL BE USED TO CLIP THE MAP SO WE CAN ZOOM
        BorderPane outerMapPane = new BorderPane();
        Rectangle clippingRectangle = new Rectangle();
        outerMapPane.setClip(clippingRectangle);
        ScrollBar s1 = new ScrollBar();
        s1.setMax(800);
        s1.setMin(0);
        Pane mapPane = new Pane();
        File imagefile;
       
        Pane leftArea = new Pane();
        VBox rightArea = workspaceBuilder.buildVBox(RVMM_RIGHTAREA, null, null, CLASS_RVMM_TABLE, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        HBox hbox1 = workspaceBuilder.buildHBox(MV_MAP_HBOX1, rightArea, null, CLASS_MV_MAP_HBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label vboxLabel = workspaceBuilder.buildLabel(MV_LABEL, hbox1, null, CLASS_MV_MAP_VBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        hbox1.setAlignment(Pos.CENTER);
        Button movedown = workspaceBuilder.buildIconButton(MV_MOVE_DOWN_BUTTON, hbox1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button moveup = workspaceBuilder.buildIconButton(MV_MOVE_UP_BUTTON, hbox1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        SplitPane sp = new SplitPane(leftArea,rightArea);
        sp.setOrientation(Orientation.HORIZONTAL);
        sp.setDividerPositions(0.6);
        Rectangle clipTable = new Rectangle();
        
        //ToolBar
        Pane topToolBar = new Pane();
        topToolBar.getStyleClass().add("topToolBar");
        HBox maintoolbar = workspaceBuilder.buildHBox(RVMM_MAIN_TOOLBAR, topToolBar, null, CLASS_RVMM_TOOLBAR_MAIN, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        HBox toolbar1 = workspaceBuilder.buildHBox(RVMM_TOOLBAR1, maintoolbar, null, CLASS_RVMM_TOOLBAR, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        HBox toolbar2 = workspaceBuilder.buildHBox(RVMM_TOOLBAR2, maintoolbar, null, CLASS_RVMM_TOOLBAR, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        HBox toolbar3 = workspaceBuilder.buildHBox(RVMM_TOOLBAR3, maintoolbar, null, CLASS_RVMM_TOOLBAR, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        VBox toolbar4 = workspaceBuilder.buildVBox(RVMM_TOOLBAR4, maintoolbar, null, CLASS_RVMM_TOOLBAR, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        HBox subtoolbar4 = workspaceBuilder.buildHBox(RVMM_SUBTOOLBAR4, toolbar4, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        HBox subtoolbar5 = workspaceBuilder.buildHBox(RVMM_SUBTOOLBAR5, toolbar4, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        HBox toolbar5 = workspaceBuilder.buildHBox(RVMM_TOOLBAR5, maintoolbar, null, CLASS_RVMM_TOOLBAR, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        //toolbar1
        Button creatNewMap = workspaceBuilder.buildIconButton(CREATEMAP_BUTTON, toolbar1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        creatNewMap.setOnAction(e->{
            dialogController.processCreatNewMap();
        });
        Button load = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_LOAD, toolbar1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        load.setOnAction(e->{
            AppFileController fileController = new AppFileController(app);
            fileController.processLoadRequest();
        });
        Button save = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_SAVE, toolbar1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button export =workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_EXPORT, toolbar1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button exit = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_EXIT, toolbar1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button undo = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_UNDO, toolbar1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button redo = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_REDO, toolbar1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
       
        //toolbar2
        Button resetviewport= workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_RESETVIEWPORT, toolbar2, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        resetviewport.setOnAction(e->{
            buttonController.processResetViewport(mapPane, resetviewport);
        });
        
        Button fitviewportToPoly= workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_FITVIEWPORT, toolbar2, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        fitviewportToPoly.setOnAction(e->{
            buttonController.processFitToPoly(mapPane, export);
            app.getGUIModule().getGUINode(RVMM_TOOLBAR_BUTTON_FITVIEWPORT).setDisable(false);
            app.getFileModule().markAsEdited(true);
        });
        //toolbar3
        Button text= workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_TEXT, toolbar3, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        text.setOnAction(e->{
            dialogController.processRename();
        });
        Button addImage = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_ADDIMAGE, toolbar3, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        addImage.setOnAction(e->{
            ImageView imageToAdd = new ImageView();
            imageToAdd = buttonController.processAddImage(leftArea);
        });
        
        Button removeImage= workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_REMOVEIMAGE, toolbar3, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button topLeft = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_TOPLEFT, toolbar3, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button bottomleft = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_BOTTOMLEFT, toolbar3, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button change = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_CHANGE, toolbar3, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Button extend = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_EXTEND, toolbar3, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        
        extend.setOnAction(e->{
            dialogController.processChangeDimensions();
        });
        
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
        });
        
        
        //toolbar4
        Label toggleFrameBoxLabel = workspaceBuilder.buildLabel(RVMM_TOOLBAR_CHECKBOX1_LABEL, subtoolbar4, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        CheckBox toggleFrameBox = workspaceBuilder.buildCheckBox(RVMM_TOOLBAR_CHECKBOX1, subtoolbar4, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label movePolygonsModeLabel = workspaceBuilder.buildLabel(RVMM_TOOLBAR_CHECKBOX2_LABEL, subtoolbar5, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        CheckBox movePolygonsMode = workspaceBuilder.buildCheckBox(RVMM_TOOLBAR_CHECKBOX2, subtoolbar5, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
                      
        //toolbar5
        Label borderColorPickerLabel = workspaceBuilder.buildLabel(RVMM_TOOLBAR_COLORPICKER_LABEL, toolbar5, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        ColorPicker borderColorPicker = workspaceBuilder.buildColorPicker(RVMM_TOOLBAR_COLORPICKER, toolbar5, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label borderThicknessSliderLabel = workspaceBuilder.buildLabel(RVMM_TOOLBAR_SLIDER_LABEL, toolbar5, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Slider borderThicknessSlider = workspaceBuilder.buildSlider(RVMM_TOOLBAR_SLIDER, toolbar5, null, CLASS_RVMM_CHECKBOX, 0, 1, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        borderThicknessSlider.setMajorTickUnit(0.5);
        
        outerMapPane.setCenter(sp);
        
        //Hbox1 -2
        TableView table = workspaceBuilder.buildTableView(RVMM_TABLE, rightArea, null, CLASS_RVMM_TABLE, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        table.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        TableColumn tableCol1 = workspaceBuilder.buildTableColumn(RVMM_TABLECOL1, table, CLASS_RVMM_TABLECOL);
        TableColumn tableCol2 = workspaceBuilder.buildTableColumn(RVMM_TABLECOL2, table, CLASS_RVMM_TABLECOL);
        TableColumn tableCol3 = workspaceBuilder.buildTableColumn(RVMM_TABLECOL3, table, CLASS_RVMM_TABLECOL);
        
        ObservableList<String> characters = FXCollections.observableArrayList("Sean");
        table.setItems(characters);
        
        table.setOnMouseClicked(e->{
            if(e.getButton().equals(MouseButton.PRIMARY)){
                if(e.getClickCount()==2){
                     dialogController.processEditSubregion();
                }
            }
        });
        //Hbox1 -3
        HBox hbox2 = workspaceBuilder.buildHBox(MV_MAP_HBOX2, rightArea, null, CLASS_RFMM_BOTTOMBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        VBox bottom1 = workspaceBuilder.buildVBox(RVMM_BOTTOM_RIGHTAREA1, hbox2, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        VBox bottom2 = workspaceBuilder.buildVBox(RVMM_BOTTOM_RIGHTAREA2, hbox2, null, CLASS_RFMM_BOTTOMBOX2, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        VBox bottom3 = workspaceBuilder.buildVBox(RVMM_BOTTOM_RIGHTAREA3, hbox2, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        VBox bottom4 = workspaceBuilder.buildVBox(RVMM_BOTTOM_RIGHTAREA4, hbox2, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        
        //Hbox2 1
        Label backgroundGradient = workspaceBuilder.buildLabel(RVMM_BOTTOM1_1LABEL, bottom1, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label focusAngle = workspaceBuilder.buildLabel(RVMM_BOTTOM1_2LABEL, bottom1, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label centerX = workspaceBuilder.buildLabel(RVMM_BOTTOM1_3LABEL, bottom1, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label radius = workspaceBuilder.buildLabel(RVMM_BOTTOM1_4LABEL, bottom1, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label stop0Color = workspaceBuilder.buildLabel(RVMM_BOTTOM1_5LABEL, bottom1, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
       
        //Hbox2 2
        Slider bggradientSlider = workspaceBuilder.buildSlider(RVMM_BOTTOM1_SLIDER1, bottom2, null, CLASS_RVMM_CHECKBOX, 0, 360, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        bggradientSlider.setMajorTickUnit(25);
        Slider fangleSlider = workspaceBuilder.buildSlider(RVMM_BOTTOM1_SLIDER2, bottom2, null, CLASS_RVMM_CHECKBOX, 0, 1920, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        fangleSlider.setMajorTickUnit(500);
        Slider centerXSlider = workspaceBuilder.buildSlider(RVMM_BOTTOM1_SLIDER3, bottom2, null, CLASS_RVMM_CHECKBOX, 0, 960, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        centerXSlider.setMajorTickUnit(200);
        ColorPicker pickStop0Color = workspaceBuilder.buildColorPicker(RVMM_BOTTOM1_COLORPICKER, bottom2, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        
        //Hbox2 3
        Label Proportional = workspaceBuilder.buildLabel(RVMM_BOTTOM2_1LABEL, bottom3, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label focusDistance = workspaceBuilder.buildLabel(RVMM_BOTTOM2_2LABEL, bottom3, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label centerY = workspaceBuilder.buildLabel(RVMM_BOTTOM2_3LABEL, bottom3, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label cycleMethod = workspaceBuilder.buildLabel(RVMM_BOTTOM2_4LABEL, bottom3, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label stop1Color = workspaceBuilder.buildLabel(RVMM_BOTTOM2_5LABEL, bottom3, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        
        //Hbox2 4
        CheckBox proportionalCheckBox = workspaceBuilder.buildCheckBox(RVMM_BOTTOM2_CHECKBOX, bottom4, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Slider focuseDistanceSlider = workspaceBuilder.buildSlider(RVMM_BOTTOM2_SLIDER1, bottom4, null, CLASS_RVMM_CHECKBOX, -1, 1, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        focuseDistanceSlider.setMajorTickUnit(2);
        Slider centerYSlider = workspaceBuilder.buildSlider(RVMM_BOTTOM2_SLIDER2, bottom4, null, CLASS_RVMM_CHECKBOX, 0, 1080, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        centerYSlider.setMajorTickUnit(500);
        
        ArrayList<String> options = new ArrayList<String>();
        options.add("NO_CYCLE");
        options.add("CYCLE");
        ComboBox cycleMethodBox = workspaceBuilder.buildComboBox(RVMM_BOTTOM2_COMBOBOX, options, "NO_CYCLE", bottom4, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        ColorPicker pickStop1Color = workspaceBuilder.buildColorPicker(RVMM_BOTTOM2_COLORPICKER, bottom4, null, CLASS_RVMM_CHECKBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        leftArea.getChildren().add(mapPane);
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
        
        
         // AND PUT EVERYTHING IN THE WORKSPACE
        workspace = new BorderPane();
        ((BorderPane)workspace).setTop(topToolBar);
        ((BorderPane)workspace).setCenter(outerMapPane);
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