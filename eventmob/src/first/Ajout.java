package first;

import Entite.Event;
import com.codename1.capture.Capture;
import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.util.Date;
import service.CrudEvent;

public class Ajout {
String path = "" ;
    public Ajout(Resources res) {
         
        SpanLabel lb;
        Form ajou = new Form("Remplissez ici");
        Button save = new Button("enregistrer");
        ajou.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        ajou.setTitle("Ajouter votre Event");
        //Ajout a = new Ajout();

        //getToolbar().addCommandToLeftBar("back", null, e -> jou.show());
        TextField width = new TextField();
        width.setHint("width");
        TextField height = new TextField();
        height.setHint("height");
        Button btn = new Button("Image de l'évènement");
        Label imagePlaceholder = new Label();
        btn.addActionListener(e -> {
            int w = -1;
            int h = -1;
            try {
                w = Integer.parseInt(width.getText());
            } catch (Throwable t) {
            }

            try {
                h = Integer.parseInt(height.getText());
            } catch (Throwable t) {
            }

             path = Capture.capturePhoto(w, h);
            if (path != null) {
                try {
                    Image img = Image.createImage(path);
                    imagePlaceholder.setIcon(img);
                    ajou.revalidate();
                } catch (Exception ex) {
                    Log.e(ex);
                }
            }

        });
        ComboBox bx = new ComboBox();
        Label l1 = new Label();
        l1.setText("Sport");
        Label l2 = new Label();
        l2.setText("Culturel");
        Label l3 = new Label();
        l3.setText("Divertissement");
        Label l4 = new Label();
        l4.setText("Excursion");
        bx.addItem("Sport");
        bx.addItem("Culturel");
        bx.addItem("Divertissement");

        TextField nomEvent = new TextField();
        nomEvent.setHint("nom de l'évènement");

        //Date dateEvent = new Date();
        Picker dateEvent = new Picker();
        
        TextField nb_p = new TextField();
        nb_p.setHint("nombre de participants");

        TextField prix = new TextField();
        prix.setHint("Prix");

        TextField description = new TextField();
        description.setHint("Description");

        TextField adresse_event = new TextField();
        adresse_event.setHint("Adresse de l'évènement");

        TextField image = new TextField();
        image.setHint("Image de l'évènement");

        ajou.add(nomEvent);
        //ajou.add(dateEvent);
        ajou.add(nb_p);
        ajou.add(prix);
        ajou.add(description);
        ajou.add(adresse_event);
      //  ajou.add(type_event);
      Label textC = new Label("Type de l'évènement") ;
      ajou.add(textC);
        ajou.add(bx);
        ajou.add(btn);
        ajou.add(imagePlaceholder);
        ajou.add(width);
        ajou.add(height);
        ajou.add(dateEvent);
        ajou.add(save);
        ajou.getToolbar().addCommandToLeftBar("back", null, e -> new ProfileForm(res).show());
        save.addActionListener((e) -> {
            CrudEvent ser = new CrudEvent();
            Event t = new Event();
            t.setNomEvent(nomEvent.getText());
            t.setDateEvent(dateEvent.getDate()) ;
            t.setNb_p(Integer.parseInt(nb_p.getText())) ;
            t.setPrix(prix.getText());
            t.setDescription(description.getText());
            t.setAdresse_event(adresse_event.getText());
            t.setType_event( bx.getSelectedItem().toString());
            t.setImage(path);
            System.out.println(t);
            ser.ajoutEvent(t);

        });

        ajou.show();

    }

}
