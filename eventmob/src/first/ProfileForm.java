package first;

import com.codename1.components.FloatingActionButton;
import static com.codename1.io.rest.Rest.options;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

public class ProfileForm extends SideMenuBaseForm {
    public ProfileForm(Resources res) {
         super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
//        Image profilePic = res.getImage("user-picture.jpg");
//        Image mask = res.getImage("round-mask.png");
//        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
//        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
//        profilePicLabel.setMask(mask.createMask());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        

        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseY(
                                    new Label("Studynet", "Title"),
                                    new Label("User de l'école", "SubTitle")
                                )
                            ).add(BorderLayout.WEST, "")//,
                       // GridLayout.encloseIn(2, remainingTasks, completedTasks)
                );
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
       // fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
                        
        add(new Label("Aujourd'hui", "TodayTitle"));
         Button b = new Button("Localisation");
        b.setUIID("Localisation");
        b.addActionListener(new ActionListener() {
          @Override
            public void actionPerformed(ActionEvent evt) {
               mapp m = new mapp();
               
               m.start(res);
               

            }
        }
        );
        
        
        titleCmp.add(b);
      
        
        setupSideMenu(res);
        
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
   

   
}