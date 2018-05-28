/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package mv.workspace;

import djf.AppPropertyType;
import static djf.AppPropertyType.LOAD_WORK_TITLE;
import djf.AppTemplate;
import static djf.AppTemplate.PATH_MAP;
import djf.ui.dialogs.MapMakerChangeDiemensionDialog;
import djf.ui.dialogs.MapMakerDialog;
import djf.ui.dialogs.MapMakerEditDialog;
import djf.ui.dialogs.MapMakerLoadDialog;
import djf.ui.dialogs.MapMakerRenameDialog;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author Jihye
 */
public class rvmmDialogController {
    AppTemplate app;
    
    MapMakerDialog mapMakerDialog;
    MapMakerChangeDiemensionDialog mapMakerChangeDialogs;
    MapMakerEditDialog mapMakerEditDialog;
    MapMakerLoadDialog mapMakerLoadDialog;
    MapMakerRenameDialog mapMakerRenameDialog;
    
    public rvmmDialogController(AppTemplate initApp) {
        app = initApp;
        mapMakerDialog = new MapMakerDialog(app);
        mapMakerChangeDialogs = new MapMakerChangeDiemensionDialog(app);
        mapMakerEditDialog = new MapMakerEditDialog(app); 
        mapMakerLoadDialog = new MapMakerLoadDialog(app);
        mapMakerRenameDialog = new MapMakerRenameDialog(app);
    }
    
    public void processCreatNewMap(){
        mapMakerDialog.showAddMapMakerDialog();
    }
    
    public void processChangeDimensions(){
        mapMakerChangeDialogs.showAddChangeDialog();
    }
    
    public void processEditSubregion(){
        mapMakerEditDialog.showAddMapMakerEditDialog();
    }
    
    public void processLoadFile(){
        mapMakerLoadDialog.showLoadMapMakerDialog();
    }
    
    public void processRename(){
        mapMakerRenameDialog.showRenameDialog();
    }
    
    public void processprocessAddImage(){
        
    }
    
    private void updateSaveButton(){
        app.getFileModule().markAsEdited(true);
    }
}
