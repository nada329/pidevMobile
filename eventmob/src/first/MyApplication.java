package first;

import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;
import java.io.IOException;

public class MyApplication {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initNamedTheme("/theme", "Theme");

        Toolbar.setGlobalToolbar(true);
        Toolbar.setCenteredDefault(false);
        
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        new LoginForm(theme).show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }
    
    public void destroy() {
    }

}