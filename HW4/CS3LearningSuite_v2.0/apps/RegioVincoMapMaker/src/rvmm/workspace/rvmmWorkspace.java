/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package rvmm.workspace;

import djf.AppTemplate;
import djf.components.AppWorkspaceComponent;
import static djf.modules.AppGUIModule.ENABLED;
import static djf.modules.AppGUIModule.FOCUS_TRAVERSABLE;
import static djf.modules.AppGUIModule.HAS_KEY_HANDLER;
import djf.ui.AppNodesBuilder;
import djf.ui.controllers.AppFileController;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import properties_manager.PropertiesManager;
import static rvmm.MapMakerPropertyType.MV_LABEL;
import static rvmm.MapMakerPropertyType.MV_MAP_HBOX1;
import static rvmm.MapMakerPropertyType.MV_MAP_HBOX2;
import static rvmm.MapMakerPropertyType.MV_MAP_PANE;
import static rvmm.MapMakerPropertyType.MV_MAP_VBOX;
import static rvmm.MapMakerPropertyType.MV_MAP_VBOX_LABEL;
import static rvmm.MapMakerPropertyType.MV_MOVE_LEFT_BUTTON;
import static rvmm.workspace.style.rvmmStyle.CLASS_MV_MAP_HBOX;
import static rvmm.workspace.style.rvmmStyle.CLASS_MV_MAP_ICON;
import static rvmm.workspace.style.rvmmStyle.CLASS_MV_MAP_OCEAN;
import static rvmm.workspace.style.rvmmStyle.CLASS_MV_MAP_VBOX;

/**
 *
 * @author Jihye
 */
public class rvmmWorkspace extends AppWorkspaceComponent{
    
    public rvmmWorkspace(AppTemplate app) {
        super(app);
        initLayout();
    }
    
    private void initLayout() {
        // FIRST LOAD THE FONT FAMILIES FOR THE COMBO BOX


        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // THIS WILL BUILD ALL OF OUR JavaFX COMPONENTS FOR US
        AppNodesBuilder workspaceBuilder = app.getGUIModule().getNodesBuilder();
        // THIS IS WHERE WE'LL DRAW THE MAP
        Pane mapPane = new Pane();
        // Controller
        AppFileController controller = new AppFileController((AppTemplate)app);
        MapMakerController rvmmController = new MapMakerController(app);
        // AND THIS WILL BE USED TO CLIP THE MAP SO WE CAN ZOOM
        
        BorderPane outerMapPane = new BorderPane();
        Rectangle clippingRectangle = new Rectangle();
        outerMapPane.setClip(clippingRectangle);        
        Pane clippedPane = new Pane();
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

        // AND PUT EVERYTHING IN THE WORKSPACE
	workspace = new BorderPane();
	((BorderPane)workspace).setCenter(outerMapPane);
    }
    
    @Override
    public void processWorkspaceKeyEvent(KeyEvent ke) {
        System.out.println("WORKSPACE REPONSE TO " + ke.getCharacter());
    }
    
    
    
}
