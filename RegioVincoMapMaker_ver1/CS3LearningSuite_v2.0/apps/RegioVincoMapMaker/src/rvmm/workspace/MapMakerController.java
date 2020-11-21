/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvmm.workspace;
import djf.AppTemplate;
//import mv.workspace.dialogs.MapMakerDialog;

/**
 *
 * @author Jihye
 */
public class MapMakerController {
    AppTemplate app;
    MapMakerDialog mapMakerDialog;
    
    public MapMakerController(AppTemplate initApp){
         app = initApp;
         mapMakerDialog = new MapMakerDialog(app);
         
    }
    
//    public void process
    public void processChangeDimension(){
        
    }
    
    
    
    
}
