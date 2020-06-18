package service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import Entite.Event;
import com.codename1.ui.Dialog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CrudEvent {

    public void ajoutEvent(Event e) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        String date = formater.format(e.getDateEvent());

        String Url = "http://localhost/Git/lite12/web/app_dev.php/ajouterMobile?"
                + "&nomEvent=" + e.getNomEvent()
                + "&dateEvent=" + date
                + "&nb_p=" + e.getNb_p()
                + "&prix=" + e.getPrix()
                + "&description=" + e.getDescription()
                + "&adresseEvent=" + e.getAdresse_event()
                + "&typeEvent=" + e.getType_event()
                + "&image=" + e.getImage();

        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion
        con.addResponseListener((ev) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
        Dialog.show("succès", "Event Ajouté!", "OK", null);
    }

    public ArrayList<Event> parseListEventsJson(String json) {

        ArrayList<Event> listEvents = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            /*
                On doit convertir notre réponse texte en CharArray à fin de
            permettre au JSONParser de la lire et la manipuler d'ou vient 
            l'utilité de new CharArrayReader(json.toCharArray())
            
            La méthode parse json retourne une MAP<String,Object> ou String est 
            la clé principale de notre résultat.
            Dans notre cas la clé principale n'est pas définie cela ne veux pas
            dire qu'elle est manquante mais plutôt gardée à la valeur par defaut
            qui est root.
            En fait c'est la clé de l'objet qui englobe la totalité des objets 
                    c'est la clé définissant le tableau de tâches.
             */
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));

            /* Ici on récupère l'objet contenant notre liste dans une liste 
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche                
             */
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Event e = new Event();

                float idevent = Float.parseFloat(obj.get("idevent").toString());
                e.setIdevent((int) idevent);

                float NbP = Float.parseFloat(obj.get("nbP").toString());
                //System.out.println("TESTTT ///"+NbP);
                e.setNb_p((int) NbP);

                e.setNomEvent(obj.get("nomEvent").toString());

                String date = obj.get("dateEvent").toString();
                System.out.println("::::::::::::::::" + date);
                Date d2 = new Date();//(long) (date - 3600) * 1000);
                e.setDateEvent(d2);

                e.setDescription(obj.get("description").toString());
                e.setPrix(obj.get("Prix").toString());
                e.setAdresse_event(obj.get("adresseEvent").toString());
                e.setType_event(obj.get("typeEvent").toString());
                e.setImage(obj.get("image").toString());

                System.out.println(e);

                listEvents.add(e);

            }

        } catch (IOException ex) {
        }

        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
         */
        System.out.println(listEvents);
        return listEvents;

    }

    ArrayList<Event> listEvents = new ArrayList<>();

    public ArrayList<Event> getList2() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Git/lite12/web/app_dev.php/afficherMobile");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                CrudEvent ser = new CrudEvent();
                listEvents = ser.parseListEventsJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listEvents;
    }

    public void SupprimerEvent(int id) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Git/lite12/web/app_dev.php/SupprimerMobile/" + id);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
        });
        NetworkManager.getInstance().addToQueue(con);
    }

    public void Modifier(Event e) {
        ConnectionRequest con = new ConnectionRequest();
        System.out.println("Event :");
        System.out.println("Id Event: " + e.getIdevent());
        System.out.println("Date Event : " + e.getDateEvent());
        System.out.println("nombre de participants: " + e.getNb_p());
        System.out.println("Prix: " + e.getPrix());
        System.out.println("Description : " + e.getDescription());
        System.out.println("Adresse Event: " + e.getAdresse_event());
        
        System.out.println("Type event: " + e.getType_event());
        System.out.println("Image : " + e.getImage());
        
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        String date = formater.format(e.getDateEvent());
        
        String Url = "http://localhost/Git/lite12/web/app_dev.php/ModifierMobile?"
                + "&idevent=" + e.getIdevent()
                + "&nomEvent=" + e.getNomEvent()
                + "&dateEvent=" + date
                + "&nb_p=" + e.getNb_p()
                + "&prix=" + e.getPrix()
                + "&description=" + e.getDescription()
                + "&adresseEvent=" + e.getAdresse_event()
                + "&typeEvent=" + e.getType_event()
                + "&image=" + e.getImage();


        con.setUrl(Url);
        con.addResponseListener((ev) -> {
            String str = new String(con.getResponseData());
            System.out.println("Modifier // :" + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

}
