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

    private int idEntity;
    private String heureDeb;

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getHeureDeb() {
        return heureDeb;
    }

    public void setHeureDeb(String heureDeb) {
        this.heureDeb = heureDeb;
    }

    public int getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(int idEntity) {
        this.idEntity = idEntity;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    private String heureFin;
    private String theme;

    public void ajouter() {
        PresentationEntity presentationEntity = new PresentationEntity();
        presentationEntity.setIdEntity(idEntity);
        presentationEntity.setHeureDeb(heureDeb);
        presentationEntity.setHeureFin(heureFin);
        presentationEntity.setSujet(theme);

        this.presentationManager.ajouter(presentationEntity);
    }

}
