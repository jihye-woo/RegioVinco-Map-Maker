/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package djf.ui.dialogs;

import static djf.AppPropertyType.LOAD_DIALOG_CHOOSER_BUTTON;
import static djf.AppPropertyType.LOAD_DIALOG_CHOOSER_BUTTON_TEXT;
import static djf.AppPropertyType.LOAD_DIALOG_CHOOSER_LABEL;
import static djf.AppPropertyType.LOAD_DIALOG_CHOOSER_LABEL_TEXT;
import static djf.AppPropertyType.LOAD_DIALOG_HEADER_LABEL;
import static djf.AppPropertyType.LOAD_DIALOG_HEADER_LABEL_TEXT;
import static djf.AppPropertyType.LOAD_DIALOG_OKBUTTON;
import static djf.AppPropertyType.LOAD_DIALOG_REGIONAME_LABEL;
import static djf.AppPropertyType.LOAD_DIALOG_REGIONAME_LABEL_TEXT;
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
import javafx.stage.Stage;

import properties_manager.PropertiesManager;

/**
 *
 * @author Jihye
 */
public class MapMakerLoadDialog extends Stage{
    AppTemplate app;
    GridPane gridPane;
    
    Label headerLabel = new Label();
    Label regionNameLabel = new Label();
    TextField regionNameTextField = new TextField();
    Button loadfilechooserButton = new Button();
    Label loadfilechooserLabel = new Label();
    Button okButton = new Button();
    
    public MapMakerLoadDialog(AppTemplate initApp){
        app = initApp;
        
        gridPane = new GridPane();
//        gridPane.getStyleClass().add(CLASS_RVMM_DIALOG_GRID);
        initDialog();

        Scene scene = new Scene(gridPane);
        this.setScene(scene);

        app.getGUIModule().initStylesheet(this);
//        scene.getStylecheets().add(CLASS_RVMM_DIALOG_GRID);

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
        
        initGridNode(headerLabel,             LOAD_DIALOG_HEADER_LABEL,         CLASS_RVMM_DIALOG_HEADER,      0, 0, 3, 1, true);
        initGridNode(regionNameLabel,         LOAD_DIALOG_REGIONAME_LABEL,      CLASS_RVMM_DIALOG_LABEL,       1, 1, 1, 1, true);
        initGridNode(regionNameTextField,     null,                             CLASS_RVMM_DIALOG_LABEL,       2, 1, 1, 1, false);
        initGridNode(loadfilechooserButton,   LOAD_DIALOG_CHOOSER_BUTTON,       CLASS_RVMM_DIALOG_LABEL,       1, 2, 1, 1, true);
        initGridNode(loadfilechooserLabel,    LOAD_DIALOG_CHOOSER_LABEL,        CLASS_RVMM_DIALOG_LABEL,       2, 2, 1, 1, true);
        initGridNode(okButton,                LOAD_DIALOG_OKBUTTON,            CLASS_RVMM_DIALOG_OK,          2, 3, 1, 1, true);
         
//        app.getGUIModule().addGUINode(CREATENEW_DIALOG_PARENTS_BUTTON_TEXT, ParentRegionChoice);
        app.getGUIModule().addGUINode(LOAD_DIALOG_CHOOSER_BUTTON, loadfilechooserButton);
//        app.getGUIModule().addGUINode(CREATENEW_DIALOG_DATACHOICE_BUTTON, dataChoice);
        
//        AppLanguageModule languageSettings = app.getLanguageModule();
//        languageSettings.addLabeledControlProperty(CREATENEW_DIALOG_PARENTS_BUTTON + "_TEXT",       ParentRegionChoice.textProperty());
//        languageSettings.addLabeledControlProperty(CREATENEW_DIALOG_DATACHOICE_BUTTON + "_TEXT",    dataChoice.textProperty());
        gridPane.setPadding(new Insets(30, 20, 20, 20));
        gridPane.setVgap(5);
        gridPane.setHgap(50);
        gridPane.setHalignment(headerLabel, HPos.CENTER);
        
        
        loadfilechooserButton.setOnAction(e->{
            AppDialogsFacade.showOpenDialog(this, LOAD_DIALOG_CHOOSER_BUTTON);
        });
        
        okButton.setOnAction(e->{
            this.hide();
        });
    }
    
    public void showLoadMapMakerDialog(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String headerText = props.getProperty(LOAD_DIALOG_HEADER_LABEL_TEXT);
        headerLabel.setText(headerText);
        setTitle(headerText);
        headerLabel.setAlignment(Pos.CENTER_RIGHT);
        
        regionNameLabel.setText(props.getProperty(LOAD_DIALOG_REGIONAME_LABEL_TEXT));
        loadfilechooserButton.setText(props.getProperty(LOAD_DIALOG_CHOOSER_BUTTON_TEXT));
        loadfilechooserLabel.setText(props.getProperty(LOAD_DIALOG_CHOOSER_LABEL_TEXT));
        okButton.setText("OK");
        
        regionNameTextField.setText("");
        showAndWait();
    }
    
}
