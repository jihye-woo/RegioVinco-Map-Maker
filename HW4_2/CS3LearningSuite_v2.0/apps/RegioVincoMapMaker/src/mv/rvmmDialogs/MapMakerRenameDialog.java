/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package mv.rvmmDialogs;

import static djf.AppPropertyType.RENAME_DIALOG_HEADER_LABEL;
import static djf.AppPropertyType.RENAME_DIALOG_HEADER_LABEL_TEXT;
import static djf.AppPropertyType.RENAME_DIALOG_NEW_LABEL;
import static djf.AppPropertyType.RENAME_DIALOG_NEW_LABEL_TEXT;
import static djf.AppPropertyType.RENAME_DIALOG_OKBUTTON;
import djf.AppTemplate;
import djf.modules.AppLanguageModule;
import static djf.ui.style.DJFStyle.CLASS_RVMM_DIALOG_HEADER;
import static djf.ui.style.DJFStyle.CLASS_RVMM_DIALOG_LABEL;
import static djf.ui.style.DJFStyle.CLASS_RVMM_DIALOG_OK;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import mv.data.rvmmData;
import properties_manager.PropertiesManager;

/**
 *
 * @author Jihye
 */
public class MapMakerRenameDialog extends Stage{
    AppTemplate app;
    GridPane gridPane;
    
    Label headerLabel = new Label();
    Label newLabel = new Label();
    TextField newNameTextField = new TextField();
    Button okButton = new Button();
    
    public MapMakerRenameDialog(AppTemplate initApp){
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
        initGridNode(headerLabel,             RENAME_DIALOG_HEADER_LABEL,    CLASS_RVMM_DIALOG_HEADER,     0, 0, 3, 1, true);
        initGridNode(newLabel,                RENAME_DIALOG_NEW_LABEL,       CLASS_RVMM_DIALOG_LABEL,      1, 2, 1, 1, true);
        initGridNode(newNameTextField,        null,                          CLASS_RVMM_DIALOG_LABEL,      2, 2, 1, 1, false);
        initGridNode(okButton,                RENAME_DIALOG_OKBUTTON,           CLASS_RVMM_DIALOG_OK,      1, 3, 1, 1, true);
        
        gridPane.setPadding(new Insets(30, 20, 20, 20));
        gridPane.setVgap(5);
        gridPane.setHgap(50);
        gridPane.setHalignment(headerLabel, HPos.CENTER);
        
    }
    
    public void showRenameDialog(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String headerText = props.getProperty(RENAME_DIALOG_HEADER_LABEL_TEXT);
        headerLabel.setText(headerText);
        setTitle(headerText);
        headerLabel.setAlignment(Pos.CENTER_RIGHT);
        newLabel.setText(props.getProperty(RENAME_DIALOG_NEW_LABEL_TEXT));
        newNameTextField.setText("");
        okButton.setOnAction(e->{
            if(!newNameTextField.getText().equals("")){
                rvmmData data = (rvmmData) app.getDataComponent();
                File file = new File(data.getFilePath());
                try {
                    app.getFileModule().saveWork(file);
                    int index;
                    String newPath = file.getPath();
                    for(int j =0; j< 2; j++){
                        index = newPath.lastIndexOf("\\");
                        newPath = newPath.substring(0,index);
                    }
                    File newNameFile = new File(newPath+"\\"+ newNameTextField.getText()+"\\"+newNameTextField.getText()+".json");
                    newNameFile.mkdirs();
                    Files.copy(file.toPath(), newNameFile.toPath(), REPLACE_EXISTING);
                    app.getFileModule().loadWork(newNameFile);
                    if(file.exists()){
                        file.delete();
                        file.getParentFile().delete();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MapMakerRenameDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.hide();
        });
        
        showAndWait();
    }
}
