package fr.dr02.gesticonf.jpa;

import javax.persistence.*;

/**
 * Created by damien on 05/02/14.
 */
@Entity
@Table(name = "CONFERENCE", schema = "PUBLIC", catalog="GESTICONF")
public class ConferenceEntity {

    private int idConference;
    private String nomConference; //TODO Rendre Nom Conference UNIQUE en BDD
    private String dateDebut;
    private String dateFin;
    private String theme;

    @Id
    @Column(name = "ID_CONFERENCE")
    public int getIdConference() {
        return idConference;
    }

    public void setIdConference(int idConference) {
        this.idConference = idConference;
    }

    @Basic
    @Column(name ="NOM_CONFERENCE")
    public String getNomConference() {
        return nomConference;
    }

    public void setNomConference(String nom) {
        this.nomConference = nom;
    }

    @Basic
    @Column(name = "DATE_DEBUT")
    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    @Basic
    @Column(name = "DATE_FIN")
    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    @Basic
    @Column(name = "THEME")
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return idConference+ " "+nomConference+" : " + dateDebut + " -> " + dateFin +" sur " + theme;
    }
}