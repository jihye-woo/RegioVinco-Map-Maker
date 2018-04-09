/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.workspace;

import djf.AppTemplate;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Jihye
 */
public class MapViewerController {
    AppTemplate app;
    double maxX = -180;
    double maxY = -90;
    double minX = 180;
    double minY = 90;
  
    public MapViewerController(AppTemplate initApp){
        app = initApp;
    }
    
     public void processFitToPoly(Pane mapPane, Button FitToPoly){
         FitToPoly.setOnAction(e->{
            for(int i=1;i<mapPane.getChildren().size();i++){
                Polygon p = (Polygon) mapPane.getChildren().get(i);
                ObservableList<Double> xyvalues = p.getPoints();
                for(int j=0; j<xyvalues.size(); j+=2){
                    double x = xyvalues.get(j);
                    double y = xyvalues.get(j+1);
                    if(maxX < x){ maxX = x;}
                    if(minX > xyvalues.get(j)){ minX = x;}
                    if(maxY < xyvalues.get(j+1)){ maxY = y; }
                    if(minY > xyvalues.get(j+1)){ minY = y;}
                }
            }
            
            mapPane.setTranslateX(0);
            mapPane.setTranslateY(0);
            System.out.println(mapPane.getTranslateX());
        });
    }
    
    public void processResetZoom(Pane mapPane, Button ResetZoom){
        ResetZoom.setOnAction(e->{
            mapPane.setScaleX(1.0);
            mapPane.setScaleY(1.0);
            System.out.println(mapPane.getTranslateX());
        });
        
    }
    public void processZoomOut(MapViewerWorkspace mv, Pane mapPane, Button ZoomOut){
        ZoomOut.setOnAction(e->{
            mapPane.setScaleX(mapPane.getScaleX()*0.5);
            mapPane.setScaleY(mapPane.getScaleY()*0.5);
            
//            mv.viewPortX += mapPane.getTranslateX();
//            mv.viewPortY += mapPane.getTranslateY();
        });
    }
    
    public void processZoomIn(MapViewerWorkspace mv, Pane mapPane, Button ZoomIn){
         ZoomIn.setOnAction(e->{
            mapPane.setScaleX(mapPane.getScaleX()*2);
            mapPane.setScaleY(mapPane.getScaleY()*2);
            
//            mv.viewPortX += mapPane.getTranslateX();
//            mv.viewPortY += mapPane.getTranslateY();
        });
    }
    
    public void processMoveLeft(Pane mapPane, Button moveLeft){
        moveLeft.setOnAction(e->{
            mapPane.setTranslateX(mapPane.getTranslateX()+50);
            System.out.println(mapPane.getTranslateX());
        });
    }
    public void processMoveRight(Pane mapPane, Button moveRight){
        moveRight.setOnAction(e->{
            mapPane.setTranslateX(mapPane.getTranslateX()-50);
            System.out.println(mapPane.getTranslateX());
        });
    }
    public void processMoveUp(Pane mapPane, Button moveUp){
         moveUp.setOnAction(e->{
           mapPane.setTranslateY(mapPane.getTranslateY()+50);
        });
    }
    public void processMoveDown(Pane mapPane, Button moveDown){
        moveDown.setOnAction(e->{
            mapPane.setTranslateY(mapPane.getTranslateY()-50);
        });
    }
    public void MovingMouseInMapPane(MapViewerWorkspace mv, double changedValue){
        
    }
    
    
    public String fronthtmlCode(){
         return "<!DOCTYPE html>\n" +
"<html>\n" +
"<head>\n" +
"<style>\n" +
"* {\n" +
"    box-sizing: border-box;\n" +
"}\n" +
"\n" +
".column1 {\n" +
"	font-family :\"Times New Roman\";\n" +
"    float: left;\n" +
"    line-height: 150%;\n" +
"    width: 75%;\n" +
"}\n" +
".column2{\n" +
"font-family :\"Times New Roman\";\n" +
" float: left;\n" +
" line-height: 150%;\n" +
" width: 25%;\n" +
"}\n" +
"\n" +
"</style>\n" +
"</head>\n" +
"\n" +
"<body>\n" +
"\n" +
"<div class =\"row\">\n" +
"  <div class =\"column1\">\n" +
"    <b>\n" +
"    Scale:<br>\n" +
"    Viewport Width:<br>\n" +
"    ViewPort Height:<br>\n" +
"    World Width:<br>\n" +
"    World Height:<br>\n" +
"    World Mouse X:<br>\n" +
"    World Mouse Y:<br>\n" +
"    Viewprot Mouse Percent X:<br>\n" +
"    Viewprot Mouse Percent Y:<br>\n" +
"    World ViewportX:<br>\n" +
"    World ViewportY:<br>\n" +
"    # of Polygon Points:<br>\n" +
"  </b>\n" +
"  </div>\n" +
"  <div class =\"column2\">";
    }
    
   public String rearhtmlCode(){
      return "    \n" +
"  </div>\n" +
"</div>\n" +
"</body>\n" +
"</html>";
       
   }
    
}
