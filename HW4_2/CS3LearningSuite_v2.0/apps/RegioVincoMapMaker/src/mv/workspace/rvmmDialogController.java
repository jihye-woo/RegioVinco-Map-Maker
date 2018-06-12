/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package mv.workspace;

import djf.AppTemplate;
import javafx.scene.shape.Polygon;
import mv.rvmmDialogs.MapMakerChangeDiemensionDialog;
import mv.rvmmDialogs.MapMakerDialog;
import mv.rvmmDialogs.MapMakerEditDialog;
import mv.rvmmDialogs.MapMakerRenameDialog;

/**
 *
 * @author Jihye
 */
public class rvmmDialogController {
    AppTemplate app;
    
    MapMakerDialog mapMakerDialog;
    MapMakerChangeDiemensionDialog mapMakerChangeDialogs;
    MapMakerEditDialog mapMakerEditDialog;
    MapMakerRenameDialog mapMakerRenameDialog;
    
    public rvmmDialogController(AppTemplate initApp) {
        app = initApp;
        mapMakerDialog = new MapMakerDialog(app);
        mapMakerChangeDialogs = new MapMakerChangeDiemensionDialog(app);
        mapMakerEditDialog = new MapMakerEditDialog(app); 
        mapMakerRenameDialog = new MapMakerRenameDialog(app);
    }
    
    public void processCreatNewMap(){
        mapMakerDialog.showAddMapMakerDialog();
    }
    
    public void processChangeDimensions(){
        mapMakerChangeDialogs.showChangeDiemensionDialog();
    }
    
    public void processEditSubregion(Polygon p){
        mapMakerEditDialog.showMapMakerEditDialog();
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
