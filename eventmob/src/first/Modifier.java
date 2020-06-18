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

public class Modifier {
String path = "" ;
    public Modifier(Resources res,Event me) {
         
        SpanLabel lb;
        Form modif = new Form("Remplissez ici");
        Button save = new Button("enregistrer");
        modif.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        modif.setTitle("Modifier votre Event");
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
                    modif.revalidate();
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

        bx.setSelectedItem(me.getType_event());
        
        TextField nomEvent = new TextField();
        nomEvent.setText(me.getNomEvent());
        nomEvent.setHint("nom de l'évènement");

        //Date dateEvent = new Date();
        Picker dateEvent = new Picker();
        
        TextField nb_p = new TextField();
        nb_p.setText(String.valueOf(me.getNb_p()));
        nb_p.setHint("nombre de participants");

        TextField prix = new TextField();
        prix.setText(me.getPrix());
        prix.setHint("Prix");

        TextField description = new TextField();
        description.setText(me.getDescription());
        description.setHint("Description");

        TextField adresse_event = new TextField();
        adresse_event.setText(me.getAdresse_event());
        adresse_event.setHint("Adresse de l'évènement");

        TextField image = new TextField();
        image.setText(me.getImage());
        image.setHint("Image de l'évènement");

        modif.add(nomEvent);
        modif.add(nb_p);
        modif.add(prix);
        modif.add(description);
        modif.add(adresse_event);
      Label textC = new Label("Type de l'évènement") ;
      modif.add(textC);
        modif.add(bx);
        modif.add(btn);
        modif.add(imagePlaceholder);
        modif.add(width);
        modif.add(height);
        modif.add(dateEvent);
        modif.add(save);
        modif.getToolbar().addCommandToLeftBar("back", null, e -> new ProfileForm(res).show());
        save.addActionListener((e) -> {
            CrudEvent ser = new CrudEvent();
            Event t = new Event();
            t.setIdevent(me.getIdevent());
            t.setNomEvent(nomEvent.getText());
            t.setDateEvent(dateEvent.getDate()) ;
            t.setNb_p(Integer.parseInt(nb_p.getText())) ;
            t.setPrix(prix.getText());
            t.setDescription(description.getText());
            t.setAdresse_event(adresse_event.getText());
            t.setType_event( bx.getSelectedItem().toString());
            t.setImage(path);
            System.out.println(t);
            ser.Modifier(t);

        });

        modif.show();

    }

}
