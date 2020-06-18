package first;

import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.Util;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import static jdk.nashorn.internal.objects.NativeArray.map;

public abstract class SideMenuBaseForm extends Form {
    

    public SideMenuBaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseForm(String title) {
        super(title);
    }

    public SideMenuBaseForm() {
    }

    public SideMenuBaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }
    
    public void setupSideMenu(Resources res) {
         Form sin= new Form("Send Message Sample", BoxLayout.y());
        sin.setTitle("Envoyez-nous vos Messages ici");
        Button b = new Button("Envoyez Message");
           b.addActionListener(e->{
            String email = "nour.affes@esprit.tn";
            Message message = new Message("");
            Display.getInstance().sendMessage(new String[] {email}, "", message);
           
            
        });
           sin.add(b);
           
           sin.getToolbar().addCommandToLeftBar("back", null, e -> new ProfileForm(res).show());
           Form ajj = new Form ("Formulaire d'ajout d'un évènement", BoxLayout.y());
           Button n = new Button("Remplissez ce formulaire");
              n.addActionListener(e->{
            Ajout a = new Ajout (res);
            //a.show();
           
        });
             
             
              
              
              ajj.getToolbar().addCommandToLeftBar("back", null, e -> new ProfileForm(res).show());
              ajj.add(n);
              ajj.show();
              
              Form hr= new Form("L'heure", BoxLayout.y());
              hr.setTitle("Horloge");
              Button bt = new Button("Heure ");
              hr.getToolbar().addCommandToLeftBar("back", null, e -> new ProfileForm(res).show());
             bt.addActionListener(e->{
            Heure h = new Heure ();
            h.start(res);
            
        });
        
           hr.add(bt);
              hr.show();
               Form med= new Form("Vos temoignages", BoxLayout.y());
              med.setTitle("Enregistrements");
              Button en = new Button("Enregistrements ");
              med.getToolbar().addCommandToLeftBar("back", null, e -> new ProfileForm(res).show());
             en.addActionListener(e->{
          
             ToastBar.Status status = ToastBar.getInstance().createStatus();
            status.setMessage("téléchargement en cours");
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
                    status.setMessage("Téléchargé !");
                    status.show();
                }
            }
              media m = new media ();
            m.start(res);
            
        });
             Label enregistrer = new Label("vous trouverez ici vos :");
        enregistrer.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        
        med.add(enregistrer);
        //med.addComponent(new SpanLabel("A task that uses a progress bar to show the user how far along it is"));
           med.add(en);
              med.show();
              Form ls= new Form("Vos évènements", BoxLayout.y());
              med.setTitle("Vos Evènements");
              Button lis = new Button("Liste des évènements ");
              ls.getToolbar().addCommandToLeftBar("back", null, e -> new ProfileForm(res).show());
               lis.addActionListener(e->{
            Affichage aff = new Affichage(res);
             //aff.getF().show();
            
        });
               ls.add(lis);
               ls.show();
              
             
        getToolbar().addMaterialCommandToSideMenu("  Dashboard", FontImage.MATERIAL_DASHBOARD,  e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Ajouter évènement", FontImage.MATERIAL_TRENDING_UP,  e -> ajj.show());
        getToolbar().addMaterialCommandToSideMenu("  Afficher les évènements", FontImage.MATERIAL_SETTINGS,  e -> ls.show());
        getToolbar().addMaterialCommandToSideMenu("  Contactez nous", FontImage.MATERIAL_ACCESS_TIME,  e -> sin.show());
        getToolbar().addMaterialCommandToSideMenu("  Paramètres", FontImage.MATERIAL_SETTINGS,  e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Horloge", FontImage.MATERIAL_SETTINGS,  e -> hr.show());
        getToolbar().addMaterialCommandToSideMenu("  Vos Enregistrements", FontImage.MATERIAL_SETTINGS,  e -> med.show());
        getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_EXIT_TO_APP,  e -> new LoginForm(res).show());
    }
    
    protected abstract void showOtherForm(Resources res);
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