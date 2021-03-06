package first;

import com.codename1.capture.Capture;

import com.codename1.components.MultiButton;

import com.codename1.io.FileSystemStorage;

import static com.codename1.ui.CN.*;

import com.codename1.ui.Display;

import com.codename1.ui.Form;

import com.codename1.ui.Dialog;

import com.codename1.ui.Label;

import com.codename1.ui.plaf.UIManager;

import com.codename1.ui.util.Resources;

import com.codename1.io.Log;

import com.codename1.ui.Toolbar;

import java.io.IOException;

import com.codename1.ui.layouts.BoxLayout;

import com.codename1.io.NetworkEvent;

import com.codename1.io.Util;

import com.codename1.l10n.SimpleDateFormat;

import com.codename1.media.Media;

import com.codename1.media.MediaManager;

import com.codename1.ui.FontImage;

import com.codename1.ui.plaf.Style;

import java.util.Date;



public class media {

    private Form current;
    private Resources theme;

    

public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if (err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });
    }

    public void start(Resources res) {
        if (current != null) {
            current.show();
            return;
        }
        Form hi = new Form("Capture", BoxLayout.y());
        hi.setToolbar(new Toolbar());
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_MIC, s);
        hi.getToolbar().addCommandToLeftBar("back", null, e -> new ProfileForm(res).show());

       
 FileSystemStorage fs = FileSystemStorage.getInstance();
     
   String recordingsDir = fs.getAppHomePath() + "recordings/";
   
     fs.mkdir(recordingsDir);
     
   try {
            for (String file : fs.listFiles(recordingsDir)) {
                MultiButton mb = new MultiButton(file.substring(file.lastIndexOf("/") + 1));
 
               mb.addActionListener((e) -> {
                    try {
                        Media m = MediaManager.createMedia(recordingsDir + file, false);
  
                      m.play();
                    } catch (Throwable err) {
                        Log.e(err);
                    }
                });

                hi.add(mb);
            }

            hi.getToolbar().addCommandToRightBar("", icon, (ev) -> {
                try {
                    String file = Capture.captureAudio();
                    if (file != null) {
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MMM-dd-kk-mm");
                        String fileName = sd.format(new Date());
                        String filePath = recordingsDir + fileName;
                        Util.copy(fs.openInputStream(file), fs.openOutputStream(filePath));
                        MultiButton mb = new MultiButton(fileName);
                        mb.addActionListener((e) -> {
                            try {
                                Media m = MediaManager.createMedia(filePath, false);
                                m.play();
                            } catch (IOException err) {
                                Log.e(err);
                            }
                        });
                        hi.add(mb);
                        hi.revalidate();
                    }
                } catch (IOException err) {
                    Log.e(err);
                }
            });
        } catch (IOException err) {
            Log.e(err);
        }
        hi.show();
    }

    public void stop() {
        current = getCurrentForm();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {
    }

}
