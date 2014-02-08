package fr.dr02.gesticonf.jee.rs;

import fr.dr02.gesticonf.jee.ejb.DeviceManager;
import fr.dr02.gesticonf.jpa.DeviceEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by damien on 08/02/14.
 */
@Path("deviceEntityManagerRS")
@RequestScoped
public class DeviceEntityManagerRS{

    @Inject
    DeviceManager deviceManager;

    @POST
    @Path("/addDevice")
    @Consumes({ MediaType.APPLICATION_JSON})
    // Rend persistant le device envoyé en tant qu'Entity dans la requête HTTP-POST
    public void create(DeviceEntity deviceEntity) {
        deviceManager.ajouter(deviceEntity);
    }

}
