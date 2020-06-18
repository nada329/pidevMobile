package Entite;

import java.util.Date;

public class Event {

    private int idevent;
    private String nomEvent;
    private Date dateEvent;
    private int nb_p;
    private String prix;
    private String description;
    private String adresse_event;
    private String type_event;
    private String image ;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Event() {
    }

    public Event(int idevent, String nomEvent, Date dateEvent, int nb_p, String prix, String description, String adresse_event, String type_event) {
        this.idevent = idevent;
        this.nomEvent = nomEvent;
        this.dateEvent = dateEvent;
        this.nb_p = nb_p;
        this.prix = prix;
        this.description = description;
        this.adresse_event = adresse_event;
        this.type_event = type_event;
    }

    public Event(String nomEvent, Date dateEvent, int nb_p, String prix, String description, String adresse_event, String type_event) {
        this.nomEvent = nomEvent;
        this.dateEvent = dateEvent;
        this.nb_p = nb_p;
        this.prix = prix;
        this.description = description;
        this.adresse_event = adresse_event;
        this.type_event = type_event;
    }

    public int getIdevent() {
        return idevent;
    }

    public void setIdevent(int idevent) {
        this.idevent = idevent;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public Date getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(Date dateEvent) {
        this.dateEvent = dateEvent;
    }

    public int getNb_p() {
        return nb_p;
    }

    public void setNb_p(int nb_p) {
        this.nb_p = nb_p;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse_event() {
        return adresse_event;
    }

    public void setAdresse_event(String adresse_event) {
        this.adresse_event = adresse_event;
    }

    public String getType_event() {
        return type_event;
    }

    public void setType_event(String type_event) {
        this.type_event = type_event;
    }

   

    
    
}
