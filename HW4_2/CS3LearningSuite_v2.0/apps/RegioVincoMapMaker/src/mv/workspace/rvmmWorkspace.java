package mv.workspace;

import djf.AppPropertyType;
import djf.components.AppWorkspaceComponent;
import djf.AppTemplate;
import djf.modules.AppFileModule;
import static djf.modules.AppGUIModule.ENABLED;
import static djf.modules.AppGUIModule.FOCUS_TRAVERSABLE;
import static djf.modules.AppGUIModule.HAS_KEY_HANDLER;
import djf.ui.AppNodesBuilder;
import djf.ui.controllers.AppFileController;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;
import javafx.scene.control.cell.PropertyValueFactory;
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
import static mv.MapMakerPropertyType.*;
import mv.data.SubRegionInfo;
import mv.data.rvmmData;
import static mv.rvmmDialogs.helperDialog.showOpenParentsDialog;
import static mv.workspace.style.MapViewerStyle.*;

/**
 * @author McKillaGorilla
 */

public class rvmmWorkspace extends AppWorkspaceComponent {
    double originalX;
    double originalY;
    double locationX;
    double locationY;
    ImageView selectedImage = new ImageView();
    ObjectProperty<TableRow> selectedRow = new SimpleObjectProperty<TableRow>();
    rvmmData data;
    
    
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
        rvmmButtonController buttonController = new rvmmButtonController((RegioVincoMapMakerApp) app);
        
        // AND THIS WILL BE USED TO CLIP THE MAP SO WE CAN ZOOM
        BorderPane outerMapPane = new BorderPane();
        Rectangle clippingRectangle = new Rectangle();
        outerMapPane.setClip(clippingRectangle);
        ScrollBar s1 = new ScrollBar();
        s1.setMax(800);
        s1.setMin(0);
        Pane mapPane = new Pane();
        File imagefile;
        Pane leftBasedArea = new Pane();
        Pane leftArea = new Pane();
        leftArea.getChildren().add(mapPane);
//        leftBasedArea.getChildren().add(leftArea);
//        leftArea.getChildren().add(leftBasedArea);
        VBox rightArea = workspaceBuilder.buildVBox(RVMM_RIGHTAREA, null, null, CLASS_RVMM_TABLE, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        HBox hbox1 = workspaceBuilder.buildHBox(MV_MAP_HBOX1, rightArea, null, CLASS_MV_MAP_HBOX, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Label vboxLabel = workspaceBuilder.buildLabel(MV_LABEL, hbox1, null, CLASS_MV_MAP_VBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        hbox1.setAlignment(Pos.CENTER);
        Button movedown = workspaceBuilder.buildIconButton(MV_MOVE_DOWN_BUTTON, hbox1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        movedown.setOnAction(e->{
            data = (rvmmData) app.getDataComponent();
            int currentIndex = data.currentSelectedPolygon();
            if(currentIndex < data.numOfSubregion()-2){
                Collections.swap(data.getSubRegionInfo(), currentIndex, currentIndex+1);
                 data.swapHashMapValue(currentIndex, currentIndex+1);
            }
        });
        Button moveup = workspaceBuilder.buildIconButton(MV_MOVE_UP_BUTTON, hbox1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        moveup.setOnAction(e->{
            data = (rvmmData) app.getDataComponent();
            int currentIndex = data.currentSelectedPolygon();
            System.out.println(currentIndex);
            System.out.println(data.getEachSubRegionsInfo(currentIndex).getSubregion());
            if(currentIndex > 0){
                Collections.swap(data.getSubRegionInfo(), currentIndex, currentIndex-1);
                data.swapHashMapValue(currentIndex, currentIndex-1);
            }
            currentIndex = data.currentSelectedPolygon();
            System.out.println(currentIndex);
            System.out.println(data.getEachSubRegionsInfo(currentIndex).getSubregion());
        });
        SplitPane sp = new SplitPane(leftArea,rightArea);
        sp.setOrientation(Orientation.HORIZONTAL);
        sp.setDividerPositions(0.6);
//        Rectangle clipTable = new Rectangle();
        
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
            controller.processLoadRequest();
        });
        Button save = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_SAVE, toolbar1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        save.setOnAction(e->{
//            controller.processSaveRequest();
            AppFileModule afm = app.getFileModule();
            data = (rvmmData) app.getDataComponent();
                try {
                    afm.saveWork(new File(data.getFilePath()));
                } catch (IOException ex) {
                    Logger.getLogger(rvmmWorkspace.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
        
        Button export =workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_EXPORT, toolbar1, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        export.setOnAction(e->{
            controller.processExportRequest();
        });
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
          File file = showOpenParentsDialog(app.getGUIModule().getWindow(), AppPropertyType.APP_TITLE);
          ImageView imageView;
          imageView = new ImageView(file.toURI().toString());
            data = (rvmmData) app.getDataComponent();
            data.addImage(file.getAbsolutePath(), locationX, locationY);
        });
        Button removeImage= workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_REMOVEIMAGE, toolbar3, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        removeImage.setOnAction(e->{
            buttonController.processRemoveImage(leftArea, selectedImage);
        });
        
        Button topLeft = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_TOPLEFT, toolbar3, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        topLeft.setOnAction(e->{
            buttonController.processSanpTopLeft();
        });
        Button bottomleft = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_BOTTOMLEFT, toolbar3, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        bottomleft.setOnAction(e->{
            buttonController.processSanpBottomleft();
        });
        Button changeColor = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_CHANGE, toolbar3, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        changeColor.setOnAction(e->{
            buttonController.processAssginRandomColor();
        });
        Button resize = workspaceBuilder.buildIconButton(RVMM_TOOLBAR_BUTTON_EXTEND, toolbar3, null, CLASS_MV_MAP_ICON, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        resize.setOnAction(e->{
            dialogController.processChangeDimensions();
        });
        
        mapPane.setOnMousePressed(e->{
            System.out.println(e.getX());
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
        borderColorPicker.setOnAction(e->{
            ((rvmmData)app.getDataComponent()).getColorController().changeColor(borderColorPicker.getValue());
        });
        
        Label borderThicknessSliderLabel = workspaceBuilder.buildLabel(RVMM_TOOLBAR_SLIDER_LABEL, toolbar5, null, CLASS_RVMM_BOTTOMBOX_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        Slider borderThicknessSlider = workspaceBuilder.buildSlider(RVMM_TOOLBAR_SLIDER, toolbar5, null, CLASS_RVMM_CHECKBOX, 0, 1, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        borderThicknessSlider.setMajorTickUnit(0.05);
        borderThicknessSlider.valueProperty().addListener((obs, oldval, newVal)
           -> ((rvmmData)app.getDataComponent()).getColorController().changeThinkness(newVal.doubleValue()/mapPane.getScaleX()));
        outerMapPane.setCenter(sp);
        
        //Hbox1 -2
        TableView table = workspaceBuilder.buildTableView(RVMM_TABLE, rightArea, null, CLASS_RVMM_TABLE, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED);
        table.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        TableColumn tableCol1 = new TableColumn("Subregion");
        tableCol1.setCellValueFactory(new PropertyValueFactory<>("Subregion"));
        TableColumn tableCol2 = new TableColumn("Capital");
        tableCol2.setCellValueFactory(new PropertyValueFactory<>("Capital"));
        TableColumn tableCol3 = new TableColumn("Leader");
        tableCol3.setCellValueFactory(new PropertyValueFactory<>("Leader"));
        
        table.getColumns().addAll(tableCol1, tableCol2, tableCol3);
        table.setRowFactory(tableView -> {
            TableRow rows= new TableRow<>();
            rows.setOnMouseClicked(e->{
                if(e.getButton().equals(MouseButton.PRIMARY)){
                    data = (rvmmData) app.getDataComponent();
                    data.polygonSelecting(rows.getIndex());
                    if(e.getClickCount()==2){
                        dialogController.processEditSubregion(rows.getIndex());
                    }
                }
            });
           return rows;
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
    
        Rectangle ocean = new Rectangle();
        mapPane.getChildren().add(ocean);
//        ocean.setOnMouseClicked(e->{
//            data = (rvmmData) app.getDataComponent();
//                if(data.currentSelectedPolygon() < 0){
//                    table.getSelectionModel().clearSelection();
//            }
//        });
        ocean.getStyleClass().add(CLASS_MV_MAP_OCEAN);
        app.getGUIModule().addGUINode(MV_MAP_PANE, mapPane);
        app.getGUIModule().addGUINode(RVMM_LEFT_MAP, leftArea);
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
    private ObservableList<SubRegionInfo> getListItem(){
            data = (rvmmData) app.buildDataComponent(app);
            ObservableList<SubRegionInfo> Info = data.getSubRegionInfo();
            return Info;
        }
    
    @Override
    public void processWorkspaceKeyEvent(KeyEvent ke) {
        System.out.println("WORKSPACE REPONSE TO " + ke.getCharacter());
    }

    private void mouseEvent(Pane mapPane) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void processEditSubregion(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}