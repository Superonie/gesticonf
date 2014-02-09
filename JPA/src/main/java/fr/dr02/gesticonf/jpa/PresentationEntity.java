package fr.dr02.gesticonf.jpa;

import javax.persistence.*;

/**
 * Created by damien on 04/02/14.
 */
@Entity
@Table(name = "PRESENTATION", schema = "PUBLIC", catalog="GESTICONF")
public class PresentationEntity {

    private int idPresentation;
    private String date;
    private String heureDeb;
    private String heureFin;
    private String sujet;
    private String lieu;
    private String orateurs;
    private double latitude;
    private double longitude;
    private int refConference;

    @Id
    @Column(name = "ID_PRESENTATION")
    public int getIdPresentation() {
        return idPresentation;
    }

    public void setIdPresentation(int idEntity) {
        this.idPresentation = idEntity;
    }

    @Basic
    @Column(name = "DATE_PRESENTATION")
    public String getDate() {return date;}

    public void setDate(String date) { this.date = date; }

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

    @Basic
    @Column(name = "LIEU")
    public String getLieu() { return lieu; }

    public void setLieu(String lieu) { this.lieu = lieu; }

    public String getOrateurs() { return orateurs; }

    public void setOrateurs(String orateurs) { this.orateurs = orateurs; }

    @Basic
    @Column(name = "LATITUDE")
    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    @Basic
    @Column(name = "LONGITUDE")
    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    @Basic
    @Column(name = "REF_CONFERENCE")
    public int getRefConference() {
        return refConference;
    }

    public void setRefConference(int refConference) {
        this.refConference = refConference;
    }

    @Override
    public String toString() {
        return refConference+"/"+ idPresentation + " : le " + date + " de " + heureDeb + " à " + heureFin +" sur " + sujet + " par " + orateurs + " en "+ lieu;
    }
}
