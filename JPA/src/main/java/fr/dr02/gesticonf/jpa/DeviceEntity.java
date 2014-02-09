package fr.dr02.gesticonf.jpa;

import javax.persistence.*;

/**
 * Created by damien on 08/02/14.
 */
@Entity
@IdClass(DeviceEntityPK.class)
@Table(name = "DEVICE", schema = "PUBLIC", catalog="GESTICONF")
public class DeviceEntity {

    private String idDevice;
    private String idRegistration;
    private int refConference;

    @Id
    @Column(name="ID_DEVICE")
    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    @Basic
    @Column(name="ID_REGISTRATION")
    public String getIdRegistration() {
        return idRegistration;
    }

    public void setIdRegistration(String idRegistration) {
        this.idRegistration = idRegistration;
    }

    @Id
    @Column(name="REF_CONFERENCE")
    public int getRefConference() { return refConference; }

    public void setRefConference(int refConference) { this.refConference = refConference; }

    @Override
    public String toString() {
        return "Conférence : "+refConference+" -> "+idDevice+" = "+idRegistration.substring(0,20);
    }
}
