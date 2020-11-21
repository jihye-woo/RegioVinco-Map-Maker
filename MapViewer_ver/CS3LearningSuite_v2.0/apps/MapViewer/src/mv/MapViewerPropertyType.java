package mv;

/**
 * This class provides the properties that are needed to be loaded for
 * setting up the Map Viewer workspace controls including language-dependent
 * text.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public enum MapViewerPropertyType {
   // THIS IS THE MV WORKSPACE PANE
         
    // THIS IS THE MV WORKSPACE PANE
    MV_MAP_PANE, MV_MAP_VBOX, MV_MAP_VBOX_LABEL,
    MV_MAP_HBOX1, MV_MAP_HBOX2,
    MV_FITPLOY_BUTTON, MV_RESET_ZOOM_BUTTON,
    MV_ZOOM_IN_BUTTON, MV_ZOOM_OUT_BUTTON,
    MV_MOVE_LEFT_BUTTON, MV_MOVE_RIGHT_BUTTON,
    MV_MOVE_UP_BUTTON, MV_MOVE_DOWN_BUTTON,
    MV_LABEL, MV_WEBVIEW,
//    MV_MAP_LEFTCOL, MV_MAP_RIGHTCOL,

    // FOOLPROOF SETTINGS
    MV_FOOLPROOF_SETTINGS,
        
    // THIS IS THE EXPORT TEMPLATE FILE NAME
    MV_EXPORT_TEMPLATE_FILE_NAME
}