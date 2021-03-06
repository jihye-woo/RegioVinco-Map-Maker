package mv.workspace.style;



/**
 * This class lists all CSS style types for this application. These
 * are used by JavaFX to apply style properties to controls like
 * buttons, labels, and panes.

 * @author Richard McKenna
 * @version 1.0
 */
public class MapViewerStyle {
    public static final String EMPTY_TEXT = "";
    public static final int BUTTON_TAG_WIDTH = 75;

    // THESE CONSTANTS ARE FOR TYING THE PRESENTATION STYLE OF
    // THIS M3Workspace'S COMPONENTS TO A STYLE SHEET THAT IT USES
    // NOTE THAT FOUR CLASS STYLES ALREADY EXIST:
    // top_toolbar, toolbar, toolbar_text_button, toolbar_icon_button

    public static final String CLASS_MV_MAP_CLIP_PANE = "mv_map_clip_pane";
    public static final String CLASS_MV_MAP_OCEAN = "mv_map_ocean";
    public static final String CLASS_MV_MAP_VBOX ="mv_map_vbox";
    public static final String CLASS_MV_MAP_HBOX ="mv_map_hbox";
    public static final String CLASS_MV_MAP_ICON = "mv_map_hbox_icon";
    public static final String CLASS_MV_MAP_VBOX_LABEL = "mv_map_vbox_label";
    public static final String CLASS_MV_MAP = "mv_map";
    public static final String CLASS_MV_MAP_SELECTED = "mv_map_selected";
}