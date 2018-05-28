/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.workspace;

import djf.AppPropertyType;
import djf.AppTemplate;
import djf.ui.dialogs.AppDialogsFacade;
import java.io.File;

/**
 *
 * @author Jihye
 */
public class rvmmButtonController {
      AppTemplate app;
      
      public rvmmButtonController(AppTemplate initApp){
          app = initApp;
      }
      
      public void processAddImage(){
          File file = AppDialogsFacade.showOpenDialog(app.getGUIModule().getWindow(), AppPropertyType.APP_TITLE);
          
      }
      
      
}
