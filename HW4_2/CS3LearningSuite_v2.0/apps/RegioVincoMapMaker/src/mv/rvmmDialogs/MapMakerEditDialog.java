/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package mv.rvmmDialogs;

import static djf.AppPropertyType.EDITSUB_DIALOG_CAPTIAL_LABEL;
import static djf.AppPropertyType.EDITSUB_DIALOG_FALG_IMAGE;
import static djf.AppPropertyType.EDITSUB_DIALOG_FLAG_LABEL;
import static djf.AppPropertyType.EDITSUB_DIALOG_HEADER_LABEL;
import static djf.AppPropertyType.EDITSUB_DIALOG_HEADER_LABEL_TEXT;
import static djf.AppPropertyType.EDITSUB_DIALOG_LEADER_LABEL;
import static djf.AppPropertyType.EDITSUB_DIALOG_NEXT_BUTTON;
import static djf.AppPropertyType.EDITSUB_DIALOG_OKBUTTON;
import static djf.AppPropertyType.EDITSUB_DIALOG_PREV_BUTTON;
import static djf.AppPropertyType.EDITSUB_DIALOG_SUBREIONNAME_LABEL;
import djf.AppTemplate;
import static djf.AppTemplate.PATH_FLAGS;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import properties_manager.PropertiesManager;

/**
 *
 * @author Jihye
 */
public class MapMakerEditDialog extends Stage{
    AppTemplate app;
    GridPane gridPane;
    
    Label headerLabel = new Label();
    Button prev = new Button();
    Button next = new Button();
    Label regionNameLabel = new Label();
    TextField regionNameTextField = new TextField();
    Label captialLabel = new Label();
    TextField captialTextField = new TextField();
    Label leaderLabel = new Label();
    TextField leaderTextField = new TextField();
    Label flagLabel = new Label();
    Button okButton = new Button();
    Image image;
    ImageView imageView = new ImageView();
    
    public MapMakerEditDialog(AppTemplate initApp){
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
    
    private void initDialog() {
        image = new Image("File:" + PATH_FLAGS + "Albania Flag.png");
        imageView.setImage(image);
        initGridNode(imageView,              EDITSUB_DIALOG_FALG_IMAGE,          CLASS_RVMM_DIALOG_LABEL,        2, 5, 1, 1, false);
        
        initGridNode(headerLabel,             EDITSUB_DIALOG_HEADER_LABEL,         CLASS_RVMM_DIALOG_HEADER,      0, 0, 3, 1, true);
        initGridNode(prev,                    EDITSUB_DIALOG_PREV_BUTTON,          CLASS_RVMM_DIALOG_LABEL,      1, 1, 1, 1, true);
        initGridNode(next,                    EDITSUB_DIALOG_NEXT_BUTTON,           CLASS_RVMM_DIALOG_LABEL,     2, 1, 1, 1, true);
        initGridNode(regionNameLabel,         EDITSUB_DIALOG_SUBREIONNAME_LABEL,     CLASS_RVMM_DIALOG_LABEL,    1, 2, 1, 1, true);
        initGridNode(regionNameTextField,     null,                                  CLASS_RVMM_DIALOG_LABEL,     2, 2, 1, 1, false);
        initGridNode(captialLabel,            EDITSUB_DIALOG_CAPTIAL_LABEL,         CLASS_RVMM_DIALOG_LABEL,      1, 3, 1, 1, true);
        initGridNode(captialTextField,        null,                                 CLASS_RVMM_DIALOG_LABEL,      2, 3, 1, 1, false);
        initGridNode(leaderLabel,             EDITSUB_DIALOG_LEADER_LABEL,          CLASS_RVMM_DIALOG_LABEL,      1, 4, 1, 1, true);
        initGridNode(leaderTextField,         null,                                 CLASS_RVMM_DIALOG_LABEL,      2, 4, 1, 1, false);
        initGridNode(flagLabel,               EDITSUB_DIALOG_FLAG_LABEL,            CLASS_RVMM_DIALOG_LABEL,      1, 5, 1, 1, true);
        initGridNode(okButton,                EDITSUB_DIALOG_OKBUTTON,              CLASS_RVMM_DIALOG_OK,        2, 6, 1, 1, true);
        
//        AppLanguageModule languageSettings = app.getLanguageModule();
//        languageSettings.addLabeledControlProperty(EDITSUB_DIALOG_PREV_BUTTON + "_TEXT",       prev.textProperty());
//        languageSettings.addLabeledControlProperty(EDITSUB_DIALOG_NEXT_BUTTON + "_TEXT",    next.textProperty());
//        languageSettings.addLabeledControlProperty(EDITSUB_DIALOG_OKBUTTON + "_TEXT",    okButton.textProperty());
//        app.getGUIModule().addGUINode(EDITSUB_DIALOG_PREV_BUTTON, prev);
//        app.getGUIModule().addGUINode(EDITSUB_DIALOG_NEXT_BUTTON, next);
//        app.getGUIModule().addGUINode(EDITSUB_DIALOG_OKBUTTON, okButton);
        gridPane.setPadding(new Insets(30, 20, 20, 20));
        gridPane.setVgap(5);
        gridPane.setHgap(50);
        gridPane.setHalignment(headerLabel, HPos.CENTER);
        
        okButton.setOnAction(e->{
            this.hide();
        });
        
    }
    
    public void showAddMapMakerEditDialog(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String headerText = props.getProperty(EDITSUB_DIALOG_HEADER_LABEL_TEXT);
        headerLabel.setText(headerText);
        setTitle(headerText);
        headerLabel.setAlignment(Pos.CENTER_RIGHT);
        
        regionNameTextField.setText("");
        showAndWait();
    }
    
}
