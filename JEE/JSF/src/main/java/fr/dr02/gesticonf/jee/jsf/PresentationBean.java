package fr.dr02.gesticonf.jee.jsf;

import fr.dr02.gesticonf.jee.ejb.PresentationManager;
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

    private int idPresentation;
    private String heureDebut;
    private String heureFin;
    private String theme;

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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void ajouter() {
        PresentationEntity presentationEntity = new PresentationEntity();
        presentationEntity.setIdEntity(idPresentation);
        presentationEntity.setHeureDeb(heureDebut);
        presentationEntity.setHeureFin(heureFin);
        presentationEntity.setSujet(theme);

        this.presentationManager.ajouter(presentationEntity);
    }

}
