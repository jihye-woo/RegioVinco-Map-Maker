/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package mv.rvmmDialogs;

import djf.AppPropertyType;
import static djf.AppPropertyType.CREATENEW_DIALOG_DATACHOICE_BUTTON;
import static djf.AppPropertyType.CREATENEW_DIALOG_DATACHOICE_LABEL;
import static djf.AppPropertyType.CREATENEW_DIALOG_HEADER_LABEL;
import static djf.AppPropertyType.CREATENEW_DIALOG_HEADER_LABEL_TEXT;
import static djf.AppPropertyType.CREATENEW_DIALOG_OKBUTTON;
import static djf.AppPropertyType.CREATENEW_DIALOG_PARENTS_BUTTON;
import static djf.AppPropertyType.CREATENEW_DIALOG_PARENTS_LABEL;
import static djf.AppPropertyType.CREATENEW_DIALOG_REGIONAME_LABEL;
import static djf.AppPropertyType.SAVE_ERROR_CONTENT;
import static djf.AppPropertyType.SAVE_ERROR_TITLE;
import djf.AppTemplate;
import static djf.AppTemplate.PATH_WORK;
import djf.modules.AppLanguageModule;
import djf.ui.controllers.AppFileController;
import djf.ui.dialogs.AppDialogsFacade;
import static djf.ui.style.DJFStyle.CLASS_RVMM_DIALOG_HEADER;
import static djf.ui.style.DJFStyle.CLASS_RVMM_DIALOG_LABEL;
import static djf.ui.style.DJFStyle.CLASS_RVMM_DIALOG_OK;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import javafx.event.ActionEvent;
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
import javafx.stage.DirectoryChooser;
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
    boolean textfieldNotEmpty = false;
    Button ParentRegionChoice = new Button();
    boolean parentchoosen = false;
    Label ParentRegionChoiceLabel = new Label();
    Button dataChoice = new Button();
    boolean datachoosen = false;
    Label dataChoiceLabel = new Label();
    HBox dialogPane = new HBox();
    Button okButton = new Button();
    String filePath;
    File fileForDirectory;
    File fileForData;
    File f;
    AppFileController controller;
    
    public MapMakerDialog(AppTemplate initApp){
        app = initApp;
        
        gridPane = new GridPane();
        initDialog();

        Scene scene = new Scene(gridPane);
        this.setScene(scene);
        controller = new AppFileController(app);
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
        okButton.setDisable(true);
        gridPane.setPadding(new Insets(30, 20, 20, 20));
        gridPane.setVgap(5);
        gridPane.setHgap(50);
        gridPane.setHalignment(headerLabel, HPos.CENTER);
        
    }
    public void okButtonUpdate(){
        if(datachoosen && parentchoosen){
              okButton.setDisable(false);
        }
        else{
             okButton.setDisable(true);
        }
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
        okButton.setText("OK");
        
        ParentRegionChoice.setOnAction((ActionEvent e)->{
            fileForDirectory = showOpenDirectoryChooserDialog(this, CREATENEW_DIALOG_PARENTS_BUTTON);
                if(fileForDirectory !=null){
                    ParentRegionChoiceLabel.setText(fileForDirectory.getAbsolutePath());
                    parentchoosen = true;
                }
                okButtonUpdate();
        });
        dataChoice.setText("Choose Data File");
        dataChoiceLabel.setText("Data File Not Chosen");
        dataChoice.setOnAction(e->{
           fileForData = AppDialogsFacade.showOpenDialog(this, CREATENEW_DIALOG_DATACHOICE_BUTTON);
                if(fileForData !=null && parentchoosen == true){
                    dataChoiceLabel.setText(fileForData.getName());
                    dataChoiceLabel.setText(fileForData.getAbsolutePath());
                    datachoosen = true;
                }
                okButtonUpdate();
        });
        okButton.setOnAction((ActionEvent e)->{
                try {
                    int index = fileForData.getAbsolutePath().lastIndexOf("/");
                    String fileName = fileForData.getAbsolutePath().substring(index + 1);
                    if(regionNameTextField.getText()!=null){
                       fileName = regionNameTextField.getText();
                    }
                    filePath =  fileForDirectory.getAbsolutePath()+ "/"+ fileName;
                    f = new File(filePath, fileName +".json");
                    f.mkdirs();
                    Files.copy(fileForData.toPath(), f.toPath(), REPLACE_EXISTING);
                     app.getFileComponent().loadData(app.getDataComponent(), f.getAbsolutePath());
                } catch (IOException ex) {
    //                AppDialogsFacade.showMessageDialog(app.getGUIModule().getWindow(), SAVE_ERROR_TITLE, SAVE_ERROR_CONTENT);
                }
            hide();
           
        });
        showAndWait();
    }
      public static File showOpenDirectoryChooserDialog(Stage window, AppPropertyType openTitleProp){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(PATH_WORK));
        File selectedDirectory = directoryChooser.showDialog(window);
        return selectedDirectory;
    }
}
