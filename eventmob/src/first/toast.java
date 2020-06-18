package first;

import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.components.ToastBar.Status;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import java.util.Random;

public class toast {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Log.bindCrashProtection(true);
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        
        //ToastBar.getInstance().setPosition(Component.TOP);  
        Form hi = new Form("Status ");
        hi.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        Button task3Btn = new Button("Do Task 3");
        task3Btn.addActionListener((evt)->{
            Status status = ToastBar.getInstance().createStatus();
            status.setMessage("Working on Task with Progress");
            status.setProgress(0);
            status.setIcon(createIcon(FontImage.MATERIAL_BACKUP));
            status.show();
            
            for (int progress=0; progress <= 100; progress += 10) {
                Display.getInstance().invokeAndBlock(()->{Util.sleep(500);});
                status.setProgress(progress);
                status.show();
                if (progress == 100) {
                    status.setIcon(createIcon(FontImage.MATERIAL_DONE));
                    status.setProgress(-1);
                    status.setExpires(3000);
                    status.setMessage("The task is now complete");
                    status.show();
                }
            }
        });
        
        Label task3Heading = new Label("Task 3");
        task3Heading.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        hi.addComponent(task3Heading);
        hi.addComponent(new SpanLabel("A task that uses a progress bar to show the user how far along it is"));
        hi.addComponent(task3Btn);
        hi.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }

    private Image createIcon(char charcode) {
        int iconWidth = Display.getInstance().convertToPixels(8, true);
        Style iconStyle = new Style();
        Font iconFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
        if (Font.isNativeFontSchemeSupported()) {
            iconFont = Font.createTrueTypeFont("native:MainBold", null).derive((int)(iconWidth * 0.5), Font.STYLE_BOLD);
        }
        iconStyle.setFont(iconFont);
        iconStyle.setFgColor(0xffffff);
        iconStyle.setBgTransparency(0);

        FontImage completeIcon = FontImage.createMaterial(charcode, iconStyle);
        return completeIcon;
    }
}