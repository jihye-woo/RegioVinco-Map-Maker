package mv.clipboard;

import djf.components.AppClipboardComponent;
import mv.MapViewerApp;

/**
 * WE AREN'T USING THIS CLASS, BUT COULD IF WE WANTED
 * TO CUT AND PASTE POLYGONS 
 */
public class MapViewerClipboard implements AppClipboardComponent {
    MapViewerApp app;
    
    public MapViewerClipboard(MapViewerApp initApp) {
        app = initApp;
    }
    
    @Override
    public void cut() {
        
    }

    @Override
    public void copy() {
        
    }
    
    @Override
    public void paste() {
        
    }    


    @Override
    public boolean hasSomethingToCut() {
        return false;
    }

    @Override
    public boolean hasSomethingToCopy() {
        return false;
    }

    @Override
    public boolean hasSomethingToPaste() {
        return false;
    }
}