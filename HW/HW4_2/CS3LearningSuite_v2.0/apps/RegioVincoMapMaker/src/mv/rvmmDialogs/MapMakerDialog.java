/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package mv.rvmmDialogs;

import static djf.AppPropertyType.CREATENEW_DIALOG_DATACHOICE_BUTTON;
import static djf.AppPropertyType.CREATENEW_DIALOG_DATACHOICE_LABEL;
import static djf.AppPropertyType.CREATENEW_DIALOG_HEADER_LABEL;
import static djf.AppPropertyType.CREATENEW_DIALOG_HEADER_LABEL_TEXT;
import static djf.AppPropertyType.CREATENEW_DIALOG_OKBUTTON;
import static djf.AppPropertyType.CREATENEW_DIALOG_PARENTS_BUTTON;
import static djf.AppPropertyType.CREATENEW_DIALOG_PARENTS_LABEL;
import static djf.AppPropertyType.CREATENEW_DIALOG_REGIONAME_LABEL;
import djf.AppTemplate;
import djf.modules.AppLanguageModule;
import djf.ui.dialogs.AppDialogsFacade;
import static djf.ui.style.DJFStyle.CLASS_RVMM_DIALOG_HEADER;
import static djf.ui.style.DJFStyle.CLASS_RVMM_DIALOG_LABEL;
import static djf.ui.style.DJFStyle.CLASS_RVMM_DIALOG_OK;
import java.io.File;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import static mv.rvmmDialogs.helperDialog.showOpenDirectoryChooserDialog;

import properties_manager.PropertiesManager;

/**
 *
 * @author Jihye
 */
public class MapMakerDialog extends Stage{
    AppTemplate app;
    GridPane gridPane;
    
    Label headerLabel = new Label();
    Label regionNameLabel = new Label();
    TextField regionNameTextField = new TextField();
    Button ParentRegionChoice = new Button();
    Label ParentRegionChoiceLabel = new Label();
    Button dataChoice = new Button();
    Label dataChoiceLabel = new Label();
    HBox dialogPane = new HBox();
    Button okButton = new Button();
    
    public MapMakerDialog(AppTemplate initApp){
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
        }
        if (col >= 0)
            gridPane.add(node, col, row, colSpan, rowSpan);
        
            node.getStyleClass().add(styleClass);
    }
    
    private void initDialog(){
        
        initGridNode(headerLabel,             CREATENEW_DIALOG_HEADER_LABEL,         CLASS_RVMM_DIALOG_HEADER,      0, 0, 3, 1, true);
        initGridNode(regionNameLabel,         CREATENEW_DIALOG_REGIONAME_LABEL,      CLASS_RVMM_DIALOG_LABEL,       1, 1, 1, 1, true);
        initGridNode(regionNameTextField,     null,                                  CLASS_RVMM_DIALOG_LABEL,       2, 1, 1, 1, false);
        initGridNode(ParentRegionChoice,      CREATENEW_DIALOG_PARENTS_BUTTON,       CLASS_RVMM_DIALOG_LABEL,       1, 2, 1, 1, true);
        initGridNode(ParentRegionChoiceLabel, CREATENEW_DIALOG_PARENTS_LABEL,        CLASS_RVMM_DIALOG_LABEL,       2, 2, 1, 1, true);
        initGridNode(dataChoice,              CREATENEW_DIALOG_DATACHOICE_BUTTON,    CLASS_RVMM_DIALOG_LABEL,       1, 3, 1, 1, true);
        initGridNode(dataChoiceLabel,         CREATENEW_DIALOG_DATACHOICE_LABEL,     CLASS_RVMM_DIALOG_LABEL,       2, 3, 1, 1, true);
        initGridNode(okButton,                CREATENEW_DIALOG_OKBUTTON,            CLASS_RVMM_DIALOG_OK,          2, 4, 1, 1, true);
         
        app.getGUIModule().addGUINode(CREATENEW_DIALOG_PARENTS_BUTTON, ParentRegionChoice);
        
        gridPane.setPadding(new Insets(30, 20, 20, 20));
        gridPane.setVgap(5);
        gridPane.setHgap(50);
        gridPane.setHalignment(headerLabel, HPos.CENTER);
        
        ParentRegionChoice.setOnAction(e->{
        File file = showOpenDirectoryChooserDialog(this, CREATENEW_DIALOG_PARENTS_BUTTON);
            if(file !=null){
                ParentRegionChoiceLabel.setText(file.getName());
            }
        });
        
        dataChoice.setOnAction(e->{
            File file = AppDialogsFacade.showOpenDialog(this, CREATENEW_DIALOG_DATACHOICE_BUTTON);
            if(file !=null){
                dataChoiceLabel.setText(file.getName());
            }
        });
    }
    
    public void showAddMapMakerDialog(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String headerText = props.getProperty(CREATENEW_DIALOG_HEADER_LABEL_TEXT);
        headerLabel.setText(headerText);
        setTitle(headerText);
        headerLabel.setAlignment(Pos.CENTER_RIGHT);
        regionNameLabel.setText("Region Name");
        ParentRegionChoice.setText("Choose Parent Region Directory");
        ParentRegionChoiceLabel.setText("Parent Region Directory Not Chosen");
        dataChoice.setText("Choose Data File");
        dataChoiceLabel.setText("Data File Not Chosen");
        okButton.setText("OK");
        okButton.setOnAction(e->{
        });
        regionNameTextField.setText("");
        showAndWait();
    }
    
}
