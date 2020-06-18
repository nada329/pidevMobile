package first;

import Entite.Event;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import service.CrudEvent;

public class Affichage {

    ArrayList<Event> listEvents = new ArrayList<>();
    Form f;
    SpanLabel lb;

    public Affichage(Resources res) {

        // Picker dateEvent = new Picker();
        CrudEvent se = new CrudEvent();
        listEvents = se.getList2();

        f = new Form();
        lb = new SpanLabel("");
        f.add(lb);
        CrudEvent serviceTask = new CrudEvent();
        lb.setText(serviceTask.getList2().toString());
        for (Event en : listEvents) {
            Container c = new Container();
            System.out.println("first.Affichage.<init>()" + en.getNomEvent() + ",,,,,,,,,,");
            Label nomEvent = new Label(en.getNomEvent());
            Label dateEvent = new Label(en.getDateEvent().toString());
            Label nb_p = new Label(en.getNb_p() + "");
            Label prix = new Label(en.getPrix());
            Label description = new Label(en.getDescription());
            Label adresse_event = new Label(en.getAdresse_event());
            Label type_event = new Label(en.getType_event());
            Label image = new Label(en.getImage());
           
            Style s = UIManager.getInstance().getComponentStyle("Button");
            Image icon = FontImage.createMaterial(FontImage.MATERIAL_CANCEL, s, 3);
            Button supprimer = new Button(icon);

            Image icon1 = FontImage.createMaterial(FontImage.MATERIAL_EDIT, s, 3);
            Button modifier = new Button(icon1);

            c.addAll(BoxLayout.encloseX(
                    BoxLayout.encloseY(nomEvent, dateEvent, nb_p, prix, description, adresse_event, type_event),
                    supprimer, modifier
            ));

            supprimer.addActionListener(ev -> {
                if (Dialog.show("Innformation", "Voulez vous vraiment supprimer cet évenement", "oui", "non")) {
                    se.SupprimerEvent(en.getIdevent());
                    f.revalidate();
                    Affichage aff = new Affichage(res);
                }
                ;
            });
            modifier.addActionListener(ev -> {
                Event t = new Event();
                t.setIdevent(en.getIdevent());
                System.out.println("Id ;"+en.getIdevent());
                t.setNomEvent(en.getNomEvent());
                t.setDateEvent(en.getDateEvent());
                t.setNb_p(en.getNb_p());
                t.setPrix(en.getPrix());
                t.setDescription(en.getDescription());
                t.setAdresse_event(en.getAdresse_event());
                t.setType_event(en.getType_event());
                t.setImage(en.getImage());
                System.out.println(t);
                Modifier m = new Modifier(res, t);
                //se.Modifier(t);

            });

            f.add(c);
        }
        f.getToolbar().addCommandToLeftBar("back", null, en -> new ProfileForm(res).show());

        f.show();

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
