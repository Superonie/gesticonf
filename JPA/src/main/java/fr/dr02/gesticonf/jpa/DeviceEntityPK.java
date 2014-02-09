package fr.dr02.gesticonf.jpa;

import java.io.Serializable;

/**
 * Created by damien on 09/02/14.
 */
public class DeviceEntityPK implements Serializable {

    private String idDevice;
    private int refConference;

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public int getRefConference() {
        return refConference;
    }

    public void setRefConference(int refConference) {
        this.refConference = refConference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceEntityPK that = (DeviceEntityPK) o;

        if (refConference != that.refConference) return false;
        if (idDevice != null ? !idDevice.equals(that.idDevice) : that.idDevice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDevice != null ? idDevice.hashCode() : 0;
        result = 31 * result + refConference;
        return result;
    }
}
