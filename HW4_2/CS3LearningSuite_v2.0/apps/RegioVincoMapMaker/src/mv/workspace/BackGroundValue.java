/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.workspace;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;

/**
 *
 * @author Jihye
 */
public class BackGroundValue {
    private SimpleDoubleProperty backgroundGadient;
    private SimpleDoubleProperty focusAngle;
    private SimpleDoubleProperty focusDistance;
    private SimpleDoubleProperty centerX;
    private SimpleDoubleProperty centerY;
    private SimpleBooleanProperty proportional;
    private SimpleObjectProperty<CycleMethod> cycleMethod;
    private SimpleObjectProperty<Stop> Stop1;
    private SimpleObjectProperty<Stop> Stop2;
    
    public BackGroundValue(){
        backgroundGadient = new SimpleDoubleProperty();
        focusAngle = new SimpleDoubleProperty();
        focusDistance = new SimpleDoubleProperty();
        centerX = new SimpleDoubleProperty();
        centerY = new SimpleDoubleProperty();
        proportional= new SimpleBooleanProperty();
        cycleMethod= new SimpleObjectProperty<CycleMethod>();
        Stop1 = new SimpleObjectProperty<>();
        Stop2 = new SimpleObjectProperty<>();
    }

    /**
     * @return the backgroundGadient
     */
    public SimpleDoubleProperty getBackgroundGadient() {
        return backgroundGadient;
    }

    /**
     * @param backgroundGadient the backgroundGadient to set
     */
    public void setBackgroundGadient(double backgroundGadient) {
        this.backgroundGadient.set(backgroundGadient);
    }

    /**
     * @return the focusAngle
     */
    public SimpleDoubleProperty getFocusAngle() {
        return focusAngle;
    }

    /**
     * @param focusAngle the focusAngle to set
     */
    public void setFocusAngle(double focusAngle) {
        this.focusAngle.set(focusAngle);
    }

    /**
     * @return the focusDistance
     */
    public SimpleDoubleProperty getFocusDistance() {
        return focusDistance;
    }

    /**
     * @param focusDistance the focusDistance to set
     */
    public void setFocusDistance(double focusDistance) {
        this.focusDistance.set(focusDistance);
    }

    /**
     * @return the centerX
     */
    public SimpleDoubleProperty getCenterX() {
        return centerX;
    }

    /**
     * @param centerX the centerX to set
     */
    public void setCenterX(double centerX) {
        this.centerX.set(centerX);
    }

    /**
     * @return the centerY
     */
    public SimpleDoubleProperty getCenterY() {
        return centerY;
    }

    /**
     * @param centerY the centerY to set
     */
    public void setCenterY(double centerY) {
        this.centerY.set(centerY);
    }

    /**
     * @return the proportional
     */
    public SimpleBooleanProperty getProportional() {
        return proportional;
    }

    /**
     * @param proportional the proportional to set
     */
    public void setProportional(boolean proportional) {
        this.proportional.set(proportional);
    }

    /**
     * @return the cycleMethod
     */
    public SimpleObjectProperty<CycleMethod> getCycleMethod() {
        return cycleMethod;
    }

    /**
     * @param cycleMethod the cycleMethod to set
     */
    public void setCycleMethod(CycleMethod cycleMethod) {
        this.cycleMethod.set(cycleMethod);
    }

    /**
     * @return the Stop1
     */
    public SimpleObjectProperty<Stop> getStop1() {
        return Stop1;
    }

    /**
     * @param Stop1 the Stop1 to set
     */
    public void setStop1(Stop Stop1) {
        this.Stop1.set(Stop1);
    }

    /**
     * @return the Stop2
     */
    public SimpleObjectProperty<Stop> getStop2() {
        return Stop2;
    }

    /**
     * @param Stop2 the Stop2 to set
     */
    public void setStop2(Stop Stop2) {
        this.Stop2.set(Stop2);
    }
}
