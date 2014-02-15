package fr.dr02.gesticonf.jee.jsf;

import fr.dr02.gesticonf.jee.ejb.ConferenceManager;
import fr.dr02.gesticonf.jpa.ConferenceEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Created by damien on 05/02/14.
 */
@Stateless
@ManagedBean
@SessionScoped
public class ConferenceBean {

    @EJB
    private ConferenceManager conferenceManager;

    private int idConference;
    private String nomConference;
    private String dateDebut;
    private String dateFin;
    private String theme;

    private boolean render = false;
    private String nameSearched = "";

    public int getIdConference() {
        return idConference;
    }

    public void setIdConference(int idConference) {
        this.idConference = idConference;
    }

    public String getNomConference() {
        return nomConference;
    }

    public void setNomConference(String nomConference) {
        this.nomConference = nomConference;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isRender() { return render; }

    public void setRender(boolean render) { this.render = render; }

    public String getNameSearched() { return nameSearched; }

    public void setNameSearched(String nameSearched) { this.nameSearched = nameSearched; }

    public void ajouter() {
        idConference = conferenceManager.findIdAvailable() ;

        ConferenceEntity conferenceEntity = new ConferenceEntity();
        conferenceEntity.setIdConference(idConference);
        conferenceEntity.setNomConference(nomConference);
        conferenceEntity.setDateDebut(dateDebut);
        conferenceEntity.setDateFin(dateFin);
        conferenceEntity.setTheme(theme);

        this.conferenceManager.ajouter(conferenceEntity);
    }

    public void initFilter() {
        this.nameSearched = "";
    }

    public boolean isPertinent(String confName) {

         return confName.toLowerCase().contains(nameSearched.toLowerCase());
    }

}
