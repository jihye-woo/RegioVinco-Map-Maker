/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.data;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

/**
 *
 * @author Jihye
 */
public class ColorAndThicknessInfo {
    DoubleProperty LINE_THICKNESS;
    double line_thickness;
    ObjectProperty LINE_COLOR;
    Color line_color;
    
    public ColorAndThicknessInfo(){
        this.LINE_THICKNESS = new SimpleDoubleProperty();
        this.LINE_COLOR = new SimpleObjectProperty();
        LINE_THICKNESS.setValue(0.05);
        line_thickness= 0.05;
        line_color = Color.BLACK;
        LINE_COLOR.setValue(Color.BLACK);
    }
    
    public void changeColor(Color lineColor){
        LINE_COLOR.setValue(lineColor);
        line_color = lineColor;
    }
    public void changeThinkness(double Thinkness){
        LINE_THICKNESS.setValue(Thinkness);
        line_thickness = Thinkness;
    }
    public Color getColor(){
        return line_color;
    }
    public double getThickness(){
        return line_thickness;
    }
}
