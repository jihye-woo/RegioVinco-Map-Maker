/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package mv.workspace;

import djf.AppTemplate;
import djf.ui.dialogs.MapMakerChangeDiemensionDialog;
import djf.ui.dialogs.MapMakerDialog;
import djf.ui.dialogs.MapMakerEditDialog;

/**
 *
 * @author Jihye
 */
public class MapViewerController {
    AppTemplate app;
    
    MapMakerDialog mapMakerDialog;
    MapMakerChangeDiemensionDialog mapMakerChangeDialogs;
    MapMakerEditDialog mapMakerEditDialog;
    
    public MapViewerController(AppTemplate initApp) {
        app = initApp;
        mapMakerDialog = new MapMakerDialog(app);
        mapMakerChangeDialogs = new MapMakerChangeDiemensionDialog(app);
        mapMakerEditDialog = new MapMakerEditDialog(app);
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
    
    private void updateSaveButton(){
        app.getFileModule().markAsEdited(true);
    }
}
