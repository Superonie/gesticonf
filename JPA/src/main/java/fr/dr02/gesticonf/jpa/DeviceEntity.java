package fr.dr02.gesticonf.jpa;

import javax.persistence.*;

/**
 * Created by damien on 08/02/14.
 */
@Entity
@Table(name = "DEVICE", schema = "PUBLIC", catalog="GESTICONF")
public class DeviceEntity {

    private String idDevice;
    private String idRegistration;

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

    @Override
    public String toString() {
        return idDevice+" "+idRegistration;
    }
}
