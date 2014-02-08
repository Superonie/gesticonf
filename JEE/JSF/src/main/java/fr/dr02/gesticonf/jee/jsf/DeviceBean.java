package fr.dr02.gesticonf.jee.jsf;

import fr.dr02.gesticonf.jee.ejb.DeviceManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Created by damien on 08/02/14.
 */
@Stateless
@ManagedBean
@SessionScoped
public class DeviceBean {

    @EJB
    private DeviceManager deviceManager;

    private String idDevice;
    private String idRegistration;


    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public String getIdRegistration() {
        return idRegistration;
    }

    public void setIdRegistration(String idRegistration) {
        this.idRegistration = idRegistration;
    }

    public void reset() {
        deviceManager.reset();
    }
}
