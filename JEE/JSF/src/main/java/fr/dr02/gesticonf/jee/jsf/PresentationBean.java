package fr.dr02.gesticonf.jee.jsf;

import fr.dr02.gesticonf.jee.ejb.ConferenceManager;
import fr.dr02.gesticonf.jee.ejb.DeviceManager;
import fr.dr02.gesticonf.jee.ejb.PresentationManager;
import fr.dr02.gesticonf.jee.gcm.NotificationSender;
import fr.dr02.gesticonf.jpa.PresentationEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by damien on 04/02/14.
 */
@Stateless
@ManagedBean
@SessionScoped
public class PresentationBean {

    @EJB
    private PresentationManager presentationManager;

    @EJB
    private ConferenceManager conferenceManager;

    @EJB
    private DeviceManager deviceManager;

    private int idPresentation;
    private String date;
    private String heureDebut;
    private String heureFin;
    private String sujet;
    private String orateurs;
    private String resume;
    private String lieu;

    private int refConference;
    private String refName;
    private String nameSearched ="";

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public int getIdPresentation() {
        return idPresentation;
    }

    public void setIdPresentation(int idPresentation) {
        this.idPresentation = idPresentation;
    }

    public String getSujet() { return sujet; }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getOrateurs() { return orateurs; }

    public void setOrateurs(String orateurs) { this.orateurs = orateurs; }

    public String getResume() { return resume; }

    public void setResume(String resume) { this.resume = resume; }

    public int getRefConference() { return refConference; }

    public void setRefConference(int refConference) { this.refConference = refConference; }

    public String getRefName() { return refName;  }

    public void setRefName(String refName) {  this.refName = refName;  }

    public String getLieu() { return lieu; }

    public void setLieu(String lieu) { this.lieu = lieu; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getNameSearched() { return nameSearched; }

    public void setNameSearched(String nameSearched) { this.nameSearched = nameSearched; }

    public void ajouter() {
        refConference = conferenceManager.findByName(refName).getIdConference();
        idPresentation = presentationManager.findIdAvailable();

        PresentationEntity presentationEntity = new PresentationEntity();
        presentationEntity.setIdPresentation(idPresentation);
        presentationEntity.setHeureDeb(heureDebut);
        presentationEntity.setHeureFin(heureFin);
        presentationEntity.setSujet(sujet);
        presentationEntity.setOrateurs(orateurs);
        presentationEntity.setResume(resume);
        presentationEntity.setRefConference(refConference);
        presentationEntity.setDate(date);
        presentationEntity.setLieu(lieu);

        this.presentationManager.ajouter(presentationEntity);
    }

    public void delete() {
        Map<String,String> paramsMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int idPresentation = Integer.valueOf( paramsMap.get("idPresentation") );
        presentationManager.delete(idPresentation);
    }

    public void update() {
        // On récupère la présentation concernée
        // On génère le message contenant les informations qui sera le contenu de la notification
        Map<String,String> paramsMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int idPresentation = Integer.valueOf(paramsMap.get("idPresentation"));
        PresentationEntity pe = presentationManager.find(idPresentation);
        String message;
        String oldSujet = pe.getSujet();

        if ( orateurs.length() > 0 )
            pe.setOrateurs(orateurs);

        if ( heureDebut.length() > 0 )
            pe.setHeureDeb(heureDebut);

        if ( heureFin.length() > 0 )
            pe.setHeureFin(heureFin);

        if ( lieu.length() > 0 )
            pe.setLieu(lieu);

        if ( date.length() > 0 )
            pe.setDate(date);

        if ( sujet.length() > 0 ) {
            pe.setSujet(sujet);
            message = "La présentation sur "+oldSujet+" concernera "+sujet+" et se déroulera " + " le " + pe.getDate() +
                    "de "+pe.getHeureDeb()+
                    " à "+pe.getHeureFin()+" en "+pe.getLieu();
        } else
            message = "La présentation sur "+oldSujet+" se déroulera " + " le " + pe.getDate() +
                    "de "+pe.getHeureDeb()+
                    " à "+pe.getHeureFin()+" en "+pe.getLieu();

        int refConf = pe.getRefConference();

        // On envoie la notification à tous les devices suivant la conférence en question
        NotificationSender.getInstance().setListRegistrationIds(deviceManager.findAllRegistrationsIdsByConf(refConf));
        NotificationSender.getInstance().sendNotification(message);

        // On met à jour la base de données à partir des informations reçues
        presentationManager.update(pe);

        // On réinitialise les données
        this.orateurs = "";
        this.heureDebut = "";
        this.heureFin ="";
        this.lieu = "";
        this.date = "";
        this.sujet = "";

    }

    public void reset() {
        presentationManager.reset();
        conferenceManager.reset();
    }


    public void initFilter() {
        this.nameSearched = "";
    }

    public boolean isPertinent(String namePres) {
        return namePres.toLowerCase().contains(nameSearched.toLowerCase());
    }
}
