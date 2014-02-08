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
    ConferenceManager conferenceManager;

    @EJB
    DeviceManager deviceManager;

    private int idPresentation;
    private String heureDebut;
    private String heureFin;
    private String sujet;
    private int refConference;
    private String refName;

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

    public int getRefConference() { return refConference; }

    public void setRefConference(int refConference) { this.refConference = refConference; }

    public String getRefName() { return refName;  }

    public void setRefName(String refName) {  this.refName = refName;  }

    public void ajouter() {
        refConference = conferenceManager.findByName(refName).getIdConference();
        idPresentation = presentationManager.findIdAvailable();

        PresentationEntity presentationEntity = new PresentationEntity();
        presentationEntity.setIdPresentation(idPresentation);
        presentationEntity.setHeureDeb(heureDebut);
        presentationEntity.setHeureFin(heureFin);
        presentationEntity.setSujet(sujet);
        presentationEntity.setRefConference(refConference);

        this.presentationManager.ajouter(presentationEntity);
    }


    public void modifierHoraire() {
        NotificationSender.getInstance().setListRegistrationIds(deviceManager.findAllRegistrationsIds());
        NotificationSender.getInstance().sendNotification();

    }

}
