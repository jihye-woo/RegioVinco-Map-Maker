/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvmmDialogs;

import djf.AppPropertyType;
import static djf.AppPropertyType.LOAD_WORK_TITLE;
import static djf.AppTemplate.PATH_MAP;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author Jihye
 */
public class helperDialog {
    public static File showOpenParentsDialog(Stage window, AppPropertyType openTitleProp) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_MAP));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
        File selectedFile = fc.showOpenDialog(window);    
        return selectedFile;
    }
}
