/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package mv.rvmmDialogs;

import static djf.AppPropertyType.MAP_DIMENSIONS_DIALOG_HEADER_LABEL;
import static djf.AppPropertyType.MAP_DIMENSIONS_DIALOG_HEADER_LABEL_TEXT;
import static djf.AppPropertyType.MAP_DIMENSIONS_DIALOG_HEIGHT_LABEL;
import static djf.AppPropertyType.MAP_DIMENSIONS_DIALOG_HEIGHT_LABEL_TEXT;
import static djf.AppPropertyType.MAP_DIMENSIONS_DIALOG_OKBUTTON;
import static djf.AppPropertyType.MAP_DIMENSIONS_DIALOG_WIDTH_BUTTON;
import djf.AppTemplate;
import djf.modules.AppLanguageModule;
import static djf.ui.style.DJFStyle.CLASS_RVMM_DIALOG_HEADER;
import static djf.ui.style.DJFStyle.CLASS_RVMM_DIALOG_LABEL;
import static djf.ui.style.DJFStyle.CLASS_RVMM_DIALOG_OK;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import mv.data.rvmmData;
import properties_manager.PropertiesManager;

/**
 *
 * @author Jihye
 */
public class MapMakerChangeDiemensionDialog extends Stage{
    AppTemplate app;
    GridPane gridPane;
    
    Label headerLabel = new Label();
    Label heightLabel = new Label();
    TextField heightTextField = new TextField();
    Label widthLabel = new Label();
    TextField widthTextField = new TextField();
    Button okButton = new Button();
    
    public MapMakerChangeDiemensionDialog(AppTemplate initApp){
        app = initApp;
        gridPane = new GridPane();
        initDialog();
        Scene scene = new Scene(gridPane);
        this.setScene(scene);
        app.getGUIModule().initStylesheet(this);
    }
    
    protected void initGridNode(Node node, Object nodeId, String styleClass, int col, int row, int colSpan, int rowSpan, boolean isLanguageDependent) {
        AppLanguageModule languageSettings = app.getLanguageModule();
        
        if (isLanguageDependent) {
            languageSettings.addLabeledControlProperty(nodeId + "_TEXT", ((Labeled)node).textProperty());
//            ((Labeled)node).setTooltip(new Tooltip(""));
//            languageSettings.addLabeledControlProperty(nodeId + "_TOOLTIP", ((Labeled)node).tooltipProperty().get().textProperty());
        }
        if (col >= 0)
            gridPane.add(node, col, row, colSpan, rowSpan);
        node.getStyleClass().add(styleClass);
    }
    
    private void initDialog(){
        
        initGridNode(headerLabel,             MAP_DIMENSIONS_DIALOG_HEADER_LABEL,       CLASS_RVMM_DIALOG_HEADER,     0, 0, 3, 1, true);
        initGridNode(heightLabel,             MAP_DIMENSIONS_DIALOG_HEIGHT_LABEL,       CLASS_RVMM_DIALOG_LABEL,      1, 1, 1, 1, true);
        initGridNode(heightTextField,         null,                                     CLASS_RVMM_DIALOG_LABEL,      2, 1, 1, 1, false);
        initGridNode(widthLabel,              MAP_DIMENSIONS_DIALOG_WIDTH_BUTTON,       CLASS_RVMM_DIALOG_LABEL,      1, 2, 1, 1, true);
        initGridNode(widthTextField,          null,                                     CLASS_RVMM_DIALOG_LABEL,      2, 2, 1, 1, false);
        initGridNode(okButton,                MAP_DIMENSIONS_DIALOG_OKBUTTON,           CLASS_RVMM_DIALOG_OK,         1, 3, 1, 1, true);
        
//        AppLanguageModule languageSettings = app.getLanguageModule();
//        languageSettings.addLabeledControlProperty(MAP_DIMENSIONS_DIALOG_OKBUTTON + "_TEXT",    okButton.textProperty());
//        app.getGUIModule().addGUINode(MAP_DIMENSIONS_DIALOG_OKBUTTON, okButton);
gridPane.setPadding(new Insets(30, 20, 20, 20));
gridPane.setVgap(5);
gridPane.setHgap(50);
gridPane.setHalignment(headerLabel, HPos.CENTER);

    }
    
    public void showChangeDiemensionDialog(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String headerText = props.getProperty(MAP_DIMENSIONS_DIALOG_HEADER_LABEL_TEXT);
        headerLabel.setText(headerText);
        setTitle(headerText);
        headerLabel.setAlignment(Pos.CENTER_RIGHT);
        heightLabel.setText(props.getProperty(MAP_DIMENSIONS_DIALOG_HEIGHT_LABEL_TEXT));
        rvmmData data = (rvmmData) app.getDataComponent();
        Pane leftArea = (Pane) data.getMap().getParent();
        heightTextField.setText(Double.toString(leftArea.getWidth()));
        widthTextField.setText(Double.toString(data.getMap().getWidth()));
        okButton.setOnAction(e->{
            Rectangle ClippingRectangle = (Rectangle)data.getMap().getParent().getParent().getClip();
            ClippingRectangle.setWidth(Double.parseDouble(widthTextField.getText()));
            ClippingRectangle.setHeight(Double.parseDouble(heightTextField.getText()));
            this.hide();
        });
        showAndWait();
    }
}
