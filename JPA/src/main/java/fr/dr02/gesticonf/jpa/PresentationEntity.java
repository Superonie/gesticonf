package fr.dr02.gesticonf.jpa;

import javax.persistence.*;

/**
 * Created by damien on 04/02/14.
 */
@Entity
@Table(name = "PRESENTATION", schema = "PUBLIC", catalog="GESTICONF")
public class PresentationEntity {

    private int idEntity;
    private String heureDeb;
    private String heureFin;
    private String sujet;

    @Id
    @Column(name = "ID_ENTITY")
    public int getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(int idEntity) {
        this.idEntity = idEntity;
    }

    @Basic
    @Column(name = "HEURE_DEBUT")
    public String getHeureDeb() {
        return heureDeb;
    }

    public void setHeureDeb(String heureDeb) {
        this.heureDeb = heureDeb;
    }

    @Basic
    @Column(name = "HEURE_FIN")
    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    @Basic
    @Column(name = "SUJET")
    public String getSujet() {
        return sujet;
    }

    public void setSujet(String theme) {
        this.sujet = theme;
    }

    @Override
    public String toString() {
        return idEntity + " : " + heureDeb + " -> " + heureFin +" sur " + sujet;
    }
}
