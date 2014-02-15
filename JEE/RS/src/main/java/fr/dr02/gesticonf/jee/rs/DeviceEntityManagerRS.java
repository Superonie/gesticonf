package fr.dr02.gesticonf.jee.rs;

import fr.dr02.gesticonf.jee.ejb.DeviceManager;
import fr.dr02.gesticonf.jpa.ConferenceEntity;
import fr.dr02.gesticonf.jpa.DeviceEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by damien on 08/02/14.
 */
@Path("deviceEntityManagerRS")
@RequestScoped
public class DeviceEntityManagerRS{

    @Inject
    DeviceManager deviceManager;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    // Renvoie une collection contenant toutes les conférences existantes
    public String findIdAvailable() {
        return deviceManager.findIdAvailable();
    }


    @POST
    @Path("/addDevice")
    @Consumes({ MediaType.APPLICATION_JSON})
    // Rend persistant le device envoyé en tant qu'Entity dans la requête HTTP-POST
    public void create(DeviceEntity deviceEntity) {
        deviceManager.ajouter(deviceEntity);
    }

    @GET
    @Path("/findDevice/{idConf}/{idDevice}")
    @Produces({ MediaType.APPLICATION_JSON})
    public DeviceEntity find(@PathParam("idConf") String idConf, @PathParam("idDevice") String idDevice ) {
        return deviceManager.findByIdConfIdDevice(idConf, idDevice);
    }

    @GET
    @Path("/findConference/{idDevice}")
    @Produces({ MediaType.APPLICATION_JSON})
    public Collection<ConferenceEntity> findConference(@PathParam("idDevice") String idDevice) {
        return deviceManager.findConferenceByIdDevice(idDevice);
    }

}
