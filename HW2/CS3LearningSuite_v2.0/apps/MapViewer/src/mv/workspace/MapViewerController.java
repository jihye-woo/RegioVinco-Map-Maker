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
import static mv.MapViewerPropertyType.MV_RESET_ZOOM_BUTTON;
import static mv.MapViewerPropertyType.MV_ZOOM_OUT_BUTTON;

/**
 *
 * @author Jihye
 */
public class MapViewerController {
    AppTemplate app;
    double maxX = -1;
    double maxY = -1;
    double minX = 9999;
    double minY = 9999;
    double dX =0;
    double dY =0;
    
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
                newScale = ((mapPane.getWidth()/dX))*0.6;
            }
            else{
                newScale = ((mapPane.getHeight()/dY))*0.6;
            }
            
            if(newScale >= mapPane.getScaleX()){ // should be zoom in
                mapPane.setScaleX(newScale);
                mapPane.setScaleY(newScale);
                mapPane.setTranslateX((((mapPane.getWidth()/2)-newX)*newScale)-100);
                mapPane.setTranslateY((((mapPane.getHeight()/2)-newY)*newScale));
            }
            else{
                mapPane.setTranslateX(-(((mapPane.getWidth()/2)-newX)*mapPane.getScaleX())-100);
                mapPane.setTranslateY(-(((mapPane.getHeight()/2)-newY)*mapPane.getScaleY()));
                mapPane.setScaleX(newScale);
                mapPane.setScaleY(newScale);
            }
            app.getGUIModule().getGUINode(MV_RESET_ZOOM_BUTTON).setDisable(false);
            app.getGUIModule().getGUINode(MV_ZOOM_OUT_BUTTON).setDisable(false);
        });
    }
    
    public void processResetZoom(Pane mapPane, Button ResetZoom){
        ResetZoom.setOnAction(e->{
            mapPane.setScaleX(1.0);
            mapPane.setScaleY(1.0);
            mapPane.setTranslateX(0);
            mapPane.setTranslateY(0);
            app.getGUIModule().getGUINode(MV_RESET_ZOOM_BUTTON).setDisable(true);
            app.getGUIModule().getGUINode(MV_ZOOM_OUT_BUTTON).setDisable(true);
        });
    }
    public void processZoomOut(Pane mapPane, Button ZoomOut){
        ZoomOut.setOnAction(e->{
            if(mapPane.getScaleX() > 1){
                mapPane.setScaleX(mapPane.getScaleX()*0.5);
                mapPane.setScaleY(mapPane.getScaleY()*0.5);
                app.getGUIModule().getGUINode(MV_RESET_ZOOM_BUTTON).setDisable(false);
            }
            if(mapPane.getScaleX() <= 1){
                app.getGUIModule().getGUINode(MV_ZOOM_OUT_BUTTON).setDisable(true);
            }
        });
    }
    
    public void processZoomIn(Pane mapPane, Button ZoomIn){
        ZoomIn.setOnAction(e->{
            mapPane.setScaleX(mapPane.getScaleX()*2);
            mapPane.setScaleY(mapPane.getScaleY()*2);
            app.getGUIModule().getGUINode(MV_RESET_ZOOM_BUTTON).setDisable(false);
            app.getGUIModule().getGUINode(MV_ZOOM_OUT_BUTTON).setDisable(false);
        });
    }
    
    public double getWorldViewPortX(Pane mapPane){
        return((mapPane.getWidth()/2 - (mapPane.getWidth()/mapPane.getScaleX())/2 ) - mapPane.getTranslateX()/mapPane.getScaleX());
    }
    public double getWorldViewPortY(Pane mapPane){
        return ((mapPane.getHeight()/2 - (mapPane.getHeight()/mapPane.getScaleY())/2 ) - mapPane.getTranslateY()/mapPane.getScaleY());
    }
    
    public void processMoveLeft(Pane mapPane, Button moveLeft){
        moveLeft.setOnAction(e->{
            mapPane.setTranslateX(mapPane.getTranslateX()+50);
            System.out.println(mapPane.getTranslateX());
            app.getGUIModule().getGUINode(MV_RESET_ZOOM_BUTTON).setDisable(false);
        });
    }
    public void processMoveRight(Pane mapPane, Button moveRight){
        moveRight.setOnAction(e->{
            mapPane.setTranslateX(mapPane.getTranslateX()-50);
            System.out.println(mapPane.getTranslateX());
            app.getGUIModule().getGUINode(MV_RESET_ZOOM_BUTTON).setDisable(false);
        });
    }
    public void processMoveUp(Pane mapPane, Button moveUp){
        moveUp.setOnAction(e->{
            mapPane.setTranslateY(mapPane.getTranslateY()+50);
            app.getGUIModule().getGUINode(MV_RESET_ZOOM_BUTTON).setDisable(false);
        });
    }
    public void processMoveDown(Pane mapPane, Button moveDown){
        moveDown.setOnAction(e->{
            mapPane.setTranslateY(mapPane.getTranslateY()-50);
            app.getGUIModule().getGUINode(MV_RESET_ZOOM_BUTTON).setDisable(false);
        });
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
