/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.workspace;

import djf.AppPropertyType;
import static djf.AppPropertyType.APP_FILE_FOOLPROOF_SETTINGS;
import static djf.AppPropertyType.APP_TITLE;
import static djf.AppPropertyType.LOAD_ERROR_CONTENT;
import static djf.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.AppPropertyType.LOAD_WORK_TITLE;
import static djf.AppPropertyType.SAVE_ERROR_CONTENT;
import static djf.AppPropertyType.SAVE_ERROR_TITLE;
import static djf.AppPropertyType.SAVE_SUCCESS_CONTENT;
import static djf.AppPropertyType.SAVE_SUCCESS_TITLE;
import djf.AppTemplate;
import static djf.AppTemplate.PATH_WORK;
import djf.components.AppDataComponent;
import djf.modules.AppFileModule;
import djf.ui.dialogs.AppDialogsFacade;
import java.io.File;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import mv.RegioVincoMapMakerApp;
import mv.data.rvmmData;
import mv.files.rvmmFiles;
import static mv.workspace.style.MapViewerStyle.CLASS_RVMM_SELECTEDIMAGE;
import static mv.rvmmDialogs.helperDialog.showOpenParentsDialog;
import properties_manager.PropertiesManager;

/**
 *
 * @author Jihye
 */
public class rvmmButtonController {
    AppTemplate app;
    double maxX = -1;
    double maxY = -1;
    double minX = 9999;
    double minY = 9999;
    double dX =0;
    double dY =0;
    double locationX = 0;
    double locationY = 0;
    rvmmData data;
    
      
      public rvmmButtonController(RegioVincoMapMakerApp initApp){
          app = initApp;
          data = new rvmmData((RegioVincoMapMakerApp) app);
      }
      
      
      public void processRemoveImage(Pane leftArea, ImageView selectedImage){
          if(leftArea.getChildren().contains(selectedImage)){
           leftArea.getChildren().remove(selectedImage);
          }
      }
      
      public void processResetViewport(Pane mapPane, Button resetviewport){
          mapPane.setScaleX(1);
          mapPane.setScaleY(1);
          mapPane.setTranslateX(0);
          mapPane.setTranslateY(0);
      }
      
      public void processFitToPoly(Pane mapPane, Button FitToPoly){
            for(int i=1;i<mapPane.getChildren().size();i++){
                Polygon p = (Polygon) mapPane.getChildren().get(i);
                ObservableList<Double> xyvalues = p.getPoints();
                for(int j=0; j<xyvalues.size(); j+=2){
                    double x = xyvalues.get(j);
                    double y = xyvalues.get(j+1);
                    if(maxX < x){ maxX = x;}
                    if(minX > x){ minX = x;}
                    if(maxY < y){ maxY = y;}
                    if(minY > y){ minY = y;}
                }
            }
            dX = maxX - minX;
            dY = maxY - minY;
            double newScale = 1;
            double newX = (maxX-(dX/2));
            double newY = (maxY-(dY/2));
            if(dX > dY){
                newScale = ((mapPane.getWidth()/dX))*0.5;
            }
            else{
                newScale = ((mapPane.getHeight()/dY))*0.5;
            }
            
            if(newScale >= mapPane.getScaleX()){ // should be zoom in
                mapPane.setScaleX(newScale);
                mapPane.setScaleY(newScale);
                mapPane.setTranslateX((((mapPane.getWidth()/2)-newX)*newScale)-250);
                mapPane.setTranslateY((((mapPane.getHeight()/2)-newY)*newScale));
            }
            else{
                mapPane.setTranslateX(-(((mapPane.getWidth()/2)-newX)*mapPane.getScaleX())-250);
                mapPane.setTranslateY(-(((mapPane.getHeight()/2)-newY)*mapPane.getScaleY()));
                mapPane.setScaleX(newScale);
                mapPane.setScaleY(newScale);
            }
    }
  
}
